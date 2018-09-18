package com.message.controller;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.message.repository.ArchiveMessageRepository;
import com.message.repository.MessageRepository;
import com.test.MessageStore.ArchiveMessageData;
import com.test.MessageStore.MessageData;

@RestController
public class MessageController {

	
	@Autowired
    MessageRepository messageRepository;
	
	long id ;
	
	@Autowired
	ArchiveMessageRepository archiveMessageRepository;
	
	@RequestMapping(value = "/chat", method = RequestMethod.POST)
    public ResponseEntity<?> addmessage(@RequestBody MessageData message) {

		Calendar cal = Calendar.getInstance();
       Date now = cal.getTime();

        cal.add(Calendar.SECOND, message.getTimeout());
        Date later = cal.getTime();

        message.setExpiration_date(later);
        message.setMessageStatus("unexpired");
        
        String username =message.getUsername();
        messageRepository.save(message);	
		
		JSONObject output = new JSONObject();
		try {
			output.put("id", message.getId());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
        return new ResponseEntity<>(output.toString(), HttpStatus.CREATED);
    }
	
	@RequestMapping(value = "/chat/{id}") 
    public ResponseEntity<?>  read(@PathVariable long id) throws JSONException {
		Optional<MessageData> message = messageRepository.findById(id);
		JSONObject output = new JSONObject();
		//System.out.println(message.empty().isPresent()+ "***************");
		
		if (!message.empty().isPresent())
		{
			Optional<ArchiveMessageData> message2 = archiveMessageRepository.findById(id);
			
			if(message2.empty().isPresent())
			{
				output.put("message", "no message found");
				
			} else
				{
						output.put("username", message2.get().getUsername());
						output.put("text", message2.get().getText());
						output.put("expiration_date", message2.get().getExpiration_date());
				
				}
				
			}
		else
		{
		        output.put("username", message.get().getUsername());
				output.put("text", message.get().getText());
				output.put("expiration_date",message.get().getExpiration_date());
			
		}
		return new ResponseEntity<>(output.toString(), HttpStatus.OK);
    }

	
	@RequestMapping(value = "/chats/{username}") 
    public String  get(@PathVariable String username) {
		
		List<MessageData> namelist = messageRepository.findByusername(username);
		JSONArray output = new JSONArray();
		int i=0;
		for (MessageData msg : namelist)
		{
			
			JSONObject obj = new JSONObject();
			try {
				obj.put("id", msg.getId());
				obj.put("text", msg.getText());
				output.put(i, obj);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			i++;
		}
		
		moveToArchive(namelist);
		return output.toString();
    }

	private void moveToArchive(List<MessageData> msgList) {
		int i=0;
		
		for (i=0;i<msgList.size();i++)
		{
			ArchiveMessageData s = new ArchiveMessageData();
			s.setId(msgList.get(i).getId());
			s.setText(msgList.get(i).getText());
			s.setTimeout(msgList.get(i).getTimeout());
			s.setUsername(msgList.get(i).getUsername());
			s.setExpiration_date(msgList.get(i).getExpiration_date());
			archiveMessageRepository.save(s);
			
		}
		messageRepository.deleteAll(msgList);
		
		
	}

	
	@RequestMapping(value = "/test") 
    public void test() {
	System.out.println("inside");
	doinit();
	}
	
	public void doinit()
	{
		List<MessageData> alldata=(List<MessageData>) messageRepository.findAll();
		System.out.println(alldata.size());
		Calendar cal = Calendar.getInstance();
         Date now = cal.getTime();
         List<MessageData> mList = new ArrayList<MessageData>();

		for(MessageData my:alldata)
		{
			Date msgExpDate = my.getExpiration_date();
			long currentDate=(now.getTime()/1000);
			long msgTime=(msgExpDate.getTime()/1000);
			if(msgTime<currentDate)
			{
				mList.add(my);
				moveToArchive(mList);
			}
				
			
		}
	}
	
}