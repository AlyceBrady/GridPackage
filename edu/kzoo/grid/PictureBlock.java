// Class: PictureBlock
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

package edu.kzoo.grid;

import javax.swing.ImageIcon;

/**
 *  Grid Container Package:<br>
 *
 *  A <code>PictureBlock</code> object encapsulates a picture (an image
 *  read from a file) to be put in a cell in a grid.
 *
 *  @author Alyce Brady
 *  @version 10 November 2004
 *  @see Grid
 *  @see Location
 **/
public class PictureBlock extends GridObject
{
    // Encapsulated data for each picture block object
    private ImageIcon icon;
    private String description;

    /** Constructs a picture block encapsulating the image in the specified
     *  file.  Looks for the named file first in the jar file, then in the
     *  current directory.
     *  @param imageFilename  name of file containing image
     *  @param description    description of this picture block object (used
     *                        by the <code>toString</code> method)
     **/
    public PictureBlock(String imageFilename, String description)
    {
        this(imageFilename, description, null, null);
    }

    /** Constructs a picture block encapsulating the image in the specified
     *  file.  Looks for the named file first in the jar file, then in the
     *  current directory.
     *  @param imageFilename  name of file containing image
     *  @param description    description of this picture block object (used
     *                        by the <code>toString</code> method)
     *  @param grid        the grid containing this picture block
     *  @param loc         the location of the picture block in <code>grid</code>
     **/
    public PictureBlock(String imageFilename, String description,
                        Grid grid, Location loc)
    {
        super(grid, loc);
        this.description = description;
        java.net.URL urlInJarFile = getClass().getResource(imageFilename);
        if (urlInJarFile != null) 
            icon = new ImageIcon(urlInJarFile);
        else 
        {
            String path = System.getProperty("user.dir") + 
                            java.io.File.separator + imageFilename;
            icon = new ImageIcon(path);
        } 
    }

    /** Gets picture associated with this picture block.
     **/
    public ImageIcon pictureIcon()
    {
        return icon;
    }

    /** Returns the description of the picture provided to the constructor. **/
    public String toString()
    {
        return description;
    }

}
