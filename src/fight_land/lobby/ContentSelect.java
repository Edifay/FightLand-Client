package fight_land.lobby;

import static fight_land.AssetsContener.assets;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JLabel;

import fight_land.ResizableForFrame;

public class ContentSelect extends JLabel implements ResizableForFrame {

	private int firstX;
	private int firstY;

	private Thread actual_thread_running = new Thread();

	private int firstFontSize;

	private Dimension firstSize;

	private Boolean resizable;

	private double racio_width;
	private double racio_height;

	private int lastX = 0;
	private int lastY = 0;

	public static Color color_background = new Color(183, 215, 214);

	private static final long serialVersionUID = -3645938849814695870L;

	public ContentSelect(int width, int height) {
		this.resizable = true;
		this.setSize(width, height);
		this.setLayout(null);
//		draggWindow();
		putLabelForHero(width, height);
		setResizeAction();
	}

	private void putLabelForHero(int width, int height) {
		int maxInColumn = 3;

		int x = 0;
		int y = 0;
		for (int i = 0; i < assets.getIconHero().size(); i++) {
			JLabelIconSelector lab = new JLabelIconSelector(width, height, i);
			lab.setLocation(20 + (125 * x), 20 + (125 * y));
			lab.setSize(120, 120);
			lab.setFirstLocation().setFirstSize();
			y++;
			if (y >= maxInColumn) {
				y = 0;
				x++;
			}
			this.add(lab);
		}
	}

	@SuppressWarnings("unused")
	private void draggWindow() {
		this.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseMoved(MouseEvent e) {

			}

			@Override
			public void mouseDragged(MouseEvent e) {
				int x = (int) ((getLocation().getX() + e.getX()) - lastX);
				int y = (int) ((getLocation().getY() + e.getY()) - lastY);

				if (x > (5 * racio_width) && x < (1915 * racio_width) - getWidth() && y > (5 * racio_height)// les deux
																											// sont bons
						&& y < (1075 * racio_height) - getHeight()) {

					setLocation(x, y);
				} else if ((x <= (5 * racio_width) || x >= (1915 * racio_width) - getWidth())// les deux sont au max
						&& (y <= (5 * racio_height) || y >= (1075 * racio_height) - getHeight())) {

				} else if ((x > (5 * racio_width) || x < (1915 * racio_width) - getWidth())// Le x est bon
						&& (y <= (5 * racio_height) || y >= (1075 * racio_height) - getHeight())) {

					setLocation(x, (int) getLocation().getY());
				} else if ((x <= (5 * racio_width) || x >= (1915 * racio_width) - getWidth())// Le y est bon
						&& (y > (5 * racio_height) || y < (1075 * racio_height) - getHeight())) {

					setLocation((int) getLocation().getX(), y);
				}
			}
		});
		this.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				if (getLocation().getX() < 5) {
					setLocation(5, (int) getLocation().getY());
				}
				if (getLocation().getX() > (1915 * racio_width) - getWidth()) {
					setLocation((int) (1915 * racio_width) - getWidth(), (int) getLocation().getY());
				}
				if (getLocation().getY() < 5) {
					setLocation((int) getLocation().getX(), 5);
				}
				if (getLocation().getY() > (1075 * racio_height) - getHeight()) {
					setLocation((int) getLocation().getX(), (int) (1075 * racio_height) - getHeight());
				}
			}

			@Override
			public void mousePressed(MouseEvent e) {
				lastX = e.getX();
				lastY = e.getY();
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(ContentLobby.backgroundColor);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		g.setColor(color_background);
		g.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), 30, 30);
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
		return firstX;
	}

	@Override
	public ResizableForFrame setFirstX() {
		this.firstX = (int) this.getLocation().getX();
		return this;
	}

	@Override
	public int getFirstY() {
		return firstY;
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
			this.setFont(new Font(this.getFont().getFontName(), this.getFont().getStyle(),
					(int) (racio_width < racio_height ? (int) (this.getFirstFontSize() * racio_width)
							: (this.getFirstFontSize() * racio_height))));
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