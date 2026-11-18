package br.com.alexsouza.vittacare

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.remember
import br.com.alexsouza.vittacare.ui.CareShiftScreen
import br.com.alexsouza.vittacare.ui.theme.VittaCareTheme
import br.com.alexsouza.vittacare.viewmodel.CareShiftViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VittaCareTheme {
                val viewModel = remember { CareShiftViewModel() }
                CareShiftScreen(viewModel = viewModel)
            }
        }
    }
}
