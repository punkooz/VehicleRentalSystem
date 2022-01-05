package entities;

import java.util.List;

public class Branch {

    String branchName;
    List<Vehicle> vehicleList;

    public Branch(String branchName, List<Vehicle> vehicleList) {
        this.branchName = branchName;
        this.vehicleList = vehicleList;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public List<Vehicle> getVehicleList() {
        return vehicleList;
    }

    public void setVehicleList(List<Vehicle> vehicleList) {
        this.vehicleList = vehicleList;
    }
}
