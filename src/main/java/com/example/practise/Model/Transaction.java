package com.example.practise.Model;

import java.util.HashMap;
import java.util.Map;

public class Transaction {
    public Transaction(){

    }

    public Map<Integer, Map<Integer, Integer> >  handleTransactionDonorToGetterDonorAmntGreater(User topGetter, User topDonor, Map<Integer, Map<Integer, Integer> > oweAmnt){
        Map<Integer, Integer> oweArr = oweAmnt.get(topDonor.getUserId())==null? new HashMap<>():oweAmnt.get(topDonor.getUserId());
        oweArr.put(topGetter.getUserId(), (Integer)((-topGetter.getOweAmount()))); 
        oweAmnt.put(topDonor.getUserId(), oweArr);
        return oweAmnt;
    }

    public Map<Integer, Map<Integer, Integer> >  handleTransactionGetterToDonorDonorAmntGreater(User topGetter, User topDonor, Map<Integer, Map<Integer, Integer> > oweAmnt){
        Map<Integer, Integer> oweArr1 = oweAmnt.get(topGetter.getUserId())==null? new HashMap<>():oweAmnt.get(topGetter.getUserId());
        oweArr1.put(topDonor.getUserId(), (Integer)((topGetter.getOweAmount()))); 
        oweAmnt.put(topGetter.getUserId(), oweArr1);
        return oweAmnt;
    }

    public Map<Integer, Map<Integer, Integer> >  handleTransactionGetterToDonorGetterAmntGreater(User topGetter, User topDonor, Map<Integer, Map<Integer, Integer> > oweAmnt){
        Map<Integer, Integer> oweArr = oweAmnt.get(topGetter.getUserId())==null? new HashMap<>():oweAmnt.get(topGetter.getUserId());
        oweArr.put(topDonor.getUserId(), (Integer)(-1*(topDonor.getOweAmount()))); 
        oweAmnt.put(topGetter.getUserId(), oweArr);
        return oweAmnt;
    }

    public Map<Integer, Map<Integer, Integer> >  handleTransactionDonorToGetterGetterAmntGreater(User topGetter, User topDonor, Map<Integer, Map<Integer, Integer> > oweAmnt){
        Map<Integer, Integer> oweArr1 = oweAmnt.get(topDonor.getUserId())==null? new HashMap<>():oweAmnt.get(topDonor.getUserId());
        oweArr1.put(topGetter.getUserId(), (Integer)((topDonor.getOweAmount()))); 
        oweAmnt.put(topDonor.getUserId(), oweArr1);
        return oweAmnt;
    }
}
