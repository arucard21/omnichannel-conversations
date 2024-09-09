package omnichannel_conversations.hello;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Info;

import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import omnichannel_conversations.joke.Joke;
import omnichannel_conversations.joke.RandomJokeClient;

@Path("/hello")
@Timed
@RequestScoped
@OpenAPIDefinition(info = @Info(title = "Hello World endpoint", version = "1.0"))
public class HelloWebResource {
	@Inject
	@ConfigProperty(name = "injected.value")
	String injectedConfigurationProperty;

	@Inject
	RandomJokeClient randomJokeClient;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String sayHello() {
        return "Hello World";
    }

    @Timeout(500)
    @Fallback(HelloFallbackHandler.class)
	@Path("timeout")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String triggerFallback() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			System.err.println("Who dares disturb my slumber?!");
		}
		return "Did not trigger fallback";
	}

	@GET
	@Path("config-property")
    @Produces(MediaType.TEXT_PLAIN)
	public String injectedConfigurationProperty() {
		return injectedConfigurationProperty;
	}

	@GET
	@Path("random-joke")
	@Produces(MediaType.TEXT_PLAIN)
	public String randomJoke() {
		Joke randomJoke = randomJokeClient.getRandomJoke();
		return randomJoke.setup() + "\n" + randomJoke.punchline();
	}

	@GET
	@Path("authenticated")
	@Produces(MediaType.TEXT_PLAIN)
	@RolesAllowed("admin")
	public String requireAuthentication() {
		return "This is a secure message that requires authentication.";
	}
}
