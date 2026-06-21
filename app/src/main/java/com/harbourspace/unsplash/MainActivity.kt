package com.harbourspace.unsplash

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.harbourspace.unsplash.data.UnsplashItem
import com.harbourspace.unsplash.ui.MainScreen
import com.harbourspace.unsplash.ui.UnsplashViewModel
import com.harbourspace.unsplash.ui.theme.UnsplashTheme
import com.harbourspace.unsplash.utils.EXTRA_IMAGE

private const val TAG = "MainActivity"

class MainActivity : ComponentActivity() {

  private val unsplashViewModel: UnsplashViewModel by viewModels()

  @OptIn(ExperimentalMaterial3Api::class)
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    Toast.makeText(this@MainActivity, "onCreate", Toast.LENGTH_SHORT).show()

    Log.d(TAG, "onCreate")

    val openDetails = { image: UnsplashItem ->
      val intent = Intent(this, DetailsActivity::class.java)
      intent.putExtra(EXTRA_IMAGE, image)
      startActivity(intent)
    }

    enableEdgeToEdge()
    setContent {
      UnsplashTheme {
        Scaffold(
          topBar = {
            CenterAlignedTopAppBar(
              title = {
                Text(
                  text = stringResource(R.string.app_name)
                )
              }
            )
          },
          modifier = Modifier.fillMaxSize()
        ) { innerPadding ->
          Column(
            modifier = Modifier.padding(innerPadding),
          ) {
            MainScreen(
              unsplashViewModel = unsplashViewModel,
              openDetails = openDetails
            )
          }
        }
      }
    }
  }
}