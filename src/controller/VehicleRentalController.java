package controller;

import dtos.SlotDTO;
import dtos.VehicleDTO;
import repos.BranchRepo;
import services.VehicleRentalService;

import java.util.List;

public class VehicleRentalController {

    BranchRepo branchRepo = new BranchRepo();

    VehicleRentalService vehicleRentalService = new VehicleRentalService(branchRepo);

    public void add_branch(String gachibowli, List<VehicleDTO> vehicleDTOList) {

        vehicleRentalService.addBranch(gachibowli, vehicleDTOList);

    }

    public void add_vehicle(String gachibowli, int addQuantity, String vehicleName) {

        vehicleRentalService.addVehicle(gachibowli, addQuantity, vehicleName);

    }

    public void rent_vehicle(String vehicleName, SlotDTO rentSlot1, String strategy) {

        vehicleRentalService.rentVehicle(vehicleName, rentSlot1, strategy);

    }

    public void getAvailable(String branchName, SlotDTO avaiSlot) {

        vehicleRentalService.showVehicles(branchName, avaiSlot);

    }

    public void printSystemView(SlotDTO avaiSlot) {

        vehicleRentalService.printSystem(avaiSlot);

    }
}
