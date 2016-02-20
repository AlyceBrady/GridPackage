// Class: ScaledImageTintDecorator
//
// Author: Joel Booth
//
// This decorator allows objects being displayed to adjust for a tint color.
// It can only be applied to ScaledImageDisplay objects.  The associated
// Object must have a Color.  
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

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.FilteredImageSource;
import java.awt.image.RGBImageFilter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

import edu.kzoo.grid.GridObject;

/**
 *  Grid Display Package:<br>
 *
 * A tinting decorator for a <code>ScaledImageDisplay</code>.  The associated
 * object must have a <code>color</code> method.
 * (Precondition: the <code>GridObjectDisplay</code> object to which this
 * decorator is applied must be a <code>ScaledImageDisplay</code> and the
 * grid objects it displays must have a <code>color</code> method.)
 *
 * @author Joel Booth
 * @version 28 July 2004
 *
 */
public class ScaledImageTintDecorator implements DisplayDecorator
{
    private HashMap tintedVersions = new HashMap();

	/**
	 * Decorate the ScaledImageDisplay so that it appears tinted.
	 */
	public void decorate(GridObjectDisplay disp, GridObject obj,
                         Component comp, Graphics2D g2)
    { 
		tint((ScaledImageDisplay) disp, obj, comp, g2);

	}
    
    /** Adjusts the graphics system to use an object's color to tint an image.
     *  (Precondition: <code>obj</code> has a <code>color</code> method.)
     *  @param   obj        object we want to draw
     *  @param   comp       the component we're drawing on
     *  @param   g2         drawing surface
     **/
    public void tint(ScaledImageDisplay imageDisplay, GridObject obj,
                     Component comp, Graphics2D g2)
    {
        // Use the object's color as an image filter.
        Class objClass = obj.getClass();
        try
        {
            Method colorMethod = objClass.getMethod("color", new Class[0]);
            Color col = (Color)colorMethod.invoke(obj, new Object[0]);
            Image tinted = (Image)tintedVersions.get(col);
            if (tinted == null)     // not cached, need new filter for color
            {
                Image originalImage = imageDisplay.getIcon().getImage();
                FilteredImageSource src = 
                    new FilteredImageSource(originalImage.getSource(), 
                                            new TintFilter(col));
                tinted = comp.createImage(src);
                // Cache tinted image in map by color, we're likely to need it again.
                tintedVersions.put(col, tinted);
            }
            
            imageDisplay.getIcon().setImage(tinted);
        }
        catch (NoSuchMethodException e)
        { throw new IllegalArgumentException("Cannot tint object of " + objClass +
            " class; cannot invoke color method."); }
        catch (InvocationTargetException e)
        { throw new IllegalArgumentException("Cannot tint object of " + objClass +
            " class; exception in color method."); }
        catch (IllegalAccessException e)
        { throw new IllegalArgumentException("Cannot tint object of " + objClass +
            " class; cannot access color method."); }
    }


    /** An image filter class that tints colors based on the tint provided
     *  to the constructor.
     **/
    private static class TintFilter extends RGBImageFilter 
    {
        private int tintR, tintG, tintB;
        
        /** Constructs an image filter for tinting colors in an image. **/
        public TintFilter(Color color)
        {
            canFilterIndexColorModel = true;
            int rgb = color.getRGB();
            tintR = (rgb >> 16) & 0xff;
            tintG = (rgb >> 8) & 0xff;
            tintB =  rgb & 0xff;
        }
                
        public int filterRGB(int x, int y, int argb)
        {
            // Separate pixel into its RGB coomponents.
            int alpha = (argb >> 24) & 0xff;
            int red =   (argb >> 16) & 0xff;
            int green = (argb >> 8) & 0xff;
            int blue =   argb & 0xff;
            
            // Use NTSC/PAL algorithm to convert RGB to grayscale.
            int lum = (int) (0.2989 * red + 0.5866 * green + 0.1144 * blue);
            
            // Interpolate along spectrum black->white with tint at midpoint
            double scale = Math.abs((lum - 128)/128.0); // absolute distance from midpt
            int edge = lum < 128 ? 0 : 255; // going towards white or black?
            red =   tintR + (int)((edge - tintR) * scale); // scale from midpt to edge
            green = tintG + (int)((edge - tintG) * scale);
            blue =  tintB + (int)((edge - tintB) * scale);
            return (alpha << 24) | (red << 16) | (green << 8) | blue;
        }

    }

}
