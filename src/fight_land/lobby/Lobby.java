package fight_land.lobby;

import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.SwingUtilities;

import fight_land.main.Main;
import fight_land.menu.JLabelFirstPosition;

public class Lobby {

	public static Lobby actual_lobby;

	private long timer_lobby;

	private ArrayList<PlayerLobby> players;

	private PlayerLobby me;

	private Thread t;

	public Lobby() {
		this.me = new PlayerLobby(Main.name, -1);
		this.players = new ArrayList<PlayerLobby>();
		this.players.add(this.me);
	}

	public Lobby setThisLobbyAtLobby() {
		actual_lobby = this;
		timer_lobby = 5;
		return this;
	}

	public long getTimer_lobby() {
		return this.timer_lobby;
	}

	public void start() {
		this.t = new Thread(() -> {
			try {
				while (this.timer_lobby > 0) {
					this.timer_lobby--;
					Thread.sleep(1000);
				}
			} catch (InterruptedException e) {
			}
		});
		this.t.start();
	}

	public void restartLobby() {
		this.t.interrupt();

		JLabelFirstPosition button_ready = (JLabelFirstPosition) fight_land.lobby.Init.content.getComponent(0);
		fight_land.lobby.Init.content.getComponent(1).setVisible(true);
		SwingUtilities.invokeLater(() -> {
			button_ready.setText("READY");
			button_ready.setFont(new Font(button_ready.getFont().getFontName(), 0, button_ready.getFont().getSize()));
		});
		button_ready.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {

			}

			@Override
			public void mousePressed(MouseEvent e) {
				Boolean oneIsSelected = false;
				for (int i = 0; i < ((ContentSelect)fight_land.lobby.Init.content.getComponent(1)).getComponentCount(); i++) {
					JLabelIconSelector selectIcon = (JLabelIconSelector) ((ContentSelect)fight_land.lobby.Init.content.getComponent(1)).getComponent(i);
					if (selectIcon.getIsSelected()) {
						oneIsSelected = true;
					}
				}
				if (oneIsSelected) {
					fight_land.connexion.Connect.manager.sendReadtToPlay();
					SwingUtilities.invokeLater(() -> {
						button_ready.setText("Wait...");
						fight_land.lobby.Init.content.getComponent(1).setVisible(false);
					});
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
				SwingUtilities.invokeLater(() -> {
					button_ready.setFont(
							new Font(button_ready.getFont().getFontName(), 0, button_ready.getFont().getSize()));
				});
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				SwingUtilities.invokeLater(() -> {
					button_ready.setFont(
							new Font(button_ready.getFont().getFontName(), 1, button_ready.getFont().getSize()));
				});
			}

			@Override
			public void mouseClicked(MouseEvent e) {

			}
		});
	}

	public void setTimer_lobby(long timer_lobby) {
		this.timer_lobby = timer_lobby;
	}

	public PlayerLobby getMe() {
		return this.me;
	}

	public void setMeGraphics(int graphicsNumber) {
		this.me.setGraphicsNumber(graphicsNumber);
	}

	public ArrayList<PlayerLobby> getPlayers() {
		return players;
	}
}
