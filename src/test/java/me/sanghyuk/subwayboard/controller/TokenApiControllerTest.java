package me.sanghyuk.subwayboard.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.sanghyuk.subwayboard.config.jwt.JwtProperties;
import me.sanghyuk.subwayboard.dto.CreateAccessTokenRequestDTO;
import me.sanghyuk.subwayboard.entity.RefreshToken;
import me.sanghyuk.subwayboard.entity.User;
import me.sanghyuk.subwayboard.config.jwt.JwtFactory;
import me.sanghyuk.subwayboard.repository.RefreshTokenRepository;
import me.sanghyuk.subwayboard.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TokenApiControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private WebApplicationContext context;
    @Autowired
    JwtProperties jwtProperties;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RefreshTokenRepository refreshTokenRepository;

    @BeforeEach
    public void mockmvcSetup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        userRepository.deleteAll();
    }
    @DisplayName("createNewAccessToken: 새로운 엑세스 토큰 발급")
    @Test
    public void createNewAccessToken() throws Exception{
        final String url = "/api/token";

        User testUser = userRepository.save(User.builder().email("test@test.com").password("test").build());

        String refreshToken = JwtFactory.builder().claims(Map.of("id",testUser.getId())).build().createToken(jwtProperties);

        refreshTokenRepository.save(new RefreshToken(testUser.getId(),refreshToken));

        CreateAccessTokenRequestDTO request = new CreateAccessTokenRequestDTO();
        request.setRefreshToken(refreshToken);
        final String requestBody = objectMapper.writeValueAsString(request);

        ResultActions resultActions = mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON_VALUE).content(requestBody));
        resultActions.andExpect(status().isCreated()).andExpect(jsonPath("$.accessToken").isNotEmpty());

    }
}
