/**
 * function:�����ݿ��ȡ���û��ĸ�����Ϣ
 */
package com.qq.server.tools;
import java.sql.*;
import java.util.Vector;

import com.qq.common.*;

public class GetInfoFromDataBase {
	private ResultSet rs;
	private Message m;
	private ServerDatabaseManual sdm;
	private User user;
	
	public GetInfoFromDataBase(Message m){
		this.m=m;
		//����һ�����ݿ�����
		sdm=new ServerDatabaseManual();
		this.getFriendsList(this.m.getUser().getUserId());
		this.getStrangersList(this.m.getUser().getUserId());
		this.getBlackList(this.m.getUser().getUserId());
		this.getInfoOfLoginingUser(this.m.getUser().getUserId());
		this.getOffLineMessage(this.m.getUser().getUserId());
		sdm.closeSerDatCon();
	
	}	
	
	//���ظ����ݰ�	
	public Message getM() {
		return this.m;
		
	}
	//��ȡ���û�����Ϣ
	public void getInfoOfLoginingUser(String userId){
		try {
			rs=sdm.getInfoOfloginingUser(userId);
			
			while(rs.next()){
				this.m.getUser().setUserId(rs.getString(1));
				this.m.getUser().setNickName(rs.getString(2));
				this.m.getUser().setImagePath(rs.getString(3));
				//System.out.println("UserId="+m.getUser().getUserId()+" NickName="+m.getUser().getNickName()+"ImagePath = "+m.getUser().getImagePath());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//��ȡ���û��ĺ��ѱ�İ���˱��Լ�������
	public void getFriendsList(String userId){
		try {
			rs=sdm.returnListsOfUsers(userId, "friendofuser");
			
			while(rs.next()){
				PersonAttribute pa=new PersonAttribute();
				pa.setUserId(rs.getString(1));
				pa.setNickName(rs.getString(2));
				pa.setImagePath(rs.getString(3));
				
				//System.out.println("�����б�UserId="+pa.getUserId()+" NickName="+pa.getNickName()+"ImagePath = "+pa.getImagePath());
				
				this.m.getUser().getFrientdsList().put(pa.getNickName(), pa);
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//��ȡİ�����б�
	public void getStrangersList(String userId){
		try {
			rs=sdm.returnListsOfUsers(userId, "strangerofuser");
			
			while(rs.next()){
				/**
				 * Ҫ��Ҫ���϶Զ�ȡ�����Ƿ�Ϊ�յ��ж�
				 */
//				if(rs.getString(1)!=null&&rs.getString(2)!=null&&rs.getString(3)!=null){
//					
//				}
				PersonAttribute pa=new PersonAttribute();
				pa.setUserId(rs.getString(1));
				pa.setNickName(rs.getString(2));
				pa.setImagePath(rs.getString(3));
				
				//System.out.println("İ�����б�UserId="+pa.getUserId()+" NickName="+pa.getNickName()+"ImagePath = "+pa.getImagePath());
				
				this.m.getUser().getStrangersList().put(pa.getNickName(), pa);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//��ȡ������
	public void getBlackList(String userId){
		try {
			rs=sdm.returnListsOfUsers(userId, "blacklistofuser");
			while(rs.next()){
				PersonAttribute pa=new PersonAttribute();
				pa.setUserId(rs.getString(1));
				pa.setNickName(rs.getString(2));
				pa.setImagePath(rs.getString(3));
				
				//System.out.println("�������б�UserId="+pa.getUserId()+" NickName="+pa.getNickName()+"ImagePath = "+pa.getImagePath());
				
				this.m.getUser().getBlackList().put(pa.getNickName(), pa);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//��ȡ������Ϣ
	public void getOffLineMessage(String userId){
		try {
			rs=sdm.getOffLineMessageOfUser(userId);
			//���rsΪ�գ���˵��û��������Ϣ������д��
			//rs��Ϊ�յ�ʱ��
			if(rs.next()){
				//System.out.println("******** userId "+userId);
				rs.beforeFirst();
			}
			while(rs.next()){
				String sender=rs.getString(1);
				//�ж�����Ϣ������û�и��û�����Ϣ
				Vector<String> vv=this.m.getOffLineMessage().get(sender);
				//��û�б������sender����Ϣ
				if(vv==null){
					Vector<String> v=new Vector<String>();
					//����Ϣ��ʱ������ݺ���һ�𱣴浽������Ϣ����
					v.add(rs.getString(2)+"\r\n"+rs.getString(3));
					this.m.getOffLineMessage().put(sender, v);
				}else{
					vv.add(rs.getString(2)+"\r\n"+rs.getString(3));
				}
				
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
