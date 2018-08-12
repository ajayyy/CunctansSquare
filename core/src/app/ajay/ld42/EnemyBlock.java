package app.ajay.ld42;

import com.badlogic.gdx.graphics.Color;

public class EnemyBlock extends Block{

	public EnemyBlock(int x, int y) {
		super(x, y);
		
		color = Color.RED;
	}
	
	public void update(Level level, Main main) {
		super.update(level, main);
	}
	
	public void playTurn(Level level, Main main) {
		super.playTurn(level, main);
		
		int direction = level.rand.nextInt(4);
		
		int oldX = x;
		int oldY = y;
		
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
		} else {
			//setup animation
			animating = true;
			startX = oldX;
			startY = oldY;
			targetX = x;
			targetY = y;
			percentageToTarget = 0;
		}
	}
}
