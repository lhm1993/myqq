package com.test;

import java.io.*;

public class Test4 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BufferedWriter osr=null;
		FileWriter folder=null;
		//�����ļ�·��
		try {
			//�ڶ����� true��ʾ����ļ�����׷�ӵķ�ʽ������ݵ�
			//FileWirter���Դ���û�е��ļ������ǲ��ܴ���û�е��ļ��У������ʼۼеļ�⻹��Ҫ�е�
			File filePath=new File("E:\\lianxi\\qqTest\\2");
			if(filePath.exists()){
				folder=new FileWriter("E:\\lianxi\\qqTest\\1\\2.txt",true);
				//File file=new File("E:\\lianxi\\qqTest\\1\\1.txt");
				osr=new BufferedWriter(folder) ;
				osr.write("����˭\r\n");
			}else{
				filePath.mkdir();
				folder=new FileWriter("E:\\lianxi\\qqTest\\2\\2.txt",true);
				//File file=new File("E:\\lianxi\\qqTest\\1\\1.txt");
				osr=new BufferedWriter(folder) ;
				osr.write("����˭\r\n");
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			try {
				if(osr!=null){
					osr.close();
					folder.close();
				}
			} catch (Exception e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
			
		}
		
		
	}

}
