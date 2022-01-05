package dtos;

public class VehicleDTO {

    String name;
    int quantity;
    int cost;

    public VehicleDTO(String name, int quantity, int cost) {
        this.name = name;
        this.quantity = quantity;
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getCost() {
        return cost;
    }
}
