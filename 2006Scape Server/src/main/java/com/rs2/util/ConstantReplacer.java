package com.rs2.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.rs2.game.content.StaticItemList;
import com.rs2.game.content.StaticNpcList;
import com.rs2.game.content.StaticObjectList;

/**
 * Introduces constants into an enum.
 * 
 * @author Advocatus
 *
 */
public class ConstantReplacer {

	private static String [] skipped = { "heal", "name", "type", "foodEffect" };
	private static String enum_clazz = "com.rs2.game.content.consumables.Food$FoodToEat";
	
	public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException, ClassNotFoundException {
		Map<Integer, String> items = buildNameMap(StaticItemList.class);
		Map<Integer, String> npcs = buildNameMap(StaticNpcList.class);
		Map<Integer, String> objects = buildNameMap(StaticObjectList.class);

		Class<?> c = Class.forName(enum_clazz);
		if (!c.isEnum()) {
			System.err.println(enum_clazz + " is not an an enum.");
			return;
		}
		if(skipped == null)
			buildSkipped(c);
		Field enumfield = null;
		try {
			enumfield = c.getDeclaredField("ENUM$VALUES");
		} catch (NoSuchFieldException e) {
			try {
				enumfield = c.getDeclaredField("$VALUES");
			} catch (NoSuchFieldException e1) {
				e1.printStackTrace();
				return;
			} 
		} 
		enumfield.setAccessible(true);

		Enum[] values = (Enum[]) enumfield.get(null);

		Field[] flds = c.getDeclaredFields();
		List<Field> vars = new LinkedList<>();
		for (Field f : flds) {
			if (!f.isEnumConstant() && !Modifier.isStatic(f.getModifiers())) {
				vars.add(f);
			}
		}

		for (Enum m : values) {
			String out = m.name() + "(";
			for (Field f : vars) {
				f.setAccessible(true);
				if(f.getGenericType().getTypeName() == "int")
					out += ((!skip(f.getName()) ? items.get(f.getInt(m)) : f.getInt(m)) + ", ");
				else if(f.getType() == (String.class))
					out += "\""+  f.get(m) +"\", ";
				else
					out +=  f.get(m) + ", ";
			}
			out = out.substring(0, out.length() - 2);
			out += "),";
			System.out.println(out);
		}
	}
	
	private static void buildSkipped(Class<?> c) {
		Field[] flds = c.getDeclaredFields();
		String out = ("private static String [] skipped = { ");
		for (Field f : flds) {
			if (!f.isEnumConstant() && !Modifier.isStatic(f.getModifiers())) {
				out += ("\"" + f.getName() + "\", ");
			}
		}
		out = out.substring(0, out.length() - 3);
		out += ("\" };");
		System.out.println(out);
	}
	
	private static Map<Integer, String> buildNameMap(Class clazz)
			throws IllegalArgumentException, IllegalAccessException {
		Field[] declaredFields = clazz.getDeclaredFields();
		Map<Integer, String> names = new HashMap<>();
		for (Field field : declaredFields) {
			if (Modifier.isStatic(field.getModifiers())) {
				names.put(field.getInt(null), field.getName());
			}
		}
		return names;
	}

	private static boolean skip(String string) {
		for (String s : skipped) {
			if (string.equalsIgnoreCase(s)) {
				return true;
			}
		}
		return false;
	}
}