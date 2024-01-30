import java.util.ArrayList;
import java.util.Iterator;

public class MyMaxHeap {       // HEAP OF THE SONGS THAT IS NOT IN THE EPIC BLEND
    private ArrayList<Song> array;
    private int size;
    private String typeOfHeap;

    MyMaxHeap(Playlist[] playlists, String typeOfHeap) {
        this.typeOfHeap = typeOfHeap;
        this.size = 0;
        array = new ArrayList<Song>();
        array.add(null);
        for (Playlist playlist : playlists) {
            Iterator iterator = playlist.songs.iterator();
            while (iterator.hasNext()) {
                Song songToAdd = (Song) iterator.next();
                array.add(songToAdd);
                size++;
            }
        }
        buildHeap();
    }

    MyMaxHeap(ArrayList<Song> passedSongs, String typeOfHeap) {
        this.typeOfHeap = typeOfHeap;
        this.size = 0;
        array = new ArrayList<Song>();
        array.add(null);
        for (int i = 0; i < passedSongs.size(); i++) {
            Song song = passedSongs.get(i);
            array.add(song);
            size++;
        }
        buildHeap();
    }


    public void insert(Song song) {
        size++;
        array.add(song);
        int hole = size;
        if (typeOfHeap.equals("headache")) {
            while (hole > 1 && song.compareHscore(array.get(hole / 2)) > 0) {
                Song parent = array.get(hole / 2);
                array.set(hole / 2, song);
                array.set(hole, parent);
                hole = hole / 2;
            }
        } else if (typeOfHeap.equals("roadtrip")) {
            while (hole > 1 && song.compareRscore(array.get(hole / 2)) > 0) {
                Song parent = array.get(hole / 2);
                array.set(hole / 2, song);
                array.set(hole, parent);
                hole = hole / 2;
            }
        } else if (typeOfHeap.equals("blissful")) {
            while (hole > 1 && song.compareBscore(array.get(hole / 2)) > 0) {
                Song parent = array.get(hole / 2);
                array.set(hole / 2, song);
                array.set(hole, parent);
                hole = hole / 2;
            }
        }
    }

    public Song maxItem() {
        return array.get(1);
    }

    public Song removeMax() {
        Song maxItem = maxItem();
        array.set(1, array.get(size));
        size--;
        percolateDown(1);
        return maxItem;
    }

    private void buildHeap() {
        for (int i = size / 2; i > 0; i--) {
            percolateDown(i);
        }
    }

    public int size() {
        return size;
    }

    private void percolateDown(int hole) {
        int child;
        Song temp = array.get(hole);

        if (typeOfHeap.equals("headache")) {
            for(; hole * 2 <= size(); hole = child) {
                child = hole * 2;
                if (child != size && array.get(child + 1).compareHscore(array.get(child)) > 0) {
                    child++;
                }
                if (array.get(child).compareHscore(temp) > 0) {
                    array.set(hole, array.get(child));
                } else {
                    break;
                }
            }
        } else if (typeOfHeap.equals("roadtrip")) {
            for(; hole * 2 <= size(); hole = child) {
                child = hole * 2;
                if (child != size && array.get(child + 1).compareRscore(array.get(child)) > 0) {
                    child++;
                }
                if (array.get(child).compareRscore(temp) > 0) {
                    array.set(hole, array.get(child));
                } else {
                    break;
                }

            }
        } else if (typeOfHeap.equals("blissful")) {
            for(; hole * 2 <= size(); hole = child) {
                child = hole * 2;
                if (child != size && array.get(child + 1).compareBscore(array.get(child)) > 0) {
                    child++;
                }
                if (array.get(child).compareBscore(temp) > 0) {
                    array.set(hole, array.get(child));
                } else {
                    break;
                }

            }
        }
        array.set(hole, temp);
    }
    public boolean remove(Song songToRemove) {
        int indexToRemove = array.indexOf(songToRemove);
        if (indexToRemove == -1) {
            return false;
        }

        array.set(indexToRemove, array.get(size));
        array.remove(size);
        size--;

        if (indexToRemove <= size) {
            percolateDown(indexToRemove);
            percolateUp(indexToRemove);
        }
        return true;
    }

    private void percolateUp(int hole) {
        Song temp = array.get(hole);

        if (typeOfHeap.equals("headache")) {
            while (hole > 1 && temp.compareHscore(array.get(hole / 2)) > 0) {
                array.set(hole, array.get(hole / 2));
                hole = hole / 2;
            }
        } else if (typeOfHeap.equals("roadtrip")) {
            while (hole > 1 && temp.compareRscore(array.get(hole / 2)) > 0) {
                array.set(hole, array.get(hole / 2));
                hole = hole / 2;
            }
        } else if (typeOfHeap.equals("blissful")) {
            while (hole > 1 && temp.compareBscore(array.get(hole / 2)) > 0) {
                array.set(hole, array.get(hole / 2));
                hole = hole / 2;
            }
        }

        array.set(hole, temp);
    }


}
