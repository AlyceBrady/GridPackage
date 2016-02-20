// Class: GridObject
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

/**
 *  Grid Container Package:<br>
 * 
 *  A <code>GridObject</code> object is an object that can be contained
 *  in a grid.  If it is in a grid, it keeps track of its location
 *  there.  
 *  it moves, notifies the grid.  This ensures that the object and the
 *  grid in which it is located always agree about where the object is.
 *
 *  Object invariant:
 *  <pre>
 *      this.grid() == null && this.location() == null ||
 *      this.grid().objectAt(this.location()) == this
 *  </pre>
 *
 *  @author Alyce Brady
 *  @version 13 December 2003
 *  @see Grid
 *  @see Location
 **/

public class GridObject
{
  // instance variables: encapsulated data for each GridObject instance
    private Grid        theGrid;   // the grid holding this grid object
    private Location    myLoc;     // this grid object's location

  // constructors

    /** Constructs an instance of a GridObject that is not yet in a grid.
     **/
    public GridObject()
    {
        this(null, null);
        // assert(theGridObjectInvariantHolds());
    }

    /** Constructs an instance of a GridObject and places it in the specified
     *  grid.
     *  (Precondition: either both <code>loc</code> and <code>grid</code> are
     *   <code>null</code> or neither is <code>null</code>; if <code>loc</code>
     *   is not <code>null</code>, it is a valid empty location in
     *   <code>grid</code>.)
     *  @param grid   the grid in which this object should be placed
     *  @param loc    the location of this grid object
     *  @throws       IllegalArgumentException if the precondition is not met
     **/
    public GridObject(Grid grid, Location loc)
    {
        theGrid = null;
        myLoc = null;

        if ( grid != null && loc != null )
        {
            addToGrid(grid, loc);
        }
        else if ( grid != null || loc != null )
            throw new IllegalArgumentException(
              "Both grid and loc should be provided or both should be null.");
 
        // assert(theGridObjectInvariantHolds());
    }


  // accessor methods

    /** Checks whether this object is in a grid.
     *  @return  <code>true</code> if the object is in a grid;
     *           <code>false</code> otherwise
     **/
    public final synchronized boolean isInAGrid()
    {
        // To be true, this object must be in the grid and at
        // the correct location.
        return ( this.grid() != null &&
                 this.grid().objectAt(this.location()) == this ) ;
    }

    /** Returns the grid in which this grid object exists.
     *  @return        the grid containing this 
     *                 <code>GridObject</code>
     **/
    public Grid grid()
    {
        return theGrid;
    }

    /** Gets this grid object's location.
     *  @return        the grid object's location
     **/
    public Location location()
    {
        return myLoc;
    }

    /** Verifies that the object invariant holds.
     *  @return  <code>true</code> if the object invariant holds;
     *           <code>false</code> if it has been violated
     **/
    protected final synchronized boolean theGridObjectInvariantHolds()
    {
        return ( (this.grid() == null && this.location() == null) ||
                 isInAGrid() ) ;
    }

    /** Returns a string representation of this grid object.
     *    @return a string representation of this grid object
     **/
    public String toString()
    {
        return location().toString();
    }

  // modifier methods: these are protected because not all subclasses
  //   will wish to allow client code to modify an object's location.
  //   Those subclasses that do should provide a public method to do so
  //   (e.g., a move method), which can, in turn, call the methods below.

    /** Adds this object to the specified grid at the specified location.
     *  (Precondition: this object is not currently in a grid;
     *                 neither <code>grid</code> nor <code>loc</code>
     *                 is <code>null</code>; <code>loc</code> is a valid
     *                 empty location in <code>grid</code>.)
     *  @param grid   the grid in which this object should be placed
     *  @param loc    the location of this grid object
     *  @throws       IllegalArgumentException if the precondition is not met
     **/
    protected synchronized void addToGrid(Grid grid, Location loc)
    {
        // Verify parts of precondition not verified by Grid.internalAdd.
        if ( this.grid() != null || grid == null || loc == null )
            throw new IllegalArgumentException();

        // Set relevant instance variables and add to grid.
        theGrid = grid;
        myLoc = loc;
        theGrid.internalAdd(this);
 
        // assert(theGridObjectInvariantHolds());
    }

    /** Modifies this grid object's location and notifies the grid.
     *  (Precondition: this object is in a grid and <code>newLoc</code>
     *  is a valid, empty location in the grid.)
     *  @param  newLoc    new location value
     *  @throws       IllegalArgumentException if the precondition is not met
     **/
    protected synchronized void changeLocation(Location newLoc)
    {
        // Verify precondition.
        if ( ! isInAGrid() || ! theGrid.isEmpty(newLoc) )
            throw new IllegalArgumentException();

        if ( ! newLoc.equals(myLoc) )
        {
            Grid theGridIStillWantToBeIn = theGrid;
            removeFromGrid();
            addToGrid(theGridIStillWantToBeIn, newLoc);
        }

        // assert(theGridObjectInvariantHolds());
    }

    /** Removes this object from the grid in which it was located.
     **/
    protected synchronized void removeFromGrid()
    {
        // No action is necessary if this object isn't in a grid.
        if ( ! isInAGrid() )
            return;

        // The grid and grid object should both notify the other
        // when an object is removed.  We set theGrid to null BEFORE
        // notifying the grid to break the circularity.
        Grid tempGrid = theGrid;
        theGrid = null;
        tempGrid.internalRemove(this);
        myLoc = null;

        // assert(theGridObjectInvariantHolds());
    }

}
