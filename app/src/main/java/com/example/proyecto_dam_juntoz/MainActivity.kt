package com.example.proyecto_dam_juntoz

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    lateinit var navigation: BottomNavigationView

    private val mOnNavMenu = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when(item.itemId){
            R.id.itemLogin -> {
                supportFragmentManager.commit {
                    replace<LoginFragment>(R.id.frameContainer)
                    setReorderingAllowed(true)
                }
                return@OnNavigationItemSelectedListener true
            }
            R.id.itemRegister -> {
                supportFragmentManager.commit {
                    replace<RegisterFragment>(R.id.frameContainer)
                    setReorderingAllowed(true)
                }
                return@OnNavigationItemSelectedListener true
            }
            R.id.itemExit -> {
                finish()
                return@OnNavigationItemSelectedListener true
            }
            R.id.itemProducts -> {
                supportFragmentManager.commit {
                    replace<ProductsFragment>(R.id.frameContainer)
                    setReorderingAllowed(true)
                }
                return@OnNavigationItemSelectedListener true
            }
            R.id.itemCart -> {
                supportFragmentManager.commit {
                    replace<CartFragment>(R.id.frameContainer)
                    setReorderingAllowed(true)
                }
                return@OnNavigationItemSelectedListener true
            }
            R.id.itemOrder -> {
                supportFragmentManager.commit {
                    replace<OrderFragment>(R.id.frameContainer)
                    setReorderingAllowed(true)
                }
                return@OnNavigationItemSelectedListener true
            }
            R.id.itemLogout -> {
                supportFragmentManager.commit {
                    replace<LoginFragment>(R.id.frameContainer)
                    setReorderingAllowed(true)
                }
                navigation.post {
                    switchToLoginMenu()
                }
            }
            else -> {
                return@OnNavigationItemSelectedListener false
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        navigation = findViewById(R.id.navMenu)
        navigation.setOnNavigationItemSelectedListener(mOnNavMenu)

        supportFragmentManager.commit {
            replace<LoginFragment>(R.id.frameContainer)
            setReorderingAllowed(true)
        }

        navigation.selectedItemId = R.id.itemLogin
    }

    fun switchToAuthenticatedMenu() {
        navigation.menu.clear()
        navigation.inflateMenu(R.menu.navigation_menu_authenticated)
        navigation.selectedItemId = R.id.itemProducts
    }

    private fun switchToLoginMenu() {
        navigation.menu.clear()
        navigation.inflateMenu(R.menu.navigation_menu_login_register)
        navigation.selectedItemId = R.id.itemLogin
    }
}