package com.example.html

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat
import com.example.html.ui.theme.HtmlTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            HtmlTheme {
                val text =
                    rememberSaveable { mutableStateOf("<div><p><b>Пол: женский</b></p><br><a href=\"https://www.e1.ru\" style=\"font-family: helvetica; font-weight: bold\">Материалы: </a><br><br><table><row><col>123123</col><col>456789</col></row><row><col>aaaaaa</col><col>bbbbbb</col></row></table>Основная ткань: 60% хлопок, 40% эластан<br>Подкладка: 100% полиэстер</p><p>Уход:<br>Химчистка</p><ol><li>qwe</li><li>asd</li><li>zxc</li></ol></div>") }
                val focusManager = LocalFocusManager.current

                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(0.45f)
                            .background(Color.Transparent)
                    ) {
                        TextField(
                            value = text.value,
                            onValueChange = {
                                text.value = it
                            },
                            modifier = Modifier.align(Alignment.Center),
                            label = { Text("Введите html текст") },
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() })
                        )
                        Button(
                            modifier = Modifier.align(Alignment.BottomCenter),
                            onClick = { text.value = "" }) {
                            Text("Очистить")
                        }
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .background(Color.Transparent)
                    ) {
                        AndroidView(
                            modifier = Modifier
                                .align(Alignment.TopCenter)
                                .padding(16.dp)
                                .background(Color.LightGray)
                                .fillMaxSize(),
                            factory = { context ->
                                val textView = TextView(context)
                                textView.movementMethod = LinkMovementMethod.getInstance()
                                textView
                            },
                            update = { it.text = HtmlCompat.fromHtml(text.value, HtmlCompat.FROM_HTML_MODE_COMPACT) })
                    }
                }
            }
        }
    }
}