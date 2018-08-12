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
		
		if (animating) {
			renderX = startX + (targetX - startX) * percentageToTarget;
			renderY = startY + (targetY - startY) * percentageToTarget;
		}
		
		main.shapeRenderer.box(renderX * level.levelConfig.blockSize + centerAmountX, renderY * level.levelConfig.blockSize + centerAmountY, 0, level.levelConfig.blockSize, level.levelConfig.blockSize, 0);
		
		main.shapeRenderer.end();
	}
}
