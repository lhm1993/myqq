package com.qq.client.model;

import java.util.Vector;

import com.qq.client.view.QqChat;
import com.qq.common.*;

public class WriteChatMessageToChatingWindow {
	
	Message m;
	
	public WriteChatMessageToChatingWindow(){
		
	}
	
	public WriteChatMessageToChatingWindow(Message m){
		this.m=m;
	}
	
	//从MyMessage中读取没有被显示在聊天窗口中的信息
	public String getOffLineMessageFromMyMessage(String chatId){
		String s="";
		ChatWindowManager cwm=new ChatWindowManager();
		Vector<String> v=cwm.getMyMessage(chatId);
		for(int i=0;v!=null&&i<v.size();i++){
			s+=(v.get(i)+"\r\n");
			//当信息被接收以后，就给消息记录加上发送者
			//准备在关闭聊天窗口的时候作为保存信息放入聊天记录中
			cwm.getMyMessage(chatId).set(i, chatId+" "+v.get(i));
		}
		return s;
	}
	public void startWrite(){
		ChatWindowManager cwm=new ChatWindowManager();
		QqChat qqChat=cwm.getQqChatWindow(m.getSender());
		//在ChatWindowManager 中找到和发送该信息的用户 m.sender 的聊天窗口
		if(qqChat!=null){
			//将聊天信息写在窗口上
			qqChat.writeGetToChatPane(m);
		
			cwm.addToMyMessage(m.getSender(),m.getSender()+" "+m.getTimeStamp()+"\r\n"+m.getContent());
			//将该信息写入到聊天记录里面
		//如果该用户并没有打开与该用户的聊天窗口，就将信息先保存在一个保存信息的地方
		//当打开这个聊天窗口的时候，就将该信息显示在该聊天窗口上，如果不打开，就保存在本地文件中
		}else{
			//用户在线没有打开聊天窗口
			//就将信息写入到该聊天窗口的信息管理处去中去
			cwm.addToMyMessage(m.getSender(),m.getTimeStamp()+"\r\n"+m.getContent());
		}
	}
}
