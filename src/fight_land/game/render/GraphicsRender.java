package fight_land.game.render;

import java.awt.Point;
import java.util.ArrayList;

import fight_land.game.render.graphics.GraphicGame;
import fight_land.game.render.graphics.Texture;

public class GraphicsRender {

	private GraphicGame content;

	private ArrayList<Texture> allTextures;

	private Texture[] texturesAtRend;

	private Thread tMove;

	private int lastWidth;
	private int lastX;
	private int lastY;
	
	public GraphicsRender() {

	}

	public void create() {
		this.content = new GraphicGame(this, fight_land.frame.FightLandFrame.frame.getWidth(),
				fight_land.frame.FightLandFrame.frame.getHeight());
		this.allTextures = new ArrayList<Texture>();
		this.lastWidth = 0;
		this.lastX = 0;
		this.lastY = 0;
	}

	public GraphicGame getContent() {
		return this.content;
	}

	public Texture[] getTexturesAtRend() {
		this.updateTextureAtRend();
		return this.texturesAtRend;
	}

	public void addTexture(Texture texture, float x, float y, int width, int height) {
		texture.setLocation(x, y);
		texture.setSize(width, height);
		this.allTextures.add(texture);
	}

	public void addTexture(Texture texture) {
		this.allTextures.add(texture);
	}

	public synchronized void updateTextureAtRend() {
		this.texturesAtRend = new Texture[this.allTextures.size()];
		float x = 5000;
		float endX = -1000;
		float y = 5000;
		float endY = -1000;

		for (int i = 0; i < this.allTextures.size(); i++) {
			if (this.allTextures.get(i).getHaveToBeIn()) {
				if (this.allTextures.get(i).getLocation().getX() < x) {
					x = (int) (this.allTextures.get(i).getLocation().getX());
				}
				if (this.allTextures.get(i).getLocation().getX()
						+ this.allTextures.get(i).getSize().getWidth() > endX) {
					endX = (int) (this.allTextures.get(i).getLocation().getX()
							+ this.allTextures.get(i).getSize().getWidth());
				}
				if (this.allTextures.get(i).getLocation().getY() < y) {
					y = (int) (this.allTextures.get(i).getLocation().getY());
				}
				if (this.allTextures.get(i).getLocation().getY()
						+ this.allTextures.get(i).getSize().getHeight() > endY) {
					endY = (int) (this.allTextures.get(i).getLocation().getY()
							+ this.allTextures.get(i).getSize().getHeight());
				}
			}
		}
		
		x -=300;
		y -= 300;
		endX += 300;
		endY +=300;

		float width = endX - x;
		float height = endY - y;

		if (width / 1920 > height / 1080) {
			height = (9 * width) / 16;
		} else {
			width = (16 * height) / 9;
		}

		if (this.lastWidth == 0) {
			this.lastWidth = (int) width;
		}
		width = this.lastWidth - ((float) (this.lastWidth - width) / 50);
		x = this.lastX - ((float)(this.lastX-x)/50);
		y = this.lastY - ((float)(this.lastY-y)/50);

		height = (9 * width) / 16;

		this.lastWidth = (int) width;
		
		this.lastX = (int) x;
		this.lastY = (int) y;

		double racio_width = 1920d / width;
		double racio_height = 1080d / height;

		for (int i = 0; i < this.allTextures.size(); i++) {
			Texture text = this.allTextures.get(i).clone();
			text.setLocation((float) ((text.getLocation().getX() - x) * racio_width),
					(float) ((text.getLocation().getY() - y) * racio_height));
			text.setSize((int) (text.getSize().getWidth() * racio_width),
					(int) (text.getSize().getHeight() * racio_height));
			this.texturesAtRend[i] = text;
		}
	}

	public void remove(Texture texture) {
		this.allTextures.remove(texture);
	}

	public synchronized void setLocationTexture(Texture texture, float x, float y) {
		this.tMove = new Thread();
		texture.setLocation(x, y);
	}

	public synchronized void setSizeTexture(Texture texture, int width, int height) {
		texture.setSize(width, height);
	}

	public synchronized void setBoundTexture(Texture texture, float x, float y, int width, int height) {
		texture.setLocation(x, y);
		texture.setSize(width, height);
	}

	public void moveTexture(Texture texture, double x, double y, int timeToGo, Boolean stoppable) {
		setThreadMove(texture, x, y, timeToGo, stoppable);
		this.tMove.start();
	}

	private void setThreadMove(Texture texture, double x, double y, int timeToGo, Boolean stoppable) {
		this.tMove = new Thread(() -> {
			contentThread(texture, x, y, timeToGo, stoppable);
		});
	}

	public Boolean containt(Texture texture) {
		return this.allTextures.contains(texture);
	}

	public void contentThread(Texture texture, double x, double y, int timeToGo, Boolean stoppable) {
		int firstPointX = (int) texture.getLocation().getX();
		int firstPointY = (int) texture.getLocation().getY();
		Point vector = new Point((int) (x - texture.getLocation().getX()), (int) (y - texture.getLocation().getY()));

		double distance = Math.sqrt(((vector.getX() - texture.getLocation().getX())
				* (vector.getX() - texture.getLocation().getX()))
				+ ((vector.getY() - texture.getLocation().getY()) * (vector.getY() - texture.getLocation().getY())));

		int distanceX = (int) (x - texture.getLocation().getX());
		double pas = distanceX / distance;
		double racio_vec = vector.getY() / vector.getX();
		double time = (timeToGo / distance);

		double endTime = System.currentTimeMillis() + (timeToGo);

		for (int i = 0; i < distance + 1; i++) {
			if (stoppable) {
				if (this.tMove != Thread.currentThread()) {// if the Thread is always the actualmove
					break;
				}
			}

			double tempsRestant = endTime - System.currentTimeMillis();
			i = (int) ((timeToGo - tempsRestant) / time);

			texture.setLocation((float) (firstPointX + (pas * i)), (float) (firstPointY + ((pas * i) * racio_vec)));

			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
