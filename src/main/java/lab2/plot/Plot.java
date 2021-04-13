package lab2.plot;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.style.Styler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class Plot {
    List<Series> series = new ArrayList<>();
    private XYChart chart;

    public Plot(String plotName, Series... seriesArray) {
        this.series.addAll(Arrays.asList(seriesArray));
        chart = new XYChartBuilder().theme(Styler.ChartTheme.Matlab)
                .title(plotName).xAxisTitle("X").yAxisTitle("Y")
                .build();
        chart.setCustomXAxisTickLabelsFormatter((x) -> String.format("%.2f", x));
        for (Series series : this.series) {
            chart.addSeries(series.getSeriesName(), series.getXData(), series.getYData());
        }
    }

    @SneakyThrows
    public void save(String filename) {
        log.info("Сохранения графика");
        BitmapEncoder.saveBitmap(chart, filename, BitmapEncoder.BitmapFormat.PNG);
        log.info("График сохранен под названием {}", filename);
    }
}
