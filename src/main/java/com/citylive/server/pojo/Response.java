package com.citylive.server.pojo;

import java.sql.Timestamp;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Response {
	
	private String topic;
	
	private MessageType type;
	
	private String sender;
	
	private Timestamp messageTimestamp;
	
	private String responseString;

}
