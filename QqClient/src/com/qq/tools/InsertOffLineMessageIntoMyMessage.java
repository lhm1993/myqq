package com.qq.tools;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import com.qq.client.model.ChatWindowManager;
import com.qq.common.Message;

public class InsertOffLineMessageIntoMyMessage {
	Message m;
	
	public InsertOffLineMessageIntoMyMessage(Message m){
		this.m=m;
	}
	
	public void startGetMessage(){
		ChatWindowManager cwm=new ChatWindowManager();
		HashMap<String, Vector<String>> hm=this.m.getOffLineMessage();
		//如果从服务器端获取的 OffLineMessage包不为空，就开始转存信息
		if(hm!=null){
			Iterator it=hm.keySet().iterator();
			while(it.hasNext()){
				String sender=(String) it.next();
				Vector v=hm.get(sender);
				int i=0;
				while(i<v.size()){
					cwm.addToMyMessage(sender,(String)v.get(i));
					i++;
				}
			}
		}	
	}
}
