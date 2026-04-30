package org.wise.portal.service.llm.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import org.junit.jupiter.api.Test;
import org.wise.portal.service.llm.LlmProvider;

/**
 * Unit tests for {@link OpenAiCompatibleLlmProvider}.
 */
public class OpenAiCompatibleLlmProviderTest {

	@Test
	public void getName_ReturnsConfiguredName() {
		LlmProvider provider = new OpenAiCompatibleLlmProvider("openai", "test-key",
		    "https://api.openai.com/v1/chat/completions");
		assertEquals("openai", provider.getName());
	}

	@Test
	public void getName_BedrockProviderName_ReturnsCorrectName() {
		LlmProvider provider = new OpenAiCompatibleLlmProvider("aws-bedrock", "test-key",
		    "https://bedrock.example.com/openai/v1/chat/completions");
		assertEquals("aws-bedrock", provider.getName());
	}

	@Test
	public void chat_MissingApiKey_ThrowsRuntimeException() {
		LlmProvider provider = new OpenAiCompatibleLlmProvider("openai", "",
		    "https://api.openai.com/v1/chat/completions");
		assertThrows(RuntimeException.class, () -> provider.chat("{}"));
	}

	@Test
	public void chat_NullApiKey_ThrowsRuntimeException() {
		LlmProvider provider = new OpenAiCompatibleLlmProvider("openai", null,
		    "https://api.openai.com/v1/chat/completions");
		assertThrows(RuntimeException.class, () -> provider.chat("{}"));
	}

	@Test
	public void chat_MissingChatApiUrl_ThrowsRuntimeException() {
		LlmProvider provider = new OpenAiCompatibleLlmProvider("aws-bedrock", "test-key", "");
		assertThrows(RuntimeException.class, () -> provider.chat("{}"));
	}

	@Test
	public void chat_NullChatApiUrl_ThrowsRuntimeException() {
		LlmProvider provider = new OpenAiCompatibleLlmProvider("aws-bedrock", "test-key", null);
		assertThrows(RuntimeException.class, () -> provider.chat("{}"));
	}
}
