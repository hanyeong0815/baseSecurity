package com.second.security.authentication;

import com.second.security.authentication.token.CommonAuthenticationToken;
import com.second.security.authentication.token.UserAuthenticationToken;
import com.second.security.service.UserService;
import com.second.security.utills.PasswordEncoderStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserAuthenticationProvider implements AuthenticationProvider {
    private final UserService userService;
    private final PasswordEncoderStorage pwEncoders;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UserDetails user = userService.loadUserByUsername(authentication.getName());

        boolean isAuthenticated = pwEncoders.getPasswordEncoder().matches((String)authentication.getCredentials(), user.getPassword());

        if (isAuthenticated) {
            authentication = CommonAuthenticationToken.authenticated(UserAuthenticationToken.class, authentication.getName(), user, user.getAuthorities());
            ((CommonAuthenticationToken)authentication).eraseCredentials();
            return authentication;
        } else throw new BadCredentialsException("wrong authentication information");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UserAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
