// Class: RotatedImageDisplay
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

import edu.kzoo.grid.Direction;
import edu.kzoo.grid.GridObject;

import java.awt.Component;
import java.awt.Graphics2D;

/**
 *  Grid Display Package:<br>
 *
 *    A <code>RotatedImageDisplay</code> displays an object in a grid.
 *    The orientation (direction) of the image depends on the
 *    orientation (direction) of the object in the grid.
 *
 *  @author Alyce Brady
 *  @version 13 December 2003
 **/

public class RotatedImageDisplay extends ScaledImageDisplay
{        
    private Direction originalDirection;
    
    /** Constructs an object that knows how to display a rotatable image.
     *  @param imageFilename  name of file containing image
     *  @param dir            direction object in image is facing
     **/
    public RotatedImageDisplay(String imageFilename, Direction dir)
    {
        super(imageFilename);
        originalDirection = dir;
    }

    /** Adjusts the graphics system for drawing a rotated image.
     *  (Precondition: <code>obj</code> has a <code>direction</code> method.)
     */
    public void adjust(GridObject obj, Component comp, Graphics2D g2)
    {
        // Rotate drawing surface to compensate for the direction of the object
        // in the image (in case it is not facing North, as assumed). 
        if ( ! originalDirection.equals(Direction.NORTH) )
        {
            int rotationInDegrees = originalDirection.inDegrees();
            g2.rotate(- Math.toRadians(rotationInDegrees));
            if (rotationInDegrees >= 180)
                g2.scale(1, -1); // flip image upside-down
        }

        // Now rotate again to represent the direction the object is facing.
        adjustForDirection(obj, g2);
    }

    /** Adjusts the graphics system for drawing an object with direction.
     *  (Precondition: <code>obj</code> has a <code>direction</code> method.)
     *  @param   obj        object we want to draw
     *  @param   g2         drawing surface
     **/
    public int adjustForDirection(GridObject obj, Graphics2D g2)
    {
        int rotationInDegrees = RotatedDisplay.adjustForDirection(obj, g2);
        if (rotationInDegrees >= 180)
            g2.scale(1, -1); // flip image upside-down
        return rotationInDegrees;
    }

}
