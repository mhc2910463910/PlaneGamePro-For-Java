package com.Plane;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GameObj {
	protected Image img;
	protected int x;
	protected int y;
	protected double speed;
	protected int Width;
	protected int Height;
	protected PlantGame frame;
	public GameObj(Image img, int x, int y, double speed, int width, int height,PlantGame frame) {
		this.img = img;
		this.x = x;
		this.y = y;
		this.speed = speed;
		Width = width;
		Height = height;
		this.frame=frame;
	}
	public Image getImg() {
		return img;
	}
	public void setImg(Image img) {
		this.img = img;
	}
	public double getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public double getSpeed() {
		return speed;
	}
	public void setSpeed(double speed) {
		this.speed = speed;
	}
	public void paintSelf(Graphics gImage) {
		gImage.drawImage(img,x,y,null);
	}
	public Rectangle getRect() {
		return new Rectangle(x,y,Width,Height);
	}
}
//父类
class BgObj extends GameObj{
	
	public BgObj(Image img, int x, int y, double speed, int width, int height, PlantGame frame) {
		super(img, x, y, speed, width, height, frame);
		// TODO Auto-generated constructor stub
	}

	public void paintSelf(Graphics gImage) {
		super.paintSelf(gImage);
		this.y++;
		if(this.y>=0) {
			this.y=-1400;
		}
	}
}
//背景图片
class PlainObj extends GameObj{
	public PlainObj(Image img, int x, int y, double speed, int width, int height, PlantGame frame) {
		super(img, x, y, speed, width, height, frame);
		// TODO Auto-generated constructor stub
	}

	@Override
	public double getX() {
		// TODO Auto-generated method stub
		return super.getX();
	}

	@Override
	public void setX(int x) {
		// TODO Auto-generated method stub
		super.setX(x);
	}

	@Override
	public double getY() {
		// TODO Auto-generated method stub
		return super.getY();
	}
	@Override
	public void setY(int y) {
		// TODO Auto-generated method stub
		super.setY(y);
	}

	public void paintSelf(Graphics gImage) {
		super.paintSelf(gImage);
			this.frame.addMouseMotionListener(new MouseAdapter() {
				@Override
				public void mouseMoved(MouseEvent e) {
					// TODO Auto-generated method stub
//					super.x=e.getX()-40;
//					super.y=e.getY()-50;
					if(PlantGame.state==1) {
						setX(e.getX()-65);
						setY(e.getY()-50);
						if(x<-50) {
							x=-50;
						}
						if(x>420) {
							x=420;
						}
					}
				}
			});
	}
}
//飞机对象
class shellobj extends GameObj{

	public shellobj(Image img, int d, int e, double speed, int width, int height, PlantGame frame) {
		super(img, d, e, speed, width, height, frame);
		// TODO Auto-generated constructor stub
	}
	public void paintSelf(Graphics gImage) {
		super.paintSelf(gImage);
		y-=speed;
	}
}
//子弹对象
class EnemyObj extends GameObj{

	public EnemyObj(Image img, int x, int y, double speed, int width, int height, PlantGame frame) {
		super(img, x, y, speed, width, height, frame);
		// TODO Auto-generated constructor stub
	}
	public void paintSelf(Graphics gImage) {
		super.paintSelf(gImage);
		y+=speed;
	}
}
//敌机
class BossObj extends GameObj{
	private short flag=1;
	public BossObj(Image img, int x, int y, double speed, int width, int height, PlantGame frame) {
		super(img, x, y, speed, width, height, frame);
		// TODO Auto-generated constructor stub
	}
	public void paintSelf(Graphics gImage) {
		super.paintSelf(gImage);
		if(this.flag==1) {
			this.x+=speed;
			if(this.x>=500-100) {
				flag=0;
			}
		}
		if(this.flag==0) {
			this.x-=speed;
			if(this.x<=0) {
				flag=1;
			}
		}
	}
}
//BOSS
class BossShellObj extends GameObj{

	public BossShellObj(Image img, int x, int y, double speed, int width, int height, PlantGame frame) {
		super(img, x, y, speed, width, height, frame);
		// TODO Auto-generated constructor stub
	}
	public void paintSelf(Graphics gImage) {
		super.paintSelf(gImage);
		this.y+=speed;
	}
}