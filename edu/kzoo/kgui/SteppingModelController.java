// Class SteppingModelController
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

import java.lang.UnsupportedOperationException;

/**
 *  K College GUI Package:<br>
 *
 *  A <code>SteppingModelController</code> controls the running of a
 *  application through timesteps.  Subclasses must override the
 *  <code>step</code> method to do something meaningful for a
 *  specific application.
 *
 *  @author Alyce Brady
 *  @version 31 March 2004
 **/
public abstract class SteppingModelController  
{
    private Object model = null;

    /** Gets the model component of this application controlled
     *  by this controller.
     *    @return the model being controlled
     **/
    public Object getModel()
    {
        return this.model;
    }

    /** Sets the model component of this application controlled
     *  by this controller.
     *    @param model the model to act on
     **/
    public void setModel(Object model)
    {
        this.model = model;
    }

    /** Advances the application one step. 
     **/
    public abstract void step();

    /** Determines whether a running application has reached
     *  a desired stopping state.  Examples include whether the
     *  mouse in a maze has found the cheese, whether the first
     *  (or last) competitor in a race has finished, or whether
     *  a chemical reaction has reached equilibrium.
     *    @return <code>true</code> if the application should
     *             stop 
     **/
    public boolean hasReachedStoppingState()
    { 
        return false;   // default behavior
    }

    /** Re-initializes the state of the grid application for
     *  a restart.
     **/
    public void restart()
    { 
        throw new UnsupportedOperationException();   // default behavior
    }

}
