/**
 * function:ת���ͻ��˷���ͻ��˵���Ϣ��
 */
package com.qq.server.model;

import java.net.Socket;

import com.qq.common.*;
import com.qq.server.tools.*;

public class TransferMessageFromClientToClient {
	
	Message m;
	
	public TransferMessageFromClientToClient(Message m){
		this.m=m;
	}
	public void startTransferMessage(){
		//�ж��û��Ƿ�����
		//m.getter���ߣ��ͽ���Ϣ���͸��Է��������߾ͽ���Ϣ�洢�����ݿ���
		Socket s=(new ServerClientSocketManager()).getSocketFromServerClientConnect(this.m.getGetter());
		//�û�����
		if(s!=null){
			SendMessageToClient smtc=new SendMessageToClient(m);
			smtc.startSendMessage();
		//�û�������
		}else if(s==null){
			//����Ϣд��m.getter �����ݿ���
			new SavaMessageForClientIsNotOnLine(this.m);
			
		}
		
		
	}
}
