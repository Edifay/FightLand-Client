package fight_land.game.listeners;

import static fight_land.game.listeners.KeyBind.DOWN;
import static fight_land.game.listeners.KeyBind.JUMP;
import static fight_land.game.listeners.KeyBind.LEFT;
import static fight_land.game.listeners.KeyBind.RIGHT;
import static fight_land.game.listeners.KeyBind.ROULADE;
import static fight_land.game.listeners.KeyBind.SPECIAL;

import static fight_land.game.listeners.KeyBind.OTHER_DOWN;
import static fight_land.game.listeners.KeyBind.OTHER_JUMP;
import static fight_land.game.listeners.KeyBind.OTHER_LEFT;
import static fight_land.game.listeners.KeyBind.OTHER_RIGHT;
import static fight_land.game.listeners.KeyBind.OTHER_ROULADE;
import static fight_land.game.listeners.KeyBind.OTHER_ATTACK1;
import static fight_land.game.listeners.KeyBind.OTHER_ATTACK2;
import static fight_land.game.listeners.KeyBind.OTHER_SPECIAL;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import fight_land.game.loop.Game;

public class GameListeners implements KeyListener, MouseListener {

	private Game game;

	public GameListeners(Game game) {
		this.game = game;
	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getButton() == 1) {
			this.game.setATTACK1(true);
		} else if (e.getButton() == 3) {
			this.game.setATTACK2(true);
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (e.getButton() == 1) {
			this.game.setATTACK1(false);
		} else if (e.getButton() == 3) {
			this.game.setATTACK2(false);
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
//		System.out.println("Pressed : "+e.getKeyCode());

		if (e.getKeyCode() == JUMP) {
			this.game.setJUMP(true);
		} else if (e.getKeyCode() == DOWN) {
			this.game.setDOWN(true);
		} else if (e.getKeyCode() == RIGHT) {
			this.game.setRIGHT(true);
		} else if (e.getKeyCode() == LEFT) {
			this.game.setLEFT(true);
		} else if (e.getKeyCode() == ROULADE) {
			this.game.setROULADE(true);
		} else if (e.getKeyCode() == SPECIAL) {
			this.game.setATTACK3(true);
		} else

		if (e.getKeyCode() == OTHER_JUMP) {
			this.game.setOtherJUMP(true);
		} else if (e.getKeyCode() == OTHER_DOWN) {
			this.game.setOtherDOWN(true);
		} else if (e.getKeyCode() == OTHER_RIGHT) {
			this.game.setOtherLEFT(true);
		} else if (e.getKeyCode() == OTHER_LEFT) {
			this.game.setOtherRIGHT(true);
		} else if (e.getKeyCode() == OTHER_ROULADE) {
			this.game.setOtherROULADE(true);
		} else if (e.getKeyCode() == OTHER_ATTACK1) {
			this.game.setOtherATTACK1(true);
		} else if (e.getKeyCode() == OTHER_ATTACK2) {
			this.game.setOtherATTACK2(true);
		} else if (e.getKeyCode() == OTHER_SPECIAL) {
			this.game.setOtherSPECIAL(true);
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == JUMP) {
			this.game.setJUMP(false);
		} else if (e.getKeyCode() == DOWN) {
			this.game.setDOWN(false);
		} else if (e.getKeyCode() == RIGHT) {
			this.game.setRIGHT(false);
		} else if (e.getKeyCode() == LEFT) {
			this.game.setLEFT(false);
		} else if (e.getKeyCode() == ROULADE) {
			this.game.setROULADE(false);
		} else if (e.getKeyCode() == SPECIAL) {
			this.game.setATTACK3(false);
		} else

		if (e.getKeyCode() == OTHER_JUMP) {
			this.game.setOtherJUMP(false);
		} else if (e.getKeyCode() == OTHER_DOWN) {
			this.game.setOtherDOWN(false);
		} else if (e.getKeyCode() == OTHER_RIGHT) {
			this.game.setOtherLEFT(false);
		} else if (e.getKeyCode() == OTHER_LEFT) {
			this.game.setOtherRIGHT(false);
		} else if (e.getKeyCode() == OTHER_ROULADE) {
			this.game.setOtherROULADE(false);
		}else if (e.getKeyCode() == OTHER_ATTACK1) {
			this.game.setOtherATTACK1(false);
		} else if (e.getKeyCode() == OTHER_ATTACK2) {
			this.game.setOtherATTACK2(false);
		} else if (e.getKeyCode() == OTHER_SPECIAL) {
			this.game.setOtherSPECIAL(false);
		}
	}
}
