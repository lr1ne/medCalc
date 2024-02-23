package com.lori.medcalc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView resultField;
    EditText numberField;
    TextView operationField;
    Double operand = null;
    String lastOperation = "=";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resultField = findViewById(R.id.resultField);
        numberField = findViewById(R.id.numberField);
        operationField = findViewById(R.id.operationField);

        findViewById(R.id.button_plus).setOnClickListener((view)->onOperationClick("+"));
        findViewById(R.id.button_enter).setOnClickListener((view)->onOperationClick("="));

        findViewById(R.id.button0).setOnClickListener((view)->onNumberClick("0"));
        findViewById(R.id.button1).setOnClickListener((view)->onNumberClick("1"));
        findViewById(R.id.button2).setOnClickListener((view)->onNumberClick("2"));
        findViewById(R.id.button3).setOnClickListener((view)->onNumberClick("3"));
        findViewById(R.id.button4).setOnClickListener((view)->onNumberClick("4"));
        findViewById(R.id.button5).setOnClickListener((view)->onNumberClick("5"));
        findViewById(R.id.button6).setOnClickListener((view)->onNumberClick("6"));
        findViewById(R.id.button7).setOnClickListener((view)->onNumberClick("7"));
        findViewById(R.id.button8).setOnClickListener((view)->onNumberClick("8"));
        findViewById(R.id.button9).setOnClickListener((view)->onNumberClick("9"));
        findViewById(R.id.comma).setOnClickListener((view)->onNumberClick(","));
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("OPERATION", lastOperation);
        if(operand!=null)
            outState.putDouble("OPERAND", operand);
        super.onSaveInstanceState(outState);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        lastOperation = savedInstanceState.getString("OPERATION");
        operand= savedInstanceState.getDouble("OPERAND");
        resultField.setText(operand.toString());
        operationField.setText(lastOperation);
    }

    public void onNumberClick(String number){
        numberField.append(number);
        if(lastOperation.equals("=") && operand!=null){
            operand = null;
        }
    }

    public void onOperationClick(String op){
        String number = numberField.getText().toString();
        if(number.length()>0){
            number = number.replace(',', '.');
            try{
                performOperation(Double.valueOf(number), op);
            }catch (NumberFormatException ex){
                numberField.setText("");
            }
        }
        lastOperation = op;
        operationField.setText(lastOperation);
    }

    @SuppressLint("SetTextI18n")
    private void performOperation(Double number, String operation){
        if(operand ==null){
            operand = number;
        }
        else{
            if(lastOperation.equals("=")){
                lastOperation = operation;
            }
            switch(lastOperation){
                case "=":
                    operand = number;
                    break;
                case "+":
                    operand = calculateBMI(operand, number);
                    lastOperation = "=";
                    break;
            }
        }
        resultField.setText(operand.toString().replace('.', ','));
        numberField.setText("");
    }

    private double calculateBMI(Double weight, Double heightInCm) {
        return weight / (heightInCm * heightInCm);
    }

    public void startSettingsActivity(View view) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }
}
