package backend.likelion.todos.auth.jwt;

import static java.util.concurrent.TimeUnit.DAYS;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

import backend.likelion.todos.common.UnAuthorizedException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import java.util.Date;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

    private final long accessTokenExpirationDayToMills;
    private final Algorithm algorithm;

    public JwtService(JwtProperty jwtProperty) {
        this.accessTokenExpirationDayToMills =
                MILLISECONDS.convert(jwtProperty.accessTokenExpirationDay(), DAYS);
        this.algorithm = Algorithm.HMAC512(jwtProperty.secretKey());
    }

    // 회원 ID를 기반으로 JWT 토큰을 생성합니다.
    public String createToken(Long memberId) {
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + accessTokenExpirationDayToMills);
        return JWT.create()
            .withClaim("memberId", memberId)
            .withExpiresAt(expirationDate)
            .sign(algorithm);
    }

    // 토큰에서 회원 ID를 추출합니다.
    public Long extractMemberId(String token) {
        try {
            return JWT.require(algorithm)
                .build()
                .verify(token)
                .getClaim("memberId")
                .asLong();
        } catch (JWTVerificationException exception) {
            throw new UnAuthorizedException("유효하지 않은 토큰입니다.");
        }
    }
}
