/**
 * function:��������ڽ������б�
 */
package com.qq.common;

import java.awt.Image;
import java.io.*;
import javax.swing.*;

public class PersonAttribute implements Serializable {
	
	private String userId;
	private String nickName;
	private String imagePath;//�ú��ѵ�ͷ���·��
	private boolean isOnline;
	
	public boolean isOnline() {
		return isOnline;
	}
	
	public void setOnline(boolean isOnline) {
		this.isOnline = isOnline;
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
