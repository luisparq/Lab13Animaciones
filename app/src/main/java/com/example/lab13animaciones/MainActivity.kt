package com.example.lab13animaciones

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    EjercicioVisibilidad()
                }
            }
        }
    }
}

@Composable
private fun EjercicioVisibilidad() {
    var visible by rememberSaveable { mutableStateOf(false) }

    val fondo = Color(0xFF0B1220)
    val acento = Color(0xFF5EEAD4)
    val textoSecundario = Color(0xFF94A3B8)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(fondo)
            .safeDrawingPadding()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(
            space = 18.dp,
            alignment = Alignment.CenterVertically
        )
    ) {
        Text(
            text = "EJERCICIO 1 · VISIBILIDAD",
            color = acento,
            style = MaterialTheme.typography.labelLarge
        )

        Text(
            text = "Cámara de energía",
            color = Color.White,
            style = MaterialTheme.typography.headlineMedium
        )

        CuadroAnimado(visible = visible)

        Text(
            text = if (visible) "NÚCLEO ACTIVO" else "NÚCLEO EN REPOSO",
            color = if (visible) acento else textoSecundario,
            style = MaterialTheme.typography.labelLarge
        )

        Button(
            onClick = { visible = !visible },
            modifier = Modifier.fillMaxWidth(),
            colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                containerColor = acento,
                contentColor = fondo
            )
        ) {
            Text(if (visible) "Desactivar núcleo" else "Activar núcleo")
        }
    }
}

@Composable
private fun CuadroAnimado(visible: Boolean) {
    val formaCamara = RoundedCornerShape(24.dp)
    val formaNucleo = RoundedCornerShape(20.dp)
    val acento = Color(0xFF5EEAD4)

    Box(
        modifier = Modifier
            .size(208.dp)
            .background(Color(0xFF111D30), formaCamara)
            .border(1.dp, Color(0xFF334155), formaCamara),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(120.dp)
                .border(1.dp, Color(0xFF475569), formaNucleo)
        )

        AnimatedVisibility(
            visible = visible,
            enter = fadeIn(animationSpec = tween(500)),
            exit = fadeOut(animationSpec = tween(500))
        ) {
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .background(Color(0xFF164E4A), formaNucleo)
                    .border(2.dp, acento, formaNucleo),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(acento, RoundedCornerShape(12.dp))
                )
            }
        }
    }
}