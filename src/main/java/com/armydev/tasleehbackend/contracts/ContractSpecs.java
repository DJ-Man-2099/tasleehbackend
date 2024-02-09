package com.armydev.tasleehbackend.contracts;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.data.jpa.domain.Specification;

public class ContractSpecs {

  public static Map<String, Function<Object, Specification<Contract>>> specsMap = new HashMap<>();

  static {
    specsMap.put("contractValue", value -> ContractSpecs.byContractValue((String) value));
    specsMap.put("contractNo", value -> ContractSpecs.byContractNo((String) value));
    specsMap.put("company", value -> ContractSpecs.byCompany((String) value));
    specsMap.put("currency", value -> ContractSpecs.byCurrency((String) value));
    specsMap.put("dateBefore", value -> ContractSpecs.byDateBefore((String) value));
    specsMap.put("dateAfter", value -> ContractSpecs.byDateAfter((String) value));
  }

  public static Specification<Contract> byContractValue(String value) {
    return (root, query, builder) -> builder.like(root.get(Contract_.contractValue).as(String.class),
        "%" + value + "%");
  }

  public static Specification<Contract> byContractNo(String no) {
    return (root, query, builder) -> builder.like(root.get(Contract_.contractNo).as(String.class),
        "%" + no + "%");
  }

  public static Specification<Contract> byCompany(String company) {
    return (root, query, builder) -> builder.like(root.get(Contract_.company).as(String.class),
        "%" + company + "%");
  }

  public static Specification<Contract> byCurrency(String currency) {
    return (root, query, builder) -> builder.like(root.get(Contract_.currency).as(String.class),
        "%" + currency + "%");
  }

  public static Specification<Contract> byDateBefore(String dateBefore) {
    if (dateBefore.length() == 0)
      return null;
    Date date = Date.valueOf(dateBefore);
    return (root, query, builder) -> builder.lessThanOrEqualTo(root.get(Contract_.contractDate).as(Date.class),
        date);
  }

  public static Specification<Contract> byDateAfter(String dateAfter) {
    if (dateAfter.length() == 0)
      return null;
    Date date = Date.valueOf(dateAfter);
    return (root, query, builder) -> builder.greaterThanOrEqualTo(root.get(Contract_.contractDate).as(Date.class),
        date);
  }

}
