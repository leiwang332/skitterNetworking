package com.networking.skitter.domain;

import java.awt.image.BufferedImage;
import java.util.Date;

public class Skit {
	private int skitId;
	private int accountId;
	private Date postDate;
	private String text;
	private BufferedImage picture;
	
	public Skit(){
	}
	
	public int getSkitId() {
		return skitId;
	}
	public void setSkitId(int skitId) {
		this.skitId = skitId;
	}
	public int getAccountId() {
		return accountId;
	}
	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}
	public Date getPostDate() {
		return postDate;
	}
	public void setPostDate(Date postDate) {
		this.postDate = postDate;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public BufferedImage getPicture() {
		return picture;
	}
	public void setPicture(BufferedImage picture) {
		this.picture = picture;
	}
}
