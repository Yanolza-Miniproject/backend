package com.miniproject.domain.member;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.miniproject.domain.member.entity.Member;
import com.miniproject.domain.member.exception.DuplicateEmailException;
import com.miniproject.domain.member.repository.MemberRepository;
import com.miniproject.domain.member.request.LoginRequest;
import com.miniproject.domain.member.request.SignUpRequest;
import com.miniproject.domain.member.service.MemberService;
import com.miniproject.domain.refresh.entity.RefreshToken;
import com.miniproject.domain.refresh.repository.RefreshTokenRepository;
import com.miniproject.global.config.CustomHttpHeaders;
import com.miniproject.global.jwt.JwtPayload;
import com.miniproject.global.jwt.service.JwtService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class MemberIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberService memberService;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private JwtService jwtService;
    private Member member;
    private HttpHeaders testAuthHeaders;

    private HttpHeaders createTestAuthHeader(String email) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(
                CustomHttpHeaders.ACCESS_TOKEN,
                jwtService.createTokenPair(new JwtPayload(email, new Date())).accessToken()
        );
        return headers;
    }

    @BeforeEach
    public void beforeEach() {
        member = memberRepository.save(Member.builder()
                .email("test@email.com")
                .nickname("tester")
                .password("test")
                .phoneNumber("010-1234-5678")
                .build());
        testAuthHeaders = createTestAuthHeader(member.getEmail());
    }

    @AfterEach
    public void afterEach(){
        memberRepository.delete(member);
    }

    @DisplayName("사용자는 회원가입을 할 수 있다.")
    @Test
    public void joinMember() throws Exception {

        //given
        ResultActions joinMemberAction = mockMvc.perform(post("/api/v1/members/join")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(new SignUpRequest(
                        "test3@email.com","1234", "tom", "010-5555-4444"
                ))));

        //then
        joinMemberAction.andExpect(status().is2xxSuccessful());
    }

    @DisplayName("회원가입을 할 때, 사용자의 Email 은 중복될 수 없다.")
    @Test
    public void userEmailShouldNotDuplicated() throws Exception {
        //given
        var newRequest = new SignUpRequest(member.getEmail(), member.getPassword(), member.getNickname(), member.getPhoneNumber());

        // When - Then
        mockMvc.perform(post("/api/v1/members/join")
                        .headers(testAuthHeaders)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(newRequest)))
                .andExpect(status().is4xxClientError())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof DuplicateEmailException));

    }

    @DisplayName("사용자가 로그아웃을 할 경우 Refresh 토큰은 소멸한다.")
    @Test
    public void userLogoutShouldInvalidateRefreshToken() throws Exception {
        //given
        ResultActions duplicatedMemberAction = mockMvc.perform(post("/api/v1/members/logout").headers(testAuthHeaders)
                .contentType(MediaType.APPLICATION_JSON));

        //when
        RefreshToken result = refreshTokenRepository.findByMemberEmail(member.getEmail());

        //then
        duplicatedMemberAction.andExpect(status().is2xxSuccessful());
        assertNull(result);
    }
}
