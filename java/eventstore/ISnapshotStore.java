package eventstore;

import java.util.UUID;

/**
 * A <see cref="Snapshot"/> store. Can store and retrieve a
 * <see cref="Snapshot"/>.
 * 
 * @author sofiane
 *
 */
public interface ISnapshotStore {

	/**
	 * Gets a snapshot of a particular event source, if one exists. Otherwise,
	 * returns <c>null</c>.
	 * 
	 * @param eventSourceId
	 *            Indicates the event source to retrieve the snapshot for.
	 * @param maxVersion
	 *            Indicates the maximum allowed version to be returned.
	 * @return Returns the most recent <see cref="Snapshot"/> that exists in the
	 *         store. If the store has a snapshot that is more recent than the
	 *         <paramref name="maxVersion"/>, then <c>null</c> will be returned.
	 */
	Snapshot getSnapshot(UUID eventSourceId, long maxVersion);

	/**
	 * Persists a <see cref="Snapshot"/> of an <see cref="EventSource"/>
	 * 
	 * @param snapshot
	 *            The <see cref="Snapshot"/> that is being saved
	 */
	void saveSnapshot(Snapshot snapshot);
}
