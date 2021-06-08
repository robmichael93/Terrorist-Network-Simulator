/*
 * Created by IntelliJ IDEA.
 * User: mike
 * Date: Sep 17, 2002
 * Time: 11:05:23 AM
 * To change template for new class use 
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package com.armygame.recruits.globals;

import java.util.*;

public class LeastFrequentlyUsed
{
  HashMap hm;
  public LeastFrequentlyUsed()
  {
    hm = new HashMap();
  }

  public Object lfu(ArrayList oal)
  {
    // spec case
    if(oal.size() == 1)
      return oal.get(0);
    ArrayList sortedList = new ArrayList(oal.size());
    for(int i=0;i<oal.size();i++)
    {
      Object o = oal.get(i);
      Integer count = (Integer)hm.get(o);
      if(count == null)
      {
        count = new Integer(0);
        hm.put(o,count);
      }
      sortedList.add(new Pair(o,count.intValue()));
    }
    Collections.sort(sortedList,new myComparator());
//    for(Iterator itr = sortedList.iterator();itr.hasNext();)
//    {
//      Pair n = (Pair)itr.next();
//       System.out.println("...."+n.o + " " + n.i);
//    }
    Pair best = (Pair)sortedList.get(0);
    hm.put(best.o,new Integer(best.i+1));
    return best.o;
  }
  class myComparator implements Comparator
  {
    public int compare(Object o1, Object o2)
    {
      int i1 = ((Pair)o1).i;
      int i2 = ((Pair)o2).i;
      return i1-i2;
    }
  }
  class Pair
  {
    Object o;
    int i;
    Pair(Object o, int i)
    {
      this.o = o ;
      this.i = i;
    }
  }
}
