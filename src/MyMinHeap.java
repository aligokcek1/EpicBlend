import java.util.ArrayList;

public class MyMinHeap {   // HEAP FOR EACH EPIC BLEND CATEGORIES
    private ArrayList<Song> array;
    private int size;
    private String typeOfHeap;
    MyMinHeap(String typeOfHeap){
        this.typeOfHeap = typeOfHeap;
        size = 0;
        array = new ArrayList<Song>();
        array.add(null);
    }

    public void insert(Song song) {
        size++;
        array.add(song);
        song.blendNumber += 1;
        int hole = size;
        if (typeOfHeap.equals("headache")) {
            song.inHeadacheBlend = true;
            while (hole > 1 && song.compareHscore(array.get(hole / 2)) < 0) {
                Song parent = array.get(hole / 2);
                array.set(hole / 2, song);
                array.set(hole, parent);
                hole = hole / 2;
            }
        }
        else if (typeOfHeap.equals("roadtrip")) {
            song.inRoadtripBlend = true;
            while (hole > 1 && song.compareRscore(array.get(hole / 2)) < 0) {
                Song parent = array.get(hole / 2);
                array.set(hole / 2, song);
                array.set(hole, parent);
                hole = hole / 2;
            }
        }
        else if (typeOfHeap.equals("blissful")) {
            song.inBlissfulBlend = true;
            while (hole > 1 && song.compareBscore(array.get(hole / 2)) < 0) {
                Song parent = array.get(hole / 2);
                array.set(hole / 2, song);
                array.set(hole, parent);
                hole = hole / 2;
            }
        }
    }

    public Song minItem() {
        return array.get(1);
    }

    public Song removeMin() {
        Song minItem = minItem();
        array.set(1, array.get(size));
        array.remove(size);
        size--;

        minItem.blendNumber -= 1;
        switch (typeOfHeap){
            case "headache":{
                minItem.inHeadacheBlend = false;
            }
            case "roadtrip":{
                minItem.inRoadtripBlend = false;
            }
            case "blissful":{
                minItem.inBlissfulBlend = false;
            }
        }
        if (size() == 0){
            return minItem;
        }
        percolateDown(1);
        return minItem;
    }

    public int size() {
        return size;
    }

    private void percolateDown(int hole) {
        int child;
        Song temp = array.get(hole);
        if (typeOfHeap.equals("headache")){
            while(hole * 2 <= size) {
                child = hole * 2;
                if(child != size && array.get(child + 1).compareHscore(array.get(child)) < 0) {
                    child++;
                }
                if(array.get(child).compareHscore(temp) < 0) {
                    array.set(hole, array.get(child));
                }else {
                    break;
                }

                hole = child;
            }
        }
        else if (typeOfHeap.equals("roadtrip")){
            while(hole * 2 <= size) {
                child = hole * 2;
                if(child != size && array.get(child + 1).compareRscore(array.get(child)) < 0) {
                    child++;
                }
                if(array.get(child).compareRscore(temp) < 0) {
                    array.set(hole, array.get(child));
                }else {
                    break;
                }

                hole = child;
            }
        }
        else if (typeOfHeap.equals("blissful")){
            while(hole * 2 <= size) {
                child = hole * 2;
                if(child != size && array.get(child + 1).compareBscore(array.get(child)) < 0) {
                    child++;
                }
                if(array.get(child).compareBscore(temp) < 0) {
                    array.set(hole, array.get(child));
                }else {
                    break;
                }

                hole = child;
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
            while (hole > 1 && temp.compareHscore(array.get(hole / 2)) < 0) {
                array.set(hole, array.get(hole / 2));
                hole = hole / 2;
            }
        } else if (typeOfHeap.equals("roadtrip")) {
            while (hole > 1 && temp.compareRscore(array.get(hole / 2)) < 0) {
                array.set(hole, array.get(hole / 2));
                hole = hole / 2;
            }
        } else if (typeOfHeap.equals("blissful")) {
            while (hole > 1 && temp.compareBscore(array.get(hole / 2)) < 0) {
                array.set(hole, array.get(hole / 2));
                hole = hole / 2;
            }
        }

        array.set(hole, temp);
    }

}
