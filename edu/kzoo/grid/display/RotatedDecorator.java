// Class: RotatedDecorator
//
// Author: Joel Booth
//
// This decorator allows objects being displayed to adjust for their direction.
// It was designed so that it can be applied to any subclass of ScaledDisplay
// as long as the Object it is associated with has a Direction.
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
import java.awt.Component;
import java.awt.Graphics2D;

import edu.kzoo.grid.Direction;

import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;

/**
 * Grid Display Package:<br>
 *
 *  A <code>RotatedDecorator</code> will allow a display to change as the
 *  object it is associated with changes direction.  The
 *  <code>GridObject</code> must have a <code>direction</code> method that
 *  returns a <code>Direction</code> object in order for the decorator to
 *  work.
 * 
 * @author Joel Booth
 * @version 28 July 2004
 *
 */
public class RotatedDecorator implements DisplayDecorator {
	
	//The original direction the object was facing
	Direction originalDirection;
	
	/** 
	 * Construct a new <code>RotatedDecorator</code> that can be
	 * added to a <code>ScaledDisplay</code>.
	 * @param d The origrinal direction the object is facing
	 */
	public RotatedDecorator(Direction d) {
		originalDirection = d;
	}
	
	/** Apply the rotating aspect of the decoration.  It is called from the class that
	 * has added the decorator.  
	 */
	public void decorate(GridObjectDisplay sd, GridObject obj, Component comp, Graphics2D g2) {
		// Rotate drawing surface to compensate for the direction of the object
		// in the image (in case it is not facing North, as assumed). 
		if ( ! originalDirection.equals(Direction.NORTH) )
		{
			int rotationInDegrees = originalDirection.inDegrees();
			g2.rotate(- Math.toRadians(rotationInDegrees));
			if (rotationInDegrees >= 180)
				g2.scale(1, -1); // flip image upside-down
		}

		// Now rotate again to represent the direction the object is facing.
		adjustForDirection(obj, g2);
	}
	
	/** Adjusts the graphics system for drawing an object with direction.
	 *  (Precondition: <code>obj</code> has a <code>direction</code> method.)
	 *  @param   obj        object we want to draw
	 *  @param   g2         drawing surface
	 **/
	public static void adjustForDirection(GridObject obj, Graphics2D g2)
	{        
		// Rotate drawing surface to capture object's orientation
		// (direction).  (Assumption is that without rotating the
		// drawing surface the object will be drawn facing North.)
		// Object must have a direction method.
                Class<? extends GridObject> objClass = obj.getClass();
		try
		{
			Method dirMethod = objClass.getMethod("direction", new Class[0]);
			Direction dir = (Direction)dirMethod.invoke(obj, new Object[0]);
			int rotationInDegrees = dir.inDegrees();
			g2.rotate(Math.toRadians(rotationInDegrees));
			//return rotationInDegrees;
		}
		catch (NoSuchMethodException e)
		{ throw new IllegalArgumentException("Cannot rotate object of " + objClass +
			" class; cannot invoke direction method."); }
		catch (InvocationTargetException e)
		{ throw new IllegalArgumentException("Cannot rotate object of " + objClass +
			" class; exception in direction method."); }
		catch (IllegalAccessException e)
		{ throw new IllegalArgumentException("Cannot rotate object of " + objClass +
			" class; cannot access direction method."); }
	}

}
