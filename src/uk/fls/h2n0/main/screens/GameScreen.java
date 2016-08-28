package uk.fls.h2n0.main.screens;

import java.awt.Graphics;

import javax.swing.plaf.basic.BasicTreeUI.CellEditorHandler;

import fls.engine.main.input.CustomController;
import fls.engine.main.screen.Screen;
import fls.engine.main.util.Camera;
import fls.engine.main.util.Renderer;
import uk.fls.h2n0.main.core.World;
import uk.fls.h2n0.main.core.entity.Player;
import uk.fls.h2n0.main.util.Font;

public class GameScreen extends Screen {

	
	private Renderer r;
	private World w;
	private Camera cam;
	private Player p;
	private int inputDelay = 0;
	
	private boolean paused;
	private int option = 0;
	private String[] options;
	
	private boolean controllerConnected;
	private boolean usingController;
	
	public void postInit(){
		this.r = new Renderer(this.game.getImage());
		this.w = new World();
		this.cam = new Camera(160/8, 144/8);
		this.p = this.w.getPlayer();
		this.w.addEntity(this.p);
		this.cam.ww = this.w.w * 8;
		this.cam.wh = this.w.h * 8;
		this.paused = false;
		this.option = 0;
		this.options = new String[]{"Resume", "Main Menu", "Exit"};
		this.controllerConnected = false;
		this.usingController = false;
	}
	
	@Override
	public void update() {
		
		if(this.input.getController() == null)this.input.setPrimaryController(CustomController.start, false);
		
		boolean up = this.input.isKeyHeld(this.input.w) || this.input.isKeyHeld(this.input.up);
		boolean down = this.input.isKeyHeld(this.input.s) || this.input.isKeyHeld(this.input.down);
		boolean left = this.input.isKeyHeld(this.input.a) || this.input.isKeyHeld(this.input.left);
		boolean right = this.input.isKeyHeld(this.input.d) || this.input.isKeyHeld(this.input.right);
		boolean action = this.input.isKeyPressed(this.input.space) || this.input.isKeyPressed(this.input.z) || this.input.leftMouseButton.isHeld();
		boolean pause = this.input.isKeyPressed(this.input.esc);
		

		float tol = 0.18f;
		
		if(up || down || left || right || action || pause){
			this.usingController = false;
		}
		
		if(this.input.getController() != null){
			//System.out.println(this.input.getController().getCoponentReadout());
			float sx = this.input.getController().getLeftStickX();
			float sy = this.input.getController().getLeftStickY();
			
			boolean cup = this.input.getController().getPOV() == CustomController.povN || sy <= -tol;
			boolean cdown = this.input.getController().getPOV() == CustomController.povS || sy >= tol;
			boolean cleft = this.input.getController().getPOV() == CustomController.povE || sx <= -tol;
			boolean cright = this.input.getController().getPOV() == CustomController.povW || sx >= tol;
			boolean caction = this.input.getController().isA() || this.input.getController().isRightTrigger();
			boolean cpause = this.input.getController().isStart();
			
			if(cup || cdown || cleft || cright || caction || cpause){
				this.usingController = true;
				up = up || cup;
				down = down || cdown;
				left = left || cleft;
				right = right || cright;
				action = action || caction;
				pause = pause || cpause;
			}
		}
		
		if(!this.controllerConnected && this.input.getController() != null){
			this.inputDelay = 10;
			this.w.showPopup("Controller added", 60 * 2);
			this.controllerConnected = true;
		}
		
		
		if(this.inputDelay > 0)this.inputDelay--;
		
		if(this.paused){
			
			if(pause && inputDelay == 0){
				this.paused = false;
				this.inputDelay = 10;
			}
			if(up && inputDelay == 0){
				this.option--;
				this.inputDelay = 10;
			}
			if(down && inputDelay == 0){
				this.option++;
				this.inputDelay = 10;
			}
			
			if(option < 0)option = 2;
			if(option > 2)option = 0;
			
			if(action && inputDelay == 0){
				if(this.option == 0){
					this.paused = false;
				}
				else if(this.option == 1){
					
				}else if(this.option == 2){
					this.game.stop();
				}
				this.inputDelay = 10;
			}
		}else{
			this.w.update(this.cam);
			float xOff = 0;
			float yOff = 0;
			float ax = 0f, ay = 0f;
			
			
			if(this.usingController){
				float sx = this.input.getController().getLeftStickX();
				float sy = this.input.getController().getLeftStickY();
				
				float rsx = this.input.getController().getRightStickX();
				float rsy = this.input.getController().getRightStickY();
				
				if(sx <= -tol){
					xOff = sx;
				}else if(sx >= tol){
					xOff = sx;
				}
				
				if(sy <= -tol){
					yOff = sy;
				}else if(sy >= tol){
					yOff = sy;
				}
				
				if(rsx <= -tol){
					ax = rsx;
				}else if(rsx >= tol){
					ax = rsx;
				}
				
				if(rsy <= -tol){
					ay = rsy;
				}else if(rsy >= tol){
					ay = rsy;
				}
			}else{
				ax = this.input.mouse.getX()/3 + this.cam.pos.getIX() - this.p.getPos().getIX();
				ay = this.input.mouse.getY()/3 + this.cam.pos.getIY() - this.p.getPos().getIY();
				
				if(up)yOff--;
				else if(down)yOff++;
				
				if(left)xOff--;
				else if(right)xOff++;
				
			}
			
			if(action && inputDelay == 0)this.p.action();
			
			if(pause && inputDelay == 0){
				this.inputDelay = 10;
				this.paused = true;
			}
			
			this.p.aim(ax, ay);
			this.p.move(xOff, yOff);
			
			this.cam.center(this.p.getPos().getIX(), this.p.getPos().getIY(), -68);
		}
	}
	
	@Override
	public void render(Graphics g) {
		if(this.paused){
			r.setOffset((160 - 14 * 8)/2, (144 - 80)/2);
			for(int x = 0; x < 14 * 8; x++){
				for(int y = 0; y < 10 * 8; y++){
					this.r.setPixel(x, y, 0);
				}
			}
			Font.drawString(r, "PAUSED", 8, 8);
			for(int i = 0; i < this.options.length; i++){
				Font.drawString(r, i==this.option?">> " + this.options[i]:this.options[i], 8, 24 + i * 16);
			}
			
			Font.drawString(r, "Gems:" + this.p.score, 8, 90);
			
		}else{
			this.w.render(r, cam);
		}
	}
}
