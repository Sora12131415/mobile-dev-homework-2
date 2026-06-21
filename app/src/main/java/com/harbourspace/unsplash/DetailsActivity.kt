package com.harbourspace.unsplash

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.IntentCompat
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import com.harbourspace.unsplash.data.UnsplashItem
import com.harbourspace.unsplash.ui.theme.UnsplashTheme
import com.harbourspace.unsplash.utils.EXTRA_IMAGE

class DetailsActivity: ComponentActivity() {

  @OptIn(ExperimentalMaterial3Api::class)
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()

    val backAction = {
      finish()
    }

    val image = IntentCompat.getParcelableExtra(intent, EXTRA_IMAGE, UnsplashItem::class.java)

    setContent {
      UnsplashTheme() {
        Scaffold(
          topBar = {
            TopAppBar(
              title = {
                Text(stringResource(R.string.description_sagrada))
              },
              navigationIcon = {
                IconButton(onClick = {
                  backAction()
                }) {
                  Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.description_back)
                  )
                }
              }
            )
          }
        ) { innerPadding->
          Column(
            modifier = Modifier.padding(innerPadding)
          ) {
            val painter = rememberAsyncImagePainter(
              model = ImageRequest.Builder(LocalContext.current)
                .data(image?.urls?.regular)
                .build()
            )

            Image(
              painter = painter,
              modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clickable(
                  onClick = {
                    val intent = Intent(this@DetailsActivity, ImageActivity::class.java)
                    intent.putExtra(EXTRA_IMAGE, image)
                    startActivity(intent)
                  }
                ),
              contentScale = ContentScale.FillWidth,
              contentDescription = stringResource(R.string.description_sagrada)
            )

            val modifier = Modifier
              .weight(1.0f)
              .padding(16.dp)

            Line(
              modifier = modifier,
              cell1ResId = R.string.image_camera,
              cell1Value = "NIKON D3200",

              cell2ResId = R.string.image_aperture,
              cell2Value = "f 1/50",
            )

            Line(
              modifier = modifier,
              cell1ResId = R.string.image_focal_length,
              cell1Value = "18.0mm",

              cell2ResId = R.string.image_shutter_speed,
              cell2Value = "1/125s",
            )

            Line(
              modifier = modifier,
              cell1ResId = R.string.image_iso,
              cell1Value = "100",

              cell2ResId = R.string.image_dimensions,
              cell2Value = "3096 x 4882",
            )

            HorizontalDivider(
              modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
              thickness = 2.dp,
              color = Color.LightGray
            )

            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.Absolute.SpaceEvenly
            ) {
              Row(
                modifier = Modifier.weight(1.0f)
              ) {
                Cell(
                  R.string.image_views,
                  "100",
                  modifier = Modifier,
                  horizontalArrangement = Arrangement.Center,
                  Alignment.CenterHorizontally
                )
              }

              Row(
                modifier = Modifier.weight(1.0f)
              ) {
                Cell(
                  R.string.image_downloads,
                  "150",
                  modifier = Modifier,
                  horizontalArrangement = Arrangement.Center,
                  Alignment.CenterHorizontally
                )
              }

              Row(
                modifier = Modifier.weight(1.0f)
              ) {
                Cell(
                  R.string.image_likes,
                  "4000",
                  modifier = Modifier,
                  horizontalArrangement = Arrangement.Center,
                  Alignment.CenterHorizontally
                )
              }
            }
          }
        }
      }
    }
  }

  @Composable
  fun Line(
    modifier: Modifier,
    @StringRes cell1ResId: Int,
    cell1Value: String,

    @StringRes cell2ResId: Int,
    cell2Value: String,
  ) {
    Row(
      modifier = Modifier.fillMaxWidth()
    ) {
      Cell(
        resId = cell1ResId,
        value = cell1Value,
        modifier = modifier
      )

      Cell(
        resId = cell2ResId,
        value = cell2Value,
        modifier = modifier
      )
    }
  }

  @Composable
  fun Cell(
    @StringRes resId: Int,
    value: String,
    modifier: Modifier,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start
  ) {
    Row(
      modifier = modifier.fillMaxWidth(),
      horizontalArrangement = horizontalArrangement
    ) {
      Column(
        horizontalAlignment = horizontalAlignment
      ) {
        Text(
          text = stringResource(resId),
          fontWeight = FontWeight.Bold
        )

        Text(
          text = value
        )
      }
    }
  }
}