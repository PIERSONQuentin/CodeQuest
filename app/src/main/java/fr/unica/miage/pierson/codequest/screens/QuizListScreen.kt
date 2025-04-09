package fr.unica.miage.pierson.codequest.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.unica.miage.pierson.codequest.R
import fr.unica.miage.pierson.codequest.data.Quiz
import fr.unica.miage.pierson.codequest.viewmodel.QuizListViewModel

/**
 * Écran de liste des quiz.
 *
 * @param viewModel Le ViewModel associé à l'écran.
 * @param onQuizSelected Callback appelé lorsque l'utilisateur sélectionne un quiz.
 *
 * Cet écran affiche une liste de quiz avec une barre de recherche en haut.
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizListScreen(
    viewModel: QuizListViewModel,
    onQuizSelected: (Int) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {

        // Supprimé les icônes, barre de recherche seule
        TextField(
            value = viewModel.searchQuery,
            onValueChange = viewModel::onSearchChange,
            placeholder = { Text("Rechercher un quiz...") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            items(viewModel.filteredQuizzes) { quiz ->
                QuizCard(quiz = quiz, onClick = {
                    val index = viewModel.filteredQuizzes.indexOf(quiz)
                    onQuizSelected(index)
                })
            }
        }
    }
}

/**
 * Carte représentant un quiz.
 *
 * @param quiz Le quiz à afficher.
 * @param onClick Callback appelé lorsque l'utilisateur clique sur la carte.
 *
 * Cette carte affiche le titre et la description du quiz, ainsi qu'une image associée.
 */

@Composable
fun QuizCard(quiz: Quiz, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            Image(
                painter = painterResource(id = quiz.image),
                contentDescription = "Quiz image",
                modifier = Modifier
                    .size(72.dp)
                    .padding(end = 16.dp)
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterVertically)
            ) {
                Text(
                    text = quiz.title,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = quiz.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row {
                    repeat(5) { index ->
                        val filled = index < quiz.note
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            tint = if (filled) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
        }
    }
}

/**
 * Aperçu de la carte de quiz.
 */

@Preview(showBackground = true)
@Composable
fun PreviewQuizCard() {
    val fakeQuiz = Quiz(
        title = "Apprendre Python",
        description = "Un quiz pour tester vos bases en python.",
        note = 4,
        image = R.drawable.python, // ou n'importe quelle image de ton projet
        type = "QCM"
    )
    QuizCard(quiz = fakeQuiz, onClick = {})
}