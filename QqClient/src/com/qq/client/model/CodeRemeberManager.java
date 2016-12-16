/**
 * 
 * function:����ͻ��˼�ס�����������ģ��
 * �������������ѡ�ѡ��
 * 	�����¼ �Ժ��������˻�������Ե�¼���ͽ�����˻����뱣���ڱ����ļ� .//client//CodeRember.txt��
 * 	
 * ���ͻ��˱���Ҫȥ��ʼ��������ʱ�򣬾ͻ���ö�ȡ�ļ��ķ���������ļ��д����Ѿ������˸��û����룬Ҳ���ǻ�����������ֵ����
 * ����ڵ�¼��ʱ���û�ȡ����ѡ�˱��������ѡ��������û��Ѿ���������ʹ��ļ��н����û�ɾ������������û�û�б����棬��ֱ�ӵ�¼�������浱ǰ��¼�û�����Ϣ
 * �������������������ݴ���Ϣ�����ڷ������˵Ļ�������ģ����ҷ�������һֱ�����ģ�һֱ���ߣ������˿ڣ������Լ��Ļ���
 * 
 */
package com.qq.client.model;

import java.util.HashMap;
import java.io.*;

import java.util.*;

public class CodeRemeberManager {
	
	//�ݴ汣����˺�����
	private HashMap<String,String> user_code=new HashMap<String,String>();

	public HashMap<String, String> getUser_code() {
		return user_code;
	}

	//���ļ��ж�ȡ��Ϣ
	public boolean readUser_Code(){
		boolean b=false;
		File file=new File("E:\\lianxi\\qqTest\\QqClient\\date\\User_Code.txt");
		//����ļ������ڣ���֤��û�м�¼���棬ֱ�ӷ���
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
	//д���ļ�
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
	
	//���˺�����д���ļ�
	public void writeUser_Code(String userId_pass){
		File file=new File("E:\\lianxi\\qqTest\\QqClient\\date\\User_Code.txt");
		//�ȴ��ļ��ж������м�¼��Ȼ���жϸ�����¼�Ƿ��Ѿ����ڣ�������ڣ��Ͳ�д�룬����ִ��д��
		if(this.readUser_Code()){
			String []s=userId_pass.split(" ");
			String p=user_code.get(s[0]);
			System.out.println(s[0]+"  "+p);
			if(p==null){
				//���������¼������
				this.writeToFile(file, userId_pass);
			}
		}else{//�ļ�Ϊ��
			this.writeToFile(file, userId_pass);
		}
		
	}
	
	//�Ƴ�һ���˺����뱣���¼
	public boolean removeOneUser_Code(String userId){
		boolean b=false;
		//��ִ�ж������������м�¼����user_code��
		boolean isExist=this.readUser_Code();
		//Ȼ��ɾ��,����ڼ�¼�ļ����м�¼����
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
