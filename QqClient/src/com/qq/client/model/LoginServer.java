/**
 * function:��������˷��͵�¼��Ϣ,���յ� MesType!=1 �����ݰ���ʱ�򣬶���Ϊ��½ʧ��
 */
package com.qq.client.model;
import com.qq.common.*;
import com.qq.tools.*;
import java.net.*;
import java.io.*;

import javax.swing.JOptionPane;

public class LoginServer{
	
	private Message m;
	private Socket s;
	private boolean isConnect;
	private boolean isAlreadyOnline;
	
	public boolean isConnect() {
		return isConnect;
	}

	public Message getM() {
		return m;
	}

	public Socket getS() {
		return s;
	}

	public LoginServer(Message m){
		this.m=m;
	}
	
	//��¼�Ƿ�ɹ�
	public boolean isLogin(){
		boolean b=false;
		//���� Socket s ����
		ClientSeverConnectManager cscm=new ClientSeverConnectManager();
		if(cscm.startClientSeverConnect()){//�Ƿ�ɹ���������
			this.isConnect=true;
			//�ȼ���¼��Ϣ�Ƿ�����,�����¼��Ϣ������������ʾ��¼���� 
			if(m.getUser().equals("")==true||m.getPasswd().equals("")==true){
				return b;
			}
			//�������������Ϣ
			SendMessageToServer smts=new SendMessageToServer(m, cscm.getS());
			this.s=cscm.getS();
			smts.startSendMessage();
			//�ӷ�����������Ϣ
			try {
				ObjectInputStream ois=new ObjectInputStream(cscm.getS().getInputStream());
				Message m=(Message)ois.readObject();
				this.m=m;
				//System.out.println("LoginServer m.MesType="+m.getMesType());
				if(m.getMesType()==1){
					b=true;
				}else if(m.getMesType()==4){
					isAlreadyOnline=true;
					b=false;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				b=false;
			}
		}else{
			
			this.isConnect=false;
			b=false;
		}
		
		//System.out.println("m.getMesType()+"+m.getMesType()+" m.getGetter()="+m.getGetter()+" m.getPasswd()="+m.getPasswd());
		
		return b;
	}

	public boolean isAlreadyOnline() {
		return isAlreadyOnline;
	}

	public void setAlreadyOnline(boolean isAlreadyOnline) {
		this.isAlreadyOnline = isAlreadyOnline;
	}

}
