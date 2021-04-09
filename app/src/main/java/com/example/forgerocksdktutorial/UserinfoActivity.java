package com.example.forgerocksdktutorial;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;

import com.nimbusds.jwt.JWTParser;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.forgerock.android.auth.AccessToken;
import org.forgerock.android.auth.FRDevice;
import org.forgerock.android.auth.FRListener;
import org.forgerock.android.auth.FRUser;
import org.forgerock.android.auth.UserInfo;
import org.forgerock.android.auth.exception.AuthenticationRequiredException;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;

public class UserinfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinfo);

//      Elementos
        TextView userInfoTxt = findViewById(R.id.textViewUserinfo);
        View divider = findViewById(R.id.divider);
        View divider2 = findViewById(R.id.divider2);
        TextView textAccessToken = findViewById(R.id.textAccessToken);
        TextView textIdToken = findViewById(R.id.textIdToken);

//      Cuando inicia sesion
        FRUser.getCurrentUser().getUserInfo(new FRListener<UserInfo>() {
            @Override
            public void onSuccess(UserInfo result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        userInfoTxt.setText("Bienvenido: \n" + result.getSub());
                    }
                });
            }

            @Override
            public void onException(Exception e) {
                System.err.println("e = " + e.getMessage());
            }
        });
        FRUser.getCurrentUser().getAccessToken(new FRListener<AccessToken>() {
            @Override
            public void onSuccess(AccessToken result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject accesToken = new JSONObject(result.toJson());
                            textAccessToken.setText(accesToken.toString(4));
                            JSONObject idToken = new JSONObject(JWTParser.parse(result.getIdToken()).getJWTClaimsSet().toString());
                            textIdToken.setText(idToken.toString(4));
                        } catch (JSONException | ParseException e) {
                            e.printStackTrace();
                        }
                    }
                });


            }

            @Override
            public void onException(Exception e) {

            }

        });

//      Botones
        FloatingActionButton btnDeviceInfo = findViewById(R.id.btnDeviceInfo);
        FloatingActionButton btnUserInfo = findViewById(R.id.btnUserInfo);
        FloatingActionButton btnLogout = findViewById(R.id.btnLogout);
        FloatingActionButton btnRefresh = findViewById(R.id.btnRefresh);

//      Volver a mostrar los datos
        btnUserInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                divider.setVisibility(View.VISIBLE);
                divider2.setVisibility(View.VISIBLE);
                userInfoTxt.setVisibility(View.VISIBLE);
                textIdToken.setVisibility(View.VISIBLE);
                btnUserInfo.setVisibility(View.GONE);
                btnDeviceInfo.setVisibility(View.VISIBLE);
                btnRefresh.setVisibility(View.VISIBLE);
                FRUser.getCurrentUser().getUserInfo(new FRListener<UserInfo>() {
                    @Override
                    public void onSuccess(UserInfo result) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                userInfoTxt.setText("Bienvenido: \n" + result.getSub());
                            }
                        });
                    }

                    @Override
                    public void onException(Exception e) {
                        System.err.println("e = " + e.getMessage());
                    }
                });
                FRUser.getCurrentUser().getAccessToken(new FRListener<AccessToken>() {
                    @Override
                    public void onSuccess(AccessToken result) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    JSONObject resulta = new JSONObject(result.toJson());
                                    textAccessToken.setText(resulta.toString(4));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });


                    }

                    @Override
                    public void onException(Exception e) {

                    }

                });

            }

        });

//      Refresh Token
        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                divider.setVisibility(View.VISIBLE);
                divider2.setVisibility(View.VISIBLE);
                userInfoTxt.setVisibility(View.VISIBLE);
                textIdToken.setVisibility(View.VISIBLE);
                btnUserInfo.setVisibility(View.GONE);
                btnDeviceInfo.setVisibility(View.VISIBLE);
                try {
                    FRUser.getCurrentUser().getAccessToken();

                    System.out.println("fr " + FRUser.getCurrentUser().getAccessToken());
                    FRUser.getCurrentUser().getAccessToken(new FRListener<AccessToken>() {
                        @Override
                        public void onSuccess(AccessToken result) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        JSONObject resulta = new JSONObject(result.toJson());
                                        textAccessToken.setText(resulta.toString(4));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });


                        }

                        @Override
                        public void onException(Exception e) {

                        }

                    });
                    FRUser.getCurrentUser().getUserInfo(new FRListener<UserInfo>() {
                        @Override
                        public void onSuccess(UserInfo result) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    userInfoTxt.setText("Bienvenido: \n" + result.getSub());
                                }
                            });
                        }

                        @Override
                        public void onException(Exception e) {
                            System.err.println("e = " + e.getMessage());
                        }
                    });
                } catch (AuthenticationRequiredException e) {
                    e.printStackTrace();
                }


            }

        });

//      Logout
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FRUser.getCurrentUser().logout();
                Intent intent = new Intent(UserinfoActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

//      Mostrar info del dispositivo
        btnDeviceInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FRDevice.getInstance().getProfile(new FRListener<JSONObject>() {

                    @Override
                    public void onSuccess(JSONObject result) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    divider.setVisibility(View.GONE);
                                    divider2.setVisibility(View.GONE);
                                    userInfoTxt.setVisibility(View.GONE);
                                    textIdToken.setVisibility(View.GONE);
                                    textAccessToken.setText(result.toString(4));
                                    btnUserInfo.setVisibility(View.VISIBLE);
                                    btnDeviceInfo.setVisibility(View.GONE);
                                    btnRefresh.setVisibility(View.GONE);
                                } catch (JSONException e) {
                                    System.err.println("e = " + e.getMessage());
                                }
                            }
                        });
                    }

                    @Override
                    public void onException(Exception e) {
                        System.err.println("e = " + e.getMessage());
                    }
                });
            }
        });
    }

}