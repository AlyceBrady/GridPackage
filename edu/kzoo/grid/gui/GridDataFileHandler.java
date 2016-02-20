// Class GridDataFileHandler
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

package edu.kzoo.grid.gui;

import edu.kzoo.grid.Grid;

import java.io.File;

/**
 *  Grid GUI Support Package:<br>
 *
 *  The <code>GridDataFileHandler</code> interface specifies methods for
 *  reading grid information from a file and writing it to a file.
 *
 *  @author Alyce Brady
 *  @version 15 December 2003
 **/
public interface GridDataFileHandler
{
    /** Reads information about a grid from an initial configuration
     *  data file and creates the grid.
     *      @param  file       java.io.File object from which to read
     *      @return the newly created grid
     *      @throws java.io.FileNotFoundException if file cannot be opened
     *      @throws RuntimeException  if invalid information is read from file
     **/
    Grid readGrid(File file)
        throws java.io.FileNotFoundException;

    /** Writes information about a grid into a data file.
     *      @param  grid    grid to write to file
     *      @param  file   java.io.File object to which to write
     *  @throws java.io.IOException if error writing to file
     **/
    void writeGrid(Grid grid, File file)
        throws java.io.IOException;

}
