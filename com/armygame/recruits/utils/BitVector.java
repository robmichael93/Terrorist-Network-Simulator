package com.armygame.recruits.utils;

public class BitVector implements BitOperations {
  protected byte[] myBits;
  protected int mySize;

  public BitVector( int size ) {
    Initialize( size );
  }

  private void Initialize( int size ) {
    mySize = size;
    int WordsInSize = (int) Math.ceil( (double) size / 8.0 );
    myBits = new byte[ WordsInSize ];
  } // BitVector

  public BitVector( BitVector initialBits ) {
    SetVector( initialBits );
  }

  public BitVector( int size, int value ) {
    Initialize( size );
    for( int i = 0; i < size; i++ ) {
      if ( ( (1 << i) & value ) > 0 ) {
        SetBit( i );
      } else {
        ClearBit( i );
      }
    }
  }

  public BitVector( int size, BitVector copySrc ) {
    Initialize( size );
    System.arraycopy( copySrc.myBits, 0, myBits, 0, myBits.length );
  }

  public int GetSize() {
    return mySize;
  }

  public void Set() {
    for( int i = 0; i < mySize; i++ ) {
      SetBit( i );
    }
  }

  public void Clear() {
    for( int i = 0; i < mySize; i++ ) {
      ClearBit( i );
    }
  }

  public BitVector NthMatch( BitVector compareVector ) {
    // Return the bit vector of length l consisting of those
    // elements of this vector and the compareVector that match up to
    // position l
    int i;
    for( i = 0; i < mySize; i++ ) {
      if ( GetBit( i ) != compareVector.GetBit( i ) )
        break;
    }
    return new BitVector( i, compareVector );
  }


  public void SetVector( BitVector newVector ) {
    mySize = newVector.mySize;
    myBits = new byte[ (int) Math.ceil( (double) mySize / 8.0 ) ];
    System.arraycopy( newVector.myBits, 0, myBits, 0, newVector.myBits.length );
  }

  public void SetBit( int bitNum ) {
    // What word is this bit in?
    int Word = bitNum / 8;
    int Bit = bitNum % 8;
    myBits[Word] |= (1 << Bit);
  }

  public void ClearBit( int bitNum ) {
    int Word = bitNum / 8;
    int Bit = bitNum % 8 ;
    myBits[Word] &= ~(1 << Bit);
  }

  private String ByteToBits( byte testByte ) {
    String Result = "";
    for( int BitPos = 0; BitPos < 8; BitPos++ ) {
      if ( ( (1 << BitPos) & testByte ) > 0 ) {
        Result += "1";
      } else {
        Result += "0";
      }
    }
    return Result;
  }

  public boolean IsSet( int bitNum ) {

    if ( bitNum >= mySize )
      return false;

    int Word = bitNum / 8;
    int Bit = bitNum % 8;

    // byte CompareByte = myBits[Word];
    // byte MaskByte = (byte) (1 << Bit );
    // System.out.println( "IsSet " + bitNum + " comparing " + ByteToBits( CompareByte ) + " to " + ByteToBits( MaskByte ) + " yields " + ( ( ( myBits[Word] & (1 << Bit) ) > 0 ) ? true : false ) );

    return( ( ( myBits[Word] & (1 << Bit) ) > 0 ) ? true : false );
  }

  public int GetBit( int bitNum ) {
    return( ( IsSet( bitNum ) ) ? 1 : 0 );
  }

  public boolean equals( BitVector arg ) {
    boolean Result = false;

    Result = ( mySize == arg.mySize );

    if ( Result == true ) {
      int BytesToCompare = ( mySize / 8 ) + ( ( ( mySize % 8 ) > 0 ) ? 1 : 0 );

      for( int i = 0; i < BytesToCompare; i++ ) {
        Result &= ( myBits[i] == arg.myBits[i] );
        if( !Result ) {
          break;
        }
      }
    }

    return Result;
  }

  public void And( BitVector bV ) {
    int Leftovers = mySize % 8;
    int BytesToAnd = ( mySize / 8 ) + ( ( Leftovers > 0 ) ? 1 : 0 );

    for( int i = 0; i < BytesToAnd; i++ ) {
      myBits[ i ] &= bV.myBits[ i ];
    }
  }

  public void or( BitVector bV ) {
    int Leftovers = mySize % 8;
    int BytesToOr = ( mySize / 8 ) + ( ( Leftovers > 0 ) ? 1 : 0 );

    for( int i = 0; i < BytesToOr; i++ ) {
      myBits[ i ] |= bV.myBits[ i ];
    }
  }

  public void Xor( BitVector bV ) {
    int Leftovers = mySize % 8;
    int BytesToXor = ( mySize / 8 ) + ( ( Leftovers > 0 ) ? 1 : 0 );

    for( int i = 0; i < BytesToXor; i++ ) {
      myBits[ i ] ^= bV.myBits[ i ];
    }
  }

  public void Not() {
    int Leftovers = mySize % 8;
    int BytesToNot = ( mySize / 8 ) + ( ( Leftovers > 0 ) ? 1 : 0 );

    for( int i = 0; i < BytesToNot; i++ ) {
      myBits[ i ] = (byte) ~myBits[ i ];
    }
  }

  public boolean TruthValue() {
    int Leftovers = mySize % 8;
    int BytesToCheck = ( mySize / 8 ) + ( ( Leftovers > 0 ) ? 1 : 0 );

    boolean Result = false;

    for( int i = 0; i < BytesToCheck; i++ ) {
      if ( ( myBits[ i ] & (byte) 0xff ) != 0 ) {
        Result = true;
        break;
      }
    }

    return Result;
  }

  public BitVector Slice( int startPos, int endPos ) {
    int Size = endPos - startPos;
    BitVector Result = new BitVector( Size );

    for( int i = 0; i < Size; i++ ) {
      if ( IsSet( startPos + i ) ) {
        Result.SetBit( i );
      } else {
        Result.ClearBit( i );
      }
    }

    return Result;
  }

  public String toString() {
    String Result = "";

    for(int i = 0; i < mySize; i++ ) {
      Result += ( IsSet( i ) ? "1" : "0" );
    }

    return Result;
  }

} // class BitVector
