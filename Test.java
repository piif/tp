import java.awt.*;  

public class Test {
    public static void main(String argv[]) {
        System.out.println("this is a test");

        Frame f= new Frame("Canvas Example");  
        f.add(new Board());  
        f.setLayout(null);  
        f.setSize(400, 400);  
        f.setVisible(true);  
    }
}