package lab4.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import lab4.data.GameGrid;

/**
 * A panel providing a graphical view of the game board
 */

public class GamePanel extends JPanel implements Observer{

    private final int UNIT_SIZE = 20;
    private GameGrid grid;

    /**
     * The constructor
     *
     * @param grid The grid that is to be displayed
     */
    public GamePanel(GameGrid grid){
        this.grid = grid;
        grid.addObserver(this);
        Dimension d = new Dimension(grid.getSize()*UNIT_SIZE+1, grid.getSize()*UNIT_SIZE+1);
        this.setMinimumSize(d);
        this.setPreferredSize(d);
        this.setBackground(Color.WHITE);
    }

    /**
     * Returns a grid position given pixel coordinates
     * of the panel
     *
     * @param x the x coordinates
     * @param y the y coordinates
     * @return an integer array containing the [x, y] grid position
     */
    public int[] getGridPosition(int x, int y){
        int[] pos = new int[2];
        pos[0] = x/UNIT_SIZE;
        pos[1] = y/UNIT_SIZE;
        return pos;
    }
    /** 
     * 	
     */
    public void update(Observable arg0, Object arg1) {
        this.repaint();
    }
    /**
     * 
     */
    public void paintComponent(Graphics g){ //TODO
        super.paintComponent(g);
        drawBoard(g);
        drawPlayers(g);
    }
    /**
     * 
     */
    private void drawBoard(Graphics g){
    	int size = grid.getSize();
    	g.setColor(Color.black);
    	for(int i = 0; i<size+1; i++) {
    		g.drawLine(i*10, 0, i*10, size*10); // vert
    		g.drawLine(0, i*10, 0, size*10); // hori
    	}
    }
    /**
     * 
     */
    private void drawPlayers(Graphics g){
    	int size = grid.getSize();
    	for(int i = 0; i<size; i++) {
    		for(int j = 0; j<size; j++) {
    			if (grid.getLocation(i, j)== 1) {
    				g.setColor(Color.black);
    				g.fillOval(i*10, j*10, 10, 10);
    			}else if(grid.getLocation(i, j)== 2) {
    				g.setColor(Color.red);
    				g.fillOval(i*10, j*10, 10, 10);
    			}
    		}
    	}
    }
}