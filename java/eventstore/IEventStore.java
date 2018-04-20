package eventstore;

import java.util.UUID;

public interface IEventStore {
	/**
	 * Reads from the stream from the "minVersion" up until "maxVersion". Returned
	 * event stream does not contain snapshots. This method is used when snapshots
	 * are stored in a separate store.
	 * 
	 * @param id
	 *            The id of the event source that owns the events.
	 * @param minVersion
	 *            The minimum version number to be read
	 * @param maxVersion
	 *            The maximum version number to be read
	 * @return All the events from the event source between specified version
	 *         numbers
	 */

	CommittedEventStream readFrom(UUID id, long minVersion, long maxVersion);

	/**
	 * Persists the <paramref name="eventStream"/> in the store as a single and
	 * atomic commit.
	 * 
	 * @param eventStream
	 *            The <see cref="UncommittedEventStream"/> to commit.
	 *            <exception cref="ConcurrencyException">Occurs when there is
	 *            already a newer version of the event provider stored in the event
	 *            store.</exception>
	 */

	void store(UncommittedEventStream eventStream);
}
