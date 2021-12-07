import common.InputUtil;
import java.util.List;

public class PositionPredicter {

    private static final String filename = "input.txt";

    public static void main(String[] args){
        var input = InputUtil.readInput(filename);
        System.out.println("What do you get if you multiply your final horizontal position by your final depth?");
        System.out.println("Answer: " + finalPosition(input));
        System.out.println("What do you get if you multiply your final horizontal position by your final depth?");
        System.out.println("Answer: " + finalPositionAim(input));
    }

    private static int finalPosition(List<String> input){
        int linearPosition = 0;
        int depth = 0;
        for (String s : input){
            var tokens = s.split(" ");
            switch (tokens[0]){
                case "forward":
                    linearPosition += Integer.parseInt(tokens[1]);
                    break;
                case "down":
                    depth += Integer.parseInt(tokens[1]);
                    break;
                case "up":
                    depth -= Integer.parseInt(tokens[1]);
                    break;
            }
        }
        return linearPosition * depth;
    }


    private static int finalPositionAim(List<String> input){
        int linearPosition = 0;
        int depth = 0;
        int aim = 0;
        for (String s : input){
            var tokens = s.split(" ");
            switch (tokens[0]){
                case "forward":
                    linearPosition += Integer.parseInt(tokens[1]);
                    depth += aim * Integer.parseInt(tokens[1]);
                    break;
                case "down":
                    aim += Integer.parseInt(tokens[1]);
                    break;
                case "up":
                    aim -= Integer.parseInt(tokens[1]);
                    break;
            }
        }
        return linearPosition * depth;
    }

}
