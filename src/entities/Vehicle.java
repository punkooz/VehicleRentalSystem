package entities;

import java.util.ArrayList;
import java.util.List;

public class Vehicle {

    String vehicleType;
    int cost;
    List<Integer> startTimes;
    List<Integer> endTimes;

    public Vehicle(String vehicleType, int cost) {
        this.vehicleType = vehicleType;
        this.cost = cost;
        this.startTimes = new ArrayList<>();
        this.endTimes = new ArrayList<>();
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public List<Integer> getStartTimes() {
        return startTimes;
    }

    public void setStartTimes(List<Integer> startTimes) {
        this.startTimes = startTimes;
    }

    public List<Integer> getEndTimes() {
        return endTimes;
    }

    public void setEndTimes(List<Integer> endTimes) {
        this.endTimes = endTimes;
    }
}
