package org.wise.portal.service.llm;

/**
 * Abstraction for AI Language Model providers that support chat completion.
 *
 * <p>Concrete implementations wrap a specific backend (e.g. OpenAI API, AWS Bedrock,
 * or a local OpenAI-compatible gateway such as Ollama/vLLM) while exposing a uniform
 * interface to callers.
 *
 * <p>Future providers (Gemini, Claude, OpenAI-compatible local models) should implement
 * this interface and be registered as Spring beans via {@link LlmProviderConfig}.
 *
 * @author WISE Contributors
 */
public interface LlmProvider {

	/**
	 * Send a chat-completion request and return the provider's raw JSON response.
	 *
	 * @param requestBody JSON request body in the OpenAI chat-completion format
	 * @return raw JSON response string from the provider
	 * @throws RuntimeException if the provider is not configured or the upstream call fails
	 */
	String chat(String requestBody);

	/**
	 * Short, human-readable identifier for this provider (e.g. {@code "aws-bedrock"},
	 * {@code "openai"}). Used for logging and future capability-based routing decisions.
	 */
	String getName();
}
