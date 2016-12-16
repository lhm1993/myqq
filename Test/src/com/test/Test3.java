package com.test;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.*;

public class Test3 extends JFrame implements ActionListener{

		JPanel jp_sent;//�������
		JScrollPane jsp_showChatMeeage;//��ʾ������Ϣ���
		JTextArea jta;//������Ϣ
		JTextField jtf;//������Ϣ����
		JButton jb_sent;//���Ͱ�ť
		String chatMessage="";
		//���ڶ�ȡ������Ϣ
		//���캯��
		public Test3(){
			//�����ʼ��
			//������Ϣ��ʾ��ʼ��
			jta =new JTextArea();
			jta.setEditable(false);//˫���������Ϣ�ǲ���ֱ���޸ĵ�
			jsp_showChatMeeage=new JScrollPane(jta);
			jsp_showChatMeeage.setBounds(0,0,285,120);
			//��������ʼ��
			jp_sent=new JPanel();
			jp_sent.setBounds(0,120,200,50);
			jb_sent=new JButton("����");
			//ע�����
			jb_sent.addActionListener(this);
			jb_sent.setActionCommand("sent");
			jtf=new JTextField(10);
			jp_sent.add(jtf);
			jp_sent.add(jb_sent);
			
			//������� ���ò���
			this.setLayout(null);
			this.add(jsp_showChatMeeage,"North");
			this.add(jp_sent,"South");
			//���ô�������
			this.setTitle("�ͻ���");
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this.setLocationRelativeTo(null);
			this.setSize(300,200);
			this.setVisible(true);
		}
		
		
		//������Ϣ
		public void sentMessage(){
			//1 ����Ϣ��ʾ�����촰��
			if(jtf.getText().trim().equals("")!=true){
				SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String dateStamp =new String(df.format(new Date()));
				System.out.println(dateStamp);
				//��Ҫ���͵���Ϣ��ʾ�ڴ�����
				chatMessage+=("woshuo"+"  "+dateStamp+"\r\n"+
				jtf.getText().toString()+"\r\n");
				System.out.println(chatMessage);
			}else{
				JOptionPane.showMessageDialog(this, "��Ϣ����Ϊ��");
			}
			jta.setText(chatMessage);
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if(e.getActionCommand().equals("sent")){
				this.sentMessage();
			}
		}
	
		public static void main(String []args){
			Test3 mc=new Test3();
			
		}

}