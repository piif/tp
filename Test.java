import java.awt.*;  
import java.awt.event.*;

public class Test {

    public Test() {
        // draw main window
        Frame f= new Frame("Canvas Example");  
        GridBagLayout layout = new GridBagLayout();
        f.setLayout(layout);

        // TODO f.setDefaultCloseOperation()
		KeyListener listener = new MyKeyListener();
		f.addKeyListener(listener);
		f.setFocusable(true);

        Board board = new Board();
        f.add(board);

        f.pack();
        f.setVisible(true);
    }

	private class MyKeyListener implements KeyListener {
		@Override
		public void keyTyped(KeyEvent e) {}

		@Override
		public void keyPressed(KeyEvent e) {
            // System.out.println("keyPressed=" + KeyEvent.getKeyText(e.getKeyCode()) + "(" + e.getKeyCode() + ")");
            switch(KeyEvent.getKeyText(e.getKeyCode())) {
                case "Q":
                    quit();
                break;
                case "Left":
                    move(-1);
                break;
                case "Right":
                    move(+1);
                break;
                case "Up":
                    rotate(-1);
                break;
                case "Down":
                    rotate(+1);
                break;
                case "Space":
                    moveDown();
                break;
            }
		}

		@Override
		public void keyReleased(KeyEvent e) {}
	}

    private void move(int direction) {

    }
    private void rotate(int direction) {

    }
    private void moveDown() {

    }

    private void quit() {
        System.exit(0);
    }

    public static void main(String argv[]) {
        Test game = new Test();
        // and then ??
    }
}