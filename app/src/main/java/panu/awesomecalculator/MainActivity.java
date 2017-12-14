package panu.awesomecalculator;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.concurrent.LinkedBlockingDeque;


public class MainActivity extends AppCompatActivity {

    //Initialize a FIFO capacity-bounded buffer/log
    private final LinkedBlockingDeque<String> log = new LinkedBlockingDeque<>(10);
    public static final String EXTRA_MESSAGE = "com.panu.awesomecalculator.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //switch activities on user demand
    public void displayLog(View view) {
        Intent intent = new Intent(this, Log.class);
        intent.putExtra(EXTRA_MESSAGE, logToString());
        startActivity(intent);
    }

    //There must be a better way to do this...?
    //Maybe try something like:
    //https://stackoverflow.com/questions/28778813/android-using-context-to-get-all-textview
    public void clearAll(View view) {
        TextView[] textViews = {
                findViewById(R.id.sum1),
                findViewById(R.id.sum2),
                findViewById(R.id.sumResult),
                findViewById(R.id.diff1),
                findViewById(R.id.diff2),
                findViewById(R.id.diffResult),
                findViewById(R.id.multi1),
                findViewById(R.id.multi2),
                findViewById(R.id.multiResult),
                findViewById(R.id.div1),
                findViewById(R.id.div2),
                findViewById(R.id.divResult)};

        for (TextView textView : textViews) {
            textView.setText("");
        }
    }

    public static void findViews(Context context, View v){
        try {
            if (v instanceof ViewGroup) {
                ViewGroup vg = (ViewGroup) v;
                for (int i = 0; i < vg.getChildCount(); i++) {
                    View child = vg.getChildAt(i);
                    //you can recursively call this method
                    findViews(context, child);
                }
            } else if (v instanceof TextView) {
                ((TextView) v).setText("");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void sum(View view) {
        ArrayList<Integer> ints = parseInts(R.id.sum1, R.id.sum2);
        String result = String.valueOf(ints.get(0) + ints.get(1));

        TextView textView = findViewById(R.id.sumResult);
        textView.setText(result);

        String loggedResult = ints.get(0) + " + " + ints.get(1) + " = " + result;
        addLogEntry(loggedResult);
    }

    public void difference(View view) {
        ArrayList<Integer> ints = parseInts(R.id.diff1, R.id.diff2);
        String result = String.valueOf(ints.get(0) - ints.get(1));

        TextView textView = findViewById(R.id.diffResult);
        textView.setText(result);

        String loggedResult = ints.get(0) + " - " + ints.get(1) + " = " + result;
        addLogEntry(loggedResult);
    }

    public void multiply(View view) {
        ArrayList<Integer> ints = parseInts(R.id.multi1, R.id.multi2);
        String result = String.valueOf(ints.get(0) * ints.get(1));

        TextView textView = findViewById(R.id.multiResult);
        textView.setText(result);

        String loggedResult = ints.get(0) + " * " + ints.get(1) + " = " + result;
        addLogEntry(loggedResult);
    }

    public void divide(View view) {
        ArrayList<Integer> ints = parseInts(R.id.div1, R.id.div2);
        String result = String.valueOf((double)ints.get(0) / (double)ints.get(1));

        TextView textView = findViewById(R.id.divResult);
        textView.setText(result);

        String loggedResult = ints.get(0) + " / " + ints.get(1) + " = " + result;
        addLogEntry(loggedResult);
    }

    //---debug---
    public void printLog(View view) {
        Iterator<String> it = log.descendingIterator();
        while (it.hasNext()) {
            System.out.println(it.next());
        }
    }

    public String logToString() {
        String logStr = "";
        Iterator<String> it = log.descendingIterator();
        while (it.hasNext()) {
            logStr += it.next() + "\n";
        }
        return logStr;
    }

    public void addLogEntry(String loggedResult) {
        if(log.remainingCapacity() == 0){
            log.poll();
        }
        log.add(loggedResult);
    }

    public ArrayList<Integer> parseInts(int fieldId1, int fieldId2) {
        ArrayList<Integer> ints = new ArrayList<>();
        EditText e1 = (EditText) findViewById(fieldId1);
        EditText e2 = (EditText) findViewById(fieldId2);

        try {
            ints.add(Integer.parseInt(e1.getText().toString()));
        }
        catch (Exception e) {
            ints.add(0);
        }

        try {
            ints.add(Integer.parseInt(e2.getText().toString()));
        }
        catch (Exception e) {
            ints.add(0);
        }
        return ints;
    }
}

