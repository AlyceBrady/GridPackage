// Class: GridChangeListener
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

import edu.kzoo.grid.Grid;

/**
 *  Grid GUI Support Package:<br>
 *
 *  The <code>GridChangeListener</code> interface specifies the
 *  method used to notify <code>GridChangeListener</code> objects
 *  of changes to the grid.
 *
 *  @author Alyce Brady
 *  @version 27 March 2004
 **/
public interface GridChangeListener
{
    /** Reacts to a change in grids being used.
     *    @param newGrid  the new grid being used
     **/
    public void reactToNewGrid(Grid newGrid);
}
