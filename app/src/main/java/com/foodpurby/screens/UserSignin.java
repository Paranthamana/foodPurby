package com.foodpurby.screens;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
import com.foodpurby.baseclass.MyActivity;
import com.foodpurby.events.AddressEvent;
import com.foodpurby.events.DummyEvent;
import com.foodpurby.events.FilterIconVisibilityEvent;
import com.foodpurby.events.MainMenuRefreshEvent;
import com.foodpurby.events.SigninEvent;
import com.foodpurby.model.DAOFBCheck;
import com.foodpurby.model.DAOSignin;
import com.foodpurby.ondbstorage.DBActionsUser;
import com.foodpurby.util.CustomProgressDialog;
import com.foodpurby.utillities.SessionManager;
import com.foodpurby.utillities.UserDetails;

import org.json.JSONObject;

import java.util.Arrays;

import de.greenrobot.event.EventBus;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static com.facebook.FacebookSdk.getApplicationContext;


public class UserSignin extends Fragment {
    private EventBus bus = EventBus.getDefault();
    private CustomProgressDialog mCustomProgressDialog;

    TextView tvSignup;
    TextView tvForgotpassword;
    Button btnSignup, mFBButton;
    Button btnSignin;
    EditText etEmailId;
    EditText etPassword;
    LoginButton mLoginButton;
    CallbackManager callbackManager;
    AccessTokenTracker accessTokenTracker;

    Button FBButton;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "goto";

    // TODO: Rename and change types of parameters
    private String goTo;

    private OnFragmentInteractionListener mListener;

    private String PreviousScreen = "";
    private String get_email;
    private String get_passwd;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserSignin.
     */

