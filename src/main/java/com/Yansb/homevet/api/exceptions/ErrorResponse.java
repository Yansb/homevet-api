package com.Yansb.homevet.api.exceptions;

import lombok.Builder;

@Builder
public record ErrorResponse(
  String message,
  int status
) {
}
