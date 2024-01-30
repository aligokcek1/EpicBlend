
import java.util.ArrayList;
public class EpicBlend { // THIS IS THE CLASS WHERE EVERYTHING IS PROCESSED
    int MAX_HEADACHE;// CONSTRAINTS
    int MAX_ROADTRIP;
    int MAX_BLISSFUL;
    int MAX_CONTAIN;
    MyAVL epicSongs;
    Playlist[] playlists;
    Song[] allSongs;

    // BINARY HEAPS
    MyMaxHeap sortedHeadache; // MAXES FOR THE SONGS OUTSIDE OF THE BLEND
    MyMaxHeap sortedRoadtrip;
    MyMaxHeap sortedBlissful;
    MyMinHeap headacheBlend; // MINS FOR THE EPIC BLEND CATEGORIES
    MyMinHeap roadtripBlend;
    MyMinHeap blissfulBlend;

    EpicBlend(int numOfAllSongs) {
        this.allSongs = new Song[numOfAllSongs];
    }

    public void addSong(int id, String name, int countNum, int hScore, int rScore, int bScore) {
        Song newSong = new Song(id, name, countNum, hScore, rScore, bScore);
        allSongs[id - 1] = newSong;
    }

    public void setEpicBlend(int playListCatLimit, int hLimit, int rLimit, int bLimit, int playlistNum) {
        this.MAX_HEADACHE = hLimit;
        this.MAX_ROADTRIP = rLimit;
        this.MAX_BLISSFUL = bLimit;
        this.MAX_CONTAIN = playListCatLimit;
        this.epicSongs = new MyAVL();
        this.headacheBlend = new MyMinHeap("headache");
        this.roadtripBlend = new MyMinHeap("roadtrip");
        this.blissfulBlend = new MyMinHeap("blissful");
        this.playlists = new Playlist[playlistNum];
    }

    public void addPlaylist(int playlistId, int songCount) {
        playlists[playlistId - 1] = new Playlist(playlistId, songCount);
    }

    public void addSongToPlaylist(int playlistId, int songId) {
        Song song = allSongs[songId - 1];
        song.playlist = playlistId;
        playlists[playlistId - 1].addSong(song);
    }

    public void ASK() {
        epicSongs.printTree();
    }

    // CREATING INITIAL BLEND
    public void createInitialBlend() {
        this.sortedBlissful = new MyMaxHeap(playlists, "blissful");
        this.sortedHeadache = new MyMaxHeap(playlists, "headache");
        this.sortedRoadtrip = new MyMaxHeap(playlists, "roadtrip");

        if (sortedHeadache.size() > 0) {
            initialHeadache();
        }
        if (sortedRoadtrip.size() > 0) {
            initialRoadtrip();
        }

        if (sortedBlissful.size() > 0) {
            initialBlissful();
        }
    }

    private void initialHeadache() {
        ArrayList<Song> passedSongs = new ArrayList<>();
        while (headacheBlend.size() < MAX_HEADACHE) {
            Song maxSong = sortedHeadache.removeMax();
            Playlist playlistOfSong = playlists[maxSong.playlist - 1];
            if (playlistOfSong.hNum < MAX_CONTAIN) {
                maxSong.inHeadacheBlend = true;
                playlistOfSong.hNum += 1;
                epicSongs.insert(maxSong);
                headacheBlend.insert(maxSong);
            } else {
                Song nextMax = maxSong;
                Playlist playlistOfNextSong = playlists[nextMax.playlist - 1];
                while (playlistOfNextSong.hNum >= MAX_CONTAIN) {
                    passedSongs.add(nextMax);
                    if (sortedHeadache.size() <= 0) {
                        sortedHeadache = new MyMaxHeap(passedSongs, "headache");
                        return;
                    }
                    nextMax = sortedHeadache.removeMax();
                    playlistOfNextSong = playlists[nextMax.playlist - 1];
                }
                nextMax.inHeadacheBlend = true;
                playlistOfNextSong.hNum += 1;
                epicSongs.insert(nextMax);
                headacheBlend.insert(nextMax);
            }
        }
        for (int i = 0; i < passedSongs.size(); i++) {
            sortedHeadache.insert(passedSongs.get(i));
        }
    }

