package app.ajay.ld42;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class Level {
	Main main;
	
	ArrayList<Block> blocks = new ArrayList<Block>();
	
	int blockSize;
	
	public Level(Main main, LevelConfiguration levelConfig) {
		this.main = main;
		
		blockSize = levelConfig.blockSize;
		
		
		//create block list out of block plan in the level config
		for (int y = 0; y < levelConfig.blocks.length; y++) {
			for (int x = 0; x < levelConfig.blocks[y].length; x++) {
				blocks.add(createBlock(levelConfig.blocks[x][y], x, y));
			}
		}
	}
	
	public void update() {
		
	}
	
	public void render() {
		main.shapeRenderer.begin(ShapeType.Filled);
		
		main.shapeRenderer.setColor(Color.BLUE);
		main.shapeRenderer.box(0, 0, 0, blockSize, blockSize, 0);
		
		main.shapeRenderer.end();
	}
	
	public static Block createBlock(int type, int x, int y) {
		switch(type) {
			case 0:
				return new PlayerBlock(x, y);
			case 1:
				return new EnemyBlock(x, y);
		}
		
		System.err.println("Invalid block type: " + type);
		return null;
	}
}
