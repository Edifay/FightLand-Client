package fight_land.lobby;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import fight_land.ResizableForFrame;
import fight_land.menu.JLabelFirstPosition;

public class ContentLobby extends JPanel {

	private static final long serialVersionUID = -4566671247279866254L;

	private Lobby lob;

	private double racio_width;
	private double racio_height;

	private int fps;
	private int actual_count;

	private static Font font_timer = new Font("Candy Beans", 0, 150);
	private static int font_timer_first = 150;
	private static Font font_fps = new Font("Candy Beans", 1, 20);

	public static final Color backgroundColor = new Color(233, 255, 255);

	public ContentLobby(Lobby lob, int width, int height) {
		ContentSelect select = new ContentSelect(width, height);// Select champ
		select.setLocation(20, 20);
		select.setSize(290, 415);
		select.setFirstLocation().setFirstSize();
		
		JLabelFirstPosition button_ready = new JLabelFirstPosition();
		button_ready.setText("READY");
		button_ready.setLocation((int) (800), (int) (600));
		button_ready.setSize((int) (450), (int) (150));
		button_ready.setFont(new Font("Candy Beans", 0, 150));
		button_ready.setFirstSize().setFirstLocation().setFirstFontSize();
		button_ready.setResizableForFrame(true);
		button_ready.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {

			}

			@Override
			public void mousePressed(MouseEvent e) {
				Boolean oneIsSelected = false;
				for(int i = 0; i < select.getComponentCount(); i++) {
					JLabelIconSelector selectIcon = (JLabelIconSelector)select.getComponent(i);
					if(selectIcon.getIsSelected()) {
						oneIsSelected = true;
					}
				}
				if(oneIsSelected) {
					fight_land.connexion.Connect.manager.sendReadtToPlay();
					SwingUtilities.invokeLater(()->{
						button_ready.removeMouseListener(this);
						button_ready.setText("Wait...");
						select.setVisible(false);
					});
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
				button_ready
						.setFont(new Font(button_ready.getFont().getFontName(), 0, button_ready.getFont().getSize()));
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				button_ready
						.setFont(new Font(button_ready.getFont().getFontName(), 1, button_ready.getFont().getSize()));
			}

			@Override
			public void mouseClicked(MouseEvent e) {

			}
		});
		this.add(button_ready);

		this.setSize(width, height);
		this.setLob(lob);
		this.setLayout(null);

		calculateRacio();

		this.add(select);

		ContentPlayerName players = new ContentPlayerName(width, height);
		players.setLocation(1614, 6);
		players.setSize(300, 400);
		players.setFirstLocation().setFirstSize();
		this.add(players);

		setResizeAction();
		fpsCalculator();
		Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
	}

	private void fpsCalculator() {
		new Thread(() -> {
			while (Lobby.actual_lobby != null) {
				this.fps = this.actual_count;
				this.actual_count = 0;
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		}).start();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(backgroundColor);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		this.actual_count++;
		g.setFont(font_fps);
		g.setColor(Color.black);
		g.drawString(this.fps + "", 2, 20);
		g.setFont(font_timer);
		if (this.lob.getTimer_lobby() != 5) {
			g.drawString(this.lob.getTimer_lobby() + "", (int) (1000 * this.racio_width),
					(int) (120 * this.racio_height));
		} else {
			g.drawString("Waiting Players...", (int) (500 * this.racio_width), (int) (120 * this.racio_height));
		}
	}

	private void setResizeAction() {
		this.addComponentListener((ComponentListener) new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				calculateRacio();
				reCalculatePosition();
			}
		});
	}

	public void calculateRacio() {
		this.racio_width = (double) this.getWidth() / (double) 1920;
		this.racio_height = (double) this.getHeight() / (double) 1080;
	}

	public void reCalculatePosition() {
		for (int i = 0; i < this.getComponentCount(); i++) {
			((ResizableForFrame) this.getComponent(i)).resize(this.racio_width, this.racio_height);
		}
		font_timer = new Font(font_timer.getName(), font_timer.getStyle(),
				(int) (this.racio_width < this.racio_height ? (int) (font_timer_first * this.racio_width)
						: (font_timer_first * this.racio_height)));
	}

	public Lobby getLob() {
		return this.lob;
	}

	public void setLob(Lobby lob) {
		this.lob = lob;
	}
}
