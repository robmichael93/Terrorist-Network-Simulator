
package com.armygame.recruits.mediaelements.rawmedia;

import java.lang.Cloneable;
import java.util.ArrayList;
import java.util.Iterator;


public class MediaAssetPlaylist implements Cloneable {

  private ArrayList myPhraseList;

  class MediaAssetPlaylistIterator implements Iterator {

    private Iterator myItr;

    public MediaAssetPlaylistIterator() {
      myItr = myPhraseList.iterator();
    }

    // Implement Iterator
    public boolean hasNext() {
      return myItr.hasNext();
    }

    public Object next() {
      return myItr.next();
    }

    public void remove() {
      myItr.remove();
    }

  } // class MediaAssetPlaylistIterator

  public MediaAssetPlaylist() {
    myPhraseList = new ArrayList();
  }

  public void addAsset( MediaAsset asset ) {
    myPhraseList.add( asset );
  }

  public Iterator iterator() {
    return ( new MediaAssetPlaylistIterator() );
  }

  public void clear() {
    myPhraseList.clear();
  }

  public int size() {
    return myPhraseList.size();
  }


  // Implement Cloneable
  public Object clone() {
    Object Result = null;

    try {
      Result = super.clone();

      // Deep copy
      ((MediaAssetPlaylist)Result).myPhraseList = (ArrayList) myPhraseList.clone();
      for( int i = 0; i < myPhraseList.size(); i++ ) {
        ((MediaAssetPlaylist)Result).myPhraseList.set( i, (MediaAsset)((MediaAsset)myPhraseList.get( i )).clone() );
      }
    } catch( CloneNotSupportedException e ) {
    }

    return Result;

  } // Object clone()

} // class MediaAssetPlaylist

