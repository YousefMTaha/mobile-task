package com.example.mobile_task;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


public class MainActivity extends AppCompatActivity {
    private Spinner typesOfDishSpinner;
    private Spinner optionsSpinner;
    private Spinner sizeSpinner;
    private Spinner extraSpinner;
    private EditText quantityEditText;
    private Button addToOrderButton;
    private Button finishOrderButton;
    private TextView orderCostTextView;
    private TextView totalCostTextView;
    private ArrayList<OrderModel> ordersArr = new ArrayList<OrderModel>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        typesOfDishSpinner = (Spinner) findViewById(R.id.typeOfDishSpinner);
        optionsSpinner = (Spinner) findViewById(R.id.optionsSpinner);
        sizeSpinner = (Spinner) findViewById(R.id.sizeSpinner);
        extraSpinner = (Spinner) findViewById(R.id.extraSpinner);
        quantityEditText = (EditText) findViewById(R.id.quantityEditText);
        addToOrderButton = (Button) findViewById(R.id.addToOrderButton);
        finishOrderButton = (Button) findViewById(R.id.finishButton);
        orderCostTextView = (TextView) findViewById(R.id.orderCostValueTextView);
        totalCostTextView = (TextView) findViewById(R.id.totalCostValueTextView);

        final ArrayAdapter<String> optionsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);

        typesOfDishSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String selectedItem = adapterView.getItemAtPosition(position).toString();
                String[] dishTypes = getResources().getStringArray(R.array.typesOfDish);
                String[] options;
                optionsAdapter.clear();
                if (selectedItem.equalsIgnoreCase(dishTypes[0])) {
                    options = getResources().getStringArray(R.array.mainCourseOptions);
                } else if (selectedItem.equalsIgnoreCase(dishTypes[1])) {
                    options = getResources().getStringArray(R.array.sideDishOptions);
                } else {
                    options = getResources().getStringArray(R.array.drinkOptions);
                }
                optionsAdapter.addAll(options);
                optionsSpinner.setAdapter(optionsAdapter);
                updateTotalCost();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        optionsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                updateTotalCost();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        sizeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                updateTotalCost();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        extraSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                updateTotalCost();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        quantityEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                updateTotalCost();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        addToOrderButton.setOnClickListener(view -> {
            if (!quantityEditText.getText().toString().isEmpty() && !quantityEditText.getText().toString().equals("0")) {
                ordersArr.add(updateTotalCost());
                quantityEditText.setText(null);
                orderCostTextView.setText(String.valueOf(0.0));
            } else {
                Toast.makeText(getApplicationContext(), "Please enter quantity", Toast.LENGTH_LONG).show();
            }
        });

        finishOrderButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, ReviewOrderActivity.class);
            startActivity(intent);
            ReviewOrderActivity.ordersArr = ordersArr;
        });
    }

    private OrderModel updateTotalCost() {
        OrderModel model;
        double totalCost = ordersArr.stream().mapToDouble(OrderModel::getPrice).sum();

        if (!quantityEditText.getText().toString().isEmpty() && !quantityEditText.getText().toString().equals("0")) {
            model = new OrderModel(
                    typesOfDishSpinner.getSelectedItem().toString(),
                    optionsSpinner.getSelectedItem().toString(),
                    sizeSpinner.getSelectedItem().toString(),
                    extraSpinner.getSelectedItem().toString(),
                    Integer.parseInt(quantityEditText.getText().toString()));
            orderCostTextView.setText(String.valueOf(model.getPrice()));
            totalCostTextView.setText(String.valueOf(totalCost + model.getPrice()));
            return model;
        } else {
            totalCostTextView.setText(String.valueOf(totalCost));
        }
        return null;
    }

}