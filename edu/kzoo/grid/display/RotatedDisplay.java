// Class: RotatedDisplay
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

import edu.kzoo.grid.Direction;
import edu.kzoo.grid.GridObject;

import java.awt.Component;
import java.awt.Graphics2D;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;

/**
 *  Grid Display Package:<br>
 *  This class should no longer be used.  It will remain in the package for
 *  sole purpose of allowing the MBSInAGrid examples to work.  All
 *  new applications should use the appropriate display and then
 *  add a <code>RotatedDecorator</code>.<br>
 *  This abstract class provides common implementation code for
 *  drawing a grid object.  The class will translate, scale, and
 *  rotate the graphics system as needed and then invoke its
 *  abstract <code>draw</code> method. Subclasses of this abstract class 
 *  define <code>draw</code> to just display an object with a fixed size.
 *
 *  @author Alyce Brady (based on AbstractFishDisplay by Julie Zelenski)
 *  @version 13 December 2003
 **/
public abstract class RotatedDisplay extends ScaledDisplay
{

    /** Adjusts the graphics system for drawing a rotated object.
     *  (Precondition: <code>obj</code> has a <code>direction</code> method.)
     */
    public void adjust(GridObject obj, Component comp, Graphics2D g2)
    {
        // Rotate to represent the direction the object is facing.
        adjustForDirection(obj, g2);
    }

    /** Adjusts the graphics system for drawing an object with direction.
     *  (Precondition: <code>obj</code> has a <code>direction</code> method.)
     *  @param   obj        object we want to draw
     *  @param   g2         drawing surface
     **/
    public static int adjustForDirection(GridObject obj, Graphics2D g2)
    {        
        // Rotate drawing surface to capture object's orientation
        // (direction).  (Assumption is that without rotating the
        // drawing surface the object will be drawn facing North.)
        // Object must have a direction method.
        Class objClass = obj.getClass();
        try
        {
            Method dirMethod = objClass.getMethod("direction", new Class[0]);
            Direction dir = (Direction)dirMethod.invoke(obj, new Object[0]);
            int rotationInDegrees = dir.inDegrees();
            g2.rotate(Math.toRadians(rotationInDegrees));
            return rotationInDegrees;
        }
        catch (NoSuchMethodException e)
        { throw new IllegalArgumentException("Cannot rotate object of " + objClass +
            " class; cannot invoke direction method."); }
        catch (InvocationTargetException e)
        { throw new IllegalArgumentException("Cannot rotate object of " + objClass +
            " class; exception in direction method."); }
        catch (IllegalAccessException e)
        { throw new IllegalArgumentException("Cannot rotate object of " + objClass +
            " class; cannot access direction method."); }
    }

}
