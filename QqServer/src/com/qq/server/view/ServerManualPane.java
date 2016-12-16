/**
 * function:服务器操作界面，实现服务器启动和关闭
 * 当启动服务器按钮被按下的时候就会开始监听端口，
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
		jb_startServer=new JButton("启动服务器");
		jb_startServer.addActionListener(this);
		jb_shotDownServer=new JButton("关闭服务器");
		jb_shotDownServer.addActionListener(this);
		jp_mianPanel.add(jb_startServer);
		jp_mianPanel.add(jb_shotDownServer);
		this.add(jp_mianPanel);
		
		//设置窗口属性
		this.setSize(500,400);
		this.setTitle("服务器端");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	public void startServerAndListeningThread(){
		try {
			ss=new ServerSocket(9527);
			//启动监线程，该线程实现服务器的不断监听
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
	
	//启动服务器面板
	public static void main(String []args){
		new ServerManualPane();
	}
	
}
