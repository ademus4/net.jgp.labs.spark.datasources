package net.jgp.labs.spark.datasources.x.ds.hipo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import net.jgp.labs.spark.datasources.x.extlib.CSVUtils;
import net.jgp.labs.spark.datasources.x.extlib.EventData;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.rdd.RDD;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.sources.BaseRelation;
import org.apache.spark.sql.sources.TableScan;
import org.apache.spark.sql.types.StructType;

import net.jgp.labs.spark.datasources.x.extlib.ExifUtils;
import net.jgp.labs.spark.datasources.x.extlib.PhotoMetadata;
import net.jgp.labs.spark.datasources.x.extlib.RecursiveExtensionFilteredLister;
import net.jgp.labs.spark.datasources.x.utils.Schema;
import net.jgp.labs.spark.datasources.x.utils.SparkBeanUtils;

public class HipoRelation extends BaseRelation
        implements Serializable, TableScan {
    private static final long serialVersionUID = 4598175080399877334L;

    private SQLContext sqlContext;
    private Schema schema = null;
    private RecursiveExtensionFilteredLister photoLister;

    @Override
    public RDD<Row> buildScan() {
        schema();
        
        String csvFile = "test.txt";
        List<EventData> events = CSVUtils.processFromFilename(csvFile);

        List<PhotoMetadata> table = new ArrayList<PhotoMetadata>();
        List<File> photosToProcess = this.photoLister.getFiles();
        PhotoMetadata photo_temp;

        for (File photoToProcess : photosToProcess) {
            photo_temp = ExifUtils.processFromFilename(photoToProcess.getAbsolutePath());
            table.add(photo_temp);
        }

        @SuppressWarnings("resource")
        JavaSparkContext sparkContext = new JavaSparkContext(sqlContext.sparkContext());
        JavaRDD<Row> rowRDD = sparkContext.parallelize(events)
                .map(event -> SparkBeanUtils.getRowFromBean(schema, event));

        return rowRDD.rdd();
    }

    /**
     * Interface with the real world: the "plumbing" between Spark and existing
     * data, in our case the classes in charge of reading the information from the
     * photos.
     * 
     * The list of photos will be "mapped" and transformed into a Row.
     * 
     * @return
     */

    @Override
    public StructType schema() {
        if (schema == null) {
            schema = SparkBeanUtils.getSchemaFromBean(EventData.class);
        }
        return schema.getSparkSchema();
    }

    @Override
    public SQLContext sqlContext() {
        return this.sqlContext;
    }

    public void setSqlContext(SQLContext sqlContext) {
        this.sqlContext = sqlContext;
    }

    public void setPhotoLister(RecursiveExtensionFilteredLister photoLister) {
        this.photoLister = photoLister;
    }
}
