/**
 * function:��ѹ�ӷ������˷��ص�Message m ��
 * �ṩ���ظ�����Ϣ������Ϣ�ķ���
 */
package com.qq.tools;

import java.net.*;
import java.io.*;

import javax.swing.JOptionPane;

import com.qq.client.model.*;
import com.qq.client.view.QqList;
import com.qq.common.*;

public class UndoMessage {
	private Socket s;
	private Message m;

	public Message getM() {
		return m;
	}

	public void setM(Message m) {
		this.m = m;
	}
	
	public UndoMessage(Message m){
		this.m=m;
		switch(m.getMesType()){
		//3������Ϣ,�����������õ���Ϣд�����촰����
		case 3:
			WriteChatMessageToChatingWindow wmtcw=new WriteChatMessageToChatingWindow(m);
			wmtcw.startWrite();
			//System.out.println(m.getSender()+"��"+m.getGetter()+"˵"+m.getContent());
			break;
		//�������쳣���ͻ�������
		case 5:
			JOptionPane.showMessageDialog(null, "�������쳣�����ѱ������ߣ�");
			ClientManager.getOnLineUserList().dispose();
			break;
		}
		
	}
	
}