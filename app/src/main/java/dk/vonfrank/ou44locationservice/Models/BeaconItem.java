package dk.vonfrank.ou44locationservice.Models;

/**
 * Created by Von Frank on 29-03-2017.
 */

public class BeaconItem {

    private String instanceId;
    private String alias;
    private String UUID;
    private String room;
    private String roomName;
    private String level;

    public BeaconItem(String instanceId, String alias, String UUID, String room, String roomName, String level) {
        this.instanceId = instanceId;
        this.alias = alias;
        this.UUID = UUID;
        this.room = room;
        this.roomName = roomName;
        this.level = level;
    }

    public String getInstanceId(){
        return this.instanceId;
    }

    public String getAlias(){
        return this.alias;
    }

    public String getUUID(){
        return this.UUID;
    }

    public String getRoom(){
        return this.room;
    }

    public String getRoomName(){
        return this.roomName;
    }

    public String getLevel(){
        return this.level;
    }
}
