public class Song {
    int id;
    String name;
    int countNum;
    int hScore;
    int rScore;
    int bScore;
    int playlist;
    int blendNumber;
    boolean inHeadacheBlend;
    boolean inRoadtripBlend;
    boolean inBlissfulBlend;

    public Song(int id, String name, int countNum, int hScore, int rScore, int bScore) {
        this.id = id;
        this.name = name;
        this.countNum = countNum;
        this.hScore = hScore;
        this.rScore = rScore;
        this.bScore = bScore;
        this.blendNumber = 0;
        this.inHeadacheBlend = false;
        this.inRoadtripBlend = false;
        this.inBlissfulBlend = false;
    }

    public int compareCountNum(Song other) {
        int comp = Integer.compare(this.countNum, other.countNum);
        if (comp != 0) {
            return comp;
        }
        return other.name.compareTo(this.name);
    }
    public int compareHscore(Song other){
        int comp = Integer.compare(this.hScore, other.hScore);
        if (comp != 0) {
            return comp;
        }
        return other.name.compareTo(this.name);
    }
    public int compareBscore(Song other){
        int comp = Integer.compare(this.bScore, other.bScore);
        if (comp != 0) {
            return comp;
        }
        return other.name.compareTo(this.name);
    }
    public int compareRscore(Song other){
        int comp = Integer.compare(this.rScore, other.rScore);
        if (comp != 0) {
            return comp;
        }
        return other.name.compareTo(this.name);
    }
    public void reset(){
        blendNumber = 0;
        inHeadacheBlend = false;
        inRoadtripBlend = false;
        inBlissfulBlend = false;
        playlist = -1;
    }
}
