package com.armygame.recruits.globals;

import java.util.ArrayList;


public class Permutations {

  public static ArrayList GenPermutations( String[] a ) {
    ArrayList Result = new ArrayList();
    int Size = a.length;
    int[] c = new int[ Size + 1 ];
    int[] d = new int[ Size + 1 ];
    int j;
    int s;
    int q;

    // P1
    for( int i = 1; i <= Size; i++ ) {
      c[ i ] = 0;
      d[ i ] = 1;
    }

    boolean Running = true;

    while( Running ) {
      // P2
      String[] NewPerm = new String[ Size ];
      for( int x = 0; x < NewPerm.length; x++ ) {
        NewPerm[ x ] = a[ x ];
        System.out.print( NewPerm[ x ] + "," );
      }
      System.out.println();
      Result.add( NewPerm );

      // P3
      j = Size;
      s = 0;

      // P4
      while( Running ) {
        q = c[ j ] + d[ j ];

        if ( q < 0 ) {
          // P7
          d[ j ] = -d[ j ];
          j--;
        } else if ( q == j ) {
          // P6
          if ( j == 1 ) {
            Running = false;
          } else {
            s++;
            d[ j ] = -d[ j ];
            j--;
          }
        } else {
          // P5
          String Temp = a[ ( j - c[ j ] + s ) - 1 ];
          a[ ( j - c[ j ] + s ) - 1 ] = a[ ( j - q + s ) - 1 ];
          a[ ( j - q + s ) - 1 ] = Temp;
          c[ j ] = q;
          break;
        }

      }

    }

    return Result;
  }

}

