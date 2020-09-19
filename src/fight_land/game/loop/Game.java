package fight_land.game.loop;

import static fight_land.frame.FightLandFrame.frame;

import java.util.ArrayList;

import fight_land.AssetsContener;
import fight_land.game.listeners.GameListeners;
import fight_land.game.loop.mouvement.Mouvement;
import fight_land.game.render.GraphicsRender;
import fight_land.game.render.animation.animation_type.AnimationChampionManager;
import fight_land.game.render.animation.animation_type.AnimationMapManager;
import fight_land.game.render.animation.specific_animation.animationChampion.AnimationCosmonaute;
import fight_land.game.render.graphics.Texture;

public class Game {

	private GraphicsRender render;
	private Texture actual_player;
	private Mouvement mouvementActual_Player;
	private AnimationChampionManager animationManagerForActualPlayer;

	public static Boolean serverSendStart = false;

	private ArrayList<AnimationChampionManager> champions;
	private ArrayList<Player> players;
	private Boolean player_loaded;

	private Thread loop;
	private Boolean RIGHT = false;
	private Boolean LEFT = false;
	private Boolean JUMP = false;
	private Boolean DOWN = false;
	private Boolean ROULADE = false;
	private Boolean ATTACK1 = false;
	private Boolean ATTACK2 = false;
	private Boolean SPECIAL = false;
	private float hp;

	private Boolean otherRIGHT = false;
	private Boolean otherLEFT = false;
	private Boolean otherJUMP = false;
	private Boolean otherDOWN = false;
	private Boolean otherROULADE = false;
	private Boolean otherATTACK1 = false;
	private Boolean otherATTACK2 = false;
	private Boolean otherSPECIAL = false;

	public Game(GraphicsRender render) {
		this.render = render;
		this.actual_player = new Texture(true);
		this.hp = 1;
		this.players = new ArrayList<Player>();
		this.setPlayer_loaded(false);
		this.player_loaded = false;
	}

	public void start() {
		this.champions = new ArrayList<AnimationChampionManager>();

		AnimationMapManager mapAnimation = new AnimationMapManager(this.render, new Texture(false),
				AssetsContener.assets.getMaps().get(0), this);
		mapAnimation.setVisible(true);

		while (!this.player_loaded) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		for (int i = 0; i < this.players.size(); i++) {
			if (this.players.get(i).getIsMe()) {// if the actual player
				System.out.println("Creation du players : YOU !");

				this.actual_player = new Texture(true);

				// Selection of the good AnimationMangerAndSprites
				switch (this.players.get(i).getTextureNumber()) {
				case 0:
					this.animationManagerForActualPlayer = new AnimationCosmonaute(this.render, this.actual_player,
							this);
					break;

				default:
					System.out.println("Can't create this: " + this.players.get(i).getTextureNumber());
					break;
				}
				// creating Mouvement
				this.mouvementActual_Player = new Mouvement(this, this.animationManagerForActualPlayer);

				this.players.get(i).setTexture(this.actual_player);
				this.players.get(i).setAnimationManger(this.animationManagerForActualPlayer);
				this.players.get(i).getAnimationManger().forceSetLocation(250, 100);
				this.players.get(i).getAnimationManger().forceSetSize(10, 10);
				this.players.get(i).getAnimationManger().setVisible(true);
				this.players.get(i).getAnimationManger().stand(true);

			} else {
				this.players.get(i).setTexture(new Texture(true));
				switch (this.players.get(i).getTextureNumber()) {
				case 0:
					this.players.get(i).setAnimationManger(
							new AnimationCosmonaute(this.render, this.players.get(i).getTexture(), this));
					break;

				default:
					System.out.println("Can't create this: " + this.players.get(i).getTextureNumber());
					break;
				}
				System.out.println("Creation du players :" + this.players.get(i).getName());
				this.players.get(i).getAnimationManger().forceSetLocation(250, 100);
				this.players.get(i).getAnimationManger().forceSetSize(200, 200);
				this.players.get(i).getAnimationManger().setVisible(true);
				this.players.get(i).getAnimationManger().stand(true);
			}
		}

//		while (!serverSendStart) {
//		}

		// ADD listeners
		GameListeners gameListeners = new GameListeners(this);
		frame.addKeyListener(gameListeners);
		frame.addMouseListener(gameListeners);

		this.mouvementActual_Player.start();

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

	public Boolean getATTACK1() {
		return this.ATTACK1;
	}

	public void setATTACK1(Boolean aTTACK1) {
		ATTACK1 = aTTACK1;
	}

	public Boolean getATTACK2() {
		return this.ATTACK2;
	}

	public void setATTACK2(Boolean aTTACK2) {
		ATTACK2 = aTTACK2;
	}

	public Boolean getATTACK3() {
		return this.SPECIAL;
	}

	public void setATTACK3(Boolean aTTACK3) {
		SPECIAL = aTTACK3;
	}

	public void setHP(float hp) {
		this.hp = hp;
	}

	public float getHP() {
		return this.hp;
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

	public ArrayList<AnimationChampionManager> getChampions() {
		return this.champions;
	}

	public void setChampions(ArrayList<AnimationChampionManager> champions) {
		this.champions = champions;
	}

	public Boolean getOtherATTACK1() {
		return this.otherATTACK1;
	}

	public void setOtherATTACK1(Boolean otherATTACK1) {
		this.otherATTACK1 = otherATTACK1;
	}

	public Boolean getOtherATTACK2() {
		return this.otherATTACK2;
	}

	public void setOtherATTACK2(Boolean otherATTACK2) {
		this.otherATTACK2 = otherATTACK2;
	}

	public Boolean getOtherSPECIAL() {
		return this.otherSPECIAL;
	}

	public void setOtherSPECIAL(Boolean otherSPECIAL) {
		this.otherSPECIAL = otherSPECIAL;
	}

	public Boolean getPlayer_loaded() {
		return this.player_loaded;
	}

	public void setPlayer_loaded(Boolean player_loaded) {
		this.player_loaded = player_loaded;
	}

	public ArrayList<Player> getPlayers() {
		return this.players;
	}
}
