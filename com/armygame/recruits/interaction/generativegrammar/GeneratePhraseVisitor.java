

package com.armygame.recruits.interaction.generativegrammar;

import java.util.ArrayList;
import com.armygame.recruits.utils.ConcreteVisitor;


public class GeneratePhraseVisitor extends ConcreteVisitor {

  private ArrayList myDialogPhraseNames;

  public GeneratePhraseVisitor() {
    super();
    myDialogPhraseNames = new ArrayList();
  }

  public void operate( Object[] args ) {
    myDialogPhraseNames.add( args[0] );
  }

  public void reset() {
    myDialogPhraseNames.clear();
  }

  public String[] GetDialog() {
    return (String[]) myDialogPhraseNames.toArray( new String[0] );
  }

} // class GeneratePhraseVisitor
