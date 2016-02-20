// Class: BGColorChoiceMenu
//
// Author: Alyce Brady
//
// This class is based on the College Board's FishToolbar class,
// as allowed by the GNU General Public License.  FishToolbar is a
// black-box GUI class within the AP(r) CS Marine Biology Simulation
// case study (see www.collegeboard.com/ap/students/compsci).
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

/**
 *  Grid GUI Nuggets Package (Handy Grid GUI Components):<br>
 *
 *    The <code>BGColorChoiceMenu</code> class represents a color chooser
 *    component for setting the background color of a grid.
 *
 *  @author Alyce Brady (based on code by Julie Zelenski)
 *  @version 29 July 2004
 **/
public class BGColorChoiceMenu extends ColorChoiceMenu
{
    // Instance Variables: Encapsulated data for EACH  object
    private GridAppFrame gui = null;

  // constructor

    /** Constructs a menu of color choices for setting the background color
     *  of a grid.  Puts the menu and a label introducing it into a panel.
     *    @param gui graphical user interface containing this button
     **/
    public BGColorChoiceMenu(GridAppFrame gui)
    {
        this(gui, "Background Color: ");
    }

    /** Constructs a menu of color choices for setting the background color
     *  of a grid.  Puts the menu and a label introducing it into a panel.
     *    @param gui graphical user interface containing this button
     *    @param label  label for color chooser
     **/
    public BGColorChoiceMenu(GridAppFrame gui, String label)
    {
        super(label);
        this.gui = gui;
    }

    /** Changes the background color and redisplays the grid.
     **/
    public void act()
    {
        gui.getDisplay().setBackgroundColor(currentColor());
        gui.showGrid();
    }

}
