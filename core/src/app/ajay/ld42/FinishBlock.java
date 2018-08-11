package app.ajay.ld42;

/**
 * The target for the player
 */
public class FinishBlock extends Block {

	public FinishBlock(int x, int y) {
		super(x, y);
		
		color = color.GREEN;
		
		type = 1;
		
		open = true;
	}

}
