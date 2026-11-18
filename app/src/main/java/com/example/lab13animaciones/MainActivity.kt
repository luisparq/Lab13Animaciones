package com.example.lab13animaciones

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
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
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.offset
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.togetherWith

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                var ejercicioActual by rememberSaveable { mutableStateOf(1) }

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFF0B1220)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .safeDrawingPadding(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(modifier = Modifier.weight(1f)) {
                            when (ejercicioActual) {
                                1 -> EjercicioVisibilidad()
                                2 -> EjercicioColor()
                                3 -> EjercicioTamanoPosicion()
                                4 -> EjercicioContenido()
                                5 -> EjercicioFinal()
                            }
                        }

                        Button(
                            onClick = {
                                ejercicioActual = if (ejercicioActual == 5) 1 else ejercicioActual + 1
                            },
                            modifier = Modifier.padding(bottom = 16.dp)
                        ) {
                            Text(
                                if (ejercicioActual == 5) "Volver al ejercicio 1"
                                else if (ejercicioActual == 4) "Ir al ejercicio final"
                                else "Ir al ejercicio ${ejercicioActual + 1}"
                            )
                        }
                    }
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

@Composable
private fun EjercicioColor() {
    var modoVerde by rememberSaveable { mutableStateOf(false) }
    var usarResorte by rememberSaveable { mutableStateOf(false) }

    val colorNucleo by animateColorAsState(
        targetValue = if (modoVerde) Color(0xFF4ADE80) else Color(0xFF60A5FA),
        animationSpec = if (usarResorte) {
            spring(dampingRatio = 0.45f, stiffness = 35f)
        } else {
            tween(durationMillis = 1800, easing = LinearEasing)
        },
        label = "Color del núcleo"
    )

    val formaCamara = RoundedCornerShape(24.dp)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(
            space = 16.dp,
            alignment = Alignment.CenterVertically
        )
    ) {
        Text(
            text = "Control del núcleo",
            color = Color.White,
            style = MaterialTheme.typography.headlineMedium
        )

        Box(
            modifier = Modifier
                .size(184.dp)
                .background(Color(0xFF111D30), formaCamara)
                .border(1.dp, Color(0xFF334155), formaCamara),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(108.dp)
                    .background(colorNucleo, RoundedCornerShape(20.dp))
            )
        }

        Button(
            onClick = { modoVerde = !modoVerde },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (modoVerde) "Cambiar a azul" else "Cambiar a verde")
        }

        Button(
            onClick = { usarResorte = !usarResorte },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (usarResorte) "Animación: Resorte" else "Animación: Duración fija")
        }
    }
}

@Composable
private fun EjercicioTamanoPosicion() {
    var expandido by rememberSaveable { mutableStateOf(false) }
    var moverFondo by rememberSaveable { mutableStateOf(true) }

    val tamano by animateDpAsState(
        targetValue = if (expandido) 104.dp else 64.dp,
        animationSpec = tween(durationMillis = 800),
        label = "Tamaño del núcleo"
    )
    val desplazamiento by animateDpAsState(
        targetValue = if (expandido) 84.dp else 0.dp,
        animationSpec = tween(durationMillis = 800),
        label = "Posición del núcleo"
    )

    val acento = Color(0xFF5EEAD4)
    val formaNucleo = RoundedCornerShape(16.dp)
    val formaCamara = RoundedCornerShape(24.dp)

    val modificadorNucleo = if (moverFondo) {
        Modifier.offset(x = desplazamiento).background(acento, formaNucleo)
    } else {
        Modifier.background(acento, formaNucleo).offset(x = desplazamiento)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(
            space = 16.dp,
            alignment = Alignment.CenterVertically
        )
    ) {
        Text(
            text = "Traslado del núcleo",
            color = Color.White,
            style = MaterialTheme.typography.headlineMedium
        )

        Box(
            modifier = Modifier
                .size(width = 220.dp, height = 152.dp)
                .background(Color(0xFF111D30), formaCamara)
                .border(1.dp, Color(0xFF334155), formaCamara)
                .padding(12.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Box(
                modifier = modificadorNucleo.size(tamano),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .background(Color(0xFF0F766E), RoundedCornerShape(6.dp))
                )
            }
        }

        Button(
            onClick = { expandido = !expandido },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (expandido) "Volver al origen" else "Mover y ampliar")
        }

        Button(
            onClick = { moverFondo = !moverFondo },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (moverFondo) "Orden: offset → fondo" else "Orden: fondo → offset")
        }
    }
}

private enum class EstadoDiagnostico(
    val titulo: String,
    val mensaje: String,
    val color: Color
) {
    CARGANDO(
        "Cargando",
        "Analizando el núcleo de energía…",
        Color(0xFF60A5FA)
    ),
    CONTENIDO(
        "Contenido",
        "Diagnóstico completo. Núcleo estable.",
        Color(0xFF5EEAD4)
    ),
    ERROR(
        "Error",
        "Se perdió la conexión con el núcleo.",
        Color(0xFFFDA4AF)
    )
}

