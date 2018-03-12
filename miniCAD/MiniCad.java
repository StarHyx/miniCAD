package miniCAD;


/**
 * 主程序类
 */
public class MiniCad {
	private static TargetController targetController = new TargetController();
	private static MyActionListener myActionListener = new MyActionListener(targetController);
	private static MainFrame mainFrame = new MainFrame(targetController, myActionListener);

	public static void main(String[] argv){
		targetController.setGarphics2d(mainFrame.getCanvasGarphics2d());
		myActionListener.setMainFrame(mainFrame);
	}

}
