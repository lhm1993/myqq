/**
 * function:���߳����ڹ���ÿһ���ͻ��˺ͷ�����������
 * 			����������ÿͻ������ӵ�Socket�в��ϵ�������ȡ��Ϣ
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
			//��ͣ�Ľ��մӿͻ��˴���������Ϣ
			try {
				ObjectInputStream ois=new ObjectInputStream(s.getInputStream());
				//ÿ�δ������Ķ���һ��Message��
				Message m=(Message)ois.readObject();
				//�Ƚ�������ӷ��� socketsTable��
				//System.out.println("  m.getSender()="+m.getSender()+" m.getGetter()="+m.getGetter()+" user.getUserId()="+m.getUser().getUserId()+" m.getPasswd()="+m.getPasswd());
				(new ServerClientSocketManager()).addSocketToServerClientConnect(m.getUser().getUserId(), s);
				
				//System.out.println(m.getUser().getUserId()+" ��Ӧ��Socket="+s);
				
				//����Message m �����ݲ�ͬ����Ϣ����������ͬ�Ĳ���
				UndoMessage um=new UndoMessage(m);
				
			} catch (Exception e) {
				
				// TODO: handle exception
				e.printStackTrace();
				//���������Ϣ�����쳣��˵�����Ӷ��ˣ���ʱ�򣬽�s�ӷ������˵�socketTable���Ƴ���
				//�����û�����״̬��λ����
				
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
					//���s
					scsm.removeSocketFromServerClientConnect(userId);
					//�������״̬
					(new OnlineUserManager()).removeUserFromOnLineUsersTable(userId);
				}
				break;
			}
		}
	}
}