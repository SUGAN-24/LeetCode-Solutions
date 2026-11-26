class Solution {
    public boolean canAliceWin(int n) {
        int stones = 10;
        int moves = 0;
        while (n >= stones) {
            n -= stones;
            stones--;
            moves++;
        }
        return moves % 2 == 1;
    }
}