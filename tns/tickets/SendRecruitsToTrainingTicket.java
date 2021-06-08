package tns.tickets;
import tns.agents.*;
import tns.roles.*;
import tns.actions.*;
import tns.connectors.*;
import tns.frames.*;
import tns.messages.*;
import tns.goals.*;
import mil.navy.nps.relate.*;
import java.util.*;

/**
 * In this ticket the recruiter checks to see if he knows any trainers directly.
 * If he does, he looks the each trainer’s influence and sends the recruits to 
 * the trainer with the highest influence, supporting the rich-get-richer 
 * phenomenon.  The recruit and the trainer each create a link to each other and 
 * add the other person to their mental maps.  The recruiter then receives 
 * either experience or influence for each recruit he sends to a trainer.  If 
 * the recruiter does not know a trainer directly, then he sends out a find 
 * person message looking for a trainer role.
 * @author  Rob Michael and Zac Staples
 */
public class SendRecruitsToTrainingTicket extends Ticket {
    
    /** 
     * Creates a new instance of SendRecruitsToTrainingTicket 
     * @param role The role that owns this ticket.
     * @param c The connector the recruit extended.
     */
    public SendRecruitsToTrainingTicket(Role role, Connector c) {
        super(role, "SendRecruitsToTrainingTicket");
        TerroristAgent recruiter = (TerroristAgent)((TNSRole)role).getAgent();
        Agent preferredTrainer = null;
        Vector trainers = new Vector();
        Vector directlyLinkedAgents = ((TerroristAgent)recruiter).getMentalMap().getDirectlyLinkedAgents();
        Enumeration e = directlyLinkedAgents.elements();
        while (e.hasMoreElements()) {
            Agent a = (Agent)e.nextElement();
            Vector roles = a.getRoleVector();
            Enumeration e2 = roles.elements();
            while (e2.hasMoreElements()) {
                Role r = (Role)e2.nextElement();
                if (r instanceof TrainerRole) {
                    trainers.add(a);
                } // end if
            } // end while
        } // end while
//        System.out.println(recruiter.getEntityName() + " knows " + trainers.size() + " trainers.");
        if (trainers.size() > 0) {
            Agent recruit = ((TNSRole)((GetTrainedConnector)c).getRole()).getAgent();
            int highestInfluence = 0;
            int influence = 0;
            Ticket ticket = null;
            Frame frame = null;
            Enumeration e3 = trainers.elements();
            while (e3.hasMoreElements()) {
                TerroristAgent ta = (TerroristAgent)e3.nextElement();
                influence = ((TerroristAgentPersonality)ta.getPersonality()).getInfluence();
                if (influence > highestInfluence) {
                    highestInfluence = influence;
                    preferredTrainer = ta;
                } // end if
            } // end while
            addFrame(new MakeDoubleLinkAction((TerroristAgent)preferredTrainer, (TerroristAgent)recruit));
            Vector goals = ((TNSRole)((GetTrainedConnector)c).getRole()).getGoalListVec();
            Enumeration e4 = goals.elements();
            while (e4.hasMoreElements()) {
                TNSGoal g = (TNSGoal)e4.nextElement();
                if (g instanceof GetTrainedGoal) {
                    addFrame(new MarkGoalCompleteAction(g));
                } // end if
            } // end while
            addFrame(new StandardRewardAction(recruiter, 1));
            // update the history between the Recruiter and the Trainer
            AgentPair historyPair = new AgentPair(recruiter, preferredTrainer);
            recruiter.changeHistory(historyPair, 5);
        } else {
            Class[] argumentTypes = {Agent.class, String.class, String.class, int.class};
            Object[] arguments = {recruiter, "TrainerRole", "FindPersonTicket", new Integer(Message.FIND_PERSON_MESSAGE)};
            addFrame(new PutMessageInOutboxAction(recruiter, "FindPersonMessage", argumentTypes, arguments));
       } // end if-else
    } // end class SendRecruitsToTrainingTicket constructor
    
} // end class SendRecruitsToTrainingTicket
