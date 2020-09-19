package fight_land.game.loop;

import fight_land.game.render.animation.animation_type.AnimationChampionManager;
import fight_land.game.render.graphics.Texture;

public class Player {

	private Boolean me; // 2
	private long ID; // 1
	private int textureNumber; // 3
	private Texture texture;
	private AnimationChampionManager animationManger;
	private String name; // 4

	public Player(long ID) {
		this.me = false;
		this.ID = ID;
	}

	public Player(long ID, Boolean me, int textureNumber, String name) {
		this.me = me;
		this.ID = ID;
		this.textureNumber = textureNumber;
		this.name = name;
	}

	public Boolean getIsMe() {
		return this.me;
	}

	public void setIsMe(Boolean bool) {
		this.me = bool;
	}

	public long getID() {
		return this.ID;
	}

	public int getTextureNumber() {
		return this.textureNumber;
	}

	public void setTextureNumber(int texture) {
		this.textureNumber = texture;
	}

	public Texture getTexture() {
		return this.texture;
	}

	public void setTexture(Texture texture) {
		this.texture = texture;
	}

	public AnimationChampionManager getAnimationManger() {
		return this.animationManger;
	}

	public void setAnimationManger(AnimationChampionManager animationManger) {
		this.animationManger = animationManger;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
