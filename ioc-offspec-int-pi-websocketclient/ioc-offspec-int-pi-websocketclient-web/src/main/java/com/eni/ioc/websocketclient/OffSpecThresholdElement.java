package com.eni.ioc.websocketclient;

public enum OffSpecThresholdElement {
	
	H2SPREDICTIVEHIHI("H2S_Predictive", true, true, "F1AbEUDw2U7g9h0WnIgK-LOAawg0OZJwl1_6BGqIjTzmkiPJwElq0XKsoAkmWbdVdxpCyCwRURPRi1BRi5FTkkuQ09NXElPQ1xPRkYtU1BFQ3xIMlNfQ09OQ0VOVFJBVElPTl9QUkVESUNUSVZFfEhJ"),
	H2SPREDICTIVEHI("H2S_Predictive", true, true, "F1AbEUDw2U7g9h0WnIgK-LOAawg0OZJwl1_6BGqIjTzmkiPJwk88_mXbhKEC-6_1-8jLNNARURPRi1BRi5FTkkuQ09NXElPQ1xPRkYtU1BFQ3xIMlNfQ09OQ0VOVFJBVElPTl9QUkVESUNUSVZFfEhJSEk"),
	CO2ACTUALVALUEHIHI("CO2_ActualValue", false, false, "F1AbEUDw2U7g9h0WnIgK-LOAawg0OZJwl1_6BGqIjTzmkiPJwfBS-xhMsRUqnRHqJ5XOdcQRURPRi1BRi5FTkkuQ09NXElPQ1xPRkYtU1BFQ3xDTzJfQUNUVUFMVkFMVUV8SElISQ"),
	DWPH2OHIHI("DWP_H2O", false, false, "F1AbEUDw2U7g9h0WnIgK-LOAawg0OZJwl1_6BGqIjTzmkiPJwOuyKyhAJPkKQYn_irig0gARURPRi1BRi5FTkkuQ09NXElPQ1xPRkYtU1BFQ3xEV1BfSDJPfEhJSEk"),
	DWPHCHIHI("DWP_HC", false, false, "F1AbEUDw2U7g9h0WnIgK-LOAawg0OZJwl1_6BGqIjTzmkiPJwHxvtTRQ8kEaDLQMjvnOf3QRURPRi1BRi5FTkkuQ09NXElPQ1xPRkYtU1BFQ3xEV1BfSEN8SElISQ"),
	H2SCONCENTRATIONHIHI("H2S_Concentration", false, false, "F1AbEUDw2U7g9h0WnIgK-LOAawg0OZJwl1_6BGqIjTzmkiPJwrWY0vDRgGkeo-KywqE76zARURPRi1BRi5FTkkuQ09NXElPQ1xPRkYtU1BFQ3xIMlNfQ09OQ0VOVFJBVElPTnxISUhJ"),
	SULFURHRCHIHI("Sulfur_HRC", false, false, "F1AbEUDw2U7g9h0WnIgK-LOAawg0OZJwl1_6BGqIjTzmkiPJwYLmG7OMF_0eUVd-79-FlKARURPRi1BRi5FTkkuQ09NXElPQ1xPRkYtU1BFQ3xTVUxGVVJfSFJDfEhJSEk"),
	SULFURTOTALHIHI("Sulfur_Total", false, false, "F1AbEUDw2U7g9h0WnIgK-LOAawg0OZJwl1_6BGqIjTzmkiPJwasHlH4cPRkG-mKdj_WX6TQRURPRi1BRi5FTkkuQ09NXElPQ1xPRkYtU1BFQ3xTVUxGVVJfVE9UQUx8SElISQ"),
	WOBBEACTUALVALUEHIHI("WOBBE_ActualValue", false, true, "F1AbEUDw2U7g9h0WnIgK-LOAawg0OZJwl1_6BGqIjTzmkiPJwYolqTPD8v0WLny7yN2XuxQRURPRi1BRi5FTkkuQ09NXElPQ1xPRkYtU1BFQ3xXT0JCRV9BQ1RVQUxWQUxVRXxISUhJ"),
	WOBBEACTUALVALUELOLO("WOBBE_ActualValue", false, true, "F1AbEUDw2U7g9h0WnIgK-LOAawg0OZJwl1_6BGqIjTzmkiPJweXw_xmN2DE-Q7cSvXmebxQRURPRi1BRi5FTkkuQ09NXElPQ1xPRkYtU1BFQ3xXT0JCRV9BQ1RVQUxWQUxVRXxMT0xP");

	private String name;
	private boolean predictive;
	private boolean range;
	private String webId;
	
	public String getName() {
		return name;
	}
	
	public boolean isPredictive() {
		return predictive;
	}
	
	public boolean hasRange() {
		return range;
	}
	
	public String getWebId() {
		return webId;
	}
	
	OffSpecThresholdElement(String name, boolean predictive, boolean range, String webId){
		this.name = name;
		this.predictive = predictive;
		this.range = range;
		this.webId = webId;
	}
	
	public static OffSpecThresholdElement getByWebId(String webId){
		for(OffSpecThresholdElement o : OffSpecThresholdElement.values()){
			if(o.getWebId().equals(webId)){
				return o;
			}
		}
		return null;
	}
	
}