package eventstore;

import java.time.LocalDateTime;
import java.util.UUID;



/** Represents an event which has not been yet persisted.
 * 
 * @author Yanis
 *
 */
public class UncommittedEvent /*implements IPublishableEvent */{
	private Object payload;
	private long eventSequence;
	private UUID eventIdentifier;
	private LocalDateTime eventTimeStamp;
	private UUID eventSourceId;
	private long initialVersionOfEventSource;
	private Version eventVersion;
	private UUID commitId;

	
	public UncommittedEvent(UUID eventIdentifier, UUID eventSourceId, long eventSequence,
			long initialVersionOfEventSource, LocalDateTime eventTimeStamp, Object payload, Version eventVersion) {
		this.payload = payload;
		this.eventVersion = eventVersion;
		this.initialVersionOfEventSource = initialVersionOfEventSource;
		this.eventSourceId = eventSourceId;
		this.eventSequence = eventSequence;
		this.eventIdentifier = eventIdentifier;
		this.eventTimeStamp = eventTimeStamp;
	}

	
	/** Gets the initial version of event source (the version it was just after
	 creating/retrieving from the store)**/
	public long getInitialVersionOfEventSource() {
		return initialVersionOfEventSource;
	}

	
	/** Gets the payload of the event.**/
	public Object getPayload() {
		return payload;
	}

	
	/** Gets the unique identifier for this event.**/
	
	public UUID getEventIdentifier() {
		return eventIdentifier;
	}

	/** Gets the time stamp for this event.
	 <value>a <see cref="DateTime"/> UTC value that represents the point
	in time where this event occurred.</value>
	**/
	public LocalDateTime getEventTimeStamp() {
	return eventTimeStamp;
	}

	/** Gets the id of the event source that caused the event.
	 <value>The id of the event source that caused the event.</value>
	 **/
	public UUID getEventSourceId() {
		return eventSourceId;
	}

	
	/** Gets the event sequence number.
	remarks:
	 An sequence of events always starts with 1. So the first event in a
	 sequence has the value of 1.
	// <value>A number that represents the order of where this events occurred in
	/// the sequence.</value>
	**/
	public long getEventSequence() {
		return eventSequence;
	}

	/** If of a commit in which this event is to be stored (usually corresponds to a
	 command id which caused this event).*/
	public UUID getCommitId() {
		return this.commitId;
	}

	public void onAppendedToStream(UUID streamCommitId) {
		this.commitId = streamCommitId;
	}


	public Version getEventVersion() {
		return eventVersion;
	}

	public String toString(){
    return String.format("{0}[{1}]", payload, eventIdentifier);
    }
}
