// Class: ClearGridButton
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

package edu.kzoo.grid.gui.nuggets;

import edu.kzoo.grid.gui.GridAppFrame;
import edu.kzoo.grid.gui.ThreadedControlButton;

/**
 *  Grid GUI Nuggets Package (Handy Grid GUI Components):<br>
 *
 *    The <code>ClearGridButton</code> class represents a button
 *    that clears all objects out of a grid.
 *
 *  @author Alyce Brady
 *  @version 29 July 2004
 **/
public class ClearGridButton extends ThreadedControlButton
{

  // constructors

    /** Constructs a button labeled "Clear Grid" that will clear all
     *  objects out of a grid.  By default, this button will not redisplay
     *  the grid contents after clearing the grid.
     *    @param gui graphical user interface containing this button
     **/
    public ClearGridButton(GridAppFrame gui)
    {
        this(gui, "Clear Grid", false);
    }

    /** Constructs a button that will clear all objects out of a grid.
     *  By default, this button will not redisplay the grid contents
     *  after clearing the grid.
     *    @param gui graphical user interface containing this button
     *    @param label  label to place on button
     **/
    public ClearGridButton(GridAppFrame gui, String label)
    {
        this(gui, label, false);
    }

    /** Constructs a button that will clear all objects out of a grid.
     *    @param gui graphical user interface containing this button
     *    @param displayAfterClear true if grid should be displayed
     *                    after being cleared; false otherwise
     **/
    public ClearGridButton(GridAppFrame gui, boolean displayAfterClear)
    {
        this(gui, "Clear Grid", displayAfterClear);
    }

    /** Constructs a button that will clear all objects out of a grid.
     *    @param gui graphical user interface containing this button
     *    @param label  label to place on button
     *    @param displayAfterClear true if grid should be displayed
     *                    after being cleared; false otherwise
     **/
    public ClearGridButton(GridAppFrame gui, String label,
                           boolean displayAfterClear)
    {
        super(gui, label, displayAfterClear);
    }

  // method that implements the core action associated with this button

    /** Clears the grid.
     **/
    public void act()
    {
        getGUI().getGrid().removeAll();
    }

}
