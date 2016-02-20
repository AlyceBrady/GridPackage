// Class: DisplayDecorator
//
// Author: Joel Booth
//
// The Display decorator provides an interface for decorators
// to be used on the different display types.
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

/**
 *  Grid Display Package:<br>
 *
 *  A <code>DisplayDecorator</code> provides and interface for any decorator that
 *  will be used in the Display package.  All decorators must implement the
 *  interface in order to be added to a Display object.
 
 *  @author Joel Booth
 *  @version 28 July 2004
 */
public interface DisplayDecorator {

	public abstract void decorate(ScaledDisplay sd, GridObject obj, Component comp, Graphics2D g2);
}
