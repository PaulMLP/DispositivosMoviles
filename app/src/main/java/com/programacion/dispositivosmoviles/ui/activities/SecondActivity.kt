package com.programacion.dispositivosmoviles.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.programacion.dispositivosmoviles.R
import com.programacion.dispositivosmoviles.databinding.ActivitySecondBinding
import com.programacion.dispositivosmoviles.ui.fragments.FirstFragment
import com.programacion.dispositivosmoviles.ui.fragments.SecondFragment
import com.programacion.dispositivosmoviles.ui.fragments.ThirdFragment
import com.programacion.dispositivosmoviles.ui.utilities.FragmentsManager

class SecondActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        FragmentsManager().replaceFragment(
            supportFragmentManager,
            binding.frmContainer.id,
            FirstFragment()
        )

        //initClass()
        binding.bottomNavigation.setOnItemReselectedListener { item ->
            when (item.itemId) {
                R.id.item_1 -> {
                    FragmentsManager().replaceFragment(
                        supportFragmentManager,
                        binding.frmContainer.id,
                        FirstFragment()
                    )
                    true
                }

                R.id.item_2 -> {
                    FragmentsManager().replaceFragment(
                        supportFragmentManager,
                        binding.frmContainer.id,
                        SecondFragment()
                    )
                    true
                }

                R.id.item_3 -> {
                    FragmentsManager().replaceFragment(
                        supportFragmentManager,
                        binding.frmContainer.id,
                        ThirdFragment()
                    )
                    true
                }

                else -> false
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    private fun initClass() {
//        binding.btnReturn.setOnClickListener {
//            var intent = Intent(
//                this,
//                MainActivity::class.java
//            )
//            startActivity(intent)
//        }
    }

}