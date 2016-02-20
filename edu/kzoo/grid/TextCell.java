// Class: TextCell
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
 *  A <code>TextCell</code> object encapsulates text to go in a
 *  cell in a grid.
 *
 *  @author Alyce Brady
 *  @version 13 December 2003
 *  @see Grid
 *  @see Location
 **/
public class TextCell extends GridObject
{
    // Encapsulated data for each text cell object
    private String   theText;       // the text to go in a cell in the grid
    private Color    theColor;      // the color of this color block

    /** Constructs a text cell with the specified text and a default color
     *  of black.
     *  @param text        the text to go in this cell
     *  @param grid        the grid containing this text cell
     *  @param loc         the location of the text cell in <code>grid</code>
     **/
    public TextCell(String text, Grid grid, Location loc)
    {
        this(text, Color.black, grid, loc);
    }

    /** Constructs a text cell with the specified text of the specified color.
     *  @param text        the text to go in this cell
     *  @param textColor   the color of the text
     *  @param grid        the grid containing this text cell
     *  @param loc         the location of the text cell in <code>grid</code>
     **/
    public TextCell(String text, Color textColor, Grid grid, Location loc)
    {
        super(grid, loc);
        theText = text;
        theColor = textColor;
    }

    /** Gets text in this text cell.
     **/
    public String text()
    {
        return theText;
    }

    /** Gets color of text.
     **/
    public Color color()
    {
        return theColor;
    }

}
