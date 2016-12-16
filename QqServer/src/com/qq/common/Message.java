/**
 * function:���������ڿͻ��˺ͷ������˻��ഫ�ݵ����ݱ�
 * 
 * MesType 0 ��ʾ�˳���¼���ɿͻ��˷�������������������յ���Ϣ�Ժ�
 * 				�رյ�Socket���ӣ���s��socketTable���Ƴ���ֹͣ���������ոö˿ڵ��̣߳� ��onLineUsersTable�н������û��Ƴ�
 *		   1 ��ʾ���͵�¼��Ϣ������������ �û����͵�¼����
 * 		   2  ��ʾ��¼ʧ�ܣ��������˷����ͻ��� �ɿͻ��˽���
 * 		   3 ��ʾ���͵���������Ϣ���������������������Լ����͸���Щ����
 *         
 */
package com.qq.common;

import java.io.*;
import java.util.HashMap;
import java.util.Vector;

public class Message implements Serializable{
	private int MesType;//��Ϣ��������
	private String sender;//��Ϣ�ķ�����
	private String getter;//��Ϣ�Ľ�����
	private String content;//��Ϣ����
	private String timeStamp;//����ʱ��
	private User user; //��ǰ�û�
	private String passwd;//��ǰ�û���ʹ�õĵ�¼����
	private HashMap<String ,Vector<String>> offLineMessage=new HashMap<String ,Vector<String>>();
	
	public HashMap<String, Vector<String>> getOffLineMessage() {
		return offLineMessage;
	}

	public void setOffLineMessage(HashMap<String, Vector<String>> offLineMessage) {
		this.offLineMessage = offLineMessage;
	}
	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getMesType() {
		return MesType;
	}

	public void setMesType(int mesType) {
		MesType = mesType;
	}
	
	public String getSender() {
		return sender;
	}
	
	public void setSender(String sender) {
		this.sender = sender;
	}
	
	public String getGetter() {
		return getter;
	}
	
	public void setGetter(String getter) {
		this.getter = getter;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getTimeStamp() {
		return timeStamp;
	}
	
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

}