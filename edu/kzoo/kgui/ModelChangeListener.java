// Class: ModelChangeListener
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

package edu.kzoo.kgui;

/**
 *  K College GUI Package:<br>
 *
 *  The <code>ModelChangeListener</code> interface specifies the
 *  method used to notify <code>ModelChangeListener</code> objects
 *  of changes to the model.
 *
 *  @author Alyce Brady
 *  @version 31 March 2004
 **/
public interface ModelChangeListener
{
    /** Reacts to a change in the model being used.
     *    @param newModel  the new model being used
     **/
    public void reactToNewModel(Object newModel);
}
