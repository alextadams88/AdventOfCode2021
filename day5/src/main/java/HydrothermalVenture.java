import common.InputUtil;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.EqualsAndHashCode;

public class HydrothermalVenture {

    private static final String filename = "input.txt";

    public static void main(String[] args){
        var input = InputUtil.readInput(filename);
        System.out.println("Consider only horizontal and vertical lines. At how many points do at least two lines overlap?");
        System.out.println("Answer: " + calculate(input, false));
        System.out.println("Consider all of the lines. At how many points do at least two lines overlap?");
        System.out.println("Answer: " + calculate(input, true));
    }

    private static int calculate(List<String> input, boolean includeAll){
        List<CoordPair> pairs = new ArrayList<>();
        Set<Coord> candidates = new HashSet<>();
        Set<Coord> overlaps = new HashSet<>();
        input.forEach(s -> {
            var coords = s.split("->");
            var start = new Coord(coords[0]);
            var end = new Coord(coords[1]);
            if (includeAll || start.x == end.x || start.y == end.y){
                pairs.add(new CoordPair(start, end));
            }
        });
        pairs.forEach(pair -> {
            var expandedCoords = expandCoordPair(pair);
            expandedCoords.forEach(coord -> {
                if (candidates.contains(coord)){
                    overlaps.add(coord);
                }
                else {
                    candidates.add(coord);
                }
            });
        });
        return overlaps.size();
    }

    private static List<Coord> expandCoordPair(CoordPair pair){
        List<Coord> expandedCoords = new ArrayList<>();
        var xDiff = pair.end.x - pair.start.x;
        var yDiff = pair.end.y - pair.start.y;
        Coord diff = new Coord(xDiff, yDiff);
        while (diff.x != 0 || diff.y != 0){
            expandedCoords.add(new Coord(pair.start.x + diff.x, pair.start.y + diff.y));
            if (diff.x > 0){
                diff.x--;
            }
            else if (diff.x < 0){
                diff.x++;
            }
            if (diff.y > 0){
                diff.y--;
            }
            else if (diff.y < 0){
                diff.y++;
            }
        }
        expandedCoords.add(pair.start);
        return expandedCoords;
    }

    @EqualsAndHashCode
    private static class Coord {
        int x;
        int y;
        public Coord(String coord){
            var tokens = coord.trim().split(",");
            x = Integer.parseInt(tokens[0]);
            y = Integer.parseInt(tokens[1]);
        }
        public Coord(int x, int y){
            this.x = x;
            this.y = y;
        }
    }

    private static class CoordPair {
        Coord start;
        Coord end;
        public CoordPair(Coord start, Coord end){
            this.start = start;
            this.end = end;
        }
    }
}
