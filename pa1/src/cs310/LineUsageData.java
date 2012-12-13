package cs310;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/* LineUsageData.java
 * Handle one line's data, using a SLL
 */

public class LineUsageData {
        //	private SinglyLinkedList<Usage> data;
        private Map<String, Usage> data;

	public LineUsageData() {
		data = new HashMap<String, Usage>();
	}

	// add one sighting of a use on this line
	public void addObservation(String username)
        {
		Usage usage;

                usage = data.get(username);
                if (usage == null) //if never found that user; create  one
                    usage = new Usage(username);

                usage.increment();
                data.put(username, usage);


	}

	// find the user with the most sightings on this line
	public Usage findMaxUsage() {
		Usage user;
		Usage record = new Usage("<NONE>");  // with count 0
                Set keySet = data.keySet();
                Iterator itr = keySet.iterator();

                while (itr.hasNext())
                {
                    user = (Usage) data.get(itr.next());
                    if (user.getCount() > record.getCount()) 
				record = user;
                }
		return record;
	}
}
