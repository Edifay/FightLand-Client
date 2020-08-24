package fight_land.lobby;

import static fight_land.frame.FightLandFrame.frame;

import java.lang.reflect.InvocationTargetException;

import javax.swing.SwingUtilities;

import fight_land.menu.ContentMenu;

public class Init {
	
	public static Lobby lob;
	
	public static ContentLobby content;
	
	public static void init() {
		new Thread(()->{
			try {
				lob = new Lobby().setThisLobbyAtLobby();
				SwingUtilities.invokeAndWait(()->{
					((ContentMenu)frame.getContentPane()).tfpsCalcul.interrupt();
					content = new ContentLobby(lob, frame.getWidth(), frame.getHeight());
					frame.setContentPane(content);
					frame.revalidate();
					content.calculateRacio();
					content.reCalculatePosition();
				});
			} catch (InvocationTargetException | InterruptedException e) {
				e.printStackTrace();
			}
		}).start();
	}
	
	public static void dispose() {
		lob = null;
		content = null;
		Lobby.actual_lobby = null;
	}
}
