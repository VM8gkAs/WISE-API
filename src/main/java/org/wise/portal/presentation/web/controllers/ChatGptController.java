package org.wise.portal.presentation.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.wise.portal.service.llm.LlmProvider;

/**
 * REST endpoint that forwards chat-completion requests to the OpenAI LLM provider.
 *
 * <p>The actual HTTP call is delegated to the {@link LlmProvider} abstraction. The
 * {@code openai.chat.api.url} property may point to any OpenAI-compatible endpoint,
 * including local gateways such as Ollama or vLLM.
 *
 * @see org.wise.portal.service.llm.LlmProviderConfig
 */
@RestController
@RequestMapping("/api/chat-gpt")
public class ChatGptController {

	@Autowired
	@Qualifier("openAiLlmProvider")
	private LlmProvider llmProvider;

	@ResponseBody
	@Secured("ROLE_USER")
	@PostMapping
	protected String sendChatMessage(@RequestBody String body) {
		return llmProvider.chat(body);
	}
}
