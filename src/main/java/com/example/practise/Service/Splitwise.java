package com.example.practise.Service;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.example.practise.Model.Transaction;
import com.example.practise.Model.User;
import com.example.practise.Service.Exceptions.SplitwiseException;

public class Splitwise {

    Map<Integer, Map<Integer, Integer> > oweAmnt = new HashMap<>();
    Map<Integer, Map<Integer, Integer> > finalAmnt = new HashMap<>();
    Map<Integer, Map<Integer, Map<Integer, Integer> >> oweAmnt1 = new HashMap<>();
    int it=0;
    public Map<Integer, Integer> getRemainingAmnt(Map<Integer, Integer> donorAmount, ArrayList<Integer> allPeople) {
        try {
            int s = 0;
            for (int i = 0; i < allPeople.size(); i++) {
                s += donorAmount.get(allPeople.get(i));
            }
    
            int sharePerPerson = s / allPeople.size();
    
            for (int i = 0; i < allPeople.size(); i++) {
                Integer amnt = donorAmount.get(allPeople.get(i));
                donorAmount.put(allPeople.get(i), sharePerPerson - amnt);
            }
    
            return donorAmount;
        } catch (Exception e) {
            // Handle or log the exception
            throw new SplitwiseException("Error while calculating remaining amount: " + e.getMessage(), e);
        }
    }
    
    public Deque<User>  getGetterList(Deque<User> finalGettersD, ArrayList<Integer> allPeople, Map<Integer, Integer> donorAmount){
        for(int i=0;i<allPeople.size(); i++){
            User user = new User(allPeople.get(i), donorAmount.get(allPeople.get(i)));
            if(donorAmount.get(allPeople.get(i))<=0){
                finalGettersD.addLast(user);
            }
        }
        return finalGettersD;
    }
    public Deque<User> getDonorList(Deque<User> finalDonorsD, ArrayList<Integer> allPeople, Map<Integer, Integer> donorAmount){
        for(int i=0;i<allPeople.size(); i++){
            User user = new User(allPeople.get(i), donorAmount.get(allPeople.get(i)));
            if(donorAmount.get(allPeople.get(i))>0){
                finalDonorsD.addLast(user);
            }
        }
        return finalDonorsD;
    }
    
public void printSplit(Map<Integer, Map<Integer, Integer>> oweAmnt) {
        ExecutorService executorService = Executors.newFixedThreadPool(7);

        try {
            List<Callable<Void>> tasks = new ArrayList<>();

            for (Map.Entry<Integer, Map<Integer, Integer>> outerEntry : oweAmnt.entrySet()) {
                final int outerKey = outerEntry.getKey();
                final Map<Integer, Integer> innerMap = outerEntry.getValue();

                // Create a Callable task for each outer loop iteration
                Callable<Void> outerLoopTask = () -> {
                    long threadId = Thread.currentThread().getId();
                    for (Map.Entry<Integer, Integer> innerEntry : innerMap.entrySet()) {
                        int innerKey = innerEntry.getKey();
                        int innerValue = innerEntry.getValue();

                        // Printing the values
                        System.out.println("Thread ID: " + threadId + "Outer Key: " + outerKey + ", Inner Key: " + innerKey + ", Value: " + innerValue);
                    }
                    return null;
                };

                tasks.add(outerLoopTask);
            }

            // Submit all tasks to the executor service
            List<Future<Void>> futures = executorService.invokeAll(tasks);

            // Wait for all tasks to complete
            for (Future<Void> future : futures) {
                future.get();
            }

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }
    }


    public void split(ArrayList<Integer> allPeople, Map<Integer, Integer> donorAmount){
        
        try {
            donorAmount = getRemainingAmnt(donorAmount, allPeople);
            
            Deque<User> finalDonorsD = new ArrayDeque<>();
            Deque<User> finalGettersD = new ArrayDeque<>();
            
            finalDonorsD = getDonorList(finalDonorsD, allPeople, donorAmount);
            finalGettersD = getGetterList(finalGettersD, allPeople, donorAmount);

            while(finalDonorsD.size()!=0){
                User topDonor = finalDonorsD.removeFirst();
                User topGetter = finalGettersD.removeFirst();
                if(topDonor.getOweAmount()>=-topGetter.getOweAmount()){
                    User user = new User(topDonor.getUserId(), topDonor.getOweAmount()-(-topGetter.getOweAmount()));
                    if(user.getOweAmount()!=0){
                        finalDonorsD.addFirst(user);
                    }
                    Transaction transaction = new Transaction();
                    oweAmnt = transaction.handleTransactionGetterToDonorDonorAmntGreater(topGetter, topDonor, oweAmnt);
                    oweAmnt = transaction.handleTransactionDonorToGetterDonorAmntGreater(topGetter, topDonor, oweAmnt);
                }
                else{
                    User user = new User(topGetter.getUserId(), -1*(-topGetter.getOweAmount())+topDonor.getOweAmount());
                    if(user.getOweAmount()!=0){
                        finalGettersD.addFirst(user);
                    }
                    Transaction transaction = new Transaction();
                    oweAmnt = transaction.handleTransactionGetterToDonorGetterAmntGreater(topGetter, topDonor, oweAmnt);
                    oweAmnt = transaction.handleTransactionDonorToGetterGetterAmntGreater(topGetter, topDonor, oweAmnt);
                }
            }
            printSplit(oweAmnt);
            it++;
            oweAmnt1.put(it, oweAmnt);
            handleFinalSplit(oweAmnt);
        } catch (Exception e) {
            throw new SplitwiseException("Error while splitting: " + e.getMessage(), e);
        }
    }

    public void getSplitByTime(Integer time){
        printSplit(oweAmnt1.get(time));
    }

    public void getFinalTransaction(){
        printSplit(finalAmnt);
    }

    public void handleFinalSplit(Map<Integer, Map<Integer, Integer> >  oweAmnt){
        
        for (Map.Entry<Integer, Map<Integer, Integer>> outerEntry : oweAmnt.entrySet()) {
            int outerKey = outerEntry.getKey();
            Map<Integer, Integer> innerMap = outerEntry.getValue();
            Map<Integer, Integer> innerMap1 = finalAmnt.get(outerKey);
            if(innerMap1==null){
                finalAmnt.put(outerKey, innerMap);
                continue;
            }
            // Iterating over the entries in the inner map
            for (Map.Entry<Integer, Integer> innerEntry : innerMap.entrySet()) {
                int innerKey = innerEntry.getKey();
                int innerValue = innerEntry.getValue();

                int innerFinalValue = innerMap1.get(innerKey)!=null?innerMap1.get(innerKey):0; 
                innerMap1.put(innerKey, innerFinalValue+innerValue);
                // Printing the values
            }
            finalAmnt.put(outerKey, innerMap1);
        }
    }


}
