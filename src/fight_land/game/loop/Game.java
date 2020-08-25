package fight_land.game.loop;

import static fight_land.frame.FightLandFrame.frame;

import fight_land.AssetsContener;
import fight_land.game.listeners.GameListeners;
import fight_land.game.loop.mouvement.Mouvement;
import fight_land.game.loop.mouvement.MouvementOther;
import fight_land.game.render.GraphicsRender;
import fight_land.game.render.animation.animation_type.AnimationChampionManager;
import fight_land.game.render.animation.animation_type.AnimationMapManager;
import fight_land.game.render.animation.specific_animation.animationChampion.AnimationCosmonaute;
import fight_land.game.render.animation.specific_animation.map.AnimationLavaMapManager;
import fight_land.game.render.graphics.Texture;

public class Game {

	private GraphicsRender render;
	private Texture actual_player;
	private Mouvement mouvementActual_Player;
	private AnimationChampionManager animationManagerForActualPlayer;
	private Thread loop;
	private Boolean RIGHT = false;
	private Boolean LEFT = false;
	private Boolean JUMP = false;
	private Boolean DOWN = false;
	private Boolean ROULADE = false;

	private Boolean otherRIGHT = false;
	private Boolean otherLEFT = false;
	private Boolean otherJUMP = false;
	private Boolean otherDOWN = false;
	private Boolean otherROULADE = false;

	public Game(GraphicsRender render) {
		this.render = render;
		this.actual_player = new Texture();
	}

	public void start() {

		AnimationMapManager mapAnimation = new AnimationLavaMapManager(this.render, new Texture(),
				AssetsContener.assets.getMaps().get(0));
		mapAnimation.setVisible(true);
		
		this.animationManagerForActualPlayer = new AnimationCosmonaute(this.render, new Texture(),
				AssetsContener.assets.getAllPlayersAndSprites().get(0));
		this.animationManagerForActualPlayer.forceSetLocation(600, 100);
		this.animationManagerForActualPlayer.forceSetSize(135, 194);
		this.animationManagerForActualPlayer.setVisible(true);
		this.animationManagerForActualPlayer.setAnimationState(0);
		this.animationManagerForActualPlayer.stand();
		
		new MouvementOther(this, this.animationManagerForActualPlayer).start();

		this.animationManagerForActualPlayer = new AnimationCosmonaute(this.render, this.actual_player,
				AssetsContener.assets.getAllPlayersAndSprites().get(0));
		this.animationManagerForActualPlayer.forceSetLocation(250, 100);
		this.animationManagerForActualPlayer.forceSetSize(135, 194);
		this.animationManagerForActualPlayer.setVisible(true);
		this.animationManagerForActualPlayer.setAnimationState(0);
		this.animationManagerForActualPlayer.stand();

		this.mouvementActual_Player = new Mouvement(this, this.animationManagerForActualPlayer);
		this.mouvementActual_Player.start();
		
		// ADD listeners
		GameListeners gameListeners = new GameListeners(this);
		frame.addKeyListener(gameListeners);
		frame.addMouseListener(gameListeners);

		this.loop = new Thread(new Loop(this));
		this.loop.start();
	}

	public GraphicsRender getRender() {
		return this.render;
	}

	public synchronized Boolean getLEFT() {
		return this.RIGHT;
	}

	public synchronized void setRIGHT(Boolean rIGHT) {
		this.RIGHT = rIGHT;
	}

	public synchronized Boolean getRIGHT() {
		return this.LEFT;
	}

	public synchronized void setLEFT(Boolean lEFT) {
		this.LEFT = lEFT;
	}

	public synchronized Boolean getJUMP() {
		return this.JUMP;
	}

	public synchronized void setJUMP(Boolean jUMP) {
		this.JUMP = jUMP;
	}

	public synchronized Boolean getDOWN() {
		return this.DOWN;
	}

	public synchronized void setDOWN(Boolean dOWN) {
		this.DOWN = dOWN;
	}
	
	public Boolean getROULADE() {
		return this.ROULADE;
	}

	public void setROULADE(Boolean rOULADE) {
		ROULADE = rOULADE;
	}

	public Boolean getOtherRIGHT() {
		return this.otherRIGHT;
	}

	public void setOtherRIGHT(Boolean otherRIGHT) {
		this.otherRIGHT = otherRIGHT;
	}

	public Boolean getOtherLEFT() {
		return this.otherLEFT;
	}

	public void setOtherLEFT(Boolean otherLEFT) {
		this.otherLEFT = otherLEFT;
	}

	public Boolean getOtherJUMP() {
		return this.otherJUMP;
	}

	public void setOtherJUMP(Boolean otherJUMP) {
		this.otherJUMP = otherJUMP;
	}

	public Boolean getOtherDOWN() {
		return this.otherDOWN;
	}

	public void setOtherDOWN(Boolean otherDOWN) {
		this.otherDOWN = otherDOWN;
	}

	public Boolean getOtherROULADE() {
		return this.otherROULADE;
	}

	public void setOtherROULADE(Boolean otherROULADE) {
		this.otherROULADE = otherROULADE;
	}

}
