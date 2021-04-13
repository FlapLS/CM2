package lab2.io.Console;


import lab2.io.Writer;

public class ConsoleWriter implements Writer {
    @Override
    public void printInfo(String info) {
        System.out.println(info);
    }

}
