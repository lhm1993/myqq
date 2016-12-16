/**
 * function:��ʾ��ǰ�û��ĺ����б�
 * ��һ���������棬��ʾ
 */
package com.qq.client.view;

import com.qq.client.model.*;
import com.qq.common.*;
import com.qq.tools.GetMessageFromServer;
import com.qq.tools.InsertOffLineMessageIntoMyMessage;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.net.Socket;
import java.sql.PreparedStatement;
import java.util.*;
import javax.swing.*;

public class QqList extends JFrame implements ActionListener,Cloneable{

	JPanel jp_welcome,jp_friends,jp_strangers,jp_blacklist;
	JPanel jp_showFirends,jp_showStrangers,jp_showBlackList;
	JScrollPane jsp_showList;
	JButton jb_friends,jb_strangers,jb_blacklist;
	boolean isShowFriends,isShowStrangers,isShowBlack,isWelcome;
	User user;
	Socket s;
	Message m;
	//���캯�������ݴ��������û���ţ���ʼ����
	public QqList(Message m,Socket s){
		//Message�а����û�����Ϣ
		this.m=m;
		this.s=s;
		this.user=this.m.getUser();
		//����ǰ��¼���û����ݽ���
		
		//�����ӷ������������ݵ��߳�
		GetMessageFromServer gmfs=new GetMessageFromServer(this.s);
		Thread t_gmfs=new Thread(gmfs);
		t_gmfs.start();
		
		//��������ϢOffLineMessage д�뵽MyMessage��
		InsertOffLineMessageIntoMyMessage ifimim=new InsertOffLineMessageIntoMyMessage(this.m);
		ifimim.startGetMessage();
		
		//��ʾ��ӭ����
		this.showWelComePage();
		
		//��������
		this.setTitle(user.getNickName()+" --���� ");
		Image image=Toolkit.getDefaultToolkit().getImage(user.getImagePath());
		this.setIconImage(image);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setSize(200,500);
		this.setVisible(true);
	}
	
	//��ʾ��ӭ����
	private void showWelComePage(){
		//�����ʼ��
		this.removeUsingList();
		jb_friends=new JButton("�����б�");
		jb_friends.addActionListener(this);
		jb_strangers=new JButton("İ����");
		jb_strangers.addActionListener(this);
		jb_blacklist=new JButton("������");
		jb_blacklist.addActionListener(this);
				
		jp_welcome=new JPanel();
		jp_welcome.setLayout(null);
		jb_friends.setBounds(0,0,200,30);
		jb_strangers.setBounds(0,30,200,30);
		jb_blacklist.setBounds(0,60,200,30);
				
		jp_welcome.add(jb_friends);
		jp_welcome.add(jb_strangers);
		jp_welcome.add(jb_blacklist);
		this.add(jp_welcome);
		this.isWelcome=true;
		this.setVisible(true);
	}
		
	//��ʼ�������б�
	private void initFriendsList(){
		jp_showFirends=new JPanel();
		jp_friends=new JPanel();
		jp_friends.setLayout(null);
		JLabel []jl=new JLabel[user.getFrientdsList().size()];
		//�������ѵ� HashMap friendsList
		//��������,�����û��ĺ����Լ����ӷ�������������Message m��user�ĺ����б��ж�����
		Iterator iter=user.getFrientdsList().keySet().iterator(); 
		Set s=user.getFrientdsList().keySet();
		int i=0;
		while(iter.hasNext()){
			PersonAttribute pa=(PersonAttribute) user.getFrientdsList().get(iter.next());
			//System.out.println("UserId="+pa.getUserId()+" NickName="+pa.getNickName()+"ImagePath = "+pa.getImagePath());
			jl[i]=new JLabel(pa.getNickName(),new ImageIcon(pa.getImagePath()),JLabel.LEFT);
			//ÿһ����ǩ��λ��˳��������
			jl[i].setBounds(5,5+i*35,200,30);
			this.addMouseListenerToLabels(jl[i]);
			jp_friends.add(jl[i++]);
		}
		
		jsp_showList=new JScrollPane(jp_friends);
		//������ʾ�����б��Ľ��沼��ģʽ
		jp_showFirends.setLayout(null);
		jb_friends.setBounds(0,0,200,30);
		jsp_showList.setBounds(0,30,200,300);
		jb_strangers.setBounds(0,330,200,30);
		jb_blacklist.setBounds(0,360,200,30);
		//��ý����������
		jp_showFirends.add(jb_friends);
		jp_showFirends.add(jsp_showList);
		jp_showFirends.add(jb_strangers);
		jp_showFirends.add(jb_blacklist);
		//����ʾ���ѵĽ���ŵ�������
		this.add(jp_showFirends);
	} 
	
