package com.mkt.corevalue.authentication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mkt.corevalue.authentication.response.JwtAuthenticationResponse;
import com.mkt.corevalue.authentication.util.JwtTokenUtilNew;

@RestController
@RequestMapping("/token")
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtilNew jwtTokenUtil;
  
    @RequestMapping(value = "/generate-token", method = RequestMethod.POST)
    public ResponseEntity<?> generateToken(@RequestBody LoginUser loginUser) 
              throws AuthenticationException {
        final Authentication authentication = authenticationManager.authenticate(
                (Authentication) new UsernamePasswordAuthenticationToken(
                        loginUser.getUsername(),
                        loginUser.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
       // final User user = userService.findOne(loginUser.getUserName());
        final String token = jwtTokenUtil.generateToken(loginUser.getUsername());
        return ResponseEntity.ok(new JwtAuthenticationResponse(token));
    }
}
