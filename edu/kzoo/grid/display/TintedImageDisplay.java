// Class: TintedImageDisplay
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

import edu.kzoo.grid.GridObject;

import java.awt.Component;
import java.awt.Graphics2D;

/**
 *  Grid Display Package:<br>
 *
 *    A <code>TintedImageDisplay</code> displays an object as a
 *    tinted image in a grid.
 *
 *  @author Alyce Brady
 *  @version 13 December 2003
 **/
public class TintedImageDisplay extends ScaledImageDisplay
{        
    
    /** Constructs an object that knows how to display a
     *  tinted image.
     *  @param imageFilename  name of file containing image
     **/
    public TintedImageDisplay(String imageFilename)
    {
        super(imageFilename);
    }

    /** Adjusts the graphics system for drawing a tinted image.
     */
    public void adjust(GridObject obj, Component comp, Graphics2D g2)
    {
        tint(obj, comp, g2);
    }
    
}
