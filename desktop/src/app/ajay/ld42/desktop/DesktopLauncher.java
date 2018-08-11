package app.ajay.ld42.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import app.ajay.ld42.Main;

public class DesktopLauncher {
	
	public static void main(String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		
		config.width = 1000;
		config.height = 600;
		
		new LwjglApplication(new Main(), config);
	}
	
}
