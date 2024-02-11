package com.armydev.tasleehbackend.supplyingsituation;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.data.jpa.domain.Specification;

import com.armydev.tasleehbackend.contracts.Contract;

import jakarta.persistence.metamodel.SingularAttribute;

public class SupplyingSituationSpecs {

  public static Map<String, Function<Object, Specification<SupplyingSituation>>> specsMap = new HashMap<>();

  static {
    specsMap.put("contract", value -> SupplyingSituationSpecs.byContract((Contract) value));
  }

  public static Specification<SupplyingSituation> byContract(Contract contract) {
    SingularAttribute<SupplyingSituation, Contract> attrib = SupplyingSituation_.contract;
    System.out.println(attrib);
    return (root, query, builder) -> builder.equal(root.get(attrib), contract);
  }
}
