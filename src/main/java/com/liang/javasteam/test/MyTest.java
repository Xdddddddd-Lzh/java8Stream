package com.liang.javasteam.test;

import com.liang.javasteam.entity.Dish;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class MyTest {

    public static void main(String[] args) {
        List<Dish> menu = Arrays.asList(
                new Dish("pork", false, 800, Dish.Type.MEAT),
                new Dish("beef", false, 700, Dish.Type.MEAT),
                new Dish("chicken", false, 400, Dish.Type.MEAT),
                new Dish("french fries", true, 530, Dish.Type.OTHER),
                new Dish("rice", true, 350, Dish.Type.OTHER),
                new Dish("season fruit", true, 120, Dish.Type.OTHER),
                new Dish("pizza", true, 550, Dish.Type.OTHER),
                new Dish("prawns", false, 300, Dish.Type.FISH),
                new Dish("salmon", false, 450, Dish.Type.FISH)
        );


//   ----------------------------------【小试牛刀】--------------------------------------
//      long count = menu.stream().filter((Dish d)->!d.isVegetarian()).count();
        System.out.println("----------------------------------【小试牛刀】--------------------------------------");
        //小试牛刀1：使用count() 终端操作
        long count = menu.stream().filter(Dish::isVegetarian).count();
        System.out.println("小试牛刀1：数量：" + count);

        //小试牛刀2：使用collect
        List<Dish> suDishList = menu.stream().filter(Dish::isVegetarian).collect(Collectors.toList());
        System.out.println("小试牛刀2：使用collect,收集所有素菜：" + suDishList);

//   ----------------------------------【筛选、切片】--------------------------------------
        System.out.println("----------------------------------【筛选、切片】--------------------------------------");
        //使用谓词筛选 filter  筛选卡路里小于500的菜
        List<Dish> collect = menu.stream().filter((Dish d) -> d.getCalories() < 500).collect(Collectors.toList());
        System.out.println("使用谓词筛选filter，筛选卡路里小于500的菜：" + collect.toString());

        //使用谓词去重 distinct，筛选是否为菜的类型【MEAT,FISH,OTHER】
        long count1 = menu.stream().map(Dish::getType).distinct().count();
        System.out.println("使用谓词去重distinct,筛选出有多少种类型的菜：" + count1);

        //使用谓词截短流 limit()  截短前3个素菜
        List<Dish> collect1 = menu.stream().filter(Dish::isVegetarian).limit(3).collect(Collectors.toList());
        System.out.println("使用谓词截短流limit,截短出前3个元素：" + collect1.toString());

        //使用谓词跳过元素skip() 跳过前2个非素菜
//        Set<Dish> collect2 = menu.stream().filter((Dish d) -> !d.isVegetarian()).skip(2).collect(Collectors.toSet());
        List<Dish> collect2 = menu.stream().filter((Dish d) -> !d.isVegetarian()).skip(2).collect(Collectors.toList());
        System.out.println("使用谓词跳过元素skip(),跳过前2个非素菜：" + collect2.toString());


//    ----------------------------------【映射】-----------------------------------------
        System.out.println("----------------------------------【映射】--------------------------------------");
        //使用map映射
        List<String> collect3 = menu.stream().map(Dish::getName).collect(Collectors.toList());
        System.out.println("使用map映射，映射成菜的名字序列：" + collect3.toString());

        //若不使用扁平化流 flatmap
        List<String> words = Arrays.asList("hello", "world");
        List<String[]> collect4 = words.stream().map((String s) -> s.split("")).distinct().collect(Collectors.toList());
        for (String[] strings : collect4) {
            for (int i = 0; i < strings.length; i++) {
                System.out.print("数组：" + strings[i]);
            }
            System.out.println("");
        }

        //使用扁平化流 flatmap   往后研究
//        words.stream().flatMap((String s)->s.split("")).distinct()

        //使用谓词查找  是否能至少匹配一个元素 返回boolean类型
        boolean isAnyVegetarian = menu.stream().anyMatch(Dish::isVegetarian);
        System.out.println("查看集合中是否有 素菜，结果是：" + isAnyVegetarian);

        //使用谓词查找 是否能匹配所有元素  返回boolean类型
        boolean isAllVegetarian = menu.stream().allMatch(Dish::isVegetarian);
        System.out.println("查看集合中是否 所有都是素菜，结果是：" + isAllVegetarian);

        //使用谓词查找 是否全部多不匹配  返回boolean
        boolean isAllNotVegetarian = menu.stream().noneMatch(Dish::isVegetarian);
        System.out.println("查看集合中 是否所有都不是素菜，结果是：" + isAllNotVegetarian);

        //使用谓词 查找元素，返回当前流中任意元素
        Optional<Dish> anyDish = menu.stream().filter((Dish d) -> d.getCalories() > 100000).findAny();
        System.out.println("查看是否存在元素，若存在则为true,若不存在则为false，结果是：" + anyDish.isPresent());

        //查找第一个元素
        Optional<Dish> first = menu.stream().filter((Dish d) -> d.getCalories() < 500).findFirst();
        System.out.println("查找第一个元素,一个低于500卡路里的菜是:" + first.get().getName());

        //----------------------------------【归约】-----------------------------------------
        System.out.println("----------------------------------【归约】-----------------------------------------");
        //元素求和  有初始值
        Integer reduce = menu.stream().map(Dish::getCalories).reduce(0, (Integer a, Integer b) -> a + b);
        System.out.println("使用元素求和，将所有菜的卡路里求和（有初始值），结果是：" + reduce);

        //元素求和 无初始值
        Optional<Integer> reduce1 = menu.stream().map(Dish::getCalories).reduce((Integer a, Integer b) -> a + b);
        System.out.println("使用元素求和，将所有菜的卡路里求和（无初始值），结果是：" + reduce1.get());

        //求最大值
//        Optional<Integer> reduce2 = menu.stream().map(Dish::getCalories).reduce(Integer::max);
        Optional<Integer> reduce2 = menu.stream().map(Dish::getCalories).reduce((Integer a, Integer b) -> Integer.max(a, b));
        System.out.println("使用求最大值,求出所有菜中卡路里最大的数值：" + reduce2.get());

        //求最小值
        Optional<Integer> reduce3 = menu.stream().map(Dish::getCalories).reduce(Integer::min);
        System.out.println("使用求最小值,求出所有菜中卡路里最小的数值：" + reduce3.get());


    }
}
