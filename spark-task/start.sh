git pull && mvn clean install
exec spark-submit --master spark://master:7077 --class com.atomscat.streaming.GetWordCountByKafka ./target/spark-task-1.0-SNAPSHOT.jar  >./applog.log&