package com.armygame.recruits.playlist;


/**
 * Enum for mapping the types of dialogs to a cast
 */
public class SoundType {

  /**
   * Holds the cast name that corresponds to this type of sound
   */
  private final String myCast;

  /**
   * The constructor is private, so the only publically available <code>SoundType</code>s
   * are those declared <code>public static final</code> below
   * @param castName The cast name that corresponds to this sound type enum
   */
  private SoundType( String castName ) {
    myCast = castName;
  }

  /**
   * Return the cast name that corresponds to this <code>SoundType</code>
   * @return The cast name for this sound type
   */
  public String toString() {
    return myCast;
  }

  /**
   * <code>SoundType</code> For background music
   */
  public static final SoundType BACKGROUND_MUSIC = new SoundType( "BGMusic" );

  /**
   * <code>SoundType</code> for background ambient sounds
   */
  public static final SoundType BACKGROUND_AMBIENT = new SoundType( "BGAudio" );

  /**
   * <code>SoundType</code> for Foley sound effects
   */
  public static final SoundType FOLEY_SOUND = new SoundType( "FoleyAudio" );

  /**
   * <code>SoundType</code> for character dialog
   */
  public static final SoundType CHARACTER_DIALOG = new SoundType( "Dialog" );

}
