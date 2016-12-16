/**
 * function:将打包好的 Message包发送给客户端
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
	
	//向客户端发送信息
	public void startSendMessage(){
		try {
			Socket s=(new ServerClientSocketManager()).getSocketFromServerClientConnect(this.m.getGetter());
			//System.out.println("m.getMesType()="+m.getMesType()+"  "+m.getSender()+"对"+m.getGetter()+"说"+m.getContent());
			//System.out.println("userId="+userId+" 对应的Socket="+s);
			ObjectOutputStream oos=new ObjectOutputStream(s.getOutputStream());
			oos.writeObject(this.m);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
