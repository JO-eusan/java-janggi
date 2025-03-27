package janggi.point;

import java.util.ArrayList;
import java.util.List;

public enum Direction {
    NORTH(-1, 0),
    SOUTH(1, 0),
    WEST(0, -1),
    EAST(0, 1),
    NORTH_WEST(-1, -1),
    NORTH_EAST(-1, 1),
    SOUTH_WEST(1, -1),
    SOUTH_EAST(1, 1);

    private final int rowDistance;
    private final int columnDistance;

    Direction(int rowDistance, int columnDistance) {
        this.rowDistance = rowDistance;
        this.columnDistance = columnDistance;
    }

    public static Direction calculateDirections(Point startPoint, Point targetPoint) {
        int rowGap = startPoint.row() - targetPoint.row();
        int columnGap = startPoint.column() - targetPoint.column();

        if (startPoint.isSameRow(targetPoint) || startPoint.isSameColumn(targetPoint)) {
            return calculateCardinalDirection(rowGap, columnGap);
        }
        return calculateDiagonalDirection(rowGap, columnGap);
    }

    public static List<Direction> calculateDirections(Point startPoint, Point targetPoint, int diagonalCount) {
        int rowGap = startPoint.row() - targetPoint.row();
        int columnGap = startPoint.column() - targetPoint.column();

        Direction cardinal = calculateCardinalDirection(rowGap, columnGap);
        Direction diagonal = calculateDiagonalDirection(rowGap, columnGap);

        return doRouting(diagonalCount, cardinal, diagonal);
    }

    private static Direction calculateCardinalDirection(int rowGap, int columnGap) {
        if (Math.abs(rowGap) > Math.abs(columnGap) && rowGap > 0) {
            return NORTH;
        }
        if (Math.abs(rowGap) > Math.abs(columnGap) && rowGap < 0) {
            return SOUTH;
        }
        if (Math.abs(rowGap) < Math.abs(columnGap) && columnGap > 0) {
            return WEST;
        }
        if (Math.abs(rowGap) < Math.abs(columnGap) && columnGap < 0) {
            return EAST;
        }
        throw new IllegalArgumentException("경로를 찾을 수 없습니다.");
    }

    private static Direction calculateDiagonalDirection(int rowGap, int columnGap) {
        if (rowGap > 0 && columnGap > 0) {
            return NORTH_WEST;
        }
        if (rowGap > 0 && columnGap < 0) {
            return NORTH_EAST;
        }
        if (rowGap < 0 && columnGap > 0) {
            return SOUTH_WEST;
        }
        if (rowGap < 0 && columnGap < 0) {
            return SOUTH_EAST;
        }
        throw new IllegalArgumentException("경로를 찾을 수 없습니다.");
    }

    private static List<Direction> doRouting(int diagonalCount, Direction cardinal,
        Direction diagonal) {
        List<Direction> directions = new ArrayList<>();
        directions.add(cardinal);
        for (int i = 0; i < diagonalCount - 1; i++) {
            directions.add(diagonal);
        }
        return directions;
    }

    public Point move(Point point) {
        return point.move(rowDistance, columnDistance);
    }
}
