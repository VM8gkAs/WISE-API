package org.wise.portal.service.llm;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.wise.portal.service.llm.impl.OpenAiCompatibleLlmProvider;

/**
 * Spring configuration that creates named {@link LlmProvider} beans from application properties.
 *
 * <p>Each AI endpoint used by WISE gets its own named bean so that controllers can inject the
 * right provider without knowing implementation details.  Adding a new provider in the future
 * (e.g. Gemini, Claude, or a local Ollama gateway) requires only:
 * <ol>
 *   <li>A new {@link LlmProvider} implementation class (or reuse {@link OpenAiCompatibleLlmProvider}
 *       for any OpenAI-compatible endpoint), and</li>
 *   <li>A new {@code @Bean} method below wired from the corresponding properties.</li>
 * </ol>
 *
 * <p>Relevant application properties:
 * <pre>
 *   # AWS Bedrock (OpenAI-compatible runtime)
 *   aws.bedrock.api.key=
 *   aws.bedrock.runtime.endpoint=
 *
 *   # OpenAI
 *   openai.api.key=
 *   openai.chat.api.url=https://api.openai.com/v1/chat/completions
 * </pre>
 *
 * @author WISE Contributors
 */
@Configuration
public class LlmProviderConfig {

	/**
	 * Provider backed by AWS Bedrock's OpenAI-compatible runtime endpoint.
	 *
	 * <p>Bedrock exposes an {@code /openai/v1/chat/completions} path on top of the configured
	 * runtime endpoint, making it compatible with the same HTTP adapter used for OpenAI.
	 */
	@Bean("bedrockLlmProvider")
	public LlmProvider bedrockLlmProvider(
	    @Value("${aws.bedrock.api.key:}") String apiKey,
	    @Value("${aws.bedrock.runtime.endpoint:}") String runtimeEndpoint) {
		String chatApiUrl = (runtimeEndpoint == null || runtimeEndpoint.isEmpty())
		    ? ""
		    : runtimeEndpoint + "/openai/v1/chat/completions";
		return new OpenAiCompatibleLlmProvider("aws-bedrock", apiKey, chatApiUrl);
	}

	/**
	 * Provider backed by the OpenAI API (or any OpenAI-compatible endpoint configured via
	 * {@code openai.chat.api.url}, e.g. a local Ollama/vLLM gateway).
	 */
	@Bean("openAiLlmProvider")
	public LlmProvider openAiLlmProvider(
	    @Value("${openai.api.key:}") String apiKey,
	    @Value("${openai.chat.api.url:https://api.openai.com/v1/chat/completions}") String chatApiUrl) {
		return new OpenAiCompatibleLlmProvider("openai", apiKey, chatApiUrl);
	}
}
