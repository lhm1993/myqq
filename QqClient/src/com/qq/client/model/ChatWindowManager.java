/**
 * function:�����û������촰��
 * MyMessage�������׶Σ�
 * 	��һ�׶Σ�����Ϣû�б���ʾ��ʱ��û����Ϣ��������ӽ����ߺͷ�����
 * �ڶ��׶Σ�����Ϣ����ʾ�Ժ󣬾�������ʾ����Ϣ����������Ͻ����ߺͷ����ߣ������ڹرյ�ʱ�򣬾ͻ�ת��Ϊ�����¼
 */
package com.qq.client.model;

import java.util.*;

import com.qq.client.view.QqChat;
import com.qq.common.Message;
public class ChatWindowManager {
	//����һ������ǰ�û��������촰�ڵ�HashMap <chatId,Qqchat>
	public static HashMap<String,QqChat> qqChatWindowsManager=new HashMap<String,QqChat>();
	//MyMessage �б�����Ǳ��οͻ��˵�¼�Ժ�Ľ��յ�����Ϣ������ÿ����ʾ֮�󣬸���Ϣ��Ӧ�ô�MyMessage ���Ƴ�
	//<id,Vector>���ﱣ����ǵ�ǰ�û�û�д򿪵����촰�ڵĺ��ѵ�id �� �ӷ������˽��ܵ��ĺ͸ú��ѵ�������Ϣ
	public static HashMap<String ,Vector<String>> MyMessage=new HashMap<String ,Vector<String>>();

	//ȡ����һ���û���������Ϣ
	public static Vector<String> getMyMessage(String chatId) {
		return MyMessage.get(chatId);
	}
	
	//������һ���û���������Ϣ
	public static void addToMyMessage(String chatId,String chatMessage) {
		Vector <String> vv=(new ChatWindowManager()).getMyMessage(chatId);
		//����Ѿ������˸��û���������Ϣ����ֱ�ӽ�����Ϣ���뵽Vector��
		if(vv!=null){
			vv.add(chatMessage);
		//������û���ص�������Ϣ�����ڣ����½�һ����¼�Ž�ȥ
		}else{
			Vector<String> v=new Vector<String>();
			v.add(chatMessage);
			MyMessage.put(chatId, v);
		}
	}
		
	//ɾ����һ���û���������Ϣ
	public static void removChatMessage(String chatId){
		Vector <String> vv=(new ChatWindowManager()).getMyMessage(chatId);
		//������Ϣ��Ϊ�գ���ɾ��������������
		if(vv!=null&&vv.size()!=0){
			MyMessage.remove(chatId);
		}
	}
	//ȡ��һ�����촰��
	public static QqChat getQqChatWindow(String chatId) {
		return qqChatWindowsManager.get(chatId);
	}
	//����һ�����촰��
	public static void addQqChatWindowsToManager(String chatId,QqChat qqchat) {
		qqChatWindowsManager.put(chatId, qqchat);
	}
	
	//ɾ��һ�����촰��
	public static boolean removChatWindowFromManager(String chatId){
		boolean b=false;
		if(qqChatWindowsManager.get(chatId)!=null){
			qqChatWindowsManager.remove(chatId);
			b=true;
		}
		return b;
	}
}
