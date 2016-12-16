/**
 * function:��ɷ������˺����ݿ�Ĳ���
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
	
	//1�������ݿ�����
	public ServerDatabaseManual(){
		try {
			Class.forName(dirver);
			connect=DriverManager.getConnection(url,user,passwd);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//JOptionPane.showMessageDialog(null, "���ݿ�����ʧ�ܣ�");
			e.printStackTrace();
			
		}
		
	}
	//2���ݲ���
	//2.1 ��½��֤
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
	
	//2.2 �����ݿ��ȡ�û��ĺ����б�,������,İ�����б�
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
	
	//2.3 �����ݿ��ж�ȡ��ǰ��¼�û�����Ϣ
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
	//2.4�����ݿ���д�뷢���������û�����Ϣ
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
	//2.5 �����ݿ��ж�ȡ��ǰ�û���������Ϣ
	public ResultSet getOffLineMessageOfUser(String userId){
		ResultSet rs=null;
		String sqlSelect="select * from messageofuser_"+userId;
		try {
			prepareStatment=connect.prepareStatement(sqlSelect);
			rs=prepareStatment.executeQuery();
			//���������ݲ�Ϊ�գ���ɾ�����ݿ��е�������Ϣ
			//������Ϣ��ֻ���͸��ͻ���һ�ξ����ڵ�¼��ʱ�򣬷�������ͷ��������ݿ���ɾ��
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
	
	//3�ر�����,�������˳���ʱ�����
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
