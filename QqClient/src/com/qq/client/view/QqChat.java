/**
 * function:ͨ�����������û� User user ���������� PersonAttribute pa��ʼ�����촰��
 * �������������Ϣ�Ĺ���
 * Socket �ǲ��ܱ����л��� �������ڴ��͵�Message m���У���Ӧ�ð���s
 * ���̼����¼������Ҫʹ�� this.requestFocus()����������Ҫ���ڴ���������������֮��
 */
package com.qq.client.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import javax.swing.*;

import com.qq.client.model.ChatRecordManager;
import com.qq.client.model.ChatWindowManager;
import com.qq.client.model.WriteChatMessageToChatingWindow;
import com.qq.common.*;
import com.qq.tools.InsertOffLineMessageIntoMyMessage;
import com.qq.tools.SendMessageToServer;

public class QqChat extends JFrame implements ActionListener{
	
	JPanel jp_send;//�������
	JScrollPane jsp_showChatMeeage;//��ʾ������Ϣ���
	JTextArea jta;//������Ϣ
	JTextField jtf;//������Ϣ����
	JButton jb_send,jb_chatRecord;//���Ͱ�ť
	String chatMessage="";
	User user ;
	PersonAttribute pa;
	Message m;
	Socket s;
	SimpleDateFormat df;
	String timeStamp;
	
	//���캯��
	public QqChat(User user, PersonAttribute pa,final Socket s){
		this.user=user;
		this.pa=pa;
		this.s=s;
		//�����ʼ��
		//������Ϣ��ʾ��ʼ��
		jta =new JTextArea();
		jta.setEditable(false);//˫���������Ϣ�ǲ���ֱ���޸ĵ�
		
		//���ӷ������õ���������Ϣ��ʾ�����������
		WriteChatMessageToChatingWindow wmtcw=new WriteChatMessageToChatingWindow();
		chatMessage=wmtcw.getOffLineMessageFromMyMessage(pa.getUserId());
		
		jta.setText(chatMessage);
		jsp_showChatMeeage=new JScrollPane(jta);
		jsp_showChatMeeage.setBounds(0,0,385,220);
		//��������ʼ��
		jp_send=new JPanel();
		jp_send.setBounds(0,220,400,50);
		jb_send=new JButton("����");
		jb_chatRecord=new JButton("�����¼");
		//ע�����
		jb_send.addActionListener(this);
		jb_chatRecord.addActionListener(this);
		jtf=new JTextField(15);
		//����س�������Ϣ
		jtf.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				if(e.getKeyCode()==KeyEvent.VK_ENTER){
					if(jtf.getText().equals("")==false){//������Ϣ����Ϊ��
						writeMessage();
					}else{
						JOptionPane.showMessageDialog(null, " ��Ϣ����Ϊ�գ�");
					}
				}		
			}
		});
		
		jp_send.add(jtf);
		jp_send.add(jb_send);
		jp_send.add(jb_chatRecord);
		
		//������ ���ò���
		this.setLayout(null);
		this.add(jsp_showChatMeeage,"North");
		this.add(jp_send,"South");
		
		//�����촰�ڵı����ͼ������Ϊ��ǰ���������ǳƺ�ͷ��
		this.setTitle(user.getNickName()+" ���ں�  "+pa.getNickName()+" ����");
		Image image=Toolkit.getDefaultToolkit().getImage(pa.getImagePath());
		this.setIconImage(image);
		this.setLocationRelativeTo(null);
		this.setSize(400,300);
		this.setVisible(true);
		//��������֮�󣬽���Ͳ��ڴ������ˣ�
		//����Ҫ���½�������ڴ����ϣ�������̼����¼����޷����
		this.requestFocus();
	}
	
	public void clearChatWindow(){
		this.jta.setText("");
	}
	//�����͵���Ϣд��jta��
	public void writeSendToChatPane(){
		//����ĵ��·���Ҫ��дM �������� СʱhСд��12Сʱ��  ��дH��24Сʱ��
		chatMessage+=(this.user.getNickName()+"  "+timeStamp+"\r\n"+
				jtf.getText().toString()+"\r\n");
		//��д����Ϣ��ӵ�MyMessage��
		ChatWindowManager cwm=new ChatWindowManager();
		cwm.addToMyMessage(pa.getUserId(), this.user.getUserId()+"  "+timeStamp+"\r\n"+
				jtf.getText().toString());
		jta.setText(chatMessage);
		jtf.setText("");
	}
	
	//�����յ���Ϣд��jta��
	public void writeGetToChatPane(Message m){
		
		chatMessage+=(pa.getNickName()+"  "+m.getTimeStamp()+"\r\n"+
				m.getContent()+"\r\n");
		jta.setText(chatMessage);
	}
	
	//д��Ϣ��
	public void writeMessage(){
		m=new Message();
		//1 ������Ϣ��ʱ��
		df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		timeStamp =new String(df.format(new Date()));
		m.setTimeStamp(timeStamp);
		
		//2 ��Ϣ����-������Ϣ�ĸ�ʽ���κ� �������������κλس�����
		m.setContent(jtf.getText());
		//3 �����ߣ�������
		m.setSender(user.getUserId());
			//��Ϣ�Ľ����ߵ�������Ҫ�ж�֮������ҵ����û��ı��
			//�ڿͻ���ʹ�õ����ǳ�Ψһ
			//�����ڷ��������Լ����ݿ���ʹ�õĻ��Ǳ��Ψһ�����ﻹ��Ҫת���ɱ���Ժ��ٷ��͸��������˷���������˺����ݿ����
		if(this.user.getFrientdsList().get(pa.getNickName())!=null){
			m.setGetter(((PersonAttribute)this.user.getFrientdsList().get(pa.getNickName())).getUserId());
		}else if(this.user.getStrangersList().get(pa.getNickName())!=null){
			m.setGetter(((PersonAttribute)this.user.getStrangersList().get(pa.getNickName())).getUserId());
		}else if(this.user.getBlackList().get(pa.getNickName())!=null){
			m.setGetter(((PersonAttribute)this.user.getBlackList().get(pa.getNickName())).getUserId());
		}
		//4��Ϣ���� 3 ��ʾ����ͨѶ
		m.setMesType(3);
		//5���û�д��
		m.setUser(this.user);
		//6 ��Ҫ���͵���Ϣ��ʾ�ڴ�����
		this.writeSendToChatPane();
		//7 ����Ϣ���ͳ�ȥ
		SendMessageToServer smts=new SendMessageToServer(this.m,this.s);
		//System.out.println(this.s);
		smts.startSendMessage();
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if(arg0.getSource()==jb_send){
			//������Ϣ
			if(jtf.getText().equals("")==false){//������Ϣ����Ϊ��
				this.writeMessage();
			}else{
				JOptionPane.showMessageDialog(this, " ��Ϣ����Ϊ�գ�");
			}
		}
		//��ʾ�����¼
		if(arg0.getSource()==jb_chatRecord){
			new ChatRecord(this.user, this.pa);
		}
	}


	
}
