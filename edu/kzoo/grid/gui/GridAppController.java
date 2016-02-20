// Class GridAppController
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

import java.lang.UnsupportedOperationException;

/**
 *  Grid GUI Support Package:<br>
 *
 *  A <code>GridAppController</code> controls the running of a
 *  grid application.
 *
 *  @author Alyce Brady
 *  @version 29 February 2004
 **/
public abstract class GridAppController  
{
    private Grid grid = null;

    /** Gets the application's grid.
     *    @return the grid being controlled
     **/
    public Grid getGrid()
    {
        return this.grid;
    }

    /** Sets the application's grid.
     *  (Precondition: grid is not null.)
     *    @param grid the Grid to act on
     **/
    public void setGrid(Grid grid)
    {
        this.grid = grid;
    }

    /** Advances the application one step. 
     **/
    public abstract void step();

    /** Determines whether a running application has reached
     *  a desired stopping state.  Examples include whether the
     *  mouse in a maze has found the cheese, whether the first
     *  (or last) competitor in a race has finished, or whether
     *  a chemical reaction has reached equilibrium.
     *    @return <code>true</code> if the application should
     *             stop 
     **/
    public boolean hasReachedStoppingState()
    { 
        return false;   // default behavior
    }

    /** Re-initializes the state of the grid application for
     *  a restart.
     **/
    public void restart()
    { 
        throw new UnsupportedOperationException();   // default behavior
    }

}
