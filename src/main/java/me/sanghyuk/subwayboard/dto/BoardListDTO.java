package me.sanghyuk.subwayboard.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class BoardListDTO {
    private final Long bno;
    private final String title;
    private final String content;
    private final String writer;
    private final LocalDateTime regDate;

}
