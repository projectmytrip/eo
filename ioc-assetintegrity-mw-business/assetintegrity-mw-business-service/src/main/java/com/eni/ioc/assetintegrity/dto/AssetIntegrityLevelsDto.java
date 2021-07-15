package com.eni.ioc.assetintegrity.dto;

import java.io.Serializable;

public class AssetIntegrityLevelsDto<T> implements Serializable {

	private static final long serialVersionUID = 4063348518652917231L;

	T level0;
	T level1;
	T level2;
	T level2a;
	T level2b;
	T level3;

	public AssetIntegrityLevelsDto() {
	}

	public AssetIntegrityLevelsDto(T level0, T level1, T level2, T level2a, T level2b, T level3) {
		super();
		this.level0 = level0;
		this.level1 = level1;
		this.level2 = level2;
		this.level2a = level2a;
		this.level2b = level2b;
		this.level3 = level3;
	}

	public T getLevel0() {
		return level0;
	}

	public void setLevel0(T level0) {
		this.level0 = level0;
	}

	public T getLevel1() {
		return level1;
	}

	public void setLevel1(T level1) {
		this.level1 = level1;
	}

	public T getLevel2() {
		return level2;
	}

	public void setLevel2(T level2) {
		this.level2 = level2;
	}

	public T getLevel2a() {
		return level2a;
	}

	public void setLevel2a(T level2a) {
		this.level2a = level2a;
	}

	public T getLevel2b() {
		return level2b;
	}

	public void setLevel2b(T level2b) {
		this.level2b = level2b;
	}

	public T getLevel3() {
		return level3;
	}

	public void setLevel3(T level3) {
		this.level3 = level3;
	}

	@Override
	public String toString() {
		return "OffspecLevelsDto [level0=" + level0 + ", level1=" + level1 + ", level2=" + level2 + ", level2a="
				+ level2a + ", level2b=" + level2b + ", level3=" + level3 + "]";
	}
}
