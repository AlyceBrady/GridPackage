// Interface: GridDisplay
//
// Author: Alyce Brady
//
// This class is based on the College Board's EnvDisplay class, as
// allowed by the GNU General Public License.  EnvDisplay is a
// black-box class within the AP(r) CS Marine Biology Simulation
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

package edu.kzoo.grid.display;

import edu.kzoo.grid.Grid;

/**
 *  Grid Display Package:<br>
 *
 *  The <code>GridDisplay</code> interface specifies the
 *  methods that must be provided by any class used to display
 *  a <code>Grid</code> object and its contents.
 *
 *  <p>
 *  The <code>GridDisplay</code> class is based on the
 *  College Board's <code>EnvDisplay</code> class,
 *  as allowed by the GNU General Public License.
 *
 *  @author Alyce Brady
 *  @version 13 December 2003
 **/
public interface GridDisplay
{
    /** Sets the Grid being displayed.
     *  @param grid the Grid to display
     **/
    void setGrid(Grid grid);

    /** Shows the current state of the grid.
     **/
    void showGrid();

}
