package com.armydev.tasleehbackend.guaranteeletter;

import java.io.IOException;

import com.armydev.tasleehbackend.contracts.Contract;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class CustomSerializer extends JsonSerializer<Contract> {

	@Override
	public void serialize(Contract c, JsonGenerator g, SerializerProvider s) throws IOException {
		g.writeStartObject();

		g.writeStringField("currency", c.currency);

		g.writeEndObject();
	}

}
