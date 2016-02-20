// Class: RotatedDecorator
//
// Author: Joel Booth
//
// This decorator allows objects being displayed to adjust for a tint color.  It
//  can only be applied to ScaledImageDisplay objects.  The associated Object
//  must have a Color.  
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

import java.awt.Component;
import java.awt.Graphics2D;

import edu.kzoo.grid.GridObject;

/**
 * A tinting decorator for a <code>ScaledImageDisplay</code>.  The associated
 * object must have a <code>Color</code>.
 * @author Joel Booth
 * @version 28 July 2004
 *
 */
public class ScaledImageTintDecorator implements DisplayDecorator {

	/**
	 * Decorate the ScaledImageDisplay so that it appears tinted
	 */
	public void decorate(ScaledDisplay sd, GridObject obj, Component comp, Graphics2D g2) { 
		((ScaledImageDisplay)sd).tint(obj, comp, g2);

	}

}
