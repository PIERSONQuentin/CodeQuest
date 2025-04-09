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
    val quizViewModel: QuizViewModel = viewModel()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    Scaffold(
        topBar = {
            // Ne pas afficher de topBar sur l'écran des résultats
            if (currentRoute?.contains("QuizResultRoute") != true) {
                TopAppBar(
                    title = { Text("CodeQuest") },
                    navigationIcon = {
                        when {
                            // Sur QuizDetail -> flèche retour
                            currentRoute?.contains("QuizDetailRoute") == true -> {
                                IconButton(onClick = { navController.popBackStack() }) {
                                    Icon(Icons.Default.ArrowBack, contentDescription = "Retour")
                                }
                            }

                            // Sur Quiz uniquement -> bouton accueil
                            currentRoute?.contains("QuizRoute") == true -> {
                                IconButton(onClick = {
                                    navController.navigate(QuizListRoute) {
                                        popUpTo(navController.graph.startDestinationId) {
                                            inclusive = true
                                        }
                                    }
                                }) {
                                    Icon(Icons.Default.Home, contentDescription = "Accueil")
                                }
                            }

                            else -> null
                        }
                    },
                    actions = {
                        IconButton(onClick = { /* TODO: profil */ }) {
                            Icon(Icons.Default.Person, contentDescription = "Profil")
                        }
                        IconButton(onClick = { /* TODO: paramètres */ }) {
                            Icon(Icons.Default.Settings, contentDescription = "Paramètres")
                        }
                    }
                )
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
                QuizScreen(
                    idQuiz = route.idQuiz,
                    viewModel = quizViewModel,
                    onFinishQuiz = {
                        navController.navigate(QuizResultRoute(route.idQuiz))
                    }
                )
            }

            composable<QuizResultRoute> {
                val route = it.toRoute<QuizResultRoute>()
                QuizResultScreen(
                    idQuiz = route.idQuiz,
                    viewModel = quizViewModel,
                    navController = navController
                )
            }


            /*
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
                val viewModel: QuizViewModel = viewModel()
                QuizResultScreen(
                    idQuiz = route.idQuiz,
                    viewModel = viewModel,
                    navController = navController
                )
            }
            */
        }
    }
}