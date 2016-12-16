/**
 * function:��ѹ�ɿͻ��˷��͹����� Message ��
 * �ṩ���ظ�����Ϣ������Ϣ�ķ���
 */
package com.qq.server.tools;

import java.io.*;
import java.net.Socket;
import java.util.Iterator;

import com.qq.common.Message;
import com.qq.server.model.OnlineUserManager;
import com.qq.server.model.TransferMessageFromClientToClient;

public class UndoMessage {
	Message m;
	
	public UndoMessage(Message m){
		this.m=m;
		switch(m.getMesType()){
		//��¼��Ϣ������ο�Message�ࣩ
		//�ж��Ƿ���Ե�¼����
		//�����¼ʧ�ܣ��ͷ���һ�� MesType=2 �ĵ�¼ʧ�ܰ���
		//��¼�ɹ�������һ�� MesType=1 ����Ϣ���Լ��û��ĺ����б�����Ϣ
		case 1:
			//1 ���ݿ���֤��½��Ϣ
			LoginCheck lc=new LoginCheck(m);
			//System.out.println("  m.getSender()="+m.getSender()+" m.getGetter()="+m.getGetter()+" user.getUserId()="+m.getUser().getUserId()+" m.getPasswd()="+m.getPasswd());
			if(lc.isLoginInfoRight()){
				//1��¼�ɹ��������ݿ��ȡ���ڸ��û�����Ϣ,�����Ķ����� m
				//�ж��Ƿ����ظ���¼
				OnlineUserManager oum=new OnlineUserManager();
				String isUserOnLine=oum.getOnLineUsersTable().get(m.getUser().getUserId());
					//boolean isUserOnline=oum.getOnLineUsersTable().get(m.getUser().getUserId());
				//System.out.println(oum.getOnLineUsersTable().get(m.getUser().getUserId()));
				if(isUserOnLine!=null&&isUserOnLine.equals("yes")==true){
					m.setMesType(4);//�ظ���¼
				}else{
					GetInfoFromDataBase gifd=new GetInfoFromDataBase(m);
					m=gifd.getM();	
					m.setMesType(1);
					oum.addUserToOnLineUsersTable(m.getUser().getUserId(), "yes");
				}
			}else {
				m.setMesType(2);
				//��¼ʧ�ܣ���ÿͻ��˷���һ��MesType=2 ��Message��
			}
			
			//2 ��ͻ��˷��� Message m 
			//�������ͷ��ͽ��շ�
			String sender=m.getGetter();
			m.setGetter(m.getSender());
			m.setSender(sender);
			SendMessageToClient smtc=new SendMessageToClient(m);
			smtc.startSendMessage();
			break;
			
		//������Ϣ
		case 3:
			TransferMessageFromClientToClient tmfctc=new TransferMessageFromClientToClient(m);
			tmfctc.startTransferMessage();
			//System.out.println(m.getSender()+" "+m.getGetter()+" **"+m.getContent()+"**");
			break;
		}
	}
	
}