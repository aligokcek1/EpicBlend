// ALI GOKCEK
// 13.12.2023


import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Project3 {
    public static PrintWriter out;
    public static void main(String[] args)throws FileNotFoundException {
// TAKING INPUT FILES
        String songFile = args[0];
        File initialSongFile = new File(songFile);
        Scanner sc1 = new Scanner(initialSongFile);
        int allSongNumber = Integer.parseInt(sc1.nextLine());
        EpicBlend epicBlend = new EpicBlend(allSongNumber);

// READING SONG.TXT FILE AND ADD ALL THE SONGS TO A PLAYLIST
        while(sc1.hasNextLine()){
            String lineArray[] = sc1.nextLine().split(" ");
            int songId = Integer.parseInt(lineArray[0]);
            String name = lineArray[1];
            int countNum = Integer.parseInt(lineArray[2]);
            int hScore = Integer.parseInt(lineArray[3]);
            int rScore = Integer.parseInt(lineArray[4]);
            int bScore = Integer.parseInt(lineArray[5]);
            epicBlend.addSong(songId, name, countNum, hScore, rScore, bScore);
        }
        sc1.close();

// TEST PHASE AND ADDING SONGS TO THEIR PLAYLISTS
        String testFile = args[1];
        File testCaseFile = new File(testFile);
        Scanner sc2 = new Scanner(testCaseFile);
        String[] firstLine = sc2.nextLine().split(" ");
        int playListCatLimit = Integer.parseInt(firstLine[0]);
        int hLimit = Integer.parseInt(firstLine[1]);
        int rLimit = Integer.parseInt(firstLine[2]);
        int bLimit = Integer.parseInt(firstLine[3]);
        int playListNum = Integer.parseInt(sc2.nextLine());
        epicBlend.setEpicBlend(playListCatLimit, hLimit, rLimit, bLimit, playListNum);
        for(int i = 0; i < playListNum; i++){
            String[] dumstr = sc2.nextLine().split(" ");
            int playlistId = Integer.parseInt(dumstr[0]);
            int playlistSongCount = Integer.parseInt(dumstr[1]);
            epicBlend.addPlaylist(playlistId, playlistSongCount);
            if (playlistSongCount == 0){
                sc2.nextLine();
            }
            else{
                for(int j = 0; j < playlistSongCount; j++){
                    int songId = sc2.nextInt();
                    epicBlend.addSongToPlaylist(playlistId, songId);
                }
            sc2.nextLine();
            }
        }
        sc2.nextLine();
        String output_file = args[2];
        out = new PrintWriter(output_file);
        epicBlend.createInitialBlend();

    //TAKE COMMANDS
        while (sc2.hasNext()){
            String[] lineArray = sc2.nextLine().split(" ");
            switch (lineArray[0]){
                case "ASK":  // PRINTING THE IDS OF SELECTED SONGS
                    epicBlend.ASK();
                    break;
                case "ADD":     // ADD A SONG TO A PLAYLIST
                    int songId = Integer.parseInt(lineArray[1]);
                    int playlistId = Integer.parseInt(lineArray[2]);
                    epicBlend.ADD(songId, playlistId);
                    break;
                case "REM":     // REMOVE A SONG FROM A PLAYLIST
                    int songId1 = Integer.parseInt(lineArray[1]);
                    int playlistId1 = Integer.parseInt(lineArray[2]);
                    epicBlend.remove(songId1, playlistId1);
                    break;
            }
        }
        sc2.close();
        out.close();
    }
}