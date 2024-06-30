package com.example.hello;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

class ManageProduct extends BorderPane {
    GetDatabase database = new GetDatabase();
    ArrayList<CategorySales> categorySales = database.getCategorySales();
    Button download = new Button("Click to download detailed transaction");

    ManageProduct( ArrayList<CategorySales>  categorySales){
        this.categorySales = categorySales;

        getStyleClass().add("background-color");
        BarChart<String, Number> barChart = createBarChart();
        setTop(download);
        setAlignment(download, Pos.CENTER);
        setCenter(barChart);
        download.getStyleClass().add("login-input-field");

        download.setOnAction(event -> {
            try {
                File csvFile = generateCSV();
                Desktop.getDesktop().open(csvFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private File generateCSV() throws IOException {
        File csvFile = new File("detailed_transaction.csv");
        FileWriter csvWriter = new FileWriter(csvFile,false);

        csvWriter.append("Name");
        csvWriter.append(",");
        csvWriter.append("Quantity");
        csvWriter.append(",");
        csvWriter.append("Price");
        csvWriter.append(",");
        csvWriter.append("Supplier");
        csvWriter.append(",");
        csvWriter.append("Category");
        csvWriter.append(",");
        csvWriter.append("\n");

            GetDatabase getDatabase = new GetDatabase();

        for (Products  cs : getDatabase.getAllTransactions()) {
            csvWriter.append(cs.name);
            csvWriter.append(",");
            csvWriter.append(String.valueOf(cs.quantity));
            csvWriter.append(",");
            csvWriter.append(String.valueOf(cs.price));
            csvWriter.append(",");
            csvWriter.append(cs.supplier);
            csvWriter.append(",");
            csvWriter.append(cs.category);
            csvWriter.append("\n");
        }

        csvWriter.flush();
        csvWriter.close();

        return csvFile;
    }

    private BarChart<String, Number> createBarChart() {
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);

        xAxis.setLabel("Category");
        yAxis.setLabel("Total Sales");

        for (CategorySales cs : categorySales) {
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName(cs.category);
            series.getData().add(new XYChart.Data<>(cs.category, cs.totalSales));
            barChart.getData().add(series);
        }

        return barChart;
    }
}
class CategorySales {
    String category;
    int totalSales;

    public CategorySales(String category, int totalSales) {
        this.category = category;
        this.totalSales = totalSales;
    }
}

