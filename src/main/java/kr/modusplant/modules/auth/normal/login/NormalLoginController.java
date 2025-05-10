package kr.modusplant.modules.auth.normal.login;

import jakarta.servlet.http.HttpServletRequest;
import kr.modusplant.global.app.servlet.response.DataResponse;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class NormalLoginController {

    @PostMapping("/login")
    public DataResponse<Void> processLogin(HttpServletRequest request) {
        Authentication auth = (Authentication) request.getAttribute("authentication");

        // TODO: JWT 토큰 생성 후 반환
        return DataResponse.ok();
    }
}
