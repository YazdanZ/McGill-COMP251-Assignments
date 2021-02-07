import java.io.*;
import java.util.*;

public class Open_Addressing {
    public int m; // number of SLOTS AVAILABLE
    public int A; // the default random number
    public int[] Table;
    int w;
    int r;

    protected Open_Addressing ( int w, int seed, int A ) {

        this.w = w;
        this.r = (int) (w - 1) / 2 + 1;
        this.m = power2 ( r );
        if (A == -1) {
            this.A = generateRandom ( (int) power2 ( w - 1 ), (int) power2 ( w ), seed );
        } else {
            this.A = A;
        }
        this.Table = new int[m];
        for (int i = 0; i < m; i++) {
            Table[i] = -1;
        }

    }

    /**
     * Calculate 2^w
     */
    public static int power2 ( int w ) {
        return (int) Math.pow ( 2, w );
    }

    public static int generateRandom ( int min, int max, int seed ) {
        Random generator = new Random ();
        if (seed >= 0) {
            generator.setSeed ( seed );
        }
        int i = generator.nextInt ( max - min - 1 );
        return i + min + 1;
    }

    /**
     * Implements the hash function g(k)
     */
    public int probe ( int key, int i ) {
        int h = ((this.A * key) % (power2 ( this.w ))) >> (this.w - this.r);
        return (h + i) % power2 ( this.r );
    }


    /**
     * Inserts key k into hash table. Returns the number of collisions encountered
     */
    public int insertKey ( int key ) {
        int i = 0;
        int zeroHash = probe ( key, 0 );
        boolean inserted = false;
        while (!inserted){
            int index = probe ( key, i );
            // Not possible to insert key
            if(index == zeroHash && i != 0)
                break;
            // Verify if slot is empty (represented by negative ints)
            if(this.Table[index] < 0){
                inserted = true;
                this.Table[index] = key;
            }else {
                i++;
            }
        }
        return i;
    }

    /**
     * Sequentially inserts a list of keys into the HashTable. Outputs total number of collisions
     */
    public int insertKeyArray ( int[] keyArray ) {
        int collision = 0;
        for (int key : keyArray) {
            collision += insertKey ( key );
        }
        return collision;
    }

    /**
     * Inserts key k into hash table. Returns the number of collisions encountered
     */
    public int removeKey ( int key ) {
        int i = 0;
        boolean deleted = false;
        int zeroHash = probe ( key, i );
        while(!deleted){
            int index = probe ( key, i );
            // visited all slots
            if (index == zeroHash && i != 0)
                break;
            // key doesn't exist
            if (this.Table[index] == -1)
                break;
            if (this.Table[index] == key){
                deleted = true;
                // -2 is a tombstone for deleted keys
                this.Table[index] = -2;
            }
            else{
                i ++;
            }
        }
        return i;
    }
}
