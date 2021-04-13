package lab2.method;

import lab2.function.Functions;
import lab2.plot.Plot;
import lab2.plot.Series;
import lab2.util.Derivatives;
import lab2.util.IntervalChecker;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Function;

@Slf4j
public class NewtonMethod implements SolvingMethod {
    private IntervalChecker intervalChecker = new IntervalChecker();

    @Override
    public double solve(Functions function) {
        double a = function.getLeft();
        double b = function.getRight();
        double x, fa = function.getFunction().apply(a), fb = function.getFunction().apply(b), fx, dfx_dx, lastX;
        int i = 0;
        Function<Double, Double> deriveFunc = Derivatives.derive(function.getFunction());
        lastX = chooseInitialApproximation(a, b, fa, fb, deriveFunc);
        while (true) {
            fx = function.getFunction().apply(lastX);
            dfx_dx = deriveFunc.apply(lastX);
            x = lastX - fx / dfx_dx;
            log.info("step {}: x(i)={}, f(x(i))={}, f'(x(i))={}, x(i+1)={},|x(i+1)-x(i)|={}", i, lastX, fx, dfx_dx, x, (lastX == a || lastX == b) ? "---" : Math.abs(x - lastX));
            if (Math.abs(x - lastX) <= function.getEpsilon()) break;
            lastX = x;
            i++;
        }
        return x;
    }

    @Override
    public void checkConditions(Functions function) {
        drawPlot(function);
        if (intervalChecker.maybeNoRoots(function))
            throw new RuntimeException("Значения функции на концах промежутка одного знака.");
        if (intervalChecker.maybeASingleRoot(function))
            log.warn("На этом интервале функция может иметь более одного корня.");
    }

    private void drawPlot(Functions function) {
        Series series = new Series(function.getTextView(), function.getFunction(), function.getLeft(), function.getRight());
        Plot plot = new Plot(function.getFunctionName(), series);
        plot.save("./Метод Ньютона");
    }

    private double chooseInitialApproximation(double a, double b, double fa, double fb, Function<Double, Double> deriveFunc) {
        double lastX;
        Function<Double, Double> secondDeriveFunc = Derivatives.derive(deriveFunc);
        double d2fa_dx2 = secondDeriveFunc.apply(a);
        double d2fb_dx2 = secondDeriveFunc.apply(b);
        log.info("f''(a): {}; f''(b): {}.", d2fa_dx2, d2fb_dx2);
        if (d2fa_dx2 * fa > 0) {
            lastX = a;
            log.info("A={} выбран как х", a);
        } else if (d2fb_dx2 * fb > 0) {
            lastX = b;
            log.info("B={} выбран как х", b);
        } else {
            lastX = a;
            log.warn("Условие быстрой сходимости не выполняется. Выбрана a={}, как x", a);
        }
        return lastX;
    }
}
