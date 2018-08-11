package app.ajay.ld42;

import java.util.ArrayList;

public class Level {
	Main main;
	
	ArrayList<Block> blocks = new ArrayList<Block>();
	
	Player player;
	
	int blockSize;
	
	LevelConfiguration levelConfig;
	
	public Level(Main main, LevelConfiguration levelConfig) {
		this.main = main;
		
		blockSize = levelConfig.blockSize;
		
		this.levelConfig = levelConfig;
		
		//create block list out of block plan in the level config
		for (int y = 0; y < levelConfig.blocks.length; y++) {
			for (int x = 0; x < levelConfig.blocks[y].length; x++) {
				int blockType = levelConfig.blocks[y][x];
				
				if(blockType != -1) {
					blocks.add(createBlock(blockType, x, y));
				}
			}
		}
		
		player = new Player(levelConfig.playerX, levelConfig.playerY);
	}
	
	public void update() {
		for (Block block : blocks) {
			block.update(this, main);
		}
		
		player.update(this, main);
	}
	
	public void render() {
		for (Block block : blocks) {
			block.render(this, main);
		}
		
		player.render(this, main);
	}
	
	public Block getBlock(int x, int y) {
		for (int i = 0; i < blocks.size(); i++) {
			if (blocks.get(i).x == x && blocks.get(i).y == y) {
				return blocks.get(i);
			}
		}
		
		System.err.println("Block at x: " + x + " y: " + y + " does not exist");
		return null;
	}
	
	public static Block createBlock(int type, int x, int y) {
		switch(type) {
			case 0:
				return new OpenBlock(x, y);
			case 1:
				return new EnemyBlock(x, y);
		}
		
		System.err.println("Invalid block type: " + type);
		return null;
	}
}
