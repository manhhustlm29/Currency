package com.example.baitap1

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var edit1: EditText
    private lateinit var edit2: EditText
    private lateinit var spinner1: Spinner
    private lateinit var spinner2: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Ánh xạ các thành phần giao diện
        edit1 = findViewById(R.id.edit1)
        edit2 = findViewById(R.id.editt2)
        spinner1 = findViewById(R.id.spinner1)
        spinner2 = findViewById(R.id.spinner2)

        // Cấu hình Spinner với danh sách tỉ lệ tiền tệ
        val tiente = resources.getStringArray(R.array.tiente)
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, tiente)
        spinner1.adapter = adapter
        spinner2.adapter = adapter

        // Thêm TextWatcher cho EditText nguồn
        edit1.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                convertCurrency()
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        // Thêm listener cho Spinner1 (Nguồn)
        spinner1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                convertCurrency()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        // Thêm listener cho Spinner2 (Đích)
        spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                convertCurrency()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    private fun convertCurrency() {
        val sourceCurrency = spinner1.selectedItem.toString()
        val targetCurrency = spinner2.selectedItem.toString()
        val rate = getConversionRate(sourceCurrency, targetCurrency)

        val sourceValue = edit1.text.toString()

        if (sourceValue.isNotEmpty()) {
            val amount = sourceValue.toDouble()
            val convertedAmount = amount * rate
            edit2.setText(convertedAmount.toString())
        } else {
            edit2.text.clear()
        }
    }

    private fun getConversionRate(source: String, target: String): Double {
        val exchangeRates = mapOf(
            "USD" to mapOf("EUR" to 0.85, "VND" to 23000.0, "CNY" to 6.5, "JPN" to 110.0),
            "EUR" to mapOf("USD" to 1.18, "VND" to 27000.0, "CNY" to 7.6, "JPN" to 130.0),
            "VND" to mapOf("USD" to 0.000043, "EUR" to 0.000037, "CNY" to 0.00028, "JPN" to 0.0048),
            "CNY" to mapOf("USD" to 0.15, "EUR" to 0.13, "VND" to 3600.0, "JPN" to 16.5),
            "JPN" to mapOf("USD" to 0.0091, "EUR" to 0.0077, "VND" to 210.0, "CNY" to 0.061)
        )

        return exchangeRates[source]?.get(target) ?: 1.0 // Trả về tỷ giá hoặc 1.0 nếu không có tỷ giá
    }

}
