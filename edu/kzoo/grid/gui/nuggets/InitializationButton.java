// Class: InitializationButton
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

package edu.kzoo.grid.gui.nuggets;

import edu.kzoo.grid.gui.GridAppFrame;
import edu.kzoo.grid.gui.ThreadedControlButton;

/**
 *  Grid GUI Nuggets Package (Handy Grid GUI Components):<br>
 *
 *    The <code>InitializationButton</code> class represents a button
 *    that initializes a grid, an application, or some other object.
 *    The actual initialization behavior is provided by the initializer
 *    object provided to the button constructor.
 *
 *  @author Alyce Brady
 *  @version 1 September 2004
 **/
public class InitializationButton extends ThreadedControlButton
{
    // Instance Variables: Encapsulated data for each object
    private Initializer initializer = null;

    /** Constructs a button labeled "Initialize" that will invoke the
     *  <code>initialize</code> method on the specified initializer.
     *  The grid will not be redisplayed after the button is pressed
     *  unless the initializer object redisplays the grid explicitly.
     *    @param initializer  object that knows how to initialize
     **/
    public InitializationButton(Initializer initializer)
    {
        this(initializer, "Initialize", null, false);
    }

    /** Constructs a button that will invoke the <code>initialize</code> method
     *  on the specified initializer.
     *  The grid will not be redisplayed after the button is pressed
     *  unless the initializer object redisplays the grid explicitly.
     *    @param initializer  object that knows how to initialize
     *    @param label  label to place on button
     **/
    public InitializationButton(Initializer initializer, String label)
    {
        this(initializer, label, null, false);
    }

    /** Constructs a button that will invoke the <code>initialize</code> method
     *  on the specified initializer.
     *    @param initializer  object that knows how to initialize
     *    @param label  label to place on button
     *    @param gui    graphical user interface containing this button
     *    @param displayAtEnd true if gui should display grid when
     *                        button behavior is complete; false otherwise
     **/
    public InitializationButton(Initializer initializer, String label,
                                GridAppFrame gui, boolean displayAtEnd)
    {
        super(gui, label, displayAtEnd);
        this.initializer = initializer;
    }

    /** Invokes the initialize method on the initializer object provided to
     *  this object's constructor.
     **/
    public void act()
    {
        initializer.initialize();
    }

    /** The <code>Initializer</code> interface specifies an
     *  <code>initialize</code> method.
     **/
    public interface Initializer
    {
        public void initialize();
    }

}
