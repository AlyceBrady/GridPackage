// Class: ColorBlockDisplay
//
// Author: Alyce Brady
//   Modified: 21 March 2004: Modified to handle any class that has
//                            a color method, not just ColorBlock objects.
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

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 *  Grid Display Package:<br>
 *
 *    A <code>ColorBlockDisplay</code> object displays a
 *    <code>ColorBlock</code> object, or any object with a
 *    <code>color</code> method, as a color block in a grid.
 *
 *  @author Alyce Brady
 *  @version 21 March 2004
 **/
public class ColorBlockDisplay extends ScaledDisplay
{        
    
    /** Draw the given object as a block of color.
     *  (Precondition: <code>obj</code> has a <code>color</code> method.)
     *  @param   obj        object we want to draw
     *  @param   comp       component on which to draw
     *  @param   g2         drawing surface
     **/
    public void draw(GridObject obj, Component comp, Graphics2D g2)
    {
        // Get the color of the object.
        Class objClass = obj.getClass();
        Color objColor;
        String errPrefix = "Cannot get color for object of " +
                                objClass + " class";
        try
        {
            // Color takes no parameters, so pass empty arrays for params.
            Method colorMethod = objClass.getMethod("color", new Class[0]);
            objColor = (Color)colorMethod.invoke(obj, new Object[0]);
        }
        catch (NoSuchMethodException e)
        { throw new IllegalArgumentException(errPrefix +
                " ; cannot invoke color method."); }
        catch (InvocationTargetException e)
        { throw new IllegalArgumentException(errPrefix +
                " ; exception thrown in color method."); }
        catch (IllegalAccessException e)
        { throw new IllegalArgumentException(errPrefix +
                " ; cannot access color method."); }
        catch (Exception e)
        { throw new IllegalArgumentException(errPrefix + " ."); }

        // Draw a 1 x 1 rectangle centered around (0, 0). Temporarily
        // scale up first.
        float scaleFactor = 10;
        g2.scale(1.0/scaleFactor, 1.0/scaleFactor);
        g2.setPaint(objColor);
        g2.fill(new Rectangle2D.Double(-5, -5, 10, 10));
        g2.scale(scaleFactor, scaleFactor);
    }
    
}
