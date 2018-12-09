package com.example.luisangel.perritos.Fragments;


import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
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
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 */
public class SemillaCompletaFragment extends Fragment {
    RecyclerView recyclerPerritos;
    ArrayList<Perrito> listaPerritos;


    public SemillaCompletaFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_semilla_completa, container, false);
        recyclerPerritos = (RecyclerView) vista.findViewById(R.id.recyclerIdSemillaCompleta);
        recyclerPerritos.setLayoutManager(new LinearLayoutManager(getContext()));

        listaPerritos = new ArrayList<>();

        RestApiAdapter restApiAdapter = new RestApiAdapter();
        Service service = restApiAdapter.getPerritoService();
        Call<JsonObject> call = service.getSemillaCompleta();

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
                            JSONObject jsonObject = new JSONObject(response.body().get("message").toString());
                            JSONArray jsonArray = jsonObject.getJSONArray("sheepdog");

                            listaPerritos.add(new Perrito(jsonArray.get(0).toString(), "https://images.dog.ceo/breeds/sheepdog-english/n02105641_1045.jpg"));
                            listaPerritos.add(new Perrito(jsonArray.get(1).toString(), "https://images.dog.ceo/breeds/sheepdog-shetland/n02105855_10095.jpg"));

                            PerritosAdapter perritosAdapter = new PerritosAdapter(getActivity(), listaPerritos);

                            recyclerPerritos.setAdapter(perritosAdapter);

                            Log.d("SemillaCompleta", "" + listaPerritos.size());
                            Log.d("SemillaCompleta", "" + jsonArray.get(0));
                        } else {
                            Snackbar.make(getView(), "No hay internet", Snackbar.LENGTH_SHORT).show();
                            Toast.makeText(getActivity(), "No hay internet", Toast.LENGTH_SHORT).show();
                            Log.d("SemillaCompleta", "error else");
                        }
                    } catch (JSONException e) {
                        Log.d("SemillaCompleta", "error catch " + e);
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {

                }
            });
        }else{
            //Snackbar.make(getView(), "No hay internet", Snackbar.LENGTH_SHORT).show();
            Toast.makeText(getActivity(), "No hay internet", Toast.LENGTH_SHORT).show();
        }

        return vista;
    }

}
