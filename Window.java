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

//window class with custom resizing and dragging

public class Window extends JFrame implements ActionListener {

    JButton close;
    JButton max; // application header buttons
    JButton min;
    Color tb = new Color(24, 24, 24); // header color
    int mouseX; // mouse coords
    int mouseY;
    GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    // ^ needed for fullscreen
    JButton temp;
    ImageIcon maxIcon; // icon for JButton 'max' when window is normal
    ImageIcon normIcon; // icon when window is fullscreen
    MouseMotionAdapter resize;
    MouseMotionAdapter hresize;
    JPanel header;

    public Window() {

        // setting up JFrame
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Flashcards");
        this.setSize(800, 500);
        this.setLayout(new BorderLayout());
        this.setUndecorated(true); // removes default window decorations (buttons, title, resizing, dragging)
        this.setResizable(true);
        this.setLocationRelativeTo(null);
        this.setMinimumSize(new Dimension(400, 400));
        this.setIconImage(new ImageIcon("./Icons/logo.png").getImage()); // sets icon in taskbar

        // frame mouse listener - stores current mouse coords
        this.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                mouseX = e.getX();
                mouseY = e.getY();
            }
        });

        // frame mouse motion listener - for resizing and dragging
        resize = new MouseMotionAdapter() {

            // changes mouse cursor to resize when moved to border
            public void mouseMoved(MouseEvent e) {
                if (e.getX() <= 10 && e.getY() >= Window.this.getHeight() - 10) { // bottom left corner
                    Window.this.setCursor(new Cursor(Cursor.NE_RESIZE_CURSOR));
                } else if (e.getX() >= Window.this.getWidth() - 10 && e.getY() >= Window.this.getHeight() - 10) {
                    Window.this.setCursor(new Cursor(Cursor.NW_RESIZE_CURSOR)); // bottom right corner
                } else if (e.getX() <= 5) {
                    Window.this.setCursor(new Cursor(Cursor.W_RESIZE_CURSOR)); // left
                } else if (e.getX() >= Window.this.getWidth() - 5) {
                    Window.this.setCursor(new Cursor(Cursor.E_RESIZE_CURSOR)); // right border
                } else if (e.getY() >= Window.this.getHeight() - 5) {
                    Window.this.setCursor(new Cursor(Cursor.S_RESIZE_CURSOR)); // bottom border
                } else {
                    Window.this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }
            }

            // for resizing the window
            public void mouseDragged(MouseEvent e) {
                if (Window.this.getCursor().getType() == Cursor.NE_RESIZE_CURSOR) { // bottom left

                    // if width is not at its minimum then...
                    if (Window.this.getWidth() != Window.this.getMinimumSize().getWidth()) {
                        // if this change is going to exceed the minimum then...
                        if (Window.this.getWidth() - e.getX() > Window.this.getMinimumSize().getWidth()) {
                            // move window to account for the change in size on the bottom right side
                            Window.this.setLocation(Window.this.getX() + e.getX(), Window.this.getY());
                        } else { // otherwise...
                            Window.this.setLocation((int) (Window.this.getX() + Window.this.getWidth()
                                    - Window.this.getMinimumSize().getWidth()), Window.this.getY());
                        }
                    } // change size based on drag
                    Window.this.setSize(Window.this.getWidth() - e.getX(), e.getY());

                } else if (Window.this.getCursor().getType() == Cursor.NW_RESIZE_CURSOR) { // bottom right
                    Window.this.setSize(e.getX(), e.getY());
                } else if (Window.this.getCursor().getType() == Cursor.W_RESIZE_CURSOR) { // left
                    // if width is not at its minimum then...
                    if (Window.this.getWidth() != Window.this.getMinimumSize().getWidth()) {
                        // if this change is going to exceed the minimum then...
                        if (Window.this.getWidth() - e.getX() > Window.this.getMinimumSize().getWidth()) {
                            // move window to account for the change in size on the right side
                            Window.this.setLocation(Window.this.getX() + e.getX(), Window.this.getY());
                        } else { // otherwise...
                            Window.this.setLocation((int) (Window.this.getX() + Window.this.getWidth()
                                    - Window.this.getMinimumSize().getWidth()), Window.this.getY());
                        }
                    } // change size based on drag
                    Window.this.setSize(Window.this.getWidth() - e.getX(), Window.this.getHeight());

                } else if (Window.this.getCursor().getType() == Cursor.E_RESIZE_CURSOR) { // right
                    Window.this.setSize(e.getX(), Window.this.getHeight());
                } else if (Window.this.getCursor().getType() == Cursor.S_RESIZE_CURSOR) { // bottom
                    Window.this.setSize(Window.this.getWidth(), e.getY());
                }
            }
        };
        this.addMouseMotionListener(resize);

        // Application Header

        header = new JPanel();
        header.setLayout(new BorderLayout());
        header.setBackground(tb);

        header.addMouseListener(new MouseAdapter() {

            // gets mouse coords
            public void mousePressed(MouseEvent e) {
                if (device.getFullScreenWindow() == null) {
                    mouseX = e.getX();
                    mouseY = e.getY();
                }
            }

            // when header is double clicked, window toggles between maximized and normal
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    if (Window.this.getExtendedState() != JFrame.MAXIMIZED_BOTH) {
                        // if window is normal then maximize
                        Window.this.setExtendedState(JFrame.MAXIMIZED_BOTH);
                    } else {
                        // if window is maximized then set normal
                        Window.this.setExtendedState(JFrame.NORMAL);
                    }
                }
            }
        });

        hresize = new MouseMotionAdapter() {

            // changes mouse cursor to resize when moved to border
            public void mouseMoved(MouseEvent e) {
                if (e.getX() <= 10 && e.getY() <= 10) {
                    Window.this.setCursor(new Cursor(Cursor.SE_RESIZE_CURSOR)); // top left corner
                } else if (e.getX() >= Window.this.getWidth() - 10 && e.getY() <= 10) {
                    Window.this.setCursor(new Cursor(Cursor.SW_RESIZE_CURSOR)); // top right corner
                } else if (e.getY() <= 5) {
                    Window.this.setCursor(new Cursor(Cursor.N_RESIZE_CURSOR)); // top side
                } else {
                    Window.this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }
            }

            // for resizing the window as well as dragging it around desktop
            public void mouseDragged(MouseEvent e) {
                if (device.getFullScreenWindow() == null) { // if the window isn't fullscreen

                    // window can be moved by dragging the header
                    if (Window.this.getCursor().getType() == Cursor.DEFAULT_CURSOR) {
                        Window.this.setLocation(Window.this.getX() + e.getX() - mouseX,
                                Window.this.getY() + e.getY() - mouseY);
                    } else if (Window.this.getCursor().getType() == Cursor.N_RESIZE_CURSOR) { // top border
                        // if mouse is on the top border
                        if (Window.this.getHeight() != Window.this.getMinimumSize().getHeight()) {
                            // if height is not at its minimum
                            if (Window.this.getHeight() - e.getY() > Window.this.getMinimumSize().getHeight()) {
                                // if the change in size passes the minimum then...
                                Window.this.setLocation(Window.this.getX(), Window.this.getY() + e.getY());
                            } else { // otherwise...
                                Window.this.setLocation(Window.this.getX(), (int) (Window.this.getY()
                                        + Window.this.getHeight() - Window.this.getMinimumSize().getHeight()));
                            }
                        }
                        // change size
                        Window.this.setSize(Window.this.getWidth(), Window.this.getHeight() - e.getY());

                    } else if (Window.this.getCursor().getType() == Cursor.SE_RESIZE_CURSOR) { // top left corner

                        System.out.println(e.getX() + " " + e.getY()); // mouse coords
                        if (Window.this.getWidth() != Window.this.getMinimumSize().getWidth()
                                && Window.this.getHeight() != Window.this.getMinimumSize().getHeight()) {
                            // if width and height are not at minimum
                            if (Window.this.getWidth() - e.getX() > Window.this.getMinimumSize().getWidth()
                                    && Window.this.getHeight() - e.getY() > Window.this.getMinimumSize().getHeight()) {
                                // if the change in both width and height is more than their minimums
                                Window.this.setLocation(Window.this.getX() + e.getY(), Window.this.getY() + e.getY());
                            } else { // otherwise...
                                Window.this.setLocation(
                                        (int) (Window.this.getX() + Window.this.getWidth()
                                                - Window.this.getMinimumSize().getWidth()),
                                        (int) (Window.this.getY() + Window.this.getHeight()
                                                - Window.this.getMinimumSize().getHeight()));
                            }
                        }
                        // change size
                        Window.this.setSize(Window.this.getWidth() + e.getX(), Window.this.getHeight() - e.getY());
                    }
                }
            }
        };
        header.addMouseMotionListener(hresize);

        // initializing header icons
        ImageIcon minIcon = new ImageIcon("./Icons/min_white.png");
        maxIcon = new ImageIcon("./Icons/full_white.png");
        normIcon = new ImageIcon("./Icons/normal_white.png");
        ImageIcon closeIcon = new ImageIcon("./Icons/close_white.png");

        JPanel windowButtons = new JPanel();
        windowButtons.setLayout(new GridLayout(1, 3));

        // minimize button
        min = new JButton(minIcon);
        min.setBorderPainted(false);
        min.setOpaque(true);
        min.setBackground(tb);
        min.setFocusable(false);
        min.addMouseListener(ma);
        min.addActionListener(this);
        windowButtons.add(min);

        // icon for max button based on current state
        if (this.getExtendedState() == JFrame.MAXIMIZED_BOTH) {
            max = new JButton(normIcon);
        } else {
            max = new JButton(maxIcon);
        }

        // maximize button
        max.setBorderPainted(false);
        max.setOpaque(true);
        max.setBackground(tb);
        max.setFocusable(false);
        max.addMouseListener(ma);
        max.addActionListener(this);
        windowButtons.add(max);

        // close button
        close = new JButton(closeIcon);
        close.setBorderPainted(false);
        close.setOpaque(true);
        close.setBackground(tb);
        close.setFocusable(false);
        close.addMouseListener(ma);
        close.addActionListener(this);
        windowButtons.add(close);

        header.add(windowButtons, BorderLayout.EAST);
        this.add(header, BorderLayout.NORTH);
        this.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == close) {
            // exit application when close button is pressed
            System.exit(0);
        } else if (e.getSource() == max) {
            // toggle fullscreen when max is pressed
            if (device.isFullScreenSupported()) {
                try {
                    if (device.getFullScreenWindow() == null) {
                        // if not fullscreen
                        device.setFullScreenWindow(this);
                        max.setIcon(normIcon); // switch icon
                        min.setEnabled(false); // disables minimize

                        // remove listeners so its not resizable in fullscreen
                        min.removeMouseListener(ma);
                        this.removeMouseMotionListener(resize);
                        header.removeMouseMotionListener(hresize);

                        this.setVisible(true);
                    } else {
                        // if fullscreen
                        device.setFullScreenWindow(null);
                        max.setIcon(maxIcon); // switch icon back
                        min.setEnabled(true); // enables minimize

                        // adds listeners back
                        min.addMouseListener(ma);
                        this.addMouseMotionListener(resize);
                        header.addMouseMotionListener(hresize);

                        this.setVisible(true);
                    }
                } catch (Exception ex) {
                    device.setFullScreenWindow(null);
                }
            } else {
                System.err.println("Device is not supported");
            }
        } else if (e.getSource() == min) {
            this.setState(JFrame.ICONIFIED); // minimizes to taskbar
        }
    }

    MouseAdapter ma = new MouseAdapter() { // changes button color when hovered over

        public void mouseEntered(java.awt.event.MouseEvent e) {
            ((JButton) e.getSource()).setBackground(new Color(32, 32, 32));
        }

        public void mouseExited(java.awt.event.MouseEvent e) {
            ((JButton) e.getSource()).setBackground(tb);
        }
    };
}