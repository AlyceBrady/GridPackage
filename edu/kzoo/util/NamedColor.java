// Class: NamedColor
//
// Author: Joel Booth
// Assistance From: Alyce Brady
//    - Some of the constructors and random color generation was taken
//      from a previous unworking version of NamedColor.
//
// Creation Date: September 1, 2004
//
// License Information:
// This class is free software; you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation.
//
// This class is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.

package edu.kzoo.util;

import java.awt.Color;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Set;


/**
 *  Kalamazoo College Utility Classes:<br>
 * 
 *  The <code>edu.kzoo.util.NamedColor</code> class extends the
 *  <code>java.awt.Color</code> class to associate names with colors and
 *  to provide <code>static</code> methods that generate random colors.
 *  The random color generation methods can generate random named colors,
 *  random opaque colors, and random colors with variable levels of
 *  transparency.  Name associations are provided (in English) for many common
 *  colors, and the <code>toString</code> method returns the color name
 *  whenever possible.  Internally all names are stored in upper-case, but
 *  the methods that take names as parameters will accept either upper- or
 *  lower-case, converting them to upper-case as needed.
 *
 *  Names are provided for all of the color constants found in
 *  <code>java.awt.Color</code>, as well as a few additional colors.  New
 *  named colors can be added with the <code>NamedColor</code> constructors
 *  that take a name as a parameter and with the <code>setName</code> methods.
 *  A set of static methods support getting color names and looking up colors
 *  by name.
 * 
 * @author Joel Booth
 * @author Alyce Brady
 * @version September 1, 2004
 *
 */
public class NamedColor extends Color
{
    // A class that contains the mapping of color names to colors.
    private static ColorMap colors = new ColorMap();

    /** NamedColor version of <code>java.awt.Color</code> constant **/
    public static final NamedColor
        WHITE = new NamedColor(Color.WHITE, "WHITE"),
        LIGHT_GRAY = new NamedColor(Color.LIGHT_GRAY, "LIGHT_GRAY"),
        GRAY = new NamedColor(Color.GRAY, "GRAY"),
        DARK_GRAY = new NamedColor(Color.DARK_GRAY, "DARK_GRAY"),
        BLACK = new NamedColor(Color.BLACK, "BLACK"),
        RED = new NamedColor(Color.RED, "RED"),
        PINK = new NamedColor(Color.PINK, "PINK"),
        ORANGE = new NamedColor(Color.ORANGE, "ORANGE"),
        YELLOW = new NamedColor(Color.YELLOW, "YELLOW"),
        GREEN = new NamedColor(Color.GREEN, "GREEN"),
        MAGENTA = new NamedColor(Color.MAGENTA, "MAGENTA"),
        CYAN = new NamedColor(Color.CYAN, "CYAN"),
        BLUE = new NamedColor(Color.BLUE, "BLUE");

    public static final NamedColor
        CINNAMON = new NamedColor(200, 100, 100, "CINNAMON");
    /** Less salmon-colored than <code>PINK</code> **/
    public static final NamedColor
        ROSE = new NamedColor(255, 144, 192, "ROSE");
    public static final NamedColor
        FUSCHIA = new NamedColor(230, 51, 181, "FUSCHIA");
    /** Slightly less yellow than <code>ORANGE</code> **/
    public static final NamedColor
        PUMPKIN = new NamedColor(255, 128, 0, "PUMPKIN");
    public static final NamedColor
        MEDIUM_GREEN = new NamedColor(0, 200, 0, "MEDIUM_GREEN");
    public static final NamedColor
        DARK_GREEN = new NamedColor(16, 106, 40, "DARK_GREEN");
    public static final NamedColor
        DARK_BLUE = new NamedColor(48, 8, 176, "DARK_BLUE");
    /** Midnight blue **/
    public static final NamedColor
        MIDNIGHT = new NamedColor(0, 0, 128, "MIDNIGHT");
    public static final NamedColor
        INDIGO = new NamedColor(64, 0, 128, "INDIGO");
    public static final NamedColor
        PURPLE = new NamedColor(128, 0, 128, "PURPLE");
    public static final NamedColor
        VIOLET = new NamedColor(128, 0, 192, "VIOLET");
    public static final NamedColor
        LILAC = new NamedColor(187, 62, 249, "LILAC");
    public static final NamedColor
        BROWN = new NamedColor(160, 64, 0, "BROWN");

