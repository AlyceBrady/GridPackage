// Interface: GraphicalModelView
//
// Author: Alyce Brady
//
// This class is based on the College Board's EnvDisplay class, as
// allowed by the GNU General Public License.  EnvDisplay is a
// black-box class within the AP(r) CS Marine Biology Simulation
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

package edu.kzoo.kgui;

/**
 *  K College GUI Package:<br>
 *
 *  The <code>GraphicalModelView</code> interface specifies the
 *  methods that must be provided by any class used to provide
 *  a graphical view of a model.
 *
 *  <p>
 *  The <code>GraphicalModelView</code> class is based on the
 *  College Board's <code>EnvDisplay</code> class,
 *  as allowed by the GNU General Public License.
 *
 *  @author Alyce Brady
 *  @version 31 March 2004
 **/
public interface GraphicalModelView
{
    /** Provides a graphical view of the current state of the model.
     **/
    void showModel();

}
