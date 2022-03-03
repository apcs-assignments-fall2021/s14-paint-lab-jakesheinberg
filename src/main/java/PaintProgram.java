import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class PaintProgram implements ActionListener {
    JFrame frame;
    DrawingPanel dPanel;
    JPanel buttonPanel;
    JButton pencilButton, eraserButton, redButton, greenButton, blueButton, clearButton, sprayButton, markerButton, pickButton;
    JPanel colorPanel;
    JTextField markerSize, redVal, blueVal, greenVal;
    // This is the PaintProgram constructor which sets up the JFrame
    // and all other components and containers
    // ** Code to be edited in Part C **
    public PaintProgram() {
        // Set up JFrame using BorderLayout
        frame = new JFrame("Paint Program");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Create and add DrawingPanel to CENTER
        dPanel = new DrawingPanel();
        frame.add(dPanel);
        // Create buttonPanel and buttons
        buttonPanel = new JPanel();
        frame.add(buttonPanel, BorderLayout.SOUTH);
        colorPanel= new JPanel();
        frame.add(colorPanel,BorderLayout.EAST);

        redButton = new JButton("Red");
        redButton.addActionListener(this);
        colorPanel.add(redButton);

        greenButton = new JButton("Green");
        greenButton.addActionListener(this);
        colorPanel.add(greenButton);

        blueButton = new JButton("Blue");
        blueButton.addActionListener(this);
        colorPanel.add(blueButton);

        redVal= new JTextField();
        redVal.setColumns(3);
        colorPanel.add(redVal);

        greenVal= new JTextField();
        greenVal.setColumns(3);
        colorPanel.add(greenVal);

        blueVal= new JTextField();
        blueVal.setColumns(3);
        colorPanel.add(blueVal);

        pickButton = new JButton("Pick your own");
        pickButton.addActionListener(this);
        colorPanel.add(pickButton);

        pencilButton = new JButton("Pencil");
        pencilButton.addActionListener(this);
        buttonPanel.add(pencilButton);

        markerButton = new JButton("Marker");
        markerButton.addActionListener(this);
        buttonPanel.add(markerButton);

        markerSize= new JTextField();
        markerSize.setColumns(3);
        buttonPanel.add(markerSize);
        markerSize.setText("1");

        eraserButton = new JButton("Eraser");
        eraserButton.addActionListener(this);
        buttonPanel.add(eraserButton);

        sprayButton = new JButton("Spray");
        sprayButton.addActionListener(this);
        buttonPanel.add(sprayButton);

        clearButton = new JButton("Clear");
        clearButton.addActionListener(this);
        buttonPanel.add(clearButton);



        // Set the size and set the visibility
        frame.pack();
        frame.setVisible(true);
    }
    // This the code that is called when any button is pressed
    // We should have a separate case for each button
    // ** Code to be edited in Part B **
    public void actionPerformed(ActionEvent ae) {
        // If pencilButton is pressed, set drawingPanel mode to "Pencil"
        if (ae.getActionCommand().equals("Pencil")) {
            dPanel.setMode("Pencil");
        }
        if(ae.getActionCommand().equals("Eraser")) {
            dPanel.setMode("Eraser");
        }
        if(ae.getActionCommand().equals("Spray")) {
            dPanel.setMode("Spray");
        }
        if(ae.getActionCommand().equals("Marker")) {
            dPanel.setMode("Marker");
        }
        if(ae.getActionCommand().equals("Clear")) {
            dPanel.clearMethod();
        }
        if(ae.getActionCommand().equals("Red")) {
            dPanel.setColor(Color.RED);
        }
        if(ae.getActionCommand().equals("Blue")) {
            dPanel.setColor(Color.BLUE);
        }
        if(ae.getActionCommand().equals("Green")) {
            dPanel.setColor(Color.GREEN);
        }
        if(ae.getActionCommand().equals("Pick your own")){
            Color chosen = new Color(Integer.parseInt(redVal.getText()),Integer.parseInt(greenVal.getText()), Integer.parseInt(blueVal.getText()));
            dPanel.setColor(chosen);
        }

        }

    // Main method just creates a PaintProgram object
    public static void main(String[] args) {
        PaintProgram x = new PaintProgram();
    }
    class DrawingPanel extends JPanel implements MouseListener, MouseMotionListener
    {
        // DrawingPanel has the following instance variables:
        // A 2D array which stores whether or not
        // each pixel should be painted
        // ** To be used in Part B **
        private boolean[][] isPainted;
        // A 2D array which stores the Java Colors
        // of each pixel
        // ** To be used in Part C **
        private Color[][] colors;
        // The mode is a String that we can use to keep track of
        // what should happen if the user presses the mouse
        // ** To be used in Part B **
        private String mode;
        // This keeps track of the current selected color
        // ** To be used in Part C **
        private Color color;
        // These are constant values
        private static final int WIDTH = 500;
        private static final int HEIGHT = 500;
        // Constructor sets up DrawingPanel
        // ** You should never need to edit this code **
        public DrawingPanel() {
            // Set background color
            setBackground(Color.WHITE);
            // Add mouse listeners
            addMouseListener(this);
            addMouseMotionListener(this);
            // Initialize instance variables
            isPainted = new boolean[WIDTH][HEIGHT];
            colors = new Color[WIDTH][HEIGHT];
            mode = "Pencil";
            color = Color.BLACK;}

            public void clearMethod()
            {
                for(int row = 0; row<WIDTH; row++)
                {
                    for(int col = 0; col<HEIGHT; col++)
                    {
                        isPainted[row][col]=false;
                    }
                }
                repaint();
            }

        // Can be called to change the current mode
        // of the drawing panel
        // ** You should never need to edit this code **
        public void setMode(String mode) {
            this.mode = mode;
        }
        // Can be called to change the current color
        // of the drawing panel
        // ** You should never need to edit this code **
        public void setColor(Color color) {
            this.color = color;
        }
        // Sets the size of the DrawingPanel, so frame.pack() considers
        // its preferred size when sizing the JFrame
        // ** You should never need to edit this code **
        @Override
        public Dimension getPreferredSize() {
            return new Dimension(WIDTH, HEIGHT);
        }
        // This is the method that draws everything
        // ** Code to be edited in Part C **
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            // Loop through the 2D array and draw a 1x1 rectangle
            // on each pixel that is currently painted
            for (int x = 0; x < WIDTH; x++) {
                for (int y = 0; y < HEIGHT; y++) {
                    if (isPainted[x][y]) {
                        g.setColor(colors[x][y]);
                        g.drawRect(x, y, 1, 1);
                    }
                }
            }
        }
        // MouseListener methods
        // This is the method that is called when the mouse
        // is pressed. This is where most of your code will go
        // ** Code to be edited in Part B **
        public void mousePressed(MouseEvent e) {
            // Check the current mode
            // * If "pencil" mode, we should mark the current
            //   pixel as painted
            if (mode.equals("Pencil")) {
                // Check that mouse is in bounds of panel
                if (e.getX() >= 0 && e.getX() < WIDTH &&
                        e.getY() >= 0 && e.getY() < HEIGHT) {
                    // Set current pixel as painted
                    isPainted[e.getX()][e.getY()] = true;
                    colors[e.getX()][e.getY()] = color;
                }
            }
            if (mode.equals("Eraser")) {
                if (e.getX() >= 3 && e.getX() < WIDTH - 3 && e.getY() >= 3 &&
                        e.getY() < HEIGHT - 3) {
                    for (int x = e.getX() - 3; x < e.getX() + 3; x++) {
                        for (int y = e.getY() - 3; y < e.getY() + 3; y++) {
                            isPainted[x][y] = false;
                        }
                    }
                }
            }

            if (mode.equals("Spray")) {
                if (e.getX() >= 8 && e.getX() < WIDTH - 8 && e.getY() >= 8 &&
                        e.getY() < HEIGHT - 3) {
                    for (int x = e.getX() - 8; x < e.getX() + 8; x++) {
                        for (int y = e.getY() - 8; y < e.getY() + 8; y++) {
                            int rand_num = (int) (Math.random() * 10);
                            if (rand_num == 2) {
                                isPainted[x][y] = true;
                                colors[x][y] = color;
                            }
                        }
                    }
                }
            }

            if (mode.equals("Marker")) {
                int size = Integer.parseInt(markerSize.getText());
                if (e.getX() >= size && e.getX() < WIDTH - size && e.getY() >= size &&
                        e.getY() < HEIGHT - size) {
                    for (int x = e.getX() - size; x < e.getX() + size; x++) {
                        for (int y = e.getY() - size; y < e.getY() + size; y++) {
                            isPainted[x][y] = true;
                            colors[x][y] = color;
                        }
                    }
                }
            }


            // We need to manually tell the panel to repaint
            // and call the paintComponent method
            repaint();
        }
        // This is a MouseMotionListener method
        // We have this method so that we don't need to click each
        // pixel that we want to draw
        // ** You should never need to edit this code **
        public void mouseDragged(MouseEvent e) {
            mousePressed(e);
        }
        // The remaining MouseListener and MouseMotionLister
        // methods are left blank
        // ** You should never need to edit this code **
        public void mouseReleased(MouseEvent e) {
            // This method is intentionally blank
        }
        // ** You should never need to edit this code **
        public void mouseEntered(MouseEvent e) {
            // This method is intentionally blank
        }
        // ** You should never need to edit this code **
        public void mouseExited(MouseEvent e) {
            // This method is intentionally blank
        }
        // ** You should never need to edit this code **
        public void mouseClicked(MouseEvent e) {
            // This method is intentionally blank
        }
        // ** You should never need to edit this code **
        public void mouseMoved(MouseEvent e) {
            // This method is intentionally blank
        }
    }}