    private void initialRoadtrip() {
        ArrayList<Song> passedSongs = new ArrayList<>();
        while (roadtripBlend.size() < MAX_ROADTRIP) {
            Song maxSong = sortedRoadtrip.removeMax();
            Playlist playlistOfSong = playlists[maxSong.playlist - 1];
            if (playlistOfSong.rNum < MAX_CONTAIN) {
                maxSong.inRoadtripBlend = true;
                playlistOfSong.rNum += 1;
                epicSongs.insert(maxSong);
                roadtripBlend.insert(maxSong);
            } else {
                Song nextMax = maxSong;
                Playlist playlistOfNextSong = playlists[nextMax.playlist - 1];
                while (playlistOfNextSong.rNum >= MAX_CONTAIN) {
                    passedSongs.add(nextMax);
                    if (sortedRoadtrip.size() <= 0) {
                        sortedRoadtrip = new MyMaxHeap(passedSongs, "roadtrip");
                        return;
                    }
                    nextMax = sortedRoadtrip.removeMax();
                    playlistOfNextSong = playlists[nextMax.playlist - 1];
                }
                nextMax.inRoadtripBlend = true;
                playlistOfNextSong.rNum += 1;
                epicSongs.insert(nextMax);
                roadtripBlend.insert(nextMax);
            }
        }
        for (int i = 0; i < passedSongs.size(); i++) {
            sortedRoadtrip.insert(passedSongs.get(i));
        }
    }

    private void initialBlissful() {
        ArrayList<Song> passedSongs = new ArrayList<>();
        while (blissfulBlend.size() < MAX_BLISSFUL) {
            if (sortedBlissful.size() == 0){
                for (int i = 0; i < passedSongs.size(); i++) {
                    sortedBlissful.insert(passedSongs.get(i));
                }
                return;
            }
            Song maxSong = sortedBlissful.maxItem();
            Playlist playlistOfSong = playlists[maxSong.playlist - 1];

            if (playlistOfSong.bNum < MAX_CONTAIN) {
                maxSong = sortedBlissful.removeMax();
                maxSong.inBlissfulBlend = true;
                playlistOfSong.bNum += 1;
                epicSongs.insert(maxSong);
                blissfulBlend.insert(maxSong);
            } else {
                Song nextMax = sortedBlissful.maxItem();
                Playlist playlistOfNextSong = playlists[nextMax.playlist - 1];
                while (playlistOfNextSong.bNum >= MAX_CONTAIN) {
                    passedSongs.add(sortedBlissful.removeMax());
                    if (sortedBlissful.size() <= 0) {
                        sortedBlissful = new MyMaxHeap(passedSongs, "blissful");
                        return;
                    }
                    nextMax = sortedBlissful.maxItem();
                    playlistOfNextSong = playlists[nextMax.playlist - 1];
                }
                nextMax.inBlissfulBlend = true;
                playlistOfNextSong.bNum += 1;
                epicSongs.insert(nextMax);
                blissfulBlend.insert(nextMax);
            }
        }

        for (int i = 0; i < passedSongs.size(); i++) {
            sortedBlissful.insert(passedSongs.get(i));
        }


    }

    //ADD METHODS:

    public void ADD(int songId, int playlistId) {
        Song songToAdd = allSongs[songId - 1];
        addSongToPlaylist(playlistId, songId);
        int[] printHchange = addHCheck(songToAdd, playlistId);
        int[] printRchange = addRCheck(songToAdd, playlistId);
        int[] printBchange = addBCheck(songToAdd, playlistId);

        Project3.out.println(printHchange[0] + " " + printRchange[0] + " "+ printBchange[0]);
        Project3.out.println(printHchange[1] + " " + printRchange[1] + " "+ printBchange[1]);

    }

