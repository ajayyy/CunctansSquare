package app.ajay.ld42;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;

public class Player extends Block{

	public Player(int x, int y) {
		super(x, y);
		
		color = Color.BLUE;
	}
	
	public void update(Level level, Main main) {
		checkForMovement(level, main);
	}
	
	public void checkForMovement(Level level, Main main) {
		int oldX = x;
		int oldY = y;
		
		if(Gdx.input.isKeyJustPressed(Keys.W)) {
			y++;
		} else if(Gdx.input.isKeyJustPressed(Keys.S)) {
			y--;
		} else if(Gdx.input.isKeyJustPressed(Keys.A)) {
			x--;
		} else if(Gdx.input.isKeyJustPressed(Keys.D)) {
			x++;
		}
		
		Block currentBlock = level.getBlock(x, y);
		
		if (currentBlock == null || currentBlock.type != 0) {
			x = oldX;
			y = oldY;
		}
	}

}
