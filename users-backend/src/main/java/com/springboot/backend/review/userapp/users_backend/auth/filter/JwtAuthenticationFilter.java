
package com.springboot.backend.review.userapp.users_backend.auth.filter;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.backend.review.userapp.users_backend.entities.User;

import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import static  com.springboot.backend.review.userapp.users_backend.auth.TokenJwtConfig.SECRET_KEY;;

public class JwtAuthenticationFilter  extends UsernamePasswordAuthenticationFilter{

    private AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager){
        this.authenticationManager = authenticationManager;
    }

    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletRequest response) throws AuthenticationException{

        String username = null;
        String password = null;
        try{
            
            User user = new ObjectMapper().readValue(request.getInputStream(), User.class);
            password = user.getPassword();
            username = user.getUsername();
        }catch(StreamReadException e){
            e.printStackTrace();
        }catch(DatabindException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
            username,
            password
        );
        return this.authenticationManager.authenticate(authenticationToken);

    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authResult) throws IOException, ServletException {
        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) authResult.getPrincipal();
        String username = user.getUsername();
        String jwt = Jwts.builder()
                .subject(username)
                .signWith(SECRET_KEY)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+3600000))
                .compact();

        response.addHeader("Authorization", "Bearer " + jwt);
        Map <String, String > body = new HashMap<>();
        body.put("token", jwt);
        body.put("username", username);
        body.put("message", String.format("Hola %s has iniciado sesion con exito ", username));
        response.getWriter().write(new ObjectMapper().writeValueAsString(body));
        response.setContentType("application/json");
        response.setStatus(200);


    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException failed) throws IOException, ServletException {
                
    }



    
    }

    

}
