// Class: CheckeredBackgroundDisplay
//
// Author: Joel Booth
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
import java.awt.Graphics2D;
import java.awt.Insets;

import edu.kzoo.grid.Grid;

/**
 *  Grid Display Package:<br>
 *
 *  The <code>GridBackgroundDisplay</code> interface specifies the
 *  method that must be provided by any class used to display
 *  a <code>Grid</code> background.
 *
 *  @author Joel Booth
 *  @version Sep 1, 2004
 **/
public class CheckeredBackgroundDisplay implements GridBackgroundDisplay
{
    private ScrollableGridDisplay overallDisplay;
    private Color upperLeftColor;
    private Color otherColor;

    /** Constructs a background display that draws a checkered background
     *  for a grid, using the specified colors.
     * 	  @param enclosingDisplay  the overall grid display for which this
     *                             object will draw the background
     *    @param upperLeftColor    color to display in upper-left corner
     *                             and every other cell after that
     *    @param otherColor        the alternate color
     **/
    public CheckeredBackgroundDisplay(ScrollableGridDisplay enclosingDisplay,
                                      Color upperLeftColor, Color otherColor)
    {
        this.overallDisplay = enclosingDisplay;
        this.upperLeftColor = upperLeftColor;
        this.otherColor = otherColor;
    }

    /* (non-Javadoc)
     * @see edu.kzoo.grid.display.GridBackgroundDisplay#drawBackground(java.awt.Graphics2D)
     */
    public void drawBackground(Graphics2D g2)
    {
        // Fill the background with one of the two colors.
        overallDisplay.fillBackground(g2, otherColor);

        // Fill in the checkerboard pattern with the other color.
        Insets insets = overallDisplay.getInsets();

        int leftSide; 
        int topSide;

        Grid grid = overallDisplay.grid();
        for (int row = 0; row < grid.numRows(); row++)
        {
            for (int col = 0; col < grid.numCols(); col++)
            {
                // Calculate upper left corner of the cell to draw.
                leftSide = overallDisplay.colToXCoord(col);
                topSide = overallDisplay.rowToYCoord(row);

                // Put the other checkered color in the top-left cell and
                // every other cell whose row and column are both even or
                // both odd.
                if ( (col % 2) == (row % 2 ) )
                {
                    g2.setColor(upperLeftColor);
                    g2.fillRect(leftSide,  topSide,
                                overallDisplay.innerCellSize(),
                                overallDisplay.innerCellSize());
                }
            }
        }
    }

}