    /** Flag to specify behavior for the <code>toString(int)</code> method **/
    public static final int PRINT_RGB = 0,
                            PRINT_NAME = 1;
    
    // A random number generator that is used in producing random values.
    private static RandNumGenerator randGen = RandNumGenerator.getInstance();


  // NamedColor constructors

    /** Creates a <code>NamedColor</code> version of the given
     *  <code>Color</code> object.
     *    @param c   the <code>Color</code> object to convert to a
     *               <code>NamedColor</code> object
     **/
    public NamedColor(Color c)
    {
        super(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha());
    }

    /** Creates a <code>NamedColor</code> version of the given
     *  <code>Color</code> object with the given name.
     *  (Precondition: there is not already a name associated with the given
     *  color, nor is there already a color associated with the given name.)
     *    @param c    the <code>Color</code> object to convert to a
     *                <code>NamedColor</code> object
     *    @param name the name to associate with the given color
     *    @throws IllegalArgumentException if there is already
     *            a name => color association involving either <code>c</code>
     *            or <code>name</code>
     **/
    public NamedColor(Color c, String name)
    {
        super(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha());
        colors.addColor(name, this);
    }
    
    /** Creates an opaque sRGB color with the specified red, green, and blue
     *  values in the range (0 - 255).  The actual color used in rendering
     *  depends on finding the best match given the color space available
     *  for a particular output device.  Alpha is defaulted to 255. 
     *    @param r the red component 
     *    @param g the green component 
     *    @param b the blue component 
     *    @see java.awt.Color#Color(int, int, int)
     **/
    public NamedColor(int r, int g, int b)
    {
        super(r, g, b);
    }
    
    /** Creates an opaque sRGB color with the specified red, green, and blue
     *  values in the range (0 - 255) and associated with the specified name.
     *  The actual color used in rendering depends on finding the best match
     *  given the color space available for a particular output device. 
     *  Alpha (the transparency level) defaults to 255. 
     *  (Precondition: there is not already a name associated with the given
     *  color, nor is there already a color associated with the given name.)
     *    @param r the red component 
     *    @param g the green component 
     *    @param b the blue component 
     *    @param name the name to associate with the given color
     *    @throws IllegalArgumentException if there is already
     *            a name => color association involving either <code>c</code>
     *            or <code>name</code>
     **/
    public NamedColor(int r, int g, int b, String name)
    {
        super(r, g, b);
        colors.addColor(name, this);
    }

    /** Creates an sRGB color with the specified red, green, blue, and
     *  alpha values in the range (0 - 255).  The actual color used
     *  in rendering depends on finding the best match given the color
     *  space available for a particular output device.
     *    @param r the red component 
     *    @param g the green component 
     *    @param b the blue component 
     *    @param a the alpha component
     *    @see java.awt.Color#Color(int, int, int, int)
     **/
    public NamedColor(int r, int g, int b, int a)
    {
        super(r, g, b, a);
    }

    /** Creates an sRGB color with the specified red, green, blue, and
     *  alpha values in the range (0 - 255) and associated with the given name.
     *  The actual color used in rendering depends on finding the best match
     *  given the color space available for a particular output device. 
     *  (Precondition: there is not already a name associated with the given
     *  color, nor is there already a color associated with the given name.)
     *    @param r the red component 
     *    @param g the green component 
     *    @param b the blue component 
     *    @param a the alpha component
     *    @param name the name to associate with the given color
     *    @throws IllegalArgumentException if there is already
     *            a name => color association involving either <code>c</code>
     *            or <code>name</code>
     **/
    public NamedColor(int r, int g, int b, int a, String name)
    {
        super(r, g, b, a);
        colors.addColor(name, this);
    }


  // Static methods, not tied to a particular instance of the NamedColor class

