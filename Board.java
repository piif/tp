import java.awt.*;  

class Board extends Canvas {
    private static final long serialVersionUID = 1L;

    public Board() {
        setBackground (Color.GRAY);
        setSize(300, 200);
    }
    public void paint(Graphics g) {  
        g.setColor(Color.red);
        g.fill3DRect(75, 75, 150, 75, true);
    }
}
