package me.sanghyuk.subwayboard.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddUserRequestDTO {
    private String email;
    private String password;
}
