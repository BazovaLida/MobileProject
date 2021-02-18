package ua.kpi.comsys.iv8101;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.*;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import java.util.ArrayList;
import java.util.Objects;

import static android.view.View.INVISIBLE;


//Fragment - analog of the UIViewController
public class DrawingViewController extends Fragment {
    private LineChart lineChart;
    private PieChart pieChart;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_second, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Switch graphSwitch = view.findViewById(R.id.graphSwitch);
        graphSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    pieChart.setVisibility(View.VISIBLE);
                    lineChart.setVisibility(INVISIBLE);
                } else {
                    lineChart.setVisibility(View.VISIBLE);
                    pieChart.setVisibility(INVISIBLE);
                }
            }
        });
        initialiseLineFragment();
        initialisePieFragment();
    }

    private void initialiseLineFragment() {
        lineChart = Objects.requireNonNull(getView()).findViewById(R.id.graphPlot);
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        YAxis yAxisLeft = lineChart.getAxisLeft();
        yAxisLeft.setDrawAxisLine(false);
        yAxisLeft.setDrawGridLines(false);
        yAxisLeft.setDrawZeroLine(true); // draw a zero line

        YAxis yAxisRight = lineChart.getAxisRight();
        yAxisRight.setEnabled(false);

        ArrayList<Entry> dataSet = new ArrayList<>();
        for (float i = -3.14f; i < 3.14f; i += 0.01f) {
            dataSet.add(new Entry(i, (float)Math.cos(i)));
        }
        ArrayList<ILineDataSet> iLineDataSets = new ArrayList<>();
        iLineDataSets.add(new LineDataSet(dataSet, "cos(x)"));
        LineData lineData = new LineData(iLineDataSets);

        Description desc = new Description();
        desc.setText("");
        lineChart.setDescription(desc);
        lineChart.setData(lineData);
        lineChart.invalidate();

    }

    private void initialisePieFragment() {
        ArrayList<PieEntry> dataSet = new ArrayList<PieEntry>();
        dataSet.add(new PieEntry(45, 45));
        dataSet.add(new PieEntry(5, 5));
        dataSet.add(new PieEntry(25, 25));
        dataSet.add(new PieEntry(25, 25));

        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.parseColor("#00bfff")); //blue
        colors.add(Color.parseColor("#7f00ff")); //violet
        colors.add(Color.parseColor("#5D5B5E")); //grey
        colors.add(Color.parseColor("#ffe338")); //yellow

        PieDataSet pieDataSet = new PieDataSet(dataSet,"");
        pieDataSet.setColors(colors);
        PieData pieData = new PieData(pieDataSet);
        pieData.setDrawValues(false);

        pieChart = getView().findViewById(R.id.diagramPlot);
        pieChart.setData(pieData);
        pieChart.invalidate();

        pieChart.getDescription().setEnabled(false);
        pieChart.animate();
        pieChart.setVisibility(INVISIBLE);
    }
}