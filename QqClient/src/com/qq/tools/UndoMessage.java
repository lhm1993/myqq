/**
 * function:解压从服务器端返回的Message m 包
 * 提供返回各种信息类型信息的方法
 */
package com.qq.tools;

import java.net.*;
import java.io.*;

import javax.swing.JOptionPane;

import com.qq.client.model.*;
import com.qq.client.view.QqList;
import com.qq.common.*;

public class UndoMessage {
	private Socket s;
	private Message m;

	public Message getM() {
		return m;
	}

	public void setM(Message m) {
		this.m = m;
	}
	
	public UndoMessage(Message m){
		this.m=m;
		switch(m.getMesType()){
		//3聊天信息,将存服务器获得的信息写在聊天窗口上
		case 3:
			WriteChatMessageToChatingWindow wmtcw=new WriteChatMessageToChatingWindow(m);
			wmtcw.startWrite();
			//System.out.println(m.getSender()+"对"+m.getGetter()+"说"+m.getContent());
			break;
		//服务器异常，客户端下线
		case 5:
			JOptionPane.showMessageDialog(null, "服务器异常，您已被迫下线！");
			ClientManager.getOnLineUserList().dispose();
			break;
		}
		
	}
	
}
