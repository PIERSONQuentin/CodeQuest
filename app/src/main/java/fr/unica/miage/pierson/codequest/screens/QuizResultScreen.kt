package fr.unica.miage.pierson.codequest.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import fr.unica.miage.pierson.codequest.QuizReviewRoute
import fr.unica.miage.pierson.codequest.data.DataSource
import fr.unica.miage.pierson.codequest.viewmodel.QuizViewModel

/**
 * Écran de résultat du quiz.
 *
 * Affiche le score final, un message personnalisé et un bouton pour revenir à l'accueil.
 *
 * @param idQuiz L'identifiant du quiz joué.
 * @param viewModel Le ViewModel contenant les résultats du quiz.
 * @param navController Le contrôleur de navigation pour revenir à l'accueil.
 */
@Composable
fun QuizResultScreen(
    idQuiz: Int,
    viewModel: QuizViewModel,
    navController: NavController
) {
    val score = viewModel.getScore()
    val total = viewModel.getTotal()
    val quiz = DataSource().loadQuiz(idQuiz)

    val message = when {
        score == total -> "Excellent travail ! 🎉"
        score >= total / 2 -> "Bien joué, mais tu peux faire encore mieux !"
        else -> "Ne te décourage pas, continue à t'entraîner 💪"
    }

    val color = when {
        score == total -> MaterialTheme.colorScheme.primary
        score >= total / 2 -> MaterialTheme.colorScheme.tertiary
        else -> MaterialTheme.colorScheme.error
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = quiz.title,
                style = MaterialTheme.typography.headlineSmall
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Score : $score / $total",
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = message,
                color = color,
                style = MaterialTheme.typography.bodyLarge
            )
        }

        Column {
            Button(
                onClick = {
                    navController.navigate(QuizReviewRoute(idQuiz))
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            ) {
                Text("Voir les réponses")
            }

            Button(
                onClick = {
                    navController.navigate(fr.unica.miage.pierson.codequest.QuizListRoute) {
                        popUpTo(navController.graph.startDestinationId) {
                            inclusive = true
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Retour à l'accueil")
            }
        }
    }
}