    private int[] addHCheck(Song songToAdd, int playlistId) {
        Playlist playlist = playlists[playlistId - 1];
        int[] a;
        if (playlist.hNum >= MAX_CONTAIN) {
            ArrayList<Song> passedSongs = new ArrayList<>();
            while (headacheBlend.minItem().playlist != playlistId) {
                passedSongs.add(headacheBlend.removeMin());
            }
            Song minHeadache = headacheBlend.minItem();
            if (songToAdd.compareHscore(minHeadache) > 0) {
                minHeadache = headacheBlend.removeMin();
                sortedHeadache.insert(minHeadache);
                headacheBlend.insert(songToAdd);
                if (minHeadache.blendNumber == 0) {
                    epicSongs.remove(minHeadache);
                }
                epicSongs.insert(songToAdd);
                for (int i = 0; i < passedSongs.size(); i++) {
                    headacheBlend.insert(passedSongs.get(i));
                }
                return a = new int[]{songToAdd.id, minHeadache.id};
            }
            for (int i = 0; i < passedSongs.size(); i++) {
                headacheBlend.insert(passedSongs.get(i));
            }
            sortedHeadache.insert(songToAdd);
            return a = new int[]{0, 0};
        }
        if (headacheBlend.size() < MAX_HEADACHE) {
            playlist.hNum += 1;
            headacheBlend.insert(songToAdd);
            epicSongs.insert(songToAdd);
            return a = new int[]{songToAdd.id, 0};
        }
        Song minHeadache = headacheBlend.minItem();
        Playlist minPlaylist = playlists[minHeadache.playlist - 1];

        if (songToAdd.compareHscore(minHeadache) > 0) {
            playlist.hNum += 1;
            minPlaylist.hNum -= 1;
            headacheBlend.removeMin();
            sortedHeadache.insert(minHeadache);
            headacheBlend.insert(songToAdd);

            if (minHeadache.blendNumber == 0) {
                epicSongs.remove(minHeadache);
            }
            epicSongs.insert(songToAdd);
            return a = new int[]{songToAdd.id, minHeadache.id};
        }

        sortedHeadache.insert(songToAdd);
        return a = new int[]{0, 0};
    }

    private int[] addRCheck(Song songToAdd, int playlistId) {
        Playlist playlist = playlists[playlistId - 1];
        int[] a;
        if (playlist.rNum >= MAX_CONTAIN) {
            ArrayList<Song> passedSongs = new ArrayList<>();
            while (roadtripBlend.minItem().playlist != playlistId) {
                passedSongs.add(roadtripBlend.removeMin());
            }
            Song minRoadtrip = roadtripBlend.minItem();
            if (songToAdd.compareRscore(minRoadtrip) > 0) {
                minRoadtrip = roadtripBlend.removeMin();
                sortedRoadtrip.insert(minRoadtrip);
                roadtripBlend.insert(songToAdd);
                if (minRoadtrip.blendNumber == 0) {
                    epicSongs.remove(minRoadtrip);
                }
                epicSongs.insert(songToAdd);
                for (int i = 0; i < passedSongs.size(); i++) {
                    roadtripBlend.insert(passedSongs.get(i));
                }
                return a = new int[]{songToAdd.id, minRoadtrip.id};
            }
            for (int i = 0; i < passedSongs.size(); i++) {
                roadtripBlend.insert(passedSongs.get(i));
            }
            sortedRoadtrip.insert(songToAdd);
            return a = new int[]{0, 0};
        }
        if (roadtripBlend.size() < MAX_ROADTRIP) {
            playlist.rNum += 1;
            roadtripBlend.insert(songToAdd);
            epicSongs.insert(songToAdd);
            return a = new int[]{songToAdd.id, 0};
        }
        Song minRoadtrip = roadtripBlend.minItem();
        Playlist minPlaylist = playlists[minRoadtrip.playlist - 1];
        if (songToAdd.compareRscore(minRoadtrip) > 0) {
            playlist.rNum += 1;
            minPlaylist.rNum -= 1;
            sortedRoadtrip.insert(roadtripBlend.removeMin());
            roadtripBlend.insert(songToAdd);

            if (minRoadtrip.blendNumber == 0) {
                epicSongs.remove(minRoadtrip);
            }
            epicSongs.insert(songToAdd);
            return a = new int[]{songToAdd.id, minRoadtrip.id};
        }
        sortedRoadtrip.insert(songToAdd);
        return a = new int[]{0, 0};
    }

