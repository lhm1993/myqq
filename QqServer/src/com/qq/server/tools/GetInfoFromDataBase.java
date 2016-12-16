/**
 * function:从数据库读取该用户的各种信息
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
		//建立一个数据库连接
		sdm=new ServerDatabaseManual();
		this.getFriendsList(this.m.getUser().getUserId());
		this.getStrangersList(this.m.getUser().getUserId());
		this.getBlackList(this.m.getUser().getUserId());
		this.getInfoOfLoginingUser(this.m.getUser().getUserId());
		this.getOffLineMessage(this.m.getUser().getUserId());
		sdm.closeSerDatCon();
	
	}	
	
	//返回该数据包	
	public Message getM() {
		return this.m;
		
	}
	//获取该用户的信息
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
	
	//读取该用户的好友表，陌生人表，以及黑名单
	public void getFriendsList(String userId){
		try {
			rs=sdm.returnListsOfUsers(userId, "friendofuser");
			
			while(rs.next()){
				PersonAttribute pa=new PersonAttribute();
				pa.setUserId(rs.getString(1));
				pa.setNickName(rs.getString(2));
				pa.setImagePath(rs.getString(3));
				
				//System.out.println("好友列表：UserId="+pa.getUserId()+" NickName="+pa.getNickName()+"ImagePath = "+pa.getImagePath());
				
				this.m.getUser().getFrientdsList().put(pa.getNickName(), pa);
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//读取陌生人列表
	public void getStrangersList(String userId){
		try {
			rs=sdm.returnListsOfUsers(userId, "strangerofuser");
			
			while(rs.next()){
				/**
				 * 要不要加上对读取内容是否为空的判断
				 */
//				if(rs.getString(1)!=null&&rs.getString(2)!=null&&rs.getString(3)!=null){
//					
//				}
				PersonAttribute pa=new PersonAttribute();
				pa.setUserId(rs.getString(1));
				pa.setNickName(rs.getString(2));
				pa.setImagePath(rs.getString(3));
				
				//System.out.println("陌生人列表：UserId="+pa.getUserId()+" NickName="+pa.getNickName()+"ImagePath = "+pa.getImagePath());
				
				this.m.getUser().getStrangersList().put(pa.getNickName(), pa);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//读取黑名单
	public void getBlackList(String userId){
		try {
			rs=sdm.returnListsOfUsers(userId, "blacklistofuser");
			while(rs.next()){
				PersonAttribute pa=new PersonAttribute();
				pa.setUserId(rs.getString(1));
				pa.setNickName(rs.getString(2));
				pa.setImagePath(rs.getString(3));
				
				//System.out.println("黑名单列表：UserId="+pa.getUserId()+" NickName="+pa.getNickName()+"ImagePath = "+pa.getImagePath());
				
				this.m.getUser().getBlackList().put(pa.getNickName(), pa);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//读取离线信息
	public void getOffLineMessage(String userId){
		try {
			rs=sdm.getOffLineMessageOfUser(userId);
			//如果rs为空，则说明没有离线信息，则不用写入
			//rs不为空的时候
			if(rs.next()){
				//System.out.println("******** userId "+userId);
				rs.beforeFirst();
			}
			while(rs.next()){
				String sender=rs.getString(1);
				//判断在信息表中有没有该用户的信息
				Vector<String> vv=this.m.getOffLineMessage().get(sender);
				//还没有保存这个sender的信息
				if(vv==null){
					Vector<String> v=new Vector<String>();
					//将信息的时间和内容合在一起保存到离线信息表中
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
