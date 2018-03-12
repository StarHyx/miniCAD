package miniCAD;


import java.awt.Dimension;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * 主界面 上右的三个按钮界面 大小 位置 颜色
 */
public class ChangeTarget extends JPanel{

	private JButton sizeButton, posButton, colorButton;
	private Dimension c_size;
	private MyActionListener c_myActionListener;

	public ChangeTarget(Dimension size, MyActionListener myActionListener) {

		c_size = size;
		c_myActionListener = myActionListener;
		this.setPreferredSize(c_size);
		this.setBorder(BorderFactory.createTitledBorder("Change Target"));



		//sizeButton = new JButton("Size");
		JButton sizeButton = new JButton();//调整大小
		sizeButton.setBounds(320, 0, 65, 65);
        sizeButton.setToolTipText("Selcet and drag target to change size");	// 添加说明
        ImageIcon image = new ImageIcon(CreateTarget.class.getResource("/icon/size.png"));
        Image temp = image.getImage().getScaledInstance(sizeButton.getWidth(),
        		sizeButton.getHeight(), Image.SCALE_SMOOTH);//自适应图标
        image = new ImageIcon(temp);
		sizeButton.setIcon(image);
		sizeButton.setActionCommand(MyActionListener.TYPE_CHANGE_SIZE + "");
		sizeButton.addActionListener(c_myActionListener);

		//posButton = new JButton("Position");
		JButton posButton = new JButton();//移动位置
		posButton.setBounds(320, 0, 65, 65);
        posButton.setToolTipText("Select and drag target to move");	// 添加说明
        image = new ImageIcon(CreateTarget.class.getResource("/icon/position.png"));
        temp = image.getImage().getScaledInstance(posButton.getWidth(),
        		posButton.getHeight(), Image.SCALE_SMOOTH);//自适应图标
        image = new ImageIcon(temp);
		posButton.setIcon(image);
		posButton.setActionCommand(MyActionListener.TYPE_CHANGE_POS + "");
		posButton.addActionListener(c_myActionListener);

		JButton colorButton = new JButton();//移动位置
		colorButton.setBounds(320, 0, 65, 65);
        colorButton.setToolTipText("Choose or change target color");	// 添加说明
        image = new ImageIcon(CreateTarget.class.getResource("/icon/color.png"));
        temp = image.getImage().getScaledInstance(colorButton.getWidth(),
        		colorButton.getHeight(), Image.SCALE_SMOOTH);//自适应图标
        image = new ImageIcon(temp);
		colorButton.setIcon(image);
		//colorButton = new JButton("Color");
		colorButton.setActionCommand(MyActionListener.TYPE_CHANGE_COLOR + "");
		colorButton.addActionListener(c_myActionListener);

		this.add(sizeButton);
		this.add(posButton);
		this.add(colorButton);
	}



}
