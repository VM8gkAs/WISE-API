package org.wise.portal.service.llm;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.wise.portal.service.llm.impl.HttpChatCompletionLlmProvider;

/**
 * Spring configuration that creates named {@link LlmProvider} beans from application properties.
 *
 * <p>Each AI endpoint used by WISE gets its own named bean so that controllers can inject the
 * right provider via {@code @Qualifier}. The relevant properties are:
 * <pre>
 *   aws.bedrock.api.key=
 *   aws.bedrock.runtime.endpoint=
 *
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
	 * <p>Bedrock appends {@code /openai/v1/chat/completions} to the configured runtime endpoint.
	 */
	@Bean("bedrockLlmProvider")
	public LlmProvider bedrockLlmProvider(
	    @Value("${aws.bedrock.api.key:}") String apiKey,
	    @Value("${aws.bedrock.runtime.endpoint:}") String runtimeEndpoint) {
		String chatApiUrl = (runtimeEndpoint == null || runtimeEndpoint.isEmpty())
		    ? ""
		    : runtimeEndpoint + "/openai/v1/chat/completions";
		return new HttpChatCompletionLlmProvider("aws-bedrock", apiKey, chatApiUrl);
	}

	/**
	 * Provider backed by the OpenAI API. The {@code openai.chat.api.url} property may be
	 * overridden to point at any OpenAI-compatible endpoint.
	 */
	@Bean("openAiLlmProvider")
	public LlmProvider openAiLlmProvider(
	    @Value("${openai.api.key:}") String apiKey,
	    @Value("${openai.chat.api.url:https://api.openai.com/v1/chat/completions}") String chatApiUrl) {
		return new HttpChatCompletionLlmProvider("openai", apiKey, chatApiUrl);
	}
}
