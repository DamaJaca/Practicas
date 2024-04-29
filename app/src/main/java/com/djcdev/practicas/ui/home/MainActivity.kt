package com.djcdev.practicas.ui.home

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.djcdev.practicas.R
import com.djcdev.practicas.databinding.ActivityMainBinding
import com.google.firebase.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.google.firebase.remoteconfig.remoteConfig
import com.google.firebase.remoteconfig.remoteConfigSettings
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var firebaseConfig: FirebaseRemoteConfig




    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        firebaseConfig.fetchAndActivate().addOnCompleteListener {
            if (it.isSuccessful){
                Log.i("PACO", firebaseConfig.getBoolean("change_style").toString())

                if (firebaseConfig.getBoolean("change_style")){
                    setTheme(R.style.Theme_Practicas_Changed)
                }else{
                    setTheme(R.style.Theme_Practicas)
                }

            }
        }


        enableEdgeToEdge()
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


    }

}


