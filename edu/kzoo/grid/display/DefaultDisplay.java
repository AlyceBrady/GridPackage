// Class: DefaultDisplay
//
// Modified: 15 September 2004: Most functionality moved to the TextDisplay
//                              class.
//
// This class is free software; you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation.
//
// This class is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.

package edu.kzoo.grid.display;

import edu.kzoo.grid.GridObject;

/**
 *  Grid Display Package:<br>
 *
 *  A <code>DefaultDisplay</code> draws a centered question-mark.
 *  A <code>DefaultDisplay</code> object is used to display any
 *  grid object that doesn't have a specialized display object.
 *  The association between a particular <code>GridObject</code> 
 *  subclass and its display object is handled in the
 *  <code>DisplayMap</code> class.
 *
 *  @author Alyce Brady
 *  @version 15 September 2004
 *  @see DisplayMap
 **/
public class DefaultDisplay extends TextDisplay
{

    /** Gets the text string to draw.
     */
    protected String getText(GridObject obj)
    {
        return "?";
    }

}
