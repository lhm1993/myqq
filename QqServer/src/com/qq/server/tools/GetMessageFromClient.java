/**
 * function:该线程用于管理每一个客户端和服务器的连接
 * 			服务器从与该客户端连接的Socket中不断地阻塞读取信息
 */
package com.qq.server.tools;


import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import com.qq.common.*;
import com.qq.server.model.OnlineUserManager;

public class GetMessageFromClient implements Runnable {
	
	Socket s;
	
	public GetMessageFromClient(Socket s){
		 this.s=s;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			//不停的接收从客户端传进来的信息
			try {
				ObjectInputStream ois=new ObjectInputStream(s.getInputStream());
				//每次传过来的都是一个Message包
				Message m=(Message)ois.readObject();
				//先将这个连接放在 socketsTable中
				//System.out.println("  m.getSender()="+m.getSender()+" m.getGetter()="+m.getGetter()+" user.getUserId()="+m.getUser().getUserId()+" m.getPasswd()="+m.getPasswd());
				(new ServerClientSocketManager()).addSocketToServerClientConnect(m.getUser().getUserId(), s);
				
				//System.out.println(m.getUser().getUserId()+" 对应的Socket="+s);
				
				//解析Message m ，根据不同的信息类型做出不同的操作
				UndoMessage um=new UndoMessage(m);
				
			} catch (Exception e) {
				
				// TODO: handle exception
				e.printStackTrace();
				//如果接收信息发生异常，说明连接断了，这时候，将s从服务器端的socketTable中移除，
				//将该用户在线状态置位离线
				
				//System.out.println("GetMessageFromClient "+s);
				ServerClientSocketManager scsm=new ServerClientSocketManager();
				HashMap<String,Socket> hm=scsm.getSocketsTble();
				String userId=null;
				for(Entry<String ,Socket> entry:hm.entrySet()){
					if(entry.getValue()==s){
						userId=entry.getKey();
						break;
					}
				}
				if(userId!=null){
					//清除s
					scsm.removeSocketFromServerClientConnect(userId);
					//清除在线状态
					(new OnlineUserManager()).removeUserFromOnLineUsersTable(userId);
				}
				break;
			}
		}
	}
}
