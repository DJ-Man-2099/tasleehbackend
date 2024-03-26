package com.armydev.tasleehbackend.guaranteeletter;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.data.jpa.domain.Specification;

import com.armydev.tasleehbackend.contracts.Contract;

public class GuaranteeLetterSpecs {
	public static Map<String, Function<Object, Specification<GuaranteeLetter>>> specsMap = new HashMap<>();

	static {
		specsMap.put("contract", value -> GuaranteeLetterSpecs.byContract((Contract) value));

	}

	public static Specification<GuaranteeLetter> byContract(Contract contract) {
		return (root, query, builder) -> builder.equal(root.get(GuaranteeLetter_.contract), contract);
	}
}
