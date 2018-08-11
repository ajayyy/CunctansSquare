package app.ajay.ld42;

/**
 * Class that stores all start data for creating a level
 * Much easier than passing each configuration option to the Level class
 */

public class LevelConfiguration {
	
	int blockSize = 50;
	
	//contains a list of the rows of blocks. -1: no block.
	int[][] blocks;
	
	int playerX;
	int playerY;
}
