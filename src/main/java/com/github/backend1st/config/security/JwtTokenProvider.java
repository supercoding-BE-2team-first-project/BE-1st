package com.github.backend1st.config.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;


import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {
    private String secretKeySource = "backend-2team";
    private String secretKey = Base64.getEncoder()
            .encodeToString(secretKeySource.getBytes());

    private long tokenValidMillisecond = 1000L * 60 * 60;//만료 시간
    private final UserDetailsService userDetailsService;
    public String resolveToken(HttpServletRequest httpServletRequest){
        return httpServletRequest.getHeader("TOKEN");//헤더이름 TOKEN
    }
    public boolean validateToken(String jwtToken) {
        try{//만료시간 확인 로직
            Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken).getBody();
            Date now = new Date();
            return claims.getExpiration().after(now);
        }catch(Exception e){
            return false;//유효기간이 이상하거나 다른문제가있으면 false
        }
    }
    public String createToken(String email,List<String> roles){
        Claims claims = Jwts.claims()
                .setSubject(email);
//        List<String> stringList = Arrays.asList("123","123");
        claims.put("roles", roles);

        Date now = new Date();
        return Jwts.builder()
                .setIssuedAt(now)
                .setClaims(claims)
                .setExpiration(new Date(now.getTime()+tokenValidMillisecond))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }
    private String getUserEmail(String jwtToken) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken).getBody().getSubject();
    }
    public Authentication getAuthentication(String jwtToken) {
        UserDetails userDetails =  userDetailsService.loadUserByUsername(getUserEmail(jwtToken));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());//userDetails ,자격, 권한
    }

}
