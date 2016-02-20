// Class: ArrayListGrid
//
// Author: Alyce Brady
//
// This class is based on the College Board's UnboundedEnv class,
// as allowed by the GNU General Public License.  UnboundedEnv is a
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

import java.util.ArrayList;

/**
 *  Grid Container Package:<br>
 *
 *  The <code>ArrayListGrid</code> class encapsulates two public inner
 *  classes that extend the <code>Grid</code> class to
 *  model a two-dimensional grid by keeping track of their contents
 *  in <code>ArrayList</code> objects.  The first public inner class,
 *  <code>ArrayListGrid.Bounded</code>, represents a bounded grid
 *  using an <code>ArrayList</code>, while the second public inner class,
 *  <code>ArrayListGrid.Unbounded</code>, represents an unbounded grid
 *  using an <code>ArrayList</code>.
 *
 *  <p>
 *  Methods of both <code>ArrayListGrid</code> classes have the
 *  following time and space characteristics:
 *  <table align="center">
 *  <tr><td>numObjects</td><td width="5"><td><i>O</i>(<code>1</code>)</td></tr>
 *  <tr><td>allObjects</td><td></td><td><i>O</i>(<code>n</code>)</td></tr>
 *  <tr><td>isEmpty, objectAt</td><td></td><td><i>O</i>(<code>n</code>)</td></tr>
 *  <tr><td>add</td><td></td><td><i>O</i>(<code>1</code>) [amortized]</td></tr>
 *  <tr><td>remove</td><td></td><td><i>O</i>(<code>n</code>)</td></tr>
 *  <tr><td>space</td><td></td><td><i>O</i>(<code>n</code>)</td></tr>
 *  </table>
 *  where <code>n</code> is the number of objects in the grid.
 *
 *  <p>
 *  The <code>ArrayListGrid</code> classes are based on the
 *  College Board's <code>UnboundedEnv</code> class, as allowed
 *  by the GNU General Public License.
 *
 *  @author Alyce Brady
 *  @version 20 March 2004
 *  @see Direction
 *  @see Location
 *  @see GridObject
 */
public class ArrayListGrid
{
    // ArrayListGrid is a scoping mechanism, not meant to be instantiated.
    private ArrayListGrid()
    {
    }

    /** An <code>ArrayListGrid.Bounded</code> object is a rectangular,
     *  bounded two-dimensional container data structure implemented as
     *  an <code>ArrayList</code> of the objects it contains.  It can contain
     *  any kind of object that can be modeled using an extension of the
     *  <code>GridObject</code> class.  For example, a bounded grid could
     *  be used to model a board for a tic-tac-toe or chess game, an
     *  environment of fish for a marine biology simulation, etc.
     *
     *  <p>
     *  <code>ArrayListGrid.Bounded</code> methods have the
     *  following time and space characteristics:
     *  <table align="center">
     *  <tr><td>numObjects</td><td width="5"><td><i>O</i>(<code>1</code>)</td></tr>
     *  <tr><td>allObjects</td><td></td><td><i>O</i>(<code>n</code>)</td></tr>
     *  <tr><td>isEmpty, objectAt</td><td></td><td><i>O</i>(<code>n</code>)</td></tr>
     *  <tr><td>add</td><td></td><td><i>O</i>(<code>1</code>) [amortized]</td></tr>
     *  <tr><td>remove</td><td></td><td><i>O</i>(<code>n</code>)</td></tr>
     *  <tr><td>space</td><td></td><td><i>O</i>(<code>n</code>)</td></tr>
     *  </table>
     *  where <code>n</code> is the number of objects in the grid.
     **/
    public static class Bounded extends Grid
    {
      // instance variables: encapsulated data for each Bounded grid object
        private int numRows;
        private int numCols;

      // constructors

        /** Constructs an empty ArrayListGrid.Bounded object with the given
         *  dimensions.
         *  A cell's neighbors include only the cells to its north, south,
         *  east, and west, not the cells on the diagonals.
         *  (Precondition: <code>rows > 0</code> and <code>cols > 0</code>.)
         *  @param rows        number of rows in grid
         *  @param cols        number of columns in grid
         *  @throws    IllegalArgumentException if the precondition is not met
         **/
        public Bounded(int rows, int cols)
        {
            // Construct and initialize inherited attributes.
            this(false, rows, cols);
        }

