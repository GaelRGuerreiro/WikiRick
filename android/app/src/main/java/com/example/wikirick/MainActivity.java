package com.example.wikirick;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.wikirick.Personajes.PersonajesFragment;
import com.example.wikirick.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

ActivityMainBinding binding;

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

}

private void replaceFragment(Fragment fragment){

    FragmentManager fragmentManager = getSupportFragmentManager();
    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
    fragmentTransaction.replace(R.id.frame_layout,fragment);
    fragmentTransaction.commit();





            }
}