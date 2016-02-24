// Class: TextDisplay
//
// This is a modified version of the College Board's DefaultDisplay class,
// as allowed by the GNU General Public License.  DefaultDisplay is a
// black-box GUI class within the AP(r) CS Marine Biology Simulation
// case study (see
// http://www.collegeboard.com/student/testing/ap/compsci_a/case.html).
//
// The modifications were to make TextDisplay extend ScaledDisplay,
// paint the text as if in a 100 x 100 cell, and use alternative values
// if the object being drawn does not have text or color methods.
//
// The original copyright and license information for DefaultDisplay is:
//
// AP(r) Computer Science Marine Biology Simulation:
// The DefaultDisplay class is copyright(c) 2002 College Entrance
// Examination Board (www.collegeboard.com).
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

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.font.FontRenderContext;
import java.awt.font.LineMetrics;
import java.awt.geom.Rectangle2D;
import java.awt.Graphics2D;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 *  Grid Display Package:<br>
 *
 *  A <code>TextDisplay</code> draws a centered text string in a
 *  grid cell.  If the object it is asked to display has a
 *  <code>text</code> method, it displays the text returned by that
 *  string, otherwise it displays the text returned by the object's
 *  <code>toString</code> method.  If the object it is asked to
 *  display has a <code>color</code> method, it displays the text
 *  in that color, otherwise it displays it in black.  The
 *  <code>TextDisplay</code> class works best for very short text
 *  strings, especially one-character strings; longer strings will
 *  not fit in the bounds of the object's grid cell location and if
 *  there are objects in the neighboring cells they will obscure the
 *  text that does not fit in its own cell.
 *
 *  <p>
 *  The <code>TextDisplay</code> class is based on the
 *  College Board's <code>DefaultDisplay</code> class,
 *  as allowed by the GNU General Public License.
 *
 *  @author Alyce Brady
 *  @author Jeff Raab, Northeastern University
 *  @author Alyce Brady (most recent modifications)
 *  @version 15 September 2004
 **/
public class TextDisplay extends ScaledDisplay
{
    /** Draws the given object.
     *  This implementation draws a text string using the Java
     *  2D Graphics API.
     *  @param   obj        object we want to draw
     *  @param   comp       component on which to draw
     *  @param   g2         drawing surface
     **/
    public void draw(GridObject obj, Component comp, Graphics2D g2)
    {
        // Get the text associated with the object (if any).
        String objText = getText(obj);

        // Get the color of the object (if any) or black.
        Color objColor = getTextColor(obj);

        // Text rendering does not work well in a 1 x 1 cell, so paint
        // the text as if in a 100 x 100 cell and then scale down by 100.
        float scaleFactor = 0.01f;
    
        // Scale to size of rectangle, adjust stroke back to 1-pixel wide
        g2.scale(scaleFactor, scaleFactor);
        g2.setStroke(new BasicStroke(1.0f/scaleFactor));

       // Set color of question mark and its font.
        g2.setPaint(objColor);
        g2.setFont(new Font("SansSerif", Font.BOLD, 80));

        // Paint it centered in the rectangle. 
        paintCenteredText(g2, objText, (float)0, (float)0);
    }

    /** Gets the text string to draw.
     */
    protected String getText(GridObject obj)
    {
        try
        {
            return (String) invokeAccessorMethod(obj, "text");
        }
        catch(Exception e) { return obj.toString(); }
    }

    /** Gets the text color.
     */
    protected Color getTextColor(GridObject obj)
    {
        try
        {
            return (Color) invokeAccessorMethod(obj, "color");
        }
        catch(Exception e) { return Color.BLACK; }
    }

    /** Invokes the named method on the specified object. **/
    protected Object invokeAccessorMethod(GridObject obj, String methodName)
    {
        Class<? extends GridObject> objClass = obj.getClass();
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

    /** Paints a horizontally and vertically-centered text string.
     *  This method is adapted from p. 134 of J. Knudsen's book,
     *  Java 2D Graphics, published by O'Reilly & Associates (1999).
     *  @param g2           drawing surface
     *  @param s            string to draw (centered)
     *  @param centerX      x-coordinate of center point
     *  @param centerY      y-coordinate of center point 
     **/
    protected void paintCenteredText(Graphics2D g2,
                            String s, float centerX, float centerY)
    {
        FontRenderContext frc = g2.getFontRenderContext();
        Rectangle2D bounds = g2.getFont().getStringBounds(s, frc);
        float leftX = centerX -  (float)bounds.getWidth()/2;
        LineMetrics lm = g2.getFont().getLineMetrics(s, frc);
        float baselineY = centerY - lm.getHeight()/2 + lm.getAscent();
        g2.drawString(s, leftX, baselineY);
    }

}
