package app.ajay.ld42;

import java.util.function.IntPredicate;

import com.badlogic.gdx.math.Vector2;

/**
 * Class that stores all start data for creating a level
 * Much easier than passing each configuration option to the Level class
 */

public class LevelConfiguration {
	
	int blockSize = 50;
	
	//contains a list of the rows of blocks. -1: no block.
	int[][] blocks;
	
	Vector2[] enemies;
	
	int playerX;
	int playerY;
	
	int endX;
	int endY;
	
	//amount of turns until another tile is destroyed
	int destroyInterval = 1;
	//amount of blocks to destroy each time
	int destroyAmount = 5;
}
