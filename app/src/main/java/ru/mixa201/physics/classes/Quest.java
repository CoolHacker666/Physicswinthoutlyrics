package ru.mixa201.physics.classes;

import java.io.Serializable;
import java.util.Vector;

public class Quest implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 489040780041955922L;
	private String quest;
	private String name;
	private String formula;
	private Vector<Value> values=new Vector<Value>();
	private String target;
	public Quest(String quest, String name, String formula,
			Vector<Value> values, String target) {
		super();
		this.quest = quest;
		this.name = name;
		this.formula = formula;
		this.values = values;
		this.target = target;
	}
	public String getQuest() {
		return quest;
	}
	public String getName() {
		return name;
	}
	public String getFormula() {
		return formula;
	}
	public Vector<Value> getValues() {
		return values;
	}
	public String getTarget() {
		return target;
	}
	
	
	
}
