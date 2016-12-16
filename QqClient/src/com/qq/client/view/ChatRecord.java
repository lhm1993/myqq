/**
 * function:��ʾ�뵱ǰ�������������¼
 * ����ʾ������ڵ�ʱ�򣬾��Ѿ�ִ���˽�MyMessage�е���Ϣд�ڼ�¼�еĶ���
 * 	���ԣ�����MyMessage���Ѿ��������  ���ChatRecordManager
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
		//�����ʼ��
		jp=new JPanel();
		jb_clearRecord=new JButton("��������¼");
		jb_clearRecord.addActionListener(this);
		jta=new JTextArea();
		jta.setEditable(false);
		//��ȡ������Ϣ
		ChatRecordManager crm=new ChatRecordManager(user,pa);
		//�����ǰ��ǰ����Ϣ��MyMessage�л�û�б��浽record�У��ȱ����ȥ���ڱ����¼��ʱ�� MyMessage���Ѿ��������
		crm.startWriteRecord();
		jta.setText(crm.readChatRecord());
		jsp=new JScrollPane(jta);
		//�����������ò���
		this.setLayout(null);
		jsp.setBounds(0,0,385,220);
		jp.setBounds(0,220,400,80);
		jp.add(jb_clearRecord);
		this.add(jsp,"North");
		this.add(jp,"South");
		//���ô�������
		this.setSize(400,300);
		this.setTitle(user.getNickName()+" �� "+pa.getNickName()+" �������¼");
		Image image=Toolkit.getDefaultToolkit().getImage(pa.getImagePath());
		this.setIconImage(image);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		//ֻ��������Ϣ��Ϊ�յ�ʱ��Ż�ִ��ɾ��
		if(e.getSource()==jb_clearRecord&&jta.getText().equals("")!=true){
			//JOptionPane.showConfirmDialog ���ص��� 0����ȷ�� ����һ����ť��/ 1����ȡ�����ڶ�����ť��
			int i=JOptionPane.showConfirmDialog(null, "ȷ��Ҫɾ������û��������¼��","ɾ�������¼",JOptionPane.YES_NO_OPTION);
			//���ȷ��ɾ����Ϣ����ִ�в�����ȡ��ɾ����Ϣ�����κδ���ȷ��ɾ����Ϣ
			if(i==0){
				//ɾ�������ļ�
				//ɾ�����ڵ�������Ϣ
				//ɾ���������е���Ϣ
				//��������������������ChatRecordManager����ɵ�
				ChatRecordManager crm=new ChatRecordManager(this.user, this.pa);
				boolean b=crm.deleteChatRecord();
				if(b){
					this.jta.setText("");
					JOptionPane.showMessageDialog(null, "������Ϣ��ɾ����");
				}
			}
		}
	}
	
}
