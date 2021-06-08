package com.armygame.recruits.playlist;

import java.util.Iterator;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import java.lang.Character;

import com.armygame.recruits.locations.LocationUsage;
import com.armygame.recruits.locations.Location;
import com.armygame.recruits.locations.LocationAnimationPlaceholder;
import com.armygame.recruits.locations.RelativePlacementTable;
import com.armygame.recruits.mediaelements.CastUsage;
import com.armygame.recruits.services.SimpleObjectFIFO;
import com.armygame.recruits.animationplayer.AnimationPlayer;
import com.armygame.recruits.animationplayer.CharacterRoleMap;
import com.armygame.recruits.globals.Permutations;


/**
 * Handles the buffering of the media resources required to execute a <CODE>Playlist</CODE>.
 * This services is implemented as a <CODE>Runnable</CODE> so that it executes in
 * its own <CODE>Thread</CODE>.  This process architecture decouples the caller
 * from the potentially lengthy buffering operation.  The caller can return immediately
 * from the call requesting resource buffering operations, letting the actual buffering
 * run independently in its thread.  By implementing the <CODE>PlaylistExecutor</CODE>
 * interface the caller will be informed via the <CODE>BufferingCompleted()</CODE>
 * callback when the buffering thread has completed its extended buffering operations.
 *
 * @see PlaylistExecutor
 */
public class PlaylistResourceLoader implements Runnable {
  /**
   * Holds a reference to the enclosing <CODE>Playlist</CODE>'s <CODE>CastUsage</CODE>
   * object.  This reference shoulud be set just prior to an actual buffering
   * request.  It should be set to the <CODE>CastUsage</CODE> object that the
   * managing <CODE>Playlist</CODE> has updated prior to any buffering service requests
   */
  private CastUsage myCastUsageSummary;

  /**
   * Holds a reference to the enclosing <CODE>Playlist</CODE>'s <CODE>CharacterUsage</CODE>
   * object.  This reference shoulud be set just prior to an actual buffering
   * request.  It should be set to the <CODE>CharacterUsage</CODE> object that the
   * managing <CODE>Playlist</CODE> has updated prior to any buffering service requests
   */
  private CharacterUsage myCharacterUsageSummary;


  /**
   * Holds a reference to the enclosing <CODE>Playlist</CODE>'s <CODE>LocationUsage</CODE>
   * object.  This reference shoulud be set just prior to an actual buffering
   * request.  It should be set to the <CODE>LocationUsage</CODE> object that the
   * managing <CODE>Playlist</CODE> has updated prior to any buffering service requests
   */
  private LocationUsage myLocationUsageSummary;

  private MediaPlayerContext myMediaPlayers;

  private PlaylistExecutor myInvoker;

  private String myCommand;

  private SimpleObjectFIFO myRequestQueue;

  private boolean myIsRunningFlag;

  public PlaylistResourceLoader( MediaPlayerContext playerContext, PlaylistExecutor invoker ) {
    myMediaPlayers = playerContext;
    myInvoker = invoker;
    myCastUsageSummary = null;
    myCharacterUsageSummary = null;
    myLocationUsageSummary = null;
    myCommand = null;
    myRequestQueue = new SimpleObjectFIFO( 1 );
    myIsRunningFlag = true;
  }

  /**
   * Sets the media resource usage context for buffering operations.  The parameters
   * passed to this call should reflect the owning <CODE>Playlist</CODE>'s latest
   * updates to media usage after all updates to the playlist's <CODE>ActionFrame</CODE>s
   * and before any requests to actually buffer resources.
   *
   * @param castUse The latest summary of cast usage in the encompassing <CODE>Playlist</CODE>
   * @param charUse The latest summary of character usage in the encompassing <CODE>Playlist</CODE>
   * @param locUse The latest summary of location usage in the encompassing <CODE>Playlist</CODE>
   */
  private synchronized void SetUsages( CastUsage castUse, CharacterUsage charUse, LocationUsage locUse ) {
    myCastUsageSummary = castUse;
    myCharacterUsageSummary = charUse;
    myLocationUsageSummary = locUse;
  }

  /**
   * Starts the thread of the resource buffering service.  This method calls the
   * <CODE>BufferResources</CODE> method, which will block pending notification that
   * the usage context has been set with the encompassing <CODE>Playlist</CODE>'s
   * latest usage summarys and that a client (which must implement <CODE>PlaylistExecutor</CODE>)
   * is awaiting a completion callback via its <CODE>BufferingCompleted()</CODE>
   * callback hook
   */
  public void run() {
    try {
      while( myIsRunningFlag ) {
        Object Dummy = myRequestQueue.remove();
        BufferResources();
      }
    } catch( InterruptedException ie ) {
      return;
    }

    //System.out.println( "Resource Loader Exiting" );
    // BufferResources();
  }

