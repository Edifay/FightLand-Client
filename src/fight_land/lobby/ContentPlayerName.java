package fight_land.lobby;

import static fight_land.AssetsContener.assets;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JLabel;

import fight_land.ResizableForFrame;

public class ContentPlayerName extends JLabel implements ResizableForFrame {

	private int firstX;
	private int firstY;

	private Thread actual_thread_running = new Thread();

	private int firstFontSize;

	private Dimension firstSize;

	private static final long serialVersionUID = -5590720555607586586L;

	private double racio_width;
	private double racio_height;

	private Font fontPlayerName = new Font("Candy Beans", 0, 25);
	private int fontSizeFirst = 25;

	private Boolean resizable;

	public ContentPlayerName(int width, int height) {
		this.resizable = true;
		this.setSize(width, height);
		this.setResizeAction();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (int i = 0; i < Lobby.actual_lobby.getPlayers().size(); i++) {
			g.setColor(Color.black);
			g.fillRoundRect(10, 10 + (45 * i), this.getWidth() - 20, 40, 20, 20);
			g.setColor(Color.white);
			g.fillRoundRect(15, 15 + (45 * i), this.getWidth() - 30, 30, 20, 20);
			g.setColor(Color.black);
			g.setFont(fontPlayerName);
			g.drawString(Lobby.actual_lobby.getPlayers().get(i).getName(), 30, 37 + (45 * i));
			if (Lobby.actual_lobby.getPlayers().get(i).getGraphicsNumber() != -1) {
				g.drawImage(assets.getIconHero().get(Lobby.actual_lobby.getPlayers().get(i).getGraphicsNumber()),
						this.getWidth() - 50, 18 + (45 * i), 25, 25, null);
			}
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
		this.racio_width = (double) this.getWidth() / (double) this.firstSize.getWidth();
		this.racio_height = (double) this.getHeight() / (double) this.getFirstSize().getHeight();
	}

	public void reCalculatePosition() {
		for (int i = 0; i < this.getComponentCount(); i++) {
			((ResizableForFrame) this.getComponent(i)).resize(this.racio_width, this.racio_height);
		}
	}

	@Override
	public int getFirstX() {
		return this.firstX;
	}

	@Override
	public ResizableForFrame setFirstX() {
		this.firstX = (int) this.getLocation().getX();
		return this;
	}

	@Override
	public int getFirstY() {
		return this.firstY;
	}

	@Override
	public ResizableForFrame setFirstY() {
		this.firstY = (int) this.getLocation().getY();
		return this;
	}

	@Override
	public ResizableForFrame setFirstLocation() {
		this.setFirstX();
		this.setFirstY();
		return this;
	}

	@Override
	public Dimension getFirstSize() {
		return firstSize;
	}

	@Override
	public ResizableForFrame setFirstSize() {
		this.firstSize = this.getSize();
		return this;
	}

	@Override
	public int getFirstFontSize() {
		return firstFontSize;
	}

	@Override
	public ResizableForFrame setFirstFontSize() {
		this.firstFontSize = this.getFont().getSize();
		return this;
	}

	@Override
	public synchronized Thread getActual_thread_running() {
		return actual_thread_running;
	}

	@Override
	public synchronized void setActual_thread_running(Thread actual_thread_running) {
		this.actual_thread_running = actual_thread_running;
	}

	@Override
	public void resize(double racio_width, double racio_height) {
		if (this.resizable) {
			this.setLocation((int) (this.getFirstX() * racio_width), (int) (this.getFirstY() * racio_height));
			this.setSize((int) (this.getFirstSize().getWidth() * racio_width),
					(int) (this.getFirstSize().getHeight() * racio_height));
			this.fontPlayerName = new Font(this.fontPlayerName.getFontName(), this.fontPlayerName.getStyle(),
					(int) (racio_width < racio_height ? (int) (this.fontSizeFirst * racio_width)
							: (this.fontSizeFirst * racio_height)));
		}
	}

	@Override
	public Boolean isResizableForFrame() {
		return this.resizable;
	}

	@Override
	public void setResizableForFrame(Boolean bool) {
		this.resizable = true;
	}
}