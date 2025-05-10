package kr.modusplant.global.middleware.security;

import kr.modusplant.global.middleware.security.models.SiteMemberAuthToken;
import kr.modusplant.global.middleware.security.models.SiteMemberUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SiteMemberAuthProvider implements AuthenticationProvider {

    private final SiteMemberUserDetailsService memberUserDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getCredentials().toString();
        String password = authentication.getPrincipal().toString();

        SiteMemberUserDetails userDetails = memberUserDetailsService.loadUserByUsername(email);

        if(passwordEncoder.matches(password, userDetails.getPassword())) {
            return new SiteMemberAuthToken(userDetails, userDetails.getAuthorities());
        } else {
            return new SiteMemberAuthToken(email, password);
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return SiteMemberAuthToken.class.isAssignableFrom(authentication);
    }
}
