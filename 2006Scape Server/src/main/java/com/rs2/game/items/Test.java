package com.rs2.game.items;

import java.util.Arrays;

public class Test {

	public static void main(String[] args) {
		ItemDefinitions2.load();
		System.out.println(ItemDefinitions2.getWeight(4151));
		System.out.println(Arrays.toString(ItemDefinitions2.getBonus(4151)));
		System.out.println(Arrays.toString(ItemDefinitions2.getBonus(995)));
	}

}
