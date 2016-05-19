package ru.mixa201.physics.Questionary;

import android.database.Cursor;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Watcher {
	public static boolean equal(String normal, String users, Cursor values1, Cursor values2){
		normal=toNumForm(normal, values1);
		users=toNumForm(users, values2);
		return normal.equalsIgnoreCase(users);
	}

	public static byte check(double normal, double users) {
		byte res = 0;
		if (users >= normal - 1 && users <= normal + 1) {
			res++;
		}
		if (users == normal) {
			res++;
		}
		return res;
	}

	public static String toNumForm(String formula, Cursor values){
		int m=0;
		String numFormula = "";
		char[] cForm = formula.toCharArray();
		values.move(0);
		while (values.moveToNext()) {
			String name = values.getString(1);
			double value = values.getDouble(2);
			for (int i = 0; i < formula.length(); i++) {
				
				try {
					if (name.equalsIgnoreCase(formula.substring(i,
							i + name.length()))) {
						numFormula += value;
						m+=(""+value).length();
						if(!isService(cForm[i+name.length()])){
							numFormula+="*";
						}
						else if(cForm[i+name.length()]=='/'){
							numFormula+="/";
						}
						for (int j = i + name.length() - 1; !inD(cForm[j], 'A',
								'Z') || !inD(cForm[j], 'a', 'z'); j++) {
							break;
						}
						break;
					}
				} catch (ArrayIndexOutOfBoundsException e) {

				} catch (StringIndexOutOfBoundsException e) {

				}
				if(i>=m){
					numFormula+=formula.substring(i, i+1);
					m+=1;
				}
			}
		}
		return numFormula;
	}

	private static boolean inD(char c, char s, char f) {
		if (c >= s && c <= f) {
			return true;
		}
		return false;
	}
	
	private static boolean isService(char c){
		if(c=='/' || c=='^' || c=='$' || c=='*'){
			return true;
		}
		return false;
	}
}
