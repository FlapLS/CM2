package lab2.util;

import lab2.function.Functions;
import lab2.plot.Series;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Function;

@Slf4j
public class IntervalChecker {
    public boolean maybeNoRoots(Functions functions) {
        double fa = functions.getFunction().apply(functions.getLeft());
        double fb = functions.getFunction().apply(functions.getRight());
        return !(fa * fb <= 0);
    }

    public boolean maybeASingleRoot(Functions functions) {
        double a = functions.getLeft();
        double b = functions.getRight();
        double step = (b - a) / Series.STEP;
        Function<Double, Double> deriveFun = Derivatives.derive(functions.getFunction());
        double dfi_dx, dfa_dx = deriveFun.apply(a);
        double i = a + step;
        boolean maybe = true;
        while (i <= b) {
            dfi_dx = deriveFun.apply(i);
            if (dfi_dx * dfa_dx <= 0) {
                log.info("Производная меняет знак немного раньше {}", i);
                maybe = false;
            }
            dfa_dx = dfi_dx;
            i += step;
        }
        return !maybe;
    }

    public boolean functionLessThanOne(Function<Double, Double> function, double a, double b) {
        double step = (b - a) / Series.STEP;
        boolean lessThanOne = true;
        for (double i = a; i <= b; i += step) {
            if (Math.abs(function.apply(i)) >= 1) {
                log.info("f({})={}", i, function.apply(i));
                lessThanOne = false;
            }
        }
        return lessThanOne;
    }
}
