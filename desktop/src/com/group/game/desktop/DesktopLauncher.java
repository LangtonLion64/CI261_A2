package com.group.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.group.game.TBWGame;
//Test

public class DesktopLauncher {
	public static void main (String[] arg)
	{
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.setFromDisplayMode(LwjglApplicationConfiguration.getDesktopDisplayMode());
		config.fullscreen = false;
		config.resizable = false;
		config.width /= 1.5f;
		config.height /= 1.5f;
		new LwjglApplication(new TBWGame(), config);
	}
}
