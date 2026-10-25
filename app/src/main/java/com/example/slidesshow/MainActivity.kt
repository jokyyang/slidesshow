package com.example.slidesshow

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.slidesshow.ui.theme.SlidesshowTheme

data class Slide(@param:DrawableRes val image: Int, val caption: String)

val slides = listOf(
    Slide(R.drawable.pic1, "Cassiopeia A"),
    Slide(R.drawable.pic2, "Centaurus A"),
    Slide(R.drawable.pic3, "M104 galaxy"),
    Slide(R.drawable.pic4, "NGC 1672"),
    Slide(R.drawable.pic5, "ZW II 96")
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SlidesshowTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    SlideshowScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun SlideshowScreen(modifier: Modifier = Modifier) {
    var index by rememberSaveable { mutableIntStateOf(0) }
    var input by rememberSaveable { mutableStateOf("") }
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val slide = slides[index]

    Column(



        modifier = modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "picture ${index + 1}", fontSize = 16.sp)

        Spacer(Modifier.height(10.dp))

        Image(
            painter = painterResource(id = slide.image),
            contentDescription = slide.caption,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )

        Spacer(Modifier.height(8.dp))

        Text(
            text = slide.caption,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = { index = (index - 1 + slides.size) % slides.size },
                modifier = Modifier.weight(1f)
            ) { Text("Back") }

            Button(
                onClick = { index = (index + 1) % slides.size },
                modifier = Modifier.weight(1f)
            ) { Text("Next") }
        }

        Spacer(Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                value = input,
                onValueChange = { input = it.filter(Char::isDigit) },
                label = { Text("Picture number") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.weight(1f)
            )

            Button(onClick = {
                val number = input.toIntOrNull()
                if (number == null || number !in 1..slides.size) {
                    Toast.makeText(
                        context,
                        "Enter a number from 1 to ${slides.size}",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    index = number - 1
                    input = ""
                    focusManager.clearFocus()
                }
            }) { Text("Go") }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SlideshowPreview() {
    SlidesshowTheme {
        SlideshowScreen()
    }
}