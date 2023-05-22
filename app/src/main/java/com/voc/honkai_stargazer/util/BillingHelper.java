/*
 * Project Honkai Stargazer (崩壞•星穹觀星者) was
 * Created & Develop by Voc-夜芷冰 , Programmer of Xectorda
 * Copyright © 2023 Xectorda 版權所有
 */

package com.voc.honkai_stargazer.util;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.billingclient.api.AcknowledgePurchaseParams;
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
import com.android.billingclient.api.QueryPurchasesParams;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.common.collect.ImmutableList;
import com.voc.honkai_stargazer.data.MaterialItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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

    private Chip[] chips;

    public BillingHelper(Context context, Activity activity, Chip[] chips) {
        this.context = context;
        this.activity = activity;
        this.chips = chips;

        billingClient = BillingClient.newBuilder(context)
                .setListener(new PurchasesUpdatedListener() {
                    @Override
                    public void onPurchasesUpdated(@NonNull BillingResult billingResult, @Nullable List<Purchase> list) {
                        connectToGooglePlayBilling();
                    }
                })
                .enablePendingPurchases()
                .build();
        connectToGooglePlayBilling();
    }

    private void connectToGooglePlayBilling(){
        billingClient.startConnection(
                new BillingClientStateListener() {
                    @Override
                    public void onBillingServiceDisconnected() {
                        connectToGooglePlayBilling();
                    }

                    @Override
                    public void onBillingSetupFinished(@NonNull BillingResult billingResult) {
                        if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK){
                            getProductDetails();
                        }
                    }
                }
        );
    }

    private void getProductDetails(){
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
                    for (int pos = 0 ; pos < list.size() && pos < chips.length ; pos ++){
                        chips[pos].setText(list.get(pos).getOneTimePurchaseOfferDetails().getFormattedPrice());

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


                        chips[pos].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                billingClient.launchBillingFlow(activity, BillingFlowParams.newBuilder()
                                        .setProductDetailsParamsList(productDetailsParamsList)
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
            for (Chip chip : chips){
                chip.setOnClickListener(null);
                chip = null;
            }
        }
    }
}
