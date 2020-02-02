package com.citylive.server.dao;

import com.citylive.server.domain.Answer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerRepository extends CrudRepository<Answer, Integer> {
    @Query(value = "select a.* from CityLive.Answer a where userName=:userName and topicId=:topicId order by time", nativeQuery = true)
    List<Answer> getAnswerByUserAndTopicId(@Param("userName") String userName,@Param("topicId") Integer topicId);
    @Query(value = "select a.* from CityLive.Answer a where topicId=:topicId order by time", nativeQuery = true)
    List<Answer> getAnswerByTopicId(@Param("topicId") Integer topicId);
}
