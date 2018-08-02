package hajjcare.hajj.com.hajjcare.models.network;

/**
 * Created by amra on 8/2/2018.
 */

public class HelpMeRequest {
    private String id;
    private String lat;
    private String Long;

    public HelpMeRequest(String id, String lat, String aLong) {
        this.id = id;
        this.lat = lat;
        Long = aLong;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        id = id;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLong() {
        return Long;
    }

    public void setLong(String aLong) {
        Long = aLong;
    }
}
