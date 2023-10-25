package io.ylab.walletservice.in.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import io.ylab.walletservice.aop.annotations.Loggable;
import io.ylab.walletservice.core.conf.PropertiesLoaderToken;
import io.ylab.walletservice.core.dto.UserDTO;

import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * Class for creating, validating token and getting information from token
 */
@Loggable
public class JWTTokenHandler {

    /**
     * Define a field with a type {@link Properties} for further aggregation
     */
    private final Properties property = PropertiesLoaderToken.loadProperties();

    /**
     * Define a field with a type {@link ObjectMapper} for further aggregation
     */
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Generate token by {@link UserDTO}
     * @param dto and put it into token
     * @return token
     */
    public String generateUserAccessToken(UserDTO dto) {
        return generateUserAccessToken(dto, dto.getLogin());
    }

    /**
     * Generate token by {@link UserDTO} and name of user
     * @param dto and put it into token
     * @param name and put it into token
     * @return token
     */
    public String generateUserAccessToken(UserDTO dto, String name) {
        return Jwts.builder()
                .claim(property.getProperty("jwt.user"), dto)
                .setSubject(name)
                .setIssuer(property.getProperty("jwt.issuer"))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(7))) // 1 week
                .signWith(SignatureAlgorithm.HS512, property.getProperty("jwt.secret"))
                .compact();
    }

    /**
     * Generate token by system for system
     * @param name should be "system"
     * @return token
     */
    public String generateSystemAccessToken(String name) {
        return Jwts.builder()
                .setSubject(name)
                .setIssuer(property.getProperty("jwt.issuer"))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(7))) // 1 week
                .signWith(SignatureAlgorithm.HS512, property.getProperty("jwt.secret"))
                .compact();
    }

    /**
     * Extract claim
     * @param token with claims
     * @param claimsResolver help to extract claim
     * @return all claims
     * @param <T> generated type for Function
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Extract all claims from token
     * @param token with claims
     * @return claims from token
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(property.getProperty("jwt.secret"))
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Give name of user from token
     * @param token has claims with name of user
     * @return name of user
     */
    public String getUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Ginve {@link UserDTO} from token
     * @param token has claims with field of {@link UserDTO}
     * @return {@link UserDTO}
     */
    public UserDTO getUser(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(property.getProperty("jwt.secret"))
                .parseClaimsJws(token)
                .getBody();
        return objectMapper.convertValue(claims.get(property.getProperty("jwt.user")), UserDTO.class);
    }

    public boolean validate(String token) {
        try {
            Jwts.parser().setSigningKey(property.getProperty("jwt.secret")).parseClaimsJws(token);
            return true;
        } catch (SignatureException ex) {
            //logger.error("Invalid JWT signature - {}", ex.getMessage());
        } catch (MalformedJwtException ex) {
            //logger.error("Invalid JWT token - {}", ex.getMessage());
        } catch (ExpiredJwtException ex) {
            //logger.error("Expired JWT token - {}", ex.getMessage());
        } catch (UnsupportedJwtException ex) {
            //logger.error("Unsupported JWT token - {}", ex.getMessage());
        } catch (IllegalArgumentException ex) {
            //logger.error("JWT claims string is empty - {}", ex.getMessage());
        }
        return false;
    }
}
