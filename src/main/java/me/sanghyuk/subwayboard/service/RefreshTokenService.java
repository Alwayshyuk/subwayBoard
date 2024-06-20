package me.sanghyuk.subwayboard.service;

import lombok.RequiredArgsConstructor;
import me.sanghyuk.subwayboard.entity.RefreshToken;
import me.sanghyuk.subwayboard.repository.RefreshTokenRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    public RefreshToken findByRefreshToken(String refreshToken) {
        return refreshTokenRepository.findByRefreshToken(refreshToken).orElseThrow(()->new IllegalArgumentException("Unexpected token"));
    }
}
