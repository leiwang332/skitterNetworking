package com.networking.skitter.domain;

import java.util.Date;
import javax.validation.constraints.*;
import org.hibernate.validator.constraints.NotEmpty;

public class User {
	private int accountId;
	@Size(min=3,max=30)
	private String accountName;
	private Gender gender;
	private Date birthday;
	@Size(min=6,max=15)
	private String password;
	@NotEmpty
	private String email;
	
	public User(){
	}
	
	public void setAccountId(int id) {
		this.accountId = id;
	}
	
	public int getAccountId(){
		return accountId;
	}
	
	public void setAccountName(String name) {
		this.accountName = name;
	}
	
	public String getAccountName(){
		return accountName;
	}
	
	public void setGender(Gender gender) {
		this.gender = gender;
	}
	
	public Gender getGender() {
		return gender;
	}

	public void setBirthday(Date birthday){
		this.birthday = birthday;
	}
	
	public Date getBirthday(){
		return birthday;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setEmail(String email){
		this.email = email;
	}
	
	public String getEmail(){
		return email;
	}
}
