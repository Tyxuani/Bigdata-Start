package com.galaxy.rdd

import org.apache.spark.sql.SQLContext
import org.apache.spark.{SparkConf, SparkContext}

object MostProduceSQL {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("MostProdece").setMaster("local[*]")
    val sc = new SparkContext(conf)

    val lines = sc.textFile("./src/main/resources/person.txt")
    val persons = lines.map(line => {
      val record = line.split(',')
      val id = record(0).toLong
      val name = record(1)
      val age = record(2).toInt
      val produce = record(3).toLong
      Person(id, name, age, produce)
    })

    val sqlContext = new SQLContext(sc)
    import sqlContext.implicits._
    persons.toDF.registerTempTable("t_person")

    new SQLContext(sc).sql("SELECT * FROM t_person order by produce desc, age asc").show()
    sc.stop()
  }
}

case class Person(id: Long, name: String, age: Int, produce: Long)