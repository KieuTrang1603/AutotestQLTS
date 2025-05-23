package helpers;
import model.AuthResponse;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.Header;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.message.StatusLine;
import java.util.Base64;

public class AuthHelper {

    private static final String LOGIN_URL = "http://asset-management-bv199.oceantech.com.vn/oauth/token";
    private static final String GET_URL = "http://bv199.oceantech.com.vn/asvn/api/v1/fixed-assets/allocation-vouchers/page?keyword=&pageIndex=1&pageSize=5";
    private static final String USERNAME = "trangptt"; // Thay bằng tài khoản thật
    private static final String PASSWORD = "123456"; // Thay bằng mật khẩu thật
    private static final String CLIENT_ID = "core_client";
    private static final String CLIENT_SECRET = "secret";

    public static AuthResponse getAuthResponse(String username, String password) throws Exception {
        try (CloseableHttpClient client = HttpClients.createDefault()) {

            // Bước 1: Gọi /oauth/token để lấy access token
            HttpPost post = new HttpPost(LOGIN_URL);

            String basicAuth = Base64.getEncoder()
                    .encodeToString((CLIENT_ID + ":" + CLIENT_SECRET).getBytes());
            post.setHeader("Authorization", "Basic " + basicAuth);
            post.setHeader("Content-Type", "application/x-www-form-urlencoded");

            String formBody = "grant_type=password&username=" + username + "&password=" + password;
            post.setEntity(new StringEntity(formBody, ContentType.APPLICATION_FORM_URLENCODED));

            String accessToken;

            try (CloseableHttpResponse response = client.execute(post)) {
                int statusCode = response.getCode();
                String responseBody = EntityUtils.toString(response.getEntity());

                if (statusCode != 200) {
                    throw new RuntimeException("Lỗi lấy token: " + statusCode + "\n" + responseBody);
                }

                org.json.JSONObject json = new org.json.JSONObject(responseBody);
                accessToken = json.getString("access_token");
            }

            // Bước 2: Gọi API có xác thực để lấy cookie (JSESSIONID)
            HttpGet get = new HttpGet(GET_URL);
            get.setHeader("Authorization", "Bearer " + accessToken);

            String jsessionId = null;

            try (CloseableHttpResponse response = client.execute(get)) {
                Header[] cookieHeaders = response.getHeaders("Set-Cookie");

                for (Header header : cookieHeaders) {
                    if (header.getValue().startsWith("JSESSIONID")) {
                        jsessionId = header.getValue().split(";")[0].split("=")[1];
                        break;
                    }
                }

                if (jsessionId == null) {
                    throw new RuntimeException("Không lấy được JSESSIONID từ Set-Cookie.");
                }
            }
            return new AuthResponse(accessToken, jsessionId);
        }
    }
}
