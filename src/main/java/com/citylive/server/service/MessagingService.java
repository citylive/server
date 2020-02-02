package com.citylive.server.service;

import java.io.IOException;
import java.util.List;

import javax.annotation.PostConstruct;

import com.citylive.server.pojo.MessageType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.citylive.server.constants.Constants;
import com.citylive.server.pojo.Query;
import com.citylive.server.pojo.Response;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.BatchResponse;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.MulticastMessage;
import com.google.firebase.messaging.Notification;

@RestController
@RequestMapping("/message")
@Slf4j
public class MessagingService {

	@PostConstruct
	private void initializeService() throws IOException {
		FirebaseOptions options = new FirebaseOptions.Builder()
				.setCredentials(GoogleCredentials.getApplicationDefault())
				.setDatabaseUrl("https://citylive-cb784.firebaseio.com/").build();

		FirebaseApp.initializeApp(options);
	}

	@RequestMapping(method = RequestMethod.POST, path = "/notifyTopic")
	public ResponseEntity sendNotificationToTopicPost(@RequestBody Response response)
			throws FirebaseMessagingException {
		return ResponseEntity.ok("Successfully sent message: " + sendNotificationToTopic(response));

	}

	public String sendNotificationToTopic(Response response) throws FirebaseMessagingException {
		Notification notification = Notification.builder().setTitle(Constants.RESPONSE_NOTIFICATION_TITLE)
				.setBody(response.getResponseString()).build();
		Message message = Message.builder().putData("type", response.getType().toString())
				.putData("topic", response.getTopic()).putData("msg",response.getResponseString())
				.putData("by", response.getSender())
				.putData("time", response.getMessageTimestamp().toString()).setNotification(notification)
				.setTopic(response.getTopic()).build();
		// Send a message to the devices subscribed to the provided topic.
		String response_fb = FirebaseMessaging.getInstance().send(message);
		return response_fb;
	}

	@RequestMapping(method = RequestMethod.POST, path = "/notifyDevices")
	public ResponseEntity sendNotificationToMultipleDevicesPost(@RequestBody Query query)
			throws FirebaseMessagingException {
		// Upper limit on number of devices ids = 100
		return ResponseEntity.ok(sendNotificationToMultipleDevices(query) + " messages were sent successfully");
	}

	public String sendQueryToDevices(Query query) throws FirebaseMessagingException {
		Integer count = sendNotificationToMultipleDevices(query);
		return sendNotificationToDevice(query, count);
	}

	public Integer sendNotificationToMultipleDevices(Query query) throws FirebaseMessagingException {
		log.info("[sendNotificationToDevice] query " + query.getQuestion() + " sent to " + query.getDeviceIds().size()
				+ " users on topic " + query.getTopic());
		if(query.getDeviceIds()!=null && !query.getDeviceIds().isEmpty()) {
			List<String> registrationTokens = query.getDeviceIds();
			Notification notification = Notification.builder().setTitle(Constants.QUERY_NOTIFICATION_TITLE)
					.setBody(query.getQuestion()).build();
			MulticastMessage message = MulticastMessage.builder().putData("type", query.getType().toString())
					.putData("topic", query.getTopic()).putData("question", query.getQuestion())
					.putData("by", query.getSender()).setNotification(notification)
					.addAllTokens(registrationTokens).build();
			BatchResponse response = FirebaseMessaging.getInstance().sendMulticast(message);
			return response.getSuccessCount();
		}
		return 0;
	}

	private String sendNotificationToDevice(Query query, Integer count) throws FirebaseMessagingException {
		log.info("[sendNotificationToDevice] query sender on " + query.getSenderDeviceId() + " topic "
				+ query.getTopic());
		if(query.getTopic()!=null && query.getSenderDeviceId()!=null){
			String registrationToken = query.getSenderDeviceId();
			Notification notification = Notification.builder().setTitle(Constants.SILENT_NOTIFICATION)
					.setBody(Constants.QUERY_SENT_TEXT.replace("%", count.toString())).build();
			Message message = Message.builder().putData("topic", query.getTopic()).putData("type", MessageType.COUNT.toString())
					.putData("message",Constants.QUERY_SENT_TEXT.replace("%", count.toString())).setNotification(notification)
					.setToken(registrationToken).build();
			String response = FirebaseMessaging.getInstance().send(message);
			return response;
		} else if(query.getSenderDeviceId()!=null) {
			String registrationToken = query.getSenderDeviceId();
			Notification notification = Notification.builder().setTitle(Constants.SILENT_NOTIFICATION)
					.setBody(Constants.ERROR_RESPONSE).build();
			Message message = Message.builder().putData("type", MessageType.COUNT.toString())
					.putData("message",Constants.ERROR_RESPONSE).setNotification(notification)
					.setToken(registrationToken).build();
			String response = FirebaseMessaging.getInstance().send(message);
			return response;
		} else{
			log.error("Sender device Id can not be null");
			return null;
		}
	}
}
