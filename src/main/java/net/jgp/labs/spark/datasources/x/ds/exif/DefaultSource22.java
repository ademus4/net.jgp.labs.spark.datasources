/**
 * 
 */
package net.jgp.labs.spark.datasources.x.ds.exif;

import org.apache.spark.sql.sources.DataSourceRegister;

/**
 * @author jgp
 *
 */
public class DefaultSource22 extends ExifDirectoryDataSource implements DataSourceRegister {

    /*
     * (non-Javadoc)
     * 
     * @see org.apache.spark.sql.sources.DataSourceRegister#shortName()
     */
    @Override
    public String shortName() {
        return "exif";
    }

}
