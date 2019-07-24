package com.galaxy.rdd

import java.net.URL

import org.apache.spark.{SparkConf, SparkContext}

object FavorateTeacher {
  def main(args: Array[String]): Unit ={
    val conf = new SparkConf().setAppName("FavorateTeacher").setMaster("local[*]")
    val sc = new SparkContext(conf)

    val lines = sc.textFile("src/main/resources/favTeacher.txt")
    val favorateT = lines.map(line => {
      val url = new URL(line)
      val host = url.getHost
      val name = url.getPath
      val subject = host.substring(0, host.indexOf("."))
      val teacher = name.substring(1)
      (subject, teacher)
    })
    val firstStep = favorateT.map((_, 1)).reduceByKey(_+_).collect
    firstStep.foreach(println)
    sc.stop()
  }
}

