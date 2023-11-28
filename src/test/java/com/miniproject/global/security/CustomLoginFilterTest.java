package com.miniproject.global.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.miniproject.domain.member.request.LoginRequest;
import com.miniproject.global.jwt.service.JwtService;
import com.miniproject.global.jwt.service.TokenPair;
import com.miniproject.global.security.login.CustomLoginToken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


//@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomLoginFilterTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private JwtService jwtService;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @DisplayName("로그인이 성공하면 isOK 를 반환한다. ")
    @Test
    public void testSuccessfulAuthentication() throws Exception {
        String username = "testUser";
        String password = "testPassword";

        LoginRequest loginRequest = new LoginRequest(username, password);

        Authentication authentication = CustomLoginToken.unAuthenticate(username, password);

        given(authenticationManager.authenticate(CustomLoginToken.unAuthenticate(username, password)))
                .willReturn(authentication);

        TokenPair tokenPair = new TokenPair("accessToken", "refreshToken");

        given(jwtService.createTokenPair(any()))
                .willReturn(tokenPair);

        mockMvc.perform(post("/api/v1/members/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(loginRequest)))
                .andExpect(status().isOk());
    }
}