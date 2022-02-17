package com.encuesta.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.encuesta.models.requests.UserLoginRequestModel;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    // AUTHENTICATION FILTER

    private final AuthenticationManager authenticationManager;

    public AuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        try {
            /*
             * vamos a recibir datos x medio de request recibimos username
             * y password los cuales los mapeamos hacia un modelo
             * llamado UserLoginRequestModel
             */
            UserLoginRequestModel userModel = new ObjectMapper().readValue(request.getInputStream(),
                    UserLoginRequestModel.class);
            /*
             * de esa request tomamos el user y password y luego se los mapeamos a
             * UserLoginRequestModel
             */

            return authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(userModel.getEmail(), userModel.getPassword(),
                            new ArrayList<>()));

        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

    }

    /*
     * si usuario existe en la base de datos llama al siguiente metodo
     * que se encarga de crear un token
     */

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authResult) throws IOException, ServletException {

        String email = ((User) authResult.getPrincipal()).getUsername();

        // generamos el token

        String token = Jwts.builder()
                .setSubject(email) // username
                .setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_DATE)) // fecha de
                                                                                                         // expiracion
                .signWith(SignatureAlgorithm.HS512, SecurityConstants.getTokenSecret())
                .compact(); // firmado con algoritmo
        String data = new ObjectMapper().writeValueAsString(Map.of("token", SecurityConstants.TOKEN_PREFIX + token));

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().println(data);
        response.flushBuffer();
    }

}
