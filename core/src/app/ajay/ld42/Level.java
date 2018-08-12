package app.ajay.ld42;

import java.awt.print.Printable;
import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.graphics.g3d.utils.FirstPersonCameraController;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;


public class Level {
	Main main;
	
	ArrayList<Block> blocks = new ArrayList<Block>();
	
	Player player;
	
	LevelConfiguration levelConfig;
	
	Random rand;
	
	int turnNumber = 0;
	
	ArrayList<Block> enemies = new ArrayList<Block>();
	
	ArrayList<Block> usableBlocks = new ArrayList<Block>();
	Thread usableBlocksThread = null;
	boolean turnQueued = false;
	
	public Level(Main main, LevelConfiguration levelConfig) {
		this.main = main;
		
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
		
		for (int i = 0; i < levelConfig.enemies.length; i++) {
			enemies.add(new EnemyBlock((int) levelConfig.enemies[i].x, (int) levelConfig.enemies[i].y));
		}
		
		player = new Player(levelConfig.playerX, levelConfig.playerY);
		
		rand = new Random();
		
		loadTurn();
	}
	
	public void update() {
		for (Block block : new ArrayList<Block>(blocks)) {
			block.update(this, main);
		}
		
		for (Block block : new ArrayList<Block>(enemies)) {
			block.update(this, main);
		}
		
		player.update(this, main);
	}
	
	public void render() {
		for (Block block : new ArrayList<Block>(blocks)) {
			block.render(this, main);
		}
		
		for (Block block : new ArrayList<Block>(enemies)) {
			block.render(this, main);
		}
		
		player.render(this, main);
	}
	
	//called by the player when a turn has started, the non player events are triggered from here
	public void playTurn() {
		
		//call all next turn methods
		for (Block block : new ArrayList<Block>(blocks)) {
			block.playTurn(this, main);
		}
		
		for (Block block : new ArrayList<Block>(enemies)) {
			block.playTurn(this, main);
		}
		
		player.playTurn(this, main);
		
		//remove random block
		if(turnNumber % levelConfig.destroyInterval == 0) {
			if (usableBlocks != null) {
				
				//remove current postition from usable blocks
				if (usableBlocks.contains(getBlock(player.x, player.y))) {
					usableBlocks.remove(getBlock(player.x, player.y));
				}
				
				if (usableBlocks.size() > 0) {
					int randomBlockIndex = rand.nextInt(usableBlocks.size());
					
					//the direction the removing animation will go in
					int direction = -1;
					
					Block removedBlock = usableBlocks.get(randomBlockIndex);
					
					Block right = getBlock(removedBlock.x + 1, removedBlock.y);
					Block left = getBlock(removedBlock.x - 1, removedBlock.y);
					Block up = getBlock(removedBlock.x, removedBlock.y + 1);
					Block down = getBlock(removedBlock.x, removedBlock.y - 1);
					
					if (right != null && left == null) {
						direction = 2;
					} else if (left != null && right == null) {
						direction = 3;
					} else if (up != null && down == null) {
						direction = 0;
					} else if (down != null && up == null) {
						direction = 1;
					}
					
					removedBlock.destroy(this, main, direction);
				}
			}
		}
		
		loadTurn();
		
		turnNumber++;
	}
	
	//loads data for the next turn in another thread
	public void loadTurn() {
		usableBlocksThread = new Thread() {
			public void run() {
				ArrayList<Vector2> path = findPath(player.x, player.y, levelConfig.endX, levelConfig.endY, blocks);
				
				if (path != null) {
					ArrayList<Block> nonPathBlocks = new ArrayList<Block>();
					for(Block block : blocks) {
						if(!vectorListContains(path, new Vector2(block.x, block.y))) {
							nonPathBlocks.add(block);
						}
					}
					
					usableBlocks = findEdgeBlocks(nonPathBlocks);
					System.out.println("generated");
				} else {
					usableBlocks = null;
				}
				
				usableBlocksThread = null;
			}
		};
		
		usableBlocksThread.start();
	}
	
	public boolean readyForTurn() {
		return usableBlocksThread == null;
	}
	
	public ArrayList<Block> findEdgeBlocks(ArrayList<Block> blocks){
		ArrayList<Block> edgeBlocks = new ArrayList<Block>();
		
		for(Block block : new ArrayList<Block>(blocks)) {
			ArrayList<Block> surroundingBlocks = new ArrayList<Block>();
			
			surroundingBlocks.add(getBlock(block.x + 1, block.y));
			surroundingBlocks.add(getBlock(block.x - 1, block.y));
			surroundingBlocks.add(getBlock(block.x, block.y + 1));
			surroundingBlocks.add(getBlock(block.x, block.y - 1));
			
			boolean edgeBlock = true;
			
			//if true, this cannot be an edge piece
			boolean noNull = true;
			
			for (Block surroundingBlock : surroundingBlocks) {
				if(surroundingBlock == null) {
					noNull = false;
				}
			}
			
			if (!noNull) {
				ArrayList<Block> otherBlocks = new ArrayList<Block>(this.blocks);
				otherBlocks.remove(block);
				
				for (Block surroundingBlock : surroundingBlocks) {
					if (surroundingBlock != null) {
						ArrayList<Vector2> path = findPath(surroundingBlock.x, surroundingBlock.y, levelConfig.endX, levelConfig.endY, otherBlocks);
						
						if(path == null) {
							edgeBlock = false;
							break;
						}
						
					}
				}
				
				if(edgeBlock) {
					edgeBlocks.add(block);
				}
			}
		}
		
		return edgeBlocks;
	}
	
	public ArrayList<Vector2> findPath(int startX, int startY, int endX, int endY, ArrayList<Block> blocks) {
		//maximum amount of blocks before giving up and trying another path entirely
		int maximumPathLength = 100;
		
		//tries left when removing the block for not being an open block
		int removeTriesLeft = 250;
		
		//tries left when clearing the whole path
		int clearingTriesLeft = 100;
		
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
			
			Vector2 newPosition = new Vector2();
			
			if(direction == 0) {
				newPosition = new Vector2(0, 1).add(currentPosition);
			} else if (direction == 1) {
				newPosition = new Vector2(0, -1).add(currentPosition);
			} else if (direction == 2) {
				newPosition = new Vector2(1, 0).add(currentPosition);
			} else if (direction == 3) {
				newPosition = new Vector2(-1, 0).add(currentPosition);
			}
			
			
			Block currentBlock = getBlock(newPosition.x, newPosition.y, blocks);

			if (currentBlock == null || !currentBlock.open || vectorListContains(path, newPosition)) {
				removeTriesLeft--;
			} else {
				path.add(newPosition);
				removeTriesLeft = 250;
			}
			
			if (path.size() > maximumPathLength || removeTriesLeft < 0) {
				path.clear();
				path.add(new Vector2(startX, startY));
				
				removeTriesLeft = 250;

				clearingTriesLeft--;
			}
			
			if (clearingTriesLeft < 0) {
				return null;
			}
			
			currentPosition = path.get(path.size() - 1);
			lastDirection = direction;
		}
		
		return path;
	}
	
	public Block getBlock(float x, float y) {
		return getBlock(x, y, blocks);
	}
	
	public Block getBlock(float x, float y, ArrayList<Block> blocks) {
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
