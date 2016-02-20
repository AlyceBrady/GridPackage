// Class: BGColorChoiceMenu
//
// Author: Alyce Brady
//
// This class is based on the College Board's FishToolbar class,
// as allowed by the GNU General Public License.  FishToolbar is a
// black-box GUI class within the AP(r) CS Marine Biology Simulation
// case study (see
// http://www.collegeboard.com/student/testing/ap/compsci_a/case.html).
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

import edu.kzoo.grid.gui.ColorChoiceDDMenu;
import edu.kzoo.grid.gui.GridAppFrame;

/**
 *  Grid GUI Nuggets Package (Handy Grid GUI Components):<br>
 *
 *    The <code>BGColorChoiceMenu</code> class provides a drop-down menu for
 *    setting the background color of a grid.
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
        this(gui, "Background Color: ", "White");
    }

    /** Constructs a menu of color choices for setting the background color
     *  of a grid.  Puts the menu and a label introducing it into a panel.
     *    @param gui graphical user interface containing this button
     *    @param label  label for color chooser
     **/
    public BGColorChoiceMenu(GridAppFrame gui, String label)
    {
        this(gui, label, "White");
    }

    /** Constructs a menu of color choices for setting the background color
     *  of a grid.  Puts the menu and a label introducing it into a panel.
     *  (Precondition: <code>defaultColor</code> is one of the
     *  color choices in <code>STANDARD_CHOICES</code>, whose labels are
     *  "Red", "Orange", "Yellow", "Green", "Blue", "Indigo", "Violet",
     *  "White", "Gray", "Black", "Random", and "Other ...".)
     *    @param gui graphical user interface containing this button
     *    @param label  label for color chooser
     *    @param defaultColor  the color that should appear as the default
     *                         on the menu when it is first constructed
     **/
    public BGColorChoiceMenu(GridAppFrame gui, String label,
                             String defaultColor)
    {
        super(label, defaultColor);
        this.gui = gui;
    }

    /** Constructs a menu of color choices for setting the background color
     *  of a grid.  Puts the menu and a label introducing it into a panel.
     *  (Precondition: <code>defaultColor</code> is one of the
     *  color choices in <code>colorChoices</code>.)
     *    @param gui graphical user interface containing this button
     *    @param label  label for color chooser
     *    @param colorChoices  the set of color choices to show in the
     *                         drop-down menu
     *    @param defaultColor  the color that should appear as the default
     *                         on the menu when it is first constructed
     **/
    public BGColorChoiceMenu(GridAppFrame gui, String label,
                             ColorChoiceDDMenu.ColorChoice[] colorChoices,
                             ColorChoiceDDMenu.ColorChoice defaultColor)
    {
        super(label, colorChoices, defaultColor);
        this.gui = gui;
    }

    /** Changes the background color and, if there is a display defined
     *  for the graphical user interface, redisplays the grid.
     **/
    public void act()
    {
        gui.getDisplay().setBackgroundColor(currentColor());
        gui.showGrid();
    }

}
