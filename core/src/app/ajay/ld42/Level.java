package app.ajay.ld42;

import java.awt.print.Printable;
import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.graphics.g3d.utils.FirstPersonCameraController;
import com.badlogic.gdx.math.Vector2;


public class Level {
	Main main;
	
	ArrayList<Block> blocks = new ArrayList<Block>();
	
	Player player;
	
	int blockSize;
	
	LevelConfiguration levelConfig;
	
	Random rand;
	
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
		
		rand = new Random();
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
	
	//called by the player when a turn has started, the non player events are triggered from here
	public void playTurn() {
		//remove random block
		ArrayList<Block> edgeBlocks = findEdgeBlocks();
		
		ArrayList<Vector2> path = findPath(player.x, player.y, levelConfig.endX, levelConfig.endY);
		
		if(path != null) {
			
			ArrayList<Block> usableBlocks = new ArrayList<Block>();
			for(Block block : edgeBlocks) {
				if(!vectorListContains(path, new Vector2(block.x, block.y))) {
					usableBlocks.add(block);
				}
			}
			
			int randomBlockIndex = rand.nextInt(usableBlocks.size());
			
			blocks.remove(usableBlocks.get(randomBlockIndex));
		}
	}
	
	public ArrayList<Block> findEdgeBlocks(){
		ArrayList<Block> edgeBlocks = new ArrayList<Block>();
		
		for(Block block : blocks) {
			Block right = getBlock(block.x + 1, block.y);
			Block left = getBlock(block.x - 1, block.y);
			Block up = getBlock(block.x, block.y + 1);
			Block down = getBlock(block.x, block.y - 1);
			
			if (right == null || left == null || up == null || down == null) {
				edgeBlocks.add(block);
			}
		}
		
		return edgeBlocks;
	}
	
	public ArrayList<Vector2> findPath(int startX, int startY, int endX, int endY) {
		//maximum amount of blocks before giving up and trying another path entirely
		int maximumPathLength = 15;
		
		//tries left when removing the block for not being an open block
		int removeTriesLeft = 10;
		
		//tries left when clearing the whole path
		int clearingTriesLeft = 10;
		
		ArrayList<Vector2> path = new ArrayList<Vector2>();
		path.add(new Vector2(startX, startY));
		
		int direction = 0;
		
		Vector2 currentPosition = path.get(path.size() - 1);
		int lastDirection = -1;
		while(currentPosition.x != endX || currentPosition.y != endY){
			direction = rand.nextInt(4);
			
			while (direction == lastDirection) {
				direction = rand.nextInt(4);
			}
			
			if(direction == 0) {
				path.add(new Vector2(0, 1).add(currentPosition));
			} else if (direction == 1) {
				path.add(new Vector2(0, -1).add(currentPosition));
			} else if (direction == 2) {
				path.add(new Vector2(1, 0).add(currentPosition));
			} else if (direction == 3) {
				path.add(new Vector2(-1, 0).add(currentPosition));
			}
			
			Vector2 newPosition = path.get(path.size() - 1);
			
			Block currentBlock = getBlock(newPosition.x, newPosition.y);
			
			if (currentBlock == null || !currentBlock.open) {
				path.remove(path.size() - 1);
				System.out.println("removed");
				removeTriesLeft--;
			} else {
				removeTriesLeft = 10;
			}
			
			if (path.size() > maximumPathLength || removeTriesLeft < 0) {
				path.clear();
				path.add(new Vector2(startX, startY));
				
				removeTriesLeft = 10;

				clearingTriesLeft--;
			}
			
			if (clearingTriesLeft < 0) {
				System.out.println("cap hit");
				return null;
			}
			
			currentPosition = path.get(path.size() - 1);
			lastDirection = direction;
		}
		
		return path;
	}
	
	public Block getBlock(float x, float y) {
		for (int i = 0; i < blocks.size(); i++) {
			if (blocks.get(i).x == x && blocks.get(i).y == y) {
				return blocks.get(i);
			}
		}
		
		return null;
	}
	
	public static Block createBlock(int type, int x, int y) {
		switch(type) {
			case 0:
				return new OpenBlock(x, y);
			case 1:
				return new FinishBlock(x, y);
		}
		
		System.err.println("Invalid block type: " + type);
		return null;
	}
	
	public static boolean vectorListContains(ArrayList<Vector2> list, Vector2 position) {
		for(Vector2 vector : list) {
			if(vector.epsilonEquals(position)) {
				return true;
			}
//			System.out.println(vector.toString() + " " + position.toString());
		}
		
		return false;
	}
}
