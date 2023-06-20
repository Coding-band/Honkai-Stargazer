/*
 * Project Honkai Stargazer (崩壞•星穹觀星者) was
 * Created & Develop by Voc-夜芷冰 , Programmer of Xectorda
 * Copyright © 2023 Xectorda 版權所有
 */

package com.voc.honkai_stargazer.util;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.ConsumeParams;
import com.android.billingclient.api.ConsumeResponseListener;
import com.android.billingclient.api.ProductDetails;
import com.android.billingclient.api.ProductDetailsResponseListener;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.QueryProductDetailsParams;
import com.google.common.collect.ImmutableList;
import com.voc.honkai_stargazer.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class BillingHelper {
    public static final String DONATION_HKD_8 = "donation_hkd_8";
    public static final String DONATION_HKD_30 = "donation_hkd_30";
    public static final String DONATION_HKD_50 = "donation_hkd_50";
    public static final String DONATION_HKD_100 = "donation_hkd_100";
    private Context context;
    private Activity activity;

    private List<ProductDetails> productDetailsListX = null;
    private final String TAG = "BillingHelper";

    private List<QueryProductDetailsParams.Product> productList = new ArrayList<>() ;

    private List<String> purchaseItemIDs = new ArrayList<String>() {{
        add(DONATION_HKD_8);
        add(DONATION_HKD_30);
        add(DONATION_HKD_50);
        add(DONATION_HKD_100);
    }};

    private BillingClient billingClient ;

    private LinearLayout[] lls;

    public static final String PAYMENT_SUCCESS = "PAYMENT_SUCCESS";
    public static final String PAYMENT_FAILED = "PAYMENT_FAILED";
    public static final String PAYMENT_USER_CANCELLED = "PAYMENT_USER_CANCELLED";

    Dialog dialog = null;
    public BillingHelper(Context context, Activity activity, LinearLayout[] lls) {
        this.context = context;
        this.activity = activity;
        this.lls = lls;

        initBilling();
        connectToGooglePlayBilling(lls);
    }

    private void initBilling() {
        System.out.println("initBilling");
        billingClient = BillingClient.newBuilder(context)
                .setListener(new PurchasesUpdatedListener() {
                    @Override
                    public void onPurchasesUpdated(@NonNull BillingResult billingResult, @Nullable List<Purchase> purchases) {
                        if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK && purchases != null) {
                            for (Purchase purchase : purchases) {
                                handlePurchase(purchase);
                            }

                            displayDialog(PAYMENT_SUCCESS,billingResult);

                        } else if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.USER_CANCELED) {
                            // Handle an error caused by a user cancelling the purchase flow.
                            displayDialog(PAYMENT_USER_CANCELLED,billingResult);
                        } else {
                            displayDialog(PAYMENT_FAILED,billingResult);
                        }
                    }
                })
                .enablePendingPurchases()
                .build();
    }

    private void displayDialog(String resultTag, BillingResult billingResult) {
        if (resultTag.equals(PAYMENT_USER_CANCELLED)) return;
        if (dialog != null) return;
        dialog = new Dialog(context, R.style.FilterDialogStyle_F);
        View view = View.inflate(context, R.layout.fragment_donation_result, null);
        ImageView donation_ico = view.findViewById(R.id.donation_ico);
        TextView donation_thx = view.findViewById(R.id.donation_thx);
        TextView donation_msg = view.findViewById(R.id.donation_msg);
        TextView donation_error_msg = view.findViewById(R.id.donation_error_msg);
        Button donation_close = view.findViewById(R.id.donation_close);
        donation_error_msg.setVisibility(View.GONE);
        donation_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog.isShowing()){
                    dialog.dismiss();
                    dialog = null;
                }
            }
        });

        switch (resultTag){
            case PAYMENT_SUCCESS: {
                donation_ico.setImageResource(R.drawable.pom_pom_success);
                donation_thx.setText(context.getString(R.string.setting_donation_thx_success));
                donation_msg.setText(context.getString(R.string.setting_donation_msg_success));
                break;
            }
            case PAYMENT_FAILED: {
                donation_ico.setImageResource(R.drawable.pom_pom_failed_issue);
                donation_thx.setText(context.getString(R.string.setting_donation_thx_failed));
                donation_msg.setText(context.getString(R.string.setting_donation_msg_failed));
                donation_error_msg.setVisibility(View.VISIBLE);
                donation_error_msg.setText("ERROR CODE ["+String.valueOf(billingResult.getResponseCode())+"]"+(!billingResult.getDebugMessage().isEmpty() ? "\n"+billingResult.getDebugMessage() : ""));
                break;
            }
        }

        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(true);
        Window dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        lp.gravity = Gravity.CENTER;
        dialogWindow.setAttributes(lp);

        if (!dialog.isShowing()){
            dialog.show();
        }
    }

    void handlePurchase(Purchase purchase) {
        // Purchase retrieved from BillingClient#queryPurchasesAsync or your PurchasesUpdatedListener.

        // Verify the purchase.
        // Ensure entitlement was not already granted for this purchaseToken.
        // Grant entitlement to the user.

        ConsumeParams consumeParams =
                ConsumeParams.newBuilder()
                        .setPurchaseToken(purchase.getPurchaseToken())
                        .build();

        ConsumeResponseListener listener = new ConsumeResponseListener() {
            @Override
            public void onConsumeResponse(BillingResult billingResult, String purchaseToken) {
                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                    // Handle the success of the consume operation.

                }
            }
        };

        billingClient.consumeAsync(consumeParams, listener);
    }
    private void connectToGooglePlayBilling(LinearLayout[] lls){
        if (billingClient == null){
            initBilling();
        }

        billingClient.startConnection(
                new BillingClientStateListener() {
                    @Override
                    public void onBillingServiceDisconnected() {
                        connectToGooglePlayBilling(lls);
                    }

                    @Override
                    public void onBillingSetupFinished(@NonNull BillingResult billingResult) {
                        if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK){
                            getProductDetails(lls);
                        }
                    }
                }
        );
    }

    private void getProductDetails(LinearLayout[] lls){
        productList = new ArrayList<>();

        for (int x = 0 ; x < purchaseItemIDs.size() ; x ++){
            productList.add(
                    QueryProductDetailsParams.Product.newBuilder()
                            .setProductId(purchaseItemIDs.get(x))
                            .setProductType(BillingClient.ProductType.INAPP)
                            .build());
        }

        QueryProductDetailsParams queryProductDetailsParams = QueryProductDetailsParams.newBuilder().setProductList(productList).build();
        billingClient.queryProductDetailsAsync(queryProductDetailsParams, new ProductDetailsResponseListener() {
            @Override
            public void onProductDetailsResponse(@NonNull BillingResult billingResult, @NonNull List<ProductDetails> list) {
                Collections.sort(list, new Comparator<ProductDetails>() {
                    public int compare(ProductDetails obj1, ProductDetails obj2) {
                        int price1 = 0;
                        int price2 = 0;
                        if (obj1.getProductId().split("_").length >=3){
                            price1 = Integer.parseInt(obj1.getProductId().split("_")[2]);
                        }
                        if (obj2.getProductId().split("_").length >=3){
                            price2 = Integer.parseInt(obj2.getProductId().split("_")[2]);
                        }
                        return Integer.compare(price1, price2);
                    }
                });

                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK && list != null){

                    System.out.println("list : "+list.size() + " | lls : "+lls.length);
                    for (int pos = 0 ; pos < list.size() && pos < lls.length ; pos ++){
                        ImmutableList productDetailsParamsList =
                                ImmutableList.of(
                                        BillingFlowParams.ProductDetailsParams.newBuilder()
                                                // retrieve a value for "productDetails" by calling queryProductDetailsAsync()
                                                .setProductDetails(list.get(pos))
                                                // to get an offer token, call ProductDetails.getSubscriptionOfferDetails()
                                                // for a list of offers that are available to the user
                                                //.setOfferToken(selectedOfferToken)
                                                .build()
                                );

                        lls[pos].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                billingClient.launchBillingFlow(activity, BillingFlowParams.newBuilder()
                                        .setProductDetailsParamsList(productDetailsParamsList)
                                        .setIsOfferPersonalized(true)
                                        .build()
                                );
                            }
                        });

                    }
                }
            }
        });
    }

    public void close(){
        if (billingClient != null){
            billingClient.endConnection();
            billingClient = null;
            for (LinearLayout linearLayout : lls){
                linearLayout.setOnClickListener(null);
                linearLayout = null;
            }
        }
    }
}
