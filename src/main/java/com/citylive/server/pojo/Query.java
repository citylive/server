package com.citylive.server.pojo;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class Query {
	
	private MessageType type;
	
	private String question;
	
	private String topic;
	
	private String sender;
	
	private String senderDeviceId;
	
	private List<String> deviceIds;

}
