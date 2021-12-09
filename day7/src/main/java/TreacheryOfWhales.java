import common.InputUtil;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TreacheryOfWhales {

    private static final String filename = "input.txt";

    public static void main(String[] args){
        var input = Arrays.stream(InputUtil.getFile(filename).split(",")).map(Integer::valueOf)
            .sorted(Integer::compareTo).collect(Collectors.toList());
        System.out.println("How much fuel must they spend to align to that position?");
        System.out.println("Answer: " + part1(input));
        System.out.println("How much fuel must they spend to align to that position?");
        System.out.println("Answer: " + part2(input));
    }

    private static long part1(List<Integer> input){
        var median = calculateMedian(input);
        return findTotalDistanceFromNumberLinear(input, median);
    }

    private static int calculateMedian(List<Integer> nums){
        if (nums.size() % 2 == 1){
            return (nums.get((nums.size() + 1) / 2));
        }
        var mid = nums.size() / 2;
        return (nums.get(mid) + nums.get(mid+1)) / 2;
    }

    private static long findTotalDistanceFromNumberLinear(List<Integer> nums, int median){
        Long runningTotal = 0L;
        for (Integer num : nums) {
            runningTotal += Math.abs(median - num);
        }
        return runningTotal;
    }

    private static long part2(List<Integer> input){
        var mean = calculateMean(input);
        return findTotalDistanceFromMeanWithGrowth(input, mean);
    }

    private static int calculateMean(List<Integer> nums){
        var sum = BigDecimal.valueOf(nums.stream().mapToInt(Integer::valueOf).sum());
        return sum.divide(BigDecimal.valueOf(nums.size()), RoundingMode.FLOOR).intValue();
    }

    private static long findTotalDistanceFromMeanWithGrowth(List<Integer> nums, int mean){
        Long runningTotal = 0L;
        for (Integer num : nums) {
            Long difference = (long) Math.abs(mean - num);
            runningTotal += (difference * (difference+1)/2); //summation formula
        }
        return runningTotal;
    }
}
