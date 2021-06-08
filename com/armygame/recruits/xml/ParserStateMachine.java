/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.0
 */

package com.armygame.recruits.xml;

import com.armygame.recruits.xml.parsestates.NewDocument;

import org.xml.sax.Attributes;
import java.util.Stack;
import java.util.ArrayList;
import java.util.HashMap;
import java.lang.Number;
import java.lang.reflect.Method;

import com.armygame.recruits.globals.MasterVersion;
import com.armygame.recruits.utils.RecruitsVersion;

/**
 * A <code>ParserStateMachine</code> acts in the role of a <b>context</b> in the <b>State</b> design
 * pattern as described in "Design Patterns" (Gamma et. al.).  The <code>ParserStateMachine<code>
 * manages the current state of an XML document parsing session, including the provision to change
 * states.  It delegates the handling of the SAX events defined in the <code>SAXEventHandler</code>
 * interface to the current instance of the abstract <code>DocumentParseState</code> object
 * representing the current state.  Using the <b>State</b> design pattern, the current state of the
 * <code>ParserStateMachine</code> may be changed dynaically, but since all concrete parse states
 * derived from <code>DocumentParseState</code> implement the same <code>SAXEventHandler</code> interface,
 * the effect is correct interpretation of the SAX event in the context of the current state.
 * The <code>ParserStateMachine</code> uses a <code>ReflectiveStateDispatcher</code> to manage state
 * changes dervied from the text of the element tags in the document using Java's <code>java.lang.reflect</code>
 * package to convert <code>String</code> names into <code>Class</code> names.
 *
 * This class is a base class for custom <copde>ParserStateMachine</code>s that encapsulate knoweldge for
 * building other Recruits system objects by integration and interpretation of the values parsed by the
 * state machine.
 *
 * @see SAXEventHandler
 * @see ReflectiveStateDispatcher
 * @see DocumentParseState
 */
public class ParserStateMachine implements SAXEventHandler {

  /**
   * This inner class maintains a buffer containing the current set of parsed characters from
   * one or more SAX <code>characters()</code> events.  It provides functions for re-interpreting the
   * characters as the fundamental data types used in the Recruits.
   */
  private class CharacterBuffer {

    private StringBuffer myCharacterBuffer;

    public CharacterBuffer() {
      myCharacterBuffer = new StringBuffer();
    }

    public void ClearCharacterBuffer() {
      myCharacterBuffer.delete( 0, myCharacterBuffer.length() );
    }

    public void AddCharsToBuffer( char[] chars, int start, int length ) {
      myCharacterBuffer.append( chars, start, length );
    }

    public String GetCharBuffer() {
      return myCharacterBuffer.toString();
    }

    public String CharBufferToString() {
      return GetCharBuffer();
    }

    public int CharBufferToInt() {
      return ( new Integer( myCharacterBuffer.toString() ) ).intValue();
    }

    public byte CharBufferToByte() {
      return ( new Byte( myCharacterBuffer.toString() ) ).byteValue();
    }

    public boolean CharBufferToBoolean() {
      return ( new Boolean( myCharacterBuffer.toString() ) ).booleanValue();
    }

  }

  /**
   * This class is used to build objects from the execution of the construction tasks
   * in the <code>ConstructionTaskList</code>
   */
  private class ObjectBuilder {

    /**
     * This inner class provides a (name,value) memory for use in building objects.  Its intended use is for
     * saving an XML item seen earlier in a parse for use later in that parse
     */
    private class ParseMemory {

      /**
       * This holds the (name,value) pairs as (<code>String</code>, <code>String</code>).
       * <b>Note:</b>  We hold ALL values as <code>String</code>s, meaning that if you want in interpret
       * the value as another Java tyep you have to re-cast it yourself.  The good news is that we are holding the
       * characters returned from the SAX <code>characters()</code> event intact, so the original info that
       * would have been available inline for conversion are there.
       */
      private HashMap myMemory;

