package miniCAD;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

//主界面类
public class MainFrame extends JFrame{
	/**
	 *
	 */
	private static final long serialVersionUID = -829899122432614586L;
	private Dimension frameSize;
	private JPanel northPanel;
	private ChangeTarget changeTarget;
	private CreateTarget createTarget;
	private Canvas canvas;
	private TargetController m_TargetController;
	private MyActionListener m_myActionListener;

	public MainFrame(TargetController targetController, MyActionListener myActionListener) {
		m_TargetController = targetController;
		m_myActionListener = myActionListener;
		makeFrame();
	}

	//主界面生成方法
	private void makeFrame() {
		// TODO Auto-generated method stub
		this.setTitle("mini CAD");
		this.setMinimumSize(new Dimension(500, 500));
		Toolkit tk = Toolkit.getDefaultToolkit();
	    Dimension screenSize = tk.getScreenSize();
	    System.out.println(screenSize);
	    frameSize = new Dimension((int)screenSize.getWidth()/3*2, (int)screenSize.getHeight()/3*2);
	    this.setSize(frameSize);
	    System.out.println(this.getSize());
	    this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout(5,5));
		addContainers();
		this.setVisible(true);
	}

	//页面布局
	private void addContainers() {


		this.northPanel = new JPanel(new BorderLayout());
		this.changeTarget = new ChangeTarget(new Dimension((int)(frameSize.getWidth()/2),(int)(frameSize.getHeight()/6)), m_myActionListener);
		this.northPanel.add(changeTarget, BorderLayout.EAST);


		this.createTarget = new CreateTarget(new Dimension((int)(frameSize.getWidth()/2),(int)(frameSize.getHeight()/6)), m_myActionListener);
		this.northPanel.add(createTarget, BorderLayout.WEST);
		this.add(northPanel,BorderLayout.NORTH);


		this.canvas = new Canvas(this.m_TargetController, this.m_myActionListener);
		this.add(canvas, BorderLayout.CENTER);




		JMenuBar menubar = new JMenuBar();

		JMenu fileMenu = new JMenu("File");
		menubar.add(fileMenu);

		JMenuItem saveItem = new JMenuItem("Save");
		JMenuItem loadItem = new JMenuItem("Open");
		JMenuItem quitItem = new JMenuItem("Quit");
		saveItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				BufferedImage image = new BufferedImage(canvas.getWidth(), canvas.getHeight(), BufferedImage.TYPE_INT_RGB);
				Graphics2D g2 = image.createGraphics();
				canvas.paintComponent(g2);

				File saveFile = new File("drawing.jpg");//保存为jpg文件
				JFileChooser chooser = new JFileChooser();
				chooser.setCurrentDirectory(new File("./"));
				chooser.setSelectedFile(saveFile);

				int rval = chooser.showSaveDialog(canvas);
				if (rval == JFileChooser.APPROVE_OPTION) {
					saveFile = chooser.getSelectedFile();
					try {
						ImageIO.write(image, "jpg", saveFile);
					} catch (IOException ex){
						System.out.println("can not save drawing");
						ex.printStackTrace();
					}
				}
			}
		});

		//增加监听
		loadItem.addActionListener(new ActionListener() {//导入文件
			@Override
			public void actionPerformed(ActionEvent arg0) {

				JFileChooser chooser = new JFileChooser();
				chooser.setCurrentDirectory(new File("./"));
				FileNameExtensionFilter filter = new FileNameExtensionFilter("JPEG file", "jpg", "jpeg");
				chooser.setFileFilter(filter);
				int rval = chooser.showOpenDialog(canvas);
				File openFile = null;
				if (rval == JFileChooser.APPROVE_OPTION){
					openFile = chooser.getSelectedFile();
					try{
						BufferedImage image = ImageIO.read(openFile);
						canvas.setImage(image);
						repaintCanvas();
					} catch (IOException ex){
						System.out.println("can not open");
					}
				}
				System.out.println("load a picture");
			}
		});

		quitItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				System.exit(0);
			}
		});

		fileMenu.add(saveItem);
		fileMenu.add(loadItem);
		fileMenu.add(quitItem);
		this.setJMenuBar(menubar);
	}

	public void repaintCanvas(){
		canvas.repaint();
	}

	public Graphics2D getCanvasGarphics2d(){
		return (Graphics2D)canvas.getGraphics();
	}


}
