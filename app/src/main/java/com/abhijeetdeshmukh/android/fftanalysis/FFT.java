package com.abhijeetdeshmukh.android.fftanalysis;

/*** Created by ABHIJEET on 05-04-2017.*/

public class FFT {

    int m ; // m is 2^m = mDataPointSize

    //total data points
    private int mDataPointsSize;
    private double mMaxFFTAmplitude ;
    private double mSumOfAmplitudeFFT = 0;

    private double[] mX = new double[256];
    private double[] mY = new double[256];

    private double[] mFFTX ;
    private double[] mFFTY ;
    private double[] mAmplitudeFFT ;
    private double[] mNormalFFTAmp ;

    // Lookup tables. Only need to recompute when size of FFT changes.
    private double[] cos;
    private double[] sin;

    // constructor
    public FFT(int n, double[] x, double[] y) {
        mDataPointsSize = n;
        m = (int) (Math.log(n) / Math.log(2));

        for(int i=0; i< x.length; i++) { mX[i] = x[i];}

        for(int i=0; i< y.length; i++) { mY[i] = y[i];}

        // Make sure n is a power of 2
        if (n != (1 << m))
            throw new RuntimeException("FFT length must be power of 2");

        // precompute tables
        cos = new double[n / 2];
        sin = new double[n / 2];

        for (int i = 0; i < n / 2; i++) {
            cos[i] = Math.cos(-2 * Math.PI * i / n);
            sin[i] = Math.sin(-2 * Math.PI * i / n);
        }
    }

    public void fft() {
        int i, j, k, n1, n2, a;
        double c, s, t1, t2;

        double[] x = new double[mX.length];
        double[] y = new double[mY.length];

        for(int z=0; z< mX.length; z++) { x[z] = mX[z];}
        for(int z=0; z< mY.length; z++) { y[z] = mY[z];}

        int n = mDataPointsSize ;

        // Bit-reverse
        j = 0;
        n2 = n / 2;
        for (i = 1; i < n - 1; i++) {
            n1 = n2;
            while (j >= n1) {
                j = j - n1;
                n1 = n1 / 2;
            }
            j = j + n1;

            if (i < j) {
                t1 = x[i];
                x[i] = x[j];
                x[j] = t1;
                t1 = y[i];
                y[i] = y[j];
                y[j] = t1;
            }
        }

        // FFT
        n1 = 0;
        n2 = 1;

        for (i = 0; i < m; i++) {
            n1 = n2;
            n2 = n2 + n2;
            a = 0;

            for (j = 0; j < n1; j++) {
                c = cos[a];
                s = sin[a];
                a += 1 << (m - i - 1);

                for (k = j; k < n; k = k + n2) {
                    t1 = c * x[k + n1] - s * y[k + n1];
                    t2 = s * x[k + n1] + c * y[k + n1];
                    x[k + n1] = x[k] - t1;
                    y[k + n1] = y[k] - t2;
                    x[k] = x[k] + t1;
                    y[k] = y[k] + t2;
                }
            }
        }

        mFFTX = new double[x.length/2];
        mFFTY = new double[y.length/2];
        for(int z=0; z< x.length/2; z++) {
            mFFTX[z] = x[z];
        }
        for(int z=0; z< y.length/2; z++) {
            mFFTY[z] = y[z];
        }

        ampitudeFFT();
        sumOfAmplitudeFFT();
        maxFFTAmplitude();
        sumOfAmplitudeFFT();
        normalFFTAmp();
    }


    public void ampitudeFFT(){
        mAmplitudeFFT = new double[mFFTY.length];
        for(int z=0; z< mFFTY.length; z++) {
            mAmplitudeFFT[z] = Math.sqrt( mFFTX[z]*mFFTX[z] + mFFTY[z]*mFFTY[z] );
        }
    }

    public void normalFFTAmp(){
        mNormalFFTAmp = new double[mFFTX.length];
        double sumOfAmplitudeFFT = getSumOfAmplitudeFFT();
        for(int z=0; z< mFFTX.length; z++){
            mNormalFFTAmp[z]= mAmplitudeFFT[z]/ sumOfAmplitudeFFT;
        }
    }
    public double[] getNormalFFTAmp() {
        return mNormalFFTAmp;
    }

    private void sumOfAmplitudeFFT(){
        mAmplitudeFFT = new double[mFFTX.length];
        double[] amplitudeFFT = getAmplitudeFFT();
        for (int z=0; z< mAmplitudeFFT.length; z++ ){
            mSumOfAmplitudeFFT = mSumOfAmplitudeFFT + mAmplitudeFFT[z];
        }
    }
    public double getSumOfAmplitudeFFT(){
        return mSumOfAmplitudeFFT;
    }

    public void maxFFTAmplitude(){
        mMaxFFTAmplitude = mAmplitudeFFT[0];
        for(int i = 1; i < mAmplitudeFFT.length; i++) {
            if(mAmplitudeFFT[i] > mMaxFFTAmplitude) {
                mMaxFFTAmplitude = mAmplitudeFFT[i];
            }
        }
    }
    public double getMaxFFTAmplitude(){
        return mMaxFFTAmplitude;
    }

    public int getDataPointsSize (){
        return mDataPointsSize;
    }
    public double[] getX(){
        return mX;
    }
    public double[] getY(){
        return mY;
    }
    public double[] getFFTX(){
        return mFFTX;
    }
    public double[] getFFTY(){
        return mFFTY;
    }
    public double[] getAmplitudeFFT() {
        return mAmplitudeFFT;
    }
}