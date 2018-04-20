package eventstore;

import java.time.LocalDateTime;
import java.util.UUID;

/// Represents an event which has been persisted.

public class CommittedEvent /** implements IPublishableEvent */
{
	private Object _payload;
	private long _eventSequence;
	private UUID _eventIdentifier;
	private LocalDateTime _eventTimeStamp;
	private UUID _eventSourceId;
	private UUID _commitId;
	private Version _eventVersion;

	/**
	 * 
	 * @param commitId
	 * @param eventIdentifier
	 * @param eventSourceId
	 * @param eventSequence
	 * @param eventTimeStamp
	 * @param payload
	 * @param eventVersion
	 */
	public CommittedEvent(UUID commitId, UUID eventIdentifier, UUID eventSourceId, long eventSequence,
			LocalDateTime eventTimeStamp, Object payload, Version eventVersion) {
		_payload = payload;
		_eventVersion = eventVersion;
		_commitId = commitId;
		_eventSourceId = eventSourceId;
		_eventSequence = eventSequence;
		_eventIdentifier = eventIdentifier;
		_eventTimeStamp = eventTimeStamp;
	}

	/**
	 * Id of a commit in which this event was stored (usually corresponds to a
	 * command id which caused this event).
	 * 
	 * @return
	 */
	public UUID getCommitId() {
		return _commitId;
	}

	/**
	 * Gets the payload of the event.
	 * 
	 * @return
	 */
	public Object getPayload() {
		return _payload;
	}

	/**
	 * Gets the unique identifier for this event.
	 * 
	 * @return
	 */
	public UUID getEventIdentifier() {
		return _eventIdentifier;
	}

	/**
	 * Gets the time stamp for this event.
	 * 
	 ** @return a <see cref="DateTime"/> UTC value that represents the point in time
	 *         where this event occurred.
	 **/
	public LocalDateTime getEventTimeStamp() {
		return _eventTimeStamp;
	}

	/**
	 * Gets the CLR version of event type that was used to persist data.
	 * 
	 * @return
	 */
	public Version getEventVersion() {
		return _eventVersion;
	}

	/**
	 * /// Gets the id of the event source that caused the event. /// <value>The id
	 * of the event source that caused the event.</value
	 * 
	 * @return
	 */
	public UUID getEventSourceId() {
		return _eventSourceId;
	}

	/**
	 * Gets the event sequence number. <remarks> An sequence of events always starts
	 * with <c>1</c>. So the first event in sequence has the
	 * <see cref="EventSequence"/> value of <c>1</c>.
	 * 
	 * @return A number that represents the order of where this events occurred in
	 */
	public long getEventSequence() {
		return _eventSequence;
	}

//	public String toString() {
//		return String.format("{0}[{1}]", getPayload().GetType().FullName, getEventIdentifier().toString("D"));
//	}
}
