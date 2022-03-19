package com.sowloo.sowloowrita.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@Slf4j
public class CustomAuthorizationFilter extends BasicAuthenticationFilter {
    public CustomAuthorizationFilter(AuthenticationManager authenticationManager){
        super(authenticationManager);
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");

        if ( header == null || !header.startsWith("Bearer ")){
            filterChain.doFilter(request, response);
        }
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(
                JWT.require(Algorithm.HMAC256("beingsweetandwritingcode@395"))
                        .build().verify(request.getHeader("Authorization").replace(
                                "Bearer ", ""
                        )).getSubject(), null,new ArrayList<>()));


    }
}
