/**
 * function:客户端连接服务器端
 * 
 */
package com.qq.tools;
import java.net.*;
import java.io.*;

import com.qq.common.*;

public class ClientSeverConnectManager {
	
	private Socket s;
	
	public Socket getS() {
		return s;
	}
	
	//1 建立连接
	public boolean startClientSeverConnect(){
		boolean b=false;
		try {
			s=new Socket("127.0.0.1",9527);
			if(s!=null){
				b=true;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			b=false;
		}
		return  b;
	}
	
	//2 关闭连接
	public void closeClientServerConnect(){
		try {
			if(s!=null){
				s.close();
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
