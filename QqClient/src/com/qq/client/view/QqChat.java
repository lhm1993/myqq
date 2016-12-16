/**
 * function:通过传进来的用户 User user 和聊天对象的 PersonAttribute pa初始化聊天窗口
 * 向服务器发送信息的功能
 * Socket 是不能被序列化的 ，所有在传送的Message m包中，不应该包括s
 * 键盘监听事件的添加要使用 this.requestFocus()而且这个语句要加在窗口添加完其他组件之后
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
	
	JPanel jp_send;//发送面板
	JScrollPane jsp_showChatMeeage;//显示聊天信息面板
	JTextArea jta;//聊天信息
	JTextField jtf;//输入信息窗口
	JButton jb_send,jb_chatRecord;//发送按钮
	String chatMessage="";
	User user ;
	PersonAttribute pa;
	Message m;
	Socket s;
	SimpleDateFormat df;
	String timeStamp;
	
	//构造函数
	public QqChat(User user, PersonAttribute pa,final Socket s){
		this.user=user;
		this.pa=pa;
		this.s=s;
		//组件初始化
		//聊天信息显示初始化
		jta =new JTextArea();
		jta.setEditable(false);//双方聊天的信息是不能直接修改的
		
		//将从服务器得到的离线信息显示在聊天界面上
		WriteChatMessageToChatingWindow wmtcw=new WriteChatMessageToChatingWindow();
		chatMessage=wmtcw.getOffLineMessageFromMyMessage(pa.getUserId());
		
		jta.setText(chatMessage);
		jsp_showChatMeeage=new JScrollPane(jta);
		jsp_showChatMeeage.setBounds(0,0,385,220);
		//发送面板初始化
		jp_send=new JPanel();
		jp_send.setBounds(0,220,400,50);
		jb_send=new JButton("发送");
		jb_chatRecord=new JButton("聊天记录");
		//注册监听
		jb_send.addActionListener(this);
		jb_chatRecord.addActionListener(this);
		jtf=new JTextField(15);
		//点击回车发送信息
		jtf.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				if(e.getKeyCode()==KeyEvent.VK_ENTER){
					if(jtf.getText().equals("")==false){//发送信息不能为空
						writeMessage();
					}else{
						JOptionPane.showMessageDialog(null, " 信息不能为空！");
					}
				}		
			}
		});
		
		jp_send.add(jtf);
		jp_send.add(jb_send);
		jp_send.add(jb_chatRecord);
		
		//添加组件 设置布局
		this.setLayout(null);
		this.add(jsp_showChatMeeage,"North");
		this.add(jp_send,"South");
		
		//将聊天窗口的标题和图标设置为当前聊天对象的昵称和头像
		this.setTitle(user.getNickName()+" 正在和  "+pa.getNickName()+" 聊天");
		Image image=Toolkit.getDefaultToolkit().getImage(pa.getImagePath());
		this.setIconImage(image);
		this.setLocationRelativeTo(null);
		this.setSize(400,300);
		this.setVisible(true);
		//添加完组件之后，焦点就不在窗口上了，
		//所以要重新将焦点放在窗口上，否则键盘监听事件就无法完成
		this.requestFocus();
	}
	
	public void clearChatWindow(){
		this.jta.setText("");
	}
	//将发送的信息写到jta上
	public void writeSendToChatPane(){
		//这里的的月份需要大写M 否则会出错 小时h小写是12小时制  大写H是24小时制
		chatMessage+=(this.user.getNickName()+"  "+timeStamp+"\r\n"+
				jtf.getText().toString()+"\r\n");
		//将写的信息添加到MyMessage中
		ChatWindowManager cwm=new ChatWindowManager();
		cwm.addToMyMessage(pa.getUserId(), this.user.getUserId()+"  "+timeStamp+"\r\n"+
				jtf.getText().toString());
		jta.setText(chatMessage);
		jtf.setText("");
	}
	
	//将接收的信息写到jta上
	public void writeGetToChatPane(Message m){
		
		chatMessage+=(pa.getNickName()+"  "+m.getTimeStamp()+"\r\n"+
				m.getContent()+"\r\n");
		jta.setText(chatMessage);
	}
	
	//写信息包
	public void writeMessage(){
		m=new Message();
		//1 发送信息的时间
		df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		timeStamp =new String(df.format(new Date()));
		m.setTimeStamp(timeStamp);
		
		//2 信息内容-不对信息的格式做任何 处理，即不包含任何回车换行
		m.setContent(jtf.getText());
		//3 发送者，接收者
		m.setSender(user.getUserId());
			//信息的接收者的类型需要判断之后才能找到改用户的编号
			//在客户端使用的是昵称唯一
			//但是在服务器端以及数据库中使用的还是编号唯一，这里还是要转换成编号以后再发送个服务器端方便服务器端和数据库操作
		if(this.user.getFrientdsList().get(pa.getNickName())!=null){
			m.setGetter(((PersonAttribute)this.user.getFrientdsList().get(pa.getNickName())).getUserId());
		}else if(this.user.getStrangersList().get(pa.getNickName())!=null){
			m.setGetter(((PersonAttribute)this.user.getStrangersList().get(pa.getNickName())).getUserId());
		}else if(this.user.getBlackList().get(pa.getNickName())!=null){
			m.setGetter(((PersonAttribute)this.user.getBlackList().get(pa.getNickName())).getUserId());
		}
		//4信息类型 3 表示请求通讯
		m.setMesType(3);
		//5将用户写入
		m.setUser(this.user);
		//6 将要发送的信息显示在窗口上
		this.writeSendToChatPane();
		//7 将信息发送出去
		SendMessageToServer smts=new SendMessageToServer(this.m,this.s);
		//System.out.println(this.s);
		smts.startSendMessage();
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if(arg0.getSource()==jb_send){
			//发送信息
			if(jtf.getText().equals("")==false){//发送信息不能为空
				this.writeMessage();
			}else{
				JOptionPane.showMessageDialog(this, " 信息不能为空！");
			}
		}
		//显示聊天记录
		if(arg0.getSource()==jb_chatRecord){
			new ChatRecord(this.user, this.pa);
		}
	}


	
}
