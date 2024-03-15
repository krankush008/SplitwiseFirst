package com.example.practise;

import java.util.ArrayList;

import com.example.practise.Controller.SplitController;
import com.example.practise.Model.Group;

public class Main {
    public static void main(String[] args) {
        SplitController splitController = new SplitController();
        ArrayList<Integer> allPeople = new ArrayList<>();
        Group group = new Group(allPeople);
        allPeople.add(2);
        allPeople.add(3);
        allPeople.add(6);
        allPeople.add(8);
        allPeople.add(9);
        allPeople.add(10);
        group.addAmount(2, 15);
        group.addAmount(3, 7);
        group.addAmount(6, 10);
        group.addAmount(8, 4);
        group.addAmount(9, 0);
        group.addAmount(10, 0);
        splitController.split(group.getAllPeople(), group.getAmountMap());
        splitController.getFinalTransaction();
        splitController.getSplitByTime(1);
    }
}