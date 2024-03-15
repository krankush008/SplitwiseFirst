package com.example.practise.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Group {
    private ArrayList<Integer> ar;
    private Map<Integer, Integer> amountMap;

    public Group(ArrayList<Integer> ar){
        this.ar=ar;
        this.amountMap=new HashMap<>();
    }

    public void addAmount(Integer donor, Integer amount){
        this.amountMap.put(donor, amount);
    }

    public Map<Integer, Integer> getAmountMap(){
        return this.amountMap;
    }

    public ArrayList<Integer> getAllPeople(){
        return this.ar;
    }
}
