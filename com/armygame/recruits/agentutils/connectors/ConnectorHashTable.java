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

public class ConnectorHashTable {

   private Hashtable connectors;

   public ConnectorHashTable() {
      connectors = new Hashtable();
   }

   public void addConnector(Connector c) {
      String name = c.getName();
      connectors.put(name, c);
   }

   public boolean removeConnector(Connector c) {
      if (connectors.containsValue(c)) {
         connectors.remove(c);
         return true;
      }
      return false;
   }

   public Connector getConnector(String name) {
      if (connectors.containsKey(name)) {
         return (Connector) connectors.get(name);
      }
      return null;
   }

   public Hashtable getConnectorTable() {
      return connectors;
   }

   public void reportConnectorsStatus() {
      Enumeration enum = connectors.elements();
      while (enum.hasMoreElements()) {
         ((Connector)enum.nextElement()).reportStatus();
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
         Connector c = (Connector)enum.nextElement();
         if (c.isExtended()) {
            output[i] = c.getName();
            i++;
         }
      }
      return output;
   }
}
