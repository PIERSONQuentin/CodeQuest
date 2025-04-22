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
        Tu dois générer exactement $numQuestions questions de quiz sur "$topic".
        
        Chaque question doit impérativement respecter exactement la structure suivante, sans exception :
        
        Extrait de code :
        (si aucun extrait de code n'est nécessaire, indique explicitement : "Pas de code associé.")
        
        Question : Texte complet de la question
        
        A. Première option
        B. Deuxième option
        C. Troisième option
        D. Quatrième option
        
        Réponse correcte : Lettre correcte (A, B, C ou D uniquement)
        
        Tu dois obligatoirement séparer chaque question avec une ligne contenant uniquement ---.
        
        Voici un exemple exact à suivre :
        
        Extrait de code :
        val x = 10
        println(x)
        
        Question : Quel résultat affiche ce code ?
        
        A. 10
        B. Erreur
        C. null
        D. 0
        
        Réponse correcte : A
        
        ---
        
        Extrait de code :
        Pas de code associé.
        
        Question : Python est-il typé dynamiquement ?
        
        A. Oui
        B. Non
        C. Seulement avec annotations
        D. Seulement en Python 3
        
        Réponse correcte : A
        
        Ne génère aucun texte supplémentaire ou explication.
        """.trimIndent()


        val chatCompletionRequest = ChatCompletionRequest(
            model = ModelId("gpt-3.5-turbo"),
            messages = listOf(
                ChatMessage(role = ChatRole.User, content = prompt)
            )
        )

        val response = openAI.chatCompletion(chatCompletionRequest)
        val generatedText = response.choices.firstOrNull()?.message?.content ?: ""
        return generatedText.split("\n---\n").filter { it.isNotBlank() }
    }
}