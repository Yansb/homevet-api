package com.Yansb.homevet.infrastructure.lib.location.dtos;

public record GetLocationByCepResponse(
    String cep,
    String state,
    String city,
    String neighborhood,
    String street,
    String service,
    LocationCoordinates location) {
  public record LocationCoordinates(
      String type,
      Coordinates coordinates) {
    public record Coordinates(
        String longitude,
        String latitude) {
    }
  }
}
