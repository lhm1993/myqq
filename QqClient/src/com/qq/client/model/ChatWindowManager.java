/**
 * function:管理用户的聊天窗口
 * MyMessage有两个阶段：
 * 	第一阶段：当信息没有被显示的时候，没有信息内容中添加接收者和发送者
 * 第二阶段：当信息被显示以后，就在已显示的信息内容上添加上接收者和发送者，当窗口关闭的时候，就会转存为聊天记录
 */
package com.qq.client.model;

import java.util.*;

import com.qq.client.view.QqChat;
import com.qq.common.Message;
public class ChatWindowManager {
	//设置一个管理当前用户所有聊天窗口的HashMap <chatId,Qqchat>
	public static HashMap<String,QqChat> qqChatWindowsManager=new HashMap<String,QqChat>();
	//MyMessage 中保存的是本次客户端登录以后的接收到的信息，并且每次显示之后，该信息都应该从MyMessage 中移除
	//<id,Vector>这里保存的是当前用户没有打开的聊天窗口的好友的id 和 从服务器端接受到的和该好友的聊天信息
	public static HashMap<String ,Vector<String>> MyMessage=new HashMap<String ,Vector<String>>();

	//取出与一个用户的聊天信息
	public static Vector<String> getMyMessage(String chatId) {
		return MyMessage.get(chatId);
	}
	
	//放入与一个用户的聊天信息
	public static void addToMyMessage(String chatId,String chatMessage) {
		Vector <String> vv=(new ChatWindowManager()).getMyMessage(chatId);
		//如果已经保存了该用户的聊天信息，就直接将该信息放入到Vector中
		if(vv!=null){
			vv.add(chatMessage);
		//如果该用户相关的聊天信息不存在，就新建一条记录放进去
		}else{
			Vector<String> v=new Vector<String>();
			v.add(chatMessage);
			MyMessage.put(chatId, v);
		}
	}
		
	//删除与一个用户的聊天信息
	public static void removChatMessage(String chatId){
		Vector <String> vv=(new ChatWindowManager()).getMyMessage(chatId);
		//聊天信息不为空，就删除，否则不做动作
		if(vv!=null&&vv.size()!=0){
			MyMessage.remove(chatId);
		}
	}
	//取出一个聊天窗口
	public static QqChat getQqChatWindow(String chatId) {
		return qqChatWindowsManager.get(chatId);
	}
	//放入一个聊天窗口
	public static void addQqChatWindowsToManager(String chatId,QqChat qqchat) {
		qqChatWindowsManager.put(chatId, qqchat);
	}
	
	//删除一个聊天窗口
	public static boolean removChatWindowFromManager(String chatId){
		boolean b=false;
		if(qqChatWindowsManager.get(chatId)!=null){
			qqChatWindowsManager.remove(chatId);
			b=true;
		}
		return b;
	}
}
