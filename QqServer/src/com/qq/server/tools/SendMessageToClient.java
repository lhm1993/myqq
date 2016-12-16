/**
 * function:������õ� Message�����͸��ͻ���
 */
package com.qq.server.tools;

import java.net.*;
import java.io.*;
import com.qq.common.Message;

public class SendMessageToClient {
	private Message m;
	
	public SendMessageToClient(Message m){
		this.m=m;
	}
	
	//��ͻ��˷�����Ϣ
	public void startSendMessage(){
		try {
			Socket s=(new ServerClientSocketManager()).getSocketFromServerClientConnect(this.m.getGetter());
			//System.out.println("m.getMesType()="+m.getMesType()+"  "+m.getSender()+"��"+m.getGetter()+"˵"+m.getContent());
			//System.out.println("userId="+userId+" ��Ӧ��Socket="+s);
			ObjectOutputStream oos=new ObjectOutputStream(s.getOutputStream());
			oos.writeObject(this.m);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
