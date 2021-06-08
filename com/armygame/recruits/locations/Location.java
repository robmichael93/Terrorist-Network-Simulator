/**
 * Title:        Army Game Project - The Recruits
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Emergent Design
 * @author Neal Elzenga
 * @version 1.0
 */

package com.armygame.recruits.locations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import com.armygame.recruits.globals.*;
import com.armygame.recruits.utils.RangeMap;
import com.armygame.recruits.utils.QueryVector;
import com.armygame.recruits.utils.StateVector;
import com.armygame.recruits.utils.StateVectorOverride;
import com.armygame.recruits.utils.TargetObjectKey;
import com.armygame.recruits.utils.AttributeGrove;
import com.armygame.recruits.locations.LocationTemplate;
import com.armygame.recruits.mediaelements.CastUsage;
import com.armygame.recruits.mediaelements.LocationMediaElement;

/**
 * Manages finding an appropriate <code>LocationTemplate</code> and instantiating that template.
 * This object maintains the list of <code>LocationAnimationPlaceholder</code>s and
 * <code>LocationImagePlaceholder</code>s that result from * instantiating a
 * <code>LocationTemplate</code>, considering their probability of occurrence, and resolution
 * of selection of a single placeholder when there are multiple alternatives as a result of an
 * instantiation query
 */
public class Location {

  /**
   * The name of this location
   */
  private String myLocationName;

  /**
   * The list of image placeholders - held as base class references so need to cast them to
   * <code>LocationImagePlaceholder</code> when using
   */
  private ArrayList myImagePlaceholders;

  /**
   * The list of animation staging placeholders - held as base class references so need to cast them to
   * <code>LocationAnimationPlaceholder</code> when using
   */
  private HashMap myAnimationStagingPlaceholders;

  /**
   * No-arg constructor
   */
  public Location() {
    myLocationName = null;
    myImagePlaceholders = new ArrayList();
    myAnimationStagingPlaceholders = new HashMap();
  }

  /**
   * Return the name of this location
   * @return The name of this location
   */
  public String Name() {
    return myLocationName;
  }

  /**
   * Instantiates a <code>LocationTemplate</code> into a <code>Location</code> object.  From the
   * supplied <code>QueryVector</code> this method locates a suitable <code>LocationTemplate</code>
   * in the <code>AttributeTrie</code> of templates.  It then parses the XML description of the
   * <code>LocationTemplate</code>.  Next, it traverses the set of <code>LocationMediaPlaceholder</code>s
   * in the template, and for each one locates the details of the actual media for that placeholder
   * and records these details in the fields that maintain the lists of placeholders in this
   * <code>Location</code> object.
   * @param query The <code>QueryVector</code> to use to find a suitable <code>LocationTemplate</code>
   */
  public void Instantiate( QueryVector query ) {
    // This TargetObjectKey holds the XML relative path to the LocationTemplate to instantiate
    // NOTE!!!! This code is current NOT robust in the face of a query for a LocationTemplate that
    // results in no template being found!!!!

    //System.out.println( query.toString() );

    // String TemplateKey = FindTemplate( query );

    HashMap NamesForTemplates = new HashMap();

    ArrayList MatchingTemplates = MasterLocationsConfiguration.Instance().LocationTemplateTrie().PatriciaQuery( query );

    // We use the cast of characters on stage for this scene to check that a given
    // location template returned from a connector-match query can also support the staging
    // requirements.
    Vector CastOnStage = com.armygame.recruits.StoryEngine.instance().castManager.getCastForCurrentScene();

    // This set of all possible permutations of the characters placed on the stage is used
    // to find at least one assignment of characters to staging placeholders in order
    // to verify that a given location template can stage this scene's cast of characters
    ArrayList CharStagingPermutations = Permutations.GenPermutations( (String[]) CastOnStage.toArray( new String[ CastOnStage.size() ] ) );

    if ( MatchingTemplates.size() > 0 ) {
      for( Iterator it = MatchingTemplates.iterator(); it.hasNext(); ) {
        // The matching templates are just the template file names.  Now we
        // need to instantiate each template to determine if its staging
        // placeholder requirements can handle the cast of characters on stage
        // for the current scene.
        String TemplateName = (String) it.next();

        // We create an intermediate LocationTemplate which we fill by parsing the XML
        // description of the template
        LocationTemplate TemplateToInstantiate = new LocationTemplate();
        String LocationTemplatePath = MasterConfiguration.Instance().MakeFullPath( MasterConfiguration.Instance().LocationTemplatesPath(), TemplateName );
        TemplateToInstantiate.ParseXMLObject( LocationTemplatePath );
        // InstantiateImages( TemplateToInstantiate, query );
        NamesForTemplates.put( TemplateName, TemplateToInstantiate );

        // If this tempalte cannot support the staging requirements, then remove it from consideration
        if ( InstantiateAnimationStagings( TemplateToInstantiate, CharStagingPermutations ) == false ) {
          it.remove();
        }
      }

      myLocationName = (String) Lfu.lfu( MatchingTemplates );
      LocationTemplate ActualTemplate = (LocationTemplate) NamesForTemplates.get( myLocationName );
      InstantiateImages( ActualTemplate, query );
      InstantiateAnimationStagings( ActualTemplate );

    } else {
    }

  }

