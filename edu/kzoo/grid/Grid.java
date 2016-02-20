// Class: Grid
//
// Author: Alyce Brady
//
// This class is based on the College Board's Environment interface
// and SquareEnvironment class, as allowed by the GNU General Public
// License.  Environment and SquareEnvironment are components of
// the AP(r) CS Marine Biology Simulation case study
// (see www.collegeboard.com/ap/students/compsci).
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

import edu.kzoo.util.RandNumGenerator;

import java.util.ArrayList;

/**
 *  Grid Container Package:<br>
 *
 *  A <code>Grid</code> is a two-dimensional, rectangular container
 *  data structure.  It can contain any kind of object that can be
 *  modeled using an extension of the <code>GridObject</code> class.
 *  For example, a grid could be used to model a board for a
 *  tic-tac-toe or chess game, an environment of fish for a marine
 *  biology simulation, etc.
 *
 *  <p>
 *  The <code>Grid</code> class is based on the College Board's
 *  <code>Environment</code> interface and <code>SquareEnvironment</code>
 *  class, as allowed by the GNU General Public License.
 *
 *  @author Alyce Brady
 *  @version 13 December 2003
 *  @see Direction
 *  @see Location
 *  @see GridObject
 **/

public abstract class Grid
{
  // constants

    /** A constant representing an unbounded (or infinite) number of
     *  rows or columns in a grid.
     **/
    public final static int UNBOUNDED = -1;

  // instance variables: encapsulated data for EACH Grid object

    /** Instance variable indicating whether the set of neighbors around
     *  each cell in the grid should include the 4 cells on the diagonals
     *  as well as the 4 cells with shared sides.
     **/
    protected final boolean includeDiagonals;

    /** Instance variable containing the internal representation of the
     *  grid, which could be implemented in a number of ways.
     **/
    protected final InternalRepresentation internalRep;


  // constructors

    /** Constructs a <code>Grid</code> object with the specified internal
     *  representation.  Each cell in this grid will have at most four
     *  adjacent neighbors, the cells to its north, south, east, and west.
     *  If the grid is bounded, cells along the boundaries will have fewer
     *  than four neighbors.
     *  @param rep  the internal representation for the grid and the
     *              objects it contains
     **/
    protected Grid(InternalRepresentation rep)
    {
        this(rep, false);
    }
        
    /** Constructs a <code>Grid</code> object with the specified internal
     *  representation.  Each cell in this grid will have at most four or
     *  eight adjacent neighbors, depending on the value of the
     *  <code>includeDiagonalNeighbors</code> parameter.  If
     *  <code>includeDiagonalNeighbors</code> is <code>true</code>, each
     *  cell will have at most eight adjacent neighbors, the cells to
     *  its north, south, east, and west and the cells on the diagonals,
     *  to the northeast, southeast, northwest, and southwest.  If
     *  <code>includeDiagonalNeighbors</code> is <code>false</code>,
     *  each cell will have at most the four neighbors to its north,
     *  south, east, and west.  If the grid is bounded, cells along
     *  the boundaries will have fewer than the maximum four or eight
     *  neighbors.
     *  @param rep  the internal representation for the grid and the
     *              objects it contains
     *  @param includedDiagonalNeighbors    whether to include the four
     *                                      diagonal locations as neighbors
     **/
    protected Grid(InternalRepresentation rep,
                   boolean includeDiagonalNeighbors)
    {
        internalRep = rep;
        includeDiagonals = includeDiagonalNeighbors;
    }


  // accessor methods dealing with grid dimensions

    /** Returns number of rows in this grid.
     *  @return   the number of rows, or <code>UNBOUNDED</code> if the grid
     *            is unbounded
     **/
    public abstract int numRows();

    /** Returns number of columns in this grid.
     *  @return   the number of columns, or <code>UNBOUNDED</code> if the grid
     *            is unbounded
     **/
    public abstract int numCols();


  // accessor methods for navigating around this grid

    /** Verifies whether a location is valid in this grid.
     *  @param  loc    location to check
     *  @return <code>true</code> if <code>loc</code> is valid;
     *          <code>false</code> otherwise
     **/
    public boolean isValid(Location loc)
    {
        return internalRep.isValid(loc);
    }

