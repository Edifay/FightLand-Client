package fight_land.game.render.animation.animation_type;

import java.awt.image.BufferedImage;

import fight_land.game.loop.Game;
import fight_land.game.render.GraphicsRender;
import fight_land.game.render.animation.AnimationManager;
import fight_land.game.render.graphics.Texture;

public abstract class AnimationMapManager extends AnimationManager {

	public AnimationMapManager(GraphicsRender render, Texture texture, BufferedImage img, Game game) {
		super(render, texture, game);
		this.texture.setImage(img);
		this.forceSetLocation(0, 0);
		this.forceSetSize(1920, 1080);
	}
}
