package fight_land.game.load;

import java.awt.Graphics;

import javax.swing.JPanel;

public class GraphicsLoad extends JPanel{
	
	private LoadGame loader;
	
	public GraphicsLoad(LoadGame loader) {
		this.loader = loader;
	}

	private static final long serialVersionUID = 7231568847447865550L;

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawString(this.loader.loaded+"/"+this.loader.atLoad, this.getWidth()/2, this.getHeight()/2);
	}
}