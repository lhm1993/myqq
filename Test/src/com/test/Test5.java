package com.test;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.*;

public class Test5 extends JFrame implements ActionListener{
	
	JPanel jp;
	JComboBox<String> jcb;
	JButton jb;
	public Test5(){
		jb=new JButton("shuru");
		jb.addActionListener(this);
		Vector v=new Vector<>();
		String []s={"111111","2","3","4","5"};
		for(int i=0;i<s.length;i++){
			v.add(s[i]);
		}
		jcb=new JComboBox<String>(v);
		jcb.setEditable(true);
		//jcb.setPopupVisible(false);
		jcb.getEditor().setItem("");
		jcb.setVisible(true);
		jp=new JPanel();
		jp.add(jcb);
		jp.add(jb);
		this.add(jp);
		this.setSize(400,300);
		this.setTitle("QQ��¼����");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setVisible(true);	
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Test5();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if(arg0.getSource()==jb){
			System.out.println(jcb.getEditor().getItem());
		}
	}

}
