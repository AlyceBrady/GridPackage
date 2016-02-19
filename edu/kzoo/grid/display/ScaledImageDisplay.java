// Class: ScaledImageDisplay
//
// Author: Alyce Brady
//
// This class is based on the College Board's FishImageDisplay class,
// as allowed by the GNU General Public License.  FishImageDisplay is a
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

import java.awt.Color;
import java.awt.Component;
import java.awt.MediaTracker;
import java.awt.Image;
import java.awt.image.FilteredImageSource;
import java.awt.image.RGBImageFilter;
import java.awt.Graphics2D;
import javax.swing.ImageIcon;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

/**
 *  Grid Display Package:<br>
 *
 *  A <code>ScaledImageDisplay</code> uses an image read from a file to
 *  represent an object with a location in a grid.  This display
 *  class does not rotate or tint the image, but subclasses could redefine
 *  the <code>draw</code> method to do so.  The <code>ScaledDisplay</code>
 *  superclass provides an <code>adjustForDirection</code> method, and
 *  <code>ScaledImageDisplay</code> provides a <code>tint</code> method.
 *
 *  @author Alyce Brady (based on FishImageDisplay by Julie Zelenski)
 *  @version 13 December 2003
 **/
public class ScaledImageDisplay extends ScaledDisplay
{
    private ImageIcon icon;
    private Image originalImage;
    private DefaultDisplay defaultDisp;
    private HashMap tintedVersions = new HashMap();
    
    /** Constructs an object that knows how to display a
     *  GridObject object as an image.  Looks for the
     *  named file first in the jar file, then in the
     *  current directory.  If the named file is not found
     *  or the file is malformed, the display will fall
     *  back to the DefaultDisplay class.
     *  @param imageFilename  name of file containing image
     **/
    public ScaledImageDisplay(String imageFilename)
    {    
        java.net.URL urlInJarFile = getClass().getResource(imageFilename);
        if (urlInJarFile != null) 
            icon = new ImageIcon(urlInJarFile);
        else 
        {
            String path = System.getProperty("user.dir") + 
                            java.io.File.separator + imageFilename;
            icon = new ImageIcon(path);
        } 
        originalImage = icon.getImage();
        defaultDisp = new DefaultDisplay();
    }
  
    /** Draws a unit-length object using an image.
     *  This implementation draws the object by scaling
     *  the image provided to the constructor.  It calls
     *  the <code>adjust</code> method to make further
     *  adjustments (for example, rotating and tinting
     *  the image) as appropriate.
     *  methods
     *  @param   obj        object we want to draw
     *  @param   comp       the component we're drawing on
     *  @param   g2         drawing surface
     **/
    public void draw(GridObject obj, Component comp, Graphics2D g2)
    {
        if (icon.getImageLoadStatus() != MediaTracker.COMPLETE) 
        {
            // Image failed to load, so fall back to default display.
            defaultDisp.draw(obj, comp, g2);
            return;
        }
    
        // Scale to shrink or enlarge the image to fit the size 1x1 cell.
        g2.scale(1.0/icon.getIconWidth(), 1.0/icon.getIconHeight());

        icon.paintIcon(comp, g2, -icon.getIconWidth()/2, -icon.getIconHeight()/2);    
    }
    
    /** Adjusts the graphics system for drawing an object, as appropriate.
     *  This method actually makes no further adjustments, but subclasses
     *  could override this method to rotate or color the object, for example.
     */
    public void adjust(GridObject obj, Component comp, Graphics2D g2)
    {
    }
    
    /** Adjusts the graphics system to use an object's color to tint an image.
     *  (Precondition: <code>obj</code> has a <code>color</code> method.)
     *  @param   obj        object we want to draw
     *  @param   comp       the component we're drawing on
     *  @param   g2         drawing surface
     **/
    public void tint(GridObject obj, Component comp, Graphics2D g2)
    {        
        // Use the object's color as an image filter.
        Class objClass = obj.getClass();
        try
        {
            Method colorMethod = objClass.getMethod("color", new Class[0]);
            Color col = (Color)colorMethod.invoke(obj, new Object[0]);
            Image tinted = (Image)tintedVersions.get(col);
            if (tinted == null) 	// not cached, need new filter for color
            {
                FilteredImageSource src = 
                    new FilteredImageSource(originalImage.getSource(), 
                                            new TintFilter(col));
                tinted = comp.createImage(src);
                // Cache tinted image in map by color, we're likely to need it again.
                tintedVersions.put(col, tinted);
            }
            
            icon.setImage(tinted);
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
            int edge = lum < 128 ? 0 : 255;	// going towards white or black?
            red =   tintR + (int)((edge - tintR) * scale); // scale from midpt to edge
            green = tintG + (int)((edge - tintG) * scale);
            blue =  tintB + (int)((edge - tintB) * scale);
            return (alpha << 24) | (red << 16) | (green << 8) | blue;
        }

    }

}
