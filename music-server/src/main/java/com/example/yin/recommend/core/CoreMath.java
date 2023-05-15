package com.example.yin.recommend.core;

import com.example.yin.recommend.dto.RelateDTO;
import com.google.common.collect.Lists;


import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author root
 * @Description:核心算法
 * @Package com.supermarket.product.core
 * @date 2021/4/22 16:12
 */
public class CoreMath {
    /**
     * 推荐
     * @param userId 用户id
     * @param list 推荐的idList集合
     * @return
     */
    public List<Integer> recommend(Integer userId, List<RelateDTO> list) {
        //找到最近邻用户id
        Map<Double, Integer> distances = computeNearestNeighbor(userId, list);
        //取出相似度最近的用户id
        Integer nearest = distances.values().iterator().next();
        System.out.println("相似度最近用户id为"+nearest);
        Iterator<Integer> iterator = distances.values().iterator();
        while(iterator.hasNext()){
            nearest = iterator.next();
        }
        Map<Integer, List<RelateDTO>> userMap =list.stream().collect(Collectors.groupingBy(RelateDTO::getUserId));

        //最近邻用户收藏的歌曲id列表
        List<Integer> neighborItemList = userMap.get(nearest).stream().map(e->e.getProductId()).collect(Collectors.toList());
        //指定用户收藏的歌曲id列表
        List<Integer> userItemList  = userMap.get(userId).stream().map(e->e.getProductId()).collect(Collectors.toList());

        //找到指定用户收藏歌曲的歌手，推荐该歌手的其他歌曲,待完善

        //找到最近邻收藏过，但是该用户未收藏的歌曲id，计算推荐，放入推荐列表
        List<Integer> recommendList = new ArrayList<>();
        for (Integer item : neighborItemList) {
            if (!userItemList.contains(item)) {
                recommendList.add(item);
            }
        }
        Collections.sort(recommendList);
        return recommendList;
    }

    /**
     * 在给定userId的情况下，计算其他用户和它的相关系数并排序
     * @param userId
     * @param list
     * @return
     */

    /**计算与指定用户最近邻的其他用户以及它们之间的相似度。
     * 方法内部，首先使用Java 8的Stream API对列表进行处理，
     * 将其中所有的RelateDTO对象按照userId属性分组到一个Map<Integer, List<RelateDTO>>数据结构中。然后通过遍历这个Map，
     * 计算每个用户与指定用户之间的相似度并将它们的距离放入一个TreeMap排序，TreeMap按照键（距离）从小到大排列。
     * 具体而言，在遍历时，如果当前的用户ID不等于指定用户ID，则调用pearson_dis()方法计算当前用户和指定用户之间的皮尔逊相关系数，
     * 然后将该相关系数作为key，当前用户ID作为value，放入distances这个TreeMap中。
     * 最后，返回distances作为结果，它是一个Map<Double, Integer>类型，其中Double表示距离值，Integer表示其他最近邻用户的ID。
     */
    private Map<Double, Integer> computeNearestNeighbor(Integer userId, List<RelateDTO> list) {
        Map<Integer, List<RelateDTO>> userMap = list.stream().collect(Collectors.groupingBy(RelateDTO::getUserId));
        //treemap是从小到大排好序的
        Map<Double, Integer> distances = new TreeMap<>();
        userMap.forEach((k,v)->{
            if(k.intValue() != userId.intValue()){
                double distance = pearson_dis(v,userMap.get(userId));
                distances.put(distance, k);
            }
        });
        return distances;
    }
    /**
     * 计算两个序列间的相关系数
     *
     * @param xList 其他用户的数据集
     * @param yList 当前用户的数据集
     * @return
     */
    private double pearson_dis(List<RelateDTO> xList, List<RelateDTO> yList) {
        List<Integer> xs= Lists.newArrayList();
        List<Integer> ys= Lists.newArrayList();
        xList.forEach(x->{
            yList.forEach(y->{
                if(x.getProductId().intValue() == y.getProductId().intValue()){
                    xs.add(x.getIndex());
                    ys.add(y.getIndex());
                }
            });
        });
        return getRelate(xs,ys);
    }

    /**
     * 方法描述: 皮尔森（pearson）相关系数计算
     * (x1,y1) 理解为 a 用户对 x 商品的点击次数和对 y 商品的点击次数
     * @param xs
     * @param ys
     * @Return {@link Double}
     * @throws
     * @author tarzan
     * @date 2020年07月31日 17:03:20
     */

    /**Pearson相关系数衡量了两个变量之间的线性关系强度，其取值范围为-1到+1。
     * 如果两个变量完全正相关，则相关系数为+1；
     * 如果两个变量完全负相关，则相关系数为-1；
     * 如果两个变量之间没有线性关系，则相关系数为0。
     */
    public static Double getRelate(List<Integer> xs, List<Integer> ys){
        int n=xs.size();
        double Ex= xs.stream().mapToDouble(x->x).sum();
        double Ey=ys.stream().mapToDouble(y->y).sum();
        double Ex2=xs.stream().mapToDouble(x-> Math.pow(x,2)).sum();
        double Ey2=ys.stream().mapToDouble(y-> Math.pow(y,2)).sum();
        double Exy= IntStream.range(0,n).mapToDouble(i->xs.get(i)*ys.get(i)).sum();
        double numerator=Exy-Ex*Ey/n;
        double denominator= Math.sqrt((Ex2- Math.pow(Ex,2)/n)*(Ey2- Math.pow(Ey,2)/n));
        if (denominator==0) {
            return 0.0;
        }
        return numerator/denominator;
    }

}
