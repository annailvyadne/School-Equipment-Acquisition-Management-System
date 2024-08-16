package MyLibs;

public class Equipment {

    private String equipmentName;
    private String equipmentType;
    private String condition;
    private String room;
    private String status;
    
	public Equipment(String equipmentName, String equipmentType, String condition, String room, String status) {
		super();
		this.equipmentName = equipmentName;
		this.equipmentType = equipmentType;
		this.condition = condition;
		this.room = room;
		this.status = status;
	}

	public String getEquipmentName() {
		return equipmentName;
	}

	public void setEquipmentName(String equipmentName) {
		this.equipmentName = equipmentName;
	}

	public String getEquipmentType() {
		return equipmentType;
	}

	public void setEquipmentType(String equipmentType) {
		this.equipmentType = equipmentType;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

    
    
    
}
