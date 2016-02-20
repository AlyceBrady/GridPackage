// Class: ScaledImageDisplay
//
// Author: Alyce Brady
//
// This class is based on the College Board's FishImageDisplay class,
// as allowed by the GNU General Public License.  FishImageDisplay is a
// black-box GUI class within the AP(r) CS Marine Biology Simulation
// case study (see
// http://www.collegeboard.com/student/testing/ap/compsci_a/case.html).
//
// Modified 10 March 2005 to allow the image filename to be an absolute
// file name with a full path.
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
import java.awt.MediaTracker;
import java.awt.Graphics2D;
import javax.swing.ImageIcon;

/**
 *  Grid Display Package:<br>
 *
 *  A <code>ScaledImageDisplay</code> uses an image read from a file to
 *  represent an object in a location in a grid.  Images can be rotated
 *  or tinted using appropriate decorators.  <code>ScaledImageDisplay</code>
 *  provides a <code>tint</code> method that can be used by a
 *  tint decorator.
 *
 *  @author Alyce Brady (based on FishImageDisplay by Julie Zelenski)
 *  @version 10 March 2005
 **/
public class ScaledImageDisplay extends ScaledDisplay
{
    private ImageIcon icon;
    private DefaultDisplay defaultDisp = new DefaultDisplay();

    /** Internal constructor that does not initialize the icon
     *  instance variable; subclasses must be sure to set the icon
     *  using the <code>setIcon</code> method (e.g., at the beginning
     *  of a redefined draw method).
     **/
    protected ScaledImageDisplay()
    {
    }

    /** Constructs an object that knows how to display a
     *  GridObject object as an image.  The <code>imageFilename</code>
     *  parameter may name a file in the jar file, may be the absolute
     *  name of a file in the current file system, or may be the name
     *  of a file in the current directory.
     *  @param imageFilename  name of file containing image
     **/
    public ScaledImageDisplay(String imageFilename)
    {    
        java.net.URL urlInJarFile = getClass().getResource(imageFilename);
        if (urlInJarFile != null) 
            icon = new ImageIcon(urlInJarFile);
        else 
        {
            icon = new ImageIcon(imageFilename);
            if ( icon == null )
            {
                String path = System.getProperty("user.dir") + 
                                java.io.File.separator + imageFilename;
                icon = new ImageIcon(path);
            }
        } 
    }

    /** Returns <code>true</code> if the image loaded OK; <code>false</code>
     *  otherwise.
     **/
    public boolean imageLoadedOK()
    {
        return (icon.getImageLoadStatus() == MediaTracker.COMPLETE) ;
    }

    /** Defines the image to use for display purposes. **/
    protected void setIcon(ImageIcon icon)
    {
        this.icon = icon;
    }

    /** Returns the image to use for display purposes. **/
    protected ImageIcon getIcon()
    {
        return this.icon;
    }

    /** Draws a unit-length object using an image.
     *  This implementation draws the object by scaling
     *  the image provided to the constructor.  If the
     *  named file is not found or the file is malformed,
     *  the display will fall back to the DefaultDisplay
     *  class.
     *  @param   obj        object we want to draw
     *  @param   comp       the component we're drawing on
     *  @param   g2         drawing surface
     **/
    public void draw(GridObject obj, Component comp, Graphics2D g2)
    {
        if ( ! imageLoadedOK() ) 
        {
            // Image failed to load, so fall back to default display.
            defaultDisp.draw(obj, comp, g2);
            return;
        }
    
        // Scale to shrink or enlarge the image to fit the size 1x1 cell.
        g2.scale(1.0/icon.getIconWidth(), 1.0/icon.getIconHeight());

        icon.paintIcon(comp, g2, -icon.getIconWidth()/2, -icon.getIconHeight()/2);    
    }


}
