import java.awt.*;  

public class Test {
    public static void main(String argv[]) {
        Board board = new Board();

        // draw main window
        Frame f= new Frame("Canvas Example");  
        GridBagLayout layout = new GridBagLayout();
        f.setLayout(layout);

        // TODO f.setDefaultCloseOperation()

        f.add(board); 
        f.pack(); 
        f.setVisible(true);  
    }
}