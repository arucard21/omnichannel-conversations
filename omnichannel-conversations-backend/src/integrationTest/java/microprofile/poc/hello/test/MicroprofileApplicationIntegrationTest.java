package microprofile.poc.hello.test;

import java.nio.file.Paths;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;
import omnichannel_conversations.hello.HelloWebResource;

@ExtendWith(ArquillianExtension.class)
public class MicroprofileApplicationIntegrationTest {
	final String serverPort = System.getProperty("tomee.httpPort");

	@Deployment
    public static WebArchive loadPrebuiltWarForDeployment() {
		// configures the integration test with the latest built war file. Requires that one is already built.
        return ShrinkWrap.createFromZipFile(WebArchive.class, Paths.get("build/libs/microprofile-poc.war").toFile());
    }

	@Test
	public void api_returnsHelloWorld() {
		Response response = ClientBuilder.newClient()
				.target(UriBuilder.fromUri("http://localhost")
						.port(Integer.valueOf(serverPort))
						.path("microprofile-poc")
						.path(HelloWebResource.class))
				.request(MediaType.TEXT_PLAIN)
				.get();
		Assertions.assertEquals(200, response.getStatus());
		Assertions.assertEquals("Hello World", response.readEntity(String.class));
	}
}
