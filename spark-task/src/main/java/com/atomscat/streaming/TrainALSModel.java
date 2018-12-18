package com.atomscat.streaming;

import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.mllib.recommendation.ALS;
import org.apache.spark.mllib.recommendation.MatrixFactorizationModel;
import org.apache.spark.mllib.recommendation.Rating;
import org.apache.spark.sql.SparkSession;
import scala.Tuple2;

import java.text.SimpleDateFormat;
import java.util.Date;


public class TrainALSModel {
    private final static org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(TrainALSModel.class);

    public static void train(JavaPairRDD<String, Integer> rdd, String user) {
        JavaRDD<Rating> ratings = rdd.map(new Function<Tuple2<String, Integer>, Rating>() {
            @Override
            public Rating call(Tuple2<String, Integer> v1) throws Exception {
                String[] fields = v1._1().replaceAll("\\(", "").replaceAll("\\)", "").split(",");
                Rating rating = new Rating(Integer.parseInt(fields[0]), Integer.parseInt(fields[1]), (double) v1._2());
                return rating;
            }
        });

        int bestRank = 1;
        double bestLamba = 0.0;
        int bestNumIter = 1;
        /**
         *    * @param ratings    RDD of [[Rating]] objects with userID, productID, and rating
         *    * @param rank       number of features to use (also referred to as the number of latent factors):潜在因素
         *    * @param iterations number of iterations of ALS:迭代次数 1000
         *    * @param lambda     regularization parameter:正则项系数
         */
        MatrixFactorizationModel bestModel = ALS.train(ratings.rdd(), bestRank, bestNumIter, bestLamba);
        SendDataToKafka.sendData(bestModel, user, "all");

    }


    public static void train(JavaPairRDD<String, Integer> rdd, String user, String goodsCategory) {
        JavaRDD<Rating> ratings = rdd.map(new Function<Tuple2<String, Integer>, Rating>() {
            @Override
            public Rating call(Tuple2<String, Integer> v1) throws Exception {
                String[] fields = v1._1().split(",");
                Rating rating = new Rating(Integer.parseInt(fields[0]), Integer.parseInt(fields[1]), (double) v1._2());
                return rating;
            }
        });

        int bestRank = 1;
        double bestLamba = 0.0;
        int bestNumIter = 1;
        /**
         *    * @param ratings    RDD of [[Rating]] objects with userID, productID, and rating
         *    * @param rank       number of features to use (also referred to as the number of latent factors):潜在因素
         *    * @param iterations number of iterations of ALS:迭代次数 1000
         *    * @param lambda     regularization parameter:正则项系数
         */
        MatrixFactorizationModel bestModel = ALS.train(ratings.rdd(), bestRank, bestNumIter, bestLamba);
        SendDataToKafka.sendData(bestModel, user, goodsCategory);
    }

}
