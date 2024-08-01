package com.kerberos.travel.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.kerberos.travel.R;
import com.kerberos.travel.pages.CardsActivity;
import com.kerberos.travel.pages.LoginActivity;
import com.kerberos.travel.pages.NotificationActivity;
import com.kerberos.travel.pages.ProfileActivity;
import com.kerberos.travel.pages.RecentActivity;

public class ProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView userEmail = view.findViewById(R.id.user);
        LinearLayout card = view.findViewById(R.id.myCards);
        TextView profile = view.findViewById(R.id.textView2);
        LinearLayout plans = view.findViewById(R.id.plans);
        LinearLayout recent = view.findViewById(R.id.recents);
        LinearLayout deals = view.findViewById(R.id.deals);
        LinearLayout upcoming = view.findViewById(R.id.upcoming);
        LinearLayout notifications = view.findViewById(R.id.notifications);
        LinearLayout bookings = view.findViewById(R.id.bookings);
        MaterialButton logout = view.findViewById(R.id.logout);

        userEmail.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());

        logout.setOnClickListener(v->{
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(requireActivity(), LoginActivity.class));
            requireActivity().finish();
        });

        upcoming.setOnClickListener(v->{
            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentHolder,new UpcomingFragment()).commit();
        });
        bookings.setOnClickListener(v->{
            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentHolder,new BookingsFragment()).commit();
        });
        recent.setOnClickListener(v->{
            startActivity(new Intent(requireActivity(), RecentActivity.class));
        });
        deals.setOnClickListener(v->{
            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentHolder,new DealsFragment()).commit();
        });
        plans.setOnClickListener(v->{
            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentHolder,new PlansFragment()).commit();
        });
        notifications.setOnClickListener(v->{
            startActivity(new Intent(requireActivity(), NotificationActivity.class));
        });
        card.setOnClickListener(v->{
            startActivity(new Intent(requireActivity(), CardsActivity.class));
        });
        profile.setOnClickListener(v->{
            startActivity(new Intent(requireActivity(), ProfileActivity.class));
        });
    }
}