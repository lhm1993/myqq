/**
 * function:显示与当前聊天对象的聊天记录
 * 在显示这个窗口的时候，就已经执行了将MyMessage中的信息写在记录中的动作
 * 	所以，这里MyMessage就已经被清空了  详见ChatRecordManager
 */
package com.qq.client.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.*;

import com.qq.client.model.ChatRecordManager;
import com.qq.common.*;

public class ChatRecord extends JFrame implements ActionListener{
	
	private User user;
	private PersonAttribute pa;
	JTextArea jta;
	JScrollPane jsp;
	JButton jb_clearRecord;
	JPanel jp;
	public ChatRecord(User user,PersonAttribute pa){
		
		this.user=user;
		this.pa=pa;
		//组件初始化
		jp=new JPanel();
		jb_clearRecord=new JButton("清空聊天记录");
		jb_clearRecord.addActionListener(this);
		jta=new JTextArea();
		jta.setEditable(false);
		//读取聊天信息
		ChatRecordManager crm=new ChatRecordManager(user,pa);
		//如果当前当前有信息在MyMessage中还没有保存到record中，先保存进去，在保存记录的时候， MyMessage就已经被清空了
		crm.startWriteRecord();
		jta.setText(crm.readChatRecord());
		jsp=new JScrollPane(jta);
		//添加组件，设置布局
		this.setLayout(null);
		jsp.setBounds(0,0,385,220);
		jp.setBounds(0,220,400,80);
		jp.add(jb_clearRecord);
		this.add(jsp,"North");
		this.add(jp,"South");
		//设置窗口属性
		this.setSize(400,300);
		this.setTitle(user.getNickName()+" 与 "+pa.getNickName()+" 的聊天记录");
		Image image=Toolkit.getDefaultToolkit().getImage(pa.getImagePath());
		this.setIconImage(image);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		//只有聊天信息不为空的时候才会执行删除
		if(e.getSource()==jb_clearRecord&&jta.getText().equals("")!=true){
			//JOptionPane.showConfirmDialog 返回的是 0——确定 （第一个按钮）/ 1——取消（第二个按钮）
			int i=JOptionPane.showConfirmDialog(null, "确定要删除与该用户的聊天记录吗？","删除聊天记录",JOptionPane.YES_NO_OPTION);
			//如果确认删除信息，才执行操作，取消删除信息后不做任何处理确认删除信息
			if(i==0){
				//删除本地文件
				//删除窗口的聊天信息
				//删除本窗口中的信息
				//上述这三个动作都是在ChatRecordManager中完成的
				ChatRecordManager crm=new ChatRecordManager(this.user, this.pa);
				boolean b=crm.deleteChatRecord();
				if(b){
					this.jta.setText("");
					JOptionPane.showMessageDialog(null, "聊天信息已删除！");
				}
			}
		}
	}
	
}