    // TODO: Rename and change types and number of parameters
    public static UserSignin newInstance(String param1, String param2) {
        UserSignin fragment = new UserSignin();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_home_page, menu);
        MenuItem item_search = menu.findItem(R.id.action_search);
        item_search.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
        item_search.setVisible(false);
        super.onPrepareOptionsMenu(menu);
    }

    public UserSignin() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            goTo = getArguments().getString(ARG_PARAM1);
        }
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FacebookSdk.sdkInitialize(getApplicationContext());
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_signin, container, false);

       // FontsManager.initFormAssets(getActivity(), "Lato-Light.ttf");
       // FontsManager.changeFonts(view);

        UserDetails.clearAll();

        tvSignup = (TextView) view.findViewById(R.id.tvSignup);
        tvForgotpassword = (TextView) view.findViewById(R.id.tvForgotpassword);
        tvSignup.setVisibility(View.VISIBLE);

        btnSignin = (Button) view.findViewById(R.id.btnSignin);
        btnSignup = (Button) view.findViewById(R.id.btnSignup);
        etEmailId = (EditText) view.findViewById(R.id.etEmailId);
        etPassword = (EditText) view.findViewById(R.id.etPassword);
        mLoginButton = (LoginButton) view.findViewById(R.id.login_button);
        mFBButton = (Button) view.findViewById(R.id.facebook_login_button);

        FBButton= (Button) view.findViewById(R.id.fbbutton);

        FBButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLoginButton.performClick();
            }
        });

        setUpFacebook();

        mFBButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOutFB();
                MyApplication.getInstance().trackEvent("Button", "Click", "Facebook Login");

            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (get_email.equals("") && get_passwd.equals("")) {
                    Toast.makeText(getActivity(), "Please enter the field", Toast.LENGTH_SHORT).show();
                } else if (get_email.isEmpty()) {
                    Toast.makeText(getActivity(), "Email id empty", Toast.LENGTH_LONG).show();
                } else if (!isValidEmail(get_email)) {
                    Toast.makeText(getActivity(), "Enter vaild Emailid", Toast.LENGTH_LONG).show();
                } else if (get_passwd.isEmpty()) {
                    Toast.makeText(getActivity(), "Enter the password", Toast.LENGTH_LONG).show();
                } else if (get_passwd.length() < 6 || get_passwd.length() > 17) {
                    Toast.makeText(getActivity(), "Enter vaild password", Toast.LENGTH_LONG).show();
                } else {
                    MyActivity.DisplayUserSignup(getActivity());
                    MyApplication.getInstance().trackEvent("Button", "Click", "SignUp");

                }


            }
        });

        tvSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyActivity.DisplayUserSignup(getActivity());
                MyApplication.getInstance().trackEvent("TextView", "Click", "SignUp");
                getActivity().finish();
            }
        });

        tvForgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyActivity.DisplayUserSigninForgotPassword(getActivity());
                MyApplication.getInstance().trackEvent("Button", "Click", "Forgot Password");
                getActivity().finish();
            }
        });

        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get_email = etEmailId.getText().toString();
                get_passwd = etPassword.getText().toString();

                if (get_email.equals("") && get_passwd.equals("")) {
                    Toast.makeText(getActivity(), "Please enter the field", Toast.LENGTH_SHORT).show();
                } else if (get_email.isEmpty()) {
                    Toast.makeText(getActivity(), "Email id empty", Toast.LENGTH_LONG).show();
                } else if (!isValidEmail(get_email)) {
                    Toast.makeText(getActivity(), "Enter vaild Emailid", Toast.LENGTH_LONG).show();
                } else if (get_passwd.isEmpty()) {
                    Toast.makeText(getActivity(), "Enter the password", Toast.LENGTH_LONG).show();
                } else if (get_passwd.length() < 6 || get_passwd.length() > 15) {
                    Toast.makeText(getActivity(), "Enter vaild password", Toast.LENGTH_LONG).show();
                } else {

                    normalUserLogin();
                    MyApplication.getInstance().trackEvent("Button", "Click", "Signin");
                }
            }
        });


        bus.post(new SigninEvent());
        bus.post(new FilterIconVisibilityEvent(View.GONE, FilterIconVisibilityEvent.PageTitleType.Signin));
        return view;
    }

    private void normalUserLogin() {
        if (!mCustomProgressDialog.isShowing()) {
            mCustomProgressDialog.ChageMessage(getActivity(), getString(R.string.dialog_please_wait), getString(R.string.dialog_please_wait));
            mCustomProgressDialog.show(getActivity());
        }

        if (SessionManager.getInstance().getDeviceToken(getActivity()) != null) {
            String ss = "";
        }

        DAOSignin.getInstance().Callresponse("token",
                etEmailId.getText().toString(),
                etPassword.getText().toString(),
                SessionManager.getInstance().getDeviceToken(getActivity()),
//                ConstantsClass.getUniqueID(getActivity()),
                "1",
                "",


                new Callback<DAOSignin.Signin>() {

                    @Override
                    public void success(DAOSignin.Signin signin, Response response) {
                        saveDataToLocal(signin);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        String ss = "";
                        if (mCustomProgressDialog.isShowing()) {
                            mCustomProgressDialog.dismiss();
                        }
                    }
                });
    }

    private void saveDataToLocal(DAOSignin.Signin signin) {


        if (signin.getHttpcode().equals("200")) {
            UserDetails.setCustomerKey(signin.getData().getCustomer_key());
            UserDetails.setCustomerEmail(signin.getData().getCustomer_email());
            UserDetails.setCustomerName(signin.getData().getCustomer_name());
            UserDetails.setCustomerLastName(signin.getData().getCustomer_last_name());
            UserDetails.setCustomerMobile(signin.getData().getCustomer_mobile());
            UserDetails.setAccessToken(signin.getData().getAccess_token());

            SessionManager.getInstance().setcreditpoint(getActivity(),signin.getData().getWallet_points());

            DBActionsUser.add(
                    UserDetails.getCustomerKey(), UserDetails.getCustomerKey(),
                    UserDetails.getCustomerEmail(),
                    UserDetails.getCustomerName(),
                    UserDetails.getCustomerLastName(),
                    UserDetails.getCustomerMobile(),
                    UserDetails.getAccessToken());

            Toast.makeText(getActivity(), R.string.txt_login_success, Toast.LENGTH_SHORT).show();

            bus.post(new AddressEvent());
            bus.post(new MainMenuRefreshEvent());
            bus.post(new SigninEvent());

            if (mCustomProgressDialog.isShowing()) {
                mCustomProgressDialog.dismiss();
            }

            MyActivity.DisplayHomePage(getActivity());
            getActivity().finish();


            if (goTo != null && goTo.trim().equals("profile")) {
                MyActivity.DisplayProfile(getActivity());
            } else if (goTo != null && goTo.trim().equals("order")) {
                MyActivity.DisplayOrder(getActivity());
            } else if (goTo != null && goTo.trim().equals("settings")) {
                MyActivity.DisplaySettings(getActivity());
            }
        } else {
            if (mCustomProgressDialog.isShowing()) {
                mCustomProgressDialog.dismiss();
            }
            Toast.makeText(getActivity(), signin.getMessage(), Toast.LENGTH_LONG).show();
        }
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(int position) {
        if (mListener != null) {
            mListener.onFragmentInteraction(position);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        EventBus.getDefault().register(this);
        mCustomProgressDialog = CustomProgressDialog.getInstance();
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {

        }
    }

    public final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        EventBus.getDefault().unregister(this);
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(int position);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);

    }

    public void onEvent(final DummyEvent ev) {
    }

    private void setUpFacebook() {
        mLoginButton.setReadPermissions(Arrays.asList("public_profile, email"));
        callbackManager = CallbackManager.Factory.create();

        mLoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                if (!mCustomProgressDialog.isShowing()) {
                    mCustomProgressDialog.ChageMessage(getActivity(), getString(R.string.dialog_please_wait), getString(R.string.dialog_please_wait));
                    mCustomProgressDialog.show(getActivity());
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
                                        birthday = object.getString("birthday") == null ? "" : object.getString("birthday");
                                    } catch (Exception e) {
                                        e.printStackTrace();
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
                                    signInToTheApp(email, firstName, lastName, birthday, id, mToken, imageUrl);

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


                /**
                 * AccessTokenTracker to manage logout
                 */
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
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.container, FBSignUp.newInstance(email, firstName, lastName, birthday, id, token, imageUrl)).commit();
                } else {

                    SessionManager.getInstance().setcreditpoint(getActivity(),fbResponse.getData().getWallet_points());
                    saveDataToLocalFromFbLogin(fbResponse);
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

                Toast.makeText(getActivity(), getString(R.string.toast_login_success), Toast.LENGTH_SHORT).show();

                bus.post(new AddressEvent());
                bus.post(new MainMenuRefreshEvent());
                bus.post(new SigninEvent());

                if (mCustomProgressDialog.isShowing()) {
                    mCustomProgressDialog.dismiss();
                }

                MyActivity.DisplayHomePage(getActivity());
                getActivity().finish();

                if (goTo != null && goTo.trim().equals("profile")) {
                    MyActivity.DisplayProfile(getActivity());
                } else if (goTo != null && goTo.trim().equals("order")) {
                    MyActivity.DisplayOrder(getActivity());
                } else if (goTo != null && goTo.trim().equals("settings")) {
                    MyActivity.DisplaySettings(getActivity());
                }
            } else {
                if (mCustomProgressDialog.isShowing()) {
                    mCustomProgressDialog.dismiss();
                }
                Toast.makeText(getActivity(), signin.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }

    private void signOutFB() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if (accessToken != null) {
            LoginManager.getInstance().logOut();
        }

        mLoginButton.performClick();
    }

}
