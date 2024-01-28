package com.armydev.tasleehbackend.contracts;

import static com.armydev.tasleehbackend.contracts.SupplyingSituationSpecs.*;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/contractSupplyingSituation")
@AllArgsConstructor
public class SupplyingSituationController {

  private final SupplyingSituationRepo repo;

  // TODO: getAllSupplyingSituation ("/")
  @GetMapping
  public ResponseEntity<Map<String, Object>> getAllSupplyingSituation(@RequestParam Map<String, String> searchParams)
      throws Exception {
    var result = new HashMap<String, Object>();
    // Get Desired Page
    int pageSize = searchParams.containsKey("limit") ? Integer.parseInt(searchParams.get("pageSize")) : 10;
    int currentPage = searchParams.containsKey("page") ? Integer.parseInt(searchParams.get("page")) : 1;
    Pageable pageable = PageRequest.of(currentPage - 1, pageSize, Sort.by(Direction.DESC, "id"));
    // Get Filters as String Map
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
    // Get Results
    Page<SupplyingSituation> contractsPage = repo.findAll(filter, pageable);
    result.put("rows", contractsPage.toList());
    result.put("count", contractsPage.getTotalPages());
    result.put("status", HttpStatus.OK.value());
    return ResponseEntity.ok(result);
  }
  // TODO: createContractSupplyingSituation ("/:contractId")
  // TODO: updateContractSupplyingSituation ("/:id")
  // TODO: getContractSupplyingSituation ("/:contractId")

}
