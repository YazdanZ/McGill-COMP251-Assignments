import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class a1_real {

    public static int silence ( int[] positions ) {
        HashMap<Integer, Integer> map = new HashMap<> ();
        int minDist = positions.length;
        for (int i = 0; i<positions.length; i++){
            Integer index = map.get ( positions[i] );
            if (index != null){
                minDist = Math.min ( minDist, i - index);
                if (minDist == 1) break;
            }
            map.put ( positions[i], i );
        }

        return minDist;
    }

    public static void main ( String[] args ) {
        try {
            String path = args[0];
            File myFile = new File ( path );
            Scanner sc = new Scanner ( myFile );
            int num_students = sc.nextInt ();
            int[] positions = new int[num_students];
            for (int student = 0; student < num_students; student++) {
                positions[student] = sc.nextInt ();
            }
            sc.close ();
            int answer = silence ( positions );
            System.out.println ( answer );
        } catch (FileNotFoundException e) {
            System.out.println ( "An error occurred." );
            e.printStackTrace ();
        }
    }
}