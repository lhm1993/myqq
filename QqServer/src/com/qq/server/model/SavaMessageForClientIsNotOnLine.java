package com.qq.server.model;

import com.qq.common.Message;
import com.qq.server.tools.*;

public class SavaMessageForClientIsNotOnLine {
	
	public SavaMessageForClientIsNotOnLine(Message m){
		ServerDatabaseManual sdm=new ServerDatabaseManual();
		sdm.saveMessageForClientIsNotOnline(m);
		sdm.closeSerDatCon();
		//System.out.println("m.getMesType()="+m.getMesType()+"  "+m.getSender()+"¶Ô"+m.getGetter()+"Ëµ"+m.getContent());
	}
}
