package com.example.ui.navigation

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.AddCircleOutline
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.R
import com.example.data.model.PlatformCategory
import com.example.data.model.SavedItemType
import com.example.ui.components.AppCallingModalSheet
import com.example.ui.screens.AdsPromotionScreen
import com.example.ui.screens.BankingDigitalScreen
import com.example.ui.screens.BusinessDirectoryScreen
import com.example.ui.screens.GovtJobsResultsScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.LocalNewsScreen
import com.example.ui.screens.SavedScreen
import com.example.ui.screens.SearchScreen
import com.example.ui.screens.ServicesHubScreen
import com.example.ui.viewmodel.SupaulViewModel

object SupaulRoutes {
    const val HOME = "home"
    const val SEARCH = "search"
    const val SAVED = "saved"
    const val HUB = "hub"
    const val LOCAL_NEWS = "local_news"
    const val BANKING_DIGITAL = "banking_digital"
    const val GOVT_JOBS = "govt_jobs"
    const val BUSINESS_DIRECTORY = "business_directory"
    const val ADS_PROMOTION = "ads_promotion"
}

private data class BottomTabItem(
    val route: String,
    val labelRes: Int,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val testTag: String
)

private val mainTabs = listOf(
    BottomTabItem(
        route = SupaulRoutes.HOME,
        labelRes = R.string.nav_home,
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home,
        testTag = "nav_tab_home"
    ),
    BottomTabItem(
        route = SupaulRoutes.SEARCH,
        labelRes = R.string.nav_search,
        selectedIcon = Icons.Filled.Search,
        unselectedIcon = Icons.Outlined.Search,
        testTag = "nav_tab_search"
    ),
    BottomTabItem(
        route = SupaulRoutes.SAVED,
        labelRes = R.string.nav_saved,
        selectedIcon = Icons.Filled.Bookmark,
        unselectedIcon = Icons.Outlined.BookmarkBorder,
        testTag = "nav_tab_saved"
    ),
    BottomTabItem(
        route = SupaulRoutes.HUB,
        labelRes = R.string.nav_services,
        selectedIcon = Icons.Filled.AddCircle,
        unselectedIcon = Icons.Outlined.AddCircleOutline,
        testTag = "nav_tab_hub"
    )
)

