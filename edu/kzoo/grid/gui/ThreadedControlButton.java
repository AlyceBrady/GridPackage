// Class: ThreadedControlButton
//
// Author: Alyce Brady
//
// License Information:
//   This class is free software; you can redistribute it and/or modify
//   it under the terms of the GNU General Public License as published by
//   the Free Software Foundation.
//
//   This class is distributed in the hope that it will be useful,
//   but WITHOUT ANY WARRANTY; without even the implied warranty of
//   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//   GNU General Public License for more details.

package edu.kzoo.grid.gui;

import edu.kzoo.grid.gui.GridAppFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

/**
 *  Grid GUI Support Package:<br>
 *
 *    The <code>ThreadedControlButton</code> class represents a button
 *    whose button action runs in its own thread.  This means that
 *    certain graphical user interface actions, such as updates to the
 *    display and changes to the slider bar, may happen concurrently with
 *    the action associated with a button of this class. 
 *
 *  @author Alyce Brady
 *  @version 29 July 2004
 **/
public abstract class ThreadedControlButton extends JButton
{
    // Instance Variables: Encapsulated data for each object
    private GridAppFrame gui = null;
    private boolean      displayAtEnd = false;

  // constructor

    /** Constructs a button that will run in its own thread.
     *    @param gui    graphical user interface containing this button
     *    @param label  label to place on button
     *     @param displayAtEnd true if grid should be displayed when
     *                    button behavior is complete; false otherwise
     **/
    public ThreadedControlButton(GridAppFrame gui, String label,
                                 boolean displayAtEnd)
    {
        super(label);
        this.gui = gui;
        this.displayAtEnd = displayAtEnd;
        addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
                { onClick(); }});
    }

  // accessor

    /** Returns the graphical user interface containing this button. **/
    public GridAppFrame getGUI()
    {
        return gui;
    }

  // methods that implement the action associated with this button

    /** Executes the action associated with this button in a separate
     *  thread.  Uses the Template Method pattern to separate the
     *  application-specific button behavior from the generic behavior
     *  of creating a new thread and deciding whether or not to display
     *  the grid when the button action is complete.
     **/
    public void onClick()
    {
        Thread myThread = new Thread()
        {
            public void run()
            {
                gui.enterRunningMode();

                act();
 
                // Redisplay grid contents if appropriate.
                if ( displayAtEnd )
                    gui.showGrid();

                gui.enterNotRunningMode();
            }
        };

        myThread.setName(getText());
        myThread.start();
    }

    /** Performs the button action associated with this button. **/
    public abstract void act();

}
