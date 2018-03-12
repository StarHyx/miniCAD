package miniCAD;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JOptionPane;

/**
 * 图形目标类
 */
public class Target {
	//线条为0 矩形为1 圆形为2 text为3
	public static final int TARGET_TYPE_LINE = 0;
	public static final int TARGET_TYPE_RECT = 1;
	public static final int TARGET_TYPE_CIRCLE = 2;
	public static final int TARGET_TYPE_TEXT = 3;
	private int t_type = -1;

	public static final int POS_LEFT_TOP = 0;
	public static final int POS_RIGHT_TOP = 1;
	public static final int POS_LEFT_BOTTOM = 2;
	public static final int POS_RIGHT_BOTTOM = 3;
	private int pos_part = -1;
	//颜色
	private Color t_color;
	//矩形
	private Rectangle rect;
	//画笔
	private Ellipse2D ell;
	//线条
	private Line2D line;
	//内容
	private String text;
	//用来绘制文字
	private FontMetrics t_fontMetrics;
	//字体
	private Font t_font;
	//绘制2d图形画笔 边框
	private Rectangle2D t_bound;
	private Rectangle2D t_chosedBound = null;
	private Graphics2D t_g2d;
	private boolean t_isChosed = false;
	private Stroke dash = new BasicStroke(1.0f,BasicStroke.CAP_BUTT,BasicStroke.JOIN_ROUND,3.5f,new float[]{15,10,},0f);
	private BasicStroke basicStroke = new BasicStroke();

	private Point2D p1;

	//构造方法
	public Target(int type, Graphics2D g2d, Color color, double x1, double y1, double x2, double y2){
		System.out.println(x1 + " " + y1 + " " + " " + x2 + " " + y2);
		t_g2d = g2d;
		t_type = type;
		t_color = color;
		t_font = new Font("Serif", Font.PLAIN, 20);
		p1 = new Point2D.Double(x1, y1);

		switch(type){
			case TARGET_TYPE_LINE: { //直线
				line = new Line2D.Double(x1, y1, 0, 0);
				t_bound = line.getBounds2D();
				break;
			}
			case TARGET_TYPE_RECT:{//矩形
				rect = new Rectangle((int)x1, (int)y1, 0, 0);
				System.out.println(rect);
				t_bound = rect.getBounds2D();
				break;
			}
			case TARGET_TYPE_CIRCLE:{//圆形
				ell = new Ellipse2D.Double(x1, y1, 0, 0);
				t_bound = ell.getBounds2D();
				break;
			}
			case TARGET_TYPE_TEXT:{//文字
				text = JOptionPane.showInputDialog("Please input the text:");
				t_fontMetrics = g2d.getFontMetrics(t_font);
				t_bound = new Rectangle2D.Double(x1, y1, t_fontMetrics.stringWidth(text), t_fontMetrics.getAscent());
				break;
			}
			default:{
				break;
			}
		}
	}

	//绘制图形方法
	public void draw(Graphics g){
		Graphics2D tmpGraphics2d = t_g2d;
		t_g2d = (Graphics2D)g;
		t_g2d.setColor(t_color);
		t_g2d.setStroke(basicStroke);
		switch(t_type){
			case TARGET_TYPE_LINE: {
				t_g2d.draw(line);
				break;
			}
			case TARGET_TYPE_RECT:{
				t_g2d.draw(rect);
				break;
			}
			case TARGET_TYPE_CIRCLE:{
				t_g2d.draw(ell);
				break;
			}
			case TARGET_TYPE_TEXT:{
				t_g2d.setFont(t_font);
				t_fontMetrics = t_g2d.getFontMetrics(t_font);
				t_g2d.drawString(text, (int)(t_bound.getX()), (int)(t_bound.getY() + t_bound.getHeight()));
				break;
			}
			default:{
				break;
			}
		}
		if(t_isChosed){ // 显示外围虚线框，标示已选中
			t_g2d.setColor(Color.BLACK);
			t_g2d.setStroke(dash);  // 设为虚线
			Rectangle2D dash_bound = new Rectangle2D.Double(t_bound.getX()-2,t_bound.getY()-2,t_bound.getWidth()+4,t_bound.getHeight()+4);
			t_g2d.draw(dash_bound);
		}
		t_g2d = tmpGraphics2d;
	}

	//获得画笔 坐标
	public Point2D getP1(){
		return new Point2D.Double(p1.getX(), p1.getY());
	}

	public Target intersects(Point2D point2d){
		if(t_bound.contains(point2d)) return this;
		else return null;

	}

	//改变大小
	public void changeSize(Point2D endPoint){

		System.out.println("End ::::::" + endPoint);

		switch(this.pos_part){
		case POS_RIGHT_BOTTOM:{
			this.setFrameFromDiagonal(new Point2D.Double(t_chosedBound.getX(), t_chosedBound.getY()), endPoint);
			break;
		}
		case POS_RIGHT_TOP:{
			this.setFrameFromDiagonal(new Point2D.Double(t_chosedBound.getX(), t_chosedBound.getY() + t_chosedBound.getHeight()), endPoint);
			break;
		}
		case POS_LEFT_BOTTOM:{
			this.setFrameFromDiagonal(new Point2D.Double(t_chosedBound.getX()+ t_chosedBound.getWidth(), t_chosedBound.getY()), endPoint);
			break;
		}
		case POS_LEFT_TOP:{
			this.setFrameFromDiagonal(new Point2D.Double(t_chosedBound.getX()+ t_chosedBound.getWidth(), t_chosedBound.getY() + t_chosedBound.getHeight()), endPoint);
			break;
		}
		}

	}

