package com.example.proyecto_dam_juntoz

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.google.firebase.firestore.FirebaseFirestore
import java.security.MessageDigest

class RegisterFragment : Fragment() {

    private lateinit var db: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_register, container, false)

        db = FirebaseFirestore.getInstance()

        val etCorreo = view.findViewById<EditText>(R.id.etRegCorreo)
        val etUsuario = view.findViewById<EditText>(R.id.etRegUsuario)
        val etContrasena = view.findViewById<EditText>(R.id.etRegContrasena)
        val btnRegister = view.findViewById<Button>(R.id.btnRegister)

        btnRegister.setOnClickListener {
            val correo = etCorreo.text.toString().trim()
            val usuario = etUsuario.text.toString().trim()
            val contrasena = etContrasena.text.toString().trim()

            if (correo.isNotEmpty() && usuario.isNotEmpty() && contrasena.isNotEmpty()) {
                val contrasenaEncriptada = encriptarContrasena(contrasena)

                val usuarioData = hashMapOf(
                    "correo" to correo,
                    "nombreUsuario" to usuario,
                    "contrasena" to contrasenaEncriptada
                )

                db.collection("Usuarios").document(usuario).set(usuarioData)
                    .addOnSuccessListener {
                        Toast.makeText(context, "Registro exitoso", Toast.LENGTH_SHORT).show()
                        navigateToLogin()
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(context, "Error al registrar: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
            } else {
                Toast.makeText(context, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }

    private fun encriptarContrasena(contrasena: String): String {
        val md = MessageDigest.getInstance("SHA-256")
        val hashBytes = md.digest(contrasena.toByteArray())
        return hashBytes.joinToString("") { "%02x".format(it) }
    }

    private fun navigateToLogin() {
        parentFragmentManager.commit {
            replace<LoginFragment>(R.id.frameContainer)
            setReorderingAllowed(true)
            addToBackStack(null)
        }
    }
}