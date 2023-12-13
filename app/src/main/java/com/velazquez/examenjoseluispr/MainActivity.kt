package com.velazquez.examenjoseluispr

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.examen1.data.DataSource
import com.example.examen1.data.Producto
import com.velazquez.examenjoseluispr.ui.theme.ExamenJoseLuisPRTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ExamenJoseLuisPRTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ExamenApp()
                }
            }
        }
    }
}

@Composable
fun ExamenApp(modifier: Modifier = Modifier) {
    var listaProductos by remember { mutableStateOf(DataSource.productos)}
    var nombre by remember { mutableStateOf("") }
    var mensaje by remember { mutableStateOf("Todavía no se han añadido ningun valor") }
    var precio by remember { mutableStateOf(-1) }
    var precioString  by remember { mutableStateOf("0") }
    Column(modifier = Modifier) {
        Column(modifier
            .background(Color.LightGray)
            .fillMaxWidth()) {
            Text(text = "Hola soy alumno Jose Luis Pino Redondo",
                modifier
                    .padding(horizontal = 20.dp, vertical = 50.dp)
                    )
        }
        Row (modifier = Modifier.height(470.dp)){
            Column(modifier = Modifier.padding(5.dp)){
                EditarCajaDeTexto(
                    value = nombre,
                    onValueChanged = { nombre = it },
                    texto = "Nombre",
                    modifier = Modifier
                        .padding(bottom = 20.dp)
                )
                EditarCajaDeTexto(
                    value = precioString,
                    onValueChanged = { precioString = it },
                    texto = "precio",
                    modifier = Modifier
                )

                Button(onClick = {
                    precio = -1
                    try {
                        precio = precioString.toInt()
                    } catch (e : Exception){

                    }

                    var nomRep = false
                    var preRep = false
                    var precioAntiguo = 0
                    for (item in listaProductos){
                        if(item.nombre == nombre){
                            nomRep=true
                            if(item.precio == precio){
                                preRep= true
                            } else {
                                precioAntiguo = item.precio
                                item.precio = precio
                            }
                            break
                        }
                    }
                    if(nomRep && preRep){
                        mensaje = "No se ha modificado nada del producto "+nombre
                    } else if(nomRep && !preRep){
                        mensaje = "Del producto "+nombre+" se ha modificado el precio de: "+precioAntiguo+" euros a "+precio+" euros"
                    } else if(nombre == "" || precio == -1){
                        mensaje = "Todavía no se han añadido ningun valor"
                    } else {
                        listaProductos.add(com.example.examen1.data.Producto(nombre, precio))
                        mensaje = "Se ha añadido el producto "+nombre+" con precio "+precio
                    }
                                 },
                    modifier
                        .padding(top = 20.dp)
                        .width(200.dp)) {
                    Text(text = "Add/Update producto")
                }
            }
            Column(modifier = Modifier.padding(5.dp)){
                Lista(modifier = modifier,
                    lista = listaProductos)
            }
        }
        Column(modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(Color.LightGray),
            horizontalAlignment = Alignment.CenterHorizontally) {
            Text(mensaje, modifier
                .height(50.dp))
        }
    }
}

@Composable
fun Lista (modifier: Modifier, lista : List<Producto>){
    LazyColumn(modifier = modifier){
        items(lista){ item ->
            Tarjeta(modifier = modifier, tema = item)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun Tarjeta (modifier: Modifier, tema : Producto){
    Card() {
        Column(
            modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Nombre: " + tema.nombre, modifier
                .background(Color.Yellow)
                .fillMaxWidth()
                .padding(20.dp))
            Text(text = "Precio: " + tema.precio, modifier
                .background(Color.Cyan)
                .fillMaxWidth()
                .padding(20.dp))
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditarCajaDeTexto(
    value: String,
    onValueChanged: (String) -> Unit,
    texto: String,
    modifier: Modifier = Modifier
){
    TextField(
        value = value,
        onValueChange = onValueChanged,
        label = { Text(text = texto) },
        modifier = modifier.width(200.dp),
        singleLine = true
    )

}

@Preview(showBackground = true)
@Composable
fun Preview() {
    ExamenJoseLuisPRTheme {
        ExamenApp()
    }
}
