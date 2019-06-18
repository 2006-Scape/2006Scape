package redone.game.players;

public class PlayerAction {

	Client c;

	public PlayerAction(Client c) {
		this.c = c;
	}

	public boolean inAction = false;
	public boolean canWalk = true;
	public boolean canEat = true;

	public boolean setAction(boolean action) {
		return inAction = action;
	}

	public boolean checkAction() {
		return inAction;
	}

	public boolean canWalk(boolean walk) {
		return canWalk = walk;
	}

	public boolean checkWalking() {
		return canWalk;
	}

	public boolean canEat(boolean eat) {
		return canEat = eat;
	}

	public boolean checkEating() {
		return canEat;
	}
}
