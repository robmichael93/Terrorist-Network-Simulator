package com.armygame.recruits.animationplayer;

/**
 * Type-safe nnumeration that encodes the ID of an Animation Player operation to
 * perform as the result of a client request.  Includes an <CODE>equals()</CODE>
 * method for comparison of request IDs
 */
public class OperationRequest {

  /**
   * Holds the ID of the operation request
   */
  private final int myRequestCode;

  /**
   * Constructs a new operation request with the specified code.  The constructor is
   * <B>private</B>, so the only <CODE>OperationRequest</CODE> objects available are
   * those declared <CODE>public static final OperationRequest</CODE> below
   *
   * @param requestCode The code for the operation request.  It is up to the implementor to make sure the
   * codes assigned are unique
   */
  private OperationRequest( int requestCode ) {
    myRequestCode = requestCode;
  }

  /**
   * Provides type-safe comparison of <CODE>OperationRequest</CODE> objects based
   * on their code
   *
   * @param rhs
   * @return
   */
  public boolean equals( OperationRequest rhs ) {
    return ( myRequestCode == rhs.myRequestCode );
  }

  /**
   * Indicates there is no pending operation to perform
   */
  public static final OperationRequest NOOP = new OperationRequest( 0 );

  /**
   * Operation Request indicating the client wants an update to the Location the player
   * is currently displaying
   */
  public static final OperationRequest SET_LOCATION = new OperationRequest( 1 );

}

