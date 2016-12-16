/**
 * function:用来描述每个在客户端登录的用户的信息
 */
package com.qq.common;

import java.awt.Image;
import java.util.HashMap;
import java.util.Vector;
import javax.swing.*;
import java.util.*;
import java.io.*;
import java.net.*;

public class User implements Serializable{
	
	private String userId;//用户信息
	private String nickName;
	private String imagePath;
	//存储形式<nickName,pa>
	private HashMap frientdsList=new HashMap<String ,PersonAttribute>();
	private HashMap strangersList=new HashMap<String ,PersonAttribute>();
	private HashMap blackList=new HashMap<String , PersonAttribute>();
	
	public HashMap getFrientdsList() {
		return frientdsList;
	}

	public void setFrientdsList(HashMap frientdsList) {
		this.frientdsList = frientdsList;
	}

	public HashMap getStrangersList() {
		return strangersList;
	}

	public void setStrangersList(HashMap strangersList) {
		this.strangersList = strangersList;
	}

	public HashMap getBlackList() {
		return blackList;
	}

	public void setBlackList(HashMap blackList) {
		this.blackList = blackList;
	}
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	
	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	
}
