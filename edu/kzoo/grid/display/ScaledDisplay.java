// Class: ScaledDisplay
//
// Author: Alyce Brady
//
// This class is based on the College Board's AbstractFishDisplay class,
// as allowed by the GNU General Public License.  AbstractFishDisplay is a
// black-box GUI class within the AP(r) CS Marine Biology Simulation
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

import edu.kzoo.grid.GridObject;

import java.awt.BasicStroke;
import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 *  Grid Display Package:<br>
 *
 *  This abstract class provides common implementation code for
 *  drawing a GridObject object.  The class will translate and
 *  scale the graphics system as needed and then invoke its
 *  abstract <code>draw</code> method. Subclasses of this abstract class 
 *  define <code>draw</code> to just display an object with a fixed size.
 *
 *  @author Alyce Brady (based on AbstractFishDisplay by Julie Zelenski)
 *  @version 13 December 2003
 **/
public abstract class ScaledDisplay implements GridObjectDisplay
{        
    /** Draw the given GridObject object.
     *  Subclasses should implement this method to draw the object in a 
     *  cell of size (1,1) centered around (0,0) on the drawing surface.
     *  (All scaling has been done beforehand).
     *  @param   obj        object we want to draw
     *  @param   comp       component on which to draw
     *  @param   g2         drawing surface
     **/
    abstract public void draw(GridObject obj, Component comp, Graphics2D g2);
    
    
    /** Draw the given object.
     *  Scales the coordinate appropriately then invokes the simple
     *  draw method above that is only responsible for drawing a
     *  unit-length object.
     *  @param   obj        object we want to draw
     *  @param   comp       component on which to draw
     *  @param   g2         drawing surface
     *  @param   rect       rectangle in which to draw 
     **/
    public void draw(GridObject obj, Component comp, Graphics2D g2,
                     Rectangle rect)
    {        
        // Translate to center of object
        g2.translate(rect.x + rect.width/2, rect.y + rect.height/2);
    
        // Scale to size of rectangle, adjust stroke back to 1-pixel wide
        float scaleFactor = Math.min(rect.width, rect.height);
        g2.scale(scaleFactor, scaleFactor);
        g2.setStroke(new BasicStroke(1.0f/scaleFactor));

        // Adjust (e.g., rotate) as necessary.
        adjust(obj, comp, g2);

        draw(obj, comp, g2);
    }
    
    /** Adjusts the graphics system for drawing an object, as appropriate.
     *  This method actually makes no further adjustments, but subclasses
     *  could override this method to rotate the object, for example.
     */
    public void adjust(GridObject obj, Component comp, Graphics2D g2)
    {
    }    
    
}
