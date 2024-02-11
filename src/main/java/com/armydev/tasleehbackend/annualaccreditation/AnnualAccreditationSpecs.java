package com.armydev.tasleehbackend.annualaccreditation;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.data.jpa.domain.Specification;

import com.armydev.tasleehbackend.contracts.Contract;

public class AnnualAccreditationSpecs {
	public static Map<String, Function<Object, Specification<AnnualAccreditation>>> specsMap = new HashMap<>();

	static {
		specsMap.put("contract", value -> AnnualAccreditationSpecs.byContract((Contract) value));

	}

	public static Specification<AnnualAccreditation> byContract(Contract contract) {
		return (root, query, builder) -> builder.equal(root.get(AnnualAccreditation_.contract), contract);
	}
}
