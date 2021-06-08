/*
 * Soldiers / Army Game Project
 * MOVES Institute, Naval Postgraduate School
 * User: mike
 * Date: Jun 5, 2002
 * Time: 10:54:43 AM
 */
package com.armygame.recruits.globals;

import java.io.Serializable;

public class SavedGame implements Serializable
{
  public String name; // withouth .sgm
  public String description;
  public SavedCharacter character;
}
