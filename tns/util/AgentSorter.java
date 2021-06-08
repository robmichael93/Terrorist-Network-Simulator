package tns.util;
import tns.agents.*;

/**
 * This class is simple merge sort with an agent's influence as the key value
 * to sort on.
 * @author  Rob Michael and Zac Staples
 */
public class AgentSorter extends MergeSort {
    
    /** Creates a new instance of MyMergeSort */
    public AgentSorter() {
        super();
    } // end AgentSorter constructor
    
    /**
     * Returns a comparator value based on agents' influence values.
     * @param beginLoc The index of the beginning element of the array to sort.
     * @param endLoc The index of the ending element of the array to sort.
     * @return int Returns -1 if the beginning element is greater than the
     * ending element, 1 if it is greater, and 0 if they are the same.
     */
    public int compareElementsAt(int beginLoc, int endLoc) {
        int beginAgentInfluence = ((TerroristAgentPersonality)((TerroristAgent)toSort[beginLoc]).getPersonality()).getInfluence();
        int endAgentInfluence = ((TerroristAgentPersonality)((TerroristAgent)toSort[endLoc]).getPersonality()).getInfluence();
        if (beginAgentInfluence > endAgentInfluence) {
            return -1;
        } else if (beginAgentInfluence < endAgentInfluence) {
            return 1;
        } else {
            return 0;
        } // end if-else
    } // end compareElementsAt
    
} // end class AgentSorter
