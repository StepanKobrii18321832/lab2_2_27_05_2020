public class Garmonic2_2 extends Garmonic {
    public Garmonic2_2(int countOfGarmonics, int limitFrequency, int countOfDescreteCalls) {
        super(countOfGarmonics, limitFrequency, countOfDescreteCalls);
    }

    public double[] calculateFFT(double[] sygnals){
        int N = sygnals.length;
        double[] fft_final = new double[N];

        double fr_1 = 0,
                fr_2 = 0, fi_1 = 0, fi_2 = 0;
        for (int p = 0; p < N; p++) {
            for (int k = 0; k < N/2 - 1 ; k++) {
                fi_1 += sygnals[2*k]*Math.sin((4*Math.PI*p*k)/N);
                fr_1 += sygnals[2*k]*Math.cos((4*Math.PI*p*k)/N);
                fi_2 += sygnals[2*k+1]*Math.sin((2*Math.PI*p*(k*2+1))/N);
                fr_2 += sygnals[2*k+1]*Math.cos((2*Math.PI*p*(k*2+1))/N);
            }
            fft_final[p] = Math.sqrt(Math.pow(fr_1+fr_2,2) + Math.pow(fi_1+fi_2,2));
        }
        return fft_final;
    }
    
     public double[] calculateDFT(double[] signalsOfResultingHarmonic){
        int N = signalsOfResultingHarmonic.length;

        Map<Integer, Double> coefficients = getCoefficientsMap(N);

        double[] dft_real = new double[N];
        double[] dft_imaginary = new double[N];
        double[] dft_final = new double[N];

        for (int p = 0; p < N; p++) {
            for (int k = 0; k < N; k++) {
                dft_real[p] += signalsOfResultingHarmonic[k] * Math.cos(coefficients.get((p*k) % N));
                dft_imaginary[p] += signalsOfResultingHarmonic[k] * Math.sin(coefficients.get((p*k) % N));
            }
            dft_final[p] = Math.sqrt(Math.pow(dft_real[p],2) + Math.pow(dft_imaginary[p],2));
        }
        return dft_final;
    }

  public Map<Integer,Double> getCoefficientsMap(int N){
        Map<Integer, Double> coefficients = new HashMap<>();
        for (int p = 0; p < N; p++) {
            for (int k = 0; k < N; k++) {
                coefficients.putIfAbsent((p*k) % N, 2 * Math.PI * p * k / N);
            }
        }
        return  coefficients;
  }
}


