/**
 * function:����ǰ�û�����Ϣд�ڱ����ļ���
 * ÿһ���û�����Ϣ���ᱣ����һ���Ը��û���������ĵ��ļ�����
 *  1 Ҫд����Ϣ����ǰ�û� User user����Ϣд userId.txt �ļ���
 *    ����û����״ε�¼���������������Ϣ��ȡ���û�����Ϣ����������״ε�¼�����ȴ��ļ��ж�ȡ��Ϣ
 *    ֮������������������󣬸����б����
 *  2 ��������Ϣд���ļ���
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
	
	//�����û��������¼д�ڱ����ļ���
	//�����û��Ͳ�ͬ���ѵ������¼�ֱ𱣴��ڲ�ͬ���ļ���
	//ÿ��һ��ʱ�䣬�ͻ��Զ����� MyMessage��MyMessage��������Ѿ����յ���Ϣ�����ǻ�û�б��浽�����¼��
	//ÿ�α�����MyMessage���ͽ�MyMessage��գ���������ռ�ڴ�
	public void startWriteRecord() {
		
		//�ж��ļ����Ƿ���ڣ������ھʹ���һ��
		File folder=new File("E:\\lianxi\\qqTest\\chatrecord\\"+user.getUserId());
		if(folder.exists()==false){
			folder.mkdir();
		}
		ChatWindowManager cwm=new ChatWindowManager();
		//��MyMessage ��Ϊ�յ�ʱ�򣬾Ϳ�ʼ�����д���ļ�
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
			//��������Ϣ֮����Ϣɾ��
			cwm.removChatMessage(pa.getUserId());
		}	
	}
	
	//���ļ��ж�ȡ�����¼
	public String readChatRecord(){
		String record="";
		File folder=new File("E:\\lianxi\\qqTest\\chatrecord\\"+user.getUserId());
		//��������ļ��в����ڣ��ͷ���һ�����ַ���
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
			//������ļ�ʧ�ܣ�����һ�����ַ���
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
	
	//ɾ����¼
	public boolean deleteChatRecord(){
		boolean b=false;
		
		//1 ��յ�ǰ�������
		ChatWindowManager cwm=new ChatWindowManager();
		QqChat qqChat=cwm.getQqChatWindow(pa.getUserId());
		qqChat.clearChatWindow();
		
		//2���ļ��б������Ϣɾ��
		File recordFile=new File("E:\\lianxi\\qqTest\\chatrecord\\"+user.getUserId()+"\\"+pa.getUserId()+".txt");
		//��������ļ��в����ڣ��ͷ���һ�����ַ���
		if(recordFile.exists()==false){
			b=false;
		}else{
			recordFile.delete();
			b=true;
		}
		return b;
	}
}
