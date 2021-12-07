
import common.InputUtil;
import java.util.stream.Collectors;
import lombok.SneakyThrows;

public class DepthCounter {

    private static final String filename = "input.txt";

    public static void main(String[] args)
    {
        System.out.println("How many measurements are larger than the previous measurement?");
        System.out.println("Answer: " + countLargerMeasurements(filename));
    }

    @SneakyThrows
    private static int countLargerMeasurements(String filename){
        var elems = InputUtil.readInput(filename).stream().map(Integer::valueOf).collect(Collectors.toList());
        var count = 0;
        for (int i = 0; i < elems.size()-1; i++){
            if (elems.get(i+1) > elems.get(i)){
                count++;
            }
        }
        return count;
    }
}