      /**
       * Constructs a new, empty memory
       */
      public ParseMemory() {
        myMemory = new HashMap( 20 );  // Initialize for a modest amount of things to remember
      }

      /**
       * Adds a (name,value) pair to the memory.
       * <b>Note:</b> We REPLACE any item we already knew by the given name, losing the previous version!
       * @param name The name of the thing to remember - typically the normalized XML tag name of the item
       * @param value The <code>String</code> value to remember, typically the cumulative characters from a SAX <code>characters()</code> event
       */
      public void Add( String name, String value ) {

        // If it exists already, replace it
        myMemory.remove( name );
        myMemory.put( name, value );
      }

      /**
       * Clears the memory of all values
       */
      public void Erase() {
        myMemory.clear();
      }

      /**
       * Retrieves the value for the given key
       * @param name The key of the value we seek
       * @return The matching value as a <code>String</code> if its in the map, otherwise <code>null</code>
       */
      public String GetValueFor( String name ) {
        return (String) myMemory.get( name );
      }

    }

    /**
     * This is used as a stack frame scratchpad for building up object from
     * Constructor and Method calls
     */
    private Stack myStackFrames;

    /**
     * This is used as a stack for the objects returned from a parse.  This stack allows us to return
     * multiple objects from a call.  We use a separate stack for returned objects from the
     * <code>myStackFrames</code> stack because it gives us a way to store off intermediate objects
     * we are composing from the middle of the parse.  Moving intermediate objects from the
     * <code>myStackFrames</code> stack to the <code>myReturnStack</code> stack gets intermediate
     * objects out of the way of the main (or current) stack object's way.
     */
    private Stack myReturnStack;

    /**
     * Holders the method we are currently operating on
     */
    private Stack myPendingMethodCall;

    /**
     * The list of arguments to the pending method call
     */
    private ArrayList myArgumentList;

    /**
     * A memory of parsed (name,value) pairs for things we want to remember from earlier in a parse
     */
    private ParseMemory myParseMemory;

    /**
     * Initialize the object builder
     */
    public ObjectBuilder() {
      myStackFrames = new Stack();
      myReturnStack = new Stack();
      myPendingMethodCall = new Stack();
      myArgumentList = new ArrayList();
      myParseMemory = new ParseMemory();
    }

    public ObjectBuilder( Object initialObject ) {
      myStackFrames = new Stack();
      AddNewObject( initialObject );
      myReturnStack = new Stack();
      myPendingMethodCall = new Stack();
      myArgumentList = new ArrayList();
      myParseMemory = new ParseMemory();
    }

    public void AddNewObject( Object newObj ) {
      myStackFrames.push( newObj );
    }

    public void SetPendingMethodCall( Method currentMethod ) {
      myPendingMethodCall.push( currentMethod );
    }

    public Class GetLastClass() {
      return myStackFrames.peek().getClass();
    }

    public Object GetLastObject() {
      return myStackFrames.peek();
    }

    public void NewArgumentList() {
      myArgumentList.clear();
    }

    public void AddArgument( Object arg ) {
      // System.out.println( "Adding arg " + arg.toString() );
      myArgumentList.add( arg );
    }

    public void AddArgumentFromStack() {
      // System.out.println( "Adding arg from stack" + myStackFrames.peek().toString() );
      myArgumentList.add( myStackFrames.pop() );
    }

    /**
     * Remember something we've parsed for later use
     * @param name The name of the thing we want to remember
     * @param value The value of the thing we want to remember
     */
    public void Remember( String name, String value ) {
      myParseMemory.Add( name, value );
    }

    /**
     * Retrieve something we've remembered by name as a <code>String</code>
     * @param name The name of the thing we want
     * @return The thing we want
     */
    public String Recall( String name ) {
      return myParseMemory.GetValueFor( name );
    }

    /**
     * Forget everything we've remembered about the parse
     */
    public void ClearParseMemory() {
      myParseMemory.Erase();
    }