    private int[] addBCheck(Song songToAdd, int playlistId) {
        Playlist playlist = playlists[playlistId - 1];
        int[] a;
        if (playlist.bNum >= MAX_CONTAIN) {
            ArrayList<Song> passedSongs = new ArrayList<>();
            while (blissfulBlend.minItem().playlist != playlistId) {
                passedSongs.add(blissfulBlend.removeMin());
            }
            Song minBlissful = blissfulBlend.minItem();
            if (songToAdd.compareBscore(minBlissful) > 0) {
                minBlissful = blissfulBlend.removeMin();
                sortedBlissful.insert(minBlissful);
                blissfulBlend.insert(songToAdd);
                if (minBlissful.blendNumber == 0) {
                    epicSongs.remove(minBlissful);
                }
                epicSongs.insert(songToAdd);
                for (int i = 0; i < passedSongs.size(); i++) {
                    blissfulBlend.insert(passedSongs.get(i));
                }
                return a = new int[]{songToAdd.id, minBlissful.id};
            }
            for (int i = 0; i < passedSongs.size(); i++) {
                blissfulBlend.insert(passedSongs.get(i));
            }
            sortedBlissful.insert(songToAdd);
            return a = new int[]{0, 0};
        }
        if (blissfulBlend.size() < MAX_BLISSFUL) {
            playlist.bNum += 1;
            blissfulBlend.insert(songToAdd);
            epicSongs.insert(songToAdd);
            return a = new int[]{songToAdd.id, 0};
        }
        Song minBlissful = blissfulBlend.minItem();
        Playlist minPlaylist = playlists[minBlissful.playlist - 1];
        if (songToAdd.compareBscore(minBlissful) > 0) {
            playlist.bNum += 1;
            minPlaylist.bNum -= 1;
            sortedBlissful.insert(blissfulBlend.removeMin());
            blissfulBlend.insert(songToAdd);

            if (minBlissful.blendNumber == 0) {
                epicSongs.remove(minBlissful);
            }
            epicSongs.insert(songToAdd);
            return a = new int[]{songToAdd.id, minBlissful.id};
        }
        sortedBlissful.insert(songToAdd);
        return a = new int[]{0, 0};
    }

    // REMOVE METHODS:
    public void remove(int songID, int playlistID) {
        Song songToRemove = allSongs[songID - 1];
        Playlist playlistOfRemove = playlists[playlistID - 1];
        playlistOfRemove.songs.remove(songToRemove);
        int[][] output = removeCheck(songToRemove, playlistOfRemove);
        Project3.out.println(output[0][0] + " " + output[1][0] + " " + output[2][0]);
        Project3.out.println(output[0][1] + " " + output[1][1] + " " + output[2][1]);
    }

    private int[][] removeCheck(Song songToRemove, Playlist playlistOfRemove) {
        int[][] output = new int[3][2];
        boolean inHBlend = songToRemove.inHeadacheBlend;
        boolean inRblend = songToRemove.inRoadtripBlend;
        boolean inBblend = songToRemove.inBlissfulBlend;
        epicSongs.remove(songToRemove);
        songToRemove.reset();
        if (inHBlend) {
            playlistOfRemove.hNum -= 1;
            headacheBlend.remove(songToRemove);
            output[0] = new int[]{findMaxHeadache(), songToRemove.id};
        } else {
            sortedHeadache.remove(songToRemove);
            output[0] = new int[]{0, 0};
        }

        if (inRblend) {
            playlistOfRemove.rNum -= 1;
            roadtripBlend.remove(songToRemove);
            output[1] = new int[]{findMaxRoadtrip(), songToRemove.id};
        } else {
            sortedRoadtrip.remove(songToRemove);
            output[1] = new int[]{0, 0};
        }

        if (inBblend) {
            playlistOfRemove.bNum -= 1;
            blissfulBlend.remove(songToRemove);
            output[2] = new int[]{findMaxBlissful(), songToRemove.id};
        } else {
            sortedBlissful.remove(songToRemove);
            output[2] = new int[]{0, 0};
        }

        return output;
    }

