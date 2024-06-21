package me.sanghyuk.subwayboard.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor
@Builder
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bno;

    @Column(name = "writer", nullable = false)
    private String writer;

    @Column(name = "title", length = 100, nullable = false)
    private String title;

    @Column(name = "content", length = 1500, nullable = false)
    private String content;

    @Column(name = "lineNo")
    private String lineNo;

    @Column(name = "trainNo")
    private String trainNo;

    @Column(name = "stdStation")
    private String stdStation;

    @Column(name = "trainTime")
    private String trainTime;//ArriveTime 기준

    @CreatedDate
    @Column(name = "regDate")
    private LocalDateTime regDate;

    @LastModifiedDate
    @Column(name = "modDate")
    private LocalDateTime modDate;


    public void update(String title, String content){
        this.title = title;
        this.content = content;
    }

    public void trainUpdate(String title, String content, String lineNo, String trainNo, String stdStation, String trainTime){
        this.title = title;
        this.content = content;
        this.lineNo = lineNo;
        this.trainNo = trainNo;
        this.stdStation = stdStation;
        this.trainTime = trainTime;
    }


}
