import common.InputUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import lombok.SneakyThrows;

public class GiantSquid {

    private static final String filename = "input.txt";

    public static void main(String[] args){
        var inputReader = InputUtil.getFileReader(filename);
        var numbers = getNumbers(inputReader);
        var boards = getBoards(inputReader);
        System.out.println("What will your final score be if you choose that board?");
        System.out.println("Answer: " + part1(numbers, boards));
        System.out.println("Figure out which board will win last. Once it wins, what would its final score be?");
        System.out.println("Answer: " + part2(numbers, boards));
    }

    private static int part1(List<Integer> numbers, List<Board> boards){
        for (Integer num : numbers){
            for (Board board : boards){
                board.drawNumber(num);
                if (board.isWinner()){
                    var sumOfUnmarked = board.getSumOfUnmarked();
                    return num * sumOfUnmarked;
                }
            }
        }
        System.out.println("Error: none found");
        return -1;
    }

    private static int part2(List<Integer> numbers, List<Board> boards){
        Board lastWinner = null;
        int lastNumberWinner = 0;
        for (Integer num : numbers){
            for (Board board : boards){
                board.drawNumber(num);
                if (board.isWinner()){
                    lastWinner = board;
                    lastNumberWinner = num;
                }
            }
        }
        var sumOfUnmarked = lastWinner.getSumOfUnmarked();
        return lastNumberWinner * sumOfUnmarked;
    }

    @SneakyThrows
    private static List<Integer> getNumbers(Scanner input){
        return Arrays.stream(input.nextLine().split(",")).map(Integer::valueOf).collect(
            Collectors.toList());
    }

    @SneakyThrows
    private static List<Board> getBoards(Scanner input){
        List<Board> boards = new ArrayList<>();
        input.nextLine();
        while (input.hasNextLine()){
            int[][] board = new int[5][5];
            for (int i = 0; i < 5; i++){
                for (int j = 0; j < 5; j++){
                    board[i][j] = input.nextInt();
                }
            }
            input.nextLine();
            boards.add(new Board(board));
        }
        return boards;
    }


    private static class Board{
        boolean hasWon = false;
        int[][] board = new int[5][5];
        int[][] winners = new int[5][5];
        public Board(int[][] board){
            this.board = board;
        }
        public void drawNumber(int number){
            if (!hasWon){
                for (int i = 0; i < 5; i++){
                    for (int j = 0; j < 5; j++){
                        if (board[i][j] == number){
                            winners[i][j] = 1;
                        }
                    }
                }
            }
        }
        public int getSumOfUnmarked(){
            int sum = 0;
            for (int i = 0; i < 5; i++){
                for (int j = 0; j < 5; j++){
                    if (winners[i][j] == 0){
                        sum += board[i][j];
                    }
                }
            }
            return sum;
        }
        public boolean isWinner(){
            //only return true the first time isWinner() is called
            if ((isWinnerHorizontal() || isWinnerVertical()) && !hasWon){
                hasWon = true;
                return true;
            }
            return false;
        }
        private boolean isWinnerHorizontal(){
            for (int i = 0; i < 5; i++){
                if (isRowWinner(i)){
                    return true;
                }
            }
            return false;
        }
        private boolean isWinnerVertical(){
            for (int i = 0; i < 5; i++){
                if (isColumnWinner(i)){
                    return true;
                }
            }
            return false;
        }
        private boolean isRowWinner(int rowNumber){
            for (int i = 0; i < 5; i++){
                if (winners[rowNumber][i] == 0){
                    return false;
                }
            }
            return true;
        }
        private boolean isColumnWinner(int columnNumber){
            for (int i = 0; i < 5; i++){
                if (winners[i][columnNumber] == 0){
                    return false;
                }
            }
            return true;
        }
    }
}
