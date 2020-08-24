package fight_land;

import java.awt.Dimension;

public interface ResizableForFrame {
	
	public abstract int getFirstX();

	public abstract ResizableForFrame setFirstX();

	public abstract int getFirstY();

	public abstract ResizableForFrame setFirstY();

	public abstract ResizableForFrame setFirstLocation();

	public abstract Dimension getFirstSize();

	public abstract ResizableForFrame setFirstSize();

	public abstract int getFirstFontSize();

	public abstract ResizableForFrame setFirstFontSize();

	public abstract Thread getActual_thread_running();

	public abstract void setActual_thread_running(Thread actual_thread_running);
	
	public abstract void resize(double racio_width, double racio_height);
	
	public abstract Boolean isResizableForFrame();
	
	public abstract void setResizableForFrame(Boolean bool);
	
}
