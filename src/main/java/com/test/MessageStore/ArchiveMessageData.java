package com.test.MessageStore;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "archivestore")
public class ArchiveMessageData {

	@Id
	private Long id;
	
//	 private Long userId;
//		
//		public Long getUserId() {
//			return userId;
//		}
//		public void setUserId(Long userId) {
//			this.userId = userId;
//		}
	private String username;
	
	private Date expiration_date;
	

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	private String text;
	private int timeout;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}
	
	public Date getExpiration_date() {
		return expiration_date;
	}
	public void setExpiration_date(Date expiration_date) {
		this.expiration_date = expiration_date;
	}

}
