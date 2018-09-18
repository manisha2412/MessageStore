package com.message.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.test.MessageStore.ArchiveMessageData;
import com.test.MessageStore.MessageData;

public interface ArchiveMessageRepository extends CrudRepository<ArchiveMessageData, Long> {
	List<ArchiveMessageData> findByusername(String username);


}
