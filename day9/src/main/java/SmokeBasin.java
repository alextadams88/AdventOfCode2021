import common.InputUtil;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

public class SmokeBasin {

    private static final String filename = "input.txt";

    public static void main(String[] args){
        List<List<Integer>> input = Arrays.stream(InputUtil.getFile(filename).split("\n"))
                                            .map(str -> str.chars().mapToObj(Character::getNumericValue)
                                            .collect(Collectors.toList())).collect(Collectors.toList());
        System.out.println("What is the sum of the risk levels of all low points on your heightmap?");
        System.out.println("Answer: " + part1(input));
        System.out.println("What do you get if you multiply together the sizes of the three largest basins?");
        System.out.println("Answer: " + part2(input));
    }

    private static int part1(List<List<Integer>> input){
        var lowPoints = findLowPoints(input);
        return lowPoints.stream().reduce(0, (subtotal, element) -> subtotal + element.getHeight() + 1, Integer::sum);
    }

    private static Set<Point> findLowPoints(List<List<Integer>> input){
        Set<Point> lowPoints = new HashSet<>();
        for (int i = 0; i < input.size(); i++){
            var row = input.get(i);
            for (int j = 0; j < row.size(); j++){
                boolean isLow = j > 0 ? (row.get(j-1) > row.get(j)) : true;
                isLow = j < row.size()-1 ? isLow && (row.get(j+1) > row.get(j)) : isLow;
                isLow = i > 0 ? isLow && (input.get(i-1).get(j) > row.get(j)) : isLow;
                isLow = i < input.size()-1 ? isLow && (input.get(i+1).get(j) > row.get(j)) : isLow;
                if (isLow){
                    lowPoints.add(new Point(i, j, row.get(j)));
                }
            }
        }
        return lowPoints;
    }

    private static int part2(List<List<Integer>> input){
        var lowPoints = findLowPoints(input);
        Set<Basin> basins = lowPoints.stream().map(point -> new Basin(point, input)).collect(Collectors.toSet());
        Set<Basin> top3 = basins.stream().sorted(((basin1, basin2) -> Integer.compare(basin2.getSize(), basin1.getSize()))).limit(3).collect(Collectors.toSet());
        return top3.stream().reduce(1, (subtotal, element) -> subtotal * element.getSize(), (sub1, sub2) -> sub1 * sub2);
    }

    @Data
    @AllArgsConstructor
    @EqualsAndHashCode
    private static class Point{
        int x;
        int y;
        int height;
    }

    @EqualsAndHashCode
    private static class Basin{
        Set<Point> points;
        public Basin(Point lowPoint, List<List<Integer>> fullMap){
            points = Set.of(lowPoint);
            boolean wholeBasinFound;
            do{
                wholeBasinFound = true;
                HashSet<Point> expandedBasin = new HashSet<>();
                for (Point point : points) {
                    expandedBasin.add(point);
                    var x = point.getX();
                    var y = point.getY();
                    if (x > 0){
                        wholeBasinFound = wholeBasinFound & !shouldAddToBasin(points, expandedBasin, x-1, y, fullMap);
                    }
                    if (x < fullMap.size()-1){
                        wholeBasinFound = wholeBasinFound & !shouldAddToBasin(points, expandedBasin, x+1, y, fullMap);
                    }
                    if(y > 0){
                        wholeBasinFound = wholeBasinFound & !shouldAddToBasin(points, expandedBasin, x, y-1, fullMap);
                    }
                    if(y < fullMap.size()-1){
                        wholeBasinFound = wholeBasinFound & !shouldAddToBasin(points, expandedBasin, x, y+1, fullMap);
                    }
                }
                points = expandedBasin;
            }while(!wholeBasinFound);
        }
        public int getSize(){
            return points.size();
        }
        private boolean shouldAddToBasin(Set<Point> oldSet, Set<Point> newSet, int x, int y, List<List<Integer>> fullMap){
            var candidate = fullMap.get(x).get(y);
            if (candidate != 9) {
                var newPoint = new Point(x, y, candidate);
                if (!oldSet.contains(newPoint)){
                    newSet.add(newPoint);
                    return true;
                }
            }
            return false;
        }
    }

}
