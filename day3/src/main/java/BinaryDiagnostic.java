import common.InputUtil;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class BinaryDiagnostic {

    private static final String filename = "input.txt";

    public static void main(String[] args){
        var input = InputUtil.readInput(filename);
        System.out.println("What is the power consumption of the submarine?");
        System.out.println("Answer: " + part1(input));
        System.out.println("What is the life support rating of the submarine?");
        System.out.println("Answer: " + part2(input));
    }

    private static int part1(List<String> input){
        StringBuilder finalNumber = new StringBuilder();
        for (int i = 0; i < input.get(0).length(); i++){
            var ones = 0;
            for (String s : input) {
                if (s.charAt(i) == '1') ones++;
            }
            finalNumber.append(ones > input.size() / 2 ? 1 : 0);
        }
        var gamma = Integer.parseUnsignedInt(finalNumber.toString(), 2);
        var mask = (Integer.highestOneBit(gamma) << 1) - 1;
        var epsilon = ~gamma & mask;
        return gamma * epsilon;
    }

    private static int part2(List<String> input){
        var oxygenGeneratorRating = filterRating(input, 0, findMostCommonBit);
        var co2ScrubberRating = filterRating(input, 0, findLeastCommonBit);
        return oxygenGeneratorRating * co2ScrubberRating;
    }

    private static int filterRating(List<String> candidates, int currentIndex, Function<List<Character>, Character> bitCriteriaFunction){
        if (candidates.size() == 1){
            return Integer.parseUnsignedInt(candidates.get(0), 2);
        }
        var bitMatch = bitCriteriaFunction.apply(candidates.stream().map(s -> s.charAt(currentIndex)).collect(Collectors.toList()));
        candidates = candidates.stream().filter(s -> s.charAt(currentIndex) == bitMatch).collect(Collectors.toList());
        return filterRating(candidates, currentIndex + 1, bitCriteriaFunction);
    }

    private static Function<List<Character>, Character> findMostCommonBit = bits -> {
        var onesCount = bits.stream().filter(i -> i.equals('1')).count();
        return onesCount >= bits.size() / 2.0 ? '1' : '0';
    };

    private static Function<List<Character>, Character> findLeastCommonBit = bits -> {
        var mostCommon = findMostCommonBit.apply(bits);
        return mostCommon == '1' ? '0' : '1';
    };
}
