import java.awt.BorderLayout;

import javax.swing.JFrame;

public class Window extends JFrame{
    public Window() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Flashcards");
        this.setSize(800, 500);
        this.setVisible(true);
        this.setLayout(new BorderLayout());

        

    } 
}
