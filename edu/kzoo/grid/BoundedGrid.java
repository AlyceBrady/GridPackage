// Class: BoundedGrid
//
// Author: Alyce Brady
//
// This class is based on the College Board's BoundedEnv class,
// as allowed by the GNU General Public License.  BoundedEnv is a
// component of the AP(r) CS Marine Biology Simulation
// case study (see
// http://www.collegeboard.com/student/testing/ap/compsci_a/case.html).
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

/**
 *  Grid Container Package:<br>
 *
 *  A <code>BoundedGrid</code> is a rectangular, bounded two-dimensional
 *  container data structure.  It can contain any kind of object that
 *  can be modeled using an extension of the <code>GridObject</code> class.
 *  For example, a grid could be used to model a board for a
 *  tic-tac-toe or chess game, an environment of fish for a marine
 *  biology simulation, etc.
 *
 *  <p>
 *  A <code>BoundedGrid</code> is implemented as a two-dimensional array
 *  corresponding to the dimensions of the grid.  This gives it the
 *  following time and space characteristics:
 *  <table align="center">
 *  <tr><td>numObjects</td><td width="5"><td><i>O</i>(<code>1</code>)</td></tr>
 *  <tr><td>allObjects</td><td></td><td><i>O</i>(<code>r * c</code>)</td></tr>
 *  <tr><td>isEmpty, objectAt</td><td></td><td><i>O</i>(<code>1</code>)</td></tr>
 *  <tr><td>add</td><td></td><td><i>O</i>(<code>1</code>)</td></tr>
 *  <tr><td>remove</td><td></td><td><i>O</i>(<code>1</code>)</td></tr>
 *  <tr><td>space</td><td></td><td><i>O</i>(<code>r * c</code>)</td></tr>
 *  </table>
 *  where <code>r</code> is the number of rows and <code>rc</code> is the
 *  number of columns in the grid.
 *
 *  <p>
 *  The <code>BoundedGrid</code> class and its internal 2D array
 *  implementation are based on the College Board's
 *  <code>BoundedEnv</code> class, as allowed by the GNU General
 *  Public License.
 *
 *  @author Alyce Brady
 *  @version 13 December 2003
 **/

public class BoundedGrid extends Grid
{
  // instance variables: encapsulated data for each BoundedGrid object
    private int numRows;
    private int numCols;

  // constructors

    /** Constructs an empty BoundedGrid object with the given dimensions.
     *  A cell's neighbors include only the cells to its north, south,
     *  east, and west, not the cells on the diagonals.
     *  (Precondition: <code>rows > 0</code> and <code>cols > 0</code>.)
     *  @param rows        number of rows in BoundedGrid
     *  @param cols        number of columns in BoundedGrid
     *  @throws    IllegalArgumentException if the precondition is not met
     **/
    public BoundedGrid(int rows, int cols)
    {
        // Construct and initialize inherited attributes.
        this(false, rows, cols);
    }

    /** Constructs an empty BoundedGrid object with the given dimensions.
     *  Each cell in this grid will have at most four or eight
     *  adjacent neighbors, depending on the value of the
     *  <code>includeDiagonalNeighbors</code> parameter.  Cells along
     *  the grid boundaries will have fewer than the maximum four or
     *  eight neighbors.  If <code>includeDiagonalNeighbors</code> is
     *  <code>true</code>, a cell's adjacent neighbors include the cells
     *  to its north, south, east, and west and the cells on the diagonals,
     *  to the northeast, southeast, northwest, and southwest.  If
     *  <code>includeDiagonalNeighbors</code> is <code>false</code>,
     *  a cell's adjacent neighbors include only the four cells to its
     *  north, south, east, and west.
     *  (Precondition: <code>rows > 0</code> and <code>cols > 0</code>.)
     *  @param includeDiagonalNeighbors  whether to include the four
     *                                   diagonal locations as neighbors
     *  @param rows        number of rows in BoundedGrid
     *  @param cols        number of columns in BoundedGrid
     *  @throws    IllegalArgumentException if the precondition is not met
     **/
    public BoundedGrid(boolean includeDiagonalNeighbors, int rows, int cols)
    {
        // Construct and initialize inherited attributes.
        super(new Array2DGridRep(rows, cols), includeDiagonalNeighbors);
        this.numRows = rows;
        this.numCols = cols;
    }

  // accessor methods dealing with grid dimensions

