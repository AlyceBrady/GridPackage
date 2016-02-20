// Interface: GridBackgroundDisplay
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

package edu.kzoo.grid.display;

import java.awt.Graphics2D;

/**
 *  Grid Display Package:<br>
 *
 *  The <code>GridBackgroundDisplay</code> interface specifies the
 *  method that must be provided by any class used to display
 *  a <code>Grid</code> background.
 *
 *  @author Alyce Brady
 *  @version 1 September 2004
 **/
public interface GridBackgroundDisplay
{

    /** Draws the grid background.
     *    @param g2 the Graphics2 object to use to render 
     **/
    void drawBackground(Graphics2D g2);

}
