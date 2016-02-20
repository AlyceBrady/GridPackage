// Class: ControlButton
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
 *    The <code>ControlButton</code> class represents a button
 *    whose button action runs in the same thread as the rest
 *    of the graphical user interface. 
 *
 *  @author Alyce Brady
 *  @version 29 July 2004
 **/
public abstract class ControlButton extends JButton
{
    // Instance Variables: Encapsulated data for each object
    private GridAppFrame gui = null;
    private boolean      displayAtEnd = false;

  // constructor

    /** Constructs a button.
     *    @param gui    graphical user interface containing this button
     *    @param label  label to place on button
     *    @param displayAtEnd true if grid should be displayed when
     *                        button behavior is complete; false otherwise
     **/
    public ControlButton(GridAppFrame gui, String label,
                                 boolean displayAtEnd)
    {
        super(label);
        this.gui = gui;
        this.displayAtEnd = displayAtEnd;
        addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
                { onClick(); }});
    }

  // accessors

    /** Returns the graphical user interface containing this button. **/
    public GridAppFrame getGUI()
    {
        return gui;
    }

    /** Returns <code>true</code> if this control button redisplays the
     *  the grid after performing the action associated with the button.
     **/
    public boolean displaysAfterButtonAction()
    {
        return displayAtEnd;
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
        act();
 
        // Redisplay grid contents if appropriate.
        if ( displayAtEnd )
            gui.showGrid();
    }

    /** Performs the button action associated with this button. **/
    public abstract void act();

}
