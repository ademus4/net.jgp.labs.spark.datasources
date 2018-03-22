package net.jgp.labs.spark.datasources.l100_photo_datasource;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

public class HipoReadApp {
    public static void main(String[] args) {
        HipoReadApp app = new HipoReadApp();
        app.start();
    }

    private boolean start() {
        SparkSession spark = SparkSession.builder()
                .appName("HIPO to Dataset")
                .master("local[*]").getOrCreate();
                
        Dataset<Row> df = spark.read().format("hipo").load();
        
        df.collect();
        df.cache();
        System.out.println("I have imported " + df.count() + " photos.");
        df.printSchema();
        df.show(5);
        
        return true;
    }
}
