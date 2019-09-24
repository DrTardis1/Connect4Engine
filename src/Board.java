/*A board is expressed as a 1D array. It will be labeled as shown:
   ____________________________
    36  37  38  39  40  41  42
    29  30  31  32  33  34  35
    22  23  24  25  26  27  28
    15  16  17  18  19  20  21
    8   9   10  11  12  13  14
    1   2   3   4   5   6   7
   ____________________________
 */

public class Board {
    private int[] state;

    public Board(){
        state = new int[42];
    }
}
