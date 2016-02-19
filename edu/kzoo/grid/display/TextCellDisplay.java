// Class: TextCellDisplay
//
// Author: Alyce Brady
//   Modified: 21 March 2004: Modified to handle any class that has text and
//                            color methods, not just TextCell objects.
//
// This class is based on the College Board's DefaultDisplay class,
// as allowed by the GNU General Public License.  DefaultDisplay is a
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
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics2D;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 *  Grid Display Package:<br>
 *
 *    A <code>TextCellDisplay</code> object displays a
 *    <code>TextCell</code> object, or any object with
 *    <code>text</code> and <code>color</code> methods,
 *    in a grid.
 *
 *  @author Alyce Brady
 *  @version 21 March 2004
 **/
public class TextCellDisplay extends DefaultDisplay
{
    /** Draws the given object.
     *  This implementation draws a string using the Java
     *  2D Graphics API.
     *  (Precondition: <code>obj</code> is a <code>TextCell</code> object.)
     *  @param   obj        object we want to draw
     *  @param   comp       component on which to draw
     *  @param   g2         drawing surface
     **/
    public void draw(GridObject obj, Component comp, Graphics2D g2)
    {
        // Get the text associated with the object.
        String objText = (String) invokeAccessorMethod(obj, "text");

        // Get the color of the object.
        Color objColor = (Color) invokeAccessorMethod(obj, "color");

        // Text rendering does not work well in a 1 x 1 cell, so paint
        // the text as if in a 100 x 100 cell and then scale down by 100.
        float scaleFactor = 0.01f;
    
        // Scale to size of rectangle, adjust stroke back to 1-pixel wide
        g2.scale(scaleFactor, scaleFactor);
        g2.setStroke(new BasicStroke(1.0f/scaleFactor));

        // Set color of text and its font.
        g2.setPaint(objColor);
        g2.setFont(new Font("SansSerif", Font.BOLD, 80));

        // Paint the text centered in the 1 x 1 rectangle. 
        paintCenteredText(g2, objText, (float)0, (float)0);
    }

    /** Invokes the named method on the specified object. **/
    protected Object invokeAccessorMethod(GridObject obj, String methodName)
    {
        Class objClass = obj.getClass();
        Object returnValue;
        try
        {
            Method textMethod = objClass.getMethod(methodName, new Class[0]);
            returnValue = textMethod.invoke(obj, new Object[0]);
        }
        catch (NoSuchMethodException e)
        { throw new IllegalArgumentException("Cannot invoke " + methodName +
            " method for object of " + objClass + " class."); }
        catch (InvocationTargetException e)
        { throw new IllegalArgumentException("Cannot get " + methodName +
            " for object of " + objClass + " class; exception thrown in " + 
            methodName + " method."); }
        catch (IllegalAccessException e)
        { throw new IllegalArgumentException("Cannot get " + methodName +
            " for object of " + objClass + " class; cannot access " +
            methodName + " method."); }
        return returnValue;
    }

}
