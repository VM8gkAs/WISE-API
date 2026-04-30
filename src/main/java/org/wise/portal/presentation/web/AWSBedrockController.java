package org.wise.portal.presentation.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.wise.portal.service.llm.LlmProvider;

/**
 * REST endpoint that forwards chat-completion requests to the AWS Bedrock LLM provider.
 *
 * <p>The actual HTTP call is delegated to the {@link LlmProvider} abstraction, keeping
 * this controller free of provider-specific details. To switch or extend the underlying
 * AI backend, register a different {@link LlmProvider} bean named {@code "bedrockLlmProvider"}
 * in {@link org.wise.portal.service.llm.LlmProviderConfig}.
 */
@RestController
@RequestMapping("/api/aws-bedrock/chat")
public class AWSBedrockController {

	@Autowired
	@Qualifier("bedrockLlmProvider")
	private LlmProvider llmProvider;

	@ResponseBody
	@Secured("ROLE_USER")
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	protected String sendChatMessage(@RequestBody String body) {
		return llmProvider.chat(body);
	}

}
