package fight_land.game.load;

import static fight_land.AssetsContener.assets;

import java.awt.Rectangle;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import fight_land.game.render.collisions.CollisionsDetector;
import fight_land.game.render.graphics.Sprites;

public class LoadGame {

	public final int atLoad = 3;
	public int loaded;

	public LoadGame() {

	}

	public void start() {
		ArrayList<Sprites> sprites1 = new ArrayList<Sprites>();

		try {// load stand right
			sprites1.add(new Sprites(
					ImageIO.read(getClass().getResource("/assets/game/champions/champion_1/stand_cosmo.png")), 171,
					205));
		} catch (IOException e) {
			e.printStackTrace();
		}
		loaded++;

		sprites1.add(sprites1.get(0).getSpritesFlip());
		loaded++;

		try {// load walk right
			sprites1.add(new Sprites(
					ImageIO.read(getClass().getResource("/assets/game/champions/champion_1/walk_cosmo.png")), 192,
					210));
		} catch (IOException e) {
			e.printStackTrace();
		}
		loaded++;

		sprites1.add(sprites1.get(2).getSpritesFlip());
		loaded++;

		try {// load fall right
			sprites1.add(new Sprites(
					ImageIO.read(getClass().getResource("/assets/game/champions/champion_1/fall_cosmo.png")), 156,
					224));
		} catch (IOException e) {
			e.printStackTrace();
		}
		loaded++;

		sprites1.add(sprites1.get(4).getSpritesFlip());
		loaded++;

		assets.getAllPlayersAndSprites().add(sprites1);
		
		try {// load roulade right
			sprites1.add(new Sprites(
					ImageIO.read(getClass().getResource("/assets/game/champions/champion_1/roulade_cosmo.png")), 274,
					259));
		} catch (IOException e) {
			e.printStackTrace();
		}
		loaded++;

		sprites1.add(sprites1.get(6).getSpritesFlip());
		loaded++;

		assets.getAllPlayersAndSprites().add(sprites1);
		
		try {// load attack1 right
			sprites1.add(new Sprites(
					ImageIO.read(getClass().getResource("/assets/game/champions/champion_1/attack1_cosmo.png")), 227,
					215));
		} catch (IOException e) {
			e.printStackTrace();
		}
		loaded++;

		sprites1.add(sprites1.get(8).getSpritesFlip());
		loaded++;
		
		try {// load cosmo moon right
			sprites1.add(new Sprites(
					ImageIO.read(getClass().getResource("/assets/game/champions/champion_1/moon_cosmo.png")), 132,
					206));
		} catch (IOException e) {
			e.printStackTrace();
		}
		loaded++;

		sprites1.add(sprites1.get(10).getSpritesFlip());
		loaded++;
		
		try {// load moon right
			sprites1.add(new Sprites(
					ImageIO.read(getClass().getResource("/assets/game/champions/champion_1/moon_anim.png")), 98,
					90));
		} catch (IOException e) {
			e.printStackTrace();
		}
		loaded++;

		sprites1.add(sprites1.get(12).getSpritesFlip());
		loaded++;
		
		try {// load cosmo fuze right
			sprites1.add(new Sprites(
					ImageIO.read(getClass().getResource("/assets/game/champions/champion_1/fuze_cosmo.png")), 185,
					259));
		} catch (IOException e) {
			e.printStackTrace();
		}
		loaded++;

		sprites1.add(sprites1.get(14).getSpritesFlip());
		loaded++;
		
		try {// load fuse right
			sprites1.add(new Sprites(
					ImageIO.read(getClass().getResource("/assets/game/champions/champion_1/fuze_anim.png")), 380,
					360));
		} catch (IOException e) {
			e.printStackTrace();
		}
		loaded++;

		sprites1.add(sprites1.get(16).getSpritesFlip());
		loaded++;

		assets.getAllPlayersAndSprites().add(sprites1);

		try {// load map 1
			assets.getMaps().add(ImageIO.read(getClass().getResource("/assets/game/maps/lavaCave/cave_hidebox.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		loaded++;
		
		
		

		CollisionsDetector.rectanglesCollisions = new ArrayList<Rectangle>();
		CollisionsDetector.rectanglesCollisions.add(new Rectangle(3, 336, 525, 17));
		CollisionsDetector.rectanglesCollisions.add(new Rectangle(1397, 336, 525, 17));

		CollisionsDetector.rectanglesCollisions.add(new Rectangle(3, 781, 660, 25));
		CollisionsDetector.rectanglesCollisions.add(new Rectangle(1256, 781, 575, 25));

	}
}