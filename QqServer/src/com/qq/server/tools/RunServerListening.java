/**
 * function:该线程用于服务器不断端口9527
 * 实现了服务器关闭后，用户被迫下线
 */
package com.qq.server.tools;

import java.net.*;
import java.io.*;

import javax.swing.JOptionPane;

import com.qq.common.*;
import com.qq.server.model.OnlineUserManager;

public class RunServerListening implements Runnable {
	
	private ServerSocket ss;
	
	public RunServerListening(ServerSocket ss){
		this.ss=ss;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("端口9527正在监听...");
		while(true){
			//监听端口
			try {
				//每当有一个客户端链接到服务器的时候，
				//就开启一个线程来维护服务器与该客户端的 Socket s
				Socket s=ss.accept();
				GetMessageFromClient ssccm=new GetMessageFromClient(s);
				Thread t=new Thread(ssccm);
				t.start();
			} catch (Exception e) {
				// TODO: handle exception
				
				e.printStackTrace();
				//在线用户全部下线onLineUser清空，清空socketTable
				(new OnlineUserManager()).onLineUsersTable.clear();
				ServerClientSocketManager.socketsTble.clear();
				JOptionPane.showMessageDialog(null, "服务器已宕机！");
				break;
			}
		}
	}

}
