package app.ajay.ld42;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class Block {
	
	int type = -1;
	
	public int x, y;
	
	Color color;
	
	public Block(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void update(Level level, Main main) {
		
	}
	
	public void render(Level level, Main main) {
		main.shapeRenderer.begin(ShapeType.Filled);
		
		main.shapeRenderer.setColor(color);
		
		float centerAmountX = Gdx.graphics.getWidth() / 2 - (level.levelConfig.blocks[0].length * level.blockSize) / 2;
		float centerAmountY = Gdx.graphics.getHeight() / 2 - (level.levelConfig.blocks.length * level.blockSize) / 2;
		
		main.shapeRenderer.box(x * level.blockSize + centerAmountX, y * level.blockSize + centerAmountY, 0, level.blockSize, level.blockSize, 0);
		
		main.shapeRenderer.end();
	}
}
