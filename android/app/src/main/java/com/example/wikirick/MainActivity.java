package com.example.wikirick;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.wikirick.Episodios.EpisodiosFragment;
import com.example.wikirick.Perfil.PerfilFragment;
import com.example.wikirick.Personajes.PersonajesFragment;
import com.example.wikirick.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    private Fragment activeFragment;
    public OnBackPressedCallback onBackPressedCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new PersonajesFragment());


        binding.bottomNavigation.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.Lista) {
                replaceFragment(new EpisodiosFragment());
            } else if (item.getItemId() == R.id.Perfil) {
                replaceFragment(new PerfilFragment());
            } else if (item.getItemId() == R.id.Personajes) {
                replaceFragment(new PersonajesFragment());
            }
            return true;
        });

        onBackPressedCallback = new OnBackPressedCallback(false) {
            @Override
            public void handleOnBackPressed()
            {
                if(activeFragment instanceof PersonajesFragment) {
                    ((PersonajesFragment)activeFragment).campoTexto.setText("");
                    ((PersonajesFragment)activeFragment).sendSearchRequest("");
                } else if (activeFragment instanceof EpisodiosFragment) {
                    ((EpisodiosFragment)activeFragment).campoTexto.setText("");
                    ((EpisodiosFragment)activeFragment).sendSearchRequest("");




                }

            }
        };
        getOnBackPressedDispatcher().addCallback(this,onBackPressedCallback);



    }




    private void replaceFragment(Fragment fragment){

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout,fragment);
        activeFragment=fragment;
        fragmentTransaction.commit();

    }
}