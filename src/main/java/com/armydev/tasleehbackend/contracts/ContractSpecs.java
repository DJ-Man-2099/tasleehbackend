package com.armydev.tasleehbackend.contracts;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.data.jpa.domain.Specification;

public class ContractSpecs {

    public static Map<String, Function<String, Specification<Contract>>> specsMap = new HashMap<>();

    static {
        specsMap.put("contractValue", value -> ContractSpecs.byContractValue(value));
        specsMap.put("contractNo", value -> ContractSpecs.byContractNo(value));
        specsMap.put("company", value -> ContractSpecs.byCompany(value));
        specsMap.put("currency", value -> ContractSpecs.byCurrency(value));
        specsMap.put("dateBefore", value -> ContractSpecs.byDateBefore(value));
        specsMap.put("dateAfter", value -> ContractSpecs.byDateAfter(value));
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
