package com.armydev.tasleehbackend.helpers;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class RequestsHelper {
	public static Specification<T> getFilters(
			Class<T> type,
			Map<String, String> searchParams,
			Map<String, Function<Object, Specification<SupplyingSituation>>> specsMap) {
		Map<String, String> filters = searchParams.entrySet().stream()
				.filter(entry -> !(entry.getKey().equals("pageSize") || entry.getKey().equals("page")))
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
		Specification<SupplyingSituation> filter = Specification.where(null);

		for (var filterEntry : filters.entrySet()) {
			try {
				filter = Specification.where(filter)
						.and(specsMap.get(filterEntry.getKey()).apply(filterEntry.getValue()));
			} catch (Exception e) {
				result.put("status", HttpStatus.BAD_REQUEST.value());
				result.put("error",
						String.format("Attribute %s cannot be found within %s", filterEntry.getKey(), "Contract"));
				return ResponseEntity.ok(result);
			}
		}
	}
}
