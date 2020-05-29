import java.util.HashMap;
import java.util.Map;
import java.util.Random;


public class Garmonic {
    private int countOfGarmonics;
    private int limitFrequency;
    private int countOfDescreteCalls;
    private double[][] sygnalsForAllGarmonics;
    private double[] sygnalsForResultingGarmonic;

    public Garmonic(int countOfGarmonics, int limitFrequency, int countOfDescreteCalls) {
        this.countOfGarmonics = countOfGarmonics;
        this.limitFrequency = limitFrequency;
        this.countOfDescreteCalls = countOfDescreteCalls;
        this.sygnalsForAllGarmonics = new double[countOfGarmonics][countOfDescreteCalls];
        this.sygnalsForResultingGarmonic = new double[countOfDescreteCalls];
    }
    public Garmonic(){}


    
    public double calculateRGBNumberForChart(){
        double sum = 0;
        final double rgbLimit = 255;
        for (double v : getSygnalsForResultingGarmonic()) {
            sum+=v;
        }
        return sum * (rgbLimit/(1.*getCountOfDescreteCalls()/1));
    }

  
    public double[][] calculateSygnalsForAllGarmonics(){
        Random r = new Random();
        double A = r.nextDouble();
        double fi = r.nextDouble()*Math.PI;
        for (int i = 0; i < getCountOfGarmonics(); i++) {
            for (int j = 0; j < getCountOfDescreteCalls(); j++) {
                getSygnalsForAllGarmonics()[i][j] = A*Math.sin(1.0*getLimitFrequency()*(i+1)/getCountOfGarmonics()*j + fi);
            }
        }
        return getSygnalsForAllGarmonics();
    }

  
    public double[] calculateSygnalsForResultingGarmonic(){
        Random r = new Random();
        double A = r.nextDouble();
        double fi = r.nextDouble()*Math.PI;

        for (int i = 0; i < getCountOfGarmonics(); i++) {
            for (int j = 0; j < getCountOfDescreteCalls(); j++) {
                getSygnalsForResultingGarmonic()[j] += A * Math.sin(1.*getLimitFrequency()*(i+1)/getCountOfGarmonics()*j + fi) ;//TODO delete A
            }
        }
 
        return getSygnalsForResultingGarmonic();
    }


    public double calculateMathExpectation(){
        double sum = 0 ;
        for (double sygnal : getSygnalsForResultingGarmonic()) {
            sum+=sygnal;
        }
        return sum/getSygnalsForResultingGarmonic().length;
    }


    public double calculateDispersion(){
        double sum = 0;
        double mathExpectation = calculateMathExpectation();
        for (double sygnal : getSygnalsForResultingGarmonic()) {
            sum += Math.pow(sygnal - mathExpectation, 2);
        }
        return sum/(getSygnalsForResultingGarmonic().length - 1);
    }


   

    public double[] calculateCorrelationWithOtherFunc(Garmonic otherGarmonic){ ////TODO Lab1.2
        double[] correlation_arr = new double[getCountOfDescreteCalls()/2];
        double mathExp = calculateMathExpectation();
        double mathExp2 = otherGarmonic.calculateMathExpectation();

        for (int tau = 0; tau < getCountOfDescreteCalls()/2; tau++){
            double correlation = 0;
            for (int t = 0; t < getCountOfDescreteCalls()/2; t++){
                correlation += (getSygnalsForResultingGarmonic()[t] - mathExp)*(otherGarmonic.getSygnalsForResultingGarmonic()[t+tau] - mathExp2);
            }
            correlation_arr[tau] = correlation/(getCountOfDescreteCalls()-1);
        }
        return correlation_arr;
    }

    public double[] calculateCorrelation(){ 
        return calculateCorrelationWithOtherFunc(this);
    }

    public long calculateExecutionTimeRxx(){
        long startTime = System.nanoTime();
        calculateCorrelation();
        return System.nanoTime() - startTime;
    }
    public long calculateExecutionTimeRxy(Garmonic harmonic){
        long startTime = System.nanoTime();
        calculateCorrelationWithOtherFunc(harmonic);
        return System.nanoTime() - startTime;
    }

    
    public double[] calculateDFT(double[] sygnalsOfResultingGarmonic){

        int N = sygnalsOfResultingGarmonic.length;

        Map<Integer, Double> coefficients = getCoefficientsMap(N);

        double[] dft_real = new double[N];
        double[] dft_image = new double[N];
        double[] dft_final = new double[N];

        for (int p = 0; p < N; p++) {
            for (int k = 0; k < N; k++) {
                dft_real[p] += sygnalsOfResultingGarmonic[k] * Math.cos(coefficients.get((p*k) % N));
                dft_image[p] += sygnalsOfResultingGarmonic[k] * Math.sin(coefficients.get((p*k) % N));  //Math.sin(Wn[p][k]);
            }
            dft_final[p] = Math.sqrt(Math.pow(dft_real[p],2) +
                    Math.pow(dft_image[p],2));
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

    
    public double[] calculateDFT(){
        return calculateDFT(getSygnalsForResultingGarmonic());
    }

    private double WReal(int p, int k, int N){
        return Math.cos(2*Math.PI*p*k/N);
    }

    private double WImage(int p, int k, int N){
        return Math.sin(2*Math.PI*p*k/N);
    }

    


    public int getCountOfGarmonics() {
        return countOfGarmonics;
    }

    public void setCountOfGarmonics(int countOfGarmonics) {
        this.countOfGarmonics = countOfGarmonics;
    }

    public int getLimitFrequency() {
        return limitFrequency;
    }

    public void setLimitFrequency(int limitFrequency) {
        this.limitFrequency = limitFrequency;
    }

    public int getCountOfDescreteCalls() {
        return countOfDescreteCalls;
    }

    public void setCountOfDescreteCalls(int countOfDescreteCalls) {
        this.countOfDescreteCalls = countOfDescreteCalls;
    }

    public double[][] getSygnalsForAllGarmonics() {
        return sygnalsForAllGarmonics;
    }

    public void setSygnalsForAllGarmonics(double[][] sygnalsForAllGarmonics) {
        this.sygnalsForAllGarmonics = sygnalsForAllGarmonics;
    }

    public double[] getSygnalsForResultingGarmonic() {
        return sygnalsForResultingGarmonic;
    }

    public void setSygnalsForResultingGarmonic(double[] sygnalsForResultingGarmonic) {
        this.sygnalsForResultingGarmonic = sygnalsForResultingGarmonic;
    }

}

