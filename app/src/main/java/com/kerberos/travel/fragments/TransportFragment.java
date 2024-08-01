package com.kerberos.travel.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.kerberos.travel.R;

public class TransportFragment extends Fragment {

    public TransportFragment() {
        // Required empty public constructor
    }
    public static TransportFragment newInstance(String param1, String param2) {
        return new TransportFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_transport, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        CardView car = view.findViewById(R.id.car);
        CardView bus = view.findViewById(R.id.bus);
        CardView ferry = view.findViewById(R.id.ferry);
        CardView train = view.findViewById(R.id.train);
        ImageView back = view.findViewById(R.id.imageView22);

        back.setOnClickListener(v ->{
            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentHolder,new HomeFragment()).commit();
        });

        car.setOnClickListener(v->{
            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentHolder,new CarFragment()).commit();
        });
        bus.setOnClickListener(v->{
            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentHolder,new BusFragment()).commit();
        });
        ferry.setOnClickListener(v->{
            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentHolder,new FerryFragment()).commit();
        });
        train.setOnClickListener(v->{
            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentHolder,new TrainFragment()).commit();
        });
    }
}