        /** Constructs an empty ArrayListGrid.Bounded object with the given
         *  dimensions.
         *  Each cell in this grid will have at most four or eight
         *  adjacent neighbors, depending on the value of the
         *  <code>includeDiagonalNeighbors</code> parameter.  Cells along
         *  the grid boundaries will have fewer than the maximum four or
         *  eight neighbors.  If <code>includeDiagonalNeighbors</code> is
         *  <code>true</code>, a cell's adjacent neighbors include the
         *  cells to its north, south, east, and west and the cells on
         *  the diagonals, to the northeast, southeast, northwest, and
         *  southwest.  If <code>includeDiagonalNeighbors</code> is
         *  <code>false</code>, a cell's adjacent neighbors include only
         *  the four cells to its north, south, east, and west.
         *  (Precondition: <code>rows > 0</code> and <code>cols > 0</code>.)
         *  @param includeDiagonalNeighbors  whether to include the four
         *                                   diagonal locations as neighbors
         *  @param rows        number of rows in grid
         *  @param cols        number of columns in grid
         *  @throws    IllegalArgumentException if the precondition is not met
         **/
        public Bounded(boolean includeDiagonalNeighbors,
                       int rows, int cols)
        {
            // Construct and initialize inherited attributes.
            super(new ArrayListGridRep(
                            new Grid.BoundedGridValidityChecker(rows, cols)),
                  includeDiagonalNeighbors);
            numRows = rows;
            numCols = cols;
        }

      // accessor methods dealing with grid dimensions

        /** Returns number of rows in this grid.
         *  @return   the number of rows in this grid
         **/
        public int numRows()
        {
            return this.numRows;
        }

        /** Returns number of columns in this grid.
         *  @return   the number of columns in this grid
         **/
        public int numCols()
        {
            return this.numCols;
        }

    }

    /** An <code>ArrayListGrid.Unounded</code> object is an unbounded
     *  two-dimensional container data structure implemented as
     *  an <code>ArrayList</code> of the objects it contains.  It can contain
     *  any kind of object that can be modeled using an extension of the
     *  <code>GridObject</code> class.  For example, a bounded grid could
     *  be used to model a board for a tic-tac-toe or chess game, an
     *  environment of fish for a marine biology simulation, etc.
     *
     *  <p>
     *  <code>ArrayListGrid.Unbounded</code> methods have the
     *  following time and space characteristics:
     *  <table align="center">
     *  <tr><td>numObjects</td><td width="5"><td><i>O</i>(<code>1</code>)</td></tr>
     *  <tr><td>allObjects</td><td></td><td><i>O</i>(<code>n</code>)</td></tr>
     *  <tr><td>isEmpty, objectAt</td><td></td><td><i>O</i>(<code>n</code>)</td></tr>
     *  <tr><td>add</td><td></td><td><i>O</i>(<code>1</code>) [amortized]</td></tr>
     *  <tr><td>remove</td><td></td><td><i>O</i>(<code>n</code>)</td></tr>
     *  <tr><td>space</td><td></td><td><i>O</i>(<code>n</code>)</td></tr>
     *  </table>
     *  where <code>n</code> is the number of objects in the grid.
     **/
    public static class Unbounded extends Grid
    {
      // constructors

        /** Constructs an empty ArrayListGrid.Unbounded object.
         *  A cell's neighbors include only the cells to its north, south,
         *  east, and west, not the cells on the diagonals.
         *  (Precondition: <code>rows > 0</code> and <code>cols > 0</code>.)
         **/
        public Unbounded()
        {
            // Construct and initialize inherited attributes.
            this(false);
        }

