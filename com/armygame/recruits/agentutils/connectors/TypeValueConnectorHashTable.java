package com.armygame.recruits.agentutils.connectors;

import java.util.*;

/**
 * Title:        Connectors
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Naval Postgraduate School
 * @author Brian Osborn
 * @version 1.0
 */

public class TypeValueConnectorHashTable {

   private Hashtable connectors;

   public TypeValueConnectorHashTable() {
      connectors = new Hashtable();
   }

   public void addConnector(Connector c) {
      String name = c.getName();
      connectors.put(name, c);
   }

   public void put(Object key, Object conn) {
      connectors.put(key, conn);
   }

   public Object get(Object key) {
      return connectors.get(key);
   }

   public Enumeration elements() {
      return connectors.elements();
   }


   public boolean removeConnector(Connector c) {
      if (connectors.containsValue(c)) {
         connectors.remove(c);
         return true;
      }
      return false;
   }

   public TypeValueConnector getConnector(String name) {
      if (connectors.containsKey(name)) {
         return (TypeValueConnector) connectors.get(name);
      }
      return null;
   }

   public Hashtable getConnectorTable() {
      return connectors;
   }

   public void reportConnectorsStatus() {
      Enumeration enum = connectors.elements();
      while (enum.hasMoreElements()) {
         ((TypeValueConnector)enum.nextElement()).reportStatus();
      }
   }

   public void setHashTable(Hashtable connTable) {
      connectors = connTable;
   }

   public String[] listActiveConnectors() {
      String[] output = new String[connectors.size()];
      Enumeration enum = connectors.elements();
      int i = 0;
      while (enum.hasMoreElements()) {
         TypeValueConnector c = (TypeValueConnector)enum.nextElement();
         if (c.isExtended()) {
            output[i] = c.getName() + ":" + c.getValue();
            i++;
         }
      }
      return output;
   }
}