    /** Returns number of rows in this grid.
     *  @return   the number of rows in this grid
     **/
    public int numRows()
    {
        return numRows;
    }

    /** Returns number of columns in this grid.
     *  @return   the number of columns in this grid
     **/
    public int numCols()
    {
        return numCols;
    }

  // internal 2D Grid representation

    /** The <code>Array2DGridRep</code> class represents an internal bounded
     *  grid using a two-dimensional array. 
     */
    protected static class Array2DGridRep implements Grid.InternalRepresentation
    {
      // instance variables: encapsulated data for each Array2DGridRep object
        private GridObject[][] theGrid; // internal representation of the grid
        private int objectCount;        // # of objects in current grid

      // constructors

        /** Constructs an empty Array2DGridRep representation with the given
         *  dimensions.
         *  (Precondition: <code>rows > 0</code> and <code>cols > 0</code>.)
         *  @param rows        number of rows in the grid
         *  @param cols        number of columns in the grid
         **/
        public Array2DGridRep(int rows, int cols)
        {
            // Verify precondition.
            if ( rows <= 0 || cols <= 0 )
                throw new IllegalArgumentException();

            theGrid = new GridObject[rows][cols];
            objectCount = 0;
        }


      // accessor methods dealing with grid dimensions

        /** Returns number of rows in this grid.
         *  @return   the number of rows, or UNBOUNDED if the grid is unbounded
         **/
        public int numRows()
        {
            return theGrid.length;
        }

        /** Returns number of columns in this grid.
         *  @return   the number of columns, or UNBOUNDED if the grid is unbounded
         **/
        public int numCols()
        {
            // Note: according to the constructor precondition, numRows() > 0,
            // so theGrid[0] is non-null.
            return theGrid[0].length;
        }

        /** Verifies whether a location is valid in this grid.
         *  @param  loc    location to check
         *  @return <code>true</code> if <code>loc</code> is valid;
         *          <code>false</code> otherwise
         **/
        public boolean isValid(Location loc)
        {
            if ( loc == null )
                return false;

            return (0 <= loc.row() && loc.row() < numRows()) &&
                   (0 <= loc.col() && loc.col() < numCols());
        }


      // accessor methods that deal with objects in this grid

        /** Returns the number of objects in this grid.
         *  @return   the number of objects
         **/
        public int numObjects()
        {
            return objectCount;
        }

        /** Returns all the objects in this grid.
         *  @return    an array of all the grid objects
         **/
        public GridObject[] allObjects()
        {
            // Create an array in which to put the objects.
            GridObject[] theObjects = new GridObject[numObjects()];
            int arrayIndex = 0;

            // Look at all grid locations.
            for ( int r = 0; r < numRows(); r++ )
            {
                for ( int c = 0; c < numCols(); c++ )
                {
                    // If there's an object at this location, put it
                    // in the list.
                    GridObject obj = theGrid[r][c];
                    if ( obj != null )
                    {
                        theObjects[arrayIndex] = obj;
                        arrayIndex++;
                    }
                }
            }

           return theObjects;
        }

        /** Returns the object at a specific location in this grid.
         *  @param loc    the location in which to look
         *  @return       the object at location <code>loc</code>;
         *                <code>null</code> if <code>loc</code> is not
         *                in the grid or is empty
         **/
        public GridObject objectAt(Location loc)
        {
            if ( ! isValid(loc) )
                return null;

            return theGrid[loc.row()][loc.col()];
        }


      // modifier methods

        /** Adds a new object to this internal grid representation at the 
         *  location it specifies.
         *  (Precondition: <code>obj.grid()</code> is this grid and
         *   <code>obj.location()</code> is a valid empty location;
         *   verified by the <code>Grid</code> object.)
         *  @param obj the new object to be added
         **/
        public void add(GridObject obj)
        {
            // Add object to the internal grid representation.
            Location loc = obj.location();
            theGrid[loc.row()][loc.col()] = obj;
            objectCount++;
        }

        /** Removes the object from this internal grid representation.
         *  (Precondition: <code>obj</code> is in this grid; verified
         *   by the <code>Grid</code> object.)
         *  @param obj     the object to be removed
         **/
        public void remove(GridObject obj)
        {
            // Remove the object from the grid.
            Location loc = obj.location();
            theGrid[loc.row()][loc.col()] = null;
            objectCount--;
        }

    }

}
