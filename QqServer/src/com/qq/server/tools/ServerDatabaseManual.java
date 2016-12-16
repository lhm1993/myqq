/**
 * function:完成服务器端和数据库的操作
 */
package com.qq.server.tools;

import java.sql.*;

import javax.swing.JOptionPane;

import com.qq.common.Message;

public class ServerDatabaseManual {
	
	public static Connection connect;
	public static PreparedStatement prepareStatment;
	private String dirver="com.mysql.jdbc.Driver";
	private String url="jdbc:mysql://127.0.0.1:3306/qq";
	private String user="root";
	private String passwd="root";
	
	//1建立数据库链接
	public ServerDatabaseManual(){
		try {
			Class.forName(dirver);
			connect=DriverManager.getConnection(url,user,passwd);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//JOptionPane.showMessageDialog(null, "数据库连接失败！");
			e.printStackTrace();
			
		}
		
	}
	//2数据操作
	//2.1 登陆验证
	public boolean loginCheak(String userId,String passwd){
		boolean b=false;
		String sql="select * from userTable where userId=? and pass=?";
		try {
			prepareStatment=connect.prepareStatement(sql);
			prepareStatment.setString(1, userId);
			prepareStatment.setString(2, passwd);
			ResultSet rs=prepareStatment.executeQuery();
			if(rs.next()){
				b=true;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return b;
		}
		
		return b;
	}	
	
	//2.2 从数据库读取用户的好友列表,黑名单,陌生人列表
	public ResultSet returnListsOfUsers(String userId,String table){
		ResultSet rs=null;
		String sql="select * from "+table+"_"+userId;
		try {
			prepareStatment=connect.prepareStatement(sql);
			rs=prepareStatment.executeQuery();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return rs;
	}
	
	//2.3 从数据库中读取当前登录用户的信息
	public ResultSet getInfoOfloginingUser(String userId){
		ResultSet rs=null;
		String sql="select * from userTable where userId=?";
		try {
			prepareStatment=connect.prepareStatement(sql);
			prepareStatment.setString(1, userId);
			rs=prepareStatment.executeQuery();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return rs;
	}	
	//2.4向数据库中写入发给不在线用户的信息
	public void saveMessageForClientIsNotOnline(Message m){
		String saveId=m.getGetter();
		String sql="insert into messageofuser_"+m.getGetter()+" values(?,?,?)";
		try {
			prepareStatment=connect.prepareStatement(sql);
			prepareStatment.setString(1, m.getSender());
			prepareStatment.setString(2, m.getTimeStamp());
			prepareStatment.setString(3, m.getContent());
			prepareStatment.executeUpdate();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//2.5 从数据库中读取当前用户的离线信息
	public ResultSet getOffLineMessageOfUser(String userId){
		ResultSet rs=null;
		String sqlSelect="select * from messageofuser_"+userId;
		try {
			prepareStatment=connect.prepareStatement(sqlSelect);
			rs=prepareStatment.executeQuery();
			//如果表的内容不为空，就删除数据库中的离线信息
			//离线信息包只发送给客户端一次就是在登录的时候，发送完就送服务器数据库中删除
			if(rs.next()){
				rs.beforeFirst();
				String sqlDelete="delete from  messageofuser_"+userId;
				prepareStatment=connect.prepareStatement(sqlDelete);
				prepareStatment.executeUpdate();
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return rs;
	}
	
	//3关闭连接,服务器退出的时候调用
	public void closeSerDatCon(){

		try {
			if(connect!=null){
				connect.close();
			}
			if(prepareStatment!=null){
				prepareStatment.close();
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
