package com.eni.ioc.assetintegrity.enitities;

public class AreaDto {

	private String mapCoords;
	private String color;
	private String color2;
	private String name;
	private String parentArea;
	private String parentMap;
	private String shape;
    private String description;
    private String decodedValue;
	private Long backlogCrit;
	private Long criticalCrit;
	private Long corrosionCrit;
	private Long integrityCrit;
	private Long backlogHigh;
	private Long criticalHigh;
	private Long corrosionHigh;
	private Long integrityHigh;
	private String code;

	public AreaDto() {
		// Auto-generated constructor stub
	}

	public String getMapCoords() {
		return mapCoords;
	}

	public void setMapCoords(String mapCoords) {
		this.mapCoords = mapCoords;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getColor2() {
		return color2;
	}

	public void setColor2(String color2) {
		this.color2 = color2;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getShape() {
		return shape;
	}

	public void setShape(String shape) {
		this.shape = shape;
	}

	public Long getBacklogCrit() {
		return backlogCrit;
	}

	public void setBacklogCrit(Long backlogCrit) {
		this.backlogCrit = backlogCrit;
	}

	public Long getCriticalCrit() {
		return criticalCrit;
	}

	public void setCriticalCrit(Long criticalCrit) {
		this.criticalCrit = criticalCrit;
	}

	public Long getCorrosionCrit() {
		return corrosionCrit;
	}

	public void setCorrosionCrit(Long corrosionCrit) {
		this.corrosionCrit = corrosionCrit;
	}

	public Long getIntegrityCrit() {
		return integrityCrit;
	}

	public void setIntegrityCrit(Long inspectionCrit) {
		this.integrityCrit = inspectionCrit;
	}

	public Long getBacklogHigh() {
		return backlogHigh;
	}

	public void setBacklogHigh(Long backlogHigh) {
		this.backlogHigh = backlogHigh;
	}

	public Long getCriticalHigh() {
		return criticalHigh;
	}

	public void setCriticalHigh(Long criticalHigh) {
		this.criticalHigh = criticalHigh;
	}

	public Long getCorrosionHigh() {
		return corrosionHigh;
	}

	public void setCorrosionHigh(Long corrosionHigh) {
		this.corrosionHigh = corrosionHigh;
	}

	public Long getIntegrityHigh() {
		return integrityHigh;
	}

	public void setIntegrityHigh(Long inspectionHigh) {
		this.integrityHigh = inspectionHigh;
	}

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getDecodedValue() {
            return decodedValue;
        }

        public void setDecodedValue(String decodedValue) {
            this.decodedValue = decodedValue;
        }

		public String getParentArea() {
			return parentArea;
		}

		public void setParentArea(String parentArea) {
			this.parentArea = parentArea;
		}

		public String getParentMap() {
			return parentMap;
		}

		public void setParentMap(String parentMap) {
			this.parentMap = parentMap;
		}

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

}
