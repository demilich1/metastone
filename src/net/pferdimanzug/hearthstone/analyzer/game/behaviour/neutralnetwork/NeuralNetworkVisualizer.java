package net.pferdimanzug.hearthstone.analyzer.game.behaviour.neutralnetwork;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class NeuralNetworkVisualizer extends JFrame {
  
  // the panel showing the network
  protected NeuralNetworkPanel panel;
  
  /**
   * Builds a visualizer given the network to
   * visualize
   *
   * @param net The network to visualize
   */
  public NeuralNetworkVisualizer(NeuralNetwork net) {
    setTitle("Neural Network Visualizer");
    setResizable(false);
    
    this.panel = new NeuralNetworkPanel(net);
    JPanel panel = new JPanel();
    
    panel.add(new JScrollPane(this.panel));
    panel.setPreferredSize(new Dimension(800, 500));
    getContentPane().add(panel);
    pack();
    show();
  }
  
  protected class NeuralNetworkPanel extends JPanel implements MouseListener {
    
    // variables defining the layout of the swing UI
    public final int WIDTH = 800;
    public final int HEIGHT = 450;
    public final int BORDER = 40;
    public final int NODE_DIAMETER = 18;
    public final int INPUT_LENGTH = 32;
    public final Font TEXT_FONT = new Font("Helvetic", Font.BOLD, 8);
    public final Color TEXT_COLOR = new Color(255, 255, 255);
    public final Color BACKGROUND_COLOR = new Color(255, 255, 255);
    public final Color NODE_OUTLINE_COLOR = new Color(0, 0, 0);
    public final Color NODE_FILL_COLOR = new Color(0, 200, 0);
    public final Color NODE_SELECTED_COLOR = new Color(200, 200, 0);
    public final Color CONNECTION_MAX_COLOR = new Color(180, 255, 180);
    public final Color CONNECTION_CENTER_COLOR = new Color(180, 180, 180);
    public final Color CONNECTION_MIN_COLOR = new Color(255, 180, 180);
    public final Color CONNECTION_SELECTED_MAX_COLOR = new Color(40, 255, 40);
    public final Color CONNECTION_SELECTED_CENTER_COLOR = new Color(40, 40, 40);
    public final Color CONNECTION_SELECTED_CENTER_COLOR_2 = new Color(200, 200, 200);
    public final Color CONNECTION_SELECTED_MIN_COLOR = new Color(255, 40, 40);
    
    // variables computed once the net is received
    protected int LEVEL_SPACING;
    
    // the level and number of the selected node
    protected int[] selected;
    
    // the network we are visualizing
    protected NeuralNetwork net;
    
    /**
     * Builds a neural network panel
     *
     * @param net The net to base this panel on
     */
    protected NeuralNetworkPanel(NeuralNetwork net) {
      this.net = net;

      setSize(WIDTH, HEIGHT);
      
      this.LEVEL_SPACING = (HEIGHT - 2*BORDER) / net.hidden.length;
      addMouseListener(this);
    }
    
    /**
     * Returns the preferred size of this panel
     *
     * @return The size of this panel
     */
    public Dimension getPreferredSize() {
      return new Dimension(WIDTH, HEIGHT);
    }
    
    /**
     * Draws this panel.  
     *
     * @param g Where to draw the panel
     */
    public void paint(Graphics g) {
      Graphics2D g2 = (Graphics2D) g;
      g2.setColor(BACKGROUND_COLOR);
      g2.fillRect(0, 0, WIDTH, HEIGHT);
      
      
      paintConnections(g2);
      paintNodes(g2);
    }
    
    /**
      * Paints the connections between the nodes
     *
     * @param g2 The grpahics
     */
    protected void paintConnections(Graphics2D g2) {
      for (int i=0; i<net.input.length; i++)
        paintConnection(g2, getX(0, i), getY(0, i), getX(0, i), getY(0, i) + INPUT_LENGTH, 0, false);
      
      for (int i=0; i<net.hidden.length; i++) 
        for (int j=0; j<net.hidden[i].length; j++)       
          if (i == 0) 
            for (int k=0; k<net.input.length; k++)
              paintConnection(g2, getX(0, k), getY(0, k), getX(1, j), getY(1, j), net.hidden[i][j].weights[k], isSelected(1, j) || isSelected(0, k));
          else
            for (int k=0; k<net.hidden[i-1].length; k++)
              paintConnection(g2, getX(i, k), getY(i, k), getX(i+1, j), getY(i+1, j), net.hidden[i][j].weights[k], isSelected(i+1, j) || isSelected(i, k));
      
      for (int i=0; i<net.hidden[net.hidden.length-1].length; i++) 
        paintConnection(g2, getX(net.hidden.length, i), getY(net.hidden.length, i), 
                        getX(net.hidden.length, i), getY(net.hidden.length, i) - INPUT_LENGTH, 0, false); 
      
      if (selected != null) 
        for (int i=0; i<net.hidden.length; i++) 
          for (int j=0; j<net.hidden[i].length; j++) 
            if (i == 0) {
              for (int k=0; k<net.input.length; k++)
                if (isSelected(1, j) || isSelected(0, k))
                  paintConnection(g2, getX(0, k), getY(0, k), getX(1, j), getY(1, j), net.hidden[i][j].weights[k], isSelected(1, j) || isSelected(0, k));
            } else {
              for (int k=0; k<net.hidden[i-1].length; k++)
                if (isSelected(i+1, j) || isSelected(i, k)) 
                  paintConnection(g2, getX(i, k), getY(i, k), getX(i+1, j), getY(i+1, j), net.hidden[i][j].weights[k], isSelected(i+1, j) || isSelected(i, k));
            }
    }
    
    /**
     * Draws one connection
     *
     * @param g2 The grpahics
     * @param x1 start x
     * @param y1 start y
     * @param x2 end x
     * @param y2 end y
     * @param weight The weight
     */
    protected void paintConnection(Graphics2D g2, int x1, int y1, int x2, int y2, double weight, boolean selected) {
      if (weight > 1) weight = 1;
      if (weight < -1) weight = -1;
      Color color = CONNECTION_CENTER_COLOR;
      Color max = CONNECTION_MAX_COLOR;
      Color min = CONNECTION_MAX_COLOR;
      
      if (selected) {
        color = CONNECTION_SELECTED_CENTER_COLOR;
        max = CONNECTION_SELECTED_MAX_COLOR;
        min = CONNECTION_SELECTED_MIN_COLOR;
      }
      
      
      if (weight > 0) {
        int r = color.getRed() + (int) ((max.getRed() - color.getRed()) * weight);
        int g = color.getGreen() + (int) ((max.getGreen() - color.getGreen()) * weight);
        int b = color.getBlue() + (int) ((max.getBlue() - color.getBlue()) * weight);
        
        color = new Color(r, g, b);
      } else if (weight < 0) {
        int r = color.getRed() - (int) ((min.getRed() - color.getRed()) * weight);
        int g = color.getGreen() - (int) ((min.getGreen() - color.getGreen()) * weight);
        int b = color.getBlue() - (int) ((min.getBlue() - color.getBlue()) * weight);
        
        color = new Color(r, g, b);        
      } else if (weight == 0) {
        color = CONNECTION_SELECTED_CENTER_COLOR_2;
      }
        
      g2.setColor(color);
      
      if (selected) {
        int xPoints[] = new int[4];
        int yPoints[] = new int[4];
        
        xPoints[0] = x1 + 1; yPoints[0] = y1 + 1;
        xPoints[1] = x1; yPoints[1] = y1;
        xPoints[2] = x2; yPoints[2] = y2;
        xPoints[3] = x2 + 1; yPoints[3] = y2 + 1;
        
        g2.fillPolygon(xPoints, yPoints, 4);
      } else { 
        g2.drawLine(x1, y1, x2, y2);
      }
    }
    
    /**
     * Paints the nodes
     *
     * @param g2 The grpahics
     */
    protected void paintNodes(Graphics2D g2) {
      // paint the input level
      for (int i=0; i<net.input.length; i++) 
        paintNode(g2, getX(0, i), getY(0, i), "I" + i, isSelected(0, i));
      
      for (int i=0; i<net.hidden.length; i++) 
        for (int j=0; j<net.hidden[i].length; j++) 
          paintNode(g2, getX(i+1, j), getY(i+1, j), ((i < net.hidden.length - 1) ? "H" + i + "," : "O") + j, isSelected(i+1, j));
    }   
    
    /**
     * Returns whetehr or not the node is slected
     *
     * @reutrn Whether or not the node is selected
     */
    protected boolean isSelected(int level, int num) {
      return (selected != null) && (selected[0] == level) && (selected[1] == num);
    }

    /**
     * Paints a node at the given location
     *
     * @param g2 The graphics
     * @param x The x coordinate
     * @param y The y coordinate
     * @param label The label
     */
    protected void paintNode(Graphics2D g2, int x, int y, String label) {
      paintNode(g2, x, y, label, false);
    }
    
    /**
     * Paints a node at the given location
     *
     * @param g2 The graphics
     * @param x The x coordinate
     * @param y The y coordinate
     * @param label The label
     * @param hightlighed Whetehr or not this node is selected
     */
    protected void paintNode(Graphics2D g2, int x, int y, String label, boolean selected) {
      if (selected) {
        g2.setColor(NODE_SELECTED_COLOR);
        g2.fillOval(x - NODE_DIAMETER/2 - 2, y - NODE_DIAMETER/2 - 2, NODE_DIAMETER + 4, NODE_DIAMETER + 4);
      }
      
      g2.setColor(NODE_OUTLINE_COLOR);
      g2.drawOval(x - NODE_DIAMETER/2, y - NODE_DIAMETER/2, NODE_DIAMETER, NODE_DIAMETER);
      
      g2.setColor(NODE_FILL_COLOR);
      g2.fillOval(x - NODE_DIAMETER/2 + 1, y - NODE_DIAMETER/2 + 1, NODE_DIAMETER - 2, NODE_DIAMETER - 2);
      
      g2.setColor(TEXT_COLOR);
      g2.setFont(TEXT_FONT);
      FontMetrics fm = g2.getFontMetrics(TEXT_FONT);
      
      g2.drawString(label, (int) (x - fm.getStringBounds(label, g2).getWidth()/2), y + fm.getAscent()/2);
    }
    
    /**
     * Returns the x-coordinate of the given node
     * 
     * @param level The level
     * @param num The num
     */
    protected int getX(int level, int num) {
      if (level == 0)
        return (num+1) * (WIDTH / (net.input.length+1));
      else
        return (num+1) * (WIDTH / (net.hidden[level-1].length+1));
    }
    
    /**
     * Returns the y-coordinate of the given node
     * 
     * @param level The level
     * @param num The num
     */
    protected int getY(int level, int num) {
      return HEIGHT - BORDER - ((level) * LEVEL_SPACING);
    }
    
    /**
     * Returns the node at the given lcoation, or null if none exists
     *
     * @param x The x location
     * @param y The y location
     * @return The node selected, or null
     */
    protected int[] getNode(int x, int y) {
      if (Math.abs(y - getY(0, 0)) <= NODE_DIAMETER/2) 
        for (int j=0; j<net.input.length; j++)
          if (Math.sqrt((x-getX(0, j)) * (x-getX(0, j)) + (y-getY(0, j)) * (y-getY(0, j))) <= NODE_DIAMETER/2)
            return new int[] {0, j};
          
      for (int i=0; i<net.hidden.length; i++)
        if (Math.abs(y - getY(i+1, 0)) <= NODE_DIAMETER/2)
          for (int j=0; j<net.hidden[i].length; j++)
            if (Math.sqrt((x-getX(i+1, j)) * (x-getX(i+1, j)) + (y-getY(i+1, j)) * (y-getY(i+1, j))) <= NODE_DIAMETER/2)
              return new int[] {i+1, j};
      
      return null;
    }
    
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}

    public void mouseClicked(MouseEvent e) {
      selected = getNode(e.getX(), e.getY());
      NeuralNetworkVisualizer.this.repaint();
    }
  }
  
  public static void main(String[] args) {
    NeuralNetworkVisualizer vis = new NeuralNetworkVisualizer(new NeuralNetwork(198, new int[] {40, 1}));
   
    
  }
}
