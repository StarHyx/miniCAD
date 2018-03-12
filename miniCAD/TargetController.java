package miniCAD;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

/**
 * 图形目标控制类
 */
public class TargetController {
	private List<Target> targetList = new ArrayList<Target>();
	private Graphics2D t_g2d;
	private Color t_color = Color.black;
	
	public TargetController(){
		
	}

	//获取画笔
	public void setGarphics2d(Graphics2D g2d){
		t_g2d = g2d;
	}

	//创建图形
	public Target createTarget(int type, double x1, double y1, double x2, double y2){
		Target newTarget = new Target(type, t_g2d, t_color, x1, y1, x2, y2);
		targetList.add(newTarget);
		return newTarget;
	}

	//选择图形
	public Target chooseTarget(Point2D pressedPoint){
		Target nowTarget = null, tmpTarget = null;
		for(Target t: targetList){
			tmpTarget = t.intersects(pressedPoint);
			if(tmpTarget != null) nowTarget = tmpTarget;
		}
		return nowTarget;
	}

	//设置颜色
	public void setColor(Color color){
		t_color = color;
	}

	//绘制
	public void renderAll(Graphics g){
		for(Target t:targetList){
			t.draw(g);
		}
	}

}
