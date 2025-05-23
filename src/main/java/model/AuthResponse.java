package model;

public class AuthResponse {
    public String accessToken;
    public String jsessionId;

    public AuthResponse(String accessToken, String jsessionId) {
        this.accessToken = accessToken;
        this.jsessionId = jsessionId;
    }
}
