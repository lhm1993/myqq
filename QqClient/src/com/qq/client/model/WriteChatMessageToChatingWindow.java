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
	
	//��MyMessage�ж�ȡû�б���ʾ�����촰���е���Ϣ
	public String getOffLineMessageFromMyMessage(String chatId){
		String s="";
		ChatWindowManager cwm=new ChatWindowManager();
		Vector<String> v=cwm.getMyMessage(chatId);
		for(int i=0;v!=null&&i<v.size();i++){
			s+=(v.get(i)+"\r\n");
			//����Ϣ�������Ժ󣬾͸���Ϣ��¼���Ϸ�����
			//׼���ڹر����촰�ڵ�ʱ����Ϊ������Ϣ���������¼��
			cwm.getMyMessage(chatId).set(i, chatId+" "+v.get(i));
		}
		return s;
	}
	public void startWrite(){
		ChatWindowManager cwm=new ChatWindowManager();
		QqChat qqChat=cwm.getQqChatWindow(m.getSender());
		//��ChatWindowManager ���ҵ��ͷ��͸���Ϣ���û� m.sender �����촰��
		if(qqChat!=null){
			//��������Ϣд�ڴ�����
			qqChat.writeGetToChatPane(m);
		
			cwm.addToMyMessage(m.getSender(),m.getSender()+" "+m.getTimeStamp()+"\r\n"+m.getContent());
			//������Ϣд�뵽�����¼����
		//������û���û�д�����û������촰�ڣ��ͽ���Ϣ�ȱ�����һ��������Ϣ�ĵط�
		//����������촰�ڵ�ʱ�򣬾ͽ�����Ϣ��ʾ�ڸ����촰���ϣ�������򿪣��ͱ����ڱ����ļ���
		}else{
			//�û�����û�д����촰��
			//�ͽ���Ϣд�뵽�����촰�ڵ���Ϣ����ȥ��ȥ
			cwm.addToMyMessage(m.getSender(),m.getTimeStamp()+"\r\n"+m.getContent());
		}
	}
}