  private boolean InstantiateAnimationStagings( LocationTemplate template, ArrayList stagingPermutations ) {
    boolean Result = true;

    // The placeholders we need to check the placement of the characters in this scene against
    LocationMediaPlaceholder[] StagingPlaceholders = template.AnimationStagingPlaceholders();

    int NumPlaceholders = StagingPlaceholders.length;

    if ( NumPlaceholders < ( (String[]) stagingPermutations.get( 0 ) ).length ) {
      Result = false;
    } else {

      // Try all the placement permutations of the characters in the scene within the given template.
      // The first valid placement found is locked into the template.
      // The routine returns true if a valid placement is found, otherwise false
      Iterator StagingPermItr = stagingPermutations.iterator();
      boolean CanStage = true;
      boolean StagingSuccessful = false;
      while( StagingPermItr.hasNext() ) {
        String[] ActorRoles = (String[]) StagingPermItr.next();
        CanStage = true;
        for( int i = 0; i < ActorRoles.length; i++ ) {
          CanStage &= ( StagingPlaceholders[ i ].RoleName().indexOf( ActorRoles[ i ] ) >= 0 );
        }
        if ( CanStage ) {
          StagingSuccessful = true;
          template.SetStagings( ActorRoles );
          for( int i = 0; i < ActorRoles.length; i++ ) {
            ( (LocationAnimationPlaceholder) StagingPlaceholders[ i ] ).SetIsUsed( true );
            myAnimationStagingPlaceholders.put( ActorRoles[ i ], StagingPlaceholders[ i ] );
          }
          break;
        }
      }
      Result = StagingSuccessful;

    }

    return Result;
  }

  /**
   * Finds a <code>LocationTemplate</code> in the Trie of <code>LocationTemplate</code>s.
   * If there are multiple candidate templates as a result of the query this routine
   * selects one at random.  If there are no matches returns <code>null</code>
   * @param query The <code>QueryVector</code> to use to search for a <code>LocationTemplate</code>
   * @return The name of the <code>LocationTemplate</code> we're looking for
   */
  static private LeastFrequentlyUsed  Lfu = new LeastFrequentlyUsed();

  private String FindTemplate( QueryVector query ) {
    String Result = null;
    ArrayList MatchingTemplates = MasterLocationsConfiguration.Instance().LocationTemplateTrie().PatriciaQuery( query );
    for(Iterator it = MatchingTemplates.iterator(); it.hasNext(); )
    {
      System.out.println("Matching location template:  "+it.next());
    }

    // Resolve multiple results by random selecting one of them
    int Matches = MatchingTemplates.size();
    if ( Matches > 0 ) {
//      int x = RecruitsRandom.RandomIndex( Matches );
//      Object obj = MatchingTemplates.get( x );
//      Result = (String) obj;
//    }
      // instead of random, do least frequently used
      Result = (String)Lfu.lfu(MatchingTemplates);
    }
    return Result;
  }

