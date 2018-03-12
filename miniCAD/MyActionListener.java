package miniCAD;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;

import javax.swing.JColorChooser;

/**
 * 动作监听类
 */
public class MyActionListener implements ActionListener, MouseListener, MouseMotionListener{
//	public static final int TYPE_CHOOSE = 0;
	public static final int TYPE_CHOSED = 0x00020;
	public static final int TYPE_CREATE = 0x0001;
	public static final int TYPE_CHANGE_SIZE = 0x0004;
	public static final int TYPE_CHANGE_POS = 0x0008;
	public static final int TYPE_CHANGE_COLOR = 0x0010;

	private static int m_type = TYPE_CHANGE_SIZE;
	private static int create_target = -1;
	private static Color m_color = Color.BLACK;
	private Point2D pressedPoint2d = new Point2D.Double();

	private Target currentTarget = null;
	private TargetController m_tarController;
	private MainFrame m_mainFrame;

	public MyActionListener(TargetController targetController) {
		m_tarController = targetController;

	}

	public void setMainFrame(MainFrame mainFrame){
		m_mainFrame = mainFrame;
	}

	/**
	 * 动作监听处理方法
	 * @param e
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		int actionCommand =  Integer.parseInt(e.getActionCommand());
		switch(actionCommand){
		case Target.TARGET_TYPE_LINE:{
		}
		case Target.TARGET_TYPE_RECT:{
		}
		case Target.TARGET_TYPE_CIRCLE:{
		}
		case Target.TARGET_TYPE_TEXT:{

			create_target = actionCommand;
			m_type = TYPE_CREATE;
			break;
		}
		case TYPE_CHANGE_COLOR:{
			m_color = JColorChooser.showDialog((Component) source, "Choose Color", Color.BLACK);
			if(currentTarget != null) {
				currentTarget.setColor(m_color);
				m_mainFrame.repaintCanvas();
			}
			else{
				m_tarController.setColor(m_color);
			}
			break;
		}
		case TYPE_CHANGE_SIZE:{
			m_type = TYPE_CHANGE_SIZE;
			break;
		}
		case TYPE_CHANGE_POS:{
			m_type = TYPE_CHANGE_POS;
			break;
		}

		default:{
			System.out.println("In click button : no match type!");
		}
		}
	}

	/**
	 * 鼠标移动方法
	 *
	 * @param e
	 */
	@Override
	public void mouseDragged(MouseEvent e) {
		Component source = e.getComponent();
		Point2D dragedPoint2d = new Point2D.Double(e.getX(), e.getY());
		if(source instanceof Canvas) {
			switch(m_type){
			case TYPE_CREATE:{
				currentTarget.setFrameFromDiagonal(currentTarget.getP1(),dragedPoint2d);
				source.repaint();
				break;
			}
			case TYPE_CHANGE_SIZE:{
				if(currentTarget != null){
					currentTarget.changeSize(dragedPoint2d);
					source.repaint();
				}
				break;
			}
			case TYPE_CHANGE_POS:{
				if(currentTarget != null){
					currentTarget.move(dragedPoint2d);
					source.repaint();
				}
				break;
			}
			}
		}
		else{
			// pressed on other compoents, do nothing.
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {}

	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	/**
	 * 按下鼠标 监听方法
	 * @param e
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		Component source = e.getComponent();
		pressedPoint2d.setLocation(e.getX(), e.getY());
		System.out.println("canvas pressed at: " + pressedPoint2d);
		if(source instanceof Canvas) {
			if(m_type == TYPE_CREATE){
				int create_type = create_target;
				if(currentTarget != null) currentTarget.setNotChosed();
				currentTarget = m_tarController.createTarget(create_type, pressedPoint2d.getX(), pressedPoint2d.getY(), pressedPoint2d.getX(), pressedPoint2d.getY());
				if(create_type == Target.TARGET_TYPE_TEXT)  m_type = TYPE_CHANGE_SIZE;
				source.repaint();
			}
			else if(m_type == TYPE_CHOSED || m_type == TYPE_CHANGE_POS || m_type == TYPE_CHANGE_SIZE){
				// 如果鼠标点击在图形区域内，则选中图形
				if(currentTarget != null) currentTarget.setNotChosed();
				currentTarget =  m_tarController.chooseTarget(pressedPoint2d);
				if(currentTarget != null) {
					currentTarget.setChosed(pressedPoint2d);
				}

				source.repaint();
			}
		}
		else {
			// do nothing
		}
	}

	/**
	 * 松开鼠标方法
	 * @param e
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		Component source = e.getComponent();
		Point2D releasePoint2d = new Point2D.Double(e.getX(), e.getY());
		System.out.println("canvas released at: " + releasePoint2d);
		if(source instanceof Canvas) {
			if(currentTarget != null) currentTarget.setNotChosed();
			if(m_type == TYPE_CREATE) currentTarget = null;
			if(m_type == TYPE_CHOSED){}
			else {
				m_type = TYPE_CHANGE_SIZE;
			}
		}
		else {
			// do nothing
		}
	}

}

