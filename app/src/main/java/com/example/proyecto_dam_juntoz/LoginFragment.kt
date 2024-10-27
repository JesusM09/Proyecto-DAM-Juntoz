package com.example.proyecto_dam_juntoz

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.google.firebase.firestore.FirebaseFirestore
import java.security.MessageDigest

class LoginFragment : Fragment() {

    private lateinit var db: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        db = FirebaseFirestore.getInstance()

        val etUsuario = view.findViewById<EditText>(R.id.etLogUsuario)
        val etContrasena = view.findViewById<EditText>(R.id.etLogContrasena)
        val btnLogin = view.findViewById<Button>(R.id.btnLogin)

        btnLogin.setOnClickListener {
            val usuario = etUsuario.text.toString().trim()
            val contrasenaIngresada = etContrasena.text.toString().trim()

            if (usuario.isNotEmpty() && contrasenaIngresada.isNotEmpty()) {
                val contrasenaEncriptada = encriptarContrasena(contrasenaIngresada)

                db.collection("Usuarios").document(usuario).get()
                    .addOnSuccessListener { document ->
                        if (document.exists()) {
                            val contrasenaGuardada = document.getString("contrasena")
                            if (contrasenaGuardada == contrasenaEncriptada) {
                                showAuthenticatedMessage()
                                navigateToProducts()
                            } else {
                                Toast.makeText(context, "Contraseña incorrecta", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            Toast.makeText(context, "Usuario no encontrado", Toast.LENGTH_SHORT).show()
                        }
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
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

    private fun showAuthenticatedMessage() {
        Toast.makeText(requireContext(), "Se ha autenticado correctamente", Toast.LENGTH_SHORT).show()
    }

    private fun navigateToProducts() {
        val mainActivity = activity as MainActivity
        mainActivity.switchToAuthenticatedMenu()
        parentFragmentManager.commit {
            replace<ProductsFragment>(R.id.frameContainer)
            setReorderingAllowed(true)
            addToBackStack(null)
        }
    }
}