package com.easternspace.chatbot.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.easternspace.chatbot.model.Message;


@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {
	
	
	List<Message> findMessageByToUserId(String empId);
	
	List<Message> findMessageByFromUserId(String empId);
	
	@Query(value="select * from cron.vaya_tracker where date >= ?1", nativeQuery = true)
	List<Message> findMessageByDate(Date date);

}