    /** Associates the given name with the given color. 
     *  (Precondition: there is not already a name associated with the given
     *  color, nor is there already a color associated with the given name.)
     *    @param name the name to associate with the color <code>c</code>
     *    @param c    the color
     *    @throws IllegalArgumentException if there is already
     *            a name => color association involving either <code>c</code>
     *            or <code>name</code>
     **/
    public static void setNameFor(Color c, String name)
    {
        colors.addColor(name.toUpperCase(), new NamedColor(c));
    }

    /** Changes a name => color association to the new name and the new color.
     *  Any previous association involving either <code>name</code> or
     *  <code>c</code> no longer exists.
     *    @param name    the name of the color
     *    @param c       the color
     **/
    public static void changeNameOrColor(String name, Color c)
    {
        colors.changeNameColorMapping(name, c);
    }

    /** Checks whether there is a name associated with the given color.
     *    @param c the color
     *    @return <code>true</code> if there is an associated name,
     *              otherwise <code>false</code>
     **/
    public static boolean hasNameFor(Color c)
    {
        return colors.containsColor(c); 
    }
     
    /** Checks whether there is a color associated with the given name.
     *    @param name the name of the color
     *    @return <code>true</code> if there is an associated color,
     *              otherwise <code>false</code>
     **/
    public static boolean hasColorFor(String name)
    {
       return colors.containsName(name);
    }

    /** Gets the name associated with the specified color, if there is one.
     *  Returns a null object if there is no name => color association for
     *  the specified name.
     *    @param c     a color
     *    @return      the name associated with <code>color</code>;
     *                 null if there is no such association
     **/
    public static String getNameFor(Color c)
    {
        return colors.getNameFor(c);
    }

    /** Gets the color associated with the specified name, if there is one.
     *  Returns a null object if there is no name => color association for
     *  the specified name.
     *    @param name  a color name
     *    @return      the color associated with <code>name</code>;
     *                 null if there is no such association
     **/
    public static NamedColor getNamedColor(String name)
    {
        name = name.toUpperCase();
        return colors.getNamedColor(name);
    }

    /** Gets the names of all the colors with name => color associations.
     *    @return all the known color names
     **/
    public static Set<String> getAllColorNames()
    {
        return colors.getAllColorNames();
    }

    /** Gets all the colors with name => color associations.
     *    @return all the known colors
     **/
    public static Set<NamedColor> getAllNamedColors()
    {
        return colors.getAllNamedColors();
    }


    /** Generates a pseudorandom opaque <code>NamedColor</code> value.
     *  @return       the new pseudorandom opaque color
     **/
    public static NamedColor getRandomColor()
    {
        // There are 256 possibilities for the red, green, and blue attributes
        // of a color.  Generate random values for each color attribute.
        return new NamedColor(randGen.nextInt(256),    // amount of red
                              randGen.nextInt(256),    // amount of green
                              randGen.nextInt(256));   // amount of blue
    }
     
    /** Generates a pseudorandom <code>NamedColor</code> value with the
     *  specified transparency level.
     *  @param        alpha   the transparency level to use, in the range
     *                        of 0 to 255 where 255 is completely opaque
     *  @return       the new pseudorandom color with the specified
     *                        transparency level
     **/
    public static NamedColor getRandomColor(int alpha)
    {
        // There are 256 possibilities for the red, green, and blue components
        // of a color.  Generate random values for each color component.
        return new NamedColor(randGen.nextInt(256),    // amount of red
                              randGen.nextInt(256),    // amount of green
                              randGen.nextInt(256),    // amount of blue
                              alpha);                     // degree of transparency
    }

    /** Generates a pseudorandom named <code>NamedColor</code> value.  Colors
     *  returned by this method have a random alpha (transparency) component,
     *  as well as random red, green, and blue components.
     *    @return       the new pseudorandom color
     **/
    public static NamedColor getRandomAlphaColor()
    {      
        // There are 256 possibilities for the red, green, and blue components
        // of a color, and for the degree of transparency.  Generate random
        // values for each color component.
        return new NamedColor(randGen.nextInt(256),    // amount of red
                              randGen.nextInt(256),    // amount of green
                              randGen.nextInt(256),    // amount of blue
                              randGen.nextInt(256));   // degree of transparency
    }

