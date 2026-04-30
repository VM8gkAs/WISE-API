package org.wise.portal.service.llm;

/**
 * Abstraction for an AI chat-completion backend.
 *
 * <p>Implementations wrap a specific HTTP endpoint while exposing a uniform
 * interface to callers. Concrete providers are wired as named Spring beans
 * in {@link LlmProviderConfig}.
 *
 * @author WISE Contributors
 */
public interface LlmProvider {

	/**
	 * Send a chat-completion request and return the provider's raw JSON response.
	 *
	 * @param requestBody JSON request body in the chat-completion format
	 * @return raw JSON response string from the provider
	 * @throws RuntimeException if the provider is misconfigured or the upstream call fails
	 */
	String chat(String requestBody);

	/**
	 * Short identifier for this provider (e.g. {@code "aws-bedrock"}, {@code "openai"}).
	 * Used for logging.
	 */
	String getName();
}
