/**
 * function:将当前用户的信息写在本地文件中
 * 每一个用户的信息都会保存在一个以该用户编号命名的的文件夹中
 *  1 要写的信息，当前用户 User user的信息写 userId.txt 文件中
 *    如果用户是首次登录，向服务器发送信息读取该用户的信息，如果不是首次登录，则先从文件中读取信息
 *    之后再向服务器发送请求，更新列表界面
 *  2 将聊天信息写入文件中
 */
package com.qq.client.model;

import java.io.*;
import java.util.*;

import com.qq.client.view.QqChat;
import com.qq.common.*;

public class ChatRecordManager  {
	
	private User user ;
	private PersonAttribute pa;
	
	public ChatRecordManager(User user,PersonAttribute pa) {
		this.user=user;
		this.pa=pa;
	}
	
	//将该用户的聊天记录写在本地文件中
	//将该用户和不同好友的聊天记录分别保存在不同的文件中
	//每隔一段时间，就会自动遍历 MyMessage，MyMessage保存的是已经接收的信息，但是还没有保存到聊天记录中
	//每次保存完MyMessage，就将MyMessage清空，这样还不占内存
	public void startWriteRecord() {
		
		//判断文件夹是否存在，不存在就创建一个
		File folder=new File("E:\\lianxi\\qqTest\\chatrecord\\"+user.getUserId());
		if(folder.exists()==false){
			folder.mkdir();
		}
		ChatWindowManager cwm=new ChatWindowManager();
		//当MyMessage 不为空的时候，就开始向磁盘写入文件
		if(cwm.MyMessage!=null){
			Vector<String> v=cwm.getMyMessage(pa.getUserId());
			FileWriter fw=null;
			BufferedWriter bw=null;
			try {
				fw=new FileWriter(folder+"\\"+pa.getUserId()+".txt",true);
				bw=new BufferedWriter(fw);
				int i=0;	
				//System.out.println("ChatRecordMessage v.size+"+v.size());
				while(v!=null&&i<v.size()){
					bw.write(v.get(i));
					bw.newLine();
					i++;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
			e.printStackTrace();
			}finally{
				try {
					if(bw!=null){
						bw.close();
						fw.close();
					}
				} catch (Exception e2) {
					// TODO: handle exception
					e2.printStackTrace();
				}
			}
			//保存完信息之后将信息删除
			cwm.removChatMessage(pa.getUserId());
		}	
	}
	
	//从文件中读取聊天记录
	public String readChatRecord(){
		String record="";
		File folder=new File("E:\\lianxi\\qqTest\\chatrecord\\"+user.getUserId());
		//如果聊天文件夹不存在，就返回一个空字符串
		if(folder.exists()==false){
			return record;
		}
		FileReader fr=null;
		BufferedReader br=null;
		try {
			fr=new FileReader(folder+"\\"+pa.getUserId()+".txt");
			br=new BufferedReader(fr);
			String s=null;
			while((s=br.readLine())!=null){
				record+=(s+"\r\n");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			//如果打开文件失败，返回一个空字符串
			return record;
		}finally{
			if(br!=null){
				try {
					br.close();
					fr.close();
				} catch (Exception e2) {
					// TODO: handle exception
					e2.printStackTrace();
				}
				
			}
		}
		return  record;
	}
	
	//删除记录
	public boolean deleteChatRecord(){
		boolean b=false;
		
		//1 清空当前聊天界面
		ChatWindowManager cwm=new ChatWindowManager();
		QqChat qqChat=cwm.getQqChatWindow(pa.getUserId());
		qqChat.clearChatWindow();
		
		//2将文件中保存的信息删除
		File recordFile=new File("E:\\lianxi\\qqTest\\chatrecord\\"+user.getUserId()+"\\"+pa.getUserId()+".txt");
		//如果聊天文件夹不存在，就返回一个空字符串
		if(recordFile.exists()==false){
			b=false;
		}else{
			recordFile.delete();
			b=true;
		}
		return b;
	}
}
