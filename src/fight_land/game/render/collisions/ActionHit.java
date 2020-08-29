package fight_land.game.render.collisions;

import fight_land.game.render.animation.animation_type.AnimationChampionManager;
import fight_land.game.render.graphics.Texture;

public interface ActionHit {

	public abstract void runActionHit(Texture collisionOwner, AnimationChampionManager collisionVictim);

}
