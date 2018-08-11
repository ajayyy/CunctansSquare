package app.ajay.ld42;

import java.util.ArrayList;

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
				int blockType = levelConfig.blocks[y][x];
				
				if(blockType != -1) {
					blocks.add(createBlock(blockType, x, y));
				}
			}
		}
	}
	
	public void update() {
		
	}
	
	public void render() {
		for (Block block : blocks) {
			block.render(this, main);
		}
	}
	
	public static Block createBlock(int type, int x, int y) {
		switch(type) {
			case 0:
				return new OpenBlock(x, y);
			case 1:
				return new PlayerBlock(x, y);
			case 2:
				return new EnemyBlock(x, y);
		}
		
		System.err.println("Invalid block type: " + type);
		return null;
	}
}
