package io.github.lukasdoesdev.sandboxEngine.engine.logging;

import io.github.lukasdoesdev.sandboxEngine.engine.utils.Clock;

import static org.fusesource.jansi.Ansi.*;
import static org.fusesource.jansi.Ansi.Color.*;

public class ConsoleOutput {
    public static void printMessage(String message) {
        Clock.updateClock();
        int hour = Clock.getHour();
        int minute = Clock.getMinute();
        int second = Clock.getSecond();
        System.out.println(
                ansi().fg(WHITE).a("[" + Clock.getDay() + "/" + Clock.getMonth()
                        + "/" + Clock.getYear() + " " + hour
                        + ":" + (minute < 10 ? "0" + minute : minute) + ":" + (second < 10 ? "0" + second : second) + "]"
                        + " "
                        + "INFO: " + message).reset()
        );
    }

    public static void printError(String errorMessage) {
        Clock.updateClock();
        int hour = Clock.getHour();
        int minute = Clock.getMinute();
        int second = Clock.getSecond();
        System.err.println(
                ansi().fg(RED).a("[" + Clock.getDay() + "/" + Clock.getMonth()
                        + "/" + Clock.getYear() + " " + hour
                        + ":" + (minute < 10 ? "0" + minute : minute) + ":" + (second < 10 ? "0" + second : second) + "]"
                        + " "
                        + "ERROR: " + errorMessage).reset()
        );
    }

    public static void printException(Exception excp) {
        Clock.updateClock();
        int hour = Clock.getHour();
        int minute = Clock.getMinute();
        int second = Clock.getSecond();
        System.err.print(
                ansi().fg(RED).a("[" + Clock.getDay() + "/" + Clock.getMonth()
                        + "/" + Clock.getYear() + " " + hour
                        + ":" + (minute < 10 ? "0" + minute : minute) + ":" + (second < 10 ? "0" + second : second) + "]"
                        + " "
                        + "EXEPTION: ").reset()
        );
        excp.printStackTrace();
    }
}
