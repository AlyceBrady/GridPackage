// Class: ColorBlock
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

import java.awt.Color;

/**
 *  Grid Container Package:<br>
 *
 *  A <code>ColorBlock</code> object encapsulates a color for a colored
 *  cell in a grid.
 *
 *  @author Alyce Brady
 *  @version 13 December 2003
 *  @see Grid
 *  @see Location
 **/
public class ColorBlock extends GridObject
{
    // Encapsulated data for each color block object
    private Color    theColor;      // the color of this color block

    /** Constructs a color block with a random color.
     **/
    public ColorBlock()
    {
        this(NamedColor.getRandomColor());
    }

    /** Constructs a color block with the specified color.
     *  @param colorValue  the color that fills this color block
     **/
    public ColorBlock(Color colorValue)
    {
        super();
        theColor = colorValue;
    }

    /** Constructs a color block with a random color in the specified grid.
     *  @param grid        the grid containing this color block
     *  @param loc         the location of the color block in <code>grid</code>
     **/
    public ColorBlock(Grid grid, Location loc)
    {
        this(grid, loc, NamedColor.getRandomColor());
    }

    /** Constructs a color block with the specified color.
     *  @param colorValue  the color that fills this color block
     *  @param grid        the grid containing this color block
     *  @param loc         the location of the color block in <code>grid</code>
     **/
    public ColorBlock(Color colorValue, Grid grid, Location loc)
    {
        this(grid, loc, colorValue);
    }

    /** Constructs a color block with the specified color.  (This is the
     *  parameter order used by the GridPkgFactory.)
     *  @param grid        the grid containing this color block
     *  @param loc         the location of the color block in <code>grid</code>
     *  @param colorValue  the color that fills this color block
     **/
    public ColorBlock(Grid grid, Location loc, Color colorValue)
    {
        super(grid, loc);
        theColor = colorValue;
    }

    /** Gets color value for color block.
     **/
    public Color color()
    {
        return theColor;
    }

    /** Returns a string representation of the color. **/
    public String toString()
    {
        return theColor.toString() + " " + location().toString();
    }

}
