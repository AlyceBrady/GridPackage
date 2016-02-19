// Class: DefaultDisplay
//
// This is a modified version of the College Board's DefaultDisplay class,
// as allowed by the GNU General Public License.  DefaultDisplay is a
// black-box GUI class within the AP(r) CS Marine Biology Simulation
// case study (see www.collegeboard.com/ap/students/compsci).
//
// The modifications were to make DefaultDisplay extend ScaledDisplay and
// paint the text as if in a 100 x 100 cell.
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

/**
 *  Grid Display Package:<br>
 *
 *  A <code>DefaultDisplay</code> draws a centered question-mark.
 *  A <code>DefaultDisplay</code> object is used to display any
 *  grid object that doesn't have a specialized display object.
 *  The association between a particular <code>GridObject</code> 
 *  subclass and its display object is handled in the
 *  <code>DisplayMap</code> class.
 *
 *  <p>
 *  The <code>DefaultDisplay</code> class is based on the
 *  College Board's <code>DefaultDisplay</code> class,
 *  as allowed by the GNU General Public License.
 *
 *  @author Alyce Brady
 *  @author Jeff Raab, Northeastern University
 *  @author Alyce Brady (most recent modifications)
 *  @version 13 December 2002
 *  @see DisplayMap
 **/
public class DefaultDisplay extends ScaledDisplay
{
    /** Draws the given object.
     *  This implementation draws a question mark using the Java
     *  2D Graphics API.
     *  @param   obj        object we want to draw
     *  @param   comp       component on which to draw
     *  @param   g2         drawing surface
     **/
    public void draw(GridObject obj, Component comp, Graphics2D g2)
    {
        // Text rendering does not work well in a 1 x 1 cell, so paint
        // the text as if in a 100 x 100 cell and then scale down by 100.
        float scaleFactor = 0.01f;
    
        // Scale to size of rectangle, adjust stroke back to 1-pixel wide
        g2.scale(scaleFactor, scaleFactor);
        g2.setStroke(new BasicStroke(1.0f/scaleFactor));

       // Set color of question mark and its font.
        g2.setPaint(Color.black);
        g2.setFont(new Font("SansSerif", Font.BOLD, 80));

        // Paint it centered in the 1 x 1 rectangle. 
        paintCenteredText(g2, "?", (float)0, (float)0);
    }

    /** Paints a horizontally and vertically-centered text string.
     *  This method is adapted from p. 134 of J. Knudsen's book, Java 2D Graphics,
     *  published by O'Reilly & Associates (1999).
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
