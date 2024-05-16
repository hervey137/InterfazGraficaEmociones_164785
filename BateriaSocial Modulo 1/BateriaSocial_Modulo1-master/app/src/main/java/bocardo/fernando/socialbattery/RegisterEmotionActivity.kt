package bocardo.fernando.socialbattery

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.Date

class RegisterEmotionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_emotion)
        val btnNext = findViewById<Button>(R.id.btnNext)
        //var tvAge = findViewById<TextView>(R.id.tvAge)
        var etName = findViewById<TextView>(R.id.etName)
        //var btnPlus = findViewById<Button>(R.id.btnPlus)
        //var btnMinus = findViewById<Button>(R.id.btnMinus)
        var imagenEmocion = findViewById<ImageView>(R.id.imgAvatar)
        var emocion = findViewById<TextView>(R.id.emocion)
        val seekBarIntensidad = findViewById<SeekBar>(R.id.intensidad)
        val emocionSeleccionada = intent.getStringExtra("emocionSeleccionada")
        var porcentaje = findViewById<TextView>(R.id.porcentaje)
        val nombre = intent.getStringExtra("nombre")
        emocion.setText(nombre)
        val imagenId = when (emocionSeleccionada) {
            "Alegria" -> {
                R.drawable.alegria
            }
            "Tristeza" -> R.drawable.tristeza
            "Confianza" -> R.drawable.angel
            "Desagrado" -> R.drawable.desagrado1
            "Miedo" -> R.drawable.miedo
            "Ira" -> R.drawable.ira2
            "Sorpresa" -> R.drawable.sorpresa2
            "Anticipación" -> R.drawable.anticipacion
            else -> R.drawable.img_error
        }

        imagenEmocion.setImageResource(imagenId)
        var mIntensidad = 5
        //var indiceImg = 0
        //val imageResources = intArrayOf(bocardo.fernando.socialbattery.R.drawable.img_feliz, bocardo.fernando.socialbattery.R.drawable.img_enojado, bocardo.fernando.socialbattery.R.drawable.img_triste)



        //tvAge.text = mIntensidad.toString()
        //btnNext.setOnClickListener {
            //val intent = Intent(this, FavoriteThemesActivity::class.java)
            //startActivity(intent)
        //}

        val btnBack = findViewById<Button>(R.id.btnBack)

        btnBack.setOnClickListener {
            val intent = Intent(this, SelectEmotion::class.java)
            startActivity(intent)
        }

        /*btnPlus.setOnClickListener {
            if (mIntensidad < 10) {
                mIntensidad++
                tvAge.text = mIntensidad.toString()
            }
        }*/

        /*btnMinus.setOnClickListener {
            if (mIntensidad > 1) {
                mIntensidad--
                tvAge.text = mIntensidad.toString()
            }
        }*/

        btnNext.setOnClickListener {
            var mName = etName.text.toString()

            if (mName.isEmpty()){
                etName.error = "Ingrese un mensaje"
                etName.requestFocus()
            }
            else {
                //val mAge = tvAge.text.toString()

                val sharedPreferences = getSharedPreferences("infoUsuario", Context.MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                //editor.putString("age", mAge)
                editor.putString("name", mName)
                editor.apply()

                println(sharedPreferences.getString("user", ""))
                println(sharedPreferences.getString("email", ""))
                println(sharedPreferences.getString("password", ""))
                println(sharedPreferences.getString("age", ""))
                println(sharedPreferences.getString("imageName", ""))
                println(sharedPreferences.getString("name", ""))

                val userId = FirebaseAuth.getInstance().currentUser?.uid
                if (userId != null) {
                    val emocion = Emocion(emocion.text.toString(), seekBarIntensidad.progress, etName.text.toString(), System.currentTimeMillis(), userId)
                    val databaseReference = FirebaseDatabase.getInstance().getReference("users").child(userId).child("emociones")
                    databaseReference.push().setValue(emocion)
                } else {
                    // Manejar el caso donde no hay un usuario autenticado
                    println("No user is currently signed in.")
                }

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }

        seekBarIntensidad.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(SeekBar: SeekBar?, progress: Int, p2: Boolean) {
                val percentage = (progress * 100) / SeekBar!!.max
                // Actualizar el TextView
                porcentaje.text = "Intensidad: $progress%"
                val imagenId = when (emocionSeleccionada) {
                    "Alegria" -> {
                        if (progress < 33) {
                            imagenEmocion.setImageResource(R.drawable.serenidad)
                            emocion.setText("Serenidad")
                        } else if (progress in 33..66) {
                            imagenEmocion.setImageResource(R.drawable.alegria)
                            emocion.setText("Alegria")
                        } else if (progress > 66) {
                            imagenEmocion.setImageResource(R.drawable.alegriaa)
                            emocion.setText("Extasis")
                        } else {

                        }
                    }
                    "Tristeza" -> {
                        if (progress < 33) {
                            imagenEmocion.setImageResource(R.drawable.melancolia)
                            emocion.setText("Melancolía")
                        } else if (progress in 33..66) {
                            imagenEmocion.setImageResource(R.drawable.tristeza)
                            emocion.setText("Tristeza")
                        } else if (progress > 66) {
                            imagenEmocion.setImageResource(R.drawable.pena)
                            emocion.setText("Pena")
                        } else {

                        }
                    }
                    "Confianza" -> {
                        if (progress < 33) {
                            imagenEmocion.setImageResource(R.drawable.confianza)
                            emocion.setText("Aprobación")
                        } else if (progress in 33..66) {
                            imagenEmocion.setImageResource(R.drawable.angel)
                            emocion.setText("Confianza")
                        } else if (progress > 66) {
                            imagenEmocion.setImageResource(R.drawable.admiracion)
                            emocion.setText("Admiración")
                        } else {

                        }
                    }
                    "Desagrado" -> {
                        if (progress < 33) {
                            imagenEmocion.setImageResource(R.drawable.tedio)
                            emocion.setText("Tedio")
                        } else if (progress in 33..66) {
                            imagenEmocion.setImageResource(R.drawable.desagrado1)
                            emocion.setText("Desagrado")
                        } else if (progress > 66) {
                            imagenEmocion.setImageResource(R.drawable.odio)
                            emocion.setText("Odio")
                        } else {

                        }
                    }
                    "Miedo" -> {
                        if (progress < 33) {
                            imagenEmocion.setImageResource(R.drawable.temor)
                            emocion.setText("Temor")
                        } else if (progress in 33..66) {
                            imagenEmocion.setImageResource(R.drawable.miedo)
                            emocion.setText("Miedo")
                        } else if (progress > 66) {
                            imagenEmocion.setImageResource(R.drawable.terror)
                            emocion.setText("Terror")
                        } else {

                        }
                    }
                    "Ira" -> {
                        if (progress < 33) {
                            imagenEmocion.setImageResource(R.drawable.enfado)
                            emocion.setText("Enfado")
                        } else if (progress in 33..66) {
                            imagenEmocion.setImageResource(R.drawable.ira2)
                            emocion.setText("Ira")
                        } else if (progress > 66) {
                            imagenEmocion.setImageResource(R.drawable.ira)
                            emocion.setText("Furia")
                        } else {

                        }
                    }
                    "Sorpresa" -> {
                        if (progress < 33) {
                            imagenEmocion.setImageResource(R.drawable.distraido)
                            emocion.setText("Distracción")
                        } else if (progress in 33..66) {
                            imagenEmocion.setImageResource(R.drawable.sorpresa2)
                            emocion.setText("Sorpresa")
                        } else if (progress > 66) {
                            imagenEmocion.setImageResource(R.drawable.sorpresa)
                            emocion.setText("Asombro")
                        } else {

                        }
                    }
                    "Anticipación" -> {
                        if (progress < 33) {
                            imagenEmocion.setImageResource(R.drawable.interes)
                            emocion.setText("Interés")
                        } else if (progress in 33..66) {
                            imagenEmocion.setImageResource(R.drawable.anticipacion)
                            emocion.setText("Anticipación")
                        } else if (progress > 66) {
                            imagenEmocion.setImageResource(R.drawable.vigilancia)
                            emocion.setText("Vigilancia")
                        } else {

                        }
                    }
                    else -> R.drawable.img_error
                }
            }


            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

            }
            })
    }
}