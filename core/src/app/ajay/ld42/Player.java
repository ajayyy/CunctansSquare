package app.ajay.ld42;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;

public class Player extends Block{
	
	int turnQueueDirection = -1;

	public Player(int x, int y) {
		super(x, y);
		
		color = Color.BLUE;
	}
	
	public void update(Level level, Main main) {
		checkForMovement(level, main);
		
		if(level.turnQueued && level.readyForTurn()) {
			level.turnQueued = false;
			
			playTurn(level, main, turnQueueDirection);
		}
	}
	
	public void checkForMovement(Level level, Main main) {
		int direction = -1;
		
		if(Gdx.input.isKeyJustPressed(Keys.W)) {
			direction = 0;
		} else if(Gdx.input.isKeyJustPressed(Keys.S)) {
			direction = 1;
		} else if(Gdx.input.isKeyJustPressed(Keys.A)) {
			direction = 2;
		} else if(Gdx.input.isKeyJustPressed(Keys.D)) {
			direction = 3;
		}
		
		if (direction != -1) {
			playTurn(level, main, direction);
		}
	}
	
	public void playTurn(Level level, Main main, int direction) {
		
		switch (direction) {
			case 0:
				y++;
				break;
			case 1:
				y--;
				break;
			case 2:
				x--;
				break;
			case 3:
				x++;
				break;
		}
		
		Block currentBlock = level.getBlock(x, y);
		
		if (currentBlock == null || !currentBlock.open) {
			//undo movement
			switch (direction) {
				case 0:
					y--;
					break;
				case 1:
					y++;
					break;
				case 2:
					x++;
					break;
				case 3:
					x--;
					break;
			}
			return;
		} else if(direction != -1) {
			//a turn has been played
			if(level.readyForTurn()) {
				level.playTurn();
			} else {
				level.turnQueued = true;
				turnQueueDirection = direction;
				
				//undo movement
				switch (direction) {
					case 0:
						y--;
						break;
					case 1:
						y++;
						break;
					case 2:
						x++;
						break;
					case 3:
						x--;
						break;
				}
			}
		}
	}

}