    /** Generates a pseudorandom opaque named<code>NamedColor</code> value.
     *    @return       the new pseudorandom named opaque color
     **/
    public static NamedColor getRandomNamedColor()
    {
        return colors.getRandomNamedColor();
    }

    /** Generates a pseudorandom named <code>NamedColor</code> value with the
     *  specified transparency level.
     *    @param      alpha   the transparency level to use, in the range
     *                        of 0 to 255 where 255 is completely opaque
     *    @return     the new pseudorandom named color with the specified
     *                        transparency level
     **/
    public static NamedColor getRandomNamedColor(int alpha)
    {      
        NamedColor c = colors.getRandomNamedColor();

        // Adjust the transparency of the random color.
        return new NamedColor(c.getRed(),     // amount of red
                              c.getGreen(),   // amount of green
                              c.getBlue(),    // amount of blue
                              alpha);         // degree of transparency
    }


  // NamedColor instance methods

    /** Associates the given name with this color. 
     *  (Precondition: there is not already a name associated with this
     *  color.)
     *    @param name the name to associate with this color
     *    @throws IllegalArgumentException if there is already
     *            a name => color association involving either <code>c</code>
     *            or <code>name</code>
     **/
    public void setName(String name)
    {
        colors.addColor(name.toUpperCase(), this);
    }

    /** Changes the name associated with this color to the specified name.
     *  If <code>name</code> was previously associated with any other color,
     *  that association no longer exists.
     *    @param name    the name of this color
     **/
    public void changeName(String name)
    {
        colors.changeNameColorMapping(name, this);
    }

    /** Gets the name associated with this color.
     *    @return   the name associated with this <code>NamedColor</code> object;
     *              null if there is no such association
     **/
    public String getName()
    {
        return colors.getNameFor(this);
    }

    /** Gets the component representation of the color (the value returned
     *  by the <code>java.util.Color.toString</code> method).
     *    @return  the component representation of the color
     */
    public String getRGBRepresentation()
    {
        return super.toString();
    }

    /** Returns a string representation of the color -- the name of the color
     *  if known, otherwise the standard representation as defined in
     *  <code>java.awt.Color.toString</code>.
     *     @return a <code>String</code> representation of the color
     **/
    public String toString()
    {
       // Check if the color is in the map       
       if (colors.containsColor(this))
           return colors.getNameFor(this);
       else
           return super.toString();
    }
    
    /** Returns a string representation of the color.  Two
     *  flags are available:<br>
     *    PRINT_RGB -  Return the <code>java.awt.Color</code> representation of
     *                 the color components<br>
     *    PRINT_NAME - Return the <code>NamedColor</code> (default) representation,
     *                 which consists of the English name if it is available or
     *                 the <code>java.awt.Color</code> representation if there
     *                 is no name available for this color
     * 
     *    @param printMode   the type of <code>String</code> to generate
     *    @return  the <code>String</code> representation of the color
     **/
    public String toString(int printMode)
    {
        // Check if it should print the RGB components
        if (printMode == PRINT_RGB)
            // If so use the method from java.awt.Color
            return super.toString();
        else if (printMode == PRINT_NAME)
            // Else use the default toString
            return toString();
        else
            // Should never go here, but it's a failsafe
            return toString();
    }

    /** <code>ColorMap</code> is a protected static class that represents a
     *  mapping of colors to names and names to colors.  It uses two HashMaps
     *  to implement this 1 - 1 mapping, ensuring quick lookups in either
     *  direction.  All names are held in all uppercase format to avoid the
     *  problem different capitilizations cause for hashing on the keys.
     *
     *  <p>
     *  All colors in the map are <code>NamedColor</code> objects.
     * 
     *    @author Joel Booth
     *    @version September 1, 2004
     **/
    protected static class ColorMap
    {
        // Two hashmaps to store the mappings between names and colors.
        private HashMap<String, NamedColor> nameToColorMap =
                                        new HashMap<String, NamedColor>(30);
        private HashMap<NamedColor, String> colorToNameMap =
                                        new HashMap<NamedColor, String>(30);

