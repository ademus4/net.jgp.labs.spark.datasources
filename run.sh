#!/usr/bin/env bash
/home/adam/dev/spark-2.2.1-bin-hadoop2.7/bin/spark-submit \
  --class net.jgp.labs.spark.datasources.hipo.HipoReadApp \
  --master spark://thornton-hp-elitebook:8888 \
  --jars ./target/labs-spark-datasources-1.0.0-SNAPSHOT.jar, \
         /home/adam/dev/coatjava/lib/clas/coat-libs-5.1-SNAPSHOT.jar
