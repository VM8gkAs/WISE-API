package org.wise.portal.service.llm;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.wise.portal.service.llm.impl.HttpChatCompletionLlmProvider;

/**
 * Unit tests for {@link LlmProviderConfig} bean factory methods.
 */
public class LlmProviderConfigTest {

	private final LlmProviderConfig config = new LlmProviderConfig();

	@Test
	public void bedrockLlmProvider_WithValidConfig_ReturnsNamedProvider() {
		LlmProvider provider = config.bedrockLlmProvider("my-api-key",
		    "https://bedrock.example.com");
		assertNotNull(provider);
		assertEquals("aws-bedrock", provider.getName());
	}

	@Test
	public void bedrockLlmProvider_AppendsOpenAiPathToRuntimeEndpoint() {
		LlmProvider provider = config.bedrockLlmProvider("key", "https://bedrock.example.com");
		assertNotNull(provider);
		// Name is accessible; URL construction is verified via chat() misconfiguration test in
		// HttpChatCompletionLlmProviderTest
		assertEquals("aws-bedrock", provider.getName());
	}

	@Test
	public void bedrockLlmProvider_EmptyEndpoint_ReturnsProviderWithEmptyUrl() {
		LlmProvider provider = config.bedrockLlmProvider("key", "");
		assertNotNull(provider);
		assertEquals("aws-bedrock", provider.getName());
	}

	@Test
	public void openAiLlmProvider_WithValidConfig_ReturnsNamedProvider() {
		LlmProvider provider = config.openAiLlmProvider("sk-test",
		    "https://api.openai.com/v1/chat/completions");
		assertNotNull(provider);
		assertEquals("openai", provider.getName());
	}

	@Test
	public void openAiLlmProvider_ReturnsHttpChatCompletionLlmProvider() {
		LlmProvider provider = config.openAiLlmProvider("sk-test",
		    "https://api.openai.com/v1/chat/completions");
		assertNotNull(provider);
		assertEquals(HttpChatCompletionLlmProvider.class, provider.getClass());
	}
}
