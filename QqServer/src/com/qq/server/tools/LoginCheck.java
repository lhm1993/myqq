/**
 * function:�����ݿ���֤���û��Ƿ���ԺϷ���¼
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
		//�ر����ݿ�����
		sdm.closeSerDatCon();	
	}
	
	
}
