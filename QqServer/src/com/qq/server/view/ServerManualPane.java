/**
 * function:�������������棬ʵ�ַ����������͹ر�
 * ��������������ť�����µ�ʱ��ͻῪʼ�����˿ڣ�
 */
package com.qq.server.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.*;

import javax.swing.*;

import com.qq.server.tools.*;

public class ServerManualPane extends JFrame implements ActionListener{
	
	JPanel jp_mianPanel;
	JScrollPane jsp;
	JTable jt_onLineUsers;
	JButton jb_startServer,jb_shotDownServer;
	ServerSocket ss;
	
	public ServerManualPane(){
		jp_mianPanel=new JPanel();
		jb_startServer=new JButton("����������");
		jb_startServer.addActionListener(this);
		jb_shotDownServer=new JButton("�رշ�����");
		jb_shotDownServer.addActionListener(this);
		jp_mianPanel.add(jb_startServer);
		jp_mianPanel.add(jb_shotDownServer);
		this.add(jp_mianPanel);
		
		//���ô�������
		this.setSize(500,400);
		this.setTitle("��������");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	public void startServerAndListeningThread(){
		try {
			ss=new ServerSocket(9527);
			//�������̣߳����߳�ʵ�ַ������Ĳ��ϼ���
			RunServerListening gscs=new RunServerListening(ss);
			Thread t=new Thread(gscs);
			t.start();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}	
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if(arg0.getSource()==jb_startServer){
				this.startServerAndListeningThread();
			
		}
		if(arg0.getSource()==jb_shotDownServer){
			try {
				ss.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	//�������������
	public static void main(String []args){
		new ServerManualPane();
	}
	
}
