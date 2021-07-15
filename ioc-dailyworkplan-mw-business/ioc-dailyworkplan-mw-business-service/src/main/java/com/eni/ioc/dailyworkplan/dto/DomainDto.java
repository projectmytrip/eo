package com.eni.ioc.dailyworkplan.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DomainDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String code;
	private String cardName;
	private boolean isWriting; 
	private List<LevelDto> levels = new ArrayList<>();
	
	public DomainDto() {
		
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCardName() {
		return cardName;
	}

	public void setCardName(String cardName) {
		this.cardName = cardName;
	}

	public List<LevelDto> getLevels() {
		return levels;
	}

	public void setLevels(List<LevelDto> levels) {
		this.levels = levels;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cardName == null) ? 0 : cardName.hashCode());
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((levels == null) ? 0 : levels.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DomainDto other = (DomainDto) obj;
		if (cardName == null) {
			if (other.cardName != null)
				return false;
		} else if (!cardName.equals(other.cardName))
			return false;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (levels == null) {
			if (other.levels != null)
				return false;
		} else if (!levels.equals(other.levels))
			return false;
		return true;
	}
	
	public boolean isWriting() {
		return isWriting;
	}
	
	public void setWriting(boolean isWriting) {
		this.isWriting = isWriting;
	}

}
