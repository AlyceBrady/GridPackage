// Class: RandNumGenerator
//
// Author: Alyce Brady
//
// This class is based on the College Board's RandNumGenerator
// (see http://www.collegeboard.com/student/testing/ap/compsci_a/case.html),
// as allowed by the GNU General Public License.
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

import java.util.Random;

/**
 *  Kalamazoo College Utility Classes:<br>
 * 
 *  The <code>RandNumGenerator</code> class provides a singleton
 *  object for random number generation.  <code>RandNumGenerator</code>
 *  extends the <code>java.util.Random</code> class, so the object of this
 *  class inherits all methods defined for the <code>Random</code> class.
 *  Using this class, though, many different objects can share a single
 *  source of random numbers.  This eliminates the potential problem of
 *  having multiple random number generators generating sequences of
 *  numbers that are too similar.
 *
 *  <p>
 *  Example of how to use <code>RandNumGenerator</code>:  
 *    <pre><code>
 *       RandNumGenerator randNumGen = RandNumGenerator.getInstance();
 *       int anInt = randNumGen.nextInt(4);
 *       double aDouble = randNumGen.nextDouble();
 *    </code></pre>
 *
 *  <p>
 *  The original <code>RandNumGenerator</code> class, which provided
 *  a getInstance method but did not extend <code>Random</code>, was
 *  copyright&copy; 2002 College Entrance Examination Board
 *  (www.collegeboard.com).
 *
 *  @author Alyce Brady
 *  @version 1 December 2003
 **/
public class RandNumGenerator extends Random
{
    // Class Variable: Only one generator is created by this class.
    private static RandNumGenerator theRandNumGenerator =
                                                new RandNumGenerator();

    /* Private constructor ensures that a RandNumGenerator is ONLY
     * acquired through the getInstance method.
     */
    private RandNumGenerator()
    {
    }

    /** Returns a random number generator.
     *  Always returns the same <code>RandNumGenerator</code> object to
     *  provide a better sequence of random numbers.
     **/
    public static RandNumGenerator getInstance()
    {
        return theRandNumGenerator;
    }

  // Pretend to redefine key inherited Random methods so that they show
  // up as part of the RandNumGenerator class documentation.

    /** Sets the seed of this random number generator using a single
     *  <code>long</code> seed.  For more technical details, see the
     *  class documentation for the <code>Random</code> class.
     *     @return a pseudorandom, uniformly distributed <code>boolean</code>
     *             value from this random number generator's sequence 
     *     @see java.util.Random#setSeed(long)
     **/
    public void setSeed(long seed)
    {
        super.setSeed(seed);
    }

    /** Returns a pseudorandom, uniformly distributed <code>boolean</code>
     *  value from this random number generator's sequence.  The  values
     *  <code>true</code> and <code>false</code> are produced with
     *  (approximately) equal probability. For more technical details,
     *  see the class documentation for the <code>Random</code> class.
     *     @return a pseudorandom, uniformly distributed <code>boolean</code>
     *             value from this random number generator's sequence 
     *     @see java.util.Random#nextBoolean
     **/
    public boolean nextBoolean()
    {
        return super.nextBoolean();
    }

    /** Returns a pseudorandom, uniformly distributed <code>double</code>
     *  value between <code>0.0</code> and <code>1.0</code> from this
     *  random number generator's sequence.  For more technical details,
     *  see the class documentation for the <code>Random</code> class.
     *     @return a pseudorandom, uniformly distributed <code>double</code>
     *             value between <code>0.0</code> and <code>1.0</code> from
     *             this random number generator's sequence. 
     *     @see java.util.Random#nextDouble
     **/
    public double nextDouble()
    {
        return super.nextDouble();
    }

    /** Returns a pseudorandom, uniformly distributed <code>int</code> value
     *  between <code>0</code> (inclusive) and the specified value (exclusive),
     *  drawn from this random number generator's sequence.  For more
     *  technical details, see the class documentation for the
     *  <code>Random</code> class.
     *     @param n the bound on the random number to be returned.  
     *              Must be positive. 
     *     @return a pseudorandom, uniformly distributed <code>int</code>
     *             value between 0 (inclusive) and <code>n</code> (exclusive). 
     *     @throws IllegalArgumentException <code>n</code> is not positive 
     *     @see java.util.Random#nextInt(int)
     **/
    public int nextInt(int n)
    {
        return super.nextInt(n);
    }
}
