import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        System.out.println("Lab 2.2");

        Garmonic2_2 garmonic = new Garmonic2_2(12,1100, 64);
        double[] i = new double[garmonic.getCountOfDescreteCalls()];
        for (int i1 = 0; i1 < i.length; i1++) {
            i[i1] = i1;
        }

        XYChart chart = new XYChartBuilder()
                .width(600)
                .height(400)
                .title("x(t)")
                .xAxisTitle("t")
                .yAxisTitle("x")
                .build();


        double[] sygnals = garmonic.calculateSygnalsForResultingGarmonic();
        chart.addSeries("Fourier Function", i, garmonic.calculateFFT(sygnals));

        List<Long> DFTTime = new ArrayList<>();
        List<Long> FFTTime = new ArrayList<>();

        for (int i1 = 0; i1 < 100; i1++) {
            long time = System.currentTimeMillis();
            garmonic.calculateFFT(sygnals);
            FFTTime.add(System.currentTimeMillis() - time);

            time = System.currentTimeMillis();
            garmonic.calculateDFT(sygnals);
            DFTTime.add(System.currentTimeMillis() - time);
        }
        System.out.println("Середній час виконання DFT: " + DFTTime.stream().mapToLong(x -> x).average().getAsDouble());
        System.out.println("Середній час виконання FFT: " + FFTTime.stream().mapToLong(x->x).average().getAsDouble());

        new SwingWrapper<>(chart).displayChart();
    }
}
