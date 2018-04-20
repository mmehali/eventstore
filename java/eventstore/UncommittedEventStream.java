package eventstore;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.UUID;


/**
 * Represents a stream of events which has not been persisted yet. They are to
 * be persisted atomicaly as a single commit with given "commitId" ID.
 **/
public class UncommittedEventStream {
	private UUID commitId;
	private UUID singleSource;
	private boolean hasSingleSource = true;
	private List<UncommittedEvent> uncomitedEvents = new ArrayList<UncommittedEvent>();
	private Map<UUID, EventSourceInformation> eventSourceInformation = new HashMap<UUID, EventSourceInformation>();

	/** Creates new uncommitted event stream. **/
	public UncommittedEventStream(UUID commitId) {
		this.commitId = commitId;
	}

	/**
	 * Appends new event to the stream. <param name="evnt">New event.</param>
	 **/
	public void append(UncommittedEvent evnt) {
	if (uncomitedEvents.size() > 0 && hasSingleSource) {
	  if (uncomitedEvents.get(0).getEventSourceId() != evnt.getEventSourceId()) {
		hasSingleSource = false;
		}
	  } 
	else if (uncomitedEvents.size() == 0) {
	  singleSource = evnt.getEventSourceId();
	  }
	uncomitedEvents.add(evnt);
	evnt.onAppendedToStream(commitId);
	UpdateEventSourceInformation(evnt);
	}

	private void UpdateEventSourceInformation(UncommittedEvent evnt) {
		EventSourceInformation newInformation = new EventSourceInformation(evnt.getEventSourceId(), evnt.getInitialVersionOfEventSource(), evnt.getEventSequence());
		eventSourceInformation.put(evnt.getEventSourceId(), newInformation);
	}

	/// <summary>
	/// Gets information about sources of events in this stream.
	/// </summary>
	public Collection <EventSourceInformation> sources(){
    return eventSourceInformation.values(); 
    }

	/**
	 * Returns whether this stream of events has a single source. An empty stream
	 * has single source by definition.
	 **/
	public boolean hasSingleSource() {
		return hasSingleSource;
	}

	/// <summary>
	/// If the stream has a single source, it returns this source.
	/// </summary>
	/// <exception cref="InvalidOperationException">If the stream has multiple
	/// sources.</exception>
	public UUID getSourceId() {
		if (!hasSingleSource()) {
			throw new RuntimeException("Event stream must have a single source in order to retrieve its source.");
		}
		return singleSource;
	}

	public long initialVersion() {
	if (!hasSingleSource()){
		throw new RuntimeException("Event stream must have a single source in order to retrieve its source initial version.");
		}
	return eventSourceInformation.get(getSourceId()).getInitialVersion();
	}

	public long currentVersion() {
		if (!hasSingleSource()) {
			throw new RuntimeException(
					"Event stream must have a single source in order to retrieve its source current version.");
		}
		return eventSourceInformation.get(getSourceId()).getInitialVersion();
	}

	/**
	 *  Returns if the stream contains at least one event.
	 * @return
	 */
	public boolean isNotEmpty(){
	return uncomitedEvents.size() > 0;
	}

	/** Returns unique id of commit associated with this stream. **/
	public UUID getCommitId() {
		return commitId;
	}

	public Iterator<UncommittedEvent> getEnumerator() {
	return uncomitedEvents.iterator();
	}

	
}
