package com.foodpurby.screens;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.foodpurby.util.CustomProgressDialog;
import com.foodpurby.R;

import org.json.JSONObject;

import de.greenrobot.event.EventBus;

public class MyPaymentOnline_PayU extends AppCompatActivity {

    private EventBus bus = EventBus.getDefault();
    private CustomProgressDialog mCustomProgressDialog;

    ProgressBar mProgress;

    private ListView lvAddress;
    Toolbar mToolbar;
    TextView mToolHeading;

    ImageView imgCart;
    TextView tvCart;

    WebView wvPayU;
    int Flag = 0;
    JSONObject mJsonObject = null;
    private String mServiceProvider = "payu_paisa";
    private String mProductInfo = "fadfudge Products"; //Passing String only
    private String mHash; // This will create below randomly
    private String mMerchantKey = "QIxmPb";
    private String mSalt = "0ED2e641";
    private String mBaseURL = "https://secure.payu.in";
    private String mAction = ""; // For Final URL

    String redirectUrl = "";
    String cancelUrl = "";
    String mFirstName = "";
    String mEmailId = "";
    String mAmount = "";
    String mPhone = "";
    String mTXNId = "";

    private String SUCCESS_URL = "";//https://www.fadfudge.com/api/v3/shop/response_payumoney.html";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        setContentView(R.layout.activity_my_payment_online_payu);
        /*mCustomProgressDialog = CustomProgressDialog.getInstance();
        mProgress = (ProgressBar) findViewById(R.id.progressBar);
        if (!mCustomProgressDialog.isShowing()) {
            mCustomProgressDialog.ChageMessage(this, "Please wait...", "Please wait...");
            mCustomProgressDialog.show(this);
        }
        if (getIntent().getExtras() != null &&
                getIntent().getExtras().getString("redirectUrl") != null &&
                getIntent().getExtras().getString("cancelUrl") != null
                ) {
            redirectUrl = getIntent().getExtras().getString("redirectUrl");
            SUCCESS_URL = redirectUrl;
            cancelUrl = getIntent().getExtras().getString("cancelUrl");
            mFirstName = getIntent().getExtras().getString("mFirstName");
            mEmailId = getIntent().getExtras().getString("mEmailId");
            mAmount = getIntent().getExtras().getString("mAmount");
            mPhone = getIntent().getExtras().getString("mPhone");
            mTXNId = getIntent().getExtras().getString("mTXNId");

        }
        wvPayU = (WebView) findViewById(R.id.wvPayU);
        wvPayU.requestFocusFromTouch();

        mToolbar = (Toolbar) findViewById(R.id.tb_header);
        mToolbar.setNavigationIcon(R.drawable.back);
        mToolHeading = (TextView) findViewById(R.id.tool_title);
        mToolHeading.setText(R.string.txt_online_payment_payumoney);

        setSupportActionBar(mToolbar);
        imgCart = (ImageView) findViewById(R.id.imgCart);
        imgCart.setVisibility(View.GONE);
        tvCart = (TextView) findViewById(R.id.tvCart);
        tvCart.setText(Common.getPriceWithCurrencyCode(AppConfig.decimalFormat.format(AppSharedValues.getGrandTotalPrice())));

        TextView tvCart = (TextView) mToolbar.findViewById(R.id.tvCart);
        tvCart.setText(Common.getPriceWithCurrencyCode(AppConfig.decimalFormat.format(AppSharedValues.getGrandTotalPrice())));

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });

*//**
 * Creating Hash Key
 *//*


        mHash = Common.PaymentGateways.hashCal("SHA-512", mMerchantKey + "|" +
                mTXNId + "|" +
                mAmount + "|" +
                mProductInfo + "|" +
                mFirstName + "|" +
                mEmailId + "|||||||||||" +
                mSalt);

        mAction = mBaseURL.concat("/_payment");

        *//**
         * WebView Client
         *//*
        wvPayU.setWebViewClient(new WebViewClient() {



            @Override
            public void onReceivedSslError(WebView view,
                                           SslErrorHandler handler, SslError error) {
                Toast.makeText(MyPaymentOnline_PayU.this, "SSL error! " + error, Toast.LENGTH_SHORT).show();
                handler.proceed();
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {


                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                wvPayU.loadUrl("javascript:window.HTMLOUT.processHTML('<head>'+document.getElementsByTagName('html')[0].innerHTML+'</head>');");
                mCustomProgressDialog.dismiss();
                super.onPageFinished(view, url);

            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                if (!mCustomProgressDialog.isShowing()) {
                    mCustomProgressDialog.ChageMessage(MyPaymentOnline_PayU.this, "Please wait...", "Please wait...");
                    mCustomProgressDialog.show(MyPaymentOnline_PayU.this);
                }
                System.out.println("SUCCESS_URL :" + SUCCESS_URL + "RESPONSE URL :" + url);

                if (url.equals(SUCCESS_URL)) {
                    wvPayU.setVisibility(View.GONE);
                    mProgress.setVisibility(View.VISIBLE);//loadUrl("about:blank");

                } else {
                    wvPayU.setVisibility(View.VISIBLE);
                    mProgress.setVisibility(View.INVISIBLE);

                }

            }
        });
        wvPayU.setVisibility(View.VISIBLE);
        wvPayU.getSettings().setBuiltInZoomControls(true);
        wvPayU.getSettings().setCacheMode(2);
        wvPayU.getSettings().setDomStorageEnabled(true);
        wvPayU.clearHistory();
        wvPayU.clearCache(true);
        wvPayU.getSettings().setJavaScriptEnabled(true);
        wvPayU.getSettings().setSupportZoom(true);
        wvPayU.getSettings().setUseWideViewPort(false);
        wvPayU.getSettings().setLoadWithOverviewMode(false);
        wvPayU.addJavascriptInterface(new MyJavaScriptInterface(), "HTMLOUT");
        // wvPayU.addJavascriptInterface(new PayUJavaScriptInterface(getActivity()), "PayUMoney");
        *//**
         * Mapping Compulsory Key Value Pairs
         *//*
        Map<String, String> mapParams = new HashMap<>();
        mapParams.put("key", mMerchantKey);
        mapParams.put("txnid", mTXNId);
        mapParams.put("amount", String.valueOf(mAmount));
        mapParams.put("productinfo", mProductInfo);
        mapParams.put("firstname", mFirstName);
        mapParams.put("email", mEmailId);
        mapParams.put("phone", mPhone);
        mapParams.put("surl", redirectUrl);
        mapParams.put("furl", cancelUrl);
        mapParams.put("hash", mHash);
        mapParams.put("service_provider", mServiceProvider);

        webViewClientPost(wvPayU, mAction, mapParams.entrySet());*/

    }


   /* public void webViewClientPost(WebView webView, String url,
                                  Collection<Map.Entry<String, String>> postData) {
        StringBuilder sb = new StringBuilder();

        sb.append("<html><head></head>");
        sb.append("<body onload='form1.submit()'>");
        sb.append(String.format("<form id='form1' action='%s' method='%s'>", url, "post"));

        for (Map.Entry<String, String> item : postData) {
            sb.append(String.format("<input name='%s' type='hidden' value='%s' />", item.getKey(), item.getValue()));
        }
        sb.append("</form></body></html>");

        webView.loadData(sb.toString(), "text/html", "utf-8");
    }

   public class MyJavaScriptInterface {
        @JavascriptInterface
        public void processHTML(String html) {
            // process the html as needed by the app

            String oAuthDetails = null;
            oAuthDetails = Html.fromHtml(html).toString();
//            wvPayU.loadUrl("");

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (mCustomProgressDialog.isShowing()) {
                        mCustomProgressDialog.dismiss();
                    }

                }
            }, 12000);

            try {
                //html = sb.toString().substring(html.indexOf("["), html.lastIndexOf("]") + 1);
                mJsonObject = new JSONObject(oAuthDetails);
                String status = mJsonObject.getString("status");
                if (status.equalsIgnoreCase("success")) {
                    try {
                        mCustomProgressDialog.dismiss();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    String Okey = mJsonObject.getJSONObject("data").getString("order_key");
                    String oTotal = mJsonObject.getJSONObject("data").getString("order_total");
                    MyActivity.DisplayPaymentOrderConfirmed(MyPaymentOnline_PayU.this, Okey, Double.valueOf(oTotal));
                    Toast.makeText(MyPaymentOnline_PayU.this, status, Toast.LENGTH_SHORT).show();
                    Flag = 1;
                } else {
                    Flag = 2;

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                mCustomProgressDialog.dismiss();
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                            finish();
                        }
                    });
                    Toast.makeText(MyPaymentOnline_PayU.this, R.string.toast_transaction_failed, Toast.LENGTH_SHORT).show();
                    // ((MyCartList) getActivity()).MoveNext(MyCartList.HOME_FRAGMENT);
                    //((PayUMoneyActivity.CashonDeliveryInterface) getActivity()).onFragmentCODPAyu();
                }
                wvPayU.setVisibility(View.VISIBLE);

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

        public class PayUJavaScriptInterface {
            Context mContext;

            *//**
             * Instantiate the interface and set the context
             *//*
            PayUJavaScriptInterface(Context c) {
                mContext = c;
            }

            public void success(long id, final String paymentId) {
                runOnUiThread(new Runnable() {

                    public void run() {
                        Toast.makeText(MyPaymentOnline_PayU.this, R.string.toast_payment_successful, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }

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
        if (id == android.R.id.home) {
            finish();
            overridePendingTransition(R.anim.anim_left_to_right, R.anim.anim_right_to_left);
        }
        return super.onOptionsItemSelected(item);
    }

    public void onEvent(final AddRemoveToCartEvent ev) {
        runOnUiThread(new Runnable() {
            public void run() {

            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }*/
}