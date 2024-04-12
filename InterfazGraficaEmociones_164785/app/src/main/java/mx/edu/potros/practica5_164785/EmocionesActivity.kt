package mx.edu.potros.practica5_164785

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView

class EmocionesActivity : AppCompatActivity() {

    var menu: ArrayList<Emocion> = ArrayList<Emocion>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_emociones)

        var imagen: ImageView = findViewById(R.id.imageView)
        var menuOption: String? = intent.getStringExtra("menuType")
        agregarEmociones(menuOption)

        var listview: ListView = findViewById(R.id.listView) as ListView

        var adaptador: AdaptadorProductos = AdaptadorProductos(this, menu)
        listview.adapter = adaptador

        when(menuOption) {
            "Registro Emociones" -> {
                imagen.setImageResource(R.drawable.banner_emociones)
            }
        }

    }

    fun agregarEmociones(option: String?) {
        when(option) {
            "Registro Emociones" -> {
                menu.add(Emocion("Alegria", R.drawable.felicidad, "La alegría es una emoción amistosa o una sensación de satisfacción o felicidad placer.La alegría es producida normalmente por un suceso favorable que suele manifestarse con un buen estado de ánimo, la satisfacción y la tendencia a la risa o la sonrisa."))
                menu.add(Emocion("Tristeza", R.drawable.tristeza, "Sensación de decaimiento o infelicidad en respuesta a una aflicción, desánimo o desilusión. Si es continua, podría indicar depresión."))
                menu.add(Emocion("Miedo", R.drawable.miedo, "Sensación desagradable provocada por la percepción de peligro, real o imaginario."))
                menu.add(Emocion("Ira", R.drawable.ira, "La ira es una señal de advertencia que avisa cuando algo anda mal en una situación. La ira es una emoción desagradable, pero también es una emoción normal y saludable. Es una respuesta natural ante amenazas percibidas. La ira se convierte en problema cuando no se controla de forma saludable."))
                menu.add(Emocion("Sorpresa", R.drawable.sorpresa, "Sorpresa es un breve estado mental y fisiológico de alteración emocional, una respuesta de sobresalto experimentada por animales y humanos como resultado de un evento inesperado."))
                menu.add(Emocion("Disgusto", R.drawable.disgusto, "Un disgusto es un sentimiento negativo que se experimenta por una desilusión, un desengaño u otro tipo de contrariedad. Puede decirse que surge cuando un individuo recibe una mala noticia."))
            }
        }
    }

    private class AdaptadorProductos: BaseAdapter {
        var emociones = ArrayList<Emocion>()
        var contexto: Context? = null

        constructor(contexto: Context, productos: ArrayList<Emocion>) {
            this.emociones = productos
            this.contexto = contexto
        }

        override fun getCount(): Int {
            return emociones.size
        }

        override fun getItem(p0: Int): Any {
            return emociones[p0]
        }

        override fun getItemId(p0: Int): Long {
            return p0.toLong()
        }

        override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
            var emocion = emociones[p0]
            var inflador = LayoutInflater.from(contexto)
            var vista = inflador.inflate(R.layout.producto_view, null)

            var imagen = vista.findViewById(R.id.producto_img) as ImageView
            var nombre = vista.findViewById(R.id.producto_nombre) as TextView
            var desc = vista.findViewById(R.id.producto_desc) as TextView

            imagen.setImageResource(emocion.image)
            nombre.setText(emocion.name)
            desc.setText(emocion.description)
            return vista
        }
    }
}