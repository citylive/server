package com.citylive.server.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(UserTopicPK.class)
@Table(name = "User_Topic")
public class UserTopic {
    @Id
    private Integer topicId;
    @Id
    private String userName;
}
