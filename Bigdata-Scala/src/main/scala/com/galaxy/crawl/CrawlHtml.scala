package com.galaxy.crawl

import scala.io.Source

object CrawlHtml {
  def main(args: Array[String]): Unit = {
    val source = Source.fromFile("I:\\StudyProject\\Bigdata-Hadoop\\src\\main\\resources\\html\\360mian.html")
    source.getLines().filter(_.contains("http")).map(line => {
      val length  = line.length
      val start = line.indexOf("http")
      line.substring(start, length)
    }).filter(_.contains("\'")).map(line => {
      line.substring(0, line.indexOf("\'"))
    }).foreach(println)
  }
}
