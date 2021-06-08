
/**

<B>File:</B> ReflectiveVisitor.java

@version 1.0
@author Neal Elzenga
@since Build P1 - March 3, 2001

**/

package com.armygame.recruits.utils;

import java.lang.reflect.*;
import java.util.*;

/**

An <code>ReflectiveVisitor</code> defines an interface for Visitor objects
that uses reflection to allow visitors to dispatch to whatever visit
methods that an object contains.  This interface was taken from the
article "Reflecting on the Visitor Design Pattern" from the JavaReport,
March 2001, Volume 6, Number 3

@version 1.0
@author Neal Elzenga
@since Build P1

**/

public interface ReflectiveVisitor extends Visitor {

  /**
   * visit
   * Implements an object independent visit() method from the Visitor
   * design pattern that uses reflection to call the appropriate visit
   * method in the concrete visitor
   * @param obj - The object being visited
   * @returns void
   * @see concrete visitors
  **/
  public void visit( Object obj );

  public void operate( Object[] args );

  public void reset();

  /**

  An <code>CommonImplementation</code> object is a static inner class
  that lets us put the getMethod() method of the RelfectiveVisitor
  pattern in the interface so it need only be coded once

  @version 1.0
  @author Neal Elzenga
  @since Build P1

  **/
  static class CommonImplementation {

    static class AcceptCache {
      private static HashSet myCache = new HashSet();

      public static void addToCache( String key ) {
        if ( myCache.add( key ) ) {
//          System.out.println( "Caching -> " + key );

        }
      }

      public static boolean isCached( String key ) {
        boolean Result = false;
        Result = myCache.contains( key );
//         if ( Result == true ) {
//           System.out.println( "Cache hit" );
//         }
        return Result;
      }

    } // class AcceptCache

    static class MethodCache {
      private static HashMap myCache = new HashMap();

      public static void addToCache( String key, Method method ) {
        myCache.put( key, method );
      }

      public static Method get( String key ) {
        return (Method) myCache.get( key );
      }

      public static boolean isCached( String key ) {
        boolean Result = false;
        Result = myCache.containsKey( key );
        return Result;
      }

    } // class MethodCache


    public static void invokeVisitor( Class theclass, Class owner, Class visitorclass, Visitor visitor, Object visitable ) {

      Class NewClass = theclass;
      Class AcceptClass = theclass;
      Class OrigOwner = owner;
      Class Owner = owner;
      Method m = null;

      // Short circuit to Cache
      String HashCandidate = theclass.toString();
      if ( AcceptCache.isCached( HashCandidate )  ) {
        while ( m == null && AcceptClass != Object.class ) {
          try {
            m = AcceptClass.getMethod( "accept", new Class[] { visitorclass} );
          } catch ( Exception e ) {
            AcceptClass = AcceptClass.getSuperclass();
            continue;
          }
          try {
            m.invoke( visitable, new Object[] { visitor} );
          } catch ( IllegalArgumentException e ) {
            System.out.println( "Illegal Argument" );
          } catch ( IllegalAccessException e ) {
            System.out.println( "Illegal Access" );
          } catch ( Exception e ) {
            System.out.println( "Method -> " + m.toString() );
          }
        }
      } else {
        if ( MethodCache.isCached( HashCandidate ) ) {
          System.out.println( "Using cached visitor -> " + HashCandidate );
          m = MethodCache.get( HashCandidate );
          try {
            m.invoke( visitor, new Object[] { visitable } );
          } catch ( Exception e ) {
            System.out.println( "visit Method -> " + m.toString() );
          }
        } else {

            // Try the superclasses
            while ( m == null && NewClass != Object.class ) {
              String method = NewClass.getName();
              method = method.substring( method.lastIndexOf( "$" ) + 1 );
              method = "visit" + method.substring( method.lastIndexOf( "." ) + 1 );

              try {
                m = Owner.getMethod( method, new Class[] { NewClass} );
                m.invoke( visitor, new Object[] { visitable} );
                MethodCache.addToCache( theclass.toString(), m );
              } catch ( NoSuchMethodException e ) {
                NewClass = NewClass.getSuperclass();
              } catch ( Exception e ) {

              } // try

            } // while

            // Try the interfaces
            if ( NewClass == Object.class ) {
              Class[] interfaces = theclass.getInterfaces();
              for ( int i = 0; i < interfaces.length; i++ ) {
                String method = interfaces[i].getName();
                method = "visit" + method.substring( method.lastIndexOf( "." ) + 1 );

                try {
                  m = (theclass.getClass()).getMethod( method, new Class[] { interfaces[i]} );
                  m.invoke( visitor, new Object[] { visitable} );
                  MethodCache.addToCache( theclass.toString(), m );
                } catch ( NoSuchMethodException e ) {
                  // Tough luck

                } catch ( Exception e ) {

                }

              } //for

            }// if

            if ( m == null ) {

              // We assume we can call the accept() method
              while ( m == null && AcceptClass != Object.class ) {
                try {
                  m = AcceptClass.getMethod( "accept", new Class[] { visitorclass} );
                } catch ( Exception e ) {
                  AcceptClass = AcceptClass.getSuperclass();
                  continue;
                }
                try {
                  m.invoke( visitable, new Object[] { visitor} );
                  AcceptCache.addToCache( new String( theclass.toString() ) );
                } catch ( IllegalArgumentException e ) {
                  System.out.println( "Illegal Argument" );
                } catch ( IllegalAccessException e ) {
                  System.out.println( "Illegal Access" );
                } catch ( Exception e ) {
                  System.out.println( e.toString() );
                  System.out.println( "Method -> " + m.toString() );
                  System.out.println( "visitable " + visitable.toString() );
                  System.out.println( "visitor " + visitor.toString() );
                  System.out.println( "theclass " + theclass.toString() );
                  System.out.println( "owner " + owner.toString() );
                  System.out.println();
                }
              }

            } // if

          } // else

        } // else if

      } // invokeVisitor( Class theclass )

    } // inner static class CommonImplementation

