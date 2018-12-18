


spark-submit  --master  spark://master:7077 --class com.atomscat.streaming.GetWordCountByKafka \
 --conf spark.driver.extraLibraryPath=/tools/git/atomscat-framwork/spark-task/target/spark-task-1.0-SNAPSHOT-dist \
 /tools/git/atomscat-framwork/spark-task/target/spark-task-1.0-SNAPSHOT.jar
 
 
spark-submit  --master  spark://master:7077 --class com.atomscat.streaming.GetWordCountByKafka \
--driver-class-path=/tools/spark-task-1.0-SNAPSHOT-dist/spark-streaming-kafka-0-10_2.12-2.4.0.jar \
/tools/git/atomscat-framwork/spark-task/target/spark-task-1.0-SNAPSHOT.jar
  
  
 spark-submit --jars=file:/usr/spark-1.5.1-bin-hadoop2.6/lib/aws-java-sdk-1.7.14.jar,file:/usr/spark-1.5.1-bin-hadoop2.6/lib/hadoop-aws-2.6.0.jar,file:/usr/spark-1.5.1-bin-hadoop2.6/lib/google-collections-1.0.jar,file:/usr/spark-1.5.1-bin-hadoop2.6/lib/joda-time-2.8.2.jar


spark-submit --master spark://master:7077 --class com.atomscat.streaming.GetWordCountByKafka \
--driver-class-path=/tools/spark-task-1.0-SNAPSHOT-dist/spark-streaming-kafka-0-10_2.11-2.4.0.jar:/tools/spark-task-1.0-SNAPSHOT-dist/lib/kafka-clients-2.0.1.jar \
./spark-task/target/spark-task-1.0-SNAPSHOT.jar


spark-submit --master spark://master:7077 --class com.atomscat.streaming.GetWordCountByKafka ./spark-task/target/spark-task-1.0-SNAPSHOT.jar


spark-submit --master spark://master:7077 --class com.atomscat.streaming.GetWordCountByKafka ./spark-task-1.0-SNAPSHOT.jar

cp /tools/spark-task-1.0-SNAPSHOT-dist/spark-streaming-kafka-0-10_2.11-2.4.0.jar ./
cp /tools/spark-task-1.0-SNAPSHOT-dist/lib/kafka-clients-2.0.1.jar ./