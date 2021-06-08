package com.armygame.recruits.gui;

import com.armygame.recruits.RecruitsMain;
import com.armygame.recruits.storyelements.sceneelements.CharInsides;

import javax.swing.*;
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class MainFrame extends JFrame
    /***************************/
{
  private int _WIDTH=800;
  private int _HEIGHT=600;
  String defaultStatus="Click on a button on the top or side to choose what to do.";
  Container cont;

  boolean panelSwitch=false;
  JButton fileButt,historyButt,continueButt,pauseButt,charButt,mapButt; //referralButt;
  JButton /*goArmyButt, amerArmyButt,*/ helpButt, skipIntroButt, financialsButt; //,linksButt,contactsButt;
  JButton contactArmyButt;
  JButton topPlayButt,topPauseButt,contPlayOnButt,contPlayOffButt;
  JButton flashMessButt, urgentMessButt, normMessButt, messageViewButt;
  JButton meterFocusButt,meterMediumButt,meterOverviewButt;

  public static IntroPanel introPanel;
  FilePanel filePanel;
  RPanel mapPanel, inetWaitPanel, inetErrorPanel,
         charEditPanel,charInteriorPanel,
         historyPanel,  achievementsPanel, referralPanel, //linksPanel, contactsPanel,
         charGoalsViewAllPanel, dutyStationsPanel, optionsPanel,
         downloadPanel, uploadPanel, saveGamePanel, opsPanel;
  MsgPanel msgPanel;
  GameUpdatesPanel updatesPanel;
  SceneListTestPanel  sceneTestPanel;
  JComponent achievementMedalPanel;
  SaveCharPanel saveCharPanel;
  UploadCharPanel uploadCharPanel;
  UploadStoryPanel uploadStoryPanel;
  DownloadCharPanel downloadCharPanel;
  DownloadStoryPanel downloadStoryPanel;
  MainPanel       mainPanel;
  ChooserPanel    chooserPanel;
  MOSPanel        mosPanel;
  FinancialsPanel financialsPanel;
  // cannedCharPanel;
  //CannedHelpPanel cannedHelpPanel;
  RankPanel       rankPanel;
  MessagePanel    messagePanel;
  //CharSelectPanel charSelectPanel;
  CharGoals5Panel charGoalsPanel;
  CharGoalsListPanel charGoalsListPanel;
  CharResourcesPanel charResourcesPanel;
  //CharPersonality charPersonalityPanel;
  //LoginPanel      loginPanel;
  NewUserPanel    newUserPanel;
  PlayScenePanel  playscenePanel; // debug
  //CharNamePanel   charNamePanel;
  CharValuesPanel charValuesPanel;
  CharGoalsHelpPanel charGoalsHelpPanel;
  CreateSoldierPanel createSoldierPanel;
  LoadCharPanel   loadCharPanel;
  LoadGamePanel   loadGamePanel;
  LoginOrRegisterPanel loginOrRegisterPanel;
  ContactArmyPanel contactArmyPanel;
  StartupNewGamePanel startupNewPanel;

  QuipPanel           quipPanel;
  NarratorPanel       narratorPanel;
  JLabel              statusLine;
  JLabel              soldierHead;
  JLabel              soldierName;
  JLabel              titleBar;

  GuiEventHandlers    handlers;
  RecruitsSplash      splash;
  RecruitsGuiMessager gmess;
  static public JLayeredPane /*JPanel*/ lp;

  static AudioClip quips[];
  static int numQuips;
  static AudioClip quipDefault,quipOffDefault;
  static AudioClip clickClip;
  static AudioClip laserClip;
  static AudioClipPlayer cPlayer;
  static AudioClipPlayer loopPlayer;
  static AudioClipPlayer quipPlayer;

  public boolean gameChosen=false;
  // temp
  class Globals
  {
    boolean loggedIn=false;
    //public String charName;
    public int charIndex;
    public ImageIcon charHead;
    public ImageIcon charHead_grey;

    public CharInsides charinsides=new CharInsides();
    public UserVitals userVitals=new UserVitals();

    Globals() // to test
    {
      charinsides.duty=25;
      charinsides.selfless=75;
      charinsides.loyalty=50;
      charinsides.unallocated=25;
    }

    public void updateValues(CharInsides ci)
    {
      charinsides=ci;
      for(int i=0; i<ci.goalEmphasis.length; i++)
      {
        if(ci.chosenGoals[i] != null)
          charinsides.goalEmphasis[i]=ci.chosenGoals[i].getEmphasis();
        charinsides.goalChangedByGui[i]=false;
      }
    }

    public CharInsides getLatest()
    {
      return charinsides;
    }
  }

  Globals globals=new Globals();
  RecruitsMain rmain;

  public static MainFrame me;

  public MainFrame(RecruitsMain rmain, RecruitsSplash splash)
  {
    super("Soldiers");
    me=this;

    this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    this.addWindowListener(new WindowAdapter()
    {
      public void windowClosing(WindowEvent e)
      {
        CloseDialog cd = new CloseDialog(MainFrame.this);
        cd.setVisible(true);
      }
    });

    this.setIconImage(Ggui.imgIconGet("FRAMEICON").getImage());
    this.splash=splash;
    this.rmain=rmain;

    this.gmess=new RecruitsGuiMessager(this);

    positionAndSize();
    handlers=new GuiEventHandlers(this);

    cPlayer=new AudioClipPlayer(this);
    loopPlayer=new AudioClipPlayer(this,true);
    quipPlayer=new AudioClipPlayer(this);

    cont=getContentPane();
    cont.setLayout(null);

    rankPanel=new RankPanel(this);
    rankPanel.setLocation(703,209);
 //   rankPanel.setVisible(false);
    cont.add(rankPanel);

    soldierHead = new JLabel(Ggui.imgIconGet("Blankportrait"));
    soldierHead.setSize(soldierHead.getPreferredSize());
    soldierHead.setLocation(712,112);
    cont.add(soldierHead);

    soldierName = new JLabel("");
    soldierName.setFont(Ggui.solderNameFont);
    soldierName.setForeground(Ggui.soldierNameColor);
    soldierName.setLocation(712,194);
    soldierName.setSize(78,14);
    soldierName.setHorizontalAlignment(SwingConstants.CENTER);
    cont.add(soldierName);

    splash.advance();

    titleBar = new JLabel();
    titleBar.setLocation(0,0);
    cont.add(titleBar);

    stackPanels();

    quipPanel = new QuipPanel(this);
    quipPanel.setLocation(430,600);
    lp.add(quipPanel,JLayeredPane.PALETTE_LAYER);

    narratorPanel = new NarratorPanel(this);
    narratorPanel.setLocation(100,600);
    lp.add(narratorPanel,JLayeredPane.PALETTE_LAYER);

    splash.advance();

    statusLine=new JLabel(defaultStatus);
    statusLine.setBounds(new Rectangle(50,575,605,20));
    statusLine.setForeground(Ggui.statusLineColor);
    statusLine.setFont(Ggui.statusLineFont);
    cont.add(statusLine);

    // buttons
    fileButt=ButtonFactory.make(ButtonFactory.MAINMAIN,this);
    cont.add(fileButt);
    financialsButt=ButtonFactory.make(ButtonFactory.MAINFINANCIALS,this);
    cont.add(financialsButt);
    historyButt = ButtonFactory.make(ButtonFactory.MAINHISTORY,this);

    skipIntroButt = ButtonFactory.make(ButtonFactory.MAINSKIPINTRO,this);
    skipIntroButt.setEnabled(false); // gets enabled in introPanel
    cont.add(skipIntroButt);

    cont.add(historyButt);
    continueButt = ButtonFactory.make(ButtonFactory.MAINCONTINUE,this);
    cont.add(continueButt);
    pauseButt = ButtonFactory.make(ButtonFactory.MAINPAUSE,this);
    cont.add(pauseButt);
    pauseButt.setVisible(false);
    charButt = ButtonFactory.make(ButtonFactory.MAINCHAR,this);
    cont.add(charButt);
    mapButt = ButtonFactory.make(ButtonFactory.MAINMAP,this);
    cont.add(mapButt);
    helpButt = ButtonFactory.makeOob(ButtonFactory.MAINHELP,this);
    cont.add(helpButt);

    topPlayButt = ButtonFactory.make(ButtonFactory.MAINTOPPLAY,this);
    cont.add(topPlayButt);
    topPauseButt= ButtonFactory.make(ButtonFactory.MAINTOPPAUSE,this);
    cont.add(topPauseButt);

    contPlayOnButt = ButtonFactory.makeOob(ButtonFactory.MAINCONTPLAYON,this);
    cont.add(contPlayOnButt);
    contPlayOffButt = ButtonFactory.makeOob(ButtonFactory.MAINCONTPLAYOFF,this);
    cont.add(contPlayOffButt);

    contPlayOnButt.setEnabled(false);   // don't turn on until after choosing game
    contPlayOffButt.setEnabled(false);
    if(RecruitsMain.instance().autoPlay == true)
    {
      contPlayOnButt.setVisible(true);
      contPlayOffButt.setVisible(false);
    }
    else
    {
      contPlayOnButt.setVisible(false);
      contPlayOffButt.setVisible(true);
    }
    //referralButt = ButtonFactory.make(ButtonFactory.MAINREFERRAL,this);
    //cont.add(referralButt);
    //linksButt = ButtonFactory.make(ButtonFactory.MAINLINKS,this);
    //cont.add(linksButt);
    //contactsButt = ButtonFactory.make(ButtonFactory.MAINCONTACTS,this);
    //cont.add(contactsButt);
    contactArmyButt = ButtonFactory.make(ButtonFactory.MAINCONTACTARMY,this);
    cont.add(contactArmyButt);
    //goArmyButt = ButtonFactory.make(ButtonFactory.MAINGOARMY,this);
    //cont.add(goArmyButt);
    //amerArmyButt = ButtonFactory.make(ButtonFactory.MAINAMERARMY,this);
    //cont.add(amerArmyButt);

    flashMessButt=ButtonFactory.makePlain(ButtonFactory.MAINMESSAGEFLASH,this);
    flashMessButt.setEnabled(false);
    cont.add(flashMessButt);
    urgentMessButt=ButtonFactory.makePlain(ButtonFactory.MAINMESSAGEURGENT,this);
    urgentMessButt.setEnabled(false);
    cont.add(urgentMessButt);
    normMessButt=ButtonFactory.makePlain(ButtonFactory.MAINMESSAGENORMAL,this);
    normMessButt.setEnabled(false);
    cont.add(normMessButt);
    messageViewButt = ButtonFactory.makePlain(ButtonFactory.MAINMESSAGEVIEW,this);
    messageViewButt.setEnabled(false);
    cont.add(messageViewButt);

    splash.advance();

    meterMediumButt = ButtonFactory.makeOob(ButtonFactory.MAINDEPTHMETER_MEDIUM,this);
    meterMediumButt.setVisible(true);
    cont.add(meterMediumButt);
    meterFocusButt = ButtonFactory.makeOob(ButtonFactory.MAINDEPTHMETER_FOCUS,this);
    meterFocusButt.setVisible(false);
    cont.add(meterFocusButt);
    meterOverviewButt = ButtonFactory.makeOob(ButtonFactory.MAINDEPTHMETER_OVERVIEW,this);
    meterOverviewButt.setVisible(false);
    cont.add(meterOverviewButt);

    // background
    JLabel backLabel=new JLabel(Ggui.imgIconGet("MAINBACK"));
    backLabel.setBounds(new Rectangle(0,0,_WIDTH,_HEIGHT));
    cont.add(backLabel);

    this.pack();
    Insets ins=this.getInsets();
    this.setSize(_WIDTH+ins.left+ins.right,_HEIGHT+ins.top+ins.bottom);
    this.setVisible(true);

    splash.dispose();

    handlers.go();

    clickClip     =Applet.newAudioClip(Ggui.resourceGet("CLICK_SOUND"));
    laserClip     =Applet.newAudioClip(Ggui.resourceGet("LASER_SOUND"));
    quipDefault   =Applet.newAudioClip(Ggui.resourceGet("QUIPCLIPS_DEFAULT"));
    quipOffDefault=Applet.newAudioClip(Ggui.resourceGet("QUIPCLIPS_OFF"));
  }

  public void go()
  {
    playscenePanel.finishInit();       // needs story engine going
    handlers.eventIn(ButtonFactory.MAINPLAYINTRO);
  }

  Rectangle panelRect=new Rectangle(28,60,640,480);  // was 0,0 without using integral layered pane

  private RPanel doPanel(RPanel p)
  {
    p.setBounds(panelRect);
    lp.add(p,JLayeredPane.DEFAULT_LAYER);
    return p;
  }

  private void stackPanels()
  {
    lp=this.getLayeredPane();
    lp.setLayout(null);
    lp.setBorder(null);

    startupNewPanel =  (StartupNewGamePanel)doPanel(new StartupNewGamePanel(this));
    introPanel =                (IntroPanel)doPanel(new IntroPanel(this));

    msgPanel = 									(MsgPanel)doPanel(new MsgPanel(this));
    inetWaitPanel =                         doPanel(new InternetWaitPanel(this));
    inetErrorPanel =                        doPanel(new InternetErrorPanel(this));
    filePanel =                  (FilePanel)doPanel(new FilePanel(this));

    splash.advance();

    financialsPanel =      (FinancialsPanel)doPanel(new FinancialsPanel(this));
    chooserPanel =            (ChooserPanel)doPanel(new ChooserPanel(this));
    createSoldierPanel =(CreateSoldierPanel)doPanel(new CreateSoldierPanel(this));
    mosPanel =                    (MOSPanel)doPanel(new MOSPanel(this));
    //cannedCharPanel =      (CannedCharPanel)doPanel(new CannedCharPanel(this));
    //cannedHelpPanel =      (CannedHelpPanel)doPanel(new CannedHelpPanel(this));
    loadCharPanel =          (LoadCharPanel)doPanel(new LoadCharPanel(this));
    mapPanel =                              doPanel(new MapPanel(this));
    charEditPanel =                         doPanel(new CharEditPanel(this));
    //charPersonalityPanel = (CharPersonality)doPanel(new CharPersonality(this));
    charGoalsHelpPanel =(CharGoalsHelpPanel)doPanel(new CharGoalsHelpPanel(this));   // before ....
    splash.advance();
    charGoalsViewAllPanel =                 doPanel(new CharGoalsViewAllPanel(this));
    charGoalsListPanel =(CharGoalsListPanel)doPanel(new CharGoalsListPanel(this));
    charGoalsPanel =       (CharGoals5Panel)doPanel(new CharGoals5Panel(this));
    charInteriorPanel=                      doPanel(new CharInteriorPanel(this));
    //charSelectPanel =      (CharSelectPanel)doPanel(new CharSelectPanel(this));
    //charNamePanel =          (CharNamePanel)doPanel(new CharNamePanel(this));
    charResourcesPanel =(CharResourcesPanel)doPanel(new CharResourcesPanel(this));
    charValuesPanel =      (CharValuesPanel)doPanel(new CharValuesPanel(this));
    historyPanel =                          doPanel(new HistoryPanel(this));
    achievementsPanel =                     doPanel(new AchievementPanel(this));
    dutyStationsPanel =                     doPanel(new DutyStationsPanel(this));
    referralPanel =                         doPanel(new ReferralPanel(this));
    //linksPanel =                            doPanel(new LinksPanel(this));
    //contactsPanel       =                 doPanel(new ContactsPanel(this));
    downloadPanel =                         doPanel(new DownloadPanel(this));
    downloadCharPanel =  (DownloadCharPanel)doPanel(new DownloadCharPanel(this));
    downloadStoryPanel = (DownloadStoryPanel)doPanel(new DownloadStoryPanel(this));
    uploadPanel =                           doPanel(new UploadPanel(this));
    uploadCharPanel =      (UploadCharPanel)doPanel(new UploadCharPanel(this));
    uploadStoryPanel=     (UploadStoryPanel)doPanel(new UploadStoryPanel(this));
    //loginPanel =                (LoginPanel)doPanel(new LoginPanel(this));
    messagePanel =            (MessagePanel)doPanel(new MessagePanel(this));
      splash.advance();
    newUserPanel =            (NewUserPanel)doPanel(new NewUserPanel(this));
    updatesPanel =        (GameUpdatesPanel)doPanel(new GameUpdatesPanel(this));
    saveGamePanel =                         doPanel(new SaveGamePanel(this));
    saveCharPanel =          (SaveCharPanel)doPanel(new SaveCharPanel(this));
    loadGamePanel =          (LoadGamePanel)doPanel(new LoadGamePanel(this));
    optionsPanel =                          doPanel(new OptionsPanel(this));
    sceneTestPanel =    (SceneListTestPanel)doPanel(new SceneListTestPanel(this));
      splash.advance();
    loginOrRegisterPanel =  (LoginOrRegisterPanel)doPanel(new LoginOrRegisterPanel(this));
    achievementMedalPanel =                 doPanel(new AchievementMedalPanel(this));
    referralPanel =                         doPanel(new ReferralPanel(this));
    contactArmyPanel =    (ContactArmyPanel)doPanel(new ContactArmyPanel(this));
    opsPanel =						                  doPanel(new OpsPanel(this));

    mainPanel =                  (MainPanel)doPanel(new MainPanel(this));
    //this needs to be deepest in the stack.
    playscenePanel =        (PlayScenePanel)doPanel(new PlayScenePanel(this));
  }

  private void positionAndSize()
  {
    Dimension screenSz=java.awt.Toolkit.getDefaultToolkit().getScreenSize();
    this.setBounds(new Rectangle((screenSz.width-_WIDTH)/2,
                                 (screenSz.height-_HEIGHT)/2,_WIDTH,_HEIGHT));
  }

  public void setCharName(String n)
  {
    globals.charinsides.charName = n;
    soldierName.setText(n);
  }

  public String getCharName() {
  	return(globals.charinsides.charName);
  }

  public void setTitleBar(String imgPropName)
  {
    if(imgPropName == null)
    {
      titleBar.setVisible(false);
    }
    else
    {
      titleBar.setVisible(true);
      titleBar.setIcon(Ggui.imgIconGet(imgPropName));
      titleBar.setSize(titleBar.getPreferredSize());
    }
  }

  public void setActorIndex(int idx)
  {
    globals.charIndex=idx;
    String name=Ggui.getProp("ACTOR"+idx);
    globals.charHead=     Ggui.imgIconGet(name+"portrait");
    globals.charHead_grey=Ggui.imgIconGet(name+"portrait_grey");
    soldierHead.setIcon(globals.charHead);
    globals.charinsides.actorName=name;
  }

  public int getActorIndex() {
  	return(globals.charIndex);
  }

  public RecruitsGuiMessager getMessager()
  {
    return gmess;
  }

  public void setStatusLine(String s)
  {
    statusLine.setText(s);
  }

  public String getStatusLine()
  {
    return statusLine.getText();
  }

  public void clickButton()
  {
    if(rmain.soundEffectsOn)
      cPlayer.play(clickClip);
  }

  static public void doLaser()
  {
    if(RecruitsMain.instance().soundEffectsOn)
      cPlayer.play(laserClip);
  }

  static public void startLaser()
  {
    if(RecruitsMain.instance().soundEffectsOn)
      loopPlayer.play(laserClip);
  }

  static public void stopLaser()
  {
    if(RecruitsMain.instance().soundEffectsOn)
      loopPlayer.stop();
  }
  static public void playDefaultQuip(boolean nocheck)
  {
    quipPlayer.play(quipDefault);
  }
  static public void playDefaultQuip()
  {
    if(RecruitsMain.instance().soundEffectsOn)
      quipPlayer.play(quipDefault);
  }
  static public void playQuipOff()
  {
    if(RecruitsMain.instance().soundEffectsOn)
      quipPlayer.play(quipOffDefault);
  }
  static public void playThisQuip(AudioClip cl)
  {
    if(RecruitsMain.instance().soundEffectsOn)
      quipPlayer.play(cl);
  }
}

// EOF
