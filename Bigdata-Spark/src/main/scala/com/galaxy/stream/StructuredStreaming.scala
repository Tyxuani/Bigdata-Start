package com.galaxy.stream

import org.apache.spark.sql.SparkSession

object StructuredStreaming {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder()
      .appName("Structured Streaming")
      .master("local[*]")
      .getOrCreate()

    val frame = spark.readStream
      .format("jdbc")
      .option("url", "jdbc:mysql://localhost:3306/mysql")
      .option("dbtable", "user")
      .option("user", "root")
      .option("password", "root123.00")
      .load()
    frame.show(false)
    spark.stop()
  }
}