	//��ʼ��İ���б�
	private void intitStangersList(){
		jp_showStrangers=new JPanel();
		jp_strangers=new JPanel();
		jp_strangers.setLayout(null);
		JLabel []jl=new JLabel[user.getStrangersList().size()];

		Iterator<Map.Entry<String,PersonAttribute>> iter=user.getStrangersList().entrySet().iterator(); 
		int i=0;
		while(iter.hasNext()){
			Map.Entry<String, PersonAttribute> entry=iter.next();
			PersonAttribute pa=(PersonAttribute)(entry.getValue());
			//System.out.println("UserId="+pa.getUserId()+" NickName="+pa.getNickName()+"ImagePath = "+pa.getImagePath());
			jl[i]=new JLabel(pa.getNickName(),new ImageIcon(pa.getImagePath()),JLabel.LEFT);
			jl[i].setBounds(5,5+i*35,200,30);
			this.addMouseListenerToLabels(jl[i]);
			jp_strangers.add(jl[i++]);
		}
		
		jsp_showList=new JScrollPane(jp_strangers);
		//������ʾStrangers�б��Ľ��沼��ģʽ
		jp_showStrangers.setLayout(null);
		jb_friends.setBounds(0,0,200,30);
		jb_strangers.setBounds(0,30,200,30);
		jsp_showList.setBounds(0,60,200,300);
		jb_blacklist.setBounds(0,360,200,30);
		//��ý����������
		jp_showStrangers.add(jb_friends);
		jp_showStrangers.add(jsp_showList);
		jp_showStrangers.add(jb_strangers);
		jp_showStrangers.add(jb_blacklist);
		//����ʾStrangers�Ľ���ŵ�������
		this.add(jp_showStrangers);
	}
	
	//��ʼ��������
	private void intitBlackList(){
		jp_showBlackList=new JPanel();
		jp_blacklist=new JPanel();
		jp_blacklist.setLayout(null);
		//��ʼ�������б�
		JLabel []jl=new JLabel[user.getBlackList().size()];
		
		Iterator<Map.Entry<String,PersonAttribute>> iter=user.getBlackList().entrySet().iterator(); 
		int i=0;
		while(iter.hasNext()){
			Map.Entry<String, PersonAttribute> entry=iter.next();
			PersonAttribute pa=(PersonAttribute)(entry.getValue());
			//System.out.println("UserId="+pa.getUserId()+" NickName="+pa.getNickName()+"ImagePath = "+pa.getImagePath());
			jl[i]=new JLabel(pa.getNickName(),new ImageIcon(pa.getImagePath()),JLabel.LEFT);
			jl[i].setBounds(5,5+i*35,200,30);
			this.addMouseListenerToLabels(jl[i]);
			jp_blacklist.add(jl[i++]);
		}
		jsp_showList=new JScrollPane(jp_blacklist);
		//������ʾBlackList�б��Ľ��沼��ģʽ
		jp_showBlackList.setLayout(null);
		jb_friends.setBounds(0,0,200,30);
		jb_strangers.setBounds(0,30,200,30);
		jb_blacklist.setBounds(0,60,200,30);
		jsp_showList.setBounds(0,90,200,300);
		//��ý����������
		jp_showBlackList.add(jb_friends);
		jp_showBlackList.add(jsp_showList);
		jp_showBlackList.add(jb_strangers);
		jp_showBlackList.add(jb_blacklist);
		//����ʾBlackList�Ľ���ŵ�������
		this.add(jp_showBlackList);		
	}
	
	
	//�Ƴ���ǰ��ʹ�õ��б�
	private void removeUsingList(){
		if(isWelcome){
			this.remove(jp_welcome);
			isWelcome=false;
		}
		if(isShowFriends){
			this.remove(jp_showFirends);
			isShowFriends=false;
		}
		if(isShowStrangers){
			this.remove(jp_showStrangers);
			isShowStrangers=false;
		}
		if(isShowBlack){
			this.remove(jp_showBlackList);
			isShowBlack=false;
		}
		this.setVisible(true);
	}
	
