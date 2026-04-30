package org.wise.portal.service.llm.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
		RuntimeException ex = assertThrows(RuntimeException.class, () -> provider.chat("{}"));
		assertTrue(ex.getMessage().contains("API key is not configured for LLM provider: openai"));
	}

	@Test
	public void chat_NullApiKey_ThrowsRuntimeException() {
		LlmProvider provider = new OpenAiCompatibleLlmProvider("openai", null,
		    "https://api.openai.com/v1/chat/completions");
		RuntimeException ex = assertThrows(RuntimeException.class, () -> provider.chat("{}"));
		assertTrue(ex.getMessage().contains("API key is not configured for LLM provider: openai"));
	}

	@Test
	public void chat_MissingChatApiUrl_ThrowsRuntimeException() {
		LlmProvider provider = new OpenAiCompatibleLlmProvider("aws-bedrock", "test-key", "");
		RuntimeException ex = assertThrows(RuntimeException.class, () -> provider.chat("{}"));
		assertTrue(
		    ex.getMessage().contains("Chat API URL is not configured for LLM provider: aws-bedrock"));
	}

	@Test
	public void chat_NullChatApiUrl_ThrowsRuntimeException() {
		LlmProvider provider = new OpenAiCompatibleLlmProvider("aws-bedrock", "test-key", null);
		RuntimeException ex = assertThrows(RuntimeException.class, () -> provider.chat("{}"));
		assertTrue(
		    ex.getMessage().contains("Chat API URL is not configured for LLM provider: aws-bedrock"));
	}
}
