package com.citylive.server.dao;

import com.citylive.server.domain.Topic;
import com.citylive.server.domain.TopicPK;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface TopicRepository extends CrudRepository<Topic, TopicPK> {

    @Query(value = "Select topic.* From CityLive.Topic topic where UserName=:userName", nativeQuery = true)
    List<Topic> getTopicByUser(@Param("userName") String userName);

    @Query(value = "Select topic.* From CityLive.Topic topic where topicId in (:topicIds)", nativeQuery = true)
    List<Topic> getTopicByIds(@Param("topicIds") Set<Integer> topicIds);
}
