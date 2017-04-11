package com.abhijeetdeshmukh.android.fftanalysis;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.TextView;
import android.widget.Toast;

import com.androidplot.xy.BoundaryMode;
import com.androidplot.xy.CatmullRomInterpolator;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.PanZoom;
import com.androidplot.xy.ScalingXYSeries;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYGraphWidget;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;

import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import java.util.Arrays;


public class MainActivity extends AppCompatActivity {

    private XYPlot plotter;

    private double[] ampFFT ;

    private TextView msizeXTextView;
    private TextView msizeYTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        double[] x = {636, 579, 651, 783, 816, 679, 520, 564, 775, 865, 723, 606, 701, 851, 844, 680,
                559, 608, 757, 819, 725, 629, 702, 843, 822, 649, 556, 644, 828, 898, 748, 572, 611,
                804, 885, 760, 591, 535, 663, 852, 847, 665, 589, 731, 839, 748, 610, 584, 689, 827,
                797, 610, 550, 721, 876, 799, 623, 603, 762, 881, 785, 576, 486, 641, 831, 790, 575,
                496, 660, 853, 832, 640, 547, 666, 799, 780, 642, 566, 644, 787, 813, 682, 548, 601,
                789, 859, 709, 520, 534, 726, 843, 725, 540, 570, 807, 931, 751, 498, 510, 741, 861,
                756, 576, 571, 741, 844, 743, 578, 588, 732, 802, 724, 629, 654, 766, 822, 736, 614,
                626, 760, 831, 765, 629, 559, 650, 807, 812, 664, 578, 674, 805, 773, 618, 546, 669,
                808, 777, 634, 578, 701, 855, 838, 673, 580, 676, 804, 760, 575, 504, 642, 813, 808,
                651, 543, 620, 786, 823, 666, 546, 624, 795, 835, 705, 582, 601, 731, 781, 681, 573,
                615, 737, 761, 668, 590, 642, 765, 827, 754, 616, 608, 761, 868, 763, 601, 600, 727,
                771, 668, 567, 599, 724, 825, 798, 663, 601, 737, 885, 791, 571, 571, 787, 906, 790,
                605, 538, 654, 838, 836, 653, 599, 755, 835, 702, 551, 542, 667, 806, 792, 612, 527,
                670, 834, 787, 639, 612, 751, 863, 787, 583, 516, 662, 840, 852, 730, 632, 659, 767,
                791, 675, 558, 632, 795, 810, 628, 480, 586, 841, 924, 718, 509, 580, 823, 895, 705,
                506, 562};

        double[] y ={0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
                0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
                0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
                0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
                0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
                0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
                0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};

        int sizeX = x.length;
        int sizeY = y.length;

        msizeXTextView = (TextView) findViewById(R.id.textView);
        msizeYTextView = (TextView) findViewById(R.id.textView2);

        msizeXTextView.setText("sizeX " + sizeX);
        msizeYTextView.setText("sizeY " + sizeY);

        if (sizeX != sizeY) {
            Toast.makeText(this, "Array size not same" + sizeX + "and" + sizeY,
                    Toast.LENGTH_SHORT).show();
            return;
        }

        FFT fft1 = new FFT(256, x, y);
        fft1.fft();
        ampFFT = new double[ampFFT.length];
        ampFFT = fft1.getNormalFFTAmp();

        String xoutput = new String(" X input: ");

        for (int i=0; i < fft1.getFFTX().length ; i++){
            xoutput = xoutput + x[i] + ", ";
        }

        String youtput = new String(" FFT normalized Amplitude: ");

        for (int i=0; i < fft1.getFFTY().length ; i++){
            youtput = youtput + ampFFT[i] + ",     ";
        }

        msizeXTextView.setText(xoutput);
        msizeYTextView.setText(youtput);

        // initialize our XYPlot reference:
        plotter = (XYPlot) findViewById(R.id.plot);

        final Number[] domainLabels = new Number[ampFFT.length];
        for(int z=1; z < ampFFT.length; z++){
            domainLabels[z] = z;
        }

        // create a couple arrays of y-values to plot:
        Number[] series1Numbers = new Number[ampFFT.length];
        for(int z=0; z < ampFFT.length; z++)
            series1Numbers[z] = ampFFT[z];

//        Number[] series2Numbers = new Number[fft1.getFFTY().length];
//        for(int z=0; z < fft1.getFFTY().length; z++)
//            series2Numbers[z] = fft1.getFFTY()[z];

        // turn the above arrays into XYSeries':
        // (Y_VALS_ONLY means use the element index as the x value)
        XYSeries series1 = new SimpleXYSeries(
                Arrays.asList(series1Numbers), SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "Series1");
//        XYSeries series2 = new SimpleXYSeries(
//                Arrays.asList(series2Numbers), SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "Series2");

        // create formatters to use for drawing a series using LineAndPointRenderer
        // and configure them from xml:
        final LineAndPointFormatter series1Format =
                new LineAndPointFormatter(this, R.xml.line_point_formatter);

//        final LineAndPointFormatter series2Format =
//                new LineAndPointFormatter(this, R.xml.line_point_formatter);
//        series2Format.getLinePaint().setColor(Color.RED);

        // just for fun, add some smoothing to the lines:
        // see: http://androidplot.com/smooth-curves-and-androidplot/
        series1Format.setInterpolationParams(new CatmullRomInterpolator.Params(10, CatmullRomInterpolator.Type.Centripetal));

//        series2Format.setInterpolationParams(new CatmullRomInterpolator.Params(10, CatmullRomInterpolator.Type.Centripetal));

        // wrap each series in instances of ScalingXYSeries before adding to the plot
        // so that we can animate the series values below:
        final ScalingXYSeries scalingSeries1 = new ScalingXYSeries(series1, 0, ScalingXYSeries.Mode.Y_ONLY);
        plotter.addSeries(scalingSeries1, series1Format);

//        final ScalingXYSeries scalingSeries2 = new ScalingXYSeries(series2, 0, ScalingXYSeries.Mode.Y_ONLY);
//        plot.addSeries(scalingSeries2, series2Format);

        plotter.getGraph().getLineLabelStyle(XYGraphWidget.Edge.BOTTOM).setFormat(new Format() {
            @Override
            public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
                int i = Math.round(((Number) obj).floatValue());
                return toAppendTo.append(domainLabels[i]);
            }
            @Override
            public Object parseObject(String source, ParsePosition pos) {
                return null;
            }
        });

      //  PanZoom.attach(plotter);

        plotter.setRangeBoundaries(0,1, BoundaryMode.SHRINK);

        // animate a scale value from a starting val of 0 to a final value of 1:
        ValueAnimator animator = ValueAnimator.ofFloat(0, 1);

        // use an animation pattern that begins and ends slowly:
        animator.setInterpolator(new AccelerateDecelerateInterpolator());

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float scale = valueAnimator.getAnimatedFraction();
                scalingSeries1.setScale(scale);
//                scalingSeries2.setScale(scale);
                plotter.redraw();
            }
        });
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                // the animation is over, so show point labels:
                series1Format.setPointLabelFormatter(null);
//                series2Format.setPointLabelFormatter(null);
//                series1Format.getPointLabelFormatter().getTextPaint().setColor(Color.WHITE);
//                series2Format.getPointLabelFormatter().getTextPaint().setColor(Color.WHITE);
                plotter.redraw();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

        // the animation will run for 1.5 seconds:
        animator.setDuration(1500);
        animator.start();
    }
}
