package miniCAD;

import java.awt.Dimension;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * 主界面上左的 四个按钮面板
 */
public class CreateTarget extends JPanel {

	private JButton lineButton, rectButton, circleButton, textButton;
	private Dimension d_size;

	public CreateTarget(Dimension size, MyActionListener myActionListener){
		d_size = size;
		this.setPreferredSize(size);
		this.setBorder(BorderFactory.createTitledBorder("Draw Target"));

		//lineButton = new JButton("Line");
		JButton lineButton = new JButton();
		lineButton.setBounds(320, 0, 65, 65);
        lineButton.setToolTipText("Draw a line");	// 添加说明
        //lineButton.setBorderPainted(false);
		System.out.println(CreateTarget.class.getResource("/icon/line.png"));
        ImageIcon image = new ImageIcon(CreateTarget.class.getResource("/icon/line.png"));
        Image temp = image.getImage().getScaledInstance(lineButton.getWidth(),
        		lineButton.getHeight(), Image.SCALE_SMOOTH);//自适应图标
        image = new ImageIcon(temp);
		lineButton.setIcon(image);
		lineButton.setActionCommand(Target.TARGET_TYPE_LINE + "");
		lineButton.addActionListener(myActionListener);

		//rectButton = new JButton("Rect");
		JButton rectButton = new JButton();
		rectButton.setBounds(320, 0, 65, 65);
        rectButton.setToolTipText("Draw a rectangle");	// 添加说明
        image = new ImageIcon(CreateTarget.class.getResource("/icon/rect.png"));
        temp = image.getImage().getScaledInstance(rectButton.getWidth(),
        		rectButton.getHeight(), Image.SCALE_SMOOTH);
        image = new ImageIcon(temp);
		rectButton.setIcon(image);
		rectButton.setActionCommand(Target.TARGET_TYPE_RECT + "");
		rectButton.addActionListener(myActionListener);

		//circleButton = new JButton("Circle");
		JButton circleButton = new JButton();
		circleButton.setBounds(320, 0, 65, 65);
        circleButton.setToolTipText("Draw a circle");	// 添加说明
        image = new ImageIcon(CreateTarget.class.getResource("/icon/circle.png"));
        temp = image.getImage().getScaledInstance(circleButton.getWidth(),
        		circleButton.getHeight(), Image.SCALE_SMOOTH);//自适应图标
        image = new ImageIcon(temp);
		circleButton.setIcon(image);
		circleButton.setActionCommand(Target.TARGET_TYPE_CIRCLE + "");
		circleButton.addActionListener(myActionListener);

		//textButton = new JButton("Text");
		JButton textButton = new JButton();
		textButton.setBounds(320, 0, 65, 65);
        textButton.setToolTipText("Clink sketchpad to input words");	// 添加说明
        image = new ImageIcon(CreateTarget.class.getResource("/icon/text.png"));
        temp = image.getImage().getScaledInstance(textButton.getWidth(),
        		textButton.getHeight(), Image.SCALE_SMOOTH);//自适应图标
        image = new ImageIcon(temp);
		textButton.setIcon(image);
		textButton.setActionCommand(Target.TARGET_TYPE_TEXT + "");
		textButton.addActionListener(myActionListener);

		this.add(lineButton);
		this.add(rectButton);
		this.add(circleButton);
		this.add(textButton);


	}


}
