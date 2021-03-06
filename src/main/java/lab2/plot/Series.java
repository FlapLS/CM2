package lab2.plot;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Getter
@Setter
public class Series {
    private String seriesName;
    private List<Double> xData = new ArrayList<>();
    private List<Double> yData = new ArrayList<>();
    public static final int STEP = 50;


    public Series(String seriesName) {
        this.seriesName = seriesName;
    }

    public Series(String seriesName, Function<Double, Double> function, double left, double right) {
        this.seriesName = seriesName;
        double step = (right - left) / STEP;

        for (double i = left - step * 5; i <= right + step * 5; i += step) {
            xData.add(i);
            yData.add(function.apply(i));
        }
    }
}
