package omnichannel_conversations.hello;

import org.eclipse.microprofile.faulttolerance.ExecutionContext;
import org.eclipse.microprofile.faulttolerance.FallbackHandler;

public class HelloFallbackHandler implements FallbackHandler<String> {
	@Override
	public String handle(ExecutionContext context) {
		return String.format("Triggered fallback mechanism with message:\n%s", context.getFailure().getMessage());
	}
}