    /** Returns the number of adjacent neighbors around each cell.
     *  @return    the number of adjacent neighbors 
     **/
    public int numAdjacentNeighbors()
    {
        return (includeDiagonals ? 8 : 4);
    }

    /** Generates a random direction.  The direction returned by
     *  <code>randomDirection</code> reflects the direction from
     *  a cell in the grid to one of its adjacent neighbors.
     *  @return a direction
     **/
    public Direction randomDirection()
    {
        RandNumGenerator randNumGen = RandNumGenerator.getInstance();
        int randNum = randNumGen.nextInt(numAdjacentNeighbors());
        return new Direction(randNum * Direction.FULL_CIRCLE/numAdjacentNeighbors());
    }

    /** Returns the direction from one location to another.  If 
     *  <code>fromLoc</code> and <code>toLoc</code> are the same, 
     *  <code>getDirection</code> arbitrarily returns <code>Direction.NORTH</code>.  
     *  @param  fromLoc       starting location for search
     *  @param  toLoc         destination location
     *  @return direction from <code>fromLoc</code> to <code>toLoc</code>
     **/
    public Direction getDirection(Location fromLoc, Location toLoc)
    {
        if (fromLoc.equals(toLoc))
            return Direction.NORTH;
        int rowDifference = fromLoc.row() - toLoc.row(); // our coord system is upside down
        int colDifference = toLoc.col() - fromLoc.col();
        double inRads = Math.atan2(rowDifference, colDifference);
        double angle = 90 - Math.toDegrees(inRads); // convert to our sweep, North is 0
        Direction d = new Direction((int)angle);
        return d.roundedDir(numAdjacentNeighbors(), Direction.NORTH);
    }

    /** Returns the adjacent neighbor (whether valid or invalid) of a location
     *  in the specified direction.
     *  @param  fromLoc       starting location for search
     *  @param  compassDir    direction in which to look for adjacent neighbor
     *  @return neighbor of <code>fromLoc</code> in given direction
     *                        (whether valid or not)
     **/
    public Location getNeighbor(Location fromLoc, Direction compassDir)
    {
        Direction roundedDir = compassDir.roundedDir(numAdjacentNeighbors(),
                                                     Direction.NORTH);

        // Calculate neighboring location using sines and cosines.
        // First have to adjust because our 0 is North, not East.
        // The row change is the opposite of what is expected because
        // our row numbers increase as they go down, not up.
        int adjustedDegrees = 90 - roundedDir.inDegrees();
        double inRads = Math.toRadians(adjustedDegrees);
        int colDelta = (int)(Math.cos(inRads) * Math.sqrt(2));
        int rowDelta = -(int)(Math.sin(inRads) * Math.sqrt(2));
        return new Location(fromLoc.row() + rowDelta, fromLoc.col() + colDelta);
    }

    /** Returns the adjacent neighbors of a specified location.
     *  Only neighbors that are valid locations in the grid will be
     *  included.
     *  @param  ofLoc       location whose neighbors to get
     *  @return a list of locations that are neighbors of <code>ofLoc</code>
     **/
    public ArrayList neighborsOf(Location ofLoc)
    {
        ArrayList nbrs = new ArrayList();

        Direction d = Direction.NORTH;
        for (int i = 0; i < numAdjacentNeighbors(); i++)
        {
            Location neighbor = getNeighbor(ofLoc, d);
            if ( isValid(neighbor) )
                nbrs.add(neighbor);
            d = d.toRight(Direction.FULL_CIRCLE/numAdjacentNeighbors());
        } 
        return nbrs;
    }


  // accessor methods that deal with objects in this grid

    /** Returns the number of objects in this grid.
     *  @return   the number of objects
     **/
    public synchronized int numObjects()
    {
        return internalRep.numObjects();
    }

    /** Returns all the objects in this grid.
     *  @return    an array of all the grid objects
     **/
    public synchronized GridObject[] allObjects()
    {
        return internalRep.allObjects();
    }