	//��ʾ�����б�
	private void showFriendsList(){
		this.removeUsingList();
		this.initFriendsList();
		this.isShowFriends=true;
		this.setVisible(true);
	}
	
	//��ʾİ�����б�
	private void showStrangersList(){
		this.removeUsingList();
		this.intitStangersList();
		this.isShowStrangers=true;
		this.setVisible(true);
		
	}
	
	//��ʾ�������б�
	private void showBlackList(){
		this.removeUsingList();
		this.intitBlackList();
		this.isShowBlack=true;
		this.setVisible(true);
	}
	
	//��ÿһ����ǩ�����������¼�
	public void addMouseListenerToLabels(final JLabel jl){
		jl.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				if(arg0.getClickCount()==2){
					//����һ���������
					final String nickName=jl.getText();
					QqChat qqChat=null;
					PersonAttribute pa=null;
					//�ж���Ա����
					if(isShowFriends){
						pa=(PersonAttribute)user.getFrientdsList().get(nickName);
					}else if(isShowStrangers){
						pa=(PersonAttribute)user.getStrangersList().get(nickName);				
					}else if(isShowBlack){
						pa=(PersonAttribute)user.getBlackList().get(nickName);
					}
					//���ݵ�ǰ������ʾ���б�ȷ��ʹ�õ�������Щ�����ѵ����촰��
					//�����ǰ���촰�ڲ����ڵĻ����ʹ���һ��������Ͳ�����Ӧ
	
					if((new ChatWindowManager()).getQqChatWindow(pa.getUserId())==null){
				
						//���ݲ�ͬ����Ա���ͣ��������촰��
						qqChat=new QqChat(user,pa,s);
						//�����ڷ��봰�ڼ����С��������洢��ʽ <id,qqchat>
						(new ChatWindowManager()).addQqChatWindowsToManager(pa.getUserId(), qqChat);
						//�����ڹرյ�ʱ�򣬾ͽ����ڴ���������� ChatWindowManager �����������촰���Ƴ��������
						final PersonAttribute paCloseWindow=pa;
						
						qqChat.addWindowListener(new WindowAdapter() {
							
							@Override
							public void windowClosing(WindowEvent e) {
								// TODO Auto-generated method stub
								boolean b=false;
								b=(new ChatWindowManager()).removChatWindowFromManager(paCloseWindow.getUserId());
								if(b){
									//�ر����촰�ڵ�ʱ�򣬽����û���������Ϣ���浽�����ļ���
									ChatRecordManager crm=new ChatRecordManager(user,paCloseWindow);
									crm.startWriteRecord();
								}else{
									JOptionPane.showMessageDialog(null, "���촰�ڲ����ڣ�");
								}
								
							}
						});
		
					}
				}
			}
			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
				JLabel jl= (JLabel)arg0.getSource();
				jl.setForeground(Color.CYAN);
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				JLabel jl= (JLabel)arg0.getSource();
				jl.setForeground(Color.BLACK);
			}
		});
	}
	
	//��������
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if(arg0.getSource()==jb_friends){
			if(isShowFriends){
				this.showWelComePage();
			}else{
				this.showFriendsList();
			}
			
		}
		if(arg0.getSource()==jb_strangers){
			if(isShowStrangers){
				this.showWelComePage();
			}else{
				this.showStrangersList();
			}
		}
		if(arg0.getSource()==jb_blacklist){
			if(isShowBlack){
				this.showWelComePage();
			}else{
				this.showBlackList();
			}
		}
		
	}
	
	//�������б�����Ϊ�ɸ��ƣ���ȸ��ƣ�ǳ���ƣ�
	@Override
	public Object clone(){
		QqList qqList=null;
		try {
			qqList=(QqList)super.clone();
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return qqList;
	}
}