    /**

    An <code>TestHarness</code> object uses an inner class to encapsulate
    the main() function.  This allows the test harness functionality of the
    class to be accessed - Run as EncolsingClass$TestHarness - but putting
    the test code for main() in a separate class file (Enclosing$TestHarness)
    so that the test code can be left out of the JAR package if desired

    @version 1.0
    @author Neal Elzenga
    @since Build P1

    **/
    public static class TestHarness {

      /**
       * main
       * @param args - Command line arguments as String[]
      **/
      public static void main( String[] args ) {

        // Define a Visitable class
        class VisitableListNode implements Visitable {

          private ArrayList myChildren;
          private String myName;

          public VisitableListNode( String name ) {
            myChildren = new ArrayList();
            myName = name;
          }

          public void addChild( VisitableListNode child ) {
            myChildren.add( child );
          }

          public String getName() {
            return myName;
          }

          public void accept( Visitor visitor ) {
            visitor.visit( myChildren );
          }

        } // inner class VisitableList

        // Define a concrete Visitor class
        class ConcreteVisitor implements ReflectiveVisitor {

          public void operate( Object[] args ) {
          }

          public void reset() {
          }

          public void visit( Object obj ) {
            try {
              CommonImplementation.invokeVisitor( obj.getClass(), this.getClass(), Class.forName("Visitor"), this, obj );
              // CommonImplementation.getMethod( obj.getClass(), this.getClass() ).invoke( this, new Object[] { obj } );
            } catch ( Exception e ) {
              System.out.println( "Dead Jim" );
            }
          }

          public void visitArrayList( ArrayList list ) {
            Iterator itr = list.iterator();
            while ( itr.hasNext() ) {
              VisitableListNode node = (VisitableListNode) itr.next();
              if ( node instanceof Visitable ) {
                node.accept( this );
              }
            }
          }
        }


        // Begin test code

        VisitableListNode A = new VisitableListNode( "A" );
        VisitableListNode AA = new VisitableListNode( "AA" );
        VisitableListNode AAA = new VisitableListNode( "AAA" );
        VisitableListNode AAB = new VisitableListNode( "AAB" );

        AA.addChild( AAB );

        VisitableListNode B = new VisitableListNode( "B" );
        VisitableListNode BA = new VisitableListNode( "BA" );
        VisitableListNode BB = new VisitableListNode( "BB" );

        B.addChild( BA );

        VisitableListNode C = new VisitableListNode( "C" );

        A.addChild( AA );
        A.addChild( B );
        AA.addChild( AAA );
        A.addChild( C );
        B.addChild( BB );

        ConcreteVisitor V = new ConcreteVisitor();

        V.visit( A );

      } // main( String[] args )

    } // inner class TestHarness


  } // interface ReflectiveVisitor


