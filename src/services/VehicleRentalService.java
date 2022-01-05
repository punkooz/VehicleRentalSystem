package services;

import dtos.SlotDTO;
import dtos.VehicleDTO;
import entities.Branch;
import entities.Vehicle;
import repos.BranchRepo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VehicleRentalService {

    BranchRepo branchRepo;

    public VehicleRentalService(BranchRepo branchRepo) {
        this.branchRepo = branchRepo;
    }

    public void rentVehicle(String vehicleName, SlotDTO rentSlot1, String strategy) {

        HashMap<String, Branch> branchHashMap = branchRepo.getBranchHashMap();

        int[] timings = findTimings(rentSlot1);

        int rentStartTime = timings[0];
        int rentEndTime = timings[1];

        boolean notFound = true;

        Branch selectedBranch = null;
        Vehicle selectedVehicle = null;
        int minCost = Integer.MAX_VALUE;
        int selectedIndex = 0;

        for(Map.Entry<String, Branch> entry : branchHashMap.entrySet()){

            Branch branch = entry.getValue();

            List<Vehicle> vehicleList = branch.getVehicleList();

            for(int i = 0; i < vehicleList.size(); i++){

                Vehicle vehicle = vehicleList.get(i);

                if(vehicle.getVehicleType().equals(vehicleName)){
                    List<Integer> startTimes = vehicle.getStartTimes();
                    List<Integer> endTimes = vehicle.getEndTimes();

                    boolean skip = false;

                    for(Integer t : startTimes){
                        if (rentEndTime >= t){
                            skip = true;
                            break;
                        }
                    }

                    for(Integer t : endTimes){
                        if (rentStartTime <= t){
                            skip = true;
                            break;
                        }
                    }

                    if (skip)
                        continue;
                    else
                        notFound = false;

                    switch (strategy){
                        case "lowCost":
                            if(vehicle.getCost() < minCost){
                                minCost = vehicle.getCost();
                                selectedVehicle = vehicle;
                                selectedBranch = branch;
                                selectedIndex = i;
                            }
                            break;
                        default:
                            throw new IllegalStateException("Unexpected strategy: " + strategy);
                    }

                }
            }

        }

        if (notFound) {
            System.out.println("no availability");
        }else{
            List<Integer> startTimes = selectedVehicle.getStartTimes();
            List<Integer> endTimes = selectedVehicle.getEndTimes();

            startTimes.add(rentStartTime);
            endTimes.add(rentEndTime);

            selectedVehicle.setStartTimes(startTimes);
            selectedVehicle.setEndTimes(endTimes);

            List<Vehicle> vehicleList = selectedBranch.getVehicleList();

            vehicleList.set(selectedIndex, selectedVehicle);

            selectedBranch.setVehicleList(vehicleList);

            branchRepo.saveBranch(selectedBranch);

            System.out.println("booked from " + selectedBranch.getBranchName());
        }


    }

    private int[] findTimings(SlotDTO rentSlot1) {

        //"10:00 PM"
        String restStart = rentSlot1.getStartTime();
        String restEnd = rentSlot1.getEndTime();

        String[] start = restStart.split("\\s");
        String[] end = restEnd.split("\\s");

        String[] startTime = start[0].split(":");
        String[] endTime = start[0].split(":");

        int rentStartTime = 0;
        int rentEndTime = 0;

        if(start[1].equals("PM")){

            int startTimeHrs = (Integer.parseInt(startTime[0]) + 12) *60;
            int startTimeMins = Integer.parseInt(startTime[1]) * 60;

            rentStartTime = startTimeHrs + startTimeMins;

        }else{

            int startTimeHrs = (Integer.parseInt(startTime[0])) *60;
            int startTimeMins = Integer.parseInt(startTime[1]) * 60;

            rentStartTime = startTimeHrs + startTimeMins;

        }

        if(end[1].equals("PM")){

            int startTimeHrs = (Integer.parseInt(endTime[0]) + 12) *60;
            int startTimeMins = Integer.parseInt(endTime[1]) * 60;

            rentEndTime = startTimeHrs + startTimeMins;

        }else{

            int startTimeHrs = (Integer.parseInt(endTime[0])) *60;
            int startTimeMins = Integer.parseInt(endTime[1]) * 60;

            rentEndTime = startTimeHrs + startTimeMins;

        }

        return new int[]{rentStartTime, rentEndTime};
    }


    public void addBranch(String branchName, List<VehicleDTO> vehicleDTOList) {

        List<Vehicle> vehicleList = new ArrayList<>();

        for(VehicleDTO vehicleDTO : vehicleDTOList){
            int quantity = vehicleDTO.getQuantity();

            while(quantity > 0){

                Vehicle vehicle = new Vehicle(vehicleDTO.getName(), vehicleDTO.getCost());

                vehicleList.add(vehicle);

                quantity--;
            }
        }

        Branch branch = new Branch(branchName, vehicleList);

        branchRepo.saveBranch(branch);

        System.out.println("Added " + branchName);

    }

    public void addVehicle(String branchName, int addQuantity, String vehicleName) {

        try {
            Branch branch = branchRepo.getBranchByName(branchName);

            if(branch == null){
                throw new IllegalArgumentException(branchName + " does not exist");
            }

            Vehicle vehicle = null;

            List<Vehicle> vehicleList = branch.getVehicleList();

            if(vehicleList == null || vehicleList.size() == 0){
                throw new IllegalArgumentException(vehicleName + " does not exist in " + branchName);
            }

            for(Vehicle temp : vehicleList){
                if(temp.getVehicleType().equals(vehicleName)){
                    vehicle = temp;
                    break;
                }
            }

            while (addQuantity > 0){

                vehicleList.add(vehicle);

                addQuantity--;
            }

            branch.setVehicleList(vehicleList);

            branchRepo.saveBranch(branch);

            System.out.println("Added " + vehicleName + " in  " + branchName);

        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

    public void showVehicles(String branchName, SlotDTO avaiSlot) {


        Branch b = branchRepo.getBranchByName(branchName);

        List<Vehicle> vehicleList = b.getVehicleList();

        int[] timings = findTimings(avaiSlot);

        int startSlot = timings[0];
        int endSlot = timings[1];

        HashMap<String, Integer[]> vehicleHashMap = new HashMap<>();

        for(Vehicle vehicle : vehicleList){
            List<Integer> startTimes = vehicle.getStartTimes();
            List<Integer> endTimes = vehicle.getEndTimes();

            if (startTimes == null || endTimes == null){

                if(vehicleHashMap.containsKey(vehicle.getVehicleType())){
                    int freq = vehicleHashMap.get(vehicle.getVehicleType())[0];
                    Integer[] temp = new Integer[]{freq + 1, vehicle.getCost()};
                    vehicleHashMap.put(vehicle.getVehicleType(), temp);
                }else{
                    Integer[] temp = new Integer[]{1, vehicle.getCost()};
                    vehicleHashMap.put(vehicle.getVehicleType(), temp);
                }

                continue;
            }

            boolean consider = true;

            for(Integer t : startTimes){
                if (endSlot >= t){
                    consider = false;
                    break;
                }
            }

            for(Integer t : endTimes){
                if (startSlot <= t){
                    consider = false;
                    break;
                }
            }

            if (consider){
                if(vehicleHashMap.containsKey(vehicle.getVehicleType())){
                    int freq = vehicleHashMap.get(vehicle.getVehicleType())[0];
                    Integer[] temp = new Integer[]{freq + 1, vehicle.getCost()};
                    vehicleHashMap.put(vehicle.getVehicleType(), temp);
                }else{
                    Integer[] temp = new Integer[]{1, vehicle.getCost()};
                    vehicleHashMap.put(vehicle.getVehicleType(), temp);
                }
            }

        }

        for(Map.Entry<String, Integer[]> entry : vehicleHashMap.entrySet()){
            System.out.println(entry.getValue()[0] + " " + entry.getKey() + " available for Rs " + entry.getValue()[1]);
        }

    }

    public void printSystem(SlotDTO avaiSlot) {

        HashMap<String, Branch> branchHashMap = branchRepo.getBranchHashMap();

        System.out.println();

        for(Map.Entry<String, Branch> entry : branchHashMap.entrySet()){

            Branch branch = entry.getValue();

            System.out.println(branch.getBranchName());

            showVehicles(branch.getBranchName(), avaiSlot);

            System.out.println();

        }

    }
}