    // METHODS THAT I USE TO FIND REMOVAL ELEMENTS
    private int findMaxHeadache() {
        ArrayList<Song> passedSongs = new ArrayList<>();
        if (sortedHeadache.size() <= 0) {
            return 0;
        }
        Song maxSong = sortedHeadache.removeMax();
        Playlist playlistOfMax = playlists[maxSong.playlist - 1];
        while (playlistOfMax.hNum >= MAX_CONTAIN) {
            passedSongs.add(maxSong);
            if (sortedHeadache.size() <= 0) {
                sortedHeadache = new MyMaxHeap(passedSongs, "headache");
                return 0;
            }
            maxSong = sortedHeadache.removeMax();
            playlistOfMax = playlists[maxSong.playlist - 1];
        }
        maxSong.inHeadacheBlend = true;
        playlistOfMax.hNum += 1;
        if (maxSong.blendNumber == 0) {
            epicSongs.insert(maxSong);
        }
        headacheBlend.insert(maxSong);
        if (sortedHeadache.size() <= 0) {
            sortedHeadache = new MyMaxHeap(passedSongs, "headache");
        } else {
            for (int i = 0; i < passedSongs.size(); i++) {
                sortedHeadache.insert(passedSongs.get(i));
            }
        }
        return maxSong.id;
    }

    private int findMaxRoadtrip() {
        ArrayList<Song> passedSongs = new ArrayList<>();
        if (sortedRoadtrip.size() <= 0) {
            return 0;
        }
        Song maxSong = sortedRoadtrip.removeMax();
        Playlist playlistOfMax = playlists[maxSong.playlist - 1];
        while (playlistOfMax.rNum >= MAX_CONTAIN) {
            passedSongs.add(maxSong);
            if (sortedRoadtrip.size() <= 0) {
                sortedRoadtrip = new MyMaxHeap(passedSongs, "roadtrip");
                return 0;
            }
            maxSong = sortedRoadtrip.removeMax();
            playlistOfMax = playlists[maxSong.playlist - 1];
        }
        maxSong.inRoadtripBlend = true;
        playlistOfMax.rNum += 1;
        if (maxSong.blendNumber == 0) {
            epicSongs.insert(maxSong);
        }
        roadtripBlend.insert(maxSong);
        if (sortedRoadtrip.size() <= 0) {
            sortedRoadtrip = new MyMaxHeap(passedSongs, "roadtrip");
        } else {
            for (int i = 0; i < passedSongs.size(); i++) {
                sortedRoadtrip.insert(passedSongs.get(i));
            }
        }
        return maxSong.id;
    }

    private int findMaxBlissful() {
        ArrayList<Song> passedSongs = new ArrayList<>();
        if (sortedBlissful.size() <= 0) {
            return 0;
        }
        Song maxSong = sortedBlissful.removeMax();
        Playlist playlistOfMax = playlists[maxSong.playlist - 1];
        while (playlistOfMax.bNum >= MAX_CONTAIN) {
            passedSongs.add(maxSong);
            if (sortedBlissful.size() <= 0) {
                sortedBlissful = new MyMaxHeap(passedSongs, "blissful");
                return 0;
            }
            maxSong = sortedBlissful.removeMax();
            playlistOfMax = playlists[maxSong.playlist - 1];
        }
        maxSong.inBlissfulBlend = true;
        playlistOfMax.bNum += 1;
        if (maxSong.blendNumber == 0) {
            epicSongs.insert(maxSong);
        }
        blissfulBlend.insert(maxSong);
        if (sortedBlissful.size() <= 0) {
            sortedBlissful = new MyMaxHeap(passedSongs, "blissful");
        } else {
            for (int i = 0; i < passedSongs.size(); i++) {
                sortedBlissful.insert(passedSongs.get(i));
            }
        }
        return maxSong.id;
    }
}


