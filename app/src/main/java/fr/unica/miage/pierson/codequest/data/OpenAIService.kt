package fr.unica.miage.pierson.codequest.data

import android.content.Context
//import com.aallam.openai.api.OpenAI
import com.aallam.openai.api.chat.ChatCompletionRequest
import com.aallam.openai.api.chat.ChatMessage
import com.aallam.openai.api.chat.ChatRole
import com.aallam.openai.api.model.ModelId
import com.aallam.openai.client.OpenAI
import fr.unica.miage.pierson.codequest.BuildConfig
import kotlinx.coroutines.runBlocking

/**
 * Utility class for interacting with the OpenAI API.
 */
class OpenAIService {

    private val openAI: OpenAI

    init {
        val apiKey = BuildConfig.OPENAI_API_KEY
        openAI = OpenAI(token = apiKey)
    }

    /**
     * Generates quiz questions using the OpenAI API.
     *
     * @param topic The topic or subject for which questions should be generated.
     * @param numQuestions The number of questions to generate.
     * @return A list of generated questions as strings.
     */
    suspend fun generateQuestions(topic: String, numQuestions: Int): List<String> {
        val prompt = """
            Generate $numQuestions quiz questions about $topic. Each question should include:
            - A code snippet (if applicable)
            - A question text
            - Multiple-choice options (A, B, C, D)
            - The correct answer(s).
            Format each question clearly and separate them with a blank line.
        """.trimIndent()

        val chatCompletionRequest = ChatCompletionRequest(
            model = ModelId("gpt-3.5-turbo"),
            messages = listOf(
                ChatMessage(role = ChatRole.User, content = prompt)
            )
        )

        val response = openAI.chatCompletion(chatCompletionRequest)
        val generatedText = response.choices.firstOrNull()?.message?.content ?: ""
        return generatedText.split("\n\n").filter { it.isNotBlank() }
    }
}