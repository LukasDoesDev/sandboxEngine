package engine.logging;

import engine.utils.Clock;

public class ConsoleOutput {
    public static void printMessage(String message) {
        Clock.updateClock();
        int hour = Clock.getHour();
        int minute = Clock.getMinute();
        int second = Clock.getSecond();
        System.out.println(
                "[" + Clock.getDay() + "/" + Clock.getMonth()
                        + "/" + Clock.getYear() + " " + hour
                        + ":" + (minute < 10 ? "0" + minute : minute) + ":" + (second < 10 ? "0" + second : second) + "]"
                        + " "
        );
    }

    public static void printError(String errorMessage) {
        Clock.updateClock();
        int hour = Clock.getHour();
        int minute = Clock.getMinute();
        int second = Clock.getSecond();
        System.err.println(
                "[" + Clock.getDay() + "/" + Clock.getMonth()
                        + "/" + Clock.getYear() + " " + hour
                        + ":" + (minute < 10 ? "0" + minute : minute) + ":" + (second < 10 ? "0" + second : second) + "]"
                        + " "
        );
    }

    public static void printException(Exception excp) {
        Clock.updateClock();
        int hour = Clock.getHour();
        int minute = Clock.getMinute();
        int second = Clock.getSecond();
        System.err.print(
                "[" + Clock.getDay() + "/" + Clock.getMonth()
                        + "/" + Clock.getYear() + " " + hour
                        + ":" + (minute < 10 ? "0" + minute : minute) + ":" + (second < 10 ? "0" + second : second) + "]"
                        + " "
        );
        excp.printStackTrace();
    }
}
// getMonth