@Composable
private fun EjercicioContenido() {
    var estado by rememberSaveable { mutableStateOf(EstadoDiagnostico.CARGANDO) }
    val formaPanel = RoundedCornerShape(24.dp)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(
            space = 16.dp,
            alignment = Alignment.CenterVertically
        )
    ) {
        Text(
            text = "Diagnóstico del núcleo",
            color = Color.White,
            style = MaterialTheme.typography.headlineSmall
        )

        AnimatedContent(
            targetState = estado,
            modifier = Modifier.fillMaxWidth(),
            transitionSpec = {
                fadeIn(animationSpec = tween(durationMillis = 550, delayMillis = 150))
                    .togetherWith(fadeOut(animationSpec = tween(durationMillis = 250)))
            },
            label = "Estado del diagnóstico"
        ) { estadoMostrado ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF111D30), formaPanel)
                    .border(1.dp, estadoMostrado.color, formaPanel)
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = estadoMostrado.titulo,
                    color = estadoMostrado.color,
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = estadoMostrado.mensaje,
                    color = Color(0xFFCBD5E1)
                )
            }
        }

        Button(
            onClick = {
                estado = when (estado) {
                    EstadoDiagnostico.CARGANDO -> EstadoDiagnostico.CONTENIDO
                    EstadoDiagnostico.CONTENIDO -> EstadoDiagnostico.ERROR
                    EstadoDiagnostico.ERROR -> EstadoDiagnostico.CARGANDO
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Siguiente estado")
        }
    }
}


@Composable
private fun EjercicioFinal() {
    var cargas by rememberSaveable { mutableStateOf(0) }
    var fallos by rememberSaveable { mutableStateOf(0) }
    val completado = cargas == 3
    val partidaTerminada = completado || fallos == 3
    val pulso = if (partidaTerminada) {
        null
    } else {
        key(cargas, fallos) {
            val reloj = rememberInfiniteTransition(label = "Reloj del reactor")
            reloj.animateFloat(
                initialValue = 0f,
                targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    animation = tween(1600 - cargas * 200, easing = LinearEasing),
                    repeatMode = RepeatMode.Reverse
                ),
                label = "Posición del pulso"
            )
        }
    }
    val mensaje = when {
        completado -> "¡Reactor activado!"
        partidaTerminada -> "Reactor bloqueado"
        else -> "Cargas $cargas/3 · Intentos ${3 - fallos}/3"
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
    ) {
        Text(
            "Reactor en marcha",
            color = Color.White,
            style = MaterialTheme.typography.headlineSmall
        )
        Text(
            "Pulsa Sincronizar cuando la marca blanca esté en la zona verde.",
            color = Color(0xFFCBD5E1)
        )
        PistaDelReactor(cargas)

        BoxWithConstraints(
            modifier = Modifier.size(width = 234.dp, height = 26.dp)
                .background(Color(0xFF111D30), RoundedCornerShape(8.dp))
                .padding(horizontal = 6.dp)
        ) {
            val recorrido = maxWidth
            Box(
                modifier = Modifier.align(Alignment.CenterStart)
                    .offset(x = recorrido * 0.40f)
                    .size(width = recorrido * 0.20f, height = 18.dp)
                    .background(Color(0xFF4ADE80), RoundedCornerShape(4.dp))
            )
            if (pulso != null) {
                Box(
                    modifier = Modifier.align(Alignment.CenterStart)
                        .offset(x = recorrido * pulso.value - 2.dp)
                        .size(width = 4.dp, height = 22.dp)
                        .background(Color.White, RoundedCornerShape(2.dp))
                )
            }
        }

        AnimatedContent(
            targetState = mensaje,
            transitionSpec = { fadeIn(tween(350)).togetherWith(fadeOut(tween(200))) },
            label = "Progreso de la misión"
        ) { texto ->
            Text(
                text = texto,
                color = when (texto) {
                    "¡Reactor activado!" -> Color(0xFF4ADE80)
                    "Reactor bloqueado" -> Color(0xFFFDA4AF)
                    else -> Color.White
                },
                style = MaterialTheme.typography.titleMedium
            )
        }

        Button(
            onClick = {
                if (cargas < 3 && fallos < 3) {
                    if ((pulso?.value ?: -1f) in 0.40f..0.60f) cargas += 1 else fallos += 1
                }
            },
            enabled = !partidaTerminada,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Sincronizar")
        }
        AnimatedVisibility(
            visible = partidaTerminada,
            enter = fadeIn(tween(600)),
            exit = fadeOut(tween(250))
        ) {
            Button(
                onClick = {
                    cargas = 0
                    fallos = 0
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Reiniciar misión")
            }
        }
    }
}

@Composable
private fun PistaDelReactor(cargas: Int) {
    val tamano by animateDpAsState(
        targetValue = (42 + cargas * 8).dp,
        animationSpec = tween(550),
        label = "Tamaño del núcleo"
    )
    val color by animateColorAsState(
        targetValue = when (cargas) {
            0 -> Color(0xFF60A5FA)
            1 -> Color(0xFF38BDF8)
            2 -> Color(0xFF2DD4BF)
            else -> Color(0xFF4ADE80)
        },
        animationSpec = tween(550),
        label = "Color del núcleo"
    )
    val formaNucleo = RoundedCornerShape(16.dp)
    val formaPista = RoundedCornerShape(24.dp)

    BoxWithConstraints(
        modifier = Modifier
            .size(width = 234.dp, height = 104.dp)
            .background(Color(0xFF111D30), formaPista)
            .border(1.dp, Color(0xFF334155), formaPista)
            .padding(horizontal = 12.dp)
    ) {
        val distancia = (maxWidth - 66.dp).coerceAtLeast(0.dp)
        val desplazamiento by animateDpAsState(
            targetValue = distancia * (cargas / 3f),
            animationSpec = tween(550),
            label = "Posición del núcleo"
        )

        Box(
            modifier = Modifier.align(Alignment.CenterEnd)
                .size(66.dp)
                .border(2.dp, Color(0xFF64748B), formaNucleo),
            contentAlignment = Alignment.Center
        ) {
            Text("META", color = Color(0xFFCBD5E1), style = MaterialTheme.typography.labelSmall)
        }
        Box(
            modifier = Modifier.align(Alignment.CenterStart)
                .offset(x = desplazamiento)
                .size(tamano)
                .background(color, formaNucleo)
        )
    }
}
