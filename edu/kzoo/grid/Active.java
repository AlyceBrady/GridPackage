// Interface: Active
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
 *  The <code>Active</code> interface defines a type for objects that
 *  respond to the <code>act</code> method.
 *
 *  @author Alyce Brady
 *  @version 13 December 2003
 **/
public interface Active
{
    /** Acts for one step in an animation or simulation.
     **/
    public void act();

}
