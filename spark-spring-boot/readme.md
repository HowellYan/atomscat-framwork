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
java -Dspring.profiles.active=dev spark-spring-boot-1.0-SNAPSHOT.jar
```