  /**
   * Constructs a <code>QueryVector</code> from a <code>LocationImagePlaceholder</code> and a
   * <code>QueryVector</code> used to find a <code>LocationTemplate</code> by factoring in the
   * Override key ranges, wildcard key ranges, and Pass Through key ranges specified in the
   * <code>LocationImagePlaceholder</code>.  First it assumes all bits in the <code>QueryVector</code>
   * it's constructing to be wild.  Then it replaces the explicitly set pass through, override,
   * and wild key ranges from the <code>LocationImagePlaceholder</code>
   * @param imagePlaceholder The <code>LocationImagePlaceholder</code> that contains the set of override, pass through, and wildcard key ranges we'll substitute into the passed in <code>QueryVector</code>
   * @param rangeMap The <code>RangeMap</code> that tells us how to interpret state range names
   * @param query The <code>QueryVector</code> source for pass through ranges
   * @return The <code>QueryVector</code> we've built
   */
  private QueryVector MakeImageSearchQueryVector( LocationImagePlaceholder imagePlaceholder,
                                                  RangeMap rangeMap, QueryVector query ) {
    StateVector FullVector = new StateVector( rangeMap );
    QueryVector Result = new QueryVector( FullVector );

    System.out.println( "Making image search query vector - starting with all wilds" );
    // Handle all the pass throughs
    String[] PassThroughRanges = imagePlaceholder.PassThroughRanges();
    for( int i = 0, len = PassThroughRanges.length; i < len; i++ ) {
      System.out.println( "Passing through range " + PassThroughRanges[i] );
      Result.CopyQueryRange( FullVector.RangeFor( PassThroughRanges[i] ), query );
    }

    // Handle all the wildcards
    String[] WildcardRanges = imagePlaceholder.WildcardRanges();
    for( int j = 0, len2 = WildcardRanges.length; j < len2; j++ ) {
      Result.ForceWild( FullVector.RangeFor( WildcardRanges[j] ) );
    }

    // Handle all the overrides
    StateVectorOverride[] OverrideRanges = imagePlaceholder.OverrideRanges();
    for( int k = 0, len3 = OverrideRanges.length; k < len3; k++ ) {
      Result.Override( OverrideRanges[k] );
    }

    return Result;
  }

  /**
   * For each <code>LocationImagePlaceholder</code> in the supplied <code>LocationTemplate</code>
   * update this <code>Location</code>'s list of instantiated images.  We do this by considering
   * the probability of occurrence of this image (maybe it won't show up in this instantiation).
   * If it is to show up, we build a query vector by combining the supplied <code>QueryVector</code>
   * (which should be the <code>QueryVector</code> used to find the <code>LocationTemplate</code>)
   * and use the key ranges in the <code>LocationTemplate</code> to build another <code>QueryVector</code>
   * that we will use to search the <code>AttributeGrove</code> of image asset descriptions
   * to find one suitable for the role required of the current placeholder.
   * @param template The <code>LocationTemplate</code> we are instantiating the <code>LocationImagePlaceholder</code>s from
   * @param query The <code>QueryVector</code> used to find the supplied <code>LocationTemplate</code>
   */
  private void InstantiateImages( LocationTemplate template, QueryVector query ) {

    // We'll use the template's range map for knowing how to interpret state range names when
    // building queries
    RangeMap TemplateRangeMap = template.GetRangeMap();

    // We need to find a suitable image for this image placehlder in the grove of images
    AttributeGrove SearchGrove = MasterLocationsConfiguration.Instance().LocationMediaElementsGrove();

    // All image placeholders are potential until we check their probability of occurrence
    LocationMediaPlaceholder[] PotentialPlaceholders = template.ImagePlaceholders();

    // Loop through all the potential placeholders and instantiate those that statisfy their
    // probability of occurrence
    System.out.println( myLocationName + " has " + PotentialPlaceholders.length + " potential placeholders" );
    for( int i = 0, len = PotentialPlaceholders.length; i < len; i++ ) {

      LocationImagePlaceholder Placeholder = ( LocationImagePlaceholder ) PotentialPlaceholders[i];
      int ProbabilityOfOccurrence = Placeholder.ProbabilityOfOccurrence();

      if ( ( ProbabilityOfOccurrence == 100 ) || ( ProbabilityOfOccurrence <= RecruitsRandom.RandomIndex( 100 ) ) ) {

        // Make the query vector by substituting the pass through, override, and wildcard
        // ranges specified by out LocationImagePlaceholder
        QueryVector SearchQuery = MakeImageSearchQueryVector( Placeholder, TemplateRangeMap, query );
        // Search the AttributeGrove to find an image TargetObjectKey that matches our query
        String[] Roles = Placeholder.Roles();
        ArrayList ImageQueryResults = new ArrayList();

        // Get all the results for queries on all the roles
        for( int j = 0, len2 = Roles.length; j < len2; j++ ) {
          System.out.println( "Querying role " + Roles[j] );
          ImageQueryResults.addAll( SearchGrove.Query( Roles[j], SearchQuery ) );
        }

        // Now choose only one of the returned matches
        int ImageMatches = ImageQueryResults.size();
        if ( ImageMatches > 0 ) {
          String InstantiatedImageKey = (String) ImageQueryResults.get( RecruitsRandom.RandomIndex( ImageMatches ) );
          LocationMediaElement MediaDescription = new LocationMediaElement();
          RecruitsConfiguration Config = MasterConfiguration.Instance();
          String MediaElementFile = Config.MakeFullPath( Config.LocationMediaAssetsPath(), (String) InstantiatedImageKey );
          System.out.println( "About to parse description for " + MediaElementFile );
          //System.out.println( "Attempting to read media asset description: " + MediaElementFile );
          MediaDescription.ParseXMLObject( MediaElementFile );
          Placeholder.SetMediaElement( MediaDescription );
          myImagePlaceholders.add( Placeholder );
        } else {
          System.out.println( "This placeholder didn't match anything in the grove and will be left out" );
        }
      }
    }

  }

