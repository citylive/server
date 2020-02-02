package com.citylive.server.domain;

import com.citylive.server.validation.UserExistsValidator;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Entity
@Data
@Builder(toBuilder = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@IdClass(TopicPK.class)
@Table(name = "Topic")
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long topicId;
    @Id
    @NotNull
//    @UserExistsValidator
    private String userName;
    @NotNull
    private String question;
    private Boolean closed;
    private Double latitude;
    private Double longitude;
    private Timestamp time;
}
