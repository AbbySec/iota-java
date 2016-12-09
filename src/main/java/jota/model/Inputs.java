package jota.model;

import com.google.gson.Gson;

import java.util.List;

/**
 * Created by Adrian on 09.12.2016.
 */
public class Inputs {
    private List<Input> inputsList;
    private long totalBalance;

    public Inputs(List<Input> inputsList, long totalBalance) {
        this.inputsList = inputsList;
        this.totalBalance = totalBalance;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    public List<Input> getInputsList() {
        return inputsList;
    }

    public void setInputsList(List<Input> inputsList) {
        inputsList = inputsList;
    }

    public long getTotalBalance() {
        return totalBalance;
    }

    public void setTotalBalance(long totalBalance) {
        totalBalance = totalBalance;
    }
}
