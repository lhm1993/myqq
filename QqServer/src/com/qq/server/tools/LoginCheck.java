/**
 * function:从数据库验证该用户是否可以合法登录
 */
package com.qq.server.tools;

import com.qq.common.Message;


public class LoginCheck {
	
	private boolean isLoginInfoRight;
	

	public boolean isLoginInfoRight() {
		return isLoginInfoRight;
	}

	public LoginCheck(Message m){
		ServerDatabaseManual sdm=new ServerDatabaseManual();
		isLoginInfoRight=sdm.loginCheak(m.getUser().getUserId(), m.getPasswd());
		//System.out.println(m.getUser().getUserId()+"  "+m.getPasswd());
		//关闭数据库连接
		sdm.closeSerDatCon();	
	}
	
	
}
