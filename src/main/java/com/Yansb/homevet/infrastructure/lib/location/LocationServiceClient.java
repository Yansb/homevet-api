package com.Yansb.homevet.infrastructure.lib.location;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.Yansb.homevet.infrastructure.lib.location.dtos.GetLocationByCepResponse;

@Service
public class LocationServiceClient {
  private final RestClient brasilApiRestClient;

  public LocationServiceClient(RestClient.Builder restClientBuilder) {
    this.brasilApiRestClient = restClientBuilder.baseUrl("https://brasilapi.com.br/api/").build();
  }

  public GetLocationByCepResponse getLocationByCep(String cep) {
    return this.brasilApiRestClient.get().uri("cep/v2/{cep}", cep).retrieve()
        .body(GetLocationByCepResponse.class);
  }

}
