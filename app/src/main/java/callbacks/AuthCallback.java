/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package callbacks;

import android.app.Activity;
import android.content.Context;

import helpers.RetrofitInitializer;
import listeners.AuthenticationListener;
import model.Auth;
import retrofit2.Call;
import retrofit2.Retrofit;

/**
 * Created by Olivine on 6/10/2017.
 */

public class AuthCallback {
    Retrofit retrofit;
    AuthenticationListener authenticationListener;

    public AuthCallback(Activity mContext) {
    retrofit= RetrofitInitializer.initNetwork(mContext);
        authenticationListener=retrofit.create(AuthenticationListener.class);
    }

  public Call<String> register(Auth auth){
      return authenticationListener.signUp(auth);
  }
}
