package me.sanghyuk.subwayboard.dto;

import lombok.*;
import me.sanghyuk.subwayboard.entity.Board;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class BoardDTO {

    private Long bno;
    private String writer;
    private String title;
    private String content;
    private String lineNo;
    private String trainNo;
    private String stdStation;
    private String trainTime; //ArriveTime 기준
    private LocalDateTime regDate,modDate;


    public Board toEntity(String writer){
        return Board.builder()
                .writer(writer)
                .title(title)
                .content(content)
                .build();
    }
    public Board toTrainEntity(String writer){
        return Board.builder()
                .writer(writer)
                .title(title)
                .content(content)
                .lineNo(lineNo)
                .trainNo(trainNo)
                .stdStation(stdStation)
                .trainTime(trainTime)
                .build();
    }

}
