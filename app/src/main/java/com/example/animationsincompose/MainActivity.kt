package com.example.animationsincompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.example.animationsincompose.ui.theme.AnimationsInComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AnimationsInComposeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Screen()
                }
            }
        }
    }
}

@Composable
fun Screen(modifier: Modifier = Modifier) {

    Column(
        modifier = Modifier.padding(vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Showcase usage of AnimatedVisibility
        AnimatingAppearanceDisappearanceOfItemInColumn()
        Divider(thickness = 1.dp)
        // Showcase animating visibility changing alpha
        AnimateAlpha()
        Divider(thickness = 1.dp)
        // Showcase animating size using animateContentSize
        AnimateSize()
        Divider(thickness = 1.dp)
        // Showcase animating padding using animateDpAsState
        AnimatePadding()
    }

}

/**
 *  Animate the visibility of a text.
 *  When the visibility is false, the text is not composed
 */
@Composable
fun AnimatingAppearanceDisappearanceOfItemInColumn(){


    var visible by remember {
        mutableStateOf(true)
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Button(onClick = {visible = !visible}) {
            Text(
                text = "Toggle visibility of the text bellow (AnimatedVisibility)",
            )
        }

        AnimatedVisibility(visible = visible) {
            if (visible) {

                Text(
                    modifier = Modifier.padding(16.dp),
                    text = "I'm being animated when the visibility changes!")
            }
        }
    }
}

@Composable
fun AnimateAlpha(){
    var visible by remember {
        mutableStateOf(true)
    }
    val animatedAlpha by animateFloatAsState(
        targetValue = if (visible) 1.0f else 0f,
        label = "alpha"
    )
    Column(
        modifier = Modifier.padding(vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = {visible = !visible}) {
            Text(
                text = "Toggle visibility of the text bellow (Alpha)",
            )
        }
        Box(
            modifier = Modifier
                .size(200.dp)
                .graphicsLayer {
                    alpha = animatedAlpha
                }
                .clip(RoundedCornerShape(8.dp))
                .background(Color.Blue)
                .padding(16.dp)
        ) {
            Text(text = "I may disappear!")
        }

    }

}

@Composable
fun AnimateSize(){

    var expanded by remember { mutableStateOf(false) }
    Column(
        Modifier.padding( vertical = 16.dp )
    ) {
        Box(
            modifier = Modifier
                .background(Color.Blue)
                .animateContentSize()
                .height(if (expanded) 200.dp else 100.dp)
                .width(200.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    expanded = !expanded
                }

        ) {
            Text(text = "Tap here to ${ if(expanded) {"contract!"} else {"expand!"} }")
        }
    }
}

@Composable
fun AnimatePadding(){
    var toggled by remember {
        mutableStateOf(false)
    }
    val animatedPadding by animateDpAsState(
        if (toggled) {
            0.dp
        } else {
            20.dp
        },
        label = "padding"
    )
    Box(
        modifier = Modifier
            .height(200.dp)
            .fillMaxWidth()
            .padding(animatedPadding)
            .background(Color(0xff53D9A1))
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                toggled = !toggled
            }
    ) {
        Text(
            modifier = Modifier.padding(8.dp),
            text = "Tap to toggle padding",
            color = Color.Black
        )
    }
}
