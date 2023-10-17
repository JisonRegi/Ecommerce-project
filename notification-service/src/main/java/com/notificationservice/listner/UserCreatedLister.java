package com.notificationservice.listner;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.notificationservice.dto.UserEventDTO;
import com.notificationservice.util.Constants;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class UserCreatedLister {
	
	@KafkaListener(topics = Constants.USER_CREATED_EVENT, groupId = Constants.USERS_GROUP)
	public void listenUserCreatedEvent(UserEventDTO userEventDTO) {
		log.info("User created with email: "+ userEventDTO.getEmail());
	}

}
