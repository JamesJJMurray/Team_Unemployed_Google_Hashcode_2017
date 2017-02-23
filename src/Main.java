import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;

public class Main
{
    public static void main(String[] args) throws Exception
    {
        BufferedReader reader = new BufferedReader(new FileReader("small.in"));
        String[] params = reader.readLine().split(" ");

        int r = Integer.valueOf(params[0]);
        int c = Integer.valueOf(params[1]);
        int l = Integer.valueOf(params[2]);
        int h = Integer.valueOf(params[3]);

        char[][] board = new char[r][c];

        for (int k = 0; k < r; k++) {
            String line = reader.readLine();
            for (int j = 0; j < c; j++)
                board[k][j] = line.charAt(j);
        }

//        for (int k = 0; k < r; k++)
//            System.out.println(Arrays.toString(board[k]));
    }
}
