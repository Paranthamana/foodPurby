package com.foodpurby.screens;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.sloop.fonts.FontsManager;
import com.foodpurby.R;
import com.foodpurby.adapters.AddressAdapter;
import com.foodpurby.baseclass.MyActivity;
import com.foodpurby.events.AddRemoveToCartEvent;
import com.foodpurby.events.AddressEvent;
import com.foodpurby.events.MainMenuRefreshEvent;
import com.foodpurby.events.SigninEvent;
import com.foodpurby.model.DAOFBCheck;
import com.foodpurby.model.DAOSignin;
import com.foodpurby.ondbstorage.DBActionsUser;
import com.foodpurby.util.CustomProgressDialog;
import com.foodpurby.utillities.AppSharedValues;
import com.foodpurby.utillities.SessionManager;
import com.foodpurby.utillities.UserDetails;

import org.json.JSONObject;

import java.util.Arrays;

import de.greenrobot.event.EventBus;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static com.foodpurby.screens.UserSignin.isValidEmail;

/**
 * Created by android1 on 12/29/2015.
 */
public class UserSigninAct extends Activity {

    private EventBus bus = EventBus.getDefault();
    private CustomProgressDialog mCustomProgressDialog;

    private ListView lvAddress;
    private AddressAdapter mpAdapter;
    Toolbar mToolbar;
    TextView mToolHeading;

    TextView tvSignup;
    TextView tvForgotpassword;
    Button btnSignup;
    Button btnSignin, mFacebookLoginButton;
    EditText etEmailId;
    EditText etPassword;

    LoginButton mfbLoginButton;