  /**
   * This flag is used to cause this thread's synchronization monitor to continue
   * to test a <CODE>wait()</CODE> condition until this thread has been
   * notified that a request to buffer resources has been received
   *
   * @see PlaylistExecutor
   */
  public synchronized void BufferResources() {

    // We get here only if the blocking <CODE>wait()</CODE> above was satisfied - signifying a request to buffer
    // System.out.println( "In PlaylistResourceLoader BufferResources" );
    AnimationPlayer Player = myMediaPlayers.GetAnimationPlayer();
    Player.PreBufferInit();
    // System.out.println( "About to Buffer Images" );
    Player.BufferImages( myCastUsageSummary );
    // System.out.println( "Done Buffer Images" );
    // System.out.println( "About to Buffer AnimationFrames" );
    Player.BufferAnimationFrames( myCharacterUsageSummary );
    // System.out.println( "Done Buffer AnimationFrames" );

    // Inform the client that requested our buffering services that buffering is complete
    myInvoker.BufferingCompleted();
    myIsRunningFlag = false;
    // System.out.println( "Out PlaylistResourceLoader BufferResources" );

  }

  /**
   * This method should be called by the enclosing <CODE>Playlist</CODE> in order to
   * trigger the actual buffering of resources.  Prior to issuing this call to
   * <CODE>Notify()</CODE> the enclosing <CODE>Playlist</CODE> should have updated
   * its usage summaries and informed us of these latest values via a call to
   * <CODE>SetUsages()</CODE>
   */
  public synchronized void Buffer( Playlist pList ) {
    CharacterRoleMap.Instance().Clear();
    // System.out.println( "In PlaylistResourceLoader Buffer" );
    myCommand = "Buffer";
    SetUsages( pList.GetCastUse(), pList.GetCharUse(), pList.GetLocUse() );

    // We need to translate relative animation commands (such as front_point_away(SC) [indicating that
    // the current character should point in the direction of the SC character]) into actual animation
    // names (in this case curran_front_pointleft_bdus_beret if SC is to the left of MC curran).
    // To resolve relative animations we proceed as follows:
    // Traverse the entire playlist.
    // When we encounter a SetLocation() action we use the set of staging placeholders for that location
    // to determine relative positioning.
    // For each PlayAnimation() action in the playlist we replace any relative animation names with the
    // absolute animation name that results from determining the relative placement direction of the
    // actor performing the action against the x-coordinate of the actor being referred to according to the
    // set of placements implied by the current location.

    List ActionFrames = pList.GetActionFrames();
    Iterator FrameItr = ActionFrames.iterator();

    while( FrameItr.hasNext() ) {
      ActionFrame CurrentFrame = (ActionFrame) FrameItr.next();
      if ( CurrentFrame.IsLocation() ) {
        RelativePlacementTable.Instance().ForgetPlacements();
        LocationUsage LocationInfo = CurrentFrame.LocationUsageSummary();
        Location CurrentLocation = LocationInfo.GetNextLocation();
        HashMap Stagings = CurrentLocation.GetAnimationStagings();
        Set StagingNames = Stagings.keySet();
        Iterator StagingItr = StagingNames.iterator();
        while( StagingItr.hasNext() ) {
          LocationAnimationPlaceholder StageLoc = (LocationAnimationPlaceholder) CurrentLocation.GetAnimationStagings().get( (String) StagingItr.next() );
          if ( StageLoc.IsUsed() ) {
            String StagedCharacterRole = StageLoc.RoleName();
            int StagedX = StageLoc.RegistrationPoint().RegX();
            System.out.println( "DDDDDDDDDDDDDDDDDDDDealing with staging for " + StagedCharacterRole + " at " + StagedX );
            CharacterRoleMap.Instance().AddRole( StagedCharacterRole, StageLoc.Uniform(),
                                                 StageLoc.Hat(),
                                                 com.armygame.recruits.StoryEngine.instance().castManager.getCharacterFromRole( StagedCharacterRole ).getActorName() );
            RelativePlacementTable.Instance().AddStaging( StagedCharacterRole, StagedX );

          }
        }
      } else if ( CurrentFrame.IsAnimation() ) {
        CurrentFrame.ResolveAnimationNames( myCharacterUsageSummary );
      }
    }

    /*
    String[] Blah = { "MC", "SC", "SGT" };
    ArrayList Answer = Permutations.GenPermutations( Blah );


    // Set the Uniform and Hat for each character in each role in the location's
    // animation staging placeholders
    Location LocationForStagings = myLocationUsageSummary.GetNextLocation();
    if ( LocationForStagings != null ) {
      System.out.println( "----------------------------------------> Location to Stage " + LocationForStagings.Name() );
      Set RolesUsed = LocationForStagings.GetAnimationStagings().keySet();
      Iterator RoleItr = RolesUsed.iterator();
      while( RoleItr.hasNext() ) {
        String RoleName = (String) RoleItr.next();
        LocationAnimationPlaceholder AnimationLocation = (LocationAnimationPlaceholder) LocationForStagings.GetAnimationStagings().get( RoleName );
        StringTokenizer RoleParser = new StringTokenizer( RoleName, "," );
        while( RoleParser.hasMoreElements() ) {
          String RoleToAdd = (String) RoleParser.nextElement();
          if ( Character.isDigit( RoleToAdd.charAt(0) ) ) {
            continue;
          }
          CharacterRoleMap.Instance().AddRole( RoleToAdd, AnimationLocation.Uniform(), AnimationLocation.Hat(),
                                               com.armygame.recruits.StoryEngine.instance().castManager.getCharacterFromRole( RoleToAdd ).getActorName() );
        }
      }
    }
    */
    // notifyAll();
    try {
      myRequestQueue.add( pList );
    } catch( InterruptedException ie ) {
      return;
    }
    // System.out.println( "Out PlaylistResourceLoader Buffer" );
  }

}

