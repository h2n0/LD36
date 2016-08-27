package uk.fls.h2n0.main;

import fls.engine.main.Init;
import fls.engine.main.input.Input;
import uk.fls.h2n0.main.screens.GameScreen;

@SuppressWarnings("serial")
public class LD37 extends Init{

	public static int w = 160;
	public static int h = 144;
	public static int s = 4;
	
	public LD37(){
		super("LD 37 Entry", w * s, h * s);
		useCustomBufferedImage(w, h, false);
		setInput(new Input(this, Input.KEYS));
		setScreen(new GameScreen());
	}
	
	public static void main(String[] args){
		new LD37().start();
	}
}
