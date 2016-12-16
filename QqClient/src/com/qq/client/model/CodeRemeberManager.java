/**
 * 
 * function:管理客户端记住密码这个功能模块
 * 当保存密码这个选项被选中
 * 	点击登录 以后，如果这个账户密码可以登录，就将这个账户密码保存在本地文件 .//client//CodeRember.txt中
 * 	
 * 当客户端被打开要去初始化输入框的时候，就会调用读取文件的方法，如果文件中存在已经保存了该用户密码，也就是会有下拉框出现的情况
 * 如果在登录的时候用户取消勾选了保存密码的选项，如果这个用户已经保存过，就从文件中将该用户删除掉，如果该用户没有被保存，就直接登录，不保存当前登录用户的信息
 * ！！！真正保存密码暂存信息的是在服务器端的缓存里面的，并且服务器是一直工作的（一直在线，监听端口），有自己的缓存
 * 
 */
package com.qq.client.model;

import java.util.HashMap;
import java.io.*;

import java.util.*;

public class CodeRemeberManager {
	
	//暂存保存的账号密码
	private HashMap<String,String> user_code=new HashMap<String,String>();

	public HashMap<String, String> getUser_code() {
		return user_code;
	}

	//从文件中读取信息
	public boolean readUser_Code(){
		boolean b=false;
		File file=new File("E:\\lianxi\\qqTest\\QqClient\\date\\User_Code.txt");
		//如果文件不存在，就证明没有记录保存，直接返回
		if(!file.exists()){
			return b;
		}
		FileReader fr=null;
		BufferedReader br=null;
		try {
			fr=new FileReader(file);
			br=new BufferedReader(fr);
			String s=null;
			while((s=br.readLine())!=null){
				String []s1=s.split(" ");
				user_code.put(s1[0], s1[1]);
			}
			if(user_code.size()!=0){
				b=true;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			b=false;
		}finally{
			if(br!=null){
				try {
					br.close();
					fr.close();
				} catch (Exception e2) {
					// TODO: handle exception
					e2.printStackTrace();
				}
			}
		}
		return b;
	}
	//写入文件
	private void writeToFile(File file,String userId_pass){
		FileWriter fw=null;
		BufferedWriter bw=null;
		try {
			fw=new FileWriter(file,true);
			bw=new BufferedWriter(fw);
			bw.write(userId_pass+"\r\n");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			try {
				if(bw!=null){
					bw.close();
					fw.close();
				}
			} catch (Exception e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
		}
	}
	
	//将账号密码写入文件
	public void writeUser_Code(String userId_pass){
		File file=new File("E:\\lianxi\\qqTest\\QqClient\\date\\User_Code.txt");
		//先从文件中读出所有记录，然后判断该条记录是否已经存在，如果存在，就不写入，否则执行写入
		if(this.readUser_Code()){
			String []s=userId_pass.split(" ");
			String p=user_code.get(s[0]);
			System.out.println(s[0]+"  "+p);
			if(p==null){
				//如果该条记录不存在
				this.writeToFile(file, userId_pass);
			}
		}else{//文件为空
			this.writeToFile(file, userId_pass);
		}
		
	}
	
	//移除一条账号密码保存记录
	public boolean removeOneUser_Code(String userId){
		boolean b=false;
		//先执行读操作，将所有记录读到user_code中
		boolean isExist=this.readUser_Code();
		//然后删除,如果在记录文件中有记录存在
		if(isExist){
			user_code.remove(userId);
			File file=new File("E:\\lianxi\\qqTest\\QqClient\\date\\User_Code.txt");
			file.delete();
			FileWriter fw=null;
			BufferedWriter bw=null;
			try {
				fw=new FileWriter(file,true);
				bw=new BufferedWriter(fw);
				Iterator it=user_code.keySet().iterator();
				while(it.hasNext()){
					String u=(String)it.next();
					String p=user_code.get(u);
					bw.write(u+" "+p+"\r\n");
				}
				b=true;
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				b=false;
			}finally{
				try {
					if(bw!=null){
						bw.close();
						fw.close();
					}
				} catch (Exception e2) {
					// TODO: handle exception
					e2.printStackTrace();
					b=false;
				}
			}
		}	
		return b ;
	}
}
