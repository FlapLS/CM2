package lab2.function;


import lombok.Getter;
import lombok.Setter;

import java.util.function.Function;

import static java.lang.Math.*;

@Getter
public enum Functions {
    f1("Многочлен 3й степени (из варианта)",
            x -> pow(x, 3) + 2.84 * pow(x, 2) - 5.606 * x - 14.766,
            "y = x^3 + 2.84x^2 − 5.606x − 14.766"),
    f2("Многочлен 3й степени (из лекции)",
            x -> pow(x, 3) - x + 4,
            "y = x^3 - x + 4"),
    f3("Периодическая функция 1",
            x -> sin(pow(x, 5) - 10),
            "y = sin(x^5 -10)"),
    f4("Функция с корнями",
            x -> pow(x, 1 / 3d) - pow(x, 1 / 10d),
            "y = x^(1/3) - x^(1/10)"),
    f5("Периодическая функция 2",
            x -> exp(pow(sin(x), 3))  + 2.5,
            "y = e^(sin^3(x)) + 2.5");
    private Function<Double, Double> function;
    private String textView;
    private String functionName;
    private double left;
    private double right;
    @Setter
    private double epsilon;

    Functions(String functionName, Function<Double, Double> function, String textView) {
        this.functionName = functionName;
        this.function = function;
        this.textView = textView;
    }

    public void setLimits(double a, double b) {
        if (a > b) {
            throw new RuntimeException("Левый граница больше правой.");
        }
        this.left = a;
        this.right = b;
    }

}
