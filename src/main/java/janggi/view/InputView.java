package janggi.view;

import janggi.point.Point;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class InputView {

    private static final String ERROR_SUFFIX = " 다시 입력해주세요.";
    private static final Scanner scanner = new Scanner(System.in);

    public boolean readGameStart() {
        try {
            System.out.println("게임을 시작하시겠습니까? (y/n)");
            return YorN.fromText(scanner.nextLine()).toBoolean();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage() + ERROR_SUFFIX);
            return readGameStart();
        }
    }

    public boolean readGameRestart() {
        try {
            System.out.println("기존에 진행하던 게임이 존재합니다. 다시 불러오시겠습니까? (y/n)");
            return YorN.fromText(scanner.nextLine()).toBoolean();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage() + ERROR_SUFFIX);
            return readGameRestart();
        }
    }

    public List<Point> readStartAndTargetPoint() {
        try {
            System.out.println("이동하고 싶은 기물의 좌표와 목적지 좌표를 입력하세요. (row,column->row,column)");

            return Arrays.stream(scanner.nextLine().split("->"))
                .map(this::parseStringToPoint)
                .toList();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage() + ERROR_SUFFIX);
            return readStartAndTargetPoint();
        }
    }

    private Point parseStringToPoint(String input) {
        String[] rawPoint = input.split(",", -1);
        validatePointSize(rawPoint);
        return new Point(parseStringToInteger(rawPoint[0]), parseStringToInteger(rawPoint[1]));
    }

    private void validatePointSize(String[] rawPoint) {
        if (rawPoint.length != 2) {
            throw new IllegalArgumentException("2차원 좌표만 입력 가능합니다.");
        }
    }

    private int parseStringToInteger(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("좌표는 숫자만 입력 가능합니다.");
        }
    }

    enum YorN {
        YES("y", true),
        NO("n", false);

        private final String text;
        private final boolean isYes;

        YorN(String text, boolean isYes) {
            this.text = text;
            this.isYes = isYes;
        }

        private static YorN fromText(String input) {
            return Arrays.stream(values())
                .filter(value -> value.text.equals(input))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("입력은 y/n만 가능합니다."));
        }

        private boolean toBoolean() {
            return isYes;
        }
    }
}
