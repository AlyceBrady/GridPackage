// Class EnabledDisabledStates
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

/**
 *  Grid GUI Support Package:<br>
 *
 *  The <code>EnabledDisabledStates</code> class provides a set of constants
 *  describing possible criteria for graphical user interface components to
 *  be enabled or disabled.
 *  This class should be replaced with an enumerated
 *  type under Java 1.5.
 *
 *  @author Alyce Brady
 *  @version 29 July 2004
 **/
public class EnabledDisabledStates
{
    /** Indicates that a component should always be enabled. **/
    public static final int ALWAYS_ENABLED = 0;

    /** Indicates that a component should be enabled if the grid
     *  associated with the component's user interface is not null.
     **/
    public static final int NEEDS_GRID = 1;

    /** Indicates that a component should be enabled whenever the application
     *  is waiting for user input (regardless of whether the user interface's
     *  grid has been set) and disabled when the application is actively
     *  executing (responding to another button, for example).
     **/
    public static final int NEEDS_APP_WAITING = 2;

    /** Indicates that a component should be enabled when the application is
     *  actively executing (responding to another button, for example) and
     *  disabled whenever the application is waiting for user input, as for
     *  a stop button.  The component's enabled/disabled status does not
     *  depend on whether the user interface's grid has been set.
     **/
    public static final int NEEDS_APP_RUNNING = 3;

    /** Indicates that a component should be enabled whenever the grid has
     *  been set and the application is waiting for user input.  If the grid
     *  has not been set, or if the application is actively executing
     *  (responding to another button, for example), the component should
     *  be disabled.
     **/
    public static final int NEEDS_GRID_AND_APP_WAITING = 4;

    /** Indicates that a component should be enabled when the grid has
     *  been set and the application is actively executing (responding
     *  to another button, for example).  If the grid has not been set,
     *  or if the application is waiting for user input, the component
     *  should be disabled.
     **/
    public static final int NEEDS_GRID_AND_APP_RUNNING = 5;

    /** Indicates that a component should always be disabled. **/
    public static final int ALWAYS_DISABLED = 6;

}


