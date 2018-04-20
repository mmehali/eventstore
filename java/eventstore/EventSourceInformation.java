package eventstore;

import java.util.UUID;

/** Contains information about an event source. **/

public class EventSourceInformation
{
    private  UUID id;
    private  long initialVersion;
    private  long currentVersion;

    public EventSourceInformation(UUID id, long initialVersion, long currentVersion)
    {
        this.id = id;
        this.currentVersion = currentVersion;
        this.initialVersion = initialVersion;
    }

    public long getCurrentVersion(){
        return currentVersion; 
    }

    public long getInitialVersion(){
      return initialVersion; 
    }

    public UUID getId(){
        return id; 
    }
}
