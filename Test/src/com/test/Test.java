package com.test;

import java.util.*;

public class Test {
	public static void main(String []args){
		HashMap hm=new HashMap<Integer,String>();
		hm.put(1, "tudou");
		hm.put(2, "tudou1");
		hm.put(3, "tudou2");
		hm.put(4, "tudou3");
		Iterator it= hm.keySet().iterator();
		while(it.hasNext()){
			System.out.println(hm.get(it.next())+"****************");
		}
	}
}