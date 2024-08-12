@file:OptIn(utils.res.InternalResourceApi::class)
@file:Suppress("INVISIBLE_MEMBER", "INVISIBLE_REFERENCE")
package utils.res

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * This is a platform-specific function that calculates and remembers a state.
 * For all platforms except a JS it is a blocking function.
 * On the JS platform it loads the state asynchronously and uses `getDefault` as an initial state value.
 */
@Composable
internal fun <T> rememberResourceState(
    key1: Any,
    getDefault: () -> T,
    block: suspend (ResourceEnvironment) -> T
): State<T> {
    val environment = LocalComposeEnvironment.current.rememberEnvironment()
    val scope = CoroutineScope(Dispatchers.Default)
    return remember(key1) {
        val mutableState = mutableStateOf(getDefault())
        scope.launch(start = CoroutineStart.UNDISPATCHED) {
            mutableState.value = block(environment)
        }
        mutableState
    }
}

/**
 * This is a platform-specific function that calculates and remembers a state.
 * For all platforms except a JS it is a blocking function.
 * On the JS platform it loads the state asynchronously and uses `getDefault` as an initial state value.
 */
@Composable
internal fun <T> rememberResourceState(
    key1: Any,
    key2: Any,
    getDefault: () -> T,
    block: suspend (ResourceEnvironment) -> T
): State<T> {
    val environment = LocalComposeEnvironment.current.rememberEnvironment()
    val scope = rememberCoroutineScope()
    return remember(key1, key2) {
        val mutableState = mutableStateOf(getDefault())
        scope.launch(start = CoroutineStart.UNDISPATCHED) {
            mutableState.value = block(environment)
        }
        mutableState
    }
}

/**
 * This is a platform-specific function that calculates and remembers a state.
 * For all platforms except a JS it is a blocking function.
 * On the JS platform it loads the state asynchronously and uses `getDefault` as an initial state value.
 */
@Composable
internal fun <T> rememberResourceState(
    key1: Any,
    key2: Any,
    key3: Any,
    getDefault: () -> T,
    block: suspend (ResourceEnvironment) -> T
): State<T> {
    val environment = LocalComposeEnvironment.current.rememberEnvironment()
    val scope = rememberCoroutineScope()
    return remember(key1, key2, key3) {
        val mutableState = mutableStateOf(getDefault())
        scope.launch(start = CoroutineStart.UNDISPATCHED) {
            mutableState.value = block(environment)
        }
        mutableState
    }
}