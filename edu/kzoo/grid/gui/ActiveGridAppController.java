// Class ActiveGridAppController
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

import edu.kzoo.grid.GridObject;

/**
 *  Grid GUI Support Package:<br>
 *
 *  An <code>ActiveGridAppController</code> controls the running of a
 *  grid application.  In each timestep it tells all of the objects in
 *  the grid to act.  The objects in the grid should be instances of a
 *  class that redefines the <code>act</code> method to do something
 *  meaningful.
 *
 *  @author Alyce Brady
 *  @version 10 November 2004
 **/
public class ActiveGridAppController extends SteppedGridAppController
{
    /** Advances the application one step by asking every object in
     *  the grid to <code>act</code>.
     **/
    public void step()
    {
        // Get all the objects in the grid and ask each
        // one to perform the actions it does in a timestep.
        GridObject[] theObjects = getGrid().allObjects();
        for ( int index = 0; index < theObjects.length; index++ )
        {
            theObjects[index].act();
        }
    }

}
