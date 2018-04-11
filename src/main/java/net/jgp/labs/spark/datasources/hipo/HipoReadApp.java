package net.jgp.labs.spark.datasources.hipo;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

public class HipoReadApp {
    
    public static void main(String[] args) {
        HipoReadApp app = new HipoReadApp();
        app.start(args[0]);
    }
    
    // use "spark://devicename:8888" if running externally
    private boolean start(String path) {
        SparkSession spark = SparkSession.builder()
                .appName("HIPO to Dataset")
                .master("local[*]").getOrCreate();
                
        Dataset<Row> df = spark
                .read()
                .format("hipo")
                .load(path);
        
        df.show(5);       
        return true;
    }
}
