import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;

public class Main {
    public static void main(String[] args) {
        System.out.println("Lab 2.2");

        Garmonic2_2 garmonic = new Garmonic2_2(14,2000, 256);
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

        new SwingWrapper<>(chart).displayChart();
    }
}
