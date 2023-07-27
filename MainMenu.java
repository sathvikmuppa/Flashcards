import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class MainMenu extends JPanel implements ActionListener {

    Color bg = new Color(18, 18, 18);

    public MainMenu() {
        this.setLayout(new BorderLayout());
        this.setBackground(bg);
        this.add(new JButton("BREH"), BorderLayout.CENTER);

    }

    public void actionPerformed(ActionEvent e) {

    }
}
