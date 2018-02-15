package co.dictionary.client;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class Test {

	public static void main(String[] args) {

		int a[] = {1,2,3};

		int duplicates = countDuplicates(a);

		System.out.println(duplicates);

	}

	private static int countDuplicates(int[] numbers) {

		int finalCount = 0;

		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		map.put(0, 0);
		map.put(1, 0);
		map.put(2, 0);
		map.put(3, 0);
		map.put(4, 0);
		map.put(5, 0);
		map.put(6, 0);
		map.put(7, 0);
		map.put(8, 0);
		map.put(9, 0);

		for (int i = 0; i < numbers.length; i++) {
			int number = numbers[i];

			if (number == 0) {
				map.put(0, map.get(0) + 1);
			} else if (number == 1) {
				map.put(1, map.get(1) + 1);
			} else if (number == 2) {
				map.put(2, map.get(2) + 1);
			} else if (number == 3) {
				map.put(3, map.get(3) + 1);
			} else if (number == 4) {
				map.put(4, map.get(4) + 1);
			} else if (number == 5) {
				map.put(5, map.get(5) + 1);
			} else if (number == 6) {
				map.put(6, map.get(6) + 1);
			} else if (number == 7) {
				map.put(7, map.get(7) + 1);
			} else if (number == 8) {
				map.put(8, map.get(8) + 1);
			} else {
				map.put(9, map.get(9) + 1);
			}

			Iterator<Entry<Integer, Integer>> itrMAp = map.entrySet().iterator();

			while (itrMAp.hasNext()) {
				Entry<Integer, Integer> entry = itrMAp.next();
				if (entry.getValue() >1) {
					finalCount++;
				}
			}

		}

		return finalCount;
	}

}