  /**
   * For each <code>LocationAnimationPlaceholder</code> in the supplied <code>LocationTemplate</code>>
   * update this <code>Location</code>'s list of animation staging placeholders.  Animation placeholders
   * always appear - they don't have a probabilty of occurrence. We simply transfer the staging
   * information from the <code>LocationTemplate</code> to this <code>Location</code>
   * @param template The <code>LocationTempalte</code> we are copying the <code>LocationAnimationPlaceholder</code>s from
   */
  private void InstantiateAnimationStagings( LocationTemplate template ) {
    LocationMediaPlaceholder[] StagingPlaceholders = template.AnimationStagingPlaceholders();
    for( int i = 0, len = StagingPlaceholders.length; i < len; i++ ) {
      System.out.println( "Hash storing role as " + StagingPlaceholders[i].RoleName() );
      myAnimationStagingPlaceholders.put( StagingPlaceholders[i].RoleName(), StagingPlaceholders[i] );
    }
  }

  public HashMap GetAnimationStagings() {
    return myAnimationStagingPlaceholders;
  }

  public ArrayList GetImagePlaceholders() {
    return myImagePlaceholders;
  }

  /**
   * Traverse the instantiated <code>Location</code> and add its cast usage to the supplied
   * <code>CastUsage</code> object
   * @param castUsage The <code>CastUsage</code> object we are adding our cast usage summary to
   */
  public void UpdateCastUsage( CastUsage castUsage ) {
    for( int i = 0, size = myImagePlaceholders.size(); i < size; i++ ) {
      LocationImagePlaceholder UpdatePlaceholder = ( LocationImagePlaceholder ) myImagePlaceholders.get( i );
      LocationMediaElement CastDefs = UpdatePlaceholder.MediaElement();
      castUsage.AddCastUse( CastDefs.CastName(), CastDefs.CastMemberName() );
    }
  }

  /**
   * Emit the information in this <code>Location</code> to XML in a format suitable for
   * Java-Director messaging.  Because this message is optimized for communications speed,
   * it contains no whitespace and is admittedly not human readable
   */
  public String toXML() {
    StringBuffer Result = new StringBuffer();
    Result.append( "<LocationDescription>" );
    Result.append( "<LocationName>" ); Result.append( myLocationName ); Result.append( "</LocationName>" );
    Result.append( "<LocationImagesList>" );
    for( int i = 0, len = myImagePlaceholders.size(); i< len; i++ ) {
      Result.append( ( (LocationImagePlaceholder) myImagePlaceholders.get( i ) ).toXML() );
    }
    Result.append( "</LocationImagesList>" );
    Result.append( "<CharacterStagings>" );
    // for( int j = 0, len2 = myAnimationStagingPlaceholders.size(); j < len2; j++ ) {
    //   Result.append( ( (LocationAnimationPlaceholder) myAnimationStagingPlaceholders.get( j ) ).toXML() );
    // }
    Result.append( "</CharacterStagings>" );
    Result.append( "</LocationDescription>" );
    return Result.toString();
  }

}
