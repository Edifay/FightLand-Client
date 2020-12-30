package fight_land.game.load;

import static fight_land.AssetsContener.assets;

import java.awt.Rectangle;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import fight_land.game.Init;
import fight_land.game.render.collisions.CollisionsDetector;
import fight_land.game.render.graphics.Sprites;

public class LoadGame {

	public final int atLoad = 3;
	public int loaded;
	
	private Boolean loadFinish;

	public LoadGame() {
		this.loadFinish = false;
	}

	public void load(int textureAtLoad) {
		switch (textureAtLoad) {
		case 0: {
			this.loadCosmonaute();
			break;
		}
		case 10: {
			this.loadLavaMap();
			break;
		}
		default:
			System.out.println("Can't Load : " + textureAtLoad);
			break;
		}
	}

	private void loadCosmonaute() {
		ArrayList<Sprites> sprites = new ArrayList<Sprites>();

		try {// load stand right
			sprites.add(new Sprites(
					ImageIO.read(getClass().getResource("/assets/game/champions/champion_1/stand_cosmo.png")), 171,
					205));
		} catch (IOException e) {
			e.printStackTrace();
		}
		loaded++;

		sprites.add(sprites.get(0).getSpritesFlip());
		loaded++;

		try {// load walk right
			sprites.add(new Sprites(
					ImageIO.read(getClass().getResource("/assets/game/champions/champion_1/walk_cosmo.png")), 192,
					210));
		} catch (IOException e) {
			e.printStackTrace();
		}
		loaded++;

		sprites.add(sprites.get(2).getSpritesFlip());
		loaded++;

		try {// load fall right
			sprites.add(new Sprites(
					ImageIO.read(getClass().getResource("/assets/game/champions/champion_1/fall_cosmo.png")), 156,
					224));
		} catch (IOException e) {
			e.printStackTrace();
		}
		loaded++;

		sprites.add(sprites.get(4).getSpritesFlip());
		loaded++;

		try {// load roulade right
			sprites.add(new Sprites(
					ImageIO.read(getClass().getResource("/assets/game/champions/champion_1/roulade_cosmo.png")), 274,
					259));
		} catch (IOException e) {
			e.printStackTrace();
		}
		loaded++;

		sprites.add(sprites.get(6).getSpritesFlip());
		loaded++;

		try {// load attack1 right
			sprites.add(new Sprites(
					ImageIO.read(getClass().getResource("/assets/game/champions/champion_1/attack1_cosmo.png")), 227,
					215));
		} catch (IOException e) {
			e.printStackTrace();
		}
		loaded++;

		sprites.add(sprites.get(8).getSpritesFlip());
		loaded++;

		try {// load cosmo moon right
			sprites.add(new Sprites(
					ImageIO.read(getClass().getResource("/assets/game/champions/champion_1/moon_cosmo.png")), 171,
					216));
		} catch (IOException e) {
			e.printStackTrace();
		}
		loaded++;

		sprites.add(sprites.get(10).getSpritesFlip());
		loaded++;

		try {// load moon right
			sprites.add(new Sprites(
					ImageIO.read(getClass().getResource("/assets/game/champions/champion_1/moon_anim.png")), 98, 90));
		} catch (IOException e) {
			e.printStackTrace();
		}
		loaded++;

		sprites.add(sprites.get(12).getSpritesFlip());
		loaded++;

		try {// load cosmo fuze right
			sprites.add(new Sprites(
					ImageIO.read(getClass().getResource("/assets/game/champions/champion_1/fuze_cosmo.png")), 185,
					259));
		} catch (IOException e) {
			e.printStackTrace();
		}
		loaded++;

		sprites.add(sprites.get(14).getSpritesFlip());
		loaded++;

		try {// load fuse right
			sprites.add(new Sprites(
					ImageIO.read(getClass().getResource("/assets/game/champions/champion_1/fuze_anim.png")), 380, 360));
		} catch (IOException e) {
			e.printStackTrace();
		}
		loaded++;

		sprites.add(sprites.get(16).getSpritesFlip());
		loaded++;

		try {// load tilt right
			sprites.add(new Sprites(
					ImageIO.read(getClass().getResource("/assets/game/champions/champion_1/tilt_cosmo.png")), 283,
					272));
		} catch (IOException e) {
			e.printStackTrace();
		}
		loaded++;

		sprites.add(sprites.get(18).getSpritesFlip());
		loaded++;

		assets.setComonauteSprites(sprites);
	}
	
	public void loadAll(ArrayList<Integer> nbAtLoad) {
		ArrayList<Integer> nbAfter = new ArrayList<Integer>();

		for (int i = 0; i < nbAtLoad.size(); i++) {// don't reapeat load
			Boolean find = false;
			for (int a = 0; a < nbAfter.size(); a++) {
				if (nbAfter.get(a).intValue() == nbAtLoad.get(i).intValue()) {
					find = true;
				}
			}
			if (!find)
				nbAfter.add(nbAtLoad.get(i));
		}
		
		for (int i = 0; i < nbAfter.size(); i++)// load texture one by one
			this.load(nbAfter.get(i).intValue());

		this.setLoadFinish(true);
	}

	private void loadLavaMap() {
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

	public Boolean getLoadFinish() {
		return this.loadFinish;
	}

	public void setLoadFinish(Boolean loadFinish) {
		this.loadFinish = loadFinish;
	}
}