package com.eni.ioc.common;

public enum ProdProcMonKeyname {
	
	OPEN_WELLS(
			"P_NbrOpenWells",
			"F1AbEUDw2U7g9h0WnIgK-LOAawgemE_MAqF6BG2PzTzmkiPJwluQSz9_xWUSH3zAfnz8cNARURPRi1BRi5FTkkuQ09NXElPQ1xQUk9ELU1PTl9XRUxMU3xQX05CUk9QRU5XRUxMUw"),
	CLOSE_WELLS(
			"P_NbrClosedWells",
			"F1AbEUDw2U7g9h0WnIgK-LOAawgemE_MAqF6BG2PzTzmkiPJwUDR1Vdvt0EyyKTySryC52QRURPRi1BRi5FTkkuQ09NXElPQ1xQUk9ELU1PTl9XRUxMU3xQX05CUkNMT1NFRFdFTExT"),
	OPEN_INJECTORS(
			"I_NbrOpenWells",
			"F1AbEUDw2U7g9h0WnIgK-LOAawgemE_MAqF6BG2PzTzmkiPJwZDEbNYtNpk2CGnVFUkfcdARURPRi1BRi5FTkkuQ09NXElPQ1xQUk9ELU1PTl9XRUxMU3xJX05CUk9QRU5XRUxMUw"),
	CLOSE_INJECTORS(
			"I_NbrClosedWells",
			"F1AbEUDw2U7g9h0WnIgK-LOAawgemE_MAqF6BG2PzTzmkiPJwSA6KGyviOEi_jB6xvPMk5wRURPRi1BRi5FTkkuQ09NXElPQ1xQUk9ELU1PTl9XRUxMU3xJX05CUkNMT1NFRFdFTExT"),
	PROCESS_KPI(
			"ProcessKPI",
			"F1AbEUDw2U7g9h0WnIgK-LOAawgB_YZJ82I6BGXYTTzmkiPJwlU8I9-jpRk2qzP5nC6StAQRURPRi1BRi5FTkkuQ09NXElPQ1xQUk9DLU1PTnxQUk9DRVNTS1BJ"),
	MAINTENANCE_KPI(
			"MaintenanceKPI",
			"F1AbEUDw2U7g9h0WnIgK-LOAawgB_YZJ82I6BGXYTTzmkiPJwy9dW87b2D0y8QfiAGPxqmARURPRi1BRi5FTkkuQ09NXElPQ1xQUk9DLU1PTnxNQUlOVEVOQU5DRUtQSQ"),
	ENERGY_EFFICIENCY_KPI(
			"EnergyEfficiencyKPI",
			"F1AbEUDw2U7g9h0WnIgK-LOAawgB_YZJ82I6BGXYTTzmkiPJwdRc7fQ1eQU6ERR4Vj-ZAgwRURPRi1BRi5FTkkuQ09NXElPQ1xQUk9DLU1PTnxFTkVSR1lFRkZJQ0lFTkNZS1BJ"),
	ASSET_INTEGRITY_KPI(
			"AssetIntegrityKPI",
			"F1AbEUDw2U7g9h0WnIgK-LOAawgB_YZJ82I6BGXYTTzmkiPJwQzlHGX_BAE2mthPlonhT8ARURPRi1BRi5FTkkuQ09NXElPQ1xQUk9DLU1PTnxBU1NFVElOVEVHUklUWUtQSQ"),
	NUM_WELLS(
			"P_NbrWells",
			"F1AbEUDw2U7g9h0WnIgK-LOAawgemE_MAqF6BG2PzTzmkiPJwMc9jDYk3VU6JxUxtPGYs7ARURPRi1BRi5FTkkuQ09NXElPQ1xQUk9ELU1PTl9XRUxMU3xQX05CUldFTExT"),
	NUM_INJECTORS(
			"I_NbrWells",
			"F1AbEUDw2U7g9h0WnIgK-LOAawgemE_MAqF6BG2PzTzmkiPJwfxPE3LwFRU-NtWk1s3sAuARURPRi1BRi5FTkkuQ09NXElPQ1xQUk9ELU1PTl9XRUxMU3xJX05CUldFTExT"),
    DELIVERY_P(
			"Delivery_P",
			"F1AbEUDw2U7g9h0WnIgK-LOAawgB_YZJ82I6BGXYTTzmkiPJwINArSIoCwUKJPM4-RUL2mgRURPRi1BRi5FTkkuQ09NXElPQ1xQUk9DLU1PTnxERUxJVkVSWV9Q"),
    DELIVERY_P_RED(
            "RED",//"Delivery_P|RED",
            "F1AbEUDw2U7g9h0WnIgK-LOAawgB_YZJ82I6BGXYTTzmkiPJwDI_Ioh9khk2_JdWVOC_uJARURPRi1BRi5FTkkuQ09NXElPQ1xQUk9DLU1PTnxERUxJVkVSWV9QfFJFRA"),
    DELIVERY_P_YELLOW(
            "YELLOW",//"Delivery_P|YELLOW",
            "F1AbEUDw2U7g9h0WnIgK-LOAawgB_YZJ82I6BGXYTTzmkiPJwiTNupe3FGkyTqbVtwFRxEgRURPRi1BRi5FTkkuQ09NXElPQ1xQUk9DLU1PTnxERUxJVkVSWV9QfFlFTExPVw");
	
	private String webId;
	private String piName;
	
	public String getWebId() {
		return this.webId;
	}
	
	public String getPiName() {
		return this.piName;
	}
	
	public static boolean contains(String toMatch) {
		ProdProcMonKeyname[] elements = ProdProcMonKeyname.values();
		for (ProdProcMonKeyname e: elements) {
			if (e.getPiName().equalsIgnoreCase(toMatch)) {
				return true;
			}
		}
		return false;
	}

	private ProdProcMonKeyname(String piName, String webId) {
		this.webId = webId;
		this.piName = piName;
	}
}