package com.armydev.tasleehbackend.contracts;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.data.jpa.domain.Specification;

public class SupplyingSituationSpecs {

  public static Map<String, Function<String, Specification<SupplyingSituation>>> specsMap = new HashMap<>();

}
