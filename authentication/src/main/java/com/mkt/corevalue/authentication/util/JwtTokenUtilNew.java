package com.mkt.corevalue.authentication.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Clock;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClock;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.stereotype.Component;



@Component
public class JwtTokenUtilNew implements Serializable {

    
    private static final long serialVersionUID = -3301605591108950415L;
 
    private Clock clock = DefaultClock.INSTANCE;

   
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token.replace(AutheticationConstant.TOKEN_PREFIX, ""), Claims::getSubject);
    }

    public Date getIssuedAtDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getIssuedAt);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
            .setSigningKey(AutheticationConstant.SECRET)
            .parseClaimsJws(token)
            .getBody();
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(clock.now());
    }

    private Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
        return (lastPasswordReset != null && created.before(lastPasswordReset));
    }

    private Boolean ignoreTokenExpiration(String token) {
        // here you specify tokens, for that the expiration is ignored
        return false;
    }

    public String generateToken(String employeeId) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, employeeId);
    }

    private String doGenerateToken(Map<String, Object> claims, String subject) {
        final Date createdDate = clock.now();
        final Date expirationDate = calculateExpirationDate(createdDate);

        String JWT=Jwts.builder()
            .setClaims(claims)
            .setSubject(subject)
            .setIssuedAt(createdDate)
            .setExpiration(expirationDate)
            .signWith(SignatureAlgorithm.HS512, AutheticationConstant.SECRET)
            .compact();
        
        return AutheticationConstant.TOKEN_PREFIX + " " + JWT;
    }

    public Boolean canTokenBeRefreshed(String token, Date lastPasswordReset) {
        final Date created = getIssuedAtDateFromToken(token.replace(AutheticationConstant.TOKEN_PREFIX, ""));
        return !isCreatedBeforeLastPasswordReset(created, lastPasswordReset)
            && (!isTokenExpired(token.replace(AutheticationConstant.TOKEN_PREFIX, "")) || ignoreTokenExpiration(token.replace(AutheticationConstant.TOKEN_PREFIX, "")));
    }

    public String refreshToken(String token) {
        final Date createdDate = clock.now();
        final Date expirationDate = calculateExpirationDate(createdDate);

        final Claims claims = getAllClaimsFromToken(token.replace(AutheticationConstant.TOKEN_PREFIX, ""));
        claims.setIssuedAt(createdDate);
        claims.setExpiration(expirationDate);

        return Jwts.builder()
            .setClaims(claims)
            .signWith(SignatureAlgorithm.HS512, AutheticationConstant.SECRET)
            .compact();
    }

    public Boolean validateToken(String token, String employeeId ) {
       
        final String employeeId_from_token = getUsernameFromToken(token.replace(AutheticationConstant.TOKEN_PREFIX, ""));
        //final Date created = getIssuedAtDateFromToken(token);
       
        return (
        employeeId_from_token.equals(employeeId)
                    && !isTokenExpired(token.replace(AutheticationConstant.TOKEN_PREFIX, ""))
                   
            );
       
    }

    private Date calculateExpirationDate(Date createdDate) {
        return new Date(createdDate.getTime() + AutheticationConstant.EXPIRATION_TIME * 1000);
    }
    
    public static void main(String[]args){
    String employeeId="12345";
    JwtTokenUtilNew jwt=new JwtTokenUtilNew();
    String token=jwt.generateToken(employeeId);
    System.out.println("Generated Token: "+token);
    String empID=jwt.getUsernameFromToken(token);
    System.out.println(empID);
Boolean br=jwt.validateToken(token, employeeId);
System.out.println(br);
    }
}
