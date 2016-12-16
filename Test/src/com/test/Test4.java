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
		//建立文件路径
		try {
			//第二参数 true表示这个文件是以追加的方式添加内容的
			//FileWirter可以创建没有的文件，但是不能创建没有的文件夹，所以问价夹的检测还是要有的
			File filePath=new File("E:\\lianxi\\qqTest\\2");
			if(filePath.exists()){
				folder=new FileWriter("E:\\lianxi\\qqTest\\1\\2.txt",true);
				//File file=new File("E:\\lianxi\\qqTest\\1\\1.txt");
				osr=new BufferedWriter(folder) ;
				osr.write("我是谁\r\n");
			}else{
				filePath.mkdir();
				folder=new FileWriter("E:\\lianxi\\qqTest\\2\\2.txt",true);
				//File file=new File("E:\\lianxi\\qqTest\\1\\1.txt");
				osr=new BufferedWriter(folder) ;
				osr.write("我是谁\r\n");
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
