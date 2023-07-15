import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
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
    GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    JButton temp;
    ImageIcon maxIcon;
    ImageIcon normIcon;
    boolean canResize;

    public Window() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Flashcards");
        this.setSize(800, 500);
        this.setLayout(new BorderLayout());
        this.setUndecorated(true);
        this.setResizable(true);
        this.setLocationRelativeTo(null);
        this.setMinimumSize(new Dimension(400, 400));

        this.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                mouseX = e.getX();
                mouseY = e.getY();
            }
        });

        this.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseMoved(MouseEvent e) {
                if (e.getX() <=10 && e.getY() >= Window.this.getHeight()-10) {
                    Window.this.setCursor(new Cursor(Cursor.NE_RESIZE_CURSOR));
                } else if (e.getX()>=Window.this.getWidth()-10 && e.getY() >= Window.this.getHeight()-10) {
                    Window.this.setCursor(new Cursor(Cursor.NW_RESIZE_CURSOR));
                } else if (e.getX() <= 5) {
                    Window.this.setCursor(new Cursor(Cursor.W_RESIZE_CURSOR));
                } else if (e.getX() >= Window.this.getWidth()-5){
                    Window.this.setCursor(new Cursor(Cursor.E_RESIZE_CURSOR));
                } else if (e.getY() >= Window.this.getHeight()-5) {
                    Window.this.setCursor(new Cursor(Cursor.S_RESIZE_CURSOR));
                } else {
                    Window.this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }
            }

            public void mouseDragged(MouseEvent e) {
                if (Window.this.getCursor().getType() == Cursor.W_RESIZE_CURSOR) {
                    if (Window.this.getWidth()!=Window.this.getMinimumSize().getWidth()) {
                        if (Window.this.getWidth() - e.getX() > Window.this.getMinimumSize().getWidth()) {
                            Window.this.setLocation(Window.this.getX() + e.getX(), Window.this.getY());
                        } else {
                            Window.this.setLocation((int) (Window.this.getX() + Window.this.getWidth() - Window.this.getMinimumSize().getWidth()), Window.this.getY());
                        }
                    }
                    Window.this.setSize(Window.this.getWidth() - e.getX(), Window.this.getHeight());
                } else if (Window.this.getCursor().getType() == Cursor.E_RESIZE_CURSOR) {
                    Window.this.setSize(e.getX(), Window.this.getHeight());
                } else if (Window.this.getCursor().getType() == Cursor.N_RESIZE_CURSOR) {

                } else if (Window.this.getCursor().getType() == Cursor.S_RESIZE_CURSOR) {
                    Window.this.setSize(Window.this.getWidth(), e.getY());
                }
            }
        });

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

            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount()==2) {
                    if (Window.this.getExtendedState()!=JFrame.MAXIMIZED_BOTH) {
                        Window.this.setExtendedState(JFrame.MAXIMIZED_BOTH);
                    } else {
                        Window.this.setExtendedState(JFrame.NORMAL);
                    }
                }
            }
        });

        header.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                if (device.getFullScreenWindow() == null) {
                    if (Window.this.getCursor().getType() == Cursor.DEFAULT_CURSOR) {
                        Window.this.setLocation(Window.this.getX() + e.getX() - mouseX,
                        Window.this.getY() + e.getY() - mouseY);
                    } else if (Window.this.getCursor().getType() == Cursor.N_RESIZE_CURSOR) {
                        if (Window.this.getHeight()!=Window.this.getMinimumSize().getHeight()) {
                            if (Window.this.getHeight() - e.getY() > Window.this.getMinimumSize().getHeight()) {
                                Window.this.setLocation(Window.this.getX(), Window.this.getY() + e.getY());
                            } else {
                                Window.this.setLocation(Window.this.getX(), (int) (Window.this.getY() + Window.this.getHeight() - Window.this.getMinimumSize().getHeight()));
                            }
                        }
                        Window.this.setSize(Window.this.getWidth(), Window.this.getHeight() - e.getY());
                    } else if (Window.this.getCursor().getType() == Cursor.SE_RESIZE_CURSOR) {
                        if (Window.this.getWidth()!=Window.this.getMinimumSize().getWidth() && Window.this.getHeight()!=Window.this.getMinimumSize().getHeight()) {
                            if (Window.this.getWidth() - e.getX() > Window.this.getMinimumSize().getWidth() && Window.this.getHeight() - e.getY() > Window.this.getMinimumSize().getHeight()) {
                                Window.this.setLocation(Window.this.getX() + e.getY(), Window.this.getY() + e.getY());
                            } else {
                                Window.this.setLocation((int) (Window.this.getX() + Window.this.getWidth() - Window.this.getMinimumSize().getWidth()), (int) (Window.this.getY() + Window.this.getHeight() - Window.this.getMinimumSize().getHeight()));
                            }
                        }
                        Window.this.setSize(Window.this.getWidth(), Window.this.getHeight() - e.getY());                 
                    }
                }
            }

            public void mouseMoved(MouseEvent e) {
                if (e.getX() <=10 && e.getY() <= 10) {
                    Window.this.setCursor(new Cursor(Cursor.SE_RESIZE_CURSOR));
                } else if (e.getX()>=Window.this.getWidth()-10 && e.getY() <= 10) {
                    Window.this.setCursor(new Cursor(Cursor.SW_RESIZE_CURSOR));
                } else if (e.getY() <= 5) {
                    Window.this.setCursor(new Cursor(Cursor.N_RESIZE_CURSOR));
                } else {
                    Window.this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }
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