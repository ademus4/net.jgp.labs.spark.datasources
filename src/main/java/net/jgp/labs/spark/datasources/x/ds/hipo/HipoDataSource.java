package net.jgp.labs.spark.datasources.x.ds.hipo;

import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.sources.BaseRelation;
import org.apache.spark.sql.sources.RelationProvider;
import static scala.collection.JavaConverters.mapAsJavaMapConverter;

import scala.collection.immutable.Map;

public class HipoDataSource implements RelationProvider {

    @Override
    public BaseRelation createRelation(
            SQLContext sqlContext,
            Map<String, String> params) {
        
        java.util.Map<String, String> javaMap = mapAsJavaMapConverter(params).asJava();
        
        HipoRelation br = new HipoRelation(javaMap.get("path"));
        br.setSqlContext(sqlContext);
        return br;
    }
}
