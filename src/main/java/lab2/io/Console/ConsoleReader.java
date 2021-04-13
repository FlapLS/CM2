package lab2.io.Console;

import lab2.io.Reader;

import java.util.Scanner;

public class ConsoleReader implements Reader {
    Scanner scanner = new Scanner(System.in);

    @Override
    public int readInt() {
        return scanner.nextInt();
    }

    @Override
    public int readIntWithInfo(String info) {
        System.out.println(info);
        return readInt();
    }

    @Override
    public double readDouble() {
        return scanner.nextDouble();
    }

    @Override
    public double readDoubleWithInfo(String info) {
        System.out.println(info);
        return readDouble();
    }

    @Override
    public String readString() {
        return scanner.next();
    }

    @Override
    public String readStringWithInfo(String info) {
        System.out.println(info);
        return readString();
    }
}
