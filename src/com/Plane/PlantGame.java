package com.Plane;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import com.Plane.*;
public class PlantGame extends JFrame {
	static int WIDTH=500;
	static int HEIGHT=720;
	static int count=0;
	static int Enemynum=0;
	static double speed;//�ٶ�,Ĭ��2
	static int Model;//ģʽ 1-�򵥣�Ĭ�ϣ�,2-��ͨ,3-����
	static int nums=10;//�л�������Ĭ��)
	static int boss=100;//boss����ֵ
	static int MyHp=100;//�ҷ�����ֵ
	static int state=1;//״̬ 0-��ͣ 1-���� 2-ʤ�� 3-ʧ��
	static int flushshell=10;//�ӵ�ˢ��
	static int flushenemy=20;//����ˢ��
	static int flushbossshell=15;//boss�ڵ�ˢ��
	static int shellgj=5;//�ӵ�������
	static int enemygj=2;//���˹�����
	Image screen=null;
	private Image plane=Pictures.plain2;//��ǰ�ɻ�
	private Image PlayGameBg=Pictures.PlayBg;//����ͼƬ
	private Image shellBg=Pictures.shell3;
	private Image enemyBg=Pictures.enemy;
	private Image BossBg=Pictures.boss;
	private Image BossShellBg=Pictures.bossShell;
	private BgObj bgobj=new BgObj(PlayGameBg, 0, -1400, 1, WIDTH, HEIGHT,this);
	private PlainObj plainobj=new PlainObj(plane,WIDTH/2-70,620,0,770,70,this);
//	private BossObj bossobj=new BossObj(BossBg,0,0,speed,100,110,this);
	private BossObj bossobj=null;
	private ArrayList<shellobj> shells=new ArrayList<>();//�ҷ��ڵ�
	private ArrayList<EnemyObj> enemys=new ArrayList<>();
	private ArrayList<GameObj> delObj=new ArrayList<>();//�����ײ�Ķ���
	private ArrayList<BossShellObj> bossShells=new ArrayList<>();
	private ArrayList<GameObj> Objs=new ArrayList<>();//���boss���ӵ����л�
	private ArrayList<shellobj> MyObjs=new ArrayList<>();//����ҷ��ɻ����ҷ��ڵ�
	public void launch() {
		repaint();
		this.setTitle("�ɻ���ս");
		this.setSize(WIDTH,HEIGHT);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
//				super.keyPressed(e);
				if(e.getKeyCode()==32) {
					if(state==0) {
						state=1;
					}else if(state==1) {
						state=0;
					}
				}
			}
		});
		while(true) {
			if(state!=-1) {
				repaint();
			}
			if(state==-1) {
//				this.getDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				System.exit(0);
			}
			if(state==1) {
				createObj();
			}
			try {
				Thread.sleep(25);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public void paint(Graphics g) {
		if(screen==null) {
			screen=this.createImage(WIDTH,HEIGHT);
		}
		Graphics gImage=screen.getGraphics();
		gImage.fillRect(0, 0, WIDTH, HEIGHT);
			
			gImage.setColor(Color.green);
			gImage.setFont(new Font("����",Font.BOLD,15));
			String ss="�÷�:"+Enemynum;
		if(state==1) {
			bgobj.paintSelf(gImage);
			plainobj.paintSelf(gImage);
			String til="HP:"+boss;
			Iterator it=Objs.iterator();
			while(it.hasNext()) {
	//			System.out.println(it.next().getClass());
				GameObj obj=(GameObj) it.next();
				if( obj instanceof EnemyObj) {
					EnemyObj enemy=(EnemyObj) obj;
					enemy.paintSelf(gImage);
					if(enemy.getY()>=720) {
						delObj.add(enemy);
					}
				}
				if(obj instanceof BossShellObj && bossobj!=null) {
					BossShellObj bossshell=(BossShellObj) obj;
					bossshell.paintSelf(gImage);
					if(bossshell.getY()>=720) {
						delObj.add(bossshell);
					}
				}
			}//
			for(int i=0;i<MyObjs.size();i++) {
					shellobj shell=(shellobj) MyObjs.get(i);
					shell.paintSelf(gImage);
					if(shell.getY()<=0) {
						delObj.add(MyObjs.get(i));
					}
					
					if(bossobj!=null&&shell.getX()>=bossobj.getX()&&shell.getX()<=bossobj.getX()+120
							&&shell.getY()<=bossobj.getY()+100) {
						this.boss-=shellgj;
						try {
							Thread.sleep(20);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						delObj.add(shell);
						if(this.boss<=0) {
							bossobj=null;
//							state=2;//ʤ��
//							win();
							state=2;
						}
					}
					for(GameObj obj:Objs) {
						if( obj instanceof EnemyObj) {
							EnemyObj enemy=(EnemyObj) obj;
							if(shell.getX()>=enemy.getX()-10&&shell.getX()<=enemy.getX()+76
									&&shell.getY()<=enemy.getY()+55) {
								delObj.add(enemy);
								delObj.add(shell);
								Enemynum++;
								nums--;
							}
							if(enemy.getX()>=plainobj.getX()-70&&enemy.getX()<=plainobj.getX()+70&&
									enemy.getY()+50>=plainobj.getY()) {
								this.MyHp-=enemygj;
								
//								System.out.println(this.MyHp);
								delObj.add(enemy);
	//							System.out.println(this.MyHp);
							}
						}
						if(obj instanceof BossShellObj) {
							BossShellObj bossshell=(BossShellObj) obj;
							if(shell.getX()>=bossshell.getX()-10&&shell.getX()<=bossshell.getX()+10
									&&shell.getY()<=bossshell.getY()+65) {
								delObj.add(bossshell);
								delObj.add(shell);
							}
							if(bossshell.getX()>=plainobj.getX()&&bossshell.getX()<=plainobj.getX()+100&&
									bossshell.getY()+30>=plainobj.getY()) {
								this.MyHp-=shellgj;
								delObj.add(bossshell);
	//							delObj.add(shell);
	//							System.out.println(this.MyHp);
							}
						}
						for(int k=0;k<delObj.size();k++) {
							if(delObj.get(k) instanceof shellobj) {
								MyObjs.remove(delObj.get(k));
							}
							if(delObj.get(k) instanceof EnemyObj) {
				//				count++;
								
								Objs.remove(delObj.get(k));
							}
							if(delObj.get(k) instanceof BossShellObj) {
								Objs.remove(delObj.get(k));
							}
						}//��Ҫɾ���Ķ���
					}
			}
			for(int k=0;k<delObj.size();k++) {
				if(delObj.get(k) instanceof shellobj) {
					MyObjs.remove(delObj.get(k));
				}
				if(delObj.get(k) instanceof EnemyObj) {
	//				count++;
					
					Objs.remove(delObj.get(k));
				}
				if(delObj.get(k) instanceof BossShellObj) {
					Objs.remove(delObj.get(k));
				}
			}//��Ҫɾ���Ķ���
			if(nums<=10&&bossobj==null) {
				bossobj=new BossObj(BossBg,0,0,speed+2,100,110,this);
			}
			if(bossobj!=null) {
				bossobj.paintSelf(gImage);
			}
			if(MyHp<=0) {
//				fail();
				state=3;//ʧ��
			}
			gImage.drawString(ss,10,100);
			gImage.setColor(Color.red);
			gImage.drawString(til, 430, 100);
			String MyTil="HP:"+MyHp;
			gImage.setColor(Color.green);
			gImage.drawString(MyTil, 10, 700);
			if(bossobj!=null) {
				gImage.setColor(Color.red);
				gImage.fillRect(50, 40, boss*4, 10);
			}
			gImage.setColor(Color.green);
			gImage.fillRect(50, 700, MyHp*4, 10);
		}
		if(state==2) {
			System.out.println("ʤ��");
			int y0=(int) plainobj.getY();
			bgobj.paintSelf(gImage);
			if(y0>=0) {
				y0-=3;
				plainobj.setY(y0);
				plainobj.paintSelf(gImage);
			}
				if(y0<0) {
					plainobj.setY(-200);
					plainobj.setX(-200);
					state=-1;
				}
				gImage.drawImage(Pictures.WinBg,60,y0+400,null);
			}
		if(state==3) {
			System.out.println("ʧ��");
			int y0=(int) bossobj.getY();
			bgobj.paintSelf(gImage);
			if(y0>=0) {
				y0+=3;
				bossobj.setY(y0);
				bossobj.paintSelf(gImage);
			}
				if(y0>=720) {
					bossobj.setY(-200);
					bossobj.setX(-200);
					state=-1;
				}
				gImage.drawImage(Pictures.failbg,60,y0-300,null);
		}
		g.drawImage(screen,0,0,null);
	}
	public void createObj() {
		if(count%flushshell==0) {
			shellobj shell=new shellobj(shellBg,(int)plainobj.getX()+45,(int)plainobj.getY(),speed+2,35,200,this);
//			shells.add(shell);
			MyObjs.add(shell);
		}//�ҷ��ڵ�
		int x=(int) (Math.random()*500);
		if(count%flushenemy==0&&nums>=0) {
			EnemyObj enemy=new EnemyObj(enemyBg,x,20,speed+2,70,70,this);
//			enemys.add(enemy);
			Objs.add(enemy);
		}//�л�
		if(count%flushbossshell==0&&bossobj!=null) {
			BossShellObj bossShell=new BossShellObj(BossShellBg,(int)bossobj.getX()+50,(int)bossobj.getY()+100,speed+2,20,20,this);
//			bossShells.add(bossShell);
			Objs.add(bossShell);
		}//boss���ڵ�
		count++;//
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	}

}
