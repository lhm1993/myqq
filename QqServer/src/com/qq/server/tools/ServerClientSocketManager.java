/**
 * function:���ڷ������Ϳͻ�������,��ÿһ���ͻ��˺ͷ�������Ӷ���������
 * ���������ļ��� ss������������������������˿�
 */
package com.qq.server.tools;
import java.io.*;
import java.net.*;
import java.util.*;

public class ServerClientSocketManager {
	
	//�����< userId,socket> 
	public static HashMap socketsTble=new HashMap<String,Socket>();
	
	public static HashMap getSocketsTble() {
		return socketsTble;
	}

	//��socketsTable���һ��s
	public void addSocketToServerClientConnect(String userId,Socket s){
		this.socketsTble.put(userId, s);
	}
	
	//ȡ��һ��s
	public Socket getSocketFromServerClientConnect(String userId){
		
		return (Socket)this.socketsTble.get(userId);
	}
	
	//�Ƴ�һ������
	public void removeSocketFromServerClientConnect(String userId){
		if(this.socketsTble!=null&&this.socketsTble.get(userId)!=null){
			this.socketsTble.remove(userId);
		}
	}
}
