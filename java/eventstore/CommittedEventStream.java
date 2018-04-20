package eventstore;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CommittedEventStream /* implements List<CommittedEvent> */ {

	private long fromVersion;
	private long toVersion;
	private UUID sourceId;
	private List<CommittedEvent> events = new ArrayList<CommittedEvent>();

	public CommittedEventStream(UUID sourceId, List<CommittedEvent> events) {
		this.sourceId = sourceId;

		if (events != null)
			this.events = new ArrayList<CommittedEvent>(events);

		validateEventInformation(this.events);

		if (this.events.size() > 0) {
			CommittedEvent first = this.events.get(0);
			fromVersion = first.getEventSequence();

			CommittedEvent last = this.events.get(this.events.size());
			toVersion = last.getEventSequence();

			toVersion = this.events.OrderByDescending(evnt -> evnt.getEventSequence()).First().EventSequence;
		}
	}

	private void validateEventInformation(List<CommittedEvent> events) {
		// An empty event stream is allowed.
		if (events == null || events.isEmpty())
			return;

		CommittedEvent firstEvent = events.get(0);
		long startSequence = firstEvent.getEventSequence();

		UUID expectedSourceId = this.sourceId;
		long expectedSequence = startSequence;

		validateSingleEvent(firstEvent, 0, expectedSourceId, expectedSequence);

		for (int position = 1; position < this.events.size(); position++) {
			CommittedEvent evnt = this.events.get(position);
			expectedSourceId = this.sourceId;
			expectedSequence = startSequence + position;

			validateSingleEvent(evnt, position, expectedSourceId, expectedSequence);
		}
	}

	public long getFromVersion() {
		return this.fromVersion;
	}

	public long getToVersion() {
		return this.toVersion;
	}

	public boolean isEmpty() {
		return this.events.size() == 0;
	}

	public UUID getSourceId() {
		return this.sourceId;
	}

	public long getCurrentSourceVersion() {
		return this.toVersion;
	}

	public CommittedEventStream(UUID sourceId) {
		this.sourceId = sourceId;
	}

	private void validateSingleEvent(CommittedEvent evnt, long position, UUID expectedSourceId, long expectedSequence) {
		if (evnt == null)
			throw new RuntimeException(
					"events :The events stream contains a null reference at position " + position + ".");

		if (evnt.getEventSourceId() != expectedSourceId) {
			String msg = String.format(
					"The events stream contains an event that is related to another event "
							+ "source at position {0}. Expected event source id {1}, but actual was {2}",
					position, this.sourceId, evnt.getEventSourceId());
			throw new RuntimeException("events :" + msg);
		}

		if (evnt.getEventSequence() != expectedSequence) {
			String msg = String.format(
					"The events stream contains an committed event with an illigal sequence at "
							+ "position {0}. The expected sequence is {1}, but actual was {2}.",
					position, expectedSequence, evnt.getEventSequence());

			throw new RuntimeException("events" + msg);
		}
	}

	// if(events.IsEmpty())
	// return;

	// var first = events.First();

	// var eventCounter = 0;
	// var eventSourceId = first.EventSourceId;
	// var currentEventSequence = first.EventSequence;

	// foreach(var evnt in events.Skip(1))
	// {
	// if(evnt.EventSourceId != eventSourceId)
	// {
	// var msg =
	// String.Format("Event {0} did not contain the expected event source id {1}. "+
	// "Actual value was {2}.",
	// evnt.EventIdentifier, eventSourceId, evnt.EventSourceId);
	// throw new ArgumentOutOfRangeException("events", msg);
	// }
	// }
}
