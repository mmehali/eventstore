package eventstore;

import java.util.UUID;

/***
 * 
 * Holds the full state of an aggregate root at a certain version.
 * 
 * @author sofiane
 *
 */

public class Snapshot {

	/**
	 * Gets the value which uniquely identifies the aggregate root to which the
	 * snapshot applies.
	 * 
	 */
	private UUID eventSourceId;

	/**
	 * Gets the position at which the snapshot applies.
	 */
	private long version;

	/**
	 * Gets the snapshot or materialized view of the stream at the revision
	 * indicated.
	 */
	private Object payload;

	/**
	 * Initializes a new instance of the Snapshot class.
	 *
	 * <param name="eventSourceId">The value which uniquely identifies the stream to
	 * which the snapshot applies.</param> <param name="version">The position at
	 * which the snapshot applies.</param> <param name="payload">The snapshot or
	 * materialized view of the stream at the revision indicated.</param>
	 */
	public Snapshot(UUID eventSourceId, long version, Object payload) {
		this.eventSourceId = eventSourceId;
		this.version = version;
		this.payload = payload;
	}

	/**
	 * Initializes a new instance of the Snapshot class.
	 */
	protected Snapshot() {
	}

	public UUID getEventSourceId() {
		return eventSourceId;
	}

	public long getVersion() {
		return version;
	}

	public Object getPayload() {
		return payload;
	}

}
