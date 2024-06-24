package me.sanghyuk.subwayboard.controller;

import lombok.RequiredArgsConstructor;
import me.sanghyuk.subwayboard.dto.CreateAccessTokenRequestDTO;
import me.sanghyuk.subwayboard.dto.CreateAccessTokenResponseDTO;
import me.sanghyuk.subwayboard.service.RefreshTokenService;
import me.sanghyuk.subwayboard.service.TokenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class TokenApiController {
    private final TokenService tokenService;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/api/token")
    public ResponseEntity<CreateAccessTokenResponseDTO> createNewAccessToken(@RequestBody CreateAccessTokenRequestDTO request){
        String newAccessToken = tokenService.createNewAccessToken(request.getRefreshToken());
        return ResponseEntity.status(HttpStatus.CREATED).body(new CreateAccessTokenResponseDTO(newAccessToken));
    }

    @DeleteMapping("/api/refresh-token")
    public ResponseEntity deleteRefreshToken(){
        refreshTokenService.delete();
        return ResponseEntity.ok().build();
    }
}
