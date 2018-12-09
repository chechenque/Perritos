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
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ImagenAleatoriaFragment extends Fragment {
    RecyclerView recyclerPerritos;
    ArrayList<Perrito> listaPerritos;


    public ImagenAleatoriaFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_imagen_aleatoria, container, false);
        recyclerPerritos = (RecyclerView) vista.findViewById(R.id.recyclerIdImagenAleatoria);
        recyclerPerritos.setLayoutManager(new LinearLayoutManager(getContext()));

        listaPerritos = new ArrayList<>();

        RestApiAdapter restApiAdapter = new RestApiAdapter();
        Service service = restApiAdapter.getPerritoService();
        Call<JsonObject> call = service.getImagenAleatoria();

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
                            //Log.d("Semilla","succes");
                            JSONArray jsonArray = new JSONArray(response.body().get("message").toString());
                            for (int i = 0; i < jsonArray.length(); ++i) {
                                listaPerritos.add(new Perrito("perrito", jsonArray.getString(i)));
                            }

                            PerritosAdapter perritosAdapter = new PerritosAdapter(getActivity(), listaPerritos);

                            recyclerPerritos.setAdapter(perritosAdapter);

                            Log.d("Imagen", "" + listaPerritos.size());
                        } else {
                            Log.d("Imagen", "error else");
                        }
                    } catch (JSONException e) {
                        Log.d("Imagen", "error catch " + e);
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {

                }
            });
        }else {
            Toast.makeText(getActivity(),"No hay Internet", Toast.LENGTH_SHORT).show();
        }

        return vista;
    }

}
