package net.jgp.labs.spark.datasources.x.ds.hipo;

import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.sources.BaseRelation;
import org.apache.spark.sql.sources.RelationProvider;

import net.jgp.labs.spark.datasources.x.extlib.RecursiveExtensionFilteredLister;
import net.jgp.labs.spark.datasources.x.utils.K;

import static scala.collection.JavaConverters.mapAsJavaMapConverter;
import scala.collection.immutable.Map;

public class HipoDataSource implements RelationProvider {

    @Override
    public BaseRelation createRelation(
            SQLContext sqlContext,
            Map<String, String> params) {

        java.util.Map<String, String> javaMap = mapAsJavaMapConverter(params).asJava();

        HipoRelation br = new HipoRelation();
        br.setSqlContext(sqlContext);
        RecursiveExtensionFilteredLister photoLister = new RecursiveExtensionFilteredLister();
        for (java.util.Map.Entry<String, String> entry : javaMap.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            switch (key) {
            case K.PATH:
                photoLister.setPath(value);
                break;
            case K.RECURSIVE:
                if (value.toLowerCase().charAt(0) == 't') {
                    photoLister.setRecursive(true);
                } else {
                    photoLister.setRecursive(false);
                }
                break;
            case K.LIMIT:
                int limit;
                try {
                    limit = Integer.valueOf(value);
                } catch (NumberFormatException e) {
                    limit = -1;
                }
                photoLister.setLimit(limit);
                break;
            case K.EXTENSIONS:
                String[] extensions = value.split(",");
                for (int i = 0; i < extensions.length; i++) {
                    photoLister.addExtension(extensions[i]);
                }
                break;

            default:
                break;
            }
        }
        
        br.setPhotoLister(photoLister);
        return br;
    }

}
