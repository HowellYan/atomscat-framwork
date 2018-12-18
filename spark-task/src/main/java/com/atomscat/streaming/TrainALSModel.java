package com.atomscat.streaming;

import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.mllib.recommendation.ALS;
import org.apache.spark.mllib.recommendation.MatrixFactorizationModel;
import org.apache.spark.mllib.recommendation.Rating;
import scala.Tuple2;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.logging.Logger;


public class TrainALSModel {
    private final static org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(TrainALSModel.class);

    public static void train(JavaSparkContext sc, JavaPairRDD<String, Integer> rdd, Map<String, String> userMap) {
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
        int bestNumIter = 3;
        /**
         *    * @param ratings    RDD of [[Rating]] objects with userID, productID, and rating
         *    * @param rank       number of features to use (also referred to as the number of latent factors):潜在因素
         *    * @param iterations number of iterations of ALS:迭代次数 1000
         *    * @param lambda     regularization parameter:正则项系数
         */
        MatrixFactorizationModel bestModel = ALS.train(ratings.rdd(), bestRank, bestNumIter, bestLamba);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSSZ");
        String time = simpleDateFormat.format(new Date());
        userMap.forEach(new BiConsumer<String, String>() {
            @Override
            public void accept(String s, String s2) {
                log.info(s+","+s2);
            }
        });


        bestModel.save(sc.sc(), "hdfs://slaves1:9000/model/all_" + time);
        //提取推荐的用户列表
//        /**
//         *  @param user the user to recommend products to
//         *  @param num how many products to return.
//         */
//        int userID = 2096876;
//        Rating[] ratingsList = bestModel.recommendProducts(userID, 3);
//        sc.parallelize(Arrays.asList(ratingsList)).saveAsTextFile("hdfs://127.0.0.1:9000/recommend/"+userID+"_"+new Date().getTime());
    }


    public static void train(JavaPairRDD<String, Integer> rdd, String key, JavaSparkContext sc, Map<String, String> userMap) {
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
        int bestNumIter = 3;
        /**
         *    * @param ratings    RDD of [[Rating]] objects with userID, productID, and rating
         *    * @param rank       number of features to use (also referred to as the number of latent factors):潜在因素
         *    * @param iterations number of iterations of ALS:迭代次数 1000
         *    * @param lambda     regularization parameter:正则项系数
         */
        MatrixFactorizationModel bestModel = ALS.train(ratings.rdd(), bestRank, bestNumIter, bestLamba);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSSZ");
        String time = simpleDateFormat.format(new Date());
        userMap.forEach(new BiConsumer<String, String>() {
            @Override
            public void accept(String s, String s2) {
                log.info(s+","+s2);
            }
        });

        bestModel.save(sc.sc(), "hdfs://slaves1:9000/model/goodsCategory_" + key + "_" + time);

        // MatrixFactorizationModel bestModel2 = MatrixFactorizationModel.load(sc.sc(), "hdfs://slaves1:9000/model/goodsCategory_" + key + "_*");
        //提取推荐的用户列表
//        /**
//         *  @param user the user to recommend products to
//         *  @param num how many products to return.
//         */
//        int userID = 2096876;
//        Rating[] ratingsList = bestModel2.recommendProducts(userID, 3);
//        sc.parallelize(Arrays.asList(ratingsList)).saveAsTextFile("hdfs://127.0.0.1:9000/recommend/" + userID + "_" + key + "_" + new Date().getTime());
    }

}
