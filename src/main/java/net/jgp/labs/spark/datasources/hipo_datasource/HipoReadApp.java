package net.jgp.labs.spark.datasources.hipo_datasource;

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
        Dataset df_results = df.filter("charge = 1");
        df.show(5);
        df_results.show(5);
        
        return true;
    }
}
