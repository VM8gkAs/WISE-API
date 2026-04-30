package org.wise.portal.presentation.web.controllers;

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

@RestController
@RequestMapping("/api/chat-gpt")
public class ChatGptController {

	@Autowired
	@Qualifier("openAiLlmProvider")
	private LlmProvider llmProvider;

	@ResponseBody
	@Secured("ROLE_USER")
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	protected String sendChatMessage(@RequestBody String body) {
		return llmProvider.chat(body);
	}
}
