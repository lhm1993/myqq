/**
 * function:用于服务器和客户端连接,将每一个客户端和服务的连接都保存下来
 * 将服务器的监听 ss传进来，由着类来代替监听端口
 */
package com.qq.server.tools;
import java.io.*;
import java.net.*;
import java.util.*;

public class ServerClientSocketManager {
	
	//存的是< userId,socket> 
	public static HashMap socketsTble=new HashMap<String,Socket>();
	
	public static HashMap getSocketsTble() {
		return socketsTble;
	}

	//向socketsTable添加一个s
	public void addSocketToServerClientConnect(String userId,Socket s){
		this.socketsTble.put(userId, s);
	}
	
	//取出一个s
	public Socket getSocketFromServerClientConnect(String userId){
		
		return (Socket)this.socketsTble.get(userId);
	}
	
	//移除一个连接
	public void removeSocketFromServerClientConnect(String userId){
		if(this.socketsTble!=null&&this.socketsTble.get(userId)!=null){
			this.socketsTble.remove(userId);
		}
	}
}
