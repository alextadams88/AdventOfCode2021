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
            signalPatterns = signalPatterns.stream().map(String::toCharArray).peek(Arrays::sort)
                .map(String::valueOf).collect(Collectors.toList());
            var convertedSignalPatterns = convertSignalPatternsToNumbers(signalPatterns);
            var outputValues = Arrays.asList(tokens[1].trim().split(" "));
            outputValues = outputValues.stream().map(String::toCharArray).peek(Arrays::sort)
                .map(String::valueOf).collect(Collectors.toList());
            result += convertedSignalPatterns.get(outputValues.get(0)) * 1000;
            result += convertedSignalPatterns.get(outputValues.get(1)) * 100;
            result += convertedSignalPatterns.get(outputValues.get(2)) * 10;
            result += convertedSignalPatterns.get(outputValues.get(3));
        }
        return result;
    }

    private static HashMap<String, Integer> convertSignalPatternsToNumbers(List<String> signalPatterns){
        String one = signalPatterns.stream().filter(str -> str.length() == 2).findFirst().get();
        String four = signalPatterns.stream().filter(str -> str.length() == 4).findFirst().get();
        String seven = signalPatterns.stream().filter(str -> str.length() == 3).findFirst().get();
        String eight = signalPatterns.stream().filter(str -> str.length() == 7).findFirst().get();
        List<String> fiveSegmentCandidates = signalPatterns.stream().filter(str -> str.length() == 5).collect(Collectors.toList());
        List<String> sixSegmentCandidates = signalPatterns.stream().filter(str -> str.length() == 6).collect(Collectors.toList());
        String zero = "";
        String two = "";
        String three = "";
        String five = "";
        String six = "";
        String nine = "";
        for (int i = 0; i < sixSegmentCandidates.size(); i++){
            var candidate = sixSegmentCandidates.get(i);
            if (four.chars().mapToObj(c->(char)c).allMatch(c-> candidate.contains(String.valueOf(c)))){
                nine = candidate;
                sixSegmentCandidates.remove(i);
                break;
            }
        }
        for (int i = 0; i < sixSegmentCandidates.size(); i++){
            var candidate = sixSegmentCandidates.get(i);
            if (one.chars().mapToObj(c->(char)c).allMatch(c-> candidate.contains(String.valueOf(c)))){
                zero = candidate;
                sixSegmentCandidates.remove(i);
                break;
            }
        }
        six = sixSegmentCandidates.get(0);
        for (int i = 0; i < fiveSegmentCandidates.size(); i++){
            var candidate = fiveSegmentCandidates.get(i);
            if (one.chars().mapToObj(c->(char)c).allMatch(c-> candidate.contains(String.valueOf(c)))){
                three = candidate;
                fiveSegmentCandidates.remove(i);
                break;
            }
        }
        for (int i = 0; i < fiveSegmentCandidates.size(); i++){
            var candidate = fiveSegmentCandidates.get(i);
            int countOfMissing = 0;
            for (char c : four.toCharArray()){
                if (!candidate.contains(String.valueOf(c))){
                    countOfMissing++;
                }
            }
            if (countOfMissing == 1){
                five = candidate;
                fiveSegmentCandidates.remove(i);
            }
        }
        two = fiveSegmentCandidates.get(0);
        HashMap<String, Integer> values = new HashMap<>();
        values.put(zero, 0);
        values.put(one, 1);
        values.put(two, 2);
        values.put(three, 3);
        values.put(four, 4);
        values.put(five, 5);
        values.put(six, 6);
        values.put(seven, 7);
        values.put(eight, 8);
        values.put(nine, 9);
        return values;
    }
}
