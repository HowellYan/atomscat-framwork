


spark-submit  --master  spark://master:7077 --class com.atomscat.streaming.GetWordCountByKafka \
 --conf spark.driver.extraLibraryPath=/tools/git/atomscat-framwork/spark-task/target/spark-task-1.0-SNAPSHOT-dist
 /tools/git/atomscat-framwork/spark-task/target/spark-task-1.0-SNAPSHOT.jar