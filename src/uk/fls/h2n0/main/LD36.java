package uk.fls.h2n0.main;

import fls.engine.main.Init;
import fls.engine.main.input.Input;
import uk.fls.h2n0.main.screens.GameScreen;

@SuppressWarnings("serial")
public class LD36 extends Init{

	public static int w = 160;
	public static int h = 144;
	public static int s = 3;
	
	public LD36(){
		super("LD 36", w * s, h * s);
		useCustomBufferedImage(w, h, false);
		setInput(new Input(this, Input.KEYS,Input.MOUSE, Input.CONTROLLER));
		setScreen(new GameScreen());
	}
	
	public static void main(String[] args){
		new LD36().start();
	}
}