    /** Determines whether a specific location in this grid is empty.
     *  @param loc  the location to test
     *  @return     <code>true</code> if <code>loc</code> is a
     *              valid location in the context of this grid
     *              and is empty; <code>false</code> otherwise
     **/
    public synchronized boolean isEmpty(Location loc)
    {
        return isValid(loc) && objectAt(loc) == null;
    }

    /** Returns the object at a specific location in this grid.
     *  @param loc    the location in which to look
     *  @return       the object at location <code>loc</code>;
     *                <code>null</code> if <code>loc</code> is not
     *                in the grid or is empty
     **/
    public synchronized GridObject objectAt(Location loc)
    {
        return internalRep.objectAt(loc);
    }

    /** Creates a single string representing all the objects in this
     *  environment (not necessarily in any particular order).
     *  @return    a string indicating all the objects in this environment
     **/
    public synchronized String toString()
    {
        GridObject[] theObjects = allObjects();
        String s = "Grid contains " + numObjects() + " objects: ";
        for ( int index = 0; index < theObjects.length; index++ )
            s += theObjects[index].toString() + " ";
        return s;
    }


  // modifier methods

    /** Adds a new object to this grid at the specified location.
     *  (Precondition: <code>obj.grid()</code> and
     *   <code>obj.location()</code> are both <code>null</code>;
     *   <code>loc</code> is a valid empty location in this grid.)
     *  @param obj the new object to be added
     *  @throws    IllegalArgumentException if the precondition is not met
     **/
    public void add(GridObject obj, Location loc)
    {
        obj.addToGrid(this, loc);
    }

    /** Adds the specified object to this grid at the location it specifies.
     *  This method is meant to be called only by
     *  <code>GridObject.addToGrid</code> because it assumes a
     *  broken <code>GridObject</code> invariant: the grid and location
     *  are set in the <code>GridObject</code> instance, but the object's
     *  location in the internal representation of the grid does not yet
     *  represent the object's own view of its location.
     *  (Precondition: <code>obj.grid()</code> is this grid and
     *   <code>obj.location()</code> is a valid empty location.)
     *  @param obj the new object to be added
     *  @throws    IllegalArgumentException if the precondition is not met
     **/
    final synchronized void internalAdd(GridObject obj)
    {
        // Verify precondition.
        Location loc = obj.location();
        if ( obj.grid() != this || ! isEmpty(loc) )
            throw new IllegalArgumentException("Location " + loc +
                                    " is not a valid empty location");

        // Add object to the grid.
        internalRep.add(obj);
    }

    /** Removes the specified object from this grid.
     *  (Precondition: <code>obj</code> is in this grid.)
     *  @param obj     the object to be removed
     *  @throws    IllegalArgumentException if the precondition is not met
     **/
    public synchronized void remove(GridObject obj)
    {
        // Make sure that the object is not in another grid.
        if ( obj.grid() != this && obj.grid() != null )
            throw new IllegalArgumentException("Cannot remove " + 
                                               obj + " from another grid");

        // The object should initiate its own removal from the grid so
        // that its object invariant is not violated.
        obj.removeFromGrid();
    }

    /** Removes whatever object is at the specified location in this grid.
     *  If there is no object at the specified location, this method does
     *  nothing.
     *  @param loc     the location from which to remove an object
     **/
    public void remove(Location loc)
    {
        // The object should initiate its own removal from the grid so
        // that its object invariant is not violated.
        GridObject obj = objectAt(loc);
        if ( obj != null )
            obj.removeFromGrid();
    }

    /** Removes the specified object from this grid. This method is meant to
     *  be called only by <code>GridObject.removeFromGrid</code>; all object
     *  removals should proceed through that method.
     *  (Precondition: <code>obj</code> is in the process of being removed
     *  via <code>GridObject.removeFromGrid</code> and, as a result, its
     *  invariant does not hold.  Specifically,
     *  <code>obj.grid() == null</code> but <code>obj.location()</code>
     *  correctly specifies the object's location in the grid.)
     *  @param obj     the object to be removed
     *  @throws    IllegalArgumentException if the precondition is not met
     **/
    final synchronized void internalRemove(GridObject obj)
    {
        // Make sure that the object has initiated its own removal from
        // the grid.
        if ( obj.grid() != null || objectAt(obj.location()) != obj )
            throw new IllegalArgumentException("Object " + obj + 
                                    " is not in process of removing itself");

        // The object is in the process of removing itself from the grid,
        // so we can remove it.
        internalRep.remove(obj);
    }

