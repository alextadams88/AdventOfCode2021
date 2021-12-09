import common.InputUtil;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class SevenSegmentSearch {

    private static final String filename = "input.txt";

    public static void main(String[] args){
        var input = InputUtil.readInput(filename);
        System.out.println("In the output values, how many times do digits 1, 4, 7, or 8 appear?");
        System.out.println("Answer: " + part1(input));
        System.out.println("What do you get if you add up all of the output values?");
        System.out.println("Answer: " + part2(input));
    }

    private static long part1(List<String> input){
        return input.stream()
            .flatMap(line -> Arrays.stream(line.split("\\|")[1].trim().split(" ")))
            .filter(token -> List.of(2,3,4,7).contains(token.length()))
            .count();
    }

    private static long part2(List<String> input){
        Long result = 0L;
        for (String line : input) {
            var tokens = line.split("\\|");
            var signalPatterns = Arrays.asList(tokens[0].trim().split(" "));
            signalPatterns = signalPatterns.stream().map(String::toCharArray).peek(Arrays::sort).map(String::valueOf).collect(Collectors.toList());
            var convertedSignalPatterns = convertSignalPatternsToNumbers(signalPatterns);
            var outputValues = Arrays.asList(tokens[1].trim().split(" "));
            outputValues = outputValues.stream().map(String::toCharArray).peek(Arrays::sort).map(String::valueOf).collect(Collectors.toList());
            result += convertedSignalPatterns.get(outputValues.get(0)) * 1000;
            result += convertedSignalPatterns.get(outputValues.get(1)) * 100;
            result += convertedSignalPatterns.get(outputValues.get(2)) * 10;
            result += convertedSignalPatterns.get(outputValues.get(3));
        }
        return result;
    }

    private static HashMap<String, Integer> convertSignalPatternsToNumbers(List<String> signalPatterns){
        HashMap<String, Integer> values = new HashMap<>();
        String one = signalPatterns.stream().filter(str -> str.length() == 2).findFirst().get();
        values.put(one, 1);
        String four = signalPatterns.stream().filter(str -> str.length() == 4).findFirst().get();
        values.put(four, 4);
        List<String> sixSegmentCandidates = signalPatterns.stream().filter(str -> str.length() == 6).collect(Collectors.toList());
        values.put(searchCandidatesForMatch(sixSegmentCandidates, four), 9);
        values.put(searchCandidatesForMatch(sixSegmentCandidates, one), 0);
        values.put(sixSegmentCandidates.get(0), 6);
        List<String> fiveSegmentCandidates = signalPatterns.stream().filter(str -> str.length() == 5).collect(Collectors.toList());
        values.put(searchCandidatesForMatch(fiveSegmentCandidates, one), 3);
        for (int i = 0; i < fiveSegmentCandidates.size(); i++){
            var candidate = fiveSegmentCandidates.get(i);
            int countOfMissing = 0;
            for (char c : four.toCharArray()){
                if (!candidate.contains(String.valueOf(c))){
                    countOfMissing++;
                }
            }
            if (countOfMissing == 1){
                values.put(candidate, 5);
                fiveSegmentCandidates.remove(i);
            }
        }
        values.put(fiveSegmentCandidates.get(0), 2);
        values.put(signalPatterns.stream().filter(str -> str.length() == 3).findFirst().get(), 7);
        values.put(signalPatterns.stream().filter(str -> str.length() == 7).findFirst().get(), 8);
        return values;
    }

    private static String searchCandidatesForMatch(List<String> candidates, String target){
        for (int i = 0; i < candidates.size(); i++){
            var candidate = candidates.get(i);
            if (target.chars().mapToObj(c->(char)c).allMatch(c-> candidate.contains(String.valueOf(c)))){
                candidates.remove(i);
                return candidate;
            }
        }
        return "";
    }
}
