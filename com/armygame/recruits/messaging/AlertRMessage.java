/*
 * Created by IntelliJ IDEA.
 * User: mike
 * Date: May 13, 2002
 * Time: 2:58:23 PM
 * To change template for new class use 
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package com.armygame.recruits.messaging;
import com.armygame.recruits.storyelements.sceneelements.AlertMessage;

public class AlertRMessage extends Rmessage
{
  AlertMessage am;
  public AlertRMessage(AlertMessage am)
  {
    super(tALERTRMESSAGE,ALERTRMESSAGE);
    this.am = am;
  }

  public int getPriority()
  {
    return am.getPriority();
  }
  public String getText()
  {
    return am.getText();
  }
  public Object toObject()
  {
    return am;
  }
  public String toTSV()
  {
    return getText()+"\t"+getPriority();
  }
  public String toQueryString()
  { return null; }

  public String toXML()
  { return null; }

}
