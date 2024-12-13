import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener{
	
	public boolean upPressed, downPressed, leftPressed, rightPressed;
	public boolean turning;
	public int angularDirection = 0;
	
	int rotateSpeed = 5;
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {

		int code = e.getKeyCode();
		
		if (code == KeyEvent.VK_W) {
			upPressed = true;
		}
		
		if (code == KeyEvent.VK_A) {
			leftPressed = true;
			turning = true;
			angularDirection -= rotateSpeed;
			if(angularDirection < 0) {
				angularDirection += 360;
			}
			if(angularDirection > 360) {
				angularDirection -= 360;
			}
			
		}		
		if (code == KeyEvent.VK_S) {	
			downPressed = true;
		}
		
		if (code == KeyEvent.VK_D) {
			rightPressed = true;
			angularDirection += rotateSpeed;
			if(angularDirection < 0) {
				angularDirection += 360;
			}
			if(angularDirection > 360) {
				angularDirection -= 360;
			}
		}
		
		if (code == KeyEvent.VK_RIGHT) {
			turning = true;
			angularDirection += rotateSpeed;
			if(angularDirection < 0) {
				angularDirection += 360;
			}
			if(angularDirection > 360) {
				angularDirection -= 360;
			}
		}
		
		if (code == KeyEvent.VK_LEFT) {
			turning = true;
			angularDirection -= rotateSpeed;
			if(angularDirection < 0) {
				angularDirection += 360;
			}
			if(angularDirection > 360) {
				angularDirection -= 360;
			}
		}		
	}

	@Override
	public void keyReleased(KeyEvent e) {

		int code = e.getKeyCode();
		
		if (code == KeyEvent.VK_W) {
			upPressed = false;
		}
		
		if (code == KeyEvent.VK_A) {
			leftPressed = false;
		}		
		if (code == KeyEvent.VK_S) {
			downPressed = false;
		}
		
		if (code == KeyEvent.VK_D) {
			rightPressed = false;
		}
		if (code == KeyEvent.VK_RIGHT) {
			turning = false;
		}
		
		if (code == KeyEvent.VK_LEFT) {
			turning = false;
		}
	}
	
}
