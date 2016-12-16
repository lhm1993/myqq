package com.test;


import java.awt.*;
import javax.swing.*;

public class Test2 extends JFrame{
	
	JPanel jp1,jp2;
	JScrollPane jsp;
	JButton jb,jb1;
	
	public Test2(){
		jb=new JButton("test");
		jb1=new JButton("jb1");
		jp1=new JPanel();
		jp2=new JPanel();
		jp1.add(jb);
		jsp=new JScrollPane(jp1);
		jp2.add(jsp);
		jp2.add(jb1);
		this.add(jp2);
		this.setSize(400,300);
		this.setVisible(true);
	}
	
	public static void main(String []args){
		new Test2();
	}
}
