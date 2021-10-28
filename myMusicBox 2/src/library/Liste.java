package library;

import javafx.scene.control.Button;

public class Liste {

    private String singerName;
    private String songName;

    public Liste(String singerName, String songName) {
        this.singerName = singerName;
        this.songName = songName;
    }

    public String getSingerName() {
        return singerName;
    }

    public String getSongName() {
        return songName;
    }
}
