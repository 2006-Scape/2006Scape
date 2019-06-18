package redone.game.objects.impl;

import redone.game.players.Client;

public class Searching {
	
	private enum SearchData {
		BOXES(new int[] {359, 361, 9536, 12545}, "There is nothing interesting in these boxes."),
		CRATES(new int[] {354, 355, 356, 357, 358, 366, 4714, 4715, 4716, 4717, 4718, 4719, 4721, 4722, 4723, 9533, 9534, 9535, 11485, 11486, 12548, 12547}, "You search the crate but find nothing."),
		SACKS(new int[] {365}, "There is nothing interesting in these sacks."),
		BOOKCASE(new int[] {380, 381, 4617, 4671, 9611}, "The bookcase is empty."),
		WARDROBE(new int[] {389}, "The wardrobe is empty."),
		DRAWER(new int[] {348, 350, 5618}, "The drawer is empty."),
		CHEST(new int[] {378}, "The chest is empty.");
		
	private final int[] objectId;
	private final String searchText;

	private SearchData(int[] objectId, String searchText) {
		this.objectId = objectId;
		this.searchText = searchText;
	}
	
	private int[] getObjectId() {
		return objectId;
	}
	private String getObjectText() {
			return searchText;
		}
	}
	
	public static void searchObject(final Client player, int objectType) {
		for (SearchData s: SearchData.values()) {
			for (int i = 0; i < s.getObjectId().length; i++) {
				if (objectType == s.getObjectId()[i]) {
					player.searchObjectDelay = System.currentTimeMillis();
					player.getActionSender().sendMessage(s.getObjectText());
 				}
			}
		}	
	}
}
