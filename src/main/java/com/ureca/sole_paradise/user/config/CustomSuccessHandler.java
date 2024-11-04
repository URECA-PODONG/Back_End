package com.ureca.sole_paradise.user.config;

import com.ureca.sole_paradise.user.db.dto.CustomOAuth2User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.Iterator;

@Component
@RequiredArgsConstructor
public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        //OAuth2User
        CustomOAuth2User customUserDetails = (CustomOAuth2User) authentication.getPrincipal();

        //ROLE 추출
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();
        String role = auth.getAuthority();

        // 04.14 - 비회원 상태일경우 가입 페이지로, 커스텀 필요
        if (role.equals("ROLE_VALIDATE")) {
            response.setStatus(205);
            //회원가입 페이지
            response.sendRedirect("http://localhost:5173/userRegister/" + URLEncoder.encode(customUserDetails.getEmail(), "UTF-8"));

            //    response.sendRedirect("http://localhost:5173/userRegister/:userId");
            return;
        }

        response.sendRedirect("http://localhost:5173/MainPage/" + customUserDetails.getUserId());
    }
}