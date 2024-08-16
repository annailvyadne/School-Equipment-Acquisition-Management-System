package MyLibs;

import java.util.ArrayList;
import java.util.List;

public class EquipmentInfo implements Subject {

    private List<Observer> admins;
    private String equipmentName;
    private int price;
    private String condition;

    public EquipmentInfo(String equipmentName, int price, String condition) {
        admins = new ArrayList<Observer>();
        this.equipmentName = equipmentName;
        this.price = price;
        this.condition = condition;
    }

    @Override
    public void addObserver(Observer observer) {
        admins.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        admins.remove(observer);
    }

    public void setCondition(String condition) {
        this.condition = condition;
        notifyObservers();
    }

    @Override
    public void notifyObservers() {
        for (Observer customer : admins) {
            customer.update(this.equipmentName, this.condition);
        }
    }
}

