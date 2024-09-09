package omnichannel_conversations.visit;

import java.net.URI;
import java.time.ZonedDateTime;
import java.util.List;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import jakarta.ws.rs.core.MediaType;

@Schema(name="Visit")
public record Visit(
		@Schema(required = false, description = "The self-link to this visit")
		URI self,
		@Schema(required = false, description = "The reason for this visit")
		String reason,
		@Schema(required = false, description = "The amount of visitors for this visit, which may exceed the amount of visitors registered for this visit to allow for unregistered visitors.")
		int amountOfVisitors,
		@Schema(required = false, description = "The date and time at which this visit was canceled, if it was indeed canceled.")
		ZonedDateTime cancelationDate,
		@Schema(required = false, description = "The visitors that are registered for this visit.")
		List<Visitor> visitors,
		@Schema(required = false, description = "The people that are hosting this visit.")
		List<URI> host,
		@Schema(required = false, description = "The parking locations assigned to this visit")
		List<URI> assigned) {
	public static final String MEDIATYPE_STRING = "application/x.visit-v1+json";
	public static final MediaType MEDIATYPE = MediaType.valueOf(MEDIATYPE_STRING);
}
