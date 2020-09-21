package fight_land.game.render.graphics;

import java.awt.Dimension;
import java.awt.image.BufferedImage;

import fight_land.game.Point;

public class Texture {

	protected BufferedImage img;

	protected Point location;

	protected Dimension size;

	protected Boolean RightOrLeft;

	protected Boolean haveToBeIn;

	protected Thread tMove;

	public Texture(Boolean haveToBeIn) {
		this.RightOrLeft = true;
		this.haveToBeIn = haveToBeIn;
		this.tMove = new Thread();
	}

	public void setLocation(float x, float y) {
		this.location = new Point(x, y);
	}

	public void setLocation(Point point) {
		this.location = point;
	}

	public void setSize(int width, int height) {
		this.size = new Dimension(width, height);
	}

	public void setSize(Dimension size) {
		this.size = size;
	}

	public Point getLocation() {
		return this.location;
	}

	public Dimension getSize() {
		return this.size;
	}

	public BufferedImage getImage() {
		return this.img;
	}

	public void setImage(BufferedImage img) {
		this.img = img;
	}

	public Boolean getRightOrLeft() {
		return this.RightOrLeft;
	}

	public void setRightOrLeft(Boolean rightOrLeft) {
		this.RightOrLeft = rightOrLeft;
	}

	public Texture cloneBound() {
		Texture text = new Texture(this.haveToBeIn);
		text.setLocation(this.getLocation());
		text.setSize(this.getSize());
		return text;
	}

	public Texture clone() {
		Texture text = new Texture(this.haveToBeIn);
		text.setImage(this.img);
		text.setLocation(this.location.getX(), this.location.getY());
		text.setRightOrLeft((boolean) this.RightOrLeft);
		text.setSize((int) this.size.getWidth(), (int) this.size.getHeight());
		return text;
	}

	public Boolean getHaveToBeIn() {
		return this.haveToBeIn;
	}

	public void setHaveToBeIn(Boolean bool) {
		this.haveToBeIn = bool;
	}

	public void setTMove(Thread t) {
		this.tMove = t;
	}

	public Thread getTMove() {
		return this.tMove;
	}
}