    /** Removes all objects from this grid.
     **/
    public synchronized void removeAll()
    {
        // Loop through the objects in the grid and remove them.
        GridObject[] objectsToRemove = allObjects();
        for ( int i = 0; i < objectsToRemove.length; i++ )
        {
            remove(objectsToRemove[i]);
        }
    }


    /** The <code>InternalRepresentation</code> interface specifies
     *  the methods that any internal representation of the
     *  <code>Grid</code> class must implement.
     */
    protected interface InternalRepresentation
    {
      // accessor methods

        /** Verifies whether a location is valid in this grid.
         *  @param  loc    location to check
         *  @return <code>true</code> if <code>loc</code> is valid;
         *          <code>false</code> otherwise
         **/
        boolean isValid(Location loc);

        /** Returns the number of objects in this grid.
         *  @return   the number of objects
         **/
        int numObjects();

        /** Returns all the objects in this grid.
         *  @return    an array of all the grid objects
         **/
        GridObject[] allObjects();

        /** Returns the object at a specific location in this grid.
         *  @param loc    the location in which to look
         *  @return       the object at location <code>loc</code>;
         *                <code>null</code> if <code>loc</code> is not
         *                in the grid or is empty
         **/
        GridObject objectAt(Location loc);


      // modifier methods

        /** Adds a new object to this environment at the location it specifies.
         *  (Precondition: <code>obj.grid()</code> is this grid and
         *   <code>obj.location()</code> is a valid empty location;
         *   verified by the <code>Grid</code> object.)
         *  @param obj the new object to be added
         **/
        void add(GridObject obj);

        /** Removes the object from this environment.
         *  (Precondition: <code>obj</code> is in this environment; verified
         *   by the <code>Grid</code> object.)
         *  @param obj     the object to be removed
         **/
        void remove(GridObject obj);
    }


    /** A <code>ValidityChecker</code> specifies a strategy for determining
     *  the validity of a location in a grid.  Known implementing classes
     *  include <code>BoundedGridValidityChecker</code> and 
     *  <code>UnboundedGridValidityChecker</code>.
     **/
    public interface ValidityChecker
    {
        /** Verifies whether a location is valid.
         *  @param  loc    location to check
         *  @return <code>true</code> if <code>loc</code> is valid;
         *          <code>false</code> otherwise
         **/
        public boolean isValid(Location loc);
    }


    /** A <code>BoundedGridValidityChecker</code> implements a strategy for
     *  determining the validity of a location in a bounded grid.
     **/
    public static class BoundedGridValidityChecker implements ValidityChecker
    {
        private int numRows;
        private int numCols;

        /** Constructs a <code>BoundedGridValidityChecker</code> object.
         *  (Precondition: <code>rows > 0</code> and <code>cols > 0</code>.)
         *  @param rows        number of rows in the grid
         *  @param cols        number of columns in the grid
         **/
        public BoundedGridValidityChecker(int rows, int cols)
        {
            numRows = rows;
            numCols = cols;
        }

        /** Verifies whether a location is valid.
         *  @param  loc    location to check
         *  @return <code>true</code> if <code>loc</code> is valid;
         *          <code>false</code> otherwise
         **/
        public boolean isValid(Location loc)
        {
            if ( loc == null )
                return false;

            return (0 <= loc.row() && loc.row() < numRows) &&
                   (0 <= loc.col() && loc.col() < numCols);
        }
    }


    /** An <code>UnboundedGridValidityChecker</code> implements a strategy
     *  for determining the validity of a location in an unbounded grid.
     **/
    public static class UnboundedGridValidityChecker implements ValidityChecker
    {
        /** Verifies whether a location is valid.
         *  @param  loc    location to check
         *  @return <code>true</code> if <code>loc</code> is valid;
         *          <code>false</code> otherwise
         **/
        public boolean isValid(Location loc)
        {
            // All non-null locations are valid in an unbounded grid.
            return loc != null;
        }
    }

}
