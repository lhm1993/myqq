/**
 * function:�ͻ����������߹���
 */
package com.qq.client.model;

import com.qq.client.view.QqList;

public class ClientManager {
	//��ǰ�����û�
	public static QqList onLineUserList=null;

	public static QqList getOnLineUserList() {
		return onLineUserList;
	}

	public static void setOnLineUserList(QqList onLineUserList) {
		ClientManager.onLineUserList = onLineUserList;
	}
}
