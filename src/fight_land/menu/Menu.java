package fight_land.menu;

import java.lang.reflect.InvocationTargetException;

import static fight_land.frame.FightLandFrame.frame;

import javax.swing.SwingUtilities;

import fight_land.connexion.Connect;

public class Menu {

	public static void init() {
		try {
			SwingUtilities.invokeAndWait(() -> {
				frame.setContentPane(new ContentMenu(frame.getWidth(), frame.getHeight()));
				frame.revalidate();
			});
		} catch (InvocationTargetException | InterruptedException e) {
			e.printStackTrace();
		}
		frame.setNanoRefresh(10000000l);
		
		new Thread(()->{
			Connect.connect();
		}).start();
	}
}
