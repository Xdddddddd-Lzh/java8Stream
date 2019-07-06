package com.liang.javasteam.test;

import com.liang.javasteam.entity.Trader;
import com.liang.javasteam.entity.Transaction;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MyTransactionTest {
    public static void main(String[] args) {
        Trader raoul = new Trader("Raoul", "Cambridge");
        Trader mario = new Trader("Mario", "Milan");
        Trader alan = new Trader("Alan", "Cambridge");
        Trader brian = new Trader("Brian", "Cambridge");

        List<Transaction> transactions = Arrays.asList(
                new Transaction(brian, 2011, 300),
                new Transaction(raoul, 2012, 1000),
                new Transaction(raoul, 2011, 400),
                new Transaction(mario, 2012, 710),
                new Transaction(mario, 2012, 700),
                new Transaction(alan, 2012, 950)
        );

        //找出2011年发生的所有交易，并按交易额顺序(从低到高)
        List<Transaction> collect = transactions.stream().filter((Transaction t) -> t.getYear() == 2011).sorted(Comparator.comparing(Transaction::getValue)).collect(Collectors.toList());
        System.out.println("所有交易额：" + collect.toString());

        //交易员都在哪些城市工作过?
        List<String> collect1 = transactions.stream().map(Transaction::getTrader).map(Trader::getCity).distinct().collect(Collectors.toList());
        for (String s : collect1) {
            System.out.println("城市有：:" + s);
        }

        //查找所有来自剑桥的交易员，并按姓名排序
        List<Trader> collect2 = transactions.stream().map(Transaction::getTrader).filter((Trader t) -> "Cambridge".equals(t.getCity())).distinct().sorted(Comparator.comparing(Trader::getName)).collect(Collectors.toList());
        System.out.println("所有来自剑桥的交易员：" + collect2.toString());

        //返回所有交易员的姓名字符串，按字母顺序排序
        String reduce = transactions.stream().map((Transaction t) -> t.getTrader().getName()).distinct().sorted().reduce("", (String a, String b) -> a + b);
        System.out.println("拼接的名字：" + reduce);
//        List<String> collect3 = transactions.stream().map((Transaction t) -> t.getTrader().getName()).distinct().sorted().collect(Collectors.toList());
//        for (String s : collect3) {
//            System.out.println("按字母排序：" + s);
//        }

        //有没有交易员是在米兰工作的
        boolean b = transactions.stream().map(Transaction::getTrader).anyMatch((Trader t) -> "Milan".equals(t.getCity()));
        System.out.println("有没有交易员在米兰工作：" + b);

        //打印生活在剑桥的交易员的所有交易额
        Integer reduce1 = transactions.stream().filter((Transaction t) -> "Cambridge".equals(t.getTrader().getCity())).map(Transaction::getValue).reduce(0, (Integer a, Integer c) -> a + c);
        System.out.println("打印生活在剑桥的交易员的所有交易额:" + reduce1);

        //所有交易中，最高的交易额是多少
        Optional<Integer> reduce2 = transactions.stream().map(Transaction::getValue).reduce(Integer::max);
        System.out.println("所有交易中，最高的交易额是:" + reduce2.get());

        //找到交易额最小的交易
        Optional<Transaction> reduce3 = transactions.stream().reduce((Transaction t1, Transaction t2) -> t1.getValue() < t2.getValue() ? t1 : t2);
        System.out.println("找到交易额最小的交易:"+reduce3.get().toString());
    }
}