        /** Constructs an empty ArrayListGrid.Unbounded object.
         *  Each cell in this grid will have at most four or eight
         *  adjacent neighbors, depending on the value of the
         *  <code>includeDiagonalNeighbors</code> parameter.  If
         *  <code>includeDiagonalNeighbors</code> is <code>true</code>,
         *  a cell's adjacent neighbors include the cells to its north,
         *  south, east, and west and the cells on the diagonals, to
         *  the northeast, southeast, northwest, and southwest.  If
         *  <code>includeDiagonalNeighbors</code> is <code>false</code>,
         *  a cell's adjacent neighbors include only the four cells to
         *  its north, south, east, and west.
         *  (Precondition: <code>rows > 0</code> and <code>cols > 0</code>.)
         *  @param  includeDiagonalNeighbors   whether to include the four
         *                                     diagonal locations as neighbors
         **/
        public Unbounded(boolean includeDiagonalNeighbors)
        {
            // Construct and initialize inherited attributes.
            super(new ArrayListGridRep(new Grid.UnboundedGridValidityChecker()),
                  includeDiagonalNeighbors);
        }

      // accessor methods dealing with grid dimensions

        /** Returns number of rows in this grid.
         *  @return   the number of rows, or UNBOUNDED if the grid is unbounded
         **/
        public int numRows()
        {
            return Grid.UNBOUNDED;
        }

        /** Returns number of columns in this grid.
         *  @return   the number of columns, or UNBOUNDED if the grid is unbounded
         **/
        public int numCols()
        {
            return Grid.UNBOUNDED;
        }
    }

    /** Internal representation for an <code>ArrayList</code>-based
     *  implementation of a <code>Grid</code> class.
     **/
    public static class ArrayListGridRep
                    implements Grid.InternalRepresentation
    {
      // instance variables: encapsulated data for each ArrayListGridRep object
        private ArrayList<GridObject> objectList;   // list of objects in a grid
        private Grid.ValidityChecker locationValidityChecker;

      // constructors

        /** Constructs an empty ArrayListGridRep representation of a grid.
         *    @param checker an object that knows how to check the validity
         *                   of a location in a grid
         **/
        protected ArrayListGridRep(Grid.ValidityChecker checker)
        {
            objectList = new ArrayList<GridObject>();
            locationValidityChecker = checker;
        }


      // accessor methods

        /** Verifies whether a location is valid in this grid.
         *  @param  loc    location to check
         *  @return <code>true</code> if <code>loc</code> is valid;
         *          <code>false</code> otherwise
         **/
        public boolean isValid(Location loc)
        {
            return locationValidityChecker.isValid(loc);
        }

        /** Returns the number of objects in this grid.
         *  @return   the number of objects
         **/
        public int numObjects()
        {
            return objectList.size();
        }

        /** Returns all the objects in this grid.
         *  @return    an array of all the grid objects
         **/
        public GridObject[] allObjects()
        {
            // Copy them to an array.
            int numObjects = objectList.size();
            GridObject[] theObjects = new GridObject[numObjects];
            for ( int i = 0; i < numObjects; i++ )
                theObjects[i] = (GridObject) objectList.get(i);
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
            int index = indexOf(loc);
            if ( index == -1 )
                return null;

            return (GridObject) objectList.get(index);
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
            objectList.add(obj);
        }

        /** Removes the object from this internal grid representation.
         *  (Precondition: <code>obj</code> is in this grid; verified
         *   by the <code>Grid</code> object.)
         *  @param obj     the object to be removed
         **/
        public void remove(GridObject obj)
        {
            // Find the index of the object and then remove it.
            objectList.remove(indexOf(obj.location()));
        }


      // internal helper method

        /** Get the index of the object at the specified location.
         *  @param loc    the location in which to look
         *  @return       the index of the object at location <code>loc</code>
         *                if there is one; -1 otherwise
         **/
        protected int indexOf(Location loc)
        {
            // Look through the list to find the object at the given location.
            for ( int index = 0; index < objectList.size(); index++ )
            {
                GridObject obj = (GridObject) objectList.get(index);
                if ( obj.location().equals(loc) )
                {
                    // Found the object -- return its index.
                    return index;
                }
            }

            // No such object found.
            return -1;
        }
    }
}