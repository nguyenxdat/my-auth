package com.datnx.router.models

import kotlinx.serialization.Serializable

/**
 * Serializable data models for navigation arguments
 */

@Serializable
data class BirthDayModel(
    val id: String,
    val name: String,
    val date: String,
    val age: Int? = null,
    val avatar: String? = null
)

@Serializable
data class NewsDetailScreenArgs(
    val newsId: String,
    val title: String,
    val content: String,
    val imageUrl: String? = null,
    val publishedDate: String? = null,
    val author: String? = null
)

@Serializable
data class TransferDetailArgs(
    val transferId: String,
    val amount: Double? = null,
    val fromAccount: String? = null,
    val toAccount: String? = null,
    val status: String? = null
)

@Serializable
data class ProfileArgs(
    val userId: String,
    val isEditMode: Boolean = false,
    val tab: String? = null
)

@Serializable
data class ProductDetailArgs(
    val productId: String,
    val categoryId: String? = null,
    val source: String? = null
)

/**
 * Navigation result data class
 */
@Serializable
data class NavigationResult<T>(
    val success: Boolean,
    val data: T? = null,
    val error: String? = null
)

/**
 * Route parameters data class
 */
data class RouteParams(
    val routeName: String,
    val arguments: Map<String, Any> = emptyMap(),
    val navOptions: NavOptions? = null
)

/**
 * Navigation options
 */
data class NavOptions(
    val popUpTo: String? = null,
    val inclusive: Boolean = false,
    val saveState: Boolean = false,
    val restoreState: Boolean = false,
    val singleTop: Boolean = false
)

/**
 * Screen transition types
 */
enum class TransitionType {
    SLIDE_LEFT,
    SLIDE_RIGHT,
    SLIDE_UP,
    SLIDE_DOWN,
    FADE,
    SCALE,
    NONE
}

/**
 * Presentation styles for modal screens
 */
enum class PresentationStyle {
    SHEET,
    FULL_SCREEN,
    DIALOG,
    BOTTOM_SHEET
}
