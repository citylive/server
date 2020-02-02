package com.citylive.server.controller;

import com.citylive.server.dao.AnswerRepository;
import com.citylive.server.domain.Answer;
import com.citylive.server.domain.Topic;
import com.citylive.server.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/topic")
public class TopicController {
    @Autowired
    private TopicService topicService;

    @Autowired
    private AnswerRepository answerRepository;

    @PostMapping
    public Topic addTopic(@RequestBody @Validated Topic topic) {
        return topicService.addTopic(topic);
    }


    @GetMapping
    public Topic getTopic(@RequestParam Integer topicId) {
        return topicService.getTopicById(topicId);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/answer")
    public void addAnswer(@RequestBody Answer answer) {
        topicService.addAnswer(answer);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/answer")
    public List<Answer> getAnswerByTopicId(@RequestParam @NotNull Integer topicId,
                                           @RequestParam(required = false) String userName) {
        return userName == null ? answerRepository.getAnswerByTopicId(topicId) :
                answerRepository.getAnswerByUserAndTopicId(userName, topicId);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/user")
    public List<Topic> getTopicByUser(@RequestParam final String userName){
        return topicService.getTopicByUser(userName);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/user/subscription")
    public List<Topic> getTopicSubscribedByUser(@RequestParam final String userName){
        return topicService.getTopicSubscribedByUser(userName);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/subscribe")
    public void subscribeTopic(@RequestParam final String userName, @RequestParam final Integer topicId){
        topicService.subscribeTopic(userName, topicId);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/unsubscribe")
    public void unsubscribeTopic(@RequestParam final String userName, @RequestParam final Integer topicId){
        topicService.unsubscribeTopic(userName, topicId);
    }
}
