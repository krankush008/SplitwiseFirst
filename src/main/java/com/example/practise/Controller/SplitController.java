package com.example.practise.Controller;

import java.util.ArrayList;
import java.util.Map;
import com.example.practise.Service.Splitwise;

public class SplitController {

    private Splitwise splitwise;

    public SplitController(){
        splitwise = new Splitwise();
    }
    
    public void getSplitByTime(Integer time){
        splitwise.getSplitByTime(time);
    }

    public void getFinalTransaction(){
        splitwise.getFinalTransaction();
    }

    public void split(ArrayList<Integer> allPeople, Map<Integer, Integer> donorAmount){
        splitwise.split(allPeople, donorAmount);
    }
}
