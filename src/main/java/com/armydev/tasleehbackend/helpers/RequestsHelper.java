package com.armydev.tasleehbackend.helpers;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class RequestsHelper {
	public static Specification<T> getFilters(
			Class<T> type,
			Map<String, String> searchParams,
			Map<String, Function<Object, Specification<SupplyingSituation>>> specsMap) throws Exception {
		Map<String, String> filters = searchParams.entrySet().stream()
				.filter(entry -> !(entry.getKey().equals("pageSize") || entry.getKey().equals("page")))
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
		Specification<SupplyingSituation> filter = Specification.where(null);

		for (var filterEntry : filters.entrySet()) {
			if (!specsMap.containsKey(filterEntry.getKey()))
				throw new Exception(
						String.format("Attribute %s cannot be found within %s",
								filterEntry.getKey(), type.getName()));
			filter = Specification.where(filter)
					.and(specsMap.get(filterEntry.getKey()).apply(filterEntry.getValue()));

		}
	}
}
