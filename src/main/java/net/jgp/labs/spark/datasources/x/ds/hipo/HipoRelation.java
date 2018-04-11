    package net.jgp.labs.spark.datasources.x.ds.hipo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import net.jgp.labs.spark.datasources.x.extlib.EventData;

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
import org.jlab.io.hipo.*;

public class HipoRelation extends BaseRelation
        implements Serializable, TableScan {
    private static final long serialVersionUID = 4598175080399877334L;

    private SQLContext sqlContext;
    private Schema schema = null;
    private String path;
    
    public HipoRelation (String path_) {
        path = path_;
    }

    @Override
    public RDD<Row> buildScan() {
        schema();  // crashes GC if this isn't here..
        
        String hipoFile = path;
        org.jlab.io.hipo.HipoDataSource reader = new org.jlab.io.hipo.HipoDataSource();
        reader.open(hipoFile);
        int nevents = reader.getSize();
        List<EventData> events = new ArrayList<>();
        for(int i = 0; i < nevents; i++){
            HipoDataEvent event = (HipoDataEvent) reader.gotoEvent(i);
            HipoDataBank bank_p = (HipoDataBank) event.getBank("mc::event");
            
            for(int k = 0; k < bank_p.rows(); k++){
                // (pid == -211 || pid == 2212) 
                int pid = bank_p.getInt("pid", k);
                EventData ed = new EventData();
                int status = bank_p.getByte("status",k);
                ed.setPID(pid);
                ed.setPX(bank_p.getFloat("px",k));
                ed.setPY(bank_p.getFloat("py",k));
                ed.setPZ(bank_p.getFloat("pz",k));
                ed.setVX(bank_p.getFloat("vx",k));
                ed.setVY(bank_p.getFloat("vy",k));
                ed.setVZ(bank_p.getFloat("vz",k));
                ed.setStatus(status);
                events.add(ed);
            }
        }
               
        @SuppressWarnings("resource")
        JavaSparkContext sparkContext = new JavaSparkContext(sqlContext.sparkContext());
        JavaRDD<Row> rowRDD = sparkContext.parallelize(events, 16)
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
