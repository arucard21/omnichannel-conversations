package microprofile.poc.hello.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import omnichannel_conversations.hello.HelloWebResource;

public class HelloWebResourceTest {
	@Test
	public void sayHello_returnsHelloWorld() {
		Assertions.assertEquals("Hello World", new HelloWebResource().sayHello());
	}
}
