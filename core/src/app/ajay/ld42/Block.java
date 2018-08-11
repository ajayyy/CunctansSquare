package app.ajay.ld42;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class Block {
	
	public float x, y;
	
	Color color;
	
	public Block(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public void update() {
		
	}
	
	public void render(Level level, Main main) {
		main.shapeRenderer.begin(ShapeType.Filled);
		
		main.shapeRenderer.setColor(color);
		main.shapeRenderer.box(x * level.blockSize, y * level.blockSize, 0, level.blockSize, level.blockSize, 0);
		
		main.shapeRenderer.end();
	}
}
