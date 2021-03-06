/**
 * This package contains the necessary code for the GUI. Eg. creating the window, buttons, game grid and player moves.
 */
package lab4.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Observable;
import java.util.Observer;

import lab4.client.GomokuClient;
import lab4.data.GameGrid;
import lab4.data.GomokuGameState;

import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;


/**
 * The GUI class
 *
 * @author Felix Martensson och Johnny Lam
 */

public class GomokuGUI implements Observer {

    private GomokuClient client;
    private GomokuGameState gamestate;
    private JFrame gui;
    private JLabel messageLabel;
    private JButton connectButton, newGameButton, disconnectButton;
    private GamePanel gameGridPanel;


    /**
     * The constructor
     * This creates the window and buttons for the game.
     *
     * @param g The game state that the GUI will visualize
     * @param c The client that is responsible for the communication
     */
    public GomokuGUI(GomokuGameState g, GomokuClient c) {
        this.client = c;
        this.gamestate = g;
        client.addObserver(this);
        gamestate.addObserver(this);

        //Creates a new JFrame and makes close operation to close the window and kill the process
        gui = new JFrame("Gomoku");
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        messageLabel = new JLabel("Gomoku");

        //Creates game grid
        gameGridPanel = new GamePanel(g.getGameGrid());
        gameGridPanel.addMouseListener(mouse);

        //Creates connect button and opens a connection window
        connectButton = new JButton("Connect");
        connectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ConnectionWindow window = new ConnectionWindow(c);
            }
        });

        //Creates a button for to start a new game
        newGameButton = new JButton("New Game");
        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gamestate.newGame();
            }
        });

        //Creates a button to disconnect from a game
        disconnectButton = new JButton("Disconnect");
        disconnectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gamestate.disconnect();
            }
        });

        Box vertical = Box.createVerticalBox();
        Box horizontal = Box.createHorizontalBox();

        //Adds the game board
        vertical.add(gameGridPanel);

        //Puts the buttons in a horizontal box
        horizontal.add(connectButton);
        horizontal.add(newGameButton);
        horizontal.add(disconnectButton);

        //Adds the horizontal box to the vertical box, and then adds message label under the horizontal box.
        vertical.add(horizontal);
        vertical.add(messageLabel);

        //add the box to the frame and makes it visible.
        gui.add(vertical);
        gui.setVisible(true);
        //Pack() sizes the window to fit preferred size and layouts of its subcomponents.
        gui.pack();

        newGameButton.setEnabled(false);
        disconnectButton.setEnabled(false);

    }

    /**
     * Updates buttons and status text
     *
     * @param arg0
     * @param arg1
     */

    public void update(Observable arg0, Object arg1) {

        // Update the buttons if the connection status has changed
        if (arg0 == client) {
            if (client.getConnectionStatus() == GomokuClient.UNCONNECTED) {
                connectButton.setEnabled(true);
                newGameButton.setEnabled(false);
                disconnectButton.setEnabled(false);
            } else {
                connectButton.setEnabled(false);
                newGameButton.setEnabled(true);
                disconnectButton.setEnabled(true);
            }
        }

        // Update the status text if the gamestate has changed
        if (arg0 == gamestate) {
            messageLabel.setText(gamestate.getMessageString());
        }

    }

    MouseListener mouse = new MouseListener() {
        @Override
        public void mouseClicked(MouseEvent e) {
            int[] pos = gameGridPanel.getGridPosition(e.getX(), e.getY());

            gamestate.move(pos[0], pos[1]);
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    };
}

