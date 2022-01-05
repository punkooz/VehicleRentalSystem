import controller.VehicleRentalController;
import dtos.SlotDTO;
import dtos.VehicleDTO;

import java.util.ArrayList;
import java.util.List;

public class VehicleRental {

    public static void main(String[] args) {
        VehicleRentalController vehicleRentalController = new VehicleRentalController();

        //add gachibowli
        VehicleDTO vehicleDTO = new VehicleDTO("suv", 1, 12);
        VehicleDTO vehicleDTO1 = new VehicleDTO("sedan", 3, 10);
        VehicleDTO vehicleDTO2 = new VehicleDTO("bikes", 3, 20);
        List<VehicleDTO> vehicleDTOList = new ArrayList<>();
        vehicleDTOList.add(vehicleDTO);
        vehicleDTOList.add(vehicleDTO1);
        vehicleDTOList.add(vehicleDTO2);
        vehicleRentalController.add_branch("gachibowli", vehicleDTOList);

        //add kukatpally
        VehicleDTO vehicleDTO3 = new VehicleDTO("hatch_back", 4, 8);
        VehicleDTO vehicleDTO4 = new VehicleDTO("sedan", 3, 11);
        VehicleDTO vehicleDTO5 = new VehicleDTO("bikes", 3, 30);
        List<VehicleDTO> vehicleDTOList2 = new ArrayList<>();
        vehicleDTOList2.add(vehicleDTO3);
        vehicleDTOList2.add(vehicleDTO4);
        vehicleDTOList2.add(vehicleDTO5);
        vehicleRentalController.add_branch("kukatpally", vehicleDTOList2);

        //add miyapur
        VehicleDTO vehicleDTO6 = new VehicleDTO("suv", 1, 11);
        VehicleDTO vehicleDTO7 = new VehicleDTO("sedan", 3, 10);
        VehicleDTO vehicleDTO8 = new VehicleDTO("bikes", 10, 3);
        List<VehicleDTO> vehicleDTOList3 = new ArrayList<>();
        vehicleDTOList3.add(vehicleDTO6);
        vehicleDTOList3.add(vehicleDTO7);
        vehicleDTOList3.add(vehicleDTO8);
        vehicleRentalController.add_branch("miyapur", vehicleDTOList3);

        vehicleRentalController.add_vehicle("gachibowli", 1, "sedan");

        //rentals

        SlotDTO rentSlot1 = new SlotDTO("20 TH FEB", "10:00 PM", "12:00 PM");
        vehicleRentalController.rent_vehicle("suv", rentSlot1, "lowCost");
        vehicleRentalController.rent_vehicle("suv", rentSlot1, "lowCost");
        vehicleRentalController.rent_vehicle("suv", rentSlot1, "lowCost");


        SlotDTO avaiSlot = new SlotDTO("20th Feb", "11:00 PM", "12:00 PM");

        vehicleRentalController.getAvailable("gachibowli", avaiSlot);

        vehicleRentalController.printSystemView(avaiSlot);
    }

}
