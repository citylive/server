package com.citylive.server.domain;

import lombok.*;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TopicPK implements Serializable {
    private Long topicId;
    private String userName;
}