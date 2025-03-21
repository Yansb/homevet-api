package com.Yansb.homevet.api.health;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {

  @GetMapping("/")
  public Map<String, Boolean> healthCheck() {
    Map<String, Boolean> response = new HashMap<>();
    response.put("health", true);
    return response;
  }

}
