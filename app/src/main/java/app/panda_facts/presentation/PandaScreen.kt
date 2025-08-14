package app.panda_facts.presentation

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import app.panda_facts.presentation.viewmodel.PandaViewModel
import coil.compose.AsyncImage

@Composable
fun PandaScreen(viewModel: PandaViewModel = hiltViewModel()) {

    val state by viewModel.state.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFFFF8E1),
                        Color(0xFFFFF3E0)
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(Modifier.height(16.dp))

            Text(
                text = "Panda Facts",
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.ExtraBold),
                color = Color(0xFF2E2E2E)
            )

            Spacer(Modifier.height(12.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(12.dp),
                shape = RoundedCornerShape(24.dp)
            ) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    when {
                        state.isLoading -> CircularProgressIndicator()
                        state.error != null ->
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("Error: ${'$'}{state.error}")
                                Button(onClick = { viewModel.loadPanda() }) { Text("Retry") }
                            }

                        state.panda != null -> {
                            val panda = state.panda

                            Crossfade(
                                targetState = panda,
                                animationSpec = tween(durationMillis = 500)
                            ) { current ->
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier.padding(20.dp)
                                ) {
                                    val scale by animateFloatAsState(
                                        targetValue = 1.02f,
                                        animationSpec = spring()
                                    )
                                    if (current?.imageUrl != null) {
                                        Box(
                                            modifier = Modifier
                                                .size(240.dp)
                                                .shadow(12.dp, CircleShape)
                                                .background(Color.White, CircleShape),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            AsyncImage(
                                                model = current.imageUrl,
                                                contentDescription = "Panda",
                                                modifier = Modifier
                                                    .size(220.dp)
                                                    .scale(scale)
                                                    .clip(CircleShape)
                                            )
                                        }
                                    }

                                    Spacer(Modifier.height(20.dp))

                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .heightIn(min = 100.dp, max = 300.dp)
                                            .verticalScroll(rememberScrollState())
                                            .padding(16.dp)
                                    ) {
                                        Text(
                                            text = current?.fact.toString(),
                                            fontSize = 20.sp,
                                            textAlign = TextAlign.Center,
                                            modifier = Modifier.padding(horizontal = 12.dp),
                                            color = Color(0xFF263238)
                                        )
                                    }

                                    Spacer(Modifier.height(24.dp))

                                    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                                        Button(onClick = { viewModel.loadPanda() }) {
                                            Text(text = "New Panda")
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                Spacer(Modifier.height(8.dp))

                Text(
                    text = "Cute pandas, curious facts 🐼",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
                Spacer(Modifier.height(12.dp))

            }

        }
    }

}