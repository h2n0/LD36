package uk.fls.h2n0.main.util;

import fls.engine.main.io.FileIO;
import fls.engine.main.util.Renderer;
import fls.engine.main.util.rendertools.SpriteParser;

public class Font {

	
	private static SpriteParser sp = new SpriteParser(FileIO.instance.readInternalFile("/font/font.art"));
	
	public static void drawString(Renderer r, String msg, int x, int y){
		String letters = "!\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_'";
		msg = msg.toUpperCase();
		for(int j = 0; j < msg.length(); j++){
			int pos = letters.indexOf(msg.charAt(j));
			
			if(pos == -1)continue;
			r.renderSection(sp.getData(pos), x + j * 8, y, 8);
		}
	}
}