	//
	public void pointInWhichPart(Point2D point2d){
		Point2D center = new Point2D.Double(t_bound.getCenterX(), t_bound.getCenterY());
		if(point2d.getX() > center.getX() && point2d.getY() > center.getY()){
			this.pos_part = POS_RIGHT_BOTTOM;
		}
		else if(point2d.getX() > center.getX() && point2d.getY() < center.getY()){
			this.pos_part = POS_RIGHT_TOP;
		}
		else if(point2d.getX() < center.getX() && point2d.getY() > center.getY()){
			this.pos_part = POS_LEFT_BOTTOM;
		}
		else if(point2d.getX() < center.getX() && point2d.getY() < center.getY()){
			this.pos_part = POS_LEFT_TOP;
		}
	}

	//关闭
	public void setChosed(Point2D startPoint){
		pointInWhichPart(startPoint);
		t_chosedBound = (Rectangle2D) this.getBound2D().clone();
		t_isChosed = true;
	}

	//不关闭
	public void setNotChosed(){
		pos_part = -1;
		t_chosedBound = null;
		t_isChosed = false;
	}

	//边框画笔
	public Rectangle2D getBound2D(){
		switch (t_type) {
		case TARGET_TYPE_LINE:
			return line.getBounds2D();
		case TARGET_TYPE_RECT:
			return rect.getBounds2D();
		case TARGET_TYPE_CIRCLE:
			return ell.getBounds2D();
		case TARGET_TYPE_TEXT:
			return this.t_bound;
		default:
			break;
		}
		return null;

	}

	public void setFrameFromDiagonal(Point2D p1, Point2D p2){
		switch(t_type){
		case TARGET_TYPE_LINE: {
			if(p1.equals(line.getP1()) || p2.equals(line.getP1()) || p1.equals(line.getP2()) || p2.equals(line.getP2())){
				line.setLine(p1, p2);
				t_bound = line.getBounds2D();
			}
			else{
				line.setLine(new Point2D.Double(p1.getX(), p2.getY()), new Point2D.Double(p2.getX(), p1.getY()));
				t_bound = line.getBounds2D();
			}
			break;
		}
		case TARGET_TYPE_RECT:{
			rect.setFrameFromDiagonal(p1, p2);
			t_bound = rect.getBounds2D();
			break;
		}
		case TARGET_TYPE_CIRCLE:{
			ell.setFrameFromDiagonal(p1, p2);
			t_bound = ell.getBounds2D();
			break;
		}
		case TARGET_TYPE_TEXT:{
			t_bound.setFrameFromDiagonal(p1, p2);
			t_font = t_font.deriveFont(1);
			t_fontMetrics = t_g2d.getFontMetrics(t_font);
			while(true){
				if(t_fontMetrics.stringWidth(text) <= t_bound.getWidth() && t_fontMetrics.getAscent() <= t_bound.getHeight()){
					t_font = t_font.deriveFont(t_font.getSize2D() + 1);
					t_fontMetrics = t_g2d.getFontMetrics(t_font);
				}
				else {
					t_font = t_font.deriveFont(t_font.getSize2D() - 1);
					t_fontMetrics = t_g2d.getFontMetrics(t_font);
					break;
				}
			}

			break;
		}
		default:{
			break;
		}
	}
	}

	//图形移动
	public void move(Point2D endPoint2d){
		double moveX = endPoint2d.getX() - t_bound.getCenterX();
		double moveY = endPoint2d.getY() - t_bound.getCenterY();
		switch(t_type){
		case TARGET_TYPE_LINE:{
			line.setLine(line.getX1() + moveX, line.getY1() + moveY, line.getX2() + moveX, line.getY2() + moveY);
			t_bound = line.getBounds2D();
			break;
		}
		case TARGET_TYPE_RECT:{
			rect.setLocation((int)endPoint2d.getX(), (int)endPoint2d.getY());
			t_bound = rect.getBounds2D();
			break;
		}
		case TARGET_TYPE_CIRCLE:{
			ell.setFrame(ell.getX() + moveX, ell.getY() + moveY, ell.getWidth(), ell.getHeight());
			t_bound = ell.getBounds2D();
			break;
		}
		case TARGET_TYPE_TEXT:{
			t_bound.setFrame(t_bound.getX() + moveX, t_bound.getY() + moveY, t_bound.getWidth(), t_bound.getHeight());

			break;
		}


		}
	}


	public Color getColor() {
		return t_color;
	}

	public void setColor(Color color) {
		this.t_color = color;
	}



	public String toString(){
		switch(t_type){
		case TARGET_TYPE_LINE: {
			return this.t_type + line.toString();
		}
		case TARGET_TYPE_RECT:{
			return this.t_type + rect.toString();
		}
		case TARGET_TYPE_CIRCLE:{
			return this.t_type + ell.toString();
		}
		case TARGET_TYPE_TEXT:{
			//TODO:
			return this.t_type + text;
		}
		default:{
			break;
		}
		}
		return null;
	}
}



