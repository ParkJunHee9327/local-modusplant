package kr.modusplant.modules.auth.normal.model;

public record NormalLoginRequest(
        String email,
        String password,
        String deviceID
) {
    public boolean isAllValid() {
        return email != null && password != null && deviceID != null;
    }
}
