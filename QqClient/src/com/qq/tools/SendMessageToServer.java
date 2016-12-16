/**
 * function:��������˷��� Message m
 */
package com.qq.tools;

import java.io.ObjectOutputStream;
import java.net.Socket;

import com.qq.common.*;

public class SendMessageToServer {
	private Message m;
	private Socket s;
	
	public SendMessageToServer(Message m,Socket s){
		this.m=m;
		this.s=s;
	}
	
	//��������˷�����Ϣ
	public void startSendMessage(){
		try {
			ObjectOutputStream oos=new ObjectOutputStream(s.getOutputStream());
			//System.out.println("userId="+this.m.getUser().getUserId());
			oos.writeObject(this.m);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
