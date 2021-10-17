package com.messenger.demo.security;

import com.messenger.demo.dao.StudentRepository;
import com.messenger.demo.entity.Student;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final StudentRepository studentRepository;

    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {
        String login = authentication.getName();
        String password = authentication.getCredentials().toString();
        Student student = studentRepository.findByLogin(login);
        if (student == null) {
            throw new BadCredentialsException("Unknown user " + student);
        }
        if (!password.equals(student.getPassword())) {
            throw new BadCredentialsException("Bad password");
        }
        UserDetails principal = User.builder()
                .username(student.getLogin())
                .password(student.getPassword())
                .roles(student.getRole().getName())
                .build();
        return new UsernamePasswordAuthenticationToken(
                principal, password, principal.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

//    private ResponseEntity<?> getToken(){
//        return (ResponseEntity<?>) ResponseEntity.ok()
//                .header(
//                        HttpHeaders.AUTHORIZATION
//                );
//    }

}
