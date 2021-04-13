package lab2;

import lab2.function.Functions;
import lab2.io.*;
import lab2.io.Console.ConsoleReader;
import lab2.io.Console.ConsoleWriter;
import lab2.io.File.FileReader;
import lab2.io.File.FileWriter;
import lab2.method.ChordMethod;
import lab2.method.NewtonMethod;
import lab2.method.SimpleIterationMethod;
import lab2.method.SolvingMethod;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.InputMismatchException;

@Slf4j
public class Main {
    private static final String commandFormat = "[-cnm] [-i file_path] [-o file_path]\n" +
            "-c Метод хорд\n" +
            "-n Метод Ньютона\n" +
            "-s Метод простых итераций";
    private static Reader input;
    private static Writer out;
    private static SolvingMethod method;
    private static Functions function;

    public static void main(String[] args) {
        try {
            configure(args);
            chooseFunction();
            chooseLimits();
            function.setEpsilon(input.readDoubleWithInfo("Введите эпсилон:"));
            double solution = method.solve(function);
            out.printInfo("Корень на промежутке [" + function.getLeft() + "; " + function.getRight() + "]: " + solution);
        } catch (InputMismatchException e) {
            log.error("Введённые данные некоректны");
            System.err.println("Введённые данные некоректны");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void chooseFunction() {
        StringBuilder message = new StringBuilder("Выберите функцию:\n");
        for (int i = 0; i < Functions.values().length - 1; i++) {
            message.append(i + 1).append("). ").append(Functions.values()[i].getTextView()).append("\n");
        }

        message.append(Functions.values().length).append("). ").append(Functions.values()[Functions.values().length - 1].getTextView());
        try {
            function = Functions.values()[input.readIntWithInfo(message.toString()) - 1];
            log.info("Выбрана функция: {}", function.getTextView());
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new RuntimeException("Номер функции вне диапазона");
        }
    }

    private static void chooseLimits() {
        while (true) {
            function.setLimits(input.readDoubleWithInfo("Введите левую границу: "), input.readDoubleWithInfo("Введите правую границу: "));
            log.info("Выбраный интервал [{}; {}]", function.getLeft(), function.getRight());
            method.checkConditions(function);

            if (input.getClass().equals(FileReader.class)) break;

            String answer = input.readStringWithInfo("Вы хотите изменить интервал? y/n");
            if (answer.equals("n")) break;
            if (!answer.equals("y")) throw new RuntimeException("Некорректный ввод");
        }
    }


    @SneakyThrows
    private static void configure(String[] args) {
        input = new ConsoleReader();
        out = new ConsoleWriter();
        if (args.length < 1 || args.length > 5)
            throw new RuntimeException("Неверное количество аргументов\n" + commandFormat);

        switch (args[0]) {
            case "-c":
                method = new ChordMethod();
                break;
            case "-n":
                method = new NewtonMethod();
                break;
            case "-s":
                method = new SimpleIterationMethod();
                break;
            default:
                throw new RuntimeException("Неверный формат команды\n" + commandFormat);
        }
        if (args.length > 1) {
            if (args[1].equals("-i")) input = new FileReader(args[2]);
            else if (args[1].equals("-o")) out = new FileWriter(args[2]);
            else throw new RuntimeException("Неверный второй аргумент\n" + commandFormat);
        }
        if (args.length > 3) {
            if (args[3].equals("-i")) input = new FileReader(args[4]);
            else if (args[3].equals("-o")) out = new FileWriter(args[4]);
            else throw new RuntimeException("Неверный четвёртый аргумент\n" + commandFormat);
        }
        log.info("Читаем с помощью: {}", input.getClass().getTypeName());
        log.info("Записываем с помщоью: {}", out.getClass().getTypeName());
    }
}
