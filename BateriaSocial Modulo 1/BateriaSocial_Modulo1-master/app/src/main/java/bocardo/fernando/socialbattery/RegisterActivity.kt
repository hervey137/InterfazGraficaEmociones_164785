package bocardo.fernando.socialbattery

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import bocardo.fernando.socialbattery.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth

        val btnRegister = findViewById<Button>(R.id.btnRegister)




        btnRegister.setOnClickListener {

            val etUser = findViewById<EditText>(R.id.etUser)
            val etEmail = findViewById<EditText>(R.id.etEmail)
            val etPassword = findViewById<EditText>(R.id.etPassword)
            val etConfirm = findViewById<EditText>(R.id.etConfirm)

            var mUser = etUser.text.toString()
            var mEmail = etEmail.text.toString()
            var mPassword = etPassword.text.toString()
            var mConfirm = etConfirm.text.toString()

            if (mUser.isEmpty()) {
                etUser.error = "Ingrese su usuario"
                etUser.requestFocus()
            } else if (!mUser.matches("^[a-zA-Z0-9]*$".toRegex())) {
                etUser.error = "El usuario no debe contener caracteres especiales"
                etUser.requestFocus()
            } else if (mEmail.isEmpty()) {
                etEmail.error = "Ingrese su correo electrónico"
                etEmail.requestFocus()
            } else if (!Patterns.EMAIL_ADDRESS.matcher(mEmail).matches()) {
                etEmail.error = "El correo ingresado es inválido"
                etEmail.requestFocus()
            } else if (mPassword.isEmpty()) {
                etPassword.error = "Ingrese su contraseña"
                etPassword.requestFocus()
            } else if (mPassword.length < 6) {
                etPassword.error = "La contraseña debe ser de al menos 6 caracteres"
                etPassword.requestFocus()
            } else if (mConfirm.isEmpty()) {
                etConfirm.error = "Confirme su contraseña"
                etConfirm.requestFocus()
            } else if (!mPassword.equals(mConfirm)){
                etConfirm.error = "Las contraseñas deben coincidir"
                etConfirm.requestFocus()
            } else {

                val sharedPreferences = getSharedPreferences("infoUsuario", Context.MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                editor.putString("username", mUser)
                editor.putString("email", mEmail)
                editor.putString("password", mPassword)
                editor.apply()

                registerUser(mEmail, mPassword, mUser)
            }
        }

        val btnBack = findViewById<Button>(R.id.btnBack)

        btnBack.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

    }

    private fun registerUser(email: String?, password: String?, username: String?) {
        if (email != null && password != null) {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        val uid = user?.uid

                        // Save user data to Firebase database
                        val db = FirebaseDatabase.getInstance()
                        val usersRef = db.getReference("users")
                        val userData = HashMap<String, String>()
                        userData["username"] = username ?: ""
                        uid?.let { usersRef.child(it).setValue(userData) }

                        Log.d(ContentValues.TAG, "createUserWithEmail:success")
                        Toast.makeText(
                            baseContext, "Usuario registrado",
                            Toast.LENGTH_SHORT
                        ).show()
                        val sharedPreferences = getSharedPreferences("infoUsuario", Context.MODE_PRIVATE)
                        val editor = sharedPreferences.edit()
                        editor.clear()
                        editor.apply()

                        val intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(ContentValues.TAG, "createUserWithEmail:failure", task.exception)
                        Toast.makeText(
                            baseContext, "Error al registrar",
                            Toast.LENGTH_SHORT
                        ).show()
                        //updateUI(null)
                    }
                }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        val sharedPreferences = getSharedPreferences("infoUsuario", Context.MODE_PRIVATE)
        sharedPreferences.edit().clear().apply()
    }
}