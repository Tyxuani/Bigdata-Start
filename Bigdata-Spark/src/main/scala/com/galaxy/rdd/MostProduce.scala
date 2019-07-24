package com.galaxy.rdd

import org.apache.spark.{SparkConf, SparkContext}

object MostProduce {
  def main(args: Array[String]): Unit ={
    val conf = new SparkConf().setAppName("MostProdece").setMaster("local[*]")
    val sc = new SparkContext(conf)

    val lines = sc.textFile("./src/main/resources/person.txt")
    val result = lines.map(line => {
      val record = line.split(',')
      val id = record(0).toLong
      val name = record(1)
      val age = record(2).toInt
      val produce = record(3).toLong
      (id, name, age, produce)
    }).sortBy(person => Productiveness(person._3, person._4)).collect()

    sc.stop()
    println(result.toBuffer)

  }
}

case class Productiveness(age: Int, produce: Long) extends Comparable[Productiveness]{
  override def compareTo(o: Productiveness): Int = {
    if(this.produce == o.produce){
      this.age - o.age
    } else {
      (o.produce - this.produce).toInt
    }
  }
}
