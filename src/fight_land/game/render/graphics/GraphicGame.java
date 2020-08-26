package fight_land.game.render.graphics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JPanel;

import fight_land.game.render.GraphicsRender;

public class GraphicGame extends JPanel {

	private static final long serialVersionUID = 3107928442240797425L;

	public static final Color backgroundColor = new Color(233, 255, 255);
	
	private double racio_width;
	private double racio_height;

	private GraphicsRender render;

	public GraphicGame(GraphicsRender render, int width, int height) {
		this.setSize(width, height);
		this.calculateRacio();
		this.render = render;
		this.setResizeAction();
	}

	private void setResizeAction() {
		this.addComponentListener((ComponentListener) new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				calculateRacio();
			}
		});
	}

	@Override
	protected void paintComponent(Graphics g) {
		this.render.updateTextureAtRend();
		Texture[] textures = this.render.getTexturesAtRend();
		super.paintComponent(g);
		/*
		 * System.out.println("at Paint: "+ textures.length);
		 * System.out.println(textures[0]);
		 */

		g.setColor(backgroundColor);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		
		g.setColor(Color.black);
		
		for (Texture texture : textures) {
			g.drawImage(texture.getImage(), (int) (texture.getLocation().getX() * this.racio_width),
					(int) (texture.getLocation().getY() * this.racio_height),
					(int) (texture.getSize().getWidth() * this.racio_width),
					(int) (texture.getSize().getHeight() * this.racio_height), null);
		/*	g.drawRect((int) (texture.getLocation().getX() * this.racio_width),
					(int) (texture.getLocation().getY() * this.racio_height),
					(int) (texture.getSize().getWidth() * this.racio_width),
					(int) (texture.getSize().getHeight() * this.racio_height));*/
		}
	}

	public void calculateRacio() {
		this.racio_width = (double) this.getWidth() / (double) 1920;
		this.racio_height = (double) this.getHeight() / (double) 1080;
	}
}
