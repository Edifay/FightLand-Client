package fight_land.game.render.collisions;

import java.awt.Rectangle;
import java.util.ArrayList;

import fight_land.game.render.graphics.Texture;

public class CollisionsDetector {

	public static ArrayList<Rectangle> rectanglesCollisions;

	public static Boolean isThisTextureTouchGravity(Texture texture) {
		Rectangle recBottom = new Rectangle((int) (texture.getLocation().getX()),
				(int) ((int) texture.getLocation().getY() + (texture.getSize().getHeight() - 2)),
				(int) texture.getSize().getWidth(), 2);
		Boolean find = false;

		for (int i = 0; i < rectanglesCollisions.size(); i++) {
			if (recBottom.getX() < rectanglesCollisions.get(i).getX() + rectanglesCollisions.get(i).getWidth()
					&& recBottom.getX() + recBottom.getWidth() > rectanglesCollisions.get(i).getX()
					&& recBottom.getY() < rectanglesCollisions.get(i).getY() + rectanglesCollisions.get(i).getHeight()
					&& recBottom.getY() + recBottom.getHeight() > rectanglesCollisions.get(i).getLocation().getY()) {
				find = true;
			}
		}
		return find;
	}
}