// Class: KColor
//
//Author: Alyce Brady
//
//License Information:
//This class is free software; you can redistribute it and/or modify
//it under the terms of the GNU General Public License as published by
//the Free Software Foundation.
//
//This class is distributed in the hope that it will be useful,
//but WITHOUT ANY WARRANTY; without even the implied warranty of
//MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//GNU General Public License for more details.

package edu.kzoo.util;

import java.awt.Color;
import java.awt.color.ColorSpace;

/**
 *  Kalamazoo College Utility Classes:<br>
 * 
 *  The <code>edu.kzoo.util.KColor</code> class extends the
 *  <code>java.awt.Color</code> class to provide <code>static</code>
 *  methods that generate random colors with and without variable
 *  levels of transparency.
 * 
 *  This class is a mess because I can't decide what to do with it.  It
 *  should probably be a wrapper class around Color, so you could do something
 *  like
 *     Color c = new KColor(Color.BLUE);
 *     System.out.println(c);  // lower case, or
 *     System.out.println(c.toString(KColor.MIXED_CASE));
 *  Would have to implement delegation methods for all Color methods, though.
 * 
 *  Or maybe it should just be a class with handy static methods, so you
 *  could do something like
 *     Color c = Color.BLUE;
 *     System.out.println(KColor.toString(c));  // lower case, or
 *     System.out.println(KColor.toString(c, KColor.MIXED_CASE));
 *  Maybe toString should be renamed in the latter case.
 *
 *  @author Alyce Brady
 *  @version 1 March 2004
 **/
public class KColor extends Color
{
  // constants that define the possible character case modes for the
  // string representation of java.awt.Color constants

    public static final int LOWER_CASE = 0;
    public static final int MIXED_CASE = 1;
    public static final int UPPER_CASE = 2;


  // constructors (analogous to the constructors in java.awt.Color)

    /** Creates an opaque sRGB color with the specified red, green, and blue
     *  values in the range (0 - 255).  The actual color used in rendering
     *  depends on finding the best match given the color space available
     *  for a particular output device.  Alpha is defaulted to 255. 
     *    @param r the red component 
     *    @param g the green component 
     *    @param b the blue component 
     *    @see java.awt.Color(int, int, int)
     */
    public KColor(int r, int g, int b)
    {
        super(r, g, b);
    }

    /** Creates an sRGB color with the specified red, green, blue, and
     *  alpha values in the range (0 - 255).  The actual color used
     *  in rendering depends on finding the best match given the color
     *  space available for a particular output device.
     *    @param r the red component 
     *    @param g the green component 
     *    @param b the blue component 
     *    @param a the alpha component
     *    @see java.awt.Color(int, int, int, int)
     */
    public KColor(int r, int g, int b, int a)
    {
        super(r, g, b, a);
    }

    /** Creates an opaque sRGB color with the specified combined RGB value
     *  consisting of the red component in bits 16-23, the green component
     *  in bits 8-15, and the blue component in bits 0-7.  The actual color
     *  used in rendering depends on finding the best match given the color
     *  space available for a particular output device.  Alpha is defaulted
     *  to 255. 
     *    @param rgb the combined RGB components  
     *    @see java.awt.Color(int)
     */
    public KColor(int rgb)
    {
        super(rgb);
    }

    /** Creates an sRGB color with the specified combined RGB value
     *  consisting of the red component in bits 16-23, the green component
     *  in bits 8-15, and the blue component in bits 0-7.  The actual color
     *  used in rendering depends on finding the best match given the color
     *  space available for a particular output device.  If the
     *  <code>hasalpha</code> argument is <code>false</code>, alpha is
     *  defaulted to 255. 
     *    @param rgba the combined RGBA components  
     *    @param hasAlpha <code>true</code> if the alpha bits are valid;
     *                    <code>false</code> otherwise 
     *    @see java.awt.Color(int, boolean)
     */
    public KColor(int rgba, boolean hasAlpha)
    {
        super(rgba, hasAlpha);
    }

