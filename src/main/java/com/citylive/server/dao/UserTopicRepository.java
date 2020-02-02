package com.citylive.server.dao;

import com.citylive.server.domain.UserTopic;
import com.citylive.server.domain.UserTopicPK;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserTopicRepository extends CrudRepository<UserTopic, UserTopicPK> {

    @Query(value = "Select userName, topicId From CityLive.User_Topic where UserName=:userName", nativeQuery = true)
    List<UserTopic> getTopicSubscribeByUser(@Param("userName") String userName);
}