        /** Adds a color to the map.
         *  (Precondition: neither <code>name</code> nor <code>c</code>
         *  appears in the map already.
         *    @param name the name of the color
         *    @param c the color to add
         *    @throws IllegalArgumentException if either
         *            <code>c</code> or <code>name</code> are already in the
         *            map
         **/
        public void addColor(String name, Color c)
        {
            if ( containsName(name) )
               throw new IllegalArgumentException("The name '" + name +
                                "' already has a color associated with it.");
            else if ( containsColor(c) )
                throw new IllegalArgumentException("The color '" + c +
                                "' already has a name associated with it.");

            // Convert the Color to a NamedColor to ensure that all items in
            // the map are of the same type.  That way a NamedColor will always
            // be returned.  A standard Color variable can hold a
            // NamedColor object because NamedColor is a subclass of Color.  If
            // c is already a NamedColor, this will merely create a new,
            // equivalent NamedColor to the map.
            NamedColor k = new NamedColor(c);
            name = name.toUpperCase();
            colorToNameMap.put(k, name);
            nameToColorMap.put(name, k);
        }

        /** Adds a named color to the map, removing any previous mapping
         *  involving either <code>name</code> or <code>c</code>.
         *    @param name the name of the color
         *    @param c the color
         **/
        public void changeNameColorMapping(String name, Color c)
        {
            // Remove any old mappings involving name or c.
            if ( containsName(name) )
            {
                Color oldColor = (Color) nameToColorMap.get(name);
                nameToColorMap.remove(name);
                colorToNameMap.remove(oldColor);
            }
            if ( containsColor(c) )
            {
                String oldName = (String) colorToNameMap.get(c);
                colorToNameMap.remove(c);
                nameToColorMap.remove(oldName);
            }

            addColor(name, c);
        }

        /** Checks whether or not the mapping contains a color with the given
         *  name.
         *    @param name the name of the color to look for
         *    @return     <code>true</code> if the color is in the map,
         *                otherwise <code>false</code> 
         */
        public boolean containsName(String name)
        {
            return nameToColorMap.containsKey(name.toUpperCase());
        }
        
        /** Checks whether or not the mapping contains a name => color
         *  mapping for the given color.
         *    @param c the <code>NamedColor</code> to look for
         *    @return  <code>true</code> if the color is in the map,
         *             otherwise <code>false</code> 
         **/
        public boolean containsColor(Color c)
        {
            return colorToNameMap.containsKey(c); 
        }

        /** Gets the name associated with the specified color.
         *    @param c     a color
         *    @return      the name associated with <code>color</code>;
         *                 null if there is no such association
         **/
        public String getNameFor(Color c)
        {
            return ( (String) colorToNameMap.get(c) );
        }

        /** Gets the color associated with the specified name.
         *    @param name  a color name
         *    @return      the color associated with <code>name</code>;
         *                 null if there is no such association
         **/
        public NamedColor getNamedColor(String name)
        {
            return (NamedColor) nameToColorMap.get(name.toUpperCase());
        }

        /** Gets all of the color names in the map.
         *    @return all the color names in the map (<code>String</code>
         *            objects)
         */
        public Set<String> getAllColorNames()
        {
            return nameToColorMap.keySet();
        }
        
        /** Gets all of the colors in the map (colors for which there are
         *  associated names.
         *    @return a <code>Set</code> of all the <code>NamedColor</code>
         *            objects in the map
         */
        public Set<NamedColor> getAllNamedColors()
        {
            return colorToNameMap.keySet();
        }

        /** Gets a random <code> NamedColor</code> from the map.
         *    @return a random <code>NamedColor</code> from the map
         */
        public NamedColor getRandomNamedColor()
        {                   
            // Get a random integer value that is within the size of the map.
            int i = randGen.nextInt(nameToColorMap.size());
            
            // Create a temporary arrayList that can be used to access a
            // random index .
            ArrayList<NamedColor> tempArrayList =
                        new ArrayList<NamedColor>(nameToColorMap.values());
            
            // Return the random indexed NamedColor.
            return tempArrayList.get(i);           
        }   

    }
}
