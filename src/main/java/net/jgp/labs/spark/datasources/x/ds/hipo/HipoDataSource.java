package net.jgp.labs.spark.datasources.x.ds.hipo;

import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.sources.BaseRelation;
import org.apache.spark.sql.sources.RelationProvider;

import scala.collection.immutable.Map;

public class HipoDataSource implements RelationProvider {

    @Override
    public BaseRelation createRelation(
            SQLContext sqlContext,
            Map<String, String> params) {

        HipoRelation br = new HipoRelation();
        br.setSqlContext(sqlContext);
        return br;
    }
}
