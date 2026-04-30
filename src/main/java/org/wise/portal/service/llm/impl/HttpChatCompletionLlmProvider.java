package org.wise.portal.service.llm.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.wise.portal.service.llm.LlmProvider;

/**
 * {@link LlmProvider} implementation that sends chat-completion requests over HTTP
 * using a Bearer-token Authorization header.
 *
 * <p>Works with any endpoint that accepts OpenAI-compatible chat-completion requests,
 * including AWS Bedrock's runtime endpoint and the OpenAI API.
 *
 * <p>Instances are created by {@link org.wise.portal.service.llm.LlmProviderConfig}
 * and injected into controllers by name.
 *
 * @author WISE Contributors
 */
public class HttpChatCompletionLlmProvider implements LlmProvider {

	private final String name;
	private final String apiKey;
	private final String chatApiUrl;

	/**
	 * @param name       short provider identifier used in logs (e.g. {@code "aws-bedrock"})
	 * @param apiKey     bearer token sent in the {@code Authorization} header
	 * @param chatApiUrl full URL of the chat-completion endpoint
	 */
	public HttpChatCompletionLlmProvider(String name, String apiKey, String chatApiUrl) {
		this.name = name;
		this.apiKey = apiKey;
		this.chatApiUrl = chatApiUrl;
	}

	@Override
	public String chat(String requestBody) {
		if (apiKey == null || apiKey.isEmpty()) {
			throw new RuntimeException("API key is not configured for LLM provider: " + name);
		}
		if (chatApiUrl == null || chatApiUrl.isEmpty()) {
			throw new RuntimeException(
			    "Chat API URL is not configured for LLM provider: " + name);
		}
		try {
			URL url = new URL(chatApiUrl);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Authorization", "Bearer " + apiKey);
			connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
			connection.setRequestProperty("Accept-Charset", "UTF-8");
			connection.setDoOutput(true);
			try (OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream())) {
				writer.write(requestBody);
			}
			StringBuilder response = new StringBuilder();
			try (BufferedReader br = new BufferedReader(
			    new InputStreamReader(connection.getInputStream(), "UTF-8"))) {
				String line;
				while ((line = br.readLine()) != null) {
					response.append(line);
				}
			}
			return response.toString();
		} catch (IOException e) {
			throw new RuntimeException("Chat request failed for LLM provider: " + name, e);
		}
	}

	@Override
	public String getName() {
		return name;
	}
}
