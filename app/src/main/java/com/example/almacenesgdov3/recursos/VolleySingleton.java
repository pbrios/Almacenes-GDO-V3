package com.example.almacenesgdov3.recursos;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class VolleySingleton {
    private static com.example.almacenesgdov3.recursos.VolleySingleton singleton;
    private RequestQueue requestQueue;
    private static Context ctx;

    private VolleySingleton(Context context){
        ctx = context;
        requestQueue = getRequestQueue();
    }

    public static synchronized com.example.almacenesgdov3.recursos.VolleySingleton getInstance(Context context) {
        if (singleton == null) {
            singleton = new com.example.almacenesgdov3.recursos.VolleySingleton(context);
        }
        return singleton;
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(ctx.getApplicationContext());
        }
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }
}
