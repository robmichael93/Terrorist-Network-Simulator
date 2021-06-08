package com.armygame.recruits.interaction.generativegrammar;

import com.armygame.recruits.utils.ReflectiveVisitor;
import com.armygame.recruits.utils.Visitor;
import com.armygame.recruits.mediaelements.MediaElement;
import com.armygame.recruits.mediaelements.rawmedia.MediaAsset;
import java.util.*;


public class ConcreteVisitor implements ReflectiveVisitor {

  private ArrayList myPlaylist;
  private int myCutoff;

  public ConcreteVisitor() {
    myPlaylist = new ArrayList();
    myCutoff = 0;
  }

  public void setCutoff( int cutoff ) {
    myCutoff = cutoff;
  }

  public int getCutoff() {
    return myCutoff;
  }

  public void addToPlaylist( MediaAsset asset ) {
    myPlaylist.add( (MediaAsset)(asset.clone()) );
  }

  public void play() {
    ListIterator Itr = (ListIterator)myPlaylist.listIterator();

    while( Itr.hasNext() ) {
      ((MediaAsset)Itr.next()).play();
    }
  }

  public void visit( Object obj ) {
    try {
      ReflectiveVisitor.CommonImplementation.invokeVisitor( obj.getClass(), this.getClass(), Visitor.class, this, obj );
    } catch ( Exception e ) {
      System.out.println( "Could not visit " + obj );
    }
  }

  public void operate( Object[] args ) {
    addToPlaylist( (MediaAsset)args[0] );
  }

  public void reset() {
    myPlaylist.clear();
  }

} // class ConcreteVisitor

