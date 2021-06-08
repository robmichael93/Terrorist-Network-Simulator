package com.armygame.recruits.globals;

import java.util.Random;


/**
 * Provides a set of services for use of random numbers in the game
 */
public class RecruitsRandom {

  /**
   * The game's random number generator
   */
  private static Random theirGenerator = new Random();

  /**
   * Returns an array index in the range [0-rangeSize] (inclusive)
   * @return The index in the range [0-rangeIndex] (includsive)
   */
  public static int RandomIndex( int rangeSize ) {
    int x = theirGenerator.nextInt( rangeSize );
    return x;
  }

}