    /**
     * Remember the current contents of the character buffer under the given name
     * @param name The name to save the value under
     */
    public void RememberTagValue( String name ) {
      Remember( name, myCharacterBuffer.CharBufferToString() );
    }

    /**
     * Recall a value we've saved
     */
    public String RecallTagValue( String name ) {
      return myParseMemory.GetValueFor( name );
    }

    /**
     * Move the current frame stack item to the return stack
     */
    public void MoveFrameObjectToReturnStack() {
      myReturnStack.push( myStackFrames.pop() );
    }

    public void InvokeMethod() {
      // System.out.println( "Invoking method with " + myArgumentList.size() + " Arguments" );
      try {
        ( (Method) myPendingMethodCall.pop() ).invoke( GetLastObject(), myArgumentList.toArray() );
        // System.out.println( "Clearing arguments list" );
        myArgumentList.clear(); //?
      } catch ( Exception e ) {
        e.printStackTrace();
      }
    }

    public void CheckMasterVersion( RecruitsVersion masterVersion ) {
      // We assume the last object on the stack is the version just read
      if ( masterVersion.equals( (RecruitsVersion) GetLastObject() ) ) {
        //System.out.println( "Read version compatible with Master version" );
      } else {
        System.out.println( "Read version INCOMPATIBLE with Master version" );
      }
      myStackFrames.pop();
    }

    /**
     * Return the top object on the stack of parsed objects
     * @return The top built object
     */
    public Object GetBuiltObject() {
      return myReturnStack.pop();
    }

    /**
     * Returns all the built objects on the stack of parsed objects
     * @return An array of all the objects on the stack of parsed objects
     */
    public Object[] GetBuiltObjects() {
      Object[] Result = new Object[ myReturnStack.size() ];
      int Index = 0;

      while( !myReturnStack.isEmpty() ) {
        Result[ Index++ ] = myReturnStack.pop();
      }

      return Result;
    }

  }

  /**
   * The list of task resulting from a parse
   */
  private ConstructionTaskList myConstructionTasks;

  /**
   * This is the class used to build the target object of the XML file
   * via constructor and method calls
   */
  private ObjectBuilder myObjectBuilder;

  /**
   * This <code>Stack</code> is used to allow nesting and un-nesting of operations on parse states.
   * This allows returning to a state after receipt of an <code>EndElement</code> event on its
   * sub-states.  Thus, the sub-states can be used to collect values, and pop back to the parent state
   * for consolidation or interpretation of those values
   */
  private Stack myStates;

  /**
   * The <code>ReflectiveStateDispatcher</code> used to perform state transitions based on the name of the
   * tag parsed rather than an object reference by using Java's <code>java.lang.reflect</code> capabilities
   */
  private static ReflectiveStateDispatcher theirStateDispatcher = new ReflectiveStateDispatcher();

  /**
   * This buffer is used to concatenate the results of multiple SAX <code>characters</code> events
   */
  private CharacterBuffer myCharacterBuffer;

  /**
   * Initializes the state machine
   */
  public ParserStateMachine() {
    // The NewDocument state is a special sentinel state that all parsers use to kick things off
    myCharacterBuffer = new CharacterBuffer();
    myStates = new Stack();

    // The NewDocument state is a special sentinel state that all parsers use to kick things off
    myStates.push( NewDocument.Instance() );
    myConstructionTasks = new ConstructionTaskList();
    myObjectBuilder = new ObjectBuilder();
  }

  public ParserStateMachine( Object readObject ) {
    // The NewDocument state is a special sentinel state that all parsers use to kick things off
    myCharacterBuffer = new CharacterBuffer();
    myStates = new Stack();

    // The NewDocument state is a special sentinel state that all parsers use to kick things off
    myStates.push( NewDocument.Instance() );
    myConstructionTasks = new ConstructionTaskList();
    myObjectBuilder = new ObjectBuilder( readObject );
  }

  /**
   * Return the object parsed
   * @return The built object
   */
  public Object GetBuiltObject() {
    return myObjectBuilder.GetBuiltObject();
  }


  /**
   * Return ALL the built objects
   * @return All the built objects
   */
  public Object[] GetBuiltObjects() {
    return myObjectBuilder.GetBuiltObjects();
  }

  /**
   * Helper routine to get the current state from peeking the stack
   */
  private DocumentParseState CurrentState() {
    return (DocumentParseState) myStates.peek();
  }

  public void AddConstructionTask( ConstructionTaskItem newTask ) {
    myConstructionTasks.AddItem( newTask );
  }

  /**
   * Execute the <code>ConstructionTaskList</code>
   */
  public void BuildObjects() {
    myConstructionTasks.Execute( this );
  }

  public void AddNewObject( Object newObj ) {
    myObjectBuilder.AddNewObject( newObj );
  }

  public void InvokeMethod() {
    myObjectBuilder.InvokeMethod();
  }

  public Class GetLastClass() {
    return myObjectBuilder.GetLastClass();
  }

  public void SetPendingMethodCall( Method pendingMethod ) {
    myObjectBuilder.SetPendingMethodCall( pendingMethod );
  }

  public void CheckMasterVersion() {
    myObjectBuilder.CheckMasterVersion( MasterVersion.Instance() );
  }

  public void NewArgumentList() {
    myObjectBuilder.NewArgumentList();
  }

  public void AddArgument( Object arg ) {
    myObjectBuilder.AddArgument( arg );
  }

  public void AddArgumentFromStack() {
    myObjectBuilder.AddArgumentFromStack();
  }

  public void ClearCharacterBuffer() {
    myCharacterBuffer.ClearCharacterBuffer();
  }

  public void AddCharsToBuffer( char[] chars, int start, int length ) {
    myCharacterBuffer.AddCharsToBuffer( chars, start, length );
  }

  public String GetCharBuffer() {
    return myCharacterBuffer.CharBufferToString();
  }

  public int CharBufferToInt() {
    return myCharacterBuffer.CharBufferToInt();
  }

  public byte CharBufferToByte() {
    return myCharacterBuffer.CharBufferToByte();
  }

  public boolean CharBufferToBoolean() {
    return myCharacterBuffer.CharBufferToBoolean();
  }

  public void RememberTagValue( String tagName ) {
    myObjectBuilder.RememberTagValue( tagName );
  }

  public String RecallTagValue( String tagName ) {
    return myObjectBuilder.RecallTagValue( tagName );
  }

  public void StartDocument() {
    CurrentState().StartDocument( this );
  }

  public void EndDocument() {
    CurrentState().EndDocument( this );
    StateHasEnded();
  }

  public void StartElement( String uri, String localName, String qName, Attributes atts ) {
    CurrentState().StartElement( this, uri, localName, qName, atts );
    ClearCharacterBuffer();
  }

  public void EndElement( String uri, String localName, String qName ) {
    CurrentState().EndElement( this, uri, localName, qName );
    StateHasEnded();
  }

  public void Characters( char[] ch, int start, int length ) {
    CurrentState().Characters( this, ch, start, length );
  }


  /**
   * Changes the current state to that passed in.  This routine is intended to be called from the current
   * concrete <code>DocumentParseState</code> object which knows about this <code>ParserStateMachine</code>
   * object from its context
   */
  public void ChangeState( DocumentParseState newState ) {
    myStates.push( newState );
    CurrentState().InitializeState( this );
  }

  /**
   * Changes the current state to the named state using reflection
   */
  public void ChangeState( String newStateTag ) {
    // System.out.println( "New State is " + newStateTag );
    myStates.push( theirStateDispatcher.StateForTag( newStateTag ) );
    CurrentState().InitializeState( this );
  }

  /**
   * Tells this state machine that the current state has ended so we can pop the state stack
   */
  public void StateHasEnded() {
    myStates.pop();
  }

  /**
   * Move the current frame stack item to the return stack
   */
  public void MoveFrameObjectToReturnStack() {
    myObjectBuilder.MoveFrameObjectToReturnStack();
  }

}
