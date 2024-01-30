
import java.util.HashSet;

public class Playlist {  // PLAYLIST CLASS TO STORE SONGS
    int id;
    int hNum, rNum, bNum;  // NUMBER OF SONGS IN EPIC CATEGORIES
    HashSet<Song> songs;
    public Playlist(int id, int songCount){
        this.id = id;
        songs = new HashSet<>(songCount);
        this.hNum = 0;
        this.rNum = 0;
        this.bNum = 0;
    }
    public void addSong(Song song){
        songs.add(song);
    }

}
