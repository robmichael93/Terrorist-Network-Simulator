//
//  GoalTreeModel.java
//  GoalTree
//
//  Created by Mike Bailey on Sun Apr 14 2002.
//  Copyright (c) 2001 __MyCompanyName__. All rights reserved.
//
package com.armygame.recruits.gui;
import java.util.*;
import javax.swing.tree.*;
import javax.swing.event.TreeModelListener;

public class GoalTreeModel implements TreeModel
{
  public ConnectedGoal root;
  //private Vector treeModelListeners = new Vector();
  private HashMap goals;
  private GoalTree tree;

  public GoalTreeModel(GoalTree tree)
  {
    this.tree = tree;
    // fill out the prerequisites
    goals = tree.goals;

    if(tree.root != null)
    {
      this.root = tree.root;
    }
    else
    {
      this.root = tree.root = new ConnectedGoal(null);        // dummy, since we don't display the root
      //Iterator itr = goals.values().iterator();
      //while(itr.hasNext())
      for(Iterator itr=goals.values().iterator(); itr.hasNext();)
      {
        ConnectedGoal cg = (ConnectedGoal)itr.next();
        if(cg.prereqs.isEmpty())
        {
          root.prereqOf.add(0,cg);
          cg.prereqs.add(root);
        }
      }
    }
  }
  public int getNodeCount()     // my own
  {
    return countChildren(root);
  }

  private int countChildren(ConnectedGoal g)
  {
    int c=0,caccum=0;

    if(isLeaf(g))
      return 0;
    c = caccum = getChildCount(g);
    for(int i=0;i<c;i++)
    {
      caccum += countChildren((ConnectedGoal)getChild(g,i));
    }
    return caccum;
  }

  public Object getRoot()
  //Returns the root of the tree. Returns null only if the tree has no nodes.
  {
    return root;
  }

  public Object getChild(Object parent, int index)
  {
    //Returns the child of parent at index index in the parent's child array.
    //parent must be a node previously obtained from this data source.
    //This should not return null if index is a valid index for parent
    //  (that is index >= 0 && index < getChildCount(parent)).
    //Parameters:
    //parent - a node in the tree, obtained from this data source
    //Returns: the child of parent at index index
    ConnectedGoal cg = (ConnectedGoal)parent;
    return cg.prereqOf.get(index);
  }

  public int getChildCount(Object parent)
  {
    //Returns the number of children of parent. Returns 0 if the node is a leaf 
    //or if it has no children.
    //parent must be a node previously obtained from this data source.
    //Parameters: parent - a node in the tree, obtained from this data source
    //Returns: the number of children of the node parent
    ConnectedGoal cg = (ConnectedGoal)parent;
    return cg.prereqOf.size();
  }
  
  public boolean isLeaf(Object node)
  {
    //Returns true if node is a leaf. It is possible for this method to
    //return false even if node has no children.
    //A directory in a filesystem, for example, may contain no files;
    //the node representing the directory is not a leaf, but it also has no children.
    //Parameters: node - a node in the tree, obtained from this data source
    //Returns: true if node is a leaf
    ConnectedGoal cg = (ConnectedGoal) node;
    if(cg.prereqOf.size() == 0)
      return true;
    return false;
  }
  
  public void valueForPathChanged(TreePath path, Object newValue)
  {
    //Messaged when the user has altered the value for the item identified by path to newValue.
    //If newValue signifies a truly new value the model should post a treeNodesChanged event.
    //Parameters: path - path to the node that the user has altered
    //            newValue - the new value from the TreeCellEditor
    // not used here
  }
  
  public int getIndexOfChild(Object parent, Object child)
  {
    //Returns the index of child in parent. If parent is null or child is null, returns -1.
    //Parameters: parent - a note in the tree, obtained from this data source
    //            child - the node we are interested in
    //Returns: the index of the child in the parent, or -1 if either child or parent are null
    if(parent == null || child == null) return -1;
    ConnectedGoal par = (ConnectedGoal)parent;
    return par.prereqOf.indexOf(child);
  }
  
  // These must be notified if the model changes.  Not used here
  public void addTreeModelListener(TreeModelListener l)
  {
    //Adds a listener for the TreeModelEvent posted after the tree changes.
    //Parameters:l - the listener to add
    //treeModelListeners.addElement(l);
  }
  
  public void removeTreeModelListener(TreeModelListener l)
  {
    //Removes a listener previously added with addTreeModelListener.
    //Parameters: l - the listener to remove
    //treeModelListeners.removeElement(l);
  }
  //////////////// Fire events //////////////////////////////////////////////
  /**
    * The only event raised by this model is TreeStructureChanged with the
    * root as path, i.e. the whole tree has changed.
    */
    /* not used here
    protected void fireTreeStructureChanged(Person oldRoot)
    {
      int len = treeModelListeners.size();
      TreeModelEvent e = new TreeModelEvent(this,  new Object[] {oldRoot});
      for (int i = 0; i < len; i++)
      {
        ((TreeModelListener)treeModelListeners.elementAt(i)).
        treeStructureChanged(e);
      }
    }
  */
}

