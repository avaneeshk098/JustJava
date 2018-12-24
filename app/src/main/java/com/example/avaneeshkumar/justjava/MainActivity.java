package com.example.avaneeshkumar.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.icu.text.NumberFormat;

import static android.R.attr.name;
import static android.R.id.message;


/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public int quantity = 0;
    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        CheckBox whippedCreamBox = (CheckBox) findViewById(R.id.whipped_cream);
        CheckBox chocolateBox = (CheckBox) findViewById(R.id.chocolate);
        boolean wChecked = whippedCreamBox.isChecked();
        boolean cChecked = chocolateBox.isChecked();
        int price = calculatePrice();
        String priceMessage = createOrderSummary(price, wChecked, cChecked);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java Order for " + getName());
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    private int calculatePrice(){
        int price = quantity*5;
        CheckBox whippedCreamBox = (CheckBox) findViewById(R.id.whipped_cream);
        CheckBox chocolateBox = (CheckBox) findViewById(R.id.chocolate);
        boolean wChecked = whippedCreamBox.isChecked();
        boolean cChecked = chocolateBox.isChecked();
        if (wChecked){
            price += 1;
        }

        if(cChecked){
            price += 2;
        }
        return price;
    }
    /**
     * This method displays the given quantity value on the screen.
     */



    private String createOrderSummary(int price, boolean addWhippedCream, boolean addChocolate){
        String name = getName();
        String summary = "Name: " + name +
                         "\n" + "Add Whipped Cream? " + addWhippedCream +
                         "\n" + "Add Chocolate? " + addChocolate +
                         "\n" + "Quantity: " + quantity +
                         "\n" + "Total: " + "$ " + price +
                         "\n" + "Thank You!";
        return summary;
    }
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given price on the screen.
     */

    public void addOrder(View view){
        quantity++;
        displayQuantity(quantity);
    }

    public void removeOrder(View view){
        if(quantity <= 0) {
            quantity = 0;
        }else{
            quantity--;
        }
        displayQuantity(quantity);
    }

    private String getName(){
        EditText editText = (EditText) findViewById(R.id.name);
        String name = editText.getText().toString();
        return name;
    }
}

