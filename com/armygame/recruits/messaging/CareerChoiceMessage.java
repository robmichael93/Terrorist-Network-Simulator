/*
 * Created by IntelliJ IDEA.
 * User: mike
 * Date: May 17, 2002
 * Time: 6:03:10 PM
 * To change template for new class use 
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package com.armygame.recruits.messaging;

public class CareerChoiceMessage extends Rmessage
{
  public CareerChoiceMessage(String choice)
  {
    super(tCAREERCHOICE,CAREERCHOICE);
    this.choice = choice;
  }

  String choice;
  public String getChoice()
  {
    return choice;
  }

  public String toTSV(){return null;}
  public String toQueryString(){return null;}
  public String toXML(){return null;}
}
