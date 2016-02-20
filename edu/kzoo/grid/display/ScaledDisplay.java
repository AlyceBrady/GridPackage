// Class: ScaledDisplay
//
// Author: Alyce Brady, Joel Booth
//
// This class is based on the College Board's AbstractFishDisplay class,
// as allowed by the GNU General Public License.  AbstractFishDisplay is a
// black-box GUI class within the AP(r) CS Marine Biology Simulation
// case study (see
// http://www.collegeboard.com/student/testing/ap/compsci_a/case.html).
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

import java.util.ArrayList;

/**
 *  Grid Display Package:<br>
 *
 *  This abstract class provides common implementation code for
 *  drawing a <code>GridObject</code> object.  The class will translate and
 *  scale the graphics system as needed and then invoke its
 *  abstract <code>draw</code> method. Subclasses of this abstract class 
 *  define <code>draw</code> to just display an object with a fixed size.
 *
 *  @author Alyce Brady (based on AbstractFishDisplay by Julie Zelenski)
 *  @author Joel Booth (Added the mechanism to allow for decorators) 
 *  @version 28 July 2004
 **/
public abstract class ScaledDisplay implements GridObjectDisplay
{        
	
	// Instance Variables
	private ArrayList<DisplayDecorator> decorations =
	                                    new ArrayList<DisplayDecorator>();
	
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

        // Apply the decorators
        for ( DisplayDecorator decorator : decorations )
        {
            decorator.decorate(this, obj, comp, g2);
        }

        // Adjust (e.g., rotate) as necessary.
        adjust(obj, comp, g2);
        
        // Draw the image
        draw(obj, comp, g2);
    }
    
    /** Add a Decorator to the display in order to add some functionality.
     * 
     *  @param d The decorator to add
     */
    public void addDecorator(DisplayDecorator d)
    {
		decorations.add(d);
    }
    
    /** Remove a Decorator from the display
     * 
     *  @param d The decorator to remove
     */
	public void removeDecorator(DisplayDecorator d)
	{
		decorations.remove(d);
	}
    
    
    /** Adjusts the graphics system for drawing an object, as appropriate.
     *  This method actually makes no further adjustments, but subclasses
     *  could override this method to rotate the object, for example.
     *  [This is a deprecated alternative to providing a decorator.]
     */
    protected void adjust(GridObject obj, Component comp, Graphics2D g2)
    {
    }    
    
}