    CallbackManager callbackManager;
    AccessTokenTracker accessTokenTracker;
    private String get_email;
    private String get_passwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        mCustomProgressDialog = CustomProgressDialog.getInstance();
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_signin);

      //  FontsManager.initFormAssets(this, "Lato-Light.ttf");
     //   FontsManager.changeFonts(this);

        initiateWidgets();

        mToolbar.setNavigationIcon(R.drawable.back);
        mToolHeading.setText(R.string.txt_signin);

        mFacebookLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AccessToken accessToken = AccessToken.getCurrentAccessToken();
                if (accessToken != null) {
                    LoginManager.getInstance().logOut();
                }
                mfbLoginButton.performClick();
            }
        });
        setUpFacebook();

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });


        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get_email = etEmailId.getText().toString();
                get_passwd = etPassword.getText().toString();

                if (get_email.equals("") && get_passwd.equals("")) {
                    Toast.makeText(UserSigninAct.this, "Please enter the field", Toast.LENGTH_SHORT).show();
                } else if (get_email.isEmpty()) {
                    Toast.makeText(UserSigninAct.this, "Email id empty", Toast.LENGTH_LONG).show();
                } else if (!isValidEmail(get_email)) {
                    Toast.makeText(UserSigninAct.this, "Enter vaild Emailid", Toast.LENGTH_LONG).show();
                } else if (get_passwd.isEmpty()) {
                    Toast.makeText(UserSigninAct.this, "Enter the password", Toast.LENGTH_LONG).show();
                } else if (get_passwd.length() < 6 || get_passwd.length() > 15) {
                    Toast.makeText(UserSigninAct.this, "Enter vaild password", Toast.LENGTH_LONG).show();
                } else {
                    MyActivity.DisplayUserSignup(UserSigninAct.this);
                }

            }
        });

        tvSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AppSharedValues.SignupStatus.equalsIgnoreCase("1")) {
                    finish();
                }
                MyActivity.DisplayUserSignup(UserSigninAct.this);
            }
        });

        tvForgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyActivity.DisplayUserSigninForgotPassword(UserSigninAct.this);
            }
        });

        btnSignin.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View v) {


                                             get_email = etEmailId.getText().toString();
                                             get_passwd = etPassword.getText().toString();

                                             if (get_email.equals("") && get_passwd.equals("")) {
                                                 Toast.makeText(UserSigninAct.this, "Please enter the field", Toast.LENGTH_SHORT).show();
                                             } else if (get_email.isEmpty()) {
                                                 Toast.makeText(UserSigninAct.this, "Email id empty", Toast.LENGTH_LONG).show();
                                             } else if (!isValidEmail(get_email)) {
                                                 Toast.makeText(UserSigninAct.this, "Enter vaild Emailid", Toast.LENGTH_LONG).show();
                                             } else if (get_passwd.isEmpty()) {
                                                 Toast.makeText(UserSigninAct.this, "Enter the password", Toast.LENGTH_LONG).show();
                                             } else if (get_passwd.length() < 6 || get_passwd.length() > 17) {
                                                 Toast.makeText(UserSigninAct.this, "Enter vaild password", Toast.LENGTH_LONG).show();
                                             } else {

                                                 MyApplication.getInstance().trackEvent("Button", "Click", "Signin");

                                                 if (!mCustomProgressDialog.isShowing()) {
                                                     mCustomProgressDialog.ChageMessage(UserSigninAct.this, "Please wait...", "Please wait...");
                                                     mCustomProgressDialog.show(UserSigninAct.this);
                                                 }

                                                 DAOSignin.getInstance().Callresponse("",
                                                         etEmailId.getText().toString(),
                                                         etPassword.getText().toString(),
                                                         "aaaa",
                                                         "1",
                                                         "1",
                                                         new Callback<DAOSignin.Signin>() {

                                                             @Override
                                                             public void success(DAOSignin.Signin signin, Response response) {

                                                                 if (signin.getHttpcode().equals("200")) {

                                                                     SessionManager.getInstance().setcreditpoint(UserSigninAct.this,signin.getData().getWallet_points());

                                                                     UserDetails.setCustomerKey(signin.getData().getCustomer_key());
                                                                     UserDetails.setCustomerEmail(signin.getData().getCustomer_email());
                                                                     UserDetails.setCustomerName(signin.getData().getCustomer_name());
                                                                     UserDetails.setCustomerLastName(signin.getData().getCustomer_last_name());
                                                                     UserDetails.setCustomerMobile(signin.getData().getCustomer_mobile());
                                                                     UserDetails.setAccessToken(signin.getData().getAccess_token());

                                                                     DBActionsUser.add(
                                                                             UserDetails.getCustomerKey(), UserDetails.getCustomerKey(),
                                                                             UserDetails.getCustomerEmail(),
                                                                             UserDetails.getCustomerName(),
                                                                             UserDetails.getCustomerLastName(),
                                                                             UserDetails.getCustomerMobile(),
                                                                             UserDetails.getAccessToken());

                                                                     Toast.makeText(UserSigninAct.this, getString(R.string.toast_login_success), Toast.LENGTH_SHORT).show();

                                                                     bus.post(new AddressEvent());
                                                                     bus.post(new MainMenuRefreshEvent());
                                                                     bus.post(new SigninEvent());

                                                                     if (mCustomProgressDialog.isShowing()) {
                                                                         mCustomProgressDialog.dismiss();
                                                                     }

                                                                     if (AppSharedValues.LoginStatus.equalsIgnoreCase("1")) {
                                                                         MyActivity.DisplayHomePage(UserSigninAct.this);

                                                                     } else {
                                                                         finish();
                                                                     }

                                                                 } else {
                                                                     Toast.makeText(UserSigninAct.this, ""+signin.getMessage(), Toast.LENGTH_SHORT).show();
                                                                     if (mCustomProgressDialog.isShowing()) {
                                                                         mCustomProgressDialog.dismiss();
                                                                     }
//                                    Toast.makeText(UserSigninAct.this, signin.getMessage(), Toast.LENGTH_LONG).show();
                                                                 }

                                                             }

                                                             @Override
                                                             public void failure(RetrofitError error) {
                                                                 Toast.makeText(UserSigninAct.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                                                                 if (mCustomProgressDialog.isShowing()) {
                                                                     mCustomProgressDialog.dismiss();
                                                                 }
                                                             }
                                                         });
                                             }
                                         }
                                     }

        );
    }

    private void setUpFacebook() {
        mfbLoginButton.setReadPermissions(Arrays.asList("public_profile, email"));
        callbackManager = CallbackManager.Factory.create();

        mfbLoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                if (!mCustomProgressDialog.isShowing()) {
                    mCustomProgressDialog.ChageMessage(UserSigninAct.this, getString(R.string.dialog_please_wait), getString(R.string.dialog_please_wait));
                    mCustomProgressDialog.show(UserSigninAct.this);
                }
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {

                                try {
                                    String email = "";
                                    String birthday = "";
                                    String id = "";
                                    String firstName = "";
                                    String lastName = "";
                                    String imageUrl = "https://graph.facebook.com/" + id + "/picture?type=large";

                                    try {
                                        email = object.getString("email") == null ? "" : object.getString("email");
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                    try {
                                        firstName = object.getString("first_name") == null ? "" : object.getString("first_name");
                                        lastName = object.getString("last_name") == null ? "" : object.getString("last_name");
                                    } catch (Exception ex) {
                                        ex.printStackTrace();
                                    }


                                    try {
                                        id = object.getString("id") == null ? "" : object.getString("id");
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    AccessToken token = AccessToken.getCurrentAccessToken();
                                    String mToken = null;
                                    if (token != null) {
                                        mToken = token.getToken();
                                    }
                                    if (mCustomProgressDialog.isShowing()) {
                                        mCustomProgressDialog.dismiss();
                                    }

//                                    Toast.makeText(UserSigninAct.this, email + "\n" + firstName + "\n" + mToken, Toast.LENGTH_LONG).show();

                                    signInToTheApp(email, firstName, lastName, "", id, mToken, imageUrl);

                                } catch (Exception e) {
                                    e.printStackTrace();
                                    if (mCustomProgressDialog.isShowing()) {
                                        mCustomProgressDialog.dismiss();
                                    }
                                }

                            }
                        });


                Bundle parameters = new Bundle();
                parameters.putString("fields", "id, first_name, last_name, email, gender, birthday");
                request.setParameters(parameters);
                request.executeAsync();

                accessTokenTracker = new AccessTokenTracker() {
                    @Override
                    protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken,
                                                               AccessToken currentAccessToken) {
                    }
                };

            }

            @Override
            public void onCancel() {
                Log.d("Message :", "User Cancel Login");
            }

            @Override
            public void onError(FacebookException error) {
                Log.e("Message :", error.getMessage().toString());
            }
        });
    }

    private void signInToTheApp(final String email, final String firstName, final String lastName, final String birthday, final String id, final String token, final String imageUrl) {


        DAOFBCheck.getInstance().callResponse(id, new Callback<DAOFBCheck.FBResponse>() {
            @Override
            public void success(DAOFBCheck.FBResponse fbResponse, Response response) {
                if (mCustomProgressDialog.isShowing()) {
                    mCustomProgressDialog.dismiss();
                }
                if (fbResponse.getData().getAccess_token() == null) {
//                    Toast.makeText(UserSigninAct.this, "Nor registered", Toast.LENGTH_LONG).show();
                    Intent toFBSignAct = new Intent(UserSigninAct.this, FBSignUpAct.class);
                    toFBSignAct.putExtra("email", email);
                    toFBSignAct.putExtra("firstName", firstName);
                    toFBSignAct.putExtra("lastName", lastName);
                    toFBSignAct.putExtra("birthday", birthday);
                    toFBSignAct.putExtra("id", id);
                    toFBSignAct.putExtra("token", token);
                    toFBSignAct.putExtra("imageUrl", imageUrl);
                    startActivity(toFBSignAct);
                    finish();
//                    FBSignUp fragment = FBSignUp.newInstance(email, firstName, lastName, birthday, id, token, imageUrl);
                } else {
                    saveDataToLocalFromFbLogin(fbResponse);

                    SessionManager.getInstance().setcreditpoint(UserSigninAct.this,fbResponse.getData().getWallet_points());

                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (mCustomProgressDialog.isShowing()) {
                    mCustomProgressDialog.dismiss();
                }
                error.printStackTrace();
            }
        });


    }

    String goTo;

    private void saveDataToLocalFromFbLogin(DAOFBCheck.FBResponse signin) {
        {
            if (signin.getHttpcode().equals("200")) {
                UserDetails.setCustomerKey(signin.getData().getCustomer_key());
                UserDetails.setCustomerEmail(signin.getData().getCustomer_email());
                UserDetails.setCustomerName(signin.getData().getCustomer_name());
                UserDetails.setCustomerLastName(signin.getData().getCustomer_last_name());
                UserDetails.setCustomerMobile(signin.getData().getCustomer_mobile());
                UserDetails.setAccessToken(signin.getData().getAccess_token());

                DBActionsUser.add(
                        UserDetails.getCustomerKey(), UserDetails.getCustomerKey(),
                        UserDetails.getCustomerEmail(),
                        UserDetails.getCustomerName(),
                        UserDetails.getCustomerLastName(),
                        UserDetails.getCustomerMobile(),
                        UserDetails.getAccessToken());

                Toast.makeText(UserSigninAct.this, getString(R.string.toast_login_success), Toast.LENGTH_SHORT).show();

                bus.post(new AddressEvent());
                bus.post(new MainMenuRefreshEvent());
                bus.post(new SigninEvent());

                if (mCustomProgressDialog.isShowing()) {
                    mCustomProgressDialog.dismiss();
                }

                finish();
                Intent intent = new Intent(UserSigninAct.this, GetStartActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            } else {
                if (mCustomProgressDialog.isShowing()) {
                    mCustomProgressDialog.dismiss();
                }
                Toast.makeText(UserSigninAct.this, signin.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }

    public boolean isLoggedIn() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        return accessToken != null;
    }

    private void initiateWidgets() {
        mFacebookLoginButton = (Button) findViewById(R.id.facebook_login_button);
        mToolbar = (Toolbar) findViewById(R.id.tb_header);
        mToolHeading = (TextView) findViewById(R.id.tool_title);
        tvSignup = (TextView) findViewById(R.id.tvSignup);
        tvForgotpassword = (TextView) findViewById(R.id.tvForgotpassword);
        btnSignin = (Button) findViewById(R.id.btnSignin);
        btnSignup = (Button) findViewById(R.id.btnSignup);
        etEmailId = (EditText) findViewById(R.id.etEmailId);
        etPassword = (EditText) findViewById(R.id.etPassword);
        mfbLoginButton = (LoginButton) findViewById(R.id.login_button);

       /* if(AppSharedValues.SignupStatus.equalsIgnoreCase("1")){
            tvSignup.setVisibility(View.VISIBLE);
        }else {*/
        tvSignup.setVisibility(View.VISIBLE);
        //}

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my_cart, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onEvent(final AddRemoveToCartEvent ev) {
        runOnUiThread(new Runnable() {
            public void run() {
                notifyChanges();
            }
        });
    }

    private void notifyChanges() {
        //btnAddress.setText("Pay order total " + Common.getPriceWithCurrencyCode(DBActionsCart.getTotalCartPriceIncludingTax(AppSharedValues.getSelectedRestaurantBranchKey()).toString()));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        try {
            mCustomProgressDialog.dismiss();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
