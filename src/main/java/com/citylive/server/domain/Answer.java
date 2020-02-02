package com.citylive.server.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Answer")
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer answerId;
    @NotNull
    private String answer;
    @NotNull
    private Integer topicId;
    @NotNull
    private String userName;
    private Timestamp time;
}
