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

import edu.kzoo.grid.Active;
import edu.kzoo.grid.GridObject;

/**
 *  Grid GUI Support Package:<br>
 *
 *  An <code>ActiveGridAppController</code> controls the running of a
 *  grid application.  In each timestep it tells all of the  objects in
 *  the grid to act.  The objects in the grid should be instances of a
 *  class that implements the <code>Active</code> interface; if any object
 *  in the grid is not <code>Active</code>, the <code>step</code> method
 *  ignores it rather than try to invoke an <code>act</code> method on it.
 *
 *  @author Alyce Brady
 *  @version 15 December 2003
 **/
public class ActiveGridAppController extends GridAppController
{
    /** Advances the application one step by asking every object in
     *  the grid to <code>act</code>, assuming that all objects in
     *  the grid are instances of classes that implement the
     *  <code>Active</code> interface.  Any object in the grid that
     *  is not <code>Active</code> is ignored.
     **/
    public void step()
    {
        // Get all the objects in the grid and ask each
        // one to perform the actions it does in a timestep.
        GridObject[] theObjects = getGrid().allObjects();
        for ( int index = 0; index < theObjects.length; index++ )
        {
            try
            {
                ((Active)theObjects[index]).act();
            }
            catch (ClassCastException e) {  /* do nothing */  }
        }
    }

}
