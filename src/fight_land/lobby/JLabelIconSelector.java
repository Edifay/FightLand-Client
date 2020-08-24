package fight_land.lobby;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;

import fight_land.AssetsContener;
import fight_land.ResizableForFrame;
import fight_land.connexion.Connect;

public class JLabelIconSelector extends JLabel implements ResizableForFrame {

	private int firstX;
	private int firstY;

	private Thread actual_thread_running = new Thread();
	private int firstFontSize;
	private Dimension firstSize;
	private Boolean resizable;
	private int graphicsIconImage;
	private int animationZoom;
	private Boolean isSelected;

	private static final long serialVersionUID = 3625137842065133703L;

	public JLabelIconSelector(int width, int height, int graphics) {
		this.isSelected = false;
		this.setSize(width, height);
		this.resizable = true;
		this.animationZoom = 0;
		this.graphicsIconImage = graphics;
		JLabelIconSelector test = this;

		this.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
				if (e.getButton() == 1) {
					if (!getIsSelected()) {
						setIsSelected(!getIsSelected());
						if (getIsSelected()) {
							Connect.manager.sendSelect(getHeroGraphicss());
							if (getActual_thread_running().isAlive()) {
								getActual_thread_running().interrupt();
							}
							Lobby.actual_lobby.setMeGraphics(graphics);
							for (int i = 0; i < test.getParent().getComponentCount(); i++) {
								if (!((JLabelIconSelector) test.getParent().getComponent(i)).equals(test)) {
									((JLabelIconSelector) test.getParent().getComponent(i)).setIsSelected(false);
									((JLabelIconSelector) test.getParent().getComponent(i)).animationExit();
								}
							}
						}
						setAnimationZoom(20);
					}
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
				animationExit();
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				animationEntered();
			}

			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});

	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(AssetsContener.assets.getIconHero().get(this.graphicsIconImage), 10 - (this.animationZoom / 2),
				10 - (this.animationZoom / 2), (this.getWidth() - 20) + (this.animationZoom),
				(this.getHeight() - 20) + (this.animationZoom), null);
		if (getIsSelected()) {
			g.setColor(new Color(0, 0, 0, 20));
			g.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), 20, 20);
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
		return this.firstSize;
	}

	@Override
	public ResizableForFrame setFirstSize() {
		this.firstSize = this.getSize();
		return this;
	}

	@Override
	public int getFirstFontSize() {
		return this.firstFontSize;
	}

	@Override
	public ResizableForFrame setFirstFontSize() {
		this.firstFontSize = this.getFont().getSize();
		return this;
	}

	@Override
	public synchronized Thread getActual_thread_running() {
		return this.actual_thread_running;
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
			if (this.getFont() != null) {
				this.setFont(new Font(this.getFont().getFontName(), this.getFont().getStyle(),
						(int) (racio_width < racio_height ? (int) (this.getFirstFontSize() * racio_width)
								: (this.getFirstFontSize() * racio_height))));
			}
		}
	}

	@Override
	public Boolean isResizableForFrame() {
		return this.resizable;
	}

	@Override
	public void setResizableForFrame(Boolean bool) {
		this.resizable = bool;
	}

	public void setHeroGraphics(int imgNb) {
		this.graphicsIconImage = imgNb;
	}

	public int getHeroGraphicss() {
		return this.graphicsIconImage;
	}

	public synchronized int getAnimationZoom() {
		return this.animationZoom;
	}

	public synchronized void setAnimationZoom(int animationZoom) {
		this.animationZoom = animationZoom;
	}

	public Boolean getIsSelected() {
		return this.isSelected;
	}

	public void setIsSelected(Boolean isSelected) {
		this.isSelected = isSelected;
	}

	private void animationExit() {
		if (!getIsSelected()) {
			if (getActual_thread_running().isAlive()) {
				getActual_thread_running().interrupt();
			}
			Thread t = new Thread(() -> {
				try {
					while (getAnimationZoom() > 0) {
						setAnimationZoom(getAnimationZoom() - 1);
						Thread.sleep(6);
					}
				} catch (InterruptedException e1) {
				}
			});
			t.start();
			if (!getActual_thread_running().isAlive()) {
			} else {
				getActual_thread_running().interrupt();
			}
			setActual_thread_running(t);
		}
	}

	private void animationEntered() {
		if (!getIsSelected()) {
			if (getActual_thread_running().isAlive()) {
				getActual_thread_running().interrupt();
			}
			Thread t = new Thread(() -> {
				try {
					while (getAnimationZoom() <= 20) {
						setAnimationZoom(getAnimationZoom() + 1);

						Thread.sleep(3);
					}
				} catch (InterruptedException e1) {
				}
			});
			t.start();
			if (!getActual_thread_running().isAlive()) {
			} else {
				getActual_thread_running().interrupt();
			}
			setActual_thread_running(t);
		}
	}
}