@Composable
fun ApnaSupaulApp(
    viewModel: SupaulViewModel = viewModel()
) {
    val navController = rememberNavController()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    var showCallingSheet by remember { mutableStateOf(false) }

    LaunchedEffect(viewModel) {
        viewModel.uiMessages.collect { message ->
            snackbarHostState.showSnackbar(message)
        }
    }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: SupaulRoutes.HOME
    val showTopLevelNav = currentRoute in mainTabs.map { it.route }

    val navigateToCategory: (PlatformCategory) -> Unit = { category ->
        val route = when (category) {
            PlatformCategory.LOCAL_NEWS -> SupaulRoutes.LOCAL_NEWS
            PlatformCategory.BANKING_DIGITAL -> SupaulRoutes.BANKING_DIGITAL
            PlatformCategory.GOVT_JOBS_RESULTS -> SupaulRoutes.GOVT_JOBS
            PlatformCategory.BUSINESS_DIRECTORY -> SupaulRoutes.BUSINESS_DIRECTORY
            PlatformCategory.ADS_PROMOTION -> SupaulRoutes.ADS_PROMOTION
        }
        navController.navigate(route)
    }

    val navigateToTopTab: (String) -> Unit = { route ->
        navController.navigate(route) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val useNavRail = maxWidth >= 600.dp

        Scaffold(
            contentWindowInsets = WindowInsets.safeDrawing,
            snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
            bottomBar = {
                if (showTopLevelNav && !useNavRail) {
                    NavigationBar(
                        windowInsets = WindowInsets.navigationBars,
                        containerColor = MaterialTheme.colorScheme.surfaceContainerLowest
                    ) {
                        mainTabs.forEach { tab ->
                            val selected = currentRoute == tab.route
                            NavigationBarItem(
                                selected = selected,
                                onClick = { navigateToTopTab(tab.route) },
                                icon = {
                                    Icon(
                                        imageVector = if (selected) tab.selectedIcon else tab.unselectedIcon,
                                        contentDescription = stringResource(tab.labelRes)
                                    )
                                },
                                label = {
                                    Text(
                                        text = stringResource(tab.labelRes),
                                        maxLines = 1
                                    )
                                },
                                modifier = Modifier.testTag(tab.testTag)
                            )
                        }
                    }
                }
            }
        ) { innerPadding ->
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                if (showTopLevelNav && useNavRail) {
                    NavigationRail(
                        containerColor = MaterialTheme.colorScheme.surfaceContainerLowest,
                        modifier = Modifier
                            .fillMaxHeight()
                            .windowInsetsPadding(WindowInsets.navigationBars)
                    ) {
                        mainTabs.forEach { tab ->
                            val selected = currentRoute == tab.route
                            NavigationRailItem(
                                selected = selected,
                                onClick = { navigateToTopTab(tab.route) },
                                icon = {
                                    Icon(
                                        imageVector = if (selected) tab.selectedIcon else tab.unselectedIcon,
                                        contentDescription = stringResource(tab.labelRes)
                                    )
                                },
                                label = {
                                    Text(
                                        text = stringResource(tab.labelRes),
                                        maxLines = 1
                                    )
                                },
                                modifier = Modifier.testTag(tab.testTag)
                            )
                        }
                    }
                }

                NavHost(
                    navController = navController,
                    startDestination = SupaulRoutes.HOME,
                    modifier = Modifier.weight(1f)
                ) {
                    composable(SupaulRoutes.HOME) {
                        HomeScreen(
                            uiState = uiState,
                            onCategoryClick = navigateToCategory,
                            onSearchClick = { navigateToTopTab(SupaulRoutes.SEARCH) },
                            onSavedClick = { navigateToTopTab(SupaulRoutes.SAVED) },
                            onCallingClick = { showCallingSheet = true }
                        )
                    }

                    composable(SupaulRoutes.SEARCH) {
                        SearchScreen(
                            uiState = uiState,
                            query = searchQuery,
                            onQueryChange = viewModel::updateSearchQuery,
                            onCategoryNavigate = navigateToCategory
                        )
                    }

                    composable(SupaulRoutes.SAVED) {
                        SavedScreen(
                            uiState = uiState,
                            onRemoveBookmark = viewModel::removeBookmark,
                            onOpenCategory = navigateToCategory
                        )
                    }

                    composable(SupaulRoutes.HUB) {
                        ServicesHubScreen(
                            uiState = uiState,
                            onOpenCategory = navigateToCategory,
                            onCallingClick = { showCallingSheet = true }
                        )
                    }

                    composable(SupaulRoutes.LOCAL_NEWS) {
                        LocalNewsScreen(
                            uiState = uiState,
                            onBackClick = { navController.popBackStack() },
                            onSelectBlock = viewModel::selectBlock,
                            onToggleBookmark = { article ->
                                viewModel.toggleBookmark(
                                    itemKey = article.id,
                                    itemType = SavedItemType.NEWS,
                                    title = article.title,
                                    subtitle = "${article.blockName} • ${article.timeAgo}"
                                )
                            },
                            onCallingClick = { showCallingSheet = true }
                        )
                    }

                    composable(SupaulRoutes.BANKING_DIGITAL) {
                        BankingDigitalScreen(
                            uiState = uiState,
                            onBackClick = { navController.popBackStack() },
                            onToggleServiceBookmark = { service ->
                                viewModel.toggleBookmark(
                                    itemKey = service.id,
                                    itemType = SavedItemType.SERVICE,
                                    title = service.title,
                                    subtitle = "${service.category} • ${service.govtFee}"
                                )
                            },
                            onCallingClick = { showCallingSheet = true }
                        )
                    }

                    composable(SupaulRoutes.GOVT_JOBS) {
                        GovtJobsResultsScreen(
                            uiState = uiState,
                            onBackClick = { navController.popBackStack() },
                            onToggleBookmark = { job ->
                                viewModel.toggleBookmark(
                                    itemKey = job.id,
                                    itemType = SavedItemType.JOB,
                                    title = job.title,
                                    subtitle = "${job.department} • ${job.keyDateLabel}"
                                )
                            }
                        )
                    }

                    composable(SupaulRoutes.BUSINESS_DIRECTORY) {
                        BusinessDirectoryScreen(
                            uiState = uiState,
                            onBackClick = { navController.popBackStack() },
                            onSelectBlock = viewModel::selectBlock,
                            onToggleBookmark = { biz ->
                                viewModel.toggleBookmark(
                                    itemKey = biz.id,
                                    itemType = SavedItemType.BUSINESS,
                                    title = biz.name,
                                    subtitle = "${biz.category} • ${biz.blockName}"
                                )
                            },
                            onAddBusiness = viewModel::addBusinessListing
                        )
                    }

                    composable(SupaulRoutes.ADS_PROMOTION) {
                        AdsPromotionScreen(
                            uiState = uiState,
                            onBackClick = { navController.popBackStack() },
                            onSubmitAdInquiry = viewModel::submitAdInquiry,
                            onDeleteAdInquiry = viewModel::deleteAdInquiry,
                            onCallingClick = { showCallingSheet = true }
                        )
                    }
                }
            }
        }

        if (showCallingSheet) {
            AppCallingModalSheet(
                helplines = uiState.helplines,
                onDismiss = { showCallingSheet = false }
            )
        }
    }
}
