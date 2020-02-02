package com.citylive.server.domain;

import lombok.*;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Builder
@Embeddable
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserTopicPK implements Serializable {
    private Integer topicId;
    private String userName;
}
