
import common.InputUtil;
import java.util.List;
import java.util.stream.Collectors;
import lombok.SneakyThrows;

public class DepthCounter {

    private static final String filename = "input.txt";

    public static void main(String[] args)
    {
        var elems = InputUtil.readInput(filename).stream().map(Integer::valueOf).collect(Collectors.toList());
        System.out.println("How many measurements are larger than the previous measurement?");
        System.out.println("Answer: " + countLargerMeasurements(elems));
        System.out.println("Consider sums of a three-measurement sliding window. How many sums are larger than the previous sum?");
        System.out.println("Answer: " + countThreeMeasurementWindows(elems));
    }

    @SneakyThrows
    private static int countLargerMeasurements(List<Integer> elems){
        var count = 0;
        for (int i = 0; i < elems.size()-1; i++){
            if (elems.get(i+1) > elems.get(i)){
                count++;
            }
        }
        return count;
    }

    @SneakyThrows
    private static int countThreeMeasurementWindows(List<Integer> elems){
        var count = 0;
        var previousSum = 0;
        for (int i = 0; i < elems.size()-3; i++){
            var currentSum = elems.subList(i, i+3).stream().mapToInt(Integer::valueOf).sum();
            if (currentSum > previousSum){
                count++;
            }
            previousSum = currentSum;
        }
        return count;
    }
}
