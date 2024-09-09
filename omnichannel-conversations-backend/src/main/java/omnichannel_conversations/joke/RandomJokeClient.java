package omnichannel_conversations.joke;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@RegisterRestClient(baseUri = "https://official-joke-api.appspot.com")
public interface RandomJokeClient {
	@GET
	@Path("random_joke")
	public Joke getRandomJoke();
}
