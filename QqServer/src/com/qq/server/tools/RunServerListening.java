/**
 * function:���߳����ڷ��������϶˿�9527
 * ʵ���˷������رպ��û���������
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
		System.out.println("�˿�9527���ڼ���...");
		while(true){
			//�����˿�
			try {
				//ÿ����һ���ͻ������ӵ���������ʱ��
				//�Ϳ���һ���߳���ά����������ÿͻ��˵� Socket s
				Socket s=ss.accept();
				GetMessageFromClient ssccm=new GetMessageFromClient(s);
				Thread t=new Thread(ssccm);
				t.start();
			} catch (Exception e) {
				// TODO: handle exception
				
				e.printStackTrace();
				//�����û�ȫ������onLineUser��գ����socketTable
				(new OnlineUserManager()).onLineUsersTable.clear();
				ServerClientSocketManager.socketsTble.clear();
				JOptionPane.showMessageDialog(null, "��������崻���");
				break;
			}
		}
	}

}