package com.nightfarmer.bezierdemo;

import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BezierDrawer bezierDrawer = (BezierDrawer) findViewById(R.id.bezierDrawer);
        final TextView textView = (TextView) findViewById(R.id.tv_info);

        bezierDrawer.setOnChangeListener(new BezierDrawer.OnChangeListener() {
            @Override
            public void onchange(List<Point> pointList) {
                String text = "";
                Point point1 = pointList.get(0);
                Point point2 = pointList.get(1);
                Point point3 = pointList.get(2);
                text+="Point1:("+ point1.x+", "+ point1.y+")\n";
                text+="Point2:("+ point2.x+", "+ point2.y+")\n";
                text+="Point3:("+ point3.x+", "+ point3.y+")\n";
                text+="path.moveTo("+ point1.x+", "+ point1.y+");\n";
                text+="path.quadTo("+ point2.x+", "+ point2.y+", "+ point3.x+", "+ point3.y+");";
                textView.setText(text);
            }
        });
    }
}
