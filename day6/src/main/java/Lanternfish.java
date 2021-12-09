import common.InputUtil;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Lanternfish {

    private static final String filename = "input.txt";

    public static void main(String[] args){
        var input = Arrays.stream(InputUtil.getFile(filename).split(",")).map(Integer::valueOf).collect(Collectors.toList());
        System.out.println("Find a way to simulate lanternfish. How many lanternfish would there be after 80 days?");
        System.out.println("Answer: " + simulateAfterDays(input, 80));
        System.out.println("How many lanternfish would there be after 256 days?");
        System.out.println("Answer: " + simulateAfterDays(input, 256));
    }

    private static long simulateAfterDays(List<Integer> input, int numDays){
        HashMap<Integer, Long> ageToCountsMap = new HashMap<>();
        for (int i = 0; i <= 8; i++){
            ageToCountsMap.put(i, 0L);
        }
        input.forEach(num -> ageToCountsMap.put(num, ageToCountsMap.get(num)+1));
        for (int i = 0; i < numDays; i++){
            HashMap<Integer, Long> processedMap = new HashMap<>();
            for (int j = 0; j <= 8; j++){
                processedMap.put(j, 0L);
            }
            ageToCountsMap.forEach((key, value) -> {
                if (key == 0) {
                    processedMap.put(6, processedMap.get(6) + value);
                    processedMap.put(8, value);
                } else {
                    processedMap.put(key - 1, processedMap.get(key - 1) + value);
                }
            });
            processedMap.forEach(ageToCountsMap::put);
        }
        return ageToCountsMap.values().stream().mapToLong(Long::valueOf).sum();
    }
}
