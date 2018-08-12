package app.ajay.ld42;

/**
 * An open space
 */
public class OpenBlock extends Block {

	public OpenBlock(int x, int y) {
		super(x, y);
		
		color = color.WHITE;
		
		type = 0;
		
		open = true;
	}
	
	public void update(Level level, Main main) {
		super.update(level, main);
	}
}
