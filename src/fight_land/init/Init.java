package fight_land.init;

import static fight_land.frame.FightLandFrame.frame;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import javax.swing.JFrame;

import fight_land.frame.FightLandFrame;

public class Init {
	
	private static InitLoading initloading;
	
	public static int init() {
		new FightLandFrame().setThisFrameAsFrame();// start frame
		frame.setSize(720, 480);
//		frame.setUndecorated(true);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setVisible(true);
		
		@SuppressWarnings("unused")
		GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
//		device.setFullScreenWindow(frame);
		
		frame.setNanoRefresh(100000000);
		frame.setRefresh(true);
		
		initloading = new InitLoading();
		initloading.start();
		
		return 0;
	}
	public static int getAtLoad() {
		return initloading.getAtLoad();
	}
	public static int getLoaded() {
		return initloading.getLoaded();
	}
}
