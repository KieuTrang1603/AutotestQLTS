package tests.allo_vouchers.web;

import helpers.AuthHelper;
import model.AuthResponse;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Allocations_VoucherTestAPIWeb {

    private String validToken,jsessionId;
    private String jsonPayload;

    private final String URL = "http://bv199.oceantech.com.vn/asvn/api/v1/fixed-assets/allocation-vouchers";

    @BeforeClass
    public void setup() throws Exception {
        AuthResponse auth = AuthHelper.getAuthResponse("trangptt","123456");
        validToken = auth.accessToken;
        jsessionId= auth.jsessionId;
        jsonPayload = Files.readString(Paths.get("src/main/resources/vd.json"), StandardCharsets.UTF_8);
    }

    @DataProvider(name = "tokenCases")
    public Object[][] tokenCases() {
        return new Object[][]{
                {"Không có token", null, 401},
                {"Token hợp lệ", "Bearer " + validToken, 200},
                {"Token sai", "Bearer invalid_token_123456", 401},
                {"Token rỗng", "Bearer ", 401},
                {"Không phải Bearer", validToken, 401},
                {"Chỉ có Cookie", "JSESSIONID=" + jsessionId, 401}
        };
    }

    @Test(dataProvider = "tokenCases")
    public void testSecurityScenarios(String scenario, String tokenHeader, int expectedStatusCode) throws IOException, ParseException {
        System.out.println("Scenario: " + scenario);

        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost post = new HttpPost(URL);
            post.setHeader("Content-Type", "application/json");
            if (tokenHeader != null) {
                post.setHeader("Authorization", tokenHeader);
            }

            post.setEntity(new StringEntity(jsonPayload, ContentType.APPLICATION_JSON));
            try (CloseableHttpResponse response = client.execute(post)) {
                int actualStatus = response.getCode();
                String responseBody = EntityUtils.toString(response.getEntity());
                System.out.println("Status: " + actualStatus);
                System.out.println("Response: " + responseBody);

                Assert.assertEquals(actualStatus, expectedStatusCode, "Kết quả không khớp với mong đợi.");
            }
        }
    }
    @Test
    public void testTokenUserNoPermission() throws Exception {

        // Lấy token người dùng B
        String tokenUserB = AuthHelper.getAuthResponse("khoagmhs", "123456").accessToken;

        // Gửi API tạo phiếu nhưng dùng token của user B
        String jsonPayload = Files.readString(Paths.get("src/main/resources/test.json"), StandardCharsets.UTF_8);
        String url = "http://bv199.oceantech.com.vn/asvn/api/v1/fixed-assets/allocation-vouchers";

        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost post = new HttpPost(url);
            post.setHeader("Content-Type", "application/json");

            // Giả lập dùng token của User B trong context của User A
            post.setHeader("Authorization", "Bearer " + tokenUserB);

            post.setEntity(new StringEntity(jsonPayload, ContentType.APPLICATION_JSON));

            try (CloseableHttpResponse response = client.execute(post)) {
                int statusCode = response.getCode();
                String responseBody = new String(response.getEntity().getContent().readAllBytes(), StandardCharsets.UTF_8);

                System.out.println("Status Code: " + statusCode);
                System.out.println("Response Body: " + responseBody);

                ObjectMapper mapper = new ObjectMapper();
                JsonNode jsonNode = mapper.readTree(responseBody);

                int appCode = jsonNode.path("code").asInt();
                String message = jsonNode.path("message").asText();

                //Kiểm tra mã lỗi nghiệp vụ
                Assert.assertEquals(appCode, 401001, "Phải trả về mã lỗi không có quyền.");
                Assert.assertEquals(message, "Bạn không có quyền", "Thông báo lỗi phải đúng.");
            }
        }
    }
}
