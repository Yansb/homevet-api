package com.Yansb.homevet.infrastructure.serializers;

import java.io.IOException;

import org.locationtech.jts.geom.Point;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class PointSerializer extends JsonSerializer<Point> {
  @Override
  public void serialize(Point value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
    if (value == null) {
      gen.writeNull();
    } else {
      gen.writeStartObject();
      gen.writeNumberField("lng", value.getX());
      gen.writeNumberField("lat", value.getY());
      gen.writeNumberField("srid", value.getSRID());
      gen.writeEndObject();
    }
  }

}
