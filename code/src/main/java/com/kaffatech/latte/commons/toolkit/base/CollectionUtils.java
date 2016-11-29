package com.kaffatech.latte.commons.toolkit.base;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CollectionUtils extends org.springframework.util.CollectionUtils {

	public static int search(Object[] array, Object key) {
		if (array == null || array.length <= 0) {
			return -1;
		}

		for (int i = 0; i < array.length; i++) {
			if (key.equals(array[i])) {
				return i;
			}
		}
		return -1;
	}

	public static <T> List<T> retainStart(List<T> list) {
		return retainStart(list, 1);
	}

	public static <T> List<T> retainEnd(List<T> list) {
		return retainEnd(list, 1);
	}

	public static <T> List<T> retainStart(List<T> list, int no) {
		if (list == null || list.size() < no + 1) {
			return list;
		}

		List<T> retainList = new ArrayList<T>();
		for (int i = 0; i < no; i++) {
			retainList.add(list.get(i));
		}

		return retainList;
	}

	public static <T> List<T> retainEnd(List<T> list, int no) {
		if (list == null || list.size() < no + 1) {
			return list;
		}

		int size = list.size();

		List<T> retainList = new ArrayList<T>();
		for (int i = size - no; i < size; i++) {
			retainList.add(list.get(i));
		}

		return retainList;
	}

	public static <T> List<T> sortList(List<T> list) {
		if (list == null) {
			return null;
		}

		Object[] array = new Object[list.size()];

		for (int i = 0; i < list.size() ; i++) {
			array[i] = list.get(i);
		}
		Arrays.sort(array);

		List<T> sortedList = new ArrayList<T>();
		for (Object each : array) {
			sortedList.add((T) each);
		}

		return sortedList;
	}

	public static <T> List<T> sublist(List<T> list, int start, int end) {
		List<T> sublist = new ArrayList<T>();
		for (int i = start; i < end; i++) {
			sublist.add(list.get(i));
		}
		return sublist;
	}

	public static <T> List<T> sublist(List<T> list, int start) {
		return sublist(list, start, list.size());
	}

	public static <T> List<T> toList(T... array) {
		return arrayToList(array);
	}

	public static <T> List<T> toListFromMap(Map<?, T> map) {
		if (map == null) {
			return null;
		}

		List<T> list = new ArrayList<T>();
		for(Map.Entry<?, T> each : map.entrySet()) {
			list.add(each.getValue());
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public static <T> List<T> asList(T... item) {
		return Arrays.asList(item);
	}

	public static <T> List<T> shuffleList(List<T> list) {
		Collections.shuffle(list);
		return list;
	}

	public static Map convertToSafeMap(Map m) {
		Map safeMap = new ConcurrentHashMap();
		for(Object each : m.entrySet()) {
			Map.Entry entry = (Map.Entry) each;
			safeMap.put(entry.getKey(), entry.getValue());
		}
		return safeMap;
	}


	public static void main(String[] args) {
		List<String> list = new ArrayList<String>();
		list.add("1");
		list.add("2");
		list.add("3");

		System.out.println(retainStart(list, 0));
		System.out.println(retainEnd(list, 4));
	}

}
