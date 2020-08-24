package fight_land.menu;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;

import fight_land.ResizableForFrame;

public class JLabelFirstPosition extends JLabel implements ResizableForFrame {

	private int firstX;
	private int firstY;

	private Thread actual_thread_running = new Thread();

	private int firstFontSize;

	private Boolean resizable;

	private Dimension firstSize;

	private static final long serialVersionUID = -9059161157884659492L;

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
		this.resizable = bool;
	}

}
