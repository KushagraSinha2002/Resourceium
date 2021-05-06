package com.bitlegion.server.accounts;

import java.util.Enumeration;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TokenChecker {

    @Autowired
    private TokenRepository tokenRepository;

    public Token checkAndReturnTokenOrRaiseException(HttpServletRequest httpRequest) throws Exception {
        Enumeration<String> headerNames = httpRequest.getHeaderNames();
        if (headerNames != null) {
            String auth = httpRequest.getHeader("Authorization");
            auth = auth.replace("Token ", "");
            System.out.println("Token: " + auth);
            Optional<Token> token = tokenRepository.findByString(auth);
            if (auth == null || token.isEmpty()) {
                throw new Exception("You are not authenticated to perform this action");
            } else {
                Token tokenVerified = tokenRepository.findByString(auth).get();
                return tokenVerified;
            }
        } else {
            throw new Exception("You are not authenticated to perform this action");
        }
    }
}
