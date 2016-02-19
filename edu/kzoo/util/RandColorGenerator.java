// Class: RandColorGenerator
//
// Author: Alyce Brady
//
// Created on Mar 9, 2004
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

package edu.kzoo.util;

import java.awt.Color;

/**
 *  Kalamazoo College Utility Classes:<br>
 * 
 *  The <code>edu.kzoo.util.RandColorGenerator</code> class provides
 *  a <code>static</code> method that can be used to generate a
 *  random color.
 *
 *  <p>
 *  Example of how to use <code>RandColorGenerator</code>:  
 *    <pre><code>
 *       RandColorGenerator randColorGen = RandColorGenerator.getInstance();
 *       Color opaqueColor = randColorGen.nextColor();
 *       Color colorWithSpecifiedTransparency = randColorGen.nextColor(alpha);
 *       Color colorWithRandomTransparency =
 *                      randColorGen.nextColorWithVariableTransparency();
 *    </code></pre>
 *
 *  @author Alyce Brady
 *  @version Mar 9, 2004
 **/
public class RandColorGenerator
{
    // Class Variable: Only one generator is created by this class.
    private static RandColorGenerator theRandColorGenerator =
                                                new RandColorGenerator();

    /* Private constructor ensures that a RandColorGenerator is ONLY
     * acquired through the getInstance method.
     */
    private RandColorGenerator()
    {
    }

    /** Returns a random color generator.
     *  Always returns the same <code>RandColorGenerator</code> object to
     *  provide a better sequence of random colors.
     **/
    public static RandColorGenerator getInstance()
    {
        return theRandColorGenerator;
    }

    /** Returns the next pseudorandom opaque <code>Color</code> value
     *  from this random color generator's sequence.
     *  @return       the next pseudorandom opaque color
     **/
    public Color nextColor()
    {
        // There are 256 possibilities for the red, green, and blue components
        // of a color.  Generate random values for each color component.
        RandNumGenerator randNumGen = RandNumGenerator.getInstance();
        return new Color(randNumGen.nextInt(256),    // amount of red
                         randNumGen.nextInt(256),    // amount of green
                         randNumGen.nextInt(256));   // amount of blue
    }

    /** Returns the next pseudorandom <code>Color</code> value with the
     *  specified transparency level from this random color generator's
     *  sequence.
     *  @param        alpha   the transparency level to use for the next
     *                        color, in the range of 0 to 255 where 255 is
     *                        completely opaque
     *  @return       the next pseudorandom color with the specified
     *                        transparency level
     **/
    public Color nextColor(int alpha)
    {
        // There are 256 possibilities for the red, green, and blue components
        // of a color.  Generate random values for each color component.
        RandNumGenerator randNumGen = RandNumGenerator.getInstance();
        return new Color(randNumGen.nextInt(256),    // amount of red
                         randNumGen.nextInt(256),    // amount of green
                         randNumGen.nextInt(256),    // amount of blue
                         alpha);                     // degree of transparency
    }

    /** Returns the next pseudorandom <code>Color</code> value from this
     *  random color generator's sequence.  Colors returned by this method
     *  are created with a random alpha (transparency) component, as well as
     *  random red, green, and blue components.
     *  @return       the next pseudorandom color
     **/
    public Color nextColorWithVariableTransparency()
    {
        // There are 256 possibilities for the red, green, and blue components
        // of a color, and for the degree of transparency.  Generate random
        // values for each color component.
        RandNumGenerator randNumGen = RandNumGenerator.getInstance();
        return new Color(randNumGen.nextInt(256),    // amount of red
                         randNumGen.nextInt(256),    // amount of green
                         randNumGen.nextInt(256),    // amount of blue
                         randNumGen.nextInt(256));   // degree of transparency
    }

}
