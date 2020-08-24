package fight_land.game;

public class Point {

	private float x;
	private float y;

	public Point(float x, float y) {
		this.setX(x);
		this.setY(y);
	}

	public Point() {

	}

	public float getY() {
		return this.y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

}
