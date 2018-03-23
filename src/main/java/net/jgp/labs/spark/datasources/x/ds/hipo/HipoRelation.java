package net.jgp.labs.spark.datasources.x.ds.hipo;

import java.io.Serializable;
import java.util.List;
import net.jgp.labs.spark.datasources.x.extlib.EventData;
import net.jgp.labs.spark.datasources.x.extlib.HipoUtils;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.rdd.RDD;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.sources.BaseRelation;
import org.apache.spark.sql.sources.TableScan;
import org.apache.spark.sql.types.StructType;

import net.jgp.labs.spark.datasources.x.utils.Schema;
import net.jgp.labs.spark.datasources.x.utils.SparkBeanUtils;

public class HipoRelation extends BaseRelation
        implements Serializable, TableScan {
    private static final long serialVersionUID = 4598175080399877334L;

    private SQLContext sqlContext;
    private Schema schema = null;

    @Override
    public RDD<Row> buildScan() {
        schema();
        
        String hipoFile = "/home/adam/uni/jlab/data/clasrun_2475.hipo.2";
        
        List<EventData> events = HipoUtils.processFromFilename(hipoFile);    
        
        @SuppressWarnings("resource")
        JavaSparkContext sparkContext = new JavaSparkContext(sqlContext.sparkContext());
        JavaRDD<Row> rowRDD = sparkContext.parallelize(events)
                .map(event -> SparkBeanUtils.getRowFromBean(schema, event));

        return rowRDD.rdd();
    }
    
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
}
