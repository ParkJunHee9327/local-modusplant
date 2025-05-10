package kr.modusplant.global.middleware.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.modusplant.global.middleware.security.models.SiteMemberAuthToken;
import kr.modusplant.modules.auth.normal.model.NormalLoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.stream.Collectors;

@Component
public class JsonEmailAuthFilter extends AbstractAuthenticationProcessingFilter {

    private final ObjectMapper objectMapper;
    private final AuthenticationManager authManager;

    protected JsonEmailAuthFilter(
            ObjectMapper objectMapper,
            AuthenticationManager authManager) {
        super("/api/auth/login");
        this.objectMapper = objectMapper;
        this.authManager = authManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {

        NormalLoginRequest loginRequest = objectMapper.readValue(request.getInputStream(), NormalLoginRequest.class);

        if(loginRequest.isAllValid()) { throw new IllegalArgumentException("one of email password deviceId missing"); }

        SiteMemberAuthToken requestToken = new SiteMemberAuthToken(
                loginRequest.email(), loginRequest.password()
        );

        Authentication authentication = authManager.authenticate(requestToken);
        request.setAttribute("authentication", authentication);

        return authentication;
    }
}
