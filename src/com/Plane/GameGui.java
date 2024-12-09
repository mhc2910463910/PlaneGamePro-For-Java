package com.Plane;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
public class GameGui extends JFrame {
	private Thread thread;
	public GameGui(){
		this.setBounds(300,400,PlantGame.WIDTH,PlantGame.HEIGHT);
		this.MakeMenu();
		this.setVisible(true);
	}
	public void MakeMenu() {
		JMenuBar menu=new JMenuBar();
		//菜单栏
		this.setJMenuBar(menu);
		//置顶
		JMenu game=new JMenu("游戏");
		//游戏菜单
		JMenuItem play=new JMenuItem("开始");
		//开始游戏
		play.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
//				System.out.println("模式"+PlantGame.Model+"\n速度"+PlantGame.speed+"\n敌机数量"+PlantGame.nums+"\nboss"+PlantGame.boss);
//				System.out.println("开始游戏");
				thread=new Thread(new Runnable() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
							// TODO Auto-generated method stub
						PlantGame game=new PlantGame();
						game.launch();
					}
				});
				thread.start();
			}
		});
		JMenuItem exit=new JMenuItem("退出");
		//退出游戏
		exit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.exit(0);
			}
			
		});
		game.add(play);
		game.addSeparator();
		game.add(exit);
		menu.add(game);
		
		JMenu setting=new JMenu("设置");
		//游戏设置
		JMenu model=new JMenu("游戏难度");
		ButtonGroup group=new ButtonGroup();
		JRadioButtonMenuItem but1=new JRadioButtonMenuItem("简单");
		but1.setSelected(true);
		but1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				PlantGame.speed=2;
				PlantGame.flushshell=35;
				PlantGame.flushenemy=40;
				PlantGame.flushbossshell=50;
				PlantGame.shellgj=3;
				PlantGame.enemygj=5;
				PlantGame.Model=1;
				PlantGame.nums=20;
				PlantGame.boss=100;
				Moeld_Dialog();
			}
		});
		JRadioButtonMenuItem but2=new JRadioButtonMenuItem("普通");
		ActionListener action2=new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				PlantGame.speed=5;
				PlantGame.flushshell=32;
				PlantGame.flushenemy=35;
				PlantGame.flushbossshell=40;
				PlantGame.shellgj=5;
				PlantGame.enemygj=7;
				PlantGame.Model=2;
				PlantGame.nums=30;
				PlantGame.boss=150;
				Moeld_Dialog();
			}
		};
		but2.addActionListener(action2);
		JRadioButtonMenuItem but3=new JRadioButtonMenuItem("困难");
		but3.addActionListener(event->{
			PlantGame.speed=7;
			PlantGame.flushshell=25;
			PlantGame.flushenemy=30;
			PlantGame.flushbossshell=25;
			PlantGame.shellgj=7;
			PlantGame.enemygj=8;
			PlantGame.Model=3;
			PlantGame.nums=40;
			PlantGame.boss=200;
			Moeld_Dialog();
		});
		group.add(but1);
		group.add(but2);
		group.add(but3);
		model.add(but1);
		model.add(but2);
		model.add(but3);
		setting.add(model);
		menu.add(setting);
		JMenu root=new JMenu("高级");
		JMenuItem set1=new JMenuItem("设置敌机数量");
		set1.addActionListener(new MyAction(1));
		JMenuItem set2=new JMenuItem("设置boss血量");
		set2.addActionListener(new MyAction(2));
		JMenuItem set3=new JMenuItem("设置速度");
		set3.addActionListener(new MyAction(3));
		root.add(set1);
		root.add(set2);
		root.add(set3);
		setting.add(root);
		JMenu help=new JMenu("帮助");
		//游戏帮助
		JMenuItem gy=new JMenuItem("关于");
		help.add(gy);
		menu.add(help);
	}
	public void Moeld_Dialog() {
			JDialog dialog=new JDialog();
			JLabel label1=new JLabel("模式:"+PlantGame.Model);
			JLabel label2=new JLabel("速度:"+PlantGame.speed);
			JLabel label3=new JLabel("敌机数量:"+PlantGame.nums);
			JLabel label4=new JLabel("boss:"+PlantGame.boss);
			label1.setFont(new Font("宋体",Font.BOLD,20));
			label2.setFont(new Font("宋体",Font.BOLD,20));
			label3.setFont(new Font("宋体",Font.BOLD,20));
			label4.setFont(new Font("宋体",Font.BOLD,20));
			dialog.setLayout(new GridLayout(4,1));
			dialog.setBounds(100,100,200,200);
			dialog.add(label1);
			dialog.add(label2);
			dialog.add(label3);
			dialog.add(label4);
			dialog.setVisible(true);
//			dialog.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public void playGame() {
//		this.setIconImage(Pictures.plain3);
		//1.把图片添加到标签里（把标签的大小设为和图片大小相同），把标签放在分层面板的最底层；
		ImageIcon bg=new ImageIcon(Pictures.playGameBg);
		JLabel label=new JLabel(bg);
		label.setSize(bg.getIconWidth(),bg.getIconHeight());
		this.getLayeredPane().add(label,new Integer(Integer.MIN_VALUE));
		//2.把窗口面板设为内容面板并设为透明、流动布局。
		JPanel pan=(JPanel)this.getContentPane();
		pan.setOpaque(false);
		pan.setLayout(new FlowLayout());
		//3.之后把组件和面板添加到窗口面板就可以；
		this.setSize(bg.getIconWidth(),bg.getIconHeight());
		this.setLocationRelativeTo(null);
//		ImageIcon bg1=new ImageIcon(Pictures.plain2);
//		JLabel label1=new JLabel(bg1);
//		this.setLayout(new GridLayout(3,3));
//		label1.setSize(bg1.getIconWidth(),bg1.getIconHeight());
////		label.setBounds(130,10,120,120);
//		this.add(new JLabel());
//		this.add(new JLabel());
//		this.add(new JLabel());
//		this.add(new JLabel());
//		this.add(label1);
//		JButton exit=new JButton();
//		exit.setIcon(Pictures.exit);
//		exit.setOpaque(false);
//		exit.setContentAreaFilled(false);
//		exit.setBorderPainted(false);
//		exit.addActionListener(event->{
//			System.exit(0);
//		});
//		this.add(exit);
//		this.add(new JLabel());
//		this.add(new JLabel());
//		JButton start=new JButton();
//		start.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				// TODO Auto-generated method stub
////				System.out.println("开始游戏");
//				thread=new Thread(new Runnable() {
//					@Override
//					public void run() {
//						// TODO Auto-generated method stub
//							// TODO Auto-generated method stub
//						PlantGame game=new PlantGame();
//						game.launch();
//					}
//				});
//				thread.start();
//			}
//		});
//		start.setIcon(Pictures.start);
//		start.setContentAreaFilled(false);
//		start.setBorderPainted(false);
//		this.add(start);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);	
	}
	public static void main(String[] args) {
		GameGui play=new GameGui();
		play.playGame();
//		play.ChangePlane();
	}
	
}
class MyAction implements ActionListener{
	private int flag;//1-设置敌机  2-设置boss血量  3-设置速度
	public MyAction(int flag) {
		this.flag=flag;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		switch(this.flag) {
		case 1:
			JDialog dialog=new JDialog();
			dialog.setLayout(new BorderLayout());
			JButton but=new JButton("确定");
			JLabel label=new JLabel("请输入敌机数量:");
			JTextField text=new JTextField();
			but.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					
					PlantGame.nums=Integer.parseInt(text.getText());
					text.setText("设置成功");
				}
				
			});
			dialog.add(label,BorderLayout.NORTH);
			dialog.add(text,BorderLayout.CENTER);
			dialog.add(but,BorderLayout.SOUTH);
			dialog.setSize(200,200);
			dialog.setVisible(true);
			break;
		case 2:
			JDialog dialog1=new JDialog();
			dialog1.setLayout(new BorderLayout());
			JButton but1=new JButton("确定");
			JLabel label1=new JLabel("请输入boss血量:");
			JTextField text1=new JTextField();
			but1.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					
					PlantGame.boss=Integer.parseInt(text1.getText());
					text1.setText("设置成功");
				}
				
			});
			dialog1.add(label1,BorderLayout.NORTH);
			dialog1.add(text1,BorderLayout.CENTER);
			dialog1.add(but1,BorderLayout.SOUTH);
			dialog1.setSize(200,200);
			dialog1.setVisible(true);
			break;
		case 3:
			JDialog dialog2=new JDialog();
			dialog2.setLayout(new BorderLayout());
			JButton but2=new JButton("确定");
			JLabel label2=new JLabel("请输入速度:");
			JTextField text2=new JTextField();
			but2.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					PlantGame.speed=Double.parseDouble(text2.getText());
					text2.setText("设置成功");
				}
			});
			dialog2.add(label2,BorderLayout.NORTH);
			dialog2.add(text2,BorderLayout.CENTER);
			dialog2.add(but2,BorderLayout.SOUTH);
			dialog2.setSize(200,200);
			dialog2.setVisible(true);
			break;
		}
	}
	
}
