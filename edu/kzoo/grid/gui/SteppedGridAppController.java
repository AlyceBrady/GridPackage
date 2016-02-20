// Class SteppedGridAppController
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
 *  A <code>SteppedGridAppController</code> controls the running of a
 *  grid application.
 *
 *  @author Alyce Brady
 *  @version 29 February 2004
 **/
public abstract class SteppedGridAppController  
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

    /** Initializes or re-initializes the state of the grid application.
     **/
    public void init()
    { 
        throw new UnsupportedOperationException();   // default behavior
    }

    /** Advances the application one step.
     *  (Note: there is no precondition on this method that getGrid()
     *  must return a non-null grid, so subclass implementations should
     *  handle the possibility of a null grid gracefully.)
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

}
