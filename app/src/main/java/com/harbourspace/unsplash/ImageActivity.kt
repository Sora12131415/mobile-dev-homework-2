package com.harbourspace.unsplash

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.core.content.IntentCompat
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import com.harbourspace.unsplash.data.UnsplashItem
import com.harbourspace.unsplash.ui.theme.UnsplashTheme
import com.harbourspace.unsplash.utils.EXTRA_IMAGE

class ImageActivity: ComponentActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()

    val image = IntentCompat.getParcelableExtra(intent, EXTRA_IMAGE, UnsplashItem::class.java)

    setContent {
      UnsplashTheme {
        Scaffold {
          Column(
            modifier = Modifier
              .fillMaxSize()
              .padding(it)
          ) {
            val painter = rememberAsyncImagePainter(
              model = ImageRequest.Builder(LocalContext.current)
                .data(image?.urls?.regular)
                .build()
            )

            Image(
              painter = painter,
              modifier = Modifier.fillMaxSize(),
              contentScale = ContentScale.FillHeight,
              contentDescription = stringResource(R.string.description_sagrada)
            )
          }
        }
      }
    }
  }
}