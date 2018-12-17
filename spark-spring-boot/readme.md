/actuator


```
定义上下文后，您必须执行以下操作：

通过创建输入DStreams定义输入源
通过对DStreams应用转换操作（transformation）和输出操作（output）来定义流计算
可以使用streamingContext.start()方法接收和处理数据
可以使用streamingContext.awaitTermination()方法等待流计算完成（手动或由于任何错误），来防止应用退出
可以使用streamingContext.stop（）手动停止处理。

注意点:
一旦上下文已经开始，则不能设置或添加新的流计算。
上下文停止后，无法重新启动。
在同一时间只有一个StreamingContext可以在JVM中处于活动状态。
在StreamingContext上调用stop()方法，也会关闭SparkContext对象。如果只想关闭StreamingContext对象，设置stop()的可选参数为false。
一个SparkContext可以重复利用创建多个StreamingContext，只要在创建下一个StreamingContext之前停止前一个StreamingContext（而不停止SparkContext）即可。
```

```
java -Dspring.profiles.active=dev -jar spark-spring-boot-1.0-SNAPSHOT.jar
java -Dspring.profiles.active=prod -jar spark-spring-boot-1.0-SNAPSHOT.jar
```
```
master:
local:  本地以一个worker线程运行(例如非并行的情况).
local[K]:  本地以K worker 线程 (理想情况下, K设置为你机器的CPU核数).
local[*]:  本地以本机同样核数的线程运行.
spark://HOST:PORT:  连接到指定的Spark standalone cluster master. 端口是你的master集群配置的端口，缺省值为7077.
mesos://HOST:PORT:  连接到指定的Mesos 集群. Port是你配置的mesos端口， 缺省是5050. 或者如果Mesos使用ZOoKeeper,格式为 mesos://zk://....
yarn-client:  以client模式连接到YARN cluster. 集群的位置基于HADOOP_CONF_DIR 变量找到.
yarn-cluster:  以cluster模式连接到YARN cluster. 集群的位置基于HADOOP_CONF_DIR 变量找到.
```