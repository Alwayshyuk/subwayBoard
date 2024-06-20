package me.sanghyuk.subwayboard.service;

import lombok.RequiredArgsConstructor;
import me.sanghyuk.subwayboard.dto.AddUserRequestDTO;
import me.sanghyuk.subwayboard.entity.User;
import me.sanghyuk.subwayboard.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    public Long save(AddUserRequestDTO dto) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return userRepository.save(User.builder()
                        .email(dto.getEmail())
                        .password(encoder.encode(dto.getPassword()))
                        .build())
                .getId();
    }
    public User findById(Long userId){
        return userRepository.findById(userId).orElseThrow(()->new IllegalArgumentException("Unexpected user"));
    }
    public User findByEmail(String email){
        return userRepository.findByEmail(email).orElseThrow(()->new IllegalArgumentException("Unexpected user"));
    }
}
