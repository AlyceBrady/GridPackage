// Class: GridObjectDisplay
//
// Author: Alyce Brady
//
// This class is based on the College Board's LocatableDisplay class,
// as allowed by the GNU General Public License.  LocatableDisplay is a
// component of the AP(r) CS Marine Biology Simulation
// case study (see www.collegeboard.com/ap/students/compsci).
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

import edu.kzoo.grid.GridObject;

import java.awt.Graphics2D;
import java.awt.Component;
import java.awt.Rectangle;

/**
 *  Grid Display Package:<br>
 *
 *  The <code>GridObjectDisplay</code> interface contains the
 *  method needed to display an object in a grid.
 *  Objects that implement the <code>GridObjectDisplay</code>
 *  interface are called on by the <code>GridDisplay</code> to
 *  draw grid objects. The association between a 
 *  particular <code>GridObject</code> subclass and its display
 *  is handled in the <code>DisplayMap</code> class.
 *
 *  <p>
 *  The <code>GridObjectDisplay</code> class is based on the
 *  College Board's <code>LocatableDisplay</code> class,
 *  as allowed by the GNU General Public License.
 *
 *  @author Alyce Brady
 *  @version 13 December 2003
 *  @see DisplayMap
 **/
public interface GridObjectDisplay
{
    /** Method invoked to draw a GridObject. The first argument is the
     *  grid object to draw, the second the component, the third the
     *  drawing surface, the last is the rectangle in which to draw. A
     *  class that implements this interface should draw a representation of
     *  the given GridObject object on the drawing surface in the given
     *  rectangle.
     *
     *  @param   obj        object we want to draw
     *  @param   comp       component on which to draw
     *  @param   g2         drawing surface
     *  @param   rect       rectangle in which to draw 
     **/
    void draw(GridObject obj, Component c, Graphics2D g2, Rectangle rect);

}
