/**
 * function:客户端在线离线管理
 */
package com.qq.client.model;

import com.qq.client.view.QqList;

public class ClientManager {
	//当前在线用户
	public static QqList onLineUserList=null;

	public static QqList getOnLineUserList() {
		return onLineUserList;
	}

	public static void setOnLineUserList(QqList onLineUserList) {
		ClientManager.onLineUserList = onLineUserList;
	}
}
