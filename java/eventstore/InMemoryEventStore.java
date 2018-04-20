package eventstore;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.UUID;
import java.util.stream.Collectors;

//An in memory event store that can be used for unit testing purpose. 
public class InMemoryEventStore implements IEventStore, ISnapshotStore {
	private Map<UUID, Queue<CommittedEvent>> commitedEvents = new HashMap<UUID, Queue<CommittedEvent>>();
	private Map<UUID, Snapshot> snapshots = new HashMap<UUID, Snapshot>();

	/*
	 * (non-Javadoc)
	 * 
	 * @see eventstore.IEventStore#store(eventstore.UncommittedEventStream)
	 */
	public void store(UncommittedEventStream eventStream) {
		if (!eventStream.isNotEmpty())
			return;
        //verifier si la liste des events est commitée
		Queue<CommittedEvent> theCommitedEvents = commitedEvents.get(eventStream.getSourceId());
        
		//aucun evenement commité. ajoute les evenements non commités a l'eventStore
		if (theCommitedEvents == null) {
			theCommitedEvents = new LinkedList<CommittedEvent>();
			commitedEvents.put(eventStream.getSourceId(), theCommitedEvents);
		}

		Iterator<UncommittedEvent> it = eventStream.getEnumerator();

		while (it.hasNext()) {
			UncommittedEvent evnt = it.next();
			CommittedEvent e = new CommittedEvent(eventStream.getCommitId(), evnt.getEventIdentifier(),
					eventStream.getSourceId(), evnt.getEventSequence(), evnt.getEventTimeStamp(), evnt.getPayload(),
					evnt.getEventVersion());
			theCommitedEvents.add(e);
		}
	}

	/**
	 * Gets a snapshot of a particular event source, if one exists. Otherwise,
	 * returns <c>null</c>.
	 * 
	 * SELECT TOP 1 * FROM Snapshots WHERE [EventSourceId]=@EventSourceId ORDER BY
	 * Version DESC
	 **/
	public Snapshot getSnapshot(UUID eventSourceId, long maxVersion) {
		Snapshot result = snapshots.get(eventSourceId);
		return result.getVersion() > maxVersion ? null : result;
	}

	/**
	 * SELECT Id, EventSourceId, Name, Version, TimeStamp, Data, Sequence FROM
	 * Events WHERE EventSourceId = ?EventSourceId AND Sequence >=
	 * ?EventSourceMinVersion AND Sequence <= ?EventSourceMaxVersion ORDER BY
	 * Sequence
	 */
	public CommittedEventStream readFrom(UUID id, long minVersion, long maxVersion) {
		Queue<CommittedEvent> listEvents = commitedEvents.get(id);
		if (listEvents != null) {
//			List<CommittedEvent> committedEvents1 = listEvents.stream()
//					.filter((e) -> (e.getEventSequence() >= minVersion && e.getEventSequence() <= maxVersion))
//					.collect(Collectors.toList());
		//	return new CommittedEventStream(id, committedEvents1);
		}
		return new CommittedEventStream(id);
	}

	// Saves a snapshot of the specified event source.
	public void saveSnapshot(Snapshot snapshot) {
		snapshots.put(snapshot.getEventSourceId(), snapshot);
	}

}
