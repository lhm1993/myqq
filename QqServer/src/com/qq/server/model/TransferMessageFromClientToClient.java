/**
 * function:转发客户端发向客户端的信息包
 */
package com.qq.server.model;

import java.net.Socket;

import com.qq.common.*;
import com.qq.server.tools.*;

public class TransferMessageFromClientToClient {
	
	Message m;
	
	public TransferMessageFromClientToClient(Message m){
		this.m=m;
	}
	public void startTransferMessage(){
		//判断用户是否在线
		//m.getter在线，就将信息发送给对方，不在线就将信息存储在数据库中
		Socket s=(new ServerClientSocketManager()).getSocketFromServerClientConnect(this.m.getGetter());
		//用户在线
		if(s!=null){
			SendMessageToClient smtc=new SendMessageToClient(m);
			smtc.startSendMessage();
		//用户不在线
		}else if(s==null){
			//将信息写入m.getter 的数据库中
			new SavaMessageForClientIsNotOnLine(this.m);
			
		}
		
		
	}
}
