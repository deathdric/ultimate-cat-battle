package org.deathdric.ultimatecatbattle

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Surface
import org.deathdric.ultimatecatbattle.ui.UltimateCatBattleApp
import org.deathdric.ultimatecatbattle.ui.theme.UltimateCatRumbleTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UltimateCatRumbleTheme {
                Surface {
                    UltimateCatBattleApp()
                }
            }
        }
    }

    @Deprecated(
        "This method has been deprecated in favor of using the\n      {@link OnBackPressedDispatcher} via {@link #getOnBackPressedDispatcher()}.\n      The OnBackPressedDispatcher controls how back button events are dispatched\n      to one or more {@link OnBackPressedCallback} objects.",
        ReplaceWith("moveTaskToBack(true)")
    )
    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {
        // Some Android devices close the app when the back button is pressed
        // as we don't use navigation, this should ensure that we will have the same
        // behavior everywhere (as in 'move the app to background')
        moveTaskToBack(true)

    }
}