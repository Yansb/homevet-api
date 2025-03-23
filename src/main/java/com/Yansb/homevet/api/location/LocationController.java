package com.Yansb.homevet.api.location;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Yansb.homevet.infrastructure.lib.location.LocationServiceClient;
import com.Yansb.homevet.infrastructure.lib.location.dtos.GetLocationByCepResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/location")
public class LocationController {
  private LocationServiceClient locationServiceClient;

  public LocationController(LocationServiceClient locationServiceClient) {
    this.locationServiceClient = locationServiceClient;
  }

  @GetMapping("/{cep}")
  public GetLocationByCepResponse getLocationByCep(@PathVariable String cep) {
    return this.locationServiceClient.getLocationByCep(cep);
  }

}