    /** Creates an opaque sRGB color with the specified red, green, and blue
     *  values in the range (0.0 - 1.0).  Alpha is defaulted to 1.0.  The
     *  actual color used in rendering depends on finding the best match
     *  given the color space available for a particular output device. 
     * 	  @param r the red component 
     * 	  @param g the green component 
     * 	  @param b the blue component 
     *    @see java.awt.Color(float, float, float)
     */
    public KColor(float r, float g, float b)
    {
        super(r, g, b);
    }

    /** Creates an sRGB color with the specified red, green, blue, and
     *  alpha values in the range (0.0 - 1.0).  The actual color used
     *  in rendering depends on finding the best match given the color
     *  space available for a particular output device.
     *    @param r the red component 
     *    @param g the green component 
     *    @param b the blue component 
     * 	  @param a the alpha component
     *    @see java.awt.Color(float, float, float, float)
     */
    public KColor(float r, float g, float b, float a)
    {
        super(r, g, b, a);
    }

    /** Creates a color in the specified <code>ColorSpace</code> with the
     *  color components specified in the float array and the specified alpha.
     *  The number of components is determined by the type of the
     *  <code>ColorSpace</code>.  For example, RGB requires 3 components,
     *  but CMYK requires 4 components.  
     * 	  @param cspacethe   <code>ColorSpace</code> to be used to
     *                       interpret the components 
     * 	  @param components  an arbitrary number of color components that is
     *                       compatible with the 
     * 	  @param alpha       alpha value 
     *    @throws IllegalArgumentException if any of the values in the
     *                       components array or alpha is outside of the
     *                       range 0.0 to 1.0 
     *    @see java.awt.Color(ColorSpace, float[], float)
     */
    public KColor(ColorSpace cspace, float[] components, float alpha)
    {
        super(cspace, components, alpha);
    }


  // static methods that generate random colors (opaque and transparent)

    /** Generates a pseudorandom opaque <code>Color</code> value.
     *  @return       the new pseudorandom opaque color
     **/
    public static Color getRandomOpaqueColor()
    {
        // There are 256 possibilities for the red, green, and blue attributes
        // of a color.  Generate random values for each color attribute.
        RandNumGenerator randNumGen = RandNumGenerator.getInstance();
        return new Color(randNumGen.nextInt(256),    // amount of red
                         randNumGen.nextInt(256),    // amount of green
                         randNumGen.nextInt(256));   // amount of blue
    }

    /** Generates a pseudorandom <code>Color</code> value with the
     *  specified transparency level.
     *  @param        alpha   the transparency level to use, in the range
     *                        of 0 to 255 where 255 is completely opaque
     *  @return       the new pseudorandom color with the specified
     *                        transparency level
     **/
    public static Color getRandomColor(int alpha)
    {
        // There are 256 possibilities for the red, green, and blue components
        // of a color.  Generate random values for each color component.
        RandNumGenerator randNumGen = RandNumGenerator.getInstance();
        return new Color(randNumGen.nextInt(256),    // amount of red
                         randNumGen.nextInt(256),    // amount of green
                         randNumGen.nextInt(256),    // amount of blue
                         alpha);                     // degree of transparency
    }

    /** Generates a pseudorandom <code>Color</code> value.  Colors returned
     *  by this method have a random alpha (transparency) component, as well
     *  as random red, green, and blue components.
     *  @return       the new pseudorandom color
     **/
    public static Color getRandomColor()
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


  // redefined method from the java.awt.Color class

    /** Returns a string representing this color.  If the color is one of
     *  the defined fields in the java.awt.Color class, the string is the
     *  name of the color in all lower-case characters.
     **/
     public String toString()
    {
        return toString(KColor.LOWER_CASE);
    }

    /** Returns a string representing this color.  If the color is one of
     *  the defined fields in the java.awt.Color class, the string is the
     *  name of the color in all lower-case, mixed case, or all upper-case
     *  characters, depending on the value of <code>characterCase</code>
     *  which should be one of KColor.LOWER_CASE, KColor.MIXED_CASE, or
     *  KColor.UPPER_CASE.
     **/
    public String toString(int characterCase)
    {
        String orig = super.toString();
        return orig;
    }
}
