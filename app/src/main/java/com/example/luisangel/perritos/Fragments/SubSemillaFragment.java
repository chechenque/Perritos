package com.example.luisangel.perritos.Fragments;


import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.luisangel.perritos.APIPerritos.RestApiAdapter;
import com.example.luisangel.perritos.APIPerritos.Service;
import com.example.luisangel.perritos.Adaptadores.PerritosAdapter;
import com.example.luisangel.perritos.Perrito;
import com.example.luisangel.perritos.R;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class SubSemillaFragment extends Fragment {
    RecyclerView recyclerPerritos;
    ArrayList<Perrito> listaPerritos;


    public SubSemillaFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_sub_semilla, container, false);
        recyclerPerritos = (RecyclerView) vista.findViewById(R.id.recyclerIdSubSemilla);
        recyclerPerritos.setLayoutManager(new LinearLayoutManager(getContext()));

        listaPerritos = new ArrayList<>();

        RestApiAdapter restApiAdapter = new RestApiAdapter();
        Service service = restApiAdapter.getPerritoService();
        Call<JsonObject> call = service.getSubSemilla();

        ConnectivityManager cm =
                (ConnectivityManager)getContext().getSystemService(getContext().CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        Log.d("Respuesta",""+ isConnected);

        if(isConnected) {
            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    try {
                        if (response.isSuccessful()) {
                            Log.d("Subsemilla", "succes");
                            JSONArray jsonArray = new JSONArray(response.body().get("message").toString());
                            jsonArray.get(0);

                            listaPerritos.add(new Perrito("" + jsonArray.get(0), ""));
                            listaPerritos.add(new Perrito("" + jsonArray.get(1), ""));
                            listaPerritos.add(new Perrito("" + jsonArray.get(2), ""));
                            listaPerritos.add(new Perrito("" + jsonArray.get(3), ""));
                            listaPerritos.add(new Perrito("" + jsonArray.get(4), ""));
                            listaPerritos.add(new Perrito("" + jsonArray.get(5), ""));

                            PerritosAdapter perritosAdapter = new PerritosAdapter(getActivity(), listaPerritos);

                            recyclerPerritos.setAdapter(perritosAdapter);

                            //Log.d("Subsemilla","" + jsonArray.length()+ " " + jsonArray.get(0));
                        } else {
                            Log.d("Subsemilla", "error else");
                        }
                    } catch (JSONException e) {
                        Log.d("Subsemilla", "error catch " + e);
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {

                }
            });
        }else {
            Toast.makeText(getActivity(),"No hay Internet",Toast.LENGTH_SHORT).show();
        }

        return vista;
    }

}
