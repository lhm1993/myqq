package com.qq.tools;

import java.net.Socket;
import java.io.*;

import com.qq.common.*;

public class GetMessageFromServer implements Runnable{

	private Socket s;
	private Message m;
	
	//���캯��
	public GetMessageFromServer(Socket s){
		this.s=s;
	}
	
	//�ӷ�����������Ϣ
	@Override
	public void run() {
		// TODO Auto-generated method stub
		ObjectInputStream ois=null;
		while(true){
			try {
				ois=new ObjectInputStream(s.getInputStream());
				//�ӷ�����������ȡ��Ϣ
				m=(Message)ois.readObject();
				//������Ϣ
				//System.out.println("m.getMesType()="+m.getMesType()+"  "+m.getSender()+"��"+m.getGetter()+"˵"+m.getContent());
				UndoMessage um=new UndoMessage(m);
				//System.out.println("m.getMesType()="+m.getMesType()+" ���Ѹ���="+m.getUser().getFrientdsList().size());
				//������˳���¼����ֹͣ���������Ϣ���߳�
//				if(m.getMesType()==0){
//					break;
//				}
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				//������쳣�����˳�����
				Message m_e=new Message();
				m_e.setMesType(5);
				UndoMessage um=new UndoMessage(m_e);
				break;
			}
		}
	}
}