/**
 * Title: SAXDocumentParser
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author Neal Elzenga
 * @version 1.0
 * @since Build 1.0
 */

package com.armygame.recruits.xml;


/**
 * This class implements a default from-XML object serializer.  The default behavior is
 * to read a specified XML file using a <code>SAXDocumentParser</code> and 
 * <code>ParserStateMachine</code>.  The default behavior returns a single object using
 * <code>ParserStateMachine.GetBuiltObject()</code>.
 */
  
public class DefaultXMLSerializer {

  /**
   * Parses the specified file and returns all the objects the parse creates
   * @param file The full path file name to parse
   * @return The array of parsed objects
   */
  public Object[] Parse( String file ) {
    ParserStateMachine StateMachine = new ParserStateMachine();
    SAXDocumentParser Parser = new SAXDocumentParser( StateMachine );
 
    try {
      Parser.ProcessDocument( file );
    } catch( Exception parseException ) {
      parseException.printStackTrace();
    }

    return StateMachine.GetBuiltObjects();
  
  }

  /**
   * Parses the specified file using the supplied object as the seed for setting the fields of
   * this object from the XML tags
   * @param readObject The object to read into.  This object is used to seed the parser by setting it as the top item on the parser's construction stack.  This allows the object to have some state set external to the parser before being parsed - with the proviso that parsing the obejct could overwrite the seed state.
   * @param file The full path file name to parse
   */
  public void Parse( Object readObject, String file ) {
    ParserStateMachine StateMachine = new ParserStateMachine( readObject );
    SAXDocumentParser Parser = new SAXDocumentParser( StateMachine );
 
    try {
      Parser.ProcessDocument( file );
    } catch( Exception parseException ) {
      parseException.printStackTrace();
    }
  }

}
