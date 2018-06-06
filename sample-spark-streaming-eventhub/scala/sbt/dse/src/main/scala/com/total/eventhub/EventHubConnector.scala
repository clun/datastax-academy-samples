package com.total.eventhub

import java.time.Duration
import java.util.Date

import com.datastax.spark.connector.{SomeColumns, _}
import com.microsoft.azure.eventhubs.EventData
import org.apache.spark.{SparkConf, TaskContext}
import org.apache.spark.eventhubs._
import org.apache.spark.eventhubs.rdd.{HasOffsetRanges, OffsetRange}
import org.apache.spark.rdd.RDD
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
  * Spark Streaming.
  * Reading events from Azure EventHUB as EventData and save into DSECassandra.
  */
object EventHubConnector extends App {

  // ---------------------------------------------
  // SETUP
  // ---------------------------------------------

  println("Starting 'EventHubConnector'...")

  // Create a StreamingContext
  val sparkConf = new SparkConf(true)
    .setAppName("Total Data Collector")
    .set("spark.cassandra.connection.host", "10.0.0.5,10.0.0.6,10.0.0.7")
    .set("spark.cassandra.auth.username", "*********")
    .set("spark.cassandra.auth.password", "*********")
  println(" + Spark setup and connected to DSE")

  // Create a DStream from the StreamingContext.
  val connectionUrl : String = "" +
    "Endpoint=********" +
    "SharedAccessKeyName=***********" +
    "SharedAccessKey=****************" +
    "EntityPath=********"

 val connectionString = ConnectionStringBuilder()
    .setNamespaceName("poc-datastax-eventhub")
    .setEventHubName("poc-sparkstreaming")
    .setSasKeyName("your-sas-Key-name")
    .setSasKey("your-sas-Key-value=")
    .build
  val eventHubConfig : EventHubsConf = EventHubsConf(connectionString)
      //.setConsumerGroup("datastax")
      .setConsumerGroup("$Default")
      .setMaxEventsPerTrigger(100)
      .setReceiverTimeout(Duration.ofSeconds(5))
      .setStartingPosition(EventPosition.fromEndOfStream)
  println(" + Configuration EventHub setup")

  // Creating DStream
  val streamCtx = new StreamingContext(sparkConf, Seconds(1))
  val stream    = EventHubsUtils.createDirectStream(streamCtx, eventHubConfig)
  println(" + Spark setup and connected to EventHub")
  println(" + EventHubConnector' started.")

  // ---------------------------------------------
  // PROCESSING
  // ---------------------------------------------

  def mapRddAndSaveToCassandra(eventData: EventData) : Unit = {
    println("Processing Event Data: " + eventData)
    // Mapping from Event Data
    // Well if only i got some result of course
    // ---
    saveRowToCassandra("spark-SampleDevice", 123)
  }

  def saveRowToCassandra(tag: String, value: Float) {
    streamCtx.sparkContext
      .parallelize(Seq(("spark-" + tag, new Date(), value)))
      .saveToCassandra("trendminer", "tags", SomeColumns("tag", "ts", "value"))
  }

  var offsetRanges = Array[OffsetRange]()
  stream.foreachRDD { rdd =>

    println(rdd.toDebugString)
    rdd.map(mapRddAndSaveToCassandra)

    // OK, this one is working
    // saveRowToCassandra("coup", 456)
    offsetRanges = rdd.asInstanceOf[HasOffsetRanges].offsetRanges
  }

  // Start
  streamCtx.start()
  streamCtx.awaitTermination()
}
