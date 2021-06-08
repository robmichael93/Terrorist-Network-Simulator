//
//  ButtonFactory.java
//  RecruitsJavaGui
//
//  Created by Mike Bailey on Wed Mar 13 2002.
//  Copyright (c) 2001 MOVES Institute, Naval Postgraduate School. All rights reserved.
//
package com.armygame.recruits.gui;
import com.armygame.recruits.gui.laf.RoundedBorder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import java.io.*;

// This class is misnamed.  It really is a factory for events, but most of the event generators
// are buttons. The int constants are so the GuiEventHandlers class can use a switch statement, and
// the names array is used by the button maker to find image file name, tool tips, etc.  It is used
// to generate keys in recruitsGui.properties, which is in the classpath -- typically packaged with
// the resources/art jar.  Standard suffixes (_NORM,_ROLL,_DISABLED,_DOWN,_tt,_x,_y) are appended
// to the string in the names array to find the specific properties.

public class ButtonFactory
/************************/
{
  static String[] names = new String[350];

  static final int MAINPLAYINTRO = 0;				static { names[MAINPLAYINTRO] = "MAINPLAYINTRO"; };
  static final int MAINSKIPINTRO = 1;				static { names[MAINSKIPINTRO] = "MAINSKIPINTRO";   };
  static final int MAINCONTINUE  = 2;				static { names[MAINCONTINUE]  = "MAINCONTINUE";  };
  static final int MAINPAUSE     = 3;				static { names[MAINPAUSE]     = "MAINPAUSE";  };
  static final int MAINHISTORY   = 4;				static { names[MAINHISTORY]   = "MAINHISTORY";  };
  static final int MAINCHAR      = 5;				static { names[MAINCHAR]      = "MAINCHAR";  };
  static final int MAINMAP       = 6;				static { names[MAINMAP]       = "MAINMAP";  };
  static final int MAINREFERRAL  = 7;				static { names[MAINREFERRAL]  = "MAINREFERRAL";  };
  static final int MAINCONTACTARMY=8;               static { names[MAINCONTACTARMY] = "MAINCONTACTARMY";};

  static final int MAINTOPPLAY   = 9;               static { names[MAINTOPPLAY]   = "MAINTOPPLAY";};
  static final int MAINTOPPAUSE  = 10;              static { names[MAINTOPPAUSE]  = "MAINTOPPAUSE";};
  static final int MAINCONTPLAYON= 11;              static { names[MAINCONTPLAYON]= "MAINCONTPLAYON";};
  static final int MAINCONTPLAYOFF=12;              static { names[MAINCONTPLAYOFF]="MAINCONTPLAYOFF";};
  //static final int MAINGOARMY    = 8;               static { names[MAINGOARMY]    = "MAINGOARMY";  };
  //static final int MAINLINKS     = 8;				static { names[MAINLINKS]     = "MAINLINKS";  };
  //static final int MAINAMERARMY  =9;                static { names[MAINAMERARMY]  = "MAINAMERARMY"; };
  //static final int MAINCONTACTS  = 9;				static { names[MAINCONTACTS]  = "MAINCONTACTS";  };
  //static final int MAINFILE      = 10;		    static { names[MAINFILE]      = "MAINFILE";  };
  static final int MAINMAIN      = 13;				static { names[MAINMAIN]      = "MAINMAIN";  };
  static final int MAINMESSAGE       = 14;          static { names[MAINMESSAGE]   = "MAINMESSAGE"; };
  static final int MAINMESSAGEFLASH  = 15;          static { names[MAINMESSAGEFLASH] = "MAINMESSAGEFLASH"; };
  static final int MAINMESSAGEURGENT = 16;          static { names[MAINMESSAGEURGENT] = "MAINMESSAGEURGENT"; };
  static final int MAINMESSAGENORMAL = 17;          static { names[MAINMESSAGENORMAL] = "MAINMESSAGENORMAL"; };
  static final int MAINFINANCIALS = 18;             static { names[MAINFINANCIALS] = "MAINFINANCIALS"; };
  static final int MAINHELP       = 19;             static { names[MAINHELP] = "MAINHELP"; };
//static final int MAINMESSAGEVIEW....bottom

  static final int CHARRESOURCESCANCEL = 22;		static { names[CHARRESOURCESCANCEL] = "CHARRESOURCESCANCEL";};
  static final int CHARRESOURCESOK     = 23;		static { names[CHARRESOURCESOK]     = "CHARRESOURCESOK";};
  static final int CHARVALUESCANCEL    = 24;		static { names[CHARVALUESCANCEL]    = "CHARVALUESCANCEL";};
  static final int CHARVALUESOK        = 25;		static { names[CHARVALUESOK]        = "CHARVALUESOK";};

  static final int CHARINTVALUES       = 26;		static { names[CHARINTVALUES]       = "CHARINTVALUES";};
  static final int CHARINTRESOURCES    = 27;		static { names[CHARINTRESOURCES]    = "CHARINTRESOURCES";};
  static final int CHARINTGOALS        = 28;		static { names[CHARINTGOALS]        = "CHARINTGOALS";};
  static final int CHARINTCLOSE        = 29;		static { names[CHARINTCLOSE]        = "CHARINTCLOSE";};
  static final int CHARINTCANCEL       = 30;        static { names[CHARINTCANCEL]       = "CHARINTCANCEL";};
  static final int CHARHIT             = 31;		static { names[CHARHIT]             = "CHARHIT";};

  static final int CHARSELECT0         = 32;		static { names[CHARSELECT0]         = "CHARSELECT0";};
  static final int CHARSELECT1         = 33;		static { names[CHARSELECT1]         = "CHARSELECT1";};
  static final int CHARSELECT2         = 34;		static { names[CHARSELECT2]         = "CHARSELECT2";};
  static final int CHARSELECT3         = 35;		static { names[CHARSELECT3]         = "CHARSELECT3";};
  static final int CHARSELECT4         = 36;		static { names[CHARSELECT4]         = "CHARSELECT4";};
  static final int CHARSELECT5         = 37;		static { names[CHARSELECT5]         = "CHARSELECT5";};
  static final int CHARSELECTLEFT      = 38;		static { names[CHARSELECTLEFT]      = "CHARSELECTLEFT";};
  static final int CHARSELECTRIGHT     = 39;		static { names[CHARSELECTRIGHT]     = "CHARSELECTRIGHT";};

  static final int CREATEOK            = 40;        static { names[CREATEOK]            = "CREATEOK";};

  static final int CHOOSERSHOW         = 45;        static { names[CHOOSERSHOW]         = "CHOOSERSHOW";};
  static final int CHOOSERSELECT       = 46;        static { names[CHOOSERSELECT]       = "CHOOSERSELECT";};
  static final int MOSCLOSE            = 47;        static { names[MOSCLOSE]            = "MOSCLOSE";};
  static final int MOSCLICKED          = 48;        static { names[MOSCLICKED]          = "MOSCLICKED";};

  static final int CONTACTARMYCANCEL   = 50;        static { names[CONTACTARMYCANCEL]     = "CONTACTARMYCANCEL";};
  static final int CONTACTARMYGOARMY   = 51;        static { names[CONTACTARMYGOARMY]     = "CONTACTARMYGOARMY";};
  static final int CONTACTARMYAMERICASARMY=52;      static { names[CONTACTARMYAMERICASARMY] = "CONTACTARMYAMERICASARMY";};
  static final int CONTACTARMYREFERRAL = 53;        static { names[CONTACTARMYREFERRAL]   = "CONTACTARMYREFERRAL";};

  static final int LOYALTYARROWLEFT    = 60;		static { names[LOYALTYARROWLEFT]      = "LOYALTYARROWLEFT";};
  static final int LOYALTYARROWRIGHT   = 61;		static { names[LOYALTYARROWRIGHT]     = "LOYALTYARROWRIGHT";};
  static final int DUTYARROWLEFT       = 62;		static { names[DUTYARROWLEFT]         = "DUTYARROWLEFT";};
  static final int DUTYARROWRIGHT      = 63;		static { names[DUTYARROWRIGHT]        = "DUTYARROWRIGHT";};
  static final int RESPECTARROWLEFT    = 64;		static { names[RESPECTARROWLEFT]      = "RESPECTARROWLEFT";};
  static final int RESPECTARROWRIGHT   = 65;		static { names[RESPECTARROWRIGHT]     = "RESPECTARROWRIGHT";};
  static final int SELFLESSARROWLEFT   = 66;		static { names[SELFLESSARROWLEFT]     = "SELFLESSARROWLEFT";};
  static final int SELFLESSARROWRIGHT  = 67;		static { names[SELFLESSARROWRIGHT]    = "SELFLESSARROWRIGHT";};
  static final int HONORARROWLEFT      = 68;		static { names[HONORARROWLEFT]        = "HONORARROWLEFT";};
  static final int HONORARROWRIGHT     = 69;		static { names[HONORARROWRIGHT]       = "HONORARROWRIGHT";};
  static final int INTEGRITYARROWLEFT  = 70;		static { names[INTEGRITYARROWLEFT]    = "INTEGRITYARROWLEFT";};
  static final int INTEGRITYARROWRIGHT = 71;		static { names[INTEGRITYARROWRIGHT]   = "INTEGRITYARROWRIGHT";};
  static final int COURAGEARROWLEFT    = 72;		static { names[COURAGEARROWLEFT]      = "COURAGEARROWLEFT";};
  static final int COURAGEARROWRIGHT   = 73;		static { names[COURAGEARROWRIGHT]     = "COURAGEARROWRIGHT";};

  static final int GOALSOK = 80;                static { names[GOALSOK]  = "GOALSOK";};
  static final int GOALSCANCEL = 81;            static { names[GOALSCANCEL]  = "GOALSCANCEL";};
  static final int GOALSVIEWALL = 82;           static { names[GOALSVIEWALL]  = "GOALSVIEWALL";};
  static final int GOALSVIEWALLCLOSE = 83;      static { names[GOALSVIEWALLCLOSE]  = "GOALSVIEWALLCLOSE";};
  static final int GOALSLIST = 84;              static { names[GOALSLIST] = "GOALSLIST";};
  static final int GOALSLISTCLOSE = 85;	        static { names[GOALSLISTCLOSE] = "GOALSLISTCLOSE";};
  static final int GOALSLISTSELECT = 86;	    static { names[GOALSLISTSELECT] = "GOALSLISTSELECT";};
  static final int GOALSLISTCANCEL = 87;	    static { names[GOALSLISTCANCEL] = "GOALSLISTCANCEL";};
  static final int GOALHELP = 88;               static { names[GOALHELP] = "GOALHELP";};
  static final int GOALHELPCLOSE = 89;          static { names[GOALHELPCLOSE] = "GOALHELPCLOSE";};

  static final int FILECANCEL = 90;             static { names[FILECANCEL] = "FILECANCEL";};
  static final int FILELOADGAME = 91;           static { names[FILELOADGAME] = "FILELOADGAME";};
  static final int FILESAVEGAME = 92;           static { names[FILESAVEGAME] = "FILESAVEGAME";};
  static final int FILEUPLOAD = 93;             static { names[FILEUPLOAD] = "FILEUPLOAD";};
  static final int FILEDOWNLOAD = 94;           static { names[FILEDOWNLOAD] = "FILEDOWNLOAD";};
  static final int FILEUPDATE = 95;             static { names[FILEUPDATE] = "FILEUPDATE";};
  static final int FILEEXIT = 96;               static { names[FILEEXIT] = "FILEEXIT";};
  static final int FILENEW = 97;                static { names[FILENEW] = "FILENEW";};
  static final int FILENEWCANCEL = 98;          static { names[FILENEWCANCEL] = "FILENEWCANCEL";};
  static final int FILEOPTIONS = 99;            static { names[FILEOPTIONS] = "FILEOPTIONS";};
  static final int FILESAVECHAR = 100;           static { names[FILESAVECHAR] = "FILESAVECHAR";};
  static final int FILESAVECANCEL = 101;        static { names[FILESAVECANCEL] = "FILESAVECANCEL";};
  static final int FILESAVE = 102;              static { names[FILESAVE] = "FILESAVE";};
  static final int FILELOAD = 103;              static { names[FILELOAD] = "FILELOAD";};
  static final int FILELOADCHAR = 104;          static { names[FILELOADCHAR] = "FILELOADCHAR";};
  static final int FILELOADCANCEL = 105;        static { names[FILELOADCANCEL] = "FILELOADCANCEL";};
  static final int FILENEWEXISTING = 106;       static { names[FILENEWEXISTING]= "FILENEWEXISTING";};
  static final int FILENEWCHAR = 107;           static { names[FILENEWCHAR]= "FILENEWCHAR";};

  static final int UPLOADCLOSE = 108;           static { names[UPLOADCLOSE] = "UPLOADCLOSE";};
  static final int UPLOADSUBMIT = 109;          static { names[UPLOADSUBMIT] = "UPLOADSUBMIT";};
  static final int UPLOADCHARCANCEL = 110;      static { names[UPLOADCHARCANCEL] = "UPLOADCHARCANCEL";};
  static final int UPLOADSTORYCANCEL = 111;     static { names[UPLOADSTORYCANCEL] = "UPLOADSTORYCANCEL";};
  static final int DOWNLOADCLOSE = 112;         static { names[DOWNLOADCLOSE] = "DOWNLOADCLOSE";};
  static final int DOWNLOADCHARCANCEL = 113;    static { names[DOWNLOADCHARCANCEL] = "DOWNLOADCHARCANCEL";};
  static final int DOWNLOADSTORYCANCEL = 114;   static { names[DOWNLOADSTORYCANCEL] = "DOWNLOADSTORYCANCEL";};

  static final int HISTORYCLOSE = 116;          static { names[HISTORYCLOSE] = "HISTORYCLOSE";};
  static final int HISTORYSTATIONS = 117;       static { names[HISTORYSTATIONS] = "HISTORYSTATIONS";};
  static final int HISTORYACHIEVEMENTS = 118;   static { names[HISTORYACHIEVEMENTS] = "HISTORYACHIEVEMENTS";};

  //static final int REFERRALCANCELLED = 120;     static { names[REFERRALCANCELLED] = "REFERRALCANCELLED";};
  //static final int REFERRALSUBMITTED = 121;     static { names[REFERRALSUBMITTED] = "REFERRALSUBMITTED";};
  static final int REFERRALCANCEL = 120;        static { names[REFERRALCANCEL] = "REFERRALCANCEL";};
  static final int REFERRALOK = 121;            static { names[REFERRALOK] = "REFERRALOK";};
  static final int REFERRALCHECKPERS = 122;     static { names[REFERRALCHECKPERS] = "REFERRALCHECKPERS";};
  static final int REFERRALCHECKEMAIL = 123;     static { names[REFERRALCHECKEMAIL] = "REFERRALCHECKEMAIL";};

  static final int INTERNETCANCEL = 125;        static { names[INTERNETCANCEL] = "INTERNETCANCEL";};
  static final int INTERNETDONE = 126;          static { names[INTERNETDONE] = "INTERNETDONE";};
  static final int INTERNETERROROK = 127;       static { names[INTERNETERROROK] = "INTERNETERROROK";};

  static final int UPDATEDONE = 129;            static { names[UPDATEDONE] = "UPDATEDONE";};
  static final int UPDATECANCEL = 130;          static { names[UPDATECANCEL] = "UPDATECANCEL";};
  static final int UPDATEDO = 131;              static { names[UPDATEDO] = "UPDATEDO";};

  static final int LOGINREG_REGISTER = 132;     static { names[LOGINREG_REGISTER] = "LOGINREG_REGISTER";};
  static final int LOGINREG_LOGIN = 133;        static { names[LOGINREG_LOGIN] = "LOGINREG_LOGIN";};
  static final int LOGINREG_CANCEL = 134;       static { names[LOGINREG_CANCEL] = "LOGINREG_CANCEL";};

  static final int LOGINNEWUSER = 135;          static { names[LOGINNEWUSER] = "LOGINNEWUSER";};
  static final int LOGINSUBMITTED = 136;        static { names[LOGINSUBMITTED] = "LOGINSUBMITTED";};
  static final int LOGINCANCEL = 137;           static { names[LOGINCANCEL] = "LOGINCANCEL";};

  static final int NEWUSERCANCEL = 140;         static { names[NEWUSERCANCEL] = "NEWUSERCANCEL";};
  static final int NEWUSERSUBMITTED = 141;      static { names[NEWUSERSUBMITTED] = "NEWUSERSUBMITTED";};
  static final int NEWUSERLOGIN = 142;          static { names[NEWUSERLOGIN] = "NEWUSERLOGIN";};

  static final int SAVEGAMECANCEL = 145;        static { names[SAVEGAMECANCEL] = "SAVEGAMECANCEL";};
  static final int SAVEGAMESAVE   = 146;        static { names[SAVEGAMESAVE] = "SAVEGAMESAVE";};
  static final int SAVECHARCANCEL = 147;        static { names[SAVECHARCANCEL] = "SAVECHARCANCEL";};
  static final int SAVECHARSAVE   = 148;        static { names[SAVECHARSAVE] = "SAVECHARSAVE";};
  static final int SAVECHARDEL   = 149;         static { names[SAVECHARDEL] = "SAVECHARDEL";};

  static final int LOADGAMECOMPLETE = 150;      static { names[LOADGAMECOMPLETE] = "LOADGAMECOMPLETE";};
  static final int LOADGAME_CANCEL = 151;       static { names[LOADGAME_CANCEL] = "LOADGAME_CANCEL";};
  static final int LOADGAMELOAD = 152;          static { names[LOADGAMELOAD] = "LOADGAMELOAD";};
  static final int LOADGAME_PROCEED = 153;      static { names[LOADGAME_PROCEED] = "LOADGAME_PROCEED";};

  static final int PLAYLISTRECEIVED = 155;      static { names[PLAYLISTRECEIVED] = "PLAYLISTRECEIVED";};
  static final int PLAYLISTFINISHED = 156;      static { names[PLAYLISTFINISHED] = "PLAYLISTFINISHED";};
  static final int NOPLAYLISTRECEIVED = 157;    static { names[NOPLAYLISTRECEIVED] = "NOPLAYLISTRECEIVED";};

  static final int CONTACTSCLOSE = 160;         static { names[CONTACTSCLOSE] = "CONTACTSCLOSE";};

  static final int INTERNETWAIT = 165;          static { names[INTERNETWAIT] = "INTERNETWAIT";};
  static final int INTRODONE = 166;             static { names[INTRODONE] = "INTRODONE";};

  static final int ACK = 170;										static { names[ACK] = "ACK";};
  static final int ERROR = 171;                 static { names[ERROR] = "ERROR";};

  static final int FINANCIALSCLOSE = 175;       static { names[FINANCIALSCLOSE] = "FINANCIALSCLOSE";};

  static final int GENERICCLOSE = 180;					static { names[GENERICCLOSE] = "GENERICCLOSE";};

  static final int GOAL_1_LEFT = 190;						static { names[GOAL_1_LEFT] = "GOAL_1_LEFT";};
  static final int GOAL_2_LEFT = 191;						static { names[GOAL_2_LEFT] = "GOAL_2_LEFT";};
  static final int GOAL_3_LEFT = 192;						static { names[GOAL_3_LEFT] = "GOAL_3_LEFT";};
  static final int GOAL_4_LEFT = 193;						static { names[GOAL_4_LEFT] = "GOAL_4_LEFT";};
  static final int GOAL_5_LEFT = 194;						static { names[GOAL_5_LEFT] = "GOAL_5_LEFT";};
  static final int GOAL_1_RIGHT = 195;					static { names[GOAL_1_RIGHT] = "GOAL_1_RIGHT";};
  static final int GOAL_2_RIGHT = 196;					static { names[GOAL_2_RIGHT] = "GOAL_2_RIGHT";};
  static final int GOAL_3_RIGHT = 197;					static { names[GOAL_3_RIGHT] = "GOAL_3_RIGHT";};
  static final int GOAL_4_RIGHT = 198;					static { names[GOAL_4_RIGHT] = "GOAL_4_RIGHT";};
  static final int GOAL_5_RIGHT = 199;					static { names[GOAL_5_RIGHT] = "GOAL_5_RIGHT";};

  static final int GOAL_DOUBLE_CLICKED = 205;       static { names[GOAL_DOUBLE_CLICKED] = "GOAL_DOUBLE_CLICKED";};
  static final int GOAL_REMOVE = 206;               static { names[GOAL_REMOVE] = "GOAL_REMOVE";};
  static final int GOAL_ADD = 207;                  static { names[GOAL_ADD] = "GOAL_ADD";};
  static final int ACHIEVEMENTSCLOSE = 210;         static { names[ACHIEVEMENTSCLOSE] = "ACHIEVEMENTSCLOSE";};
  static final int STATIONSCLOSE = 215;             static { names[STATIONSCLOSE] = "STATIONSCLOSE";};

  static final int CLOSEDIALOG_YES = 218;           static { names[CLOSEDIALOG_YES]   = "CLOSEDIALOG_YES";};
  static final int CLOSEDIALOG_NO = 219;            static { names[CLOSEDIALOG_NO]   = "CLOSEDIALOG_NO";};

  static final int CHARPERSSELECT = 220;			static { names[CHARPERSSELECT]    = "CHARPERSSELECT";};
  static final int CHARPERSSAVE = 221;				static { names[CHARPERSSAVE]      = "CHARPERSSAVE";};
  static final int CHARPERSFINI = 222;				static { names[CHARPERSFINI]      = "CHARPERSFINI";};
  static final int CHARPERSVALUES = 223;			static { names[CHARPERSVALUES]    = "CHARPERSVALUES";};
  static final int CHARPERSRESOURCES = 224;			static { names[CHARPERSRESOURCES] = "CHARPERSRESOURCES";};
  static final int CHARPERSGOALS = 225;				static { names[CHARPERSGOALS]     = "CHARPERSGOALS";};

  static final int RESOURCESENERGYLEFT = 230;		static { names[RESOURCESENERGYLEFT]     = "RESOURCESENERGYLEFT";};
  static final int RESOURCESSTRENGTHLEFT = 231;		static { names[RESOURCESSTRENGTHLEFT]   = "RESOURCESSTRENGTHLEFT";};
  static final int RESOURCESKNOWLEDGELEFT = 232;	static { names[RESOURCESKNOWLEDGELEFT]  = "RESOURCESKNOWLEDGELEFT";};
  static final int RESOURCESSKILLLEFT = 233;		static { names[RESOURCESSKILLLEFT]      = "RESOURCESSKILLLEFT";};
  static final int RESOURCESFINANCIALLEFT = 234;	static { names[RESOURCESFINANCIALLEFT]  = "RESOURCESFINANCIALLEFT";};
  static final int RESOURCESPOPULARITYLEFT = 235;	static { names[RESOURCESPOPULARITYLEFT] = "RESOURCESPOPULARITYLEFT";};

  static final int RESOURCESENERGYRIGHT = 236;		static { names[RESOURCESENERGYRIGHT]     = "RESOURCESENERGYRIGHT";};
  static final int RESOURCESSTRENGTHRIGHT = 237;	static { names[RESOURCESSTRENGTHRIGHT]   = "RESOURCESSTRENGTHRIGHT";};
  static final int RESOURCESKNOWLEDGERIGHT = 238;	static { names[RESOURCESKNOWLEDGERIGHT]  = "RESOURCESKNOWLEDGERIGHT";};
  static final int RESOURCESSKILLRIGHT = 239;		static { names[RESOURCESSKILLRIGHT]      = "RESOURCESSKILLRIGHT";};
  static final int RESOURCESFINANCIALRIGHT = 240;	static { names[RESOURCESFINANCIALRIGHT]  = "RESOURCESFINANCIALRIGHT";};
  static final int RESOURCESPOPULARITYRIGHT = 241;	static { names[RESOURCESPOPULARITYRIGHT] = "RESOURCESPOPULARITYRIGHT";};

  static final int MESSAGESHOWINGCLOSE = 250;       static { names[MESSAGESHOWINGCLOSE]      = "MESSAGESHOWINGCLOSE";};
  static final int MESSAGESHOWINGNEXT = 251;        static { names[MESSAGESHOWINGNEXT]       = "MESSAGESHOWINGNEXT";};
  static final int ALERTMESSAGE = 252;              static { names[ALERTMESSAGE]             = "ALERTMESSAGE";};

  static final int CANNEDSELECT = 255;              static { names[CANNEDSELECT]             = "CANNEDSELECT";};
  static final int CANNEDCANCEL = 256;              static { names[CANNEDCANCEL]             = "CANNEDCANCEL";};
  static final int CANNED_DOUBLE_CLICKED = 257;     static { names[CANNED_DOUBLE_CLICKED]    = "CANNED_DOUBLE_CLICKED";};
  static final int CANNEDHELPCLOSE = 258;           static { names[CANNEDHELPCLOSE]          = "CANNEDHELPCLOSE";};

  static final int CREATESOLDIERVALUES =260;        static { names[CREATESOLDIERVALUES]      = "CREATESOLDIERVALUES";};
  static final int CREATESOLDIERGOALS = 261;        static { names[CREATESOLDIERGOALS]       = "CREATESOLDIERGOALS";};
  static final int CREATESOLDIERRESOURCES = 262;    static { names[CREATESOLDIERRESOURCES]   = "CREATESOLDIERRESOURCES";};
  static final int CREATESOLDIERSAVE = 263;         static { names[CREATESOLDIERSAVE]        = "CREATESOLDIERSAVE";};
  static final int CREATESOLDIERPROCEED = 264;      static { names[CREATESOLDIERPROCEED]     = "CREATESOLDIERPROCEED";};
  static final int CREATESOLDIERCANCEL = 265;       static { names[CREATESOLDIERCANCEL]      = "CREATESOLDIERCANCEL";};

  static final int LOADCHAR_PROCEED = 266;          static { names[LOADCHAR_PROCEED]         = "LOADCHAR_PROCEED";};
  static final int LOADCHAR_CANCEL = 267;           static { names[LOADCHAR_CANCEL]          = "LOADCHAR_CANCEL";};

  static final int FILEOPS = 269;           			 static { names[FILEOPS] = "FILEOPS";};
  static final int FILEUPLOADCHAR = 270;            static { names[FILEUPLOADCHAR]           = "FILEUPLOADCHAR";};
  static final int FILEUPLOADSTORY = 271;           static { names[FILEUPLOADSTORY]          = "FILEUPLOADSTORY";};
  static final int FILEUPLOADCANCEL = 272;          static { names[FILEUPLOADCANCEL]         = "FILEUPLOADCANCEL";};
  static final int FILEDOWNLOADCHAR = 273;          static { names[FILEDOWNLOADCHAR]         = "FILEDOWNLOADCHAR";};
  static final int FILEDOWNLOADSTORY = 274;         static { names[FILEDOWNLOADSTORY]        = "FILEDOWNLOADSTORY";};
  static final int FILEDOWNLOADCANCEL = 275;        static { names[FILEDOWNLOADCANCEL]       = "FILEDOWNLOADCANCEL";};

  static final int MAPPOST_0       = 280;           static { names[MAPPOST_0]                = "MAPPOST_0";};
  static final int MAPPOST_1       = 281;           static { names[MAPPOST_1]                = "MAPPOST_1";};
  static final int MAPPOST_2       = 282;           static { names[MAPPOST_2]                = "MAPPOST_2";};
  static final int MAPPOST_3       = 283;           static { names[MAPPOST_3]                = "MAPPOST_3";};
  static final int MAPPOST_4       = 284;           static { names[MAPPOST_4]                = "MAPPOST_4";};
  static final int MAPPOST_5       = 285;           static { names[MAPPOST_5]                = "MAPPOST_5";};
  static final int MAPPOST_6       = 286;           static { names[MAPPOST_6]                = "MAPPOST_6";};
  static final int MAPPOST_7       = 287;           static { names[MAPPOST_7]                = "MAPPOST_7";};
  static final int MAPPOST_8       = 288;           static { names[MAPPOST_8]                = "MAPPOST_8";};
  static final int MAPPOST_9       = 289;           static { names[MAPPOST_9]                = "MAPPOST_9";};
  static final int MAPPOST_10      = 290;           static { names[MAPPOST_10]               = "MAPPOST_10";};
  static final int MAPPOST_11      = 291;           static { names[MAPPOST_11]               = "MAPPOST_11";};
  static final int MAPPOST_12      = 292;           static { names[MAPPOST_12]               = "MAPPOST_12";};
  static final int MAPPOST_13      = 293;           static { names[MAPPOST_13]               = "MAPPOST_13";};
  static final int MAPPOST_14      = 294;           static { names[MAPPOST_14]               = "MAPPOST_14";};
  static final int MAPPOST_15      = 295;           static { names[MAPPOST_15]               = "MAPPOST_15";};
  static final int MAPPOST_16      = 296;           static { names[MAPPOST_16]               = "MAPPOST_16";};
  static final int MAPPOST_17      = 297;           static { names[MAPPOST_17]               = "MAPPOST_17";};
  static final int MAPPOST_18      = 298;           static { names[MAPPOST_18]               = "MAPPOST_18";};
  static final int MAPPOST_19      = 299;           static { names[MAPPOST_19]               = "MAPPOST_19";};
  static final int MAPPOST_20      = 300;           static { names[MAPPOST_20]               = "MAPPOST_20";};
  static final int MAPPOST_21      = 301;           static { names[MAPPOST_21]               = "MAPPOST_21";};
  static final int MAPPOST_22      = 302;           static { names[MAPPOST_22]               = "MAPPOST_22";};
  static final int MAPPOST_23      = 303;           static { names[MAPPOST_23]               = "MAPPOST_23";};
  static final int MAPPOST_24      = 304;           static { names[MAPPOST_24]               = "MAPPOST_24";};

  static final int MAPCLOSE        = 310;           static { names[MAPCLOSE]                 = "MAPCLOSE";};
  static final int MAPALLBASES     = 311;           static { names[MAPALLBASES]              = "MAPALLBASES";};
  static final int MAPMYBASES      = 312;           static { names[MAPMYBASES]               = "MAPMYBASES";};

  static final int MAINMESSAGEVIEW = 315;           static { names[MAINMESSAGEVIEW]          = "MAINMESSAGEVIEW";};
  static final int STARTUPGAME     = 316;           static { names[STARTUPGAME]              = "STARTUPGAME";};
  static final int STARTUPNEWGAME  = 317;           static { names[STARTUPNEWGAME]           = "STARTUPNEWGAME";};
  static final int STARTUPLOADGAME = 318;           static { names[STARTUPLOADGAME]          = "STARTUPLOADGAME";};
  static final int STARTUPCOMPLETE = 319;           static { names[STARTUPCOMPLETE]          = "STARTUPCOMPLETE";};

  static final int OPTIONSSOUNDISON  = 325;         static { names[OPTIONSSOUNDISON]         = "OPTIONSSOUNDISON";};
  static final int OPTIONSSOUNDISOFF = 326;         static { names[OPTIONSSOUNDISOFF]        = "OPTIONSSOUNDISOFF";};
  static final int OPTIONSCANCEL     = 327;         static { names[OPTIONSCANCEL]            = "OPTIONSCANCEL";};
  static final int OPTIONSOK         = 328;         static { names[OPTIONSOK]                = "OPTIONSOK";};

  static final int SCENETESTCANCEL     = 329;       static { names[SCENETESTCANCEL]          = "SCENETESTCANCEL";};
  static final int SCENETESTSCENECHOSEN = 330;      static { names[SCENETESTSCENECHOSEN]     = "SCENETESTSCENECHOSEN";};

  static final int CHAREDITVIEW        = 335;		static { names[CHAREDITVIEW]             = "CHAREDITVIEW";};
  static final int CHAREDITCLOSE       = 336;		static { names[CHAREDITCLOSE]            = "CHAREDITCLOSE";};
  static final int CHAREDITRES         = 337;		static { names[CHAREDITRES]              = "CHAREDITRES";};
  static final int CHAREDITGOALS       = 338;		static { names[CHAREDITGOALS]            = "CHAREDITGOALS";};
  static final int CHAREDITVALUES      = 339;		static { names[CHAREDITVALUES]           = "CHAREDITVALUES";};

  static final int MAINDEPTHMETER_FOCUS     = 345;  static { names[MAINDEPTHMETER_FOCUS]  ="MAINDEPTHMETER_FOCUS";};
  static final int MAINDEPTHMETER_MEDIUM    = 346;  static { names[MAINDEPTHMETER_MEDIUM]  ="MAINDEPTHMETER_MEDIUM";};
  static final int MAINDEPTHMETER_OVERVIEW  = 347;  static { names[MAINDEPTHMETER_OVERVIEW]  ="MAINDEPTHMETER_OVERVIEW";};
  static final int MSG_OK  = 348;  static { names[MSG_OK]  ="MSG_OK";};
  public static final int SERVICESTARTED  = 349;  static { names[SERVICESTARTED]  ="SERVICESTARTED";};

  static private ButtonFactory me = new ButtonFactory();

  public static ButtonFactory instance()
  {
    return me;
  }

  static public JButton makePlain(int wh, MainFrame mf)
  //---------------------------------------------------
  {
    return make(wh,mf,true,true,false,false);
  }
  static public JButton make(int wh, MainFrame mf)
  //----------------------------------------------
  {
    return make(wh,mf,true,false,false,false);
  }
  static public JButton makeOob(int wh,MainFrame mf)
  {
    return make(wh,mf,true,false,false,true);
  }
  // superplain not used
  static public JButton make(int wh, MainFrame mf, boolean addhandler, boolean plain, boolean superPlain, boolean oob)
  {
    String base = names[wh];
    JButton b = new JButton(Ggui.imgIconGet(base+"_NORM"));
    b.setBorder(null);		// makes absolute positioning work
    b.setSize(b.getPreferredSize());
    b.setBorderPainted(false);
    b.setContentAreaFilled(false);
    b.setFocusPainted(false);	// prevents blue focus border in Windows
    /* no tool tips
    String tt = Ggui.getProp(base+"_tt");
    if(tt == null | tt == "")
      ;
    else
    b.setToolTipText(tt);
    */
    b.addMouseListener(new StatLineHandler(mf,Ggui.getProp(base+"_tt"),b));
    if(!plain)
    {
      b.setRolloverEnabled(true);
      b.setRolloverIcon(Ggui.imgIconGet(base+"_ROLL"));
    }
    b.setDisabledIcon(Ggui.imgIconGet(base+"_DISABLED"));
    b.setPressedIcon(Ggui.imgIconGet(base+"_DOWN"));
    int x = Integer.parseInt(Ggui.getProp(base+"_x"));
    int y = Integer.parseInt(Ggui.getProp(base+"_y"));
    b.setLocation(x,y);
    if(addhandler)
      b.addActionListener(new ButtListener(wh,mf,oob));
    return b;
  }

  static public JButton makeTextButt(int wh, boolean plain, Font f)
  {
    String base = names[wh];

    JButton butt = new JButton(Ggui.getProp(base+"_text"));
    butt.setFont(f);

    butt.addMouseListener(new StatLineHandler(MainFrame.me,Ggui.getProp(base+"_tt"),butt));

    butt.setRolloverEnabled(true);
    butt.setForeground(Ggui.buttonForeground()); //new Color(.95f,.76f,0.0f));
    butt.setBorder(null);
    if(!plain)
    {
      butt.setBackground(new Color(48,48,48));
      butt.setMargin(new Insets(3,15,3,15));
      butt.setBorder(new RoundedBorder(6));
    }
    butt.setContentAreaFilled(false);   // will prevent pressed color
    butt.setFocusPainted(false);	// prevents blue focus border in Windows
    butt.setSize(butt.getPreferredSize());
    int x = Integer.parseInt(Ggui.getProp(base+"_x"));
    int y = Integer.parseInt(Ggui.getProp(base+"_y"));
    butt.setLocation(x,y);
    butt.addActionListener(new ButtListener(wh,MainFrame.me));
    return butt;
  }

  static public JButton makeTextButt(int wh)
  //----------------------------------------
  {
    return makeTextButt(wh,false,Ggui.buttonFont());
  }
  static public JButton makeBigTextButt(int wh)
  //-------------------------------------------
  {
    return makeBigTextButt(wh,false);
  }
  static public JButton makeBigTextButt(int wh, boolean plain)
  //----------------------------------------------------------
  {
    return makeTextButt(wh,plain,Ggui.bigButtonFont());
  }
  static public JButton makeBigBigTextButt(int wh)
  //----------------------------------------------
  {
    return makeTextButt(wh,true,Ggui.bigBigButtonFont());
  }
}
class StatLineHandler extends MouseAdapter
{
  String s,olds="";
  MainFrame mf;
  JButton butt;
  StatLineHandler(MainFrame mf, String s,JButton butt)
  {
    this.mf = mf;
    this.s = s;
    this.butt = butt;
  }
  public void mouseEntered(MouseEvent e)
  {
    if(butt.isEnabled())
    {
      olds = mf.getStatusLine();
      mf.setStatusLine(s);
    }
    mf.panelSwitch=false;
  }
  public void mouseExited(MouseEvent e)
  {
    if(butt.isEnabled() && mf.panelSwitch==false)
    {
      mf.setStatusLine(olds);
    }
    mf.panelSwitch=false;
  }
}
class ButtListener implements ActionListener
{
  int evtN;
  MainFrame mf;
  boolean oob;;
  ButtListener(int eventNum, MainFrame main)
  {
    evtN=eventNum;
    mf=main;
    oob=false;
  }
  ButtListener(int eventNum, MainFrame main, boolean oob)
  {
    this(eventNum,main);
    this.oob = oob;
  }
  public void actionPerformed(ActionEvent ev)
  {
    mf.clickButton();
    if(oob)
      mf.handlers.oobEventIn(evtN,null);
    else
      mf.handlers.eventIn(evtN);

    ((JComponent)ev.getSource()).dispatchEvent(ev);         // gets the buttons to end
                                                            // up in the right state...?
  }
}


