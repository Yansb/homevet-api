package com.Yansb.homevet.api.location;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Yansb.homevet.api.location.request.GetNearbyDoctorsRequest;
import com.Yansb.homevet.api.location.response.NearbyDoctorResponse;
import com.Yansb.homevet.application.services.LocationService;
import com.Yansb.homevet.infrastructure.lib.location.LocationServiceClient;
import com.Yansb.homevet.infrastructure.lib.location.dtos.GetLocationByCepResponse;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/location")
public class LocationController {
  private LocationServiceClient locationServiceClient;
  private LocationService locationService;

  public LocationController(LocationServiceClient locationServiceClient, LocationService locationService) {
    this.locationServiceClient = locationServiceClient;
    this.locationService = locationService;
  }

  @GetMapping("/{cep}")
  public GetLocationByCepResponse getLocationByCep(@PathVariable String cep) {
    return this.locationServiceClient.getLocationByCep(cep);
  }

  @GetMapping("/nearby-doctors")
  public List<NearbyDoctorResponse> getNearbyDoctors(@ModelAttribute GetNearbyDoctorsRequest query) {
    return this.locationService.getNearbyDoctors(query.lat(), query.lng());
  }
}
