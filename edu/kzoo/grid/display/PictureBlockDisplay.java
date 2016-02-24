// Class: PictureBlockDisplay
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
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.swing.ImageIcon;

/**
 *  Grid Display Package:<br>
 *
 *    A <code>PictureBlockDisplay</code> object displays a
 *    <code>PictureBlock</code> object, or any object with a
 *    <code>pictureIcon</code> method, as a picture in a grid.
 *
 *  @author Alyce Brady
 *  @version 10 November 2004
 **/
public class PictureBlockDisplay extends ScaledImageDisplay
{
    /** Constructs a display object that can display grid objects that have
     *  a <code>pictureIcon</code> method.
     **/    
    public PictureBlockDisplay()
    {
    }

    /** Draws a unit-length object using an image.
     *  This implementation draws the object by scaling
     *  the image provided to the constructor.  It calls
     *  the <code>adjust</code> method to make further
     *  adjustments (for example, rotating and tinting
     *  the image) as appropriate.
     *  (Precondition: <code>obj</code> has a <code>pictureIcon</code> method.)
     *  @param   obj        object we want to draw
     *  @param   comp       component on which to draw
     *  @param   g2         drawing surface
     **/
    public void draw(GridObject obj, Component comp, Graphics2D g2)
    {
        Class<? extends GridObject> objClass = obj.getClass();

        // Get the picture to display.
        String errorBeginning = "Cannot get picture for object of " +
                                objClass + " class; ";
        ImageIcon objIcon;
        try
        {
            Method iconMethod = objClass.getMethod("pictureIcon", new Class[0]);
            objIcon = (ImageIcon)iconMethod.invoke(obj, new Object[0]);
        }
        catch (NoSuchMethodException e)
        { throw new IllegalArgumentException(errorBeginning +
            "cannot invoke pictureIcon method."); }
        catch (InvocationTargetException e)
        { throw new IllegalArgumentException(errorBeginning +
            "exception thrown in pictureIcon method."); }
        catch (IllegalAccessException e)
        { throw new IllegalArgumentException(errorBeginning +
            "cannot access pictureIcon method."); }

        setIcon(objIcon);
        super.draw(obj, comp, g2);
    }
    
}
