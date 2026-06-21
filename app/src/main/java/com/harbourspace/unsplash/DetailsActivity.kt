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
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.IntentCompat
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import com.harbourspace.unsplash.api.UnsplashProvider
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

    val initialImage = IntentCompat.getParcelableExtra(intent, EXTRA_IMAGE, UnsplashItem::class.java)

    setContent {
      val detailedImageState = remember { mutableStateOf<UnsplashItem?>(null) }

      LaunchedEffect(initialImage?.id) {
        initialImage?.id?.let { id ->
          UnsplashProvider().fetchImageById(id).collect {
            detailedImageState.value = it
          }
        }
      }

      val image = detailedImageState.value ?: initialImage

      UnsplashTheme() {
        Scaffold(
          topBar = {
            TopAppBar(
              title = { },
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

            Box {
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
                contentDescription = image?.description ?: ""
              )

              Row(
                modifier = Modifier
                  .align(Alignment.BottomStart)
                  .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
              ) {
                Icon(
                  imageVector = Icons.Default.LocationOn,
                  contentDescription = null,
                  tint = Color.White
                )

                Text(
                  text = image?.user?.location ?: "-",
                  color = Color.White
                )
              }
            }

            Row(
              modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              val userPainter = rememberAsyncImagePainter(
                model = ImageRequest.Builder(LocalContext.current)
                  .data(image?.user?.profile_image?.medium)
                  .build()
              )

              Image(
                painter = userPainter,
                contentDescription = null,
                modifier = Modifier
                  .size(40.dp)
                  .clip(CircleShape)
              )

              Spacer(Modifier.width(10.dp))

              Text(
                text = image?.user?.name ?: "IDK",
                color = Color.Black
              )

              Spacer(Modifier.weight(1f))

              IconButton(onClick = {}) {
                Icon(
                  modifier = Modifier.size(40.dp),
                  imageVector = Icons.Default.Download,
                  contentDescription = null,
                  tint = Color.Black
                )
              }

              IconButton(onClick = {}) {
                Icon(
                  modifier = Modifier.size(40.dp),
                  imageVector = Icons.Default.Share,
                  contentDescription = null,
                  tint = Color.Black
                )
              }

              IconButton(onClick = {}) {
                Icon(
                  modifier = Modifier.size(40.dp),
                  imageVector = Icons.Default.Bookmark,
                  contentDescription = null,
                  tint = Color.Black
                )
              }
            }

            val modifier = Modifier
              .weight(1.0f)
              .padding(16.dp)

            Line(
              modifier = modifier,
              cell1ResId = R.string.image_camera,
              cell1Value = image?.exif?.model ?: "-",

              cell2ResId = R.string.image_aperture,
              cell2Value = image?.exif?.aperture ?: "-",
            )

            Line(
              modifier = modifier,
              cell1ResId = R.string.image_focal_length,
              cell1Value = image?.exif?.focal_length ?: "-",

              cell2ResId = R.string.image_shutter_speed,
              cell2Value = image?.exif?.exposure_time ?: "-",
            )

            Line(
              modifier = modifier,
              cell1ResId = R.string.image_iso,
              cell1Value = image?.exif?.iso?.toString() ?: "-",

              cell2ResId = R.string.image_dimensions,
              cell2Value = if (image?.width != null && image.height != null) "${image.width} x ${image.height}" else "-",
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
                  image?.views?.toString() ?: "-",
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
                  image?.downloads?.toString() ?: "-",
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
                  image?.likes?.toString() ?: "-",
                  modifier = Modifier,
                  horizontalArrangement = Arrangement.Center,
                  Alignment.CenterHorizontally
                )
              }
            }

            LazyRow(
              modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
              image?.tags?.let { tags ->
                items(tags) { tag ->
                  Button(onClick = { }, shape = CircleShape) {
                    Text(
                      text = tag.title ?: "-",
                      color = Color.White
                    )
                  }
                }
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