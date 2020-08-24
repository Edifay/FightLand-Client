package fight_land;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import fight_land.game.render.graphics.Sprites;

public class AssetsContener {

	public static AssetsContener assets;

	private ArrayList<BufferedImage> backgrounds;
	private ArrayList<BufferedImage> IconHero;
	private ArrayList<BufferedImage> Maps;
	private ArrayList<ArrayList<Sprites>> allPlayersAndSprites;

	public AssetsContener() {
		this.backgrounds = new ArrayList<BufferedImage>();
		this.IconHero = new ArrayList<BufferedImage>();
		this.allPlayersAndSprites = new ArrayList<ArrayList<Sprites>>();
		this.Maps = new ArrayList<BufferedImage>();
	}

	public void setThisAssetsAsAssets() {
		assets = this;
	}

	public synchronized ArrayList<BufferedImage> getBackgrounds() {
		return backgrounds;
	}

	public ArrayList<BufferedImage> getIconHero() {
		return IconHero;
	}

	public ArrayList<ArrayList<Sprites>> getAllPlayersAndSprites() {
		return this.allPlayersAndSprites;
	}

	public ArrayList<BufferedImage> getMaps() {
		return this.Maps;
	}
}