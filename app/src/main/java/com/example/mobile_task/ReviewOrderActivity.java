package com.example.mobile_task;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


public class ReviewOrderActivity extends AppCompatActivity {

    public static ArrayList<OrderModel> ordersArr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_review_order);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ListView ordersListView = (ListView) findViewById(R.id.orderListView);
        ArrayAdapter<String> ordersAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);

        ordersArr.forEach(e -> {
            String data = e.typeOfDish + "\n" + e.mealOption + "\n" + e.size + "                            Total Price: " + e.getPrice() + "$" + "\n" + e.extra + "\n" + e.quantity;
            ordersAdapter.add(data);
        });
        ordersListView.setAdapter(ordersAdapter);

        double totalPrice = ordersArr.stream().mapToDouble(OrderModel::getPrice).sum();

        TextView text = (TextView) findViewById(R.id.costValueTextView);
        text.setText(String.valueOf(totalPrice + "$"));

        Button submitButton = (Button) findViewById(R.id.submitButton);
        submitButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, SubmitOrderActivity.class);
            startActivity(intent);
        });
    }
}