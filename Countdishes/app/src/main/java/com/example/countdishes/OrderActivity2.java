package com.example.countdishes;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class OrderActivity2 extends AppCompatActivity {

    private List<String> dishesList;
    private Button addOrderButton;
    LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order2);

        dishesList = (ArrayList<String>)getIntent().getStringArrayListExtra(OrderActivity.FOODPORTIONS);
        addOrderButton = findViewById(R.id.add_order_btn);
        layout = findViewById(R.id.container);

        addOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showChangeDishesDialog();
            }
        });

    }

    private void showChangeDishesDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(OrderActivity2.this);
        builder.setTitle("Choose Dishes...");
        builder.setSingleChoiceItems(dishesList.toArray(new String[dishesList.size()]), -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                addOrder(dishesList.get(i));
                dialogInterface.dismiss();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void addOrder(String dishesName) {
        View view = getLayoutInflater().inflate(R.layout.order,null);

        TextView name = view.findViewById(R.id.name);
        Button delete = view.findViewById(R.id.remove_btn);

        name.setText(dishesName);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout.removeView(view);
            }
        });

        layout.addView(view);
    }
}