// Class: NewBoundedGridButton
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

import edu.kzoo.grid.BoundedGrid;
import edu.kzoo.grid.Grid;
import edu.kzoo.grid.gui.ControlButton;
import edu.kzoo.grid.gui.GridAppFrame;
import edu.kzoo.grid.gui.GridCreationDialog;

/**
 *  Grid GUI Nuggets Package (Handy Grid GUI Components):<br>
 *
 *    The <code>NewBoundedGridButton</code> class represents a button
 *    that prompts the user for the dimensions of a new grid and then
 *    constructs that grid.
 *
 *  @author Alyce Brady
 *  @version 1 September 2004
 **/
public class NewBoundedGridButton extends ControlButton
{
    // Instance Variables: Encapsulated data for each object
    private GridCreationDialog gridCreationDialog = null;


  // constructor

    /** Constructs a button labeled "Create New Grid" that will create a
     *  new BoundedGrid object with dimensions specified by the user
     *  through a dialog box.
     *    @param gui graphical user interface containing this button
     **/
    public NewBoundedGridButton(GridAppFrame gui)
    {
        this(gui, "Create New Grid");
    }

    /** Constructs a button that will create a new BoundedGrid object with
     *  dimensions specified by the user through a dialog box.
     *    @param gui graphical user interface containing this button
     *    @param label  label to place on button
     **/
    public NewBoundedGridButton(GridAppFrame gui, String label)
    {
        super(gui, label, true);
    }

    /** Creates a new grid and notifies the user interface.  The grid
     *  dimensions come from a dialog box presented to the user; the
     *  default grid dimensions if the user doesn't provide any are 10 x 10.
     **/
    public void act()
    {
        GridAppFrame gui = getGUI();

        // Ask user for grid dimensions.
        if ( gridCreationDialog == null )
            gridCreationDialog = GridCreationDialog.makeDimensionsDialog(gui);
        Grid newGrid = gridCreationDialog.showDialog();

        // Using user-provided grid dimensions (or default 10 x 10),
        // construct the grid.
        if ( gui.getGrid() == null && newGrid == null )
            newGrid = new BoundedGrid(10, 10);
        if ( newGrid != null )
            gui.setGrid(newGrid);
    }

}
