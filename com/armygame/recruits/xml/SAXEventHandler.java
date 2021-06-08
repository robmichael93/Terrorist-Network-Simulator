/**
 * Title: SAXEventHandler
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author Neal Elzenga
 * @version 1.0
 * @since Build 1.0
 */

package com.armygame.recruits.xml;

import org.xml.sax.Attributes;

/**
 * The <code>SAXEventHandler</code> interface describes the dispatch interface that concrete
 * <code>DocumentParseState</code> instance objects use to be told of SAX events from
 * a <code>SAXDocumentParser</code>.  It's modeled after the SAX ecent handlers in the
 * <code>ContentHandler</code> interface.
 * @see org.xml.sax.ContentHandler
 */
public interface SAXEventHandler {

  /**
   * @see org.xml.sax.ContentHandler#startDocument
   */
  public void StartDocument();

  /**
   * @see org.xml.sax.ContentHandler#endDocument
   */
  public void EndDocument();

  /**
   * @see org.xml.sax.ContentHandler#startElement
   */
  public void StartElement( String uri, String localName, String qName, Attributes atts );

  /**
   * @see org.xml.sax.ContentHandler#endElement
   */
  public void EndElement( String uri, String localName, String qName );

  /**
   * @see org.xml.sax.ContentHandler#characters
   */
  public void Characters( char[] ch, int start, int length );

}