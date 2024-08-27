package Models;

public class Track {
    private int trackId;
    private Platform platform; 

    public Track(int trackId, Platform platform) {
        this.trackId = trackId;
        this.platform = platform;
    }

    public int getTrackId() {
        return trackId;
    }

    public void setTrackId(int trackId) {
        this.trackId = trackId;
    }

    public Platform getPlatform() {
        return platform;
    }

    public void setPlatform(Platform platform) {
        this.platform = platform;
    }

	@Override
	public String toString() {
		return "Track [trackId=" + trackId + ", platform=" + platform + "]";
	}
    
}
