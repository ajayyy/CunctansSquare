package app.ajay.ld42;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class Block {
	
	int type = -1;
	
	public int x, y;
	
	Color color;
	
	boolean open = false;
	
	//animation variables
	boolean animating;
	int startX, startY;
	int targetX, targetY;
	float percentageToTarget;
	float percentageIncrement = 0.1f;
	
	//shrink animation
	boolean shrinkAnimating;
	int shrinkDirection;
	
	public Block(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void update(Level level, Main main) {
		if (animating) {
			percentageToTarget += percentageIncrement;
			
			if(percentageToTarget > 1) {
				animating = false;
				percentageToTarget = 0;
			}
		}
		
		if (shrinkAnimating) {
			percentageToTarget += percentageIncrement;
			
			if(percentageToTarget > 1) {
				shrinkAnimating = false;
				percentageToTarget = 0;
				
				level.blocks.remove(this);
			}
		}
	}
	
	public void playTurn(Level level, Main main) {
		
	}
	
	public void render(Level level, Main main) {
		main.shapeRenderer.begin(ShapeType.Filled);
		
		main.shapeRenderer.setColor(color);
		
		float centerAmountX = Gdx.graphics.getWidth() / 2 - (level.levelConfig.blocks[0].length * level.levelConfig.blockSize) / 2;
		float centerAmountY = Gdx.graphics.getHeight() / 2 - (level.levelConfig.blocks.length * level.levelConfig.blockSize) / 2;
		
		float renderX = x;
		float renderY = y;
		
		float width = level.levelConfig.blockSize;
		float height = level.levelConfig.blockSize;
		
		if (animating) {
			renderX = startX + (targetX - startX) * percentageToTarget;
			renderY = startY + (targetY - startY) * percentageToTarget;
		}
		
		if (shrinkAnimating) {
			switch (shrinkDirection) {
				case 0:
					height = height * (1 - percentageToTarget);
					renderY += percentageToTarget;
					break;
				case 1:
					height = height * (1 - percentageToTarget);
					break;
				case 2:
					width = width * (1 - percentageToTarget);
					renderX += percentageToTarget;
					break;
				case 3:
					width = width * (1 - percentageToTarget);
					break;
			}
		}
		
		main.shapeRenderer.box(renderX * level.levelConfig.blockSize + centerAmountX, renderY * level.levelConfig.blockSize + centerAmountY, 0, width, height, 0);
		
		main.shapeRenderer.end();
	}
	
	public void destroy (Level level, Main main, int direction) {
		shrinkAnimating = true;
		shrinkDirection = direction;
	}
}
