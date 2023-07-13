import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Window extends JFrame implements ActionListener {

    JButton close;
    JButton max;
    JButton min;
    Color tb = new Color(24, 24, 24);
    int mouseX;
    int mouseY;
    // GraphicsDevice device =
    // GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0];
    GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    JButton temp;
    ImageIcon maxIcon;
    ImageIcon normIcon;

    public Window() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Flashcards");
        this.setSize(800, 500);
        this.setLayout(new BorderLayout());
        this.setUndecorated(true);
        this.setResizable(true);
        this.setLocationRelativeTo(null);

        this.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                mouseX = e.getX();
                mouseY = e.getY();
            }
        });

        this.addMouseMotionListener(new MouseMotionAdapter() {

            public void mouseMoved(MouseEvent e) {
                if (e.getX() <= 5 || e.getX() >= Window.this.getWidth()-5) {
                    Window.this.setCursor(new Cursor(Cursor.W_RESIZE_CURSOR));
                } else {
                    Window.this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }
            }

            public void mouseDragged(MouseEvent e) {

                if (e.getX()<=5 && e.getY()<=5){ 
                    //Window.this.setSize(e.getX(), e.getY());
                } else if (e.getX()<=5) { // this limits the amount changed by 5
                    // Window.this.setLocation(Window.this.getX() - e.getX() + mouseX, Window.this.getY());
                    Window.this.setLocation(Window.this.getX() + e.getX(), Window.this.getY());

                    Window.this.setSize(Window.this.getWidth() - e.getX(), Window.this.getHeight());
                    System.out.println(Window.this.getX() + " " + e.getX() + " " + (Window.this.getX() - e.getX()) + " ");

                } else if (e.getY()<=5) {
                    Window.this.setSize(Window.this.getWidth(), e.getY()+ Window.this.getHeight());
                } else if (e.getX() >= Window.this.getWidth()-5) {
                    Window.this.setSize(e.getX(), Window.this.getHeight());
                } else if (e.getY() >= Window.this.getHeight()-5){
                    Window.this.setSize(Window.this.getWidth(), e.getY());
                }
            }
        });

        // this.add(new MainMenu(), BorderLayout.CENTER);

        JPanel header = new JPanel();
        header.setLayout(new BorderLayout());
        header.setBackground(tb);

        header.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (device.getFullScreenWindow() == null) {
                    mouseX = e.getX();
                    mouseY = e.getY();
                }
            }
        });

        header.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                if (device.getFullScreenWindow() == null)
                    Window.this.setLocation(Window.this.getX() + e.getX() - mouseX,
                            Window.this.getY() + e.getY() - mouseY);
            }
        });

        ImageIcon minIcon = new ImageIcon("./Icons/min_white.png");
        maxIcon = new ImageIcon("./Icons/full_white.png");
        normIcon = new ImageIcon("./Icons/normal_white.png");
        ImageIcon closeIcon = new ImageIcon("./Icons/close_white.png");

        JPanel windowButtons = new JPanel();
        windowButtons.setLayout(new GridLayout(1, 3));

        min = new JButton(minIcon);
        min.setBorderPainted(false);
        min.setOpaque(true);
        min.setBackground(tb);
        min.addMouseListener(ma);
        min.addActionListener(this);
        windowButtons.add(min);

        if (this.getExtendedState() == JFrame.MAXIMIZED_BOTH) {
            max = new JButton(normIcon);
        } else {
            max = new JButton(maxIcon);
        }
        max.setBorderPainted(false);
        max.setOpaque(true);
        max.setBackground(tb);
        max.addMouseListener(ma);
        max.addActionListener(this);
        windowButtons.add(max);

        close = new JButton(closeIcon);
        close.setBorderPainted(false);
        close.setOpaque(true);
        close.setBackground(tb);
        close.addMouseListener(ma);
        close.addActionListener(this);
        windowButtons.add(close);

        header.add(windowButtons, BorderLayout.EAST);
        this.add(header, BorderLayout.NORTH);

        this.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == close) {
            System.exit(0);
        } else if (e.getSource() == max) {
            if (device.isFullScreenSupported()) {
                try {
                    if (device.getFullScreenWindow() == null) {
                        device.setFullScreenWindow(this);
                        max.setIcon(normIcon);
                        this.setVisible(true);
                    } else {
                        device.setFullScreenWindow(null);
                        max.setIcon(maxIcon);
                        this.setVisible(true);
                    }
                } catch (Exception ex) {
                    device.setFullScreenWindow(null);
                }
            } else {
                System.err.println("nah");
            }
        } else if (e.getSource() == min) {
            this.setState(JFrame.ICONIFIED);
        }
    }

    MouseAdapter ma = new MouseAdapter() {
        public void mouseEntered(java.awt.event.MouseEvent e) {
            ((JButton) e.getSource()).setBackground(new Color(32, 32, 32));
        }

        public void mouseExited(java.awt.event.MouseEvent e) {
            ((JButton) e.getSource()).setBackground(tb);
        }
    };

}
