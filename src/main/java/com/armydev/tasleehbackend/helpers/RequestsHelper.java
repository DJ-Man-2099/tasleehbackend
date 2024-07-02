package com.armydev.tasleehbackend.helpers;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.data.jpa.domain.Specification;

public class RequestsHelper<T> {
	public Specification<T> getFilters(
			Class<T> type,
			Map<String, String> searchParams,
			Map<String, Function<Object, Specification<T>>> specsMap) throws Exception {
		Map<String, String> filters = searchParams.entrySet().stream()
				.filter(entry -> !(entry.getKey().equals("pageSize") || entry.getKey().equals("page")))
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
		Specification<T> filter = Specification.where(null);

		for (var filterEntry : filters.entrySet()) {
			if (!specsMap.containsKey(filterEntry.getKey()))
				throw new Exception(
						"Attribute %s cannot be found within %s".formatted(
								filterEntry.getKey(), type.getSimpleName()));
			filter = Specification.where(filter)
					.and(specsMap.get(filterEntry.getKey()).apply(filterEntry.getValue()));

		}
		return filter;
	}
}
