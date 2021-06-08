package com.armygame.recruits.xml.parsestates;

/**
 * Title: ImagePlaceholder
 * Description: Auto generated concrete <code>DocumentParseState</code>
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author Neal Elzenga (Auto Generated)
 * @version 1.0
 */
import org.xml.sax.Attributes;

import com.armygame.recruits.globals.RecruitsPackageConstants;
import com.armygame.recruits.xml.ParserStateMachine;
import com.armygame.recruits.xml.DocumentParseState;
import com.armygame.recruits.xml.ConstructorCall;
import com.armygame.recruits.xml.EndConstructorSentinel;
import com.armygame.recruits.xml.ConstructionMethodCall;
import com.armygame.recruits.xml.EndMethodCallSentinel;
import com.armygame.recruits.xml.MakeArgumentFromStackTopSentinel;
import com.armygame.recruits.locations.LocationMediaPlaceholder;

/**
 * Class for handling XML ImagePlaceholder tags.  This class is a <b>Singleton</b>
 * (see 'Design Patterns' - Gamma et. al)
 * <b>IMPORTANT NOTE:</b> This class was auto generated by the
 * <b>MakeStates</b> tool, and requires user customization of the
 * following methods:
 * <code>InitializeState()</code>, <code>StartElement()</code>,
 * <code>EndElement</code>, <code>Characters</code>
 */
public class ImagePlaceholder extends DocumentParseState implements RecruitsPackageConstants {

  /**
   * The single ImagePlaceholder
   */
  private static ImagePlaceholder theirInstance = null;

  /**
   * The constructor is <code>private</code> to enforce <b>Singleton</b>
   * behavior
   */
  private ImagePlaceholder() {
    super();
  }

  /**
   * Return the ImagePlaceholder <b>Singleton</b>
   * @return The ImagePlaceholder <b>Singleton</b>
   */
  public static ImagePlaceholder Instance() {
    if( theirInstance == null ) {
      theirInstance = new ImagePlaceholder();
    }
    return theirInstance;
  }

  /**
   * This state is called when a <b>&lt;ImagePlaceholder&gt;</b> element is parsed.
   * @param context The <code>ParserStateMachine</code> that is managing our parsing
   */
  public void InitializeState( ParserStateMachine context ) {
    // Add this placeholder to the location template
    context.AddConstructionTask( new ConstructionMethodCall( "AddImagePlaceholder", new Class[] { LocationMediaPlaceholder.class } ) );
    context.AddConstructionTask( new ConstructorCall( LOCATIONS_PACKAGE, "LocationImagePlaceholder" ) );
  }


  /**
   * This state is called when we get the end element for state ImagePlaceholder
   * @param context The <code>ParserStateMachine</code> that is managing our parsing
   * @param uri
   * @param localName
   * @param qName
   * @see org.xml.SAX#endElement()
   */
  public void EndElement( ParserStateMachine context, String uri, String localName, String qName ) {
    context.AddConstructionTask( new EndConstructorSentinel() );
    context.AddConstructionTask( new MakeArgumentFromStackTopSentinel() );
    context.AddConstructionTask( new EndMethodCallSentinel() );
  }

}