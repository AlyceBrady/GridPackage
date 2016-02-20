// Class: DefaultDisplayFactory
//
// Author: Alyce Brady 
//
// Created on Mar 1, 2005
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

import java.util.HashSet;
import java.util.Iterator;

/**
 *  Grid Display Package:<br>
 *
 *  The <code>DefaultDisplayFactory</code> class contains methods
 *  that provide a suitable default display for a given class.
 *
 *  @author Alyce Brady
 *  @version Mar 10, 2005
 **/
public class DefaultDisplayFactory
{
    private static GridObjectDisplay baseDefault = new DefaultDisplay();
    private static String baseImageDirectories[] = {""};
    private static String baseSuffixes[] = {"gif", "GIF",
                                            "jpg", "jpeg", "JPG", "JPEG"};
    private static HashSet imageDirectories = new HashSet();
    private static HashSet suffixes = new HashSet();

    static
    {
        for ( int i = 0; i < baseImageDirectories.length; i++ )
            imageDirectories.add(baseImageDirectories[i]);
        for ( int i = 0; i < baseSuffixes.length; i++ )
            suffixes.add(baseSuffixes[i]);
    }

    /** Adds the specified directory to the list of directories in
     *  which to look for image files.
     *    @param directory  name of directory in which to look for image
     *                      files, including separator that precedes a
     *                      filename (e.g., <code>directory + imagefile</code>
     *                      should be a valid name for a file in the
     *                      specified directory)
     */
    public static void addDirectory(String directory)
    {
        imageDirectories.add(directory);
    }

    /** Adds the specified suffix to the list of image suffixes to look for.
     *    @param suffix  image suffix (e.g., "tiff")
     */
    public static void addSuffix(String suffix)
    {
        suffixes.add(suffix);
    }

    /** Returns a DefaultDisplay object.
     **/
    public static GridObjectDisplay getDefaultDisplay()
    {
        return baseDefault;
    }

    /** Returns a default display specific to the given class if there
     *  is one.  In the following detailed description of which
     *  display object is returned, assume that <em>ClassName</em>
     *  is the base class name passed in as a parameter and <em>package</em>
     *  is the name of the package in which that class resides.  The
     *  returned display is:
     *    <ul>
     *      <li>a <code><em>ClassName</em>Display</code> object if there is a
     *          a <code><em>ClassName</em>Display</code> class with a default
     *          (no-parameter) constructor in <em>package</em>, in
     *          the associated display package (<em>package</em>.display),
     *          or in the unnamed default display
     *      <li>a <code>ScaledImageDisplay</code> object if there is a
     *          an image file named <em>ClassName.sfx</em> in the jar
     *          file or current directory (where <em>sfx</em> is one of
     *          the following suffixes: gif, GIF, jpg, JPG, jpeg, JPEG,
     *          or any added with the <code>addSuffix</code> method)
     *  If no default display specific to the given class is available,
     *  this method returns <code>null</code>.
     *      @param className  the name of the class for which to find
     *                        a default display
     **/
    public static GridObjectDisplay getDefaultDisplay(Class cls)
    {
        return getDefaultDisplay(cls.getName());
    }

    /** Returns a default display specific to the given class if there
     *  is one.  In the following detailed description of which
     *  display object is returned, assume that <em>ClassName</em>
     *  is the base class name passed in as a parameter and <em>package</em>
     *  is the name of the package in which that class resides.  The
     *  returned display is:
     *    <ul>
     *      <li>a <code><em>ClassName</em>Display</code> object if there is a
     *          a <code><em>ClassName</em>Display</code> class with a default
     *          (no-parameter) constructor in <em>package</em>, in
     *          an associated display package (<em>package</em>.display),
     *          or in the unnamed default display
     *      <li>a <code>ScaledImageDisplay</code> object if there is a
     *          an image file named <em>ClassName.sfx</em> in the jar
     *          file, in the current directory, or in a directory specified
     *          with the <code>addDirectory</code> method, where <em>sfx</em>
     *          is one of the following suffixes: gif, GIF, jpg, JPG, jpeg,
     *          JPEG, or any added with the <code>addSuffix</code> method)
     *  If no default display specific to the given class is available,
     *  this method returns <code>null</code>.
     *      @param className  the name of the class for which to find
     *                        a default display
     **/
    public static GridObjectDisplay getDefaultDisplay(String className)
    {
        // If the className includes package information, strip it.
        String packageName = null;
        String baseName = className;
        int endOfPkgPrefix = className.lastIndexOf('.');
        if ( endOfPkgPrefix != -1 )
        {
            packageName = className.substring(0, endOfPkgPrefix);
            baseName = className.substring(endOfPkgPrefix + 1);
        }

        // Look for ...Display class in a package.
        if ( packageName != null )
        {
            // Look in that package.
            try { return getDisplayClass(className, className + "Display"); }
            catch (Exception e) { /* Just go on to next attempt. */ }

            // Look in the related display package.
            String tempName = packageName + ".display." + baseName;
            try { return getDisplayClass(className, tempName + "Display"); }
            catch (Exception e) { /* Just go on to next attempt. */ }
        }

        // Look for ...Display class in unnamed default package.
        try { return getDisplayClass(className, baseName + "Display"); }
        catch (Exception e) { /* Just go on to next attempt. */ }

        // Look for image files in various directories, e.g., ClassName.gif.
        Iterator dirIt = imageDirectories.iterator();
        while ( dirIt.hasNext() )
        {
            String fullName = (String) dirIt.next() + baseName;
            Iterator suffixIt = suffixes.iterator();
            while ( suffixIt.hasNext() )
            {
                String suffix = (String) suffixIt.next();
                String imageFilename = fullName + "." + suffix;
                ScaledImageDisplay disp =
                                        new ScaledImageDisplay(imageFilename);
                if ( disp.imageLoadedOK() )
                {
                    // Add the image display class to the DisplayMap to
                    // speed up future displays.
                    DisplayMap.associate(className, disp);
                    return disp;
                }
            }
        }

        return null;
    }

    /** Instantiates a display class named <code>fullDisplayClassName</code>,
     *  if such a class exists.
     *    @param fullClassName  the name of the class being displayed
     *    @param fullDispClassName  the name of the display class to create
     *    @return an object of the specified display class
     *    @throws ClassNotFoundException
     *    @throws IllegalAccessException
     *    @throws InstantiationException
     */
    protected static
    GridObjectDisplay getDisplayClass(String fullClassName,
                                      String fullDispClassName)
        throws ClassNotFoundException, IllegalAccessException,
               InstantiationException
    {
        // Instantiate the appropriate display class (if it exists).
        Class dispClass = Class.forName(fullDispClassName);
        ScaledDisplay disp = (ScaledDisplay) dispClass.newInstance();

        // Assuming everything has worked so far, add the default
        // display class to the DisplayMap to speed up future displays.
        DisplayMap.associate(fullClassName, disp);
        return disp;
    }

}
