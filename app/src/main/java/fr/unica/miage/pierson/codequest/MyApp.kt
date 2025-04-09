package fr.unica.miage.pierson.codequest

import androidx.compose.material3.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import fr.unica.miage.pierson.codequest.screens.*
import fr.unica.miage.pierson.codequest.viewmodel.*
import kotlinx.serialization.Serializable

/**
 * Routes de l'application
 *
 * Chaque route est un objet ou une classe de données qui représente une page de l'application.
 * @see [QuizListRoute] pour la page de la liste des quizzes (page d'accueil de l'application)
 * @see [QuizDetailRoute] pour la page de détails d'un quiz
 * @see [QuizRoute] pour la page d'un quiz (questionnaire) -> génération avec API ChatGPT
 * @see [QuizResultRoute] pour la page de résultats d'un quiz
 */

@Serializable
object QuizListRoute
// Route vers la page de la liste des quizzes (page d'accueil)

@Serializable
data class QuizDetailRoute(val idQuiz: Int)
// Route vers la page de détails d'un quiz identifié par son id

@Serializable
data class QuizRoute(val idQuiz: Int)
// Route vers la page du quiz identifié par son id

@Serializable
data class QuizResultRoute(val idQuiz: Int)
// Route vers la page de résultats d'un quiz identifié par son id


/**
 * Fonction principale de l'application
 *
 * Cette fonction est appelée lors du démarrage de l'application.
 * Elle initialise le contrôleur de navigation et définit la structure de l'application.
 * Elle utilise un Scaffold pour gérer la barre de navigation inférieure et le contenu principal.
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyApp() {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val canGoBack = navController.previousBackStackEntry != null

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("CodeQuest") },
                actions = {
                    IconButton(onClick = { /* TODO: profil */ }) {
                        Icon(Icons.Default.Person, contentDescription = "Profil")
                    }
                    IconButton(onClick = { /* TODO: paramètres */ }) {
                        Icon(Icons.Default.Settings, contentDescription = "Paramètres")
                    }
                }
            )
        },
        bottomBar = {
            BottomAppBar {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Bouton retour à gauche si possible
                    if (canGoBack) {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Retour")
                        }
                    } else {
                        Spacer(modifier = Modifier.size(48.dp)) // Espace réservé si pas de bouton retour
                    }

                    // Bouton Accueil centré
                    val isOnQuizList = backStackEntry?.destination?.route?.contains("QuizListRoute") == true
                    if (isOnQuizList) {
                        IconButton(onClick = {
                            navController.navigate(QuizListRoute) {
                                popUpTo(navController.graph.startDestinationId) {
                                    inclusive = true
                                }
                            }
                        }) {
                            Icon(Icons.Default.Home, contentDescription = "Accueil")
                        }
                    } else {
                        Spacer(modifier = Modifier.size(48.dp)) // Espace réservé si pas de bouton accueil
                    }

                    // Espace visuel à droite pour équilibrer
                    Spacer(modifier = Modifier.size(48.dp))
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = QuizListRoute,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable<QuizListRoute> {
                val viewModel: QuizListViewModel = viewModel()
                QuizListScreen(
                    viewModel = viewModel,
                    onQuizSelected = { id ->
                        navController.navigate(QuizDetailRoute(id))
                    }
                )
            }

            /*
            composable<QuizDetailRoute> {
                val route = it.toRoute<QuizDetailRoute>()
                val viewModel: QuizDetailViewModel = viewModel()
                QuizDetailScreen(
                    idQuiz = route.idQuiz,
                    viewModel = viewModel,
                    onStartQuiz = {
                        navController.navigate(QuizRoute(route.idQuiz))
                    }
                )
            }

            composable<QuizRoute> {
                val route = it.toRoute<QuizRoute>()
                val viewModel: QuizViewModel = viewModel()
                QuizScreen(
                    idQuiz = route.idQuiz,
                    viewModel = viewModel,
                    onFinishQuiz = {
                        navController.navigate(QuizResultRoute(route.idQuiz))
                    }
                )
            }

            composable<QuizResultRoute> {
                val route = it.toRoute<QuizResultRoute>()
                val viewModel: QuizResultViewModel = viewModel()
                QuizResultScreen(
                    idQuiz = route.idQuiz,
                    viewModel = viewModel
                )
            }
            */
        }
    }
}