package ru.mixa201.physics.classes;

import java.io.Serializable;

public class Value implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5149761525785747505L;
	private String type;
	private double value;
	private String descript;
	
	public Value(String type, double value, String descript) {
		super();
		this.type = type;
		this.value = value;
		this.descript = descript;
	}

	public String getType() {
		return type;
	}

	public double getValue() {
		return value;
	}

	public String getDescript() {
		return descript;
	}
	
	
}
