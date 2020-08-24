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

	public GraphicsRender() {

	}

	public void create() {
		this.content = new GraphicGame(this, fight_land.frame.FightLandFrame.frame.getWidth(),
				fight_land.frame.FightLandFrame.frame.getHeight());
		this.allTextures = new ArrayList<Texture>();
	}

	public GraphicGame getContent() {
		return this.content;
	}

	public Texture[] getTexturesAtRend() {
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
		this.allTextures.toArray(this.texturesAtRend);
	}

	public void remove(Texture texture) {
		this.allTextures.remove(texture);
	}

	public synchronized void setLocationTexture(Texture texture, float x, float y) {
		texture.setLocation(x, y);
	}

	public synchronized void setSizeTexture(Texture texture, int width, int height) {
		texture.setSize(width, height);
	}

	public synchronized void setBoundTexture(Texture texture, float x, float y, int width, int height) {
		texture.setLocation(x, y);
		texture.setSize(width, height);
	}

	public void moveTexture(Texture texture, double x, double y, int timeToGo) {
		if (this.tMove == null) {

		} else if (this.tMove.isAlive()) {
		}
		setThreadMove(texture, x, y, timeToGo);
		this.tMove.start();
	}

	private void setThreadMove(Texture texture, double x, double y, int timeToGo) {
		this.tMove = new Thread(() -> {
			int firstPointX = (int) texture.getLocation().getX();
			int firstPointY = (int) texture.getLocation().getY();
			Point vector = new Point((int) (x - texture.getLocation().getX()),
					(int) (y - texture.getLocation().getY()));

			double distance = Math.sqrt(
					((vector.getX() - texture.getLocation().getX()) * (vector.getX() - texture.getLocation().getX()))
							+ ((vector.getY() - texture.getLocation().getY())
									* (vector.getY() - texture.getLocation().getY())));

			int distanceX = (int) (x - texture.getLocation().getX());
			double pas = distanceX / distance;
			double racio_vec = vector.getY() / vector.getX();
			double time = (timeToGo / distance);

			double endTime = System.currentTimeMillis() + (timeToGo);

			for (int i = 0; i < distance + 1; i++) {
				if (this.tMove != Thread.currentThread()) {// if the Thread is always the actualmove
					break;
				}

				double tempsRestant = endTime - System.currentTimeMillis();
				i = (int) ((timeToGo - tempsRestant) / time);

				this.setLocationTexture(texture, (float) (firstPointX + (pas * i)),
						(float) (firstPointY + ((pas * i) * racio_vec)));
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Boolean containt(Texture texture) {
		return this.allTextures.contains(texture);
	}
}
