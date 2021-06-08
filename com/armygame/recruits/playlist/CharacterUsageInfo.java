package com.armygame.recruits.playlist;

public class CharacterUsageInfo {

  private String myName;
  private CharacterUsage.AnimationDef myAnimationInfo;

  public CharacterUsageInfo( String charName, CharacterUsage.AnimationDef animationInfo ) {
    myName = charName;
    myAnimationInfo = animationInfo;
  }

  public String Name() {
    return myName;
  }

  public String AnimationName() {
    return myAnimationInfo.Name();
  }

  public CharacterUsage.AnimationDef AnimationInfo() {
    return myAnimationInfo;
  }
}
