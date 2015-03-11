package demo.util;

import java.util.ArrayList;
import java.util.List;

public class IterableUtil {

	public static <E> List<E> makeList(Iterable<E> iterable) {
		List<E> list = new ArrayList<E>();
		for (E item : iterable) {
			list.add(item);
		}
		return list;
	}

}
