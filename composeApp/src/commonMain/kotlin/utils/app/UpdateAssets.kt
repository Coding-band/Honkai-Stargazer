package utils.app

import androidx.compose.runtime.Composable
import com.russhwolf.settings.Settings

private var localCommit = Settings().getString("localCommit", "")

@Composable
fun UpdateAssetsPopup() {
    //First, check what git commit is the user using

    //Then, check if the user has the latest commit
    //If the user has the latest commit, do nothing

    //If not, show a popup to the user to update the app


}

/**
 * This function will update the assets of the app
 */
fun UpdateAssetsProcess() {

}

fun checkIsNeedUpdateAssets(): Boolean {
    return false
}