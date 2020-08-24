package fight_land.menu;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import fight_land.AssetsContener;
import fight_land.ResizableForFrame;
import fight_land.connexion.Connect;
import fight_land.main.Main;

public class ContentMenu extends JPanel {

	private static final long serialVersionUID = -8105652512187519841L;

	private double racio_width;
	private double racio_height;

	public Thread tfpsCalcul;

	private int fps;
	private int actual_count;

	private ArrayList<JLabelFirstPosition> lab;

	public ContentMenu(int width, int height) {
		this.lab = new ArrayList<JLabelFirstPosition>();
		this.setSize(width, height);

		this.setLayout(null);

		this.calculateRacio();

		this.addComponent();
		this.setResizeAction();
		this.fpsCalculator();

		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		try {
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT,
					getClass().getResourceAsStream("/assets/menu/font/Candy Beans.otf")));
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.reCalculatePosition();
	}

	private void setResizeAction() {
		this.addComponentListener((ComponentListener) new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				calculateRacio();
				reCalculatePosition();
			}
		});
	}

	private void fpsCalculator() {
		this.tfpsCalcul = new Thread(() -> {
			try {
				while (true) {
					this.fps = this.actual_count;
					this.actual_count = 0;
					Thread.sleep(1000);
				}
			} catch (Exception e) {
			}
		});
		this.tfpsCalcul.start();
	}

	private void addComponent() {
		JLabelFirstPosition name_text = new JLabelFirstPosition();
		name_text.setText(Main.name);
		name_text.setLocation((int) (1600), (int) (0));
		name_text.setSize((int) (420), (int) (80));
		name_text.setFont(new Font("Candy Beans", 0, 40));
		name_text.setFirstSize().setFirstLocation().setFirstFontSize();
		name_text.setResizableForFrame(true);
		this.add(name_text);
		this.lab.add(name_text);

		JLabelFirstPosition menu_text = new JLabelFirstPosition();
		menu_text.setText("MENU");
		menu_text.setLocation((int) (20), (int) (20));
		menu_text.setSize((int) (500), (int) (200));
		menu_text.setFont(new Font("Candy Beans", 1, 150));
		menu_text.setFirstSize().setFirstLocation().setFirstFontSize();
		menu_text.setResizableForFrame(true);
		this.add(menu_text);
		this.lab.add(menu_text);

		Font buttonFont = new Font("Candy Beans", 0, 60);

		JLabelFirstPosition button_play = new JLabelFirstPosition();
		button_play.setText("PLAY");
		button_play.setLocation(40, 200);
		button_play.setSize((int) (160), 60);
		button_play.setFont(buttonFont);
		button_play.setFirstSize().setFirstLocation().setFirstFontSize();
		button_play.setResizableForFrame(true);
		button_play.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
				Connect.manager.sendJoinLobby();
				fight_land.lobby.Init.init();
			}

			@Override
			public void mouseExited(MouseEvent e) {
				animationButtonExited(button_play);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				animationButtonEntered(button_play);
			}

			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		this.add(button_play);
		this.lab.add(button_play);

		JLabelFirstPosition button_option = new JLabelFirstPosition();
		button_option.setText("OPTION");
		button_option.setLocation(40, 260);
		button_option.setSize(240, 60);
		button_option.setFont(buttonFont);
		button_option.setFirstSize().setFirstLocation().setFirstFontSize();
		button_option.setResizableForFrame(true);
		button_option.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
				System.out.println("pressed : OPTION");
			}

			@Override
			public void mouseExited(MouseEvent e) {
				animationButtonExited(button_option);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				animationButtonEntered(button_option);
			}

			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		this.add(button_option);
		this.lab.add(button_option);

		JLabelFirstPosition button_exit = new JLabelFirstPosition();
		button_exit.setText("EXIT");
		button_exit.setLocation(40, 320);
		button_exit.setSize(140, 60);
		button_exit.setFont(buttonFont);
		button_exit.setFirstSize().setFirstLocation().setFirstFontSize();
		button_exit.setResizableForFrame(true);
		button_exit.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
				fight_land.dispose.Dispose.dispose();
			}

			@Override
			public void mouseExited(MouseEvent e) {
				animationButtonExited(button_exit);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				animationButtonEntered(button_exit);
			}

			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		this.add(button_exit);
		this.lab.add(button_exit);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		g.drawImage(AssetsContener.assets.getBackgrounds().get(0), 0, 0, this.getWidth(), this.getHeight(), null);

		g.setFont(new Font(null, 1, 20));
		g.drawString(this.fps + "", 2, (int) (1078 * this.racio_height));
		if (Connect.manager != null) {
			g.drawString("TCP: " + Connect.manager.getPingTCP(), 2, (int) (1060 * this.racio_height));
			g.drawString("UDP: " + Connect.manager.getPingUDP(), 2, (int) (1040 * this.racio_height));
		} else {
			g.drawString("TCP: disconnected", 2, (int) (1060 * this.racio_height));
			g.drawString("UDP: disconnected", 2, (int) (1040 * this.racio_height));
		}
		this.actual_count++;
	}

	private synchronized void animationButtonExited(JLabelFirstPosition button) {
		if (button.getActual_thread_running().isAlive()) {
			button.getActual_thread_running().interrupt();
		}
		Thread t = new Thread(() -> {
			SwingUtilities.invokeLater(() -> {
				button.setFont(new Font(button.getFont().getFontName(), 0,
						(int) (this.racio_width < this.racio_height
								? (int) (button.getFirstFontSize() * this.racio_width)
								: (button.getFirstFontSize() * this.racio_height))));
				button.setSize((int) (button.getFirstSize().getWidth() * this.racio_width),
						(int) (button.getFirstSize().getHeight() * this.racio_height));
			});
			try {
				while (button.getLocation().getX() > button.getFirstX() * this.racio_width) {
					SwingUtilities.invokeLater(() -> {
						button.setLocation((int) ((button.getLocation().getX() - 1)),
								(int) (button.getFirstY() * this.racio_height));
					});
					Thread.sleep(6);
				}
			} catch (InterruptedException e1) {
			}
		});
		t.start();
		if (!button.getActual_thread_running().isAlive()) {
		} else {
			button.getActual_thread_running().interrupt();
		}
		button.setActual_thread_running(t);
	}

	private synchronized void animationButtonEntered(JLabelFirstPosition button) {
		if (button.getActual_thread_running().isAlive()) {
			button.getActual_thread_running().interrupt();
		}
		Thread t = new Thread(() -> {
			SwingUtilities.invokeLater(() -> {
				button.setFont(new Font(button.getFont().getFontName(), 1,
						(int) (this.racio_width < this.racio_height
								? (int) (button.getFirstFontSize() * this.racio_width)
								: ((button.getFirstFontSize() + 5) * this.racio_height))));
				button.setSize((int) ((button.getFirstSize().getWidth() + 20) * this.racio_width),
						(int) (button.getFirstSize().getHeight() * this.racio_height));
			});
			try {
				while (button.getLocation().getX() <= (button.getFirstX() + 20) * this.racio_width) {
					SwingUtilities.invokeLater(() -> {
						button.setLocation((int) ((button.getLocation().getX() + 1)),
								(int) (button.getFirstY() * this.racio_height));
					});
					Thread.sleep(3);
				}
			} catch (InterruptedException e1) {
			}
		});
		t.start();
		if (!button.getActual_thread_running().isAlive()) {
		} else {
			button.getActual_thread_running().interrupt();
		}
		button.setActual_thread_running(t);
	}

	public void calculateRacio() {
		this.racio_width = (double) this.getWidth() / (double) 1920;
		this.racio_height = (double) this.getHeight() / (double) 1080;
	}

	public void reCalculatePosition() {
		for (int i = 0; i < this.getComponentCount(); i++) {
			((ResizableForFrame) this.getComponent(i)).resize(this.racio_width, this.racio_height);
		}
	}
}
