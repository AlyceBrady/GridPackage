// Class: RotatedTintedImageDisplay
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
import edu.kzoo.grid.Direction;

import java.awt.Component;
import java.awt.Graphics2D;

/**
 *  Grid Display Package:<br>
 *
 *    A <code>RotatedTintedImageDisplay</code> displays an object as a tinted image
 *    in a grid.  The orientation (direction) of the image depends
 *    on the orientation (direction) of the object in the grid.
 *
 *  @author Alyce Brady
 *  @version 13 December 2003
 **/

public class RotatedTintedImageDisplay extends RotatedImageDisplay
{        
    /** Constructs an object that knows how to display a rotatable
     *  tinted image.
     *  @param imageFilename  name of file containing image
     *  @param dir            direction object in image is facing
     **/
    public RotatedTintedImageDisplay(String imageFilename, Direction dir)
    {
        super(imageFilename, dir);
    }

    /** Adjusts the graphics system for drawing a rotated tinted image.
     *  (Precondition: <code>obj</code> has a <code>direction</code> method.)
     */
    public void adjust(GridObject obj, Component comp, Graphics2D g2)
    {
        // Adjust to deal with the rotation, then tint.
        super.adjust(obj, comp, g2);
        tint(obj, comp, g2);
    }
    
}
