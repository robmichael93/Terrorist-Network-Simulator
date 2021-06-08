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

import java.io.FileReader;

import javax.xml.parsers.SAXParserFactory;
import javax.xml.parsers.SAXParser;

import org.xml.sax.XMLReader;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;
import com.armygame.recruits.globals.ResourceReader;

/**
 * This class is used to parse XML data files and process them according to the logic encapsulated in a
 * <code>ParseStateMachine</code> object used to decode the structure and contents of the XML.  The parser
 * takes its input from a <code>FileReader</code>, its output is determined by the functionality of the
 * associated <code>ParserStateMachine</code>.  The parser uses the Sax 2.0 <code>DefaultHandler</code> interface
 * as the basis for its processing of documents.  The SAX events and paramters it receives are forwarded for
 * interpretation by the <code>ParserStateMachine</code>.
 */
public class SAXDocumentParser extends DefaultHandler {

  /**
   * The <code>ParserStateMachine</code> instance that will interpret SAX events in the context of
   * the current parsing state.
   */
  private ParserStateMachine myStateDispatcher;

  /**
   * Constructs and initializes the <code>ParserStateMachine</code> used to interpret XML documents.
   * @param stateDispatcher The <code>ParserStateMachine</code> used to interpret SAX events from an XML document
   */
  public SAXDocumentParser( ParserStateMachine stateDispatcher ) {
    myStateDispatcher = stateDispatcher;
  }

  public Object GetBuiltObject() {
    return myStateDispatcher.GetBuiltObject();
  }

  /**
   * This workhorse routine initializes a SAX 2.0 parser (<b>Xerces-style</b>) and then reads the requested file
   * and passes the SAX events so generated to the <code>ParserStateMachine</code> for interpretation.
   */
  public void ProcessDocument( String fileName ) throws Exception {

    SAXParserFactory ParserFactory = SAXParserFactory.newInstance();
    SAXParser Parser = ParserFactory.newSAXParser();

    XMLReader Reader = Parser.getXMLReader();
    Reader.setContentHandler( this );

    // InFile = new FileReader( fileName );
    //Reader.parse( new InputSource( InFile ) );
    Reader.parse(new InputSource(new FileReader(fileName)));
//    Reader.parse( new InputSource( ResourceReader.getInputReader(fileName) ) );
    myStateDispatcher.BuildObjects();
  }

  public void startDocument() throws SAXException {
    myStateDispatcher.StartDocument();
  }

  public void endDocument() throws SAXException {
    myStateDispatcher.EndDocument();
  }

  public void startElement( String uri, String localName, String qName, Attributes atts ) {
    myStateDispatcher.StartElement( uri, localName, qName, atts );
  }

  public void endElement( String uri, String localName, String qName ) {
    myStateDispatcher.EndElement( uri, localName, qName );
  }

  public void characters( char[] ch, int start, int length ) {
    myStateDispatcher.Characters( ch, start, length );
  }

}