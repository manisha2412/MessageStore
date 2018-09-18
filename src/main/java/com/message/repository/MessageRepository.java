package com.message.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.test.MessageStore.MessageData;


public interface MessageRepository extends CrudRepository<MessageData, Long> {
	
	List<MessageData> findByusername(String username);


}