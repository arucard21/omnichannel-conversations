package omnichannel_conversations.visit;

import java.net.URI;
import java.time.ZonedDateTime;

public record Visitor(
		String name,
		String organization,
		String telephoneNumber,
		String email,
		boolean confidential,
		ZonedDateTime expectedArrival,
		ZonedDateTime expectedDeparture,
		ZonedDateTime actualArrival,
		ZonedDateTime actualDeparture,
		String identificationNumber,
		String identificationType,
		URI assigned, //badge
		URI attending //reservation
		) {}
