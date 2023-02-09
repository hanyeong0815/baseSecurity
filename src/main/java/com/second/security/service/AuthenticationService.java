package com.second.security.service;

import com.second.security.common.RandomGenerator;
import com.second.security.domain.RefreshToken;
import com.second.security.domain.RefreshTokenRepo;
import com.second.security.domain.Users;
import com.second.security.domain.UsersRepo;
import com.second.security.dto.response.JwtResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UsersRepo usersRepo;
    private final RefreshTokenRepo refreshTokenRepo;
    private final JwtEncoder jwtEncoder;
    private static final String USER = "USER";
    private static final String SELF = "self";
    private static final String TOKEN_TYPE = "bearer";
    private static final String PRIVATE_CLAIM_SCOPE = "scope";
    private static final Long EXPIRY = 3600L;
    private static final Long REFRESH_EXPIRY = 2628000L;

    public JwtResponse issueUserJwt(Authentication authentication) {
        Instant now = Instant.now();
        String subject = USER + "|" + authentication.getName();
        String scope = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(" "));
        Users userInfo = usersRepo.findByUsername(authentication.getName()).get();

        return new JwtResponse(userInfo.getId(), userInfo.getUsername(), accessToken(SELF, subject, scope, now), makeRefreshToken(subject, now), EXPIRY, REFRESH_EXPIRY, TOKEN_TYPE);
    }

    private String accessToken(String issuer, String subject, String scope, Instant now) {
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer(issuer)
                .subject(subject)
                .issuedAt(now)
                .expiresAt(now.plusSeconds(EXPIRY))
                .claim(PRIVATE_CLAIM_SCOPE, scope)
                .build();
        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    private String makeRefreshToken(String subject, Instant now) {
        try {
            refreshTokenRepo.updateEnabledBySubject(false, subject);
            RefreshToken refreshToken = new RefreshToken(
                    subject,
                    RandomGenerator.Alpha(128),
                    now,
                    REFRESH_EXPIRY
            );
            refreshTokenRepo.save(refreshToken);
            return refreshToken.getToken();
        }catch (Exception e) {
            return makeRefreshToken(subject, now);
        }
    }
}
