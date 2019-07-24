package com.galaxy.rdd

import org.apache.spark.{SparkConf, SparkContext}

object CsvToParquet {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("CSVParquet").setMaster("local[3]")
    val sc = new SparkContext(conf)

    sc.makeRDD(Seq(1, 5, 8, 45, 23, 45, 26, 21, 5, 6, 4))
      .map(_/2).collect()

    sc.stop()
    println(sc.isStopped)
    sc.stop()
  }
}
