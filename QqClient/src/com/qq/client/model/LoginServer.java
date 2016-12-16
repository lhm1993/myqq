/**
 * function:向服务器端发送登录信息,接收到 MesType!=1 的数据包的时候，都视为登陆失败
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
	
	//登录是否成功
	public boolean isLogin(){
		boolean b=false;
		//建立 Socket s 连接
		ClientSeverConnectManager cscm=new ClientSeverConnectManager();
		if(cscm.startClientSeverConnect()){//是否成功建立连接
			this.isConnect=true;
			//先检测登录信息是否完整,如果登录信息不完整，就提示登录错误 
			if(m.getUser().equals("")==true||m.getPasswd().equals("")==true){
				return b;
			}
			//向服务器发送信息
			SendMessageToServer smts=new SendMessageToServer(m, cscm.getS());
			this.s=cscm.getS();
			smts.startSendMessage();
			//从服务器接收信息
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
