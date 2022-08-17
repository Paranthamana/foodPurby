package com.foodpurby.dialogs;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Vibrator;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.foodpurby.R;
import com.foodpurby.cart.ShowIngredientCustomItem;
import com.foodpurby.events.AddRemoveToCartEvent;
import com.foodpurby.ondbstorage.DBActionsCart;
import com.foodpurby.ondbstorage.DBActionsIngredients;
import com.foodpurby.ondbstorage.DBActionsIngredientsCategory;
import com.foodpurby.ondbstorage.Ingredients;
import com.foodpurby.ondbstorage.IngredientsCategory;
import com.foodpurby.ondbstorage.Products;
import com.foodpurby.util.Common;
import com.foodpurby.utillities.AppConfig;
import com.foodpurby.utillities.AppSharedValues;
import com.nineoldandroids.animation.ValueAnimator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import de.greenrobot.event.EventBus;

/**
 * Created by android1 on 12/17/2015.
 */
public class IngredientsSelection {

    Vibrator vibrator;
    Animation shake;

    AlertDialog.Builder mDialog;
    LinearLayout llIngredients;
    AlertDialog mAlertDilaog;
    LinearLayout mDynamicSize;
    ImageButton mUp, mDown;
    TextView mItemCount;
    TextView tvPrice;
    TextView tvProductName;

    Map<String, Ingredients> selectedIngredients = new HashMap<String, Ingredients>();

    Map<String, Integer> selectedIngredientsMinCountByCategory = new HashMap<String, Integer>();
    Map<String, Integer> selectedIngredientsCountByCategory = new HashMap<String, Integer>();

    Button btnAddToCart;
    Double ingredientPrice = 0.0;
    Double productPrice = 0.0;
    Integer count = 1;

    public void ShowDialog(final Context context, final LayoutInflater mInflater, final View targetView, final EventBus bus, final Products product) {
        mDialog = new AlertDialog.Builder(context, R.style.MyAlertDialogStyle);
        final View vDialog = mInflater.inflate(R.layout.dialog_cart_items, null);
        mDialog.setView(vDialog);

      //  FontsManager.initFormAssets(context, "Lato-Light.ttf");
      //  FontsManager.changeFonts(vDialog);

        mAlertDilaog = mDialog.create();
        mAlertDilaog.show();

        shake = AnimationUtils.loadAnimation(context, R.anim.shakeanim);
        vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);

        //                CustomGrid adapter = new CustomGrid(MainActivity.this, imageId);
        llIngredients = (LinearLayout) vDialog.findViewById(R.id.llIngredients);

        mUp = (ImageButton) vDialog.findViewById(R.id.up_button);
        mDown = (ImageButton) vDialog.findViewById(R.id.down_button);
        mItemCount = (TextView) vDialog.findViewById(R.id.DCI_TV_count);
        tvPrice = (TextView) vDialog.findViewById(R.id.tvPrice);
        btnAddToCart = (Button) vDialog.findViewById(R.id.btnAddToCart);
        tvProductName = (TextView) vDialog.findViewById(R.id.tvProductName);

        productPrice = product.getPrice();

        tvProductName.setText(product.getProductName());
        tvPrice.setText(Common.getPriceWithCurrencyCode(AppConfig.decimalFormat.format(product.getPrice())));
        tvPrice.setTypeface(Typeface.DEFAULT);

        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (MinSelectionStatus()) {
                    List<Ingredients> ingredients = new ArrayList<Ingredients>();

                    Iterator<Map.Entry<String, Ingredients>> iterator = selectedIngredients.entrySet().iterator();
                    while (iterator.hasNext()) {
                        Map.Entry<String, Ingredients> pairs = (Map.Entry<String, Ingredients>) iterator.next();
                        ingredients.add(pairs.getValue());
                    }

                    DBActionsCart.add(AppSharedValues.getSelectedRestaurantBranchKey(), product.getProductKey(), ingredients, Integer.valueOf(mItemCount.getText().toString()));
                    bus.post(new AddRemoveToCartEvent(true, targetView));

                    if (vDialog != null && vDialog.isShown()) {
                        mAlertDilaog.dismiss();
                    }
                } else {
                    Toast.makeText(context, "Please select minimum ingredients", Toast.LENGTH_LONG).show();
                }
            }
        });

        mUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mItemsValues = mItemCount.getText().toString();
                count = Integer.parseInt(mItemsValues) + 1;
                mItemCount.setText("" + count);

                if (count > AppConfig.MAX_CART_PER_ITEM) {

                    count = 99;
                    mItemCount.setText("" + count);

                    AlertDialog.Builder mDialog = new AlertDialog.Builder(context, R.style.MyAlertDialogStyle);
                    final View vDialog = mInflater.inflate(R.layout.dialog_my_cart_max_items, null);
                    Button btnDismiss = (Button) vDialog.findViewById(R.id.btnDismiss);
                    mDialog.setView(vDialog);
                    final AlertDialog mAlertDilaog = mDialog.create();
                    mAlertDilaog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                    mAlertDilaog.show();

                    btnDismiss.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (mAlertDilaog != null && mAlertDilaog.isShowing()) {
                                mAlertDilaog.dismiss();
                            }
                        }
                    });
                }

                CalculateCost();
            }
        });


        mDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mItemsValues = mItemCount.getText().toString();
                count = Integer.parseInt(mItemsValues) - 1;
                if (count > 0) {
                    mItemCount.setText("" + count);
                } else {
                    count = 1;
                }

                CalculateCost();

            }
        });

        List<IngredientsCategory> ingredientsCategories = DBActionsIngredientsCategory.getIngredientsCategory(product);


        String previousIngredientsCategory = "";
        boolean isAdded = false;

        LinearLayout mParentLayout = new LinearLayout(context);
        mParentLayout.setOrientation(LinearLayout.VERTICAL);

        LinearLayout mChildLayout = new LinearLayout(context);
        mChildLayout.setOrientation(LinearLayout.HORIZONTAL);

        for (IngredientsCategory ingredientsCategory : ingredientsCategories) {

            mParentLayout = new LinearLayout(context);
            mParentLayout.setOrientation(LinearLayout.VERTICAL);

            mDynamicSize = new LinearLayout(context);
            mDynamicSize.setOrientation(LinearLayout.VERTICAL);


            View viewCatTitle = mInflater.inflate(R.layout.row_ingredient_category_title, null);

          //  FontsManager.initFormAssets(context, "Lato-Light.ttf");
          //  FontsManager.changeFonts(viewCatTitle);

            final TextView tvCategoryTitle = (TextView) viewCatTitle.findViewById(R.id.tvCatTitle);
            tvCategoryTitle.setText(ingredientsCategory.getIngredientsCategoryName());

            final TextView tvMin = (TextView) viewCatTitle.findViewById(R.id.tvMin);
            tvMin.setText(ingredientsCategory.getMinSelection().toString() + " min");

            final TextView tvMax = (TextView) viewCatTitle.findViewById(R.id.tvMax);
            tvMax.setText(ingredientsCategory.getMaxSelection().toString() + " max");

            mParentLayout.addView(viewCatTitle);

            if (!selectedIngredientsMinCountByCategory.containsKey(ingredientsCategory.getIngredientsCategoryKey())) {
                selectedIngredientsMinCountByCategory.put(ingredientsCategory.getIngredientsCategoryKey(), ingredientsCategory.getMinSelection());
            }

            List<Ingredients> ingredients = DBActionsIngredients.getIngredients(product, ingredientsCategory.getIngredientsCategoryKey());
            int count = 0;
            for (Ingredients ingredient : ingredients) {

                if (count % 2 == 0) {

                    if (count > 0) {
                        isAdded = true;

                        mDynamicSize.addView(mChildLayout);
                    }

                    mChildLayout = new LinearLayout(context);
                    mChildLayout.setOrientation(LinearLayout.HORIZONTAL);
                }


                View view = mInflater.inflate(R.layout.row_textview, null);
                LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                p.weight = 1;
                view.setLayoutParams(p);
             //   FontsManager.initFormAssets(context, "Lato-Light.ttf");
             //   FontsManager.changeFonts(view);
                final TextView mTextSizeLabel = (TextView) view.findViewById(R.id.tv_label);

                mTextSizeLabel.setText(ingredient.getIngredientsName()+"\n"+Common.getPriceWithCurrencyCode(ingredient.getPrice()+""));
                mTextSizeLabel.setTypeface(Typeface.DEFAULT);

                ShowIngredientCustomItem showIngredientCustomItem = new ShowIngredientCustomItem(
                        ingredient,
                        false,
                        ingredientsCategory.getMinSelection(),
                        ingredientsCategory.getMaxSelection(),
                        ingredientsCategory.getRequiredSelection()
                );
                mTextSizeLabel.setTag(showIngredientCustomItem);

                mTextSizeLabel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        ShowIngredientCustomItem showIngredientCustomItem = (ShowIngredientCustomItem) v.getTag();

                        Integer totalSelected = 0;
                        if (selectedIngredientsCountByCategory.containsKey(showIngredientCustomItem.getIngredients().getIngredientsCategoryKey())) {
                            totalSelected = selectedIngredientsCountByCategory.get(showIngredientCustomItem.getIngredients().getIngredientsCategoryKey());
                        }
                        if (totalSelected < showIngredientCustomItem.getMaxSelection() && !v.isSelected()) {
                            selectedIngredientsCountByCategory.put(showIngredientCustomItem.getIngredients().getIngredientsCategoryKey(), totalSelected + 1);
                            v.setSelected(!v.isSelected());
                            if (v.isSelected()) {
                                showIngredientCustomItem.setIsSelected(true);
                                ingredientPrice = ingredientPrice + showIngredientCustomItem.getIngredients().getPrice();

                                if (!selectedIngredients.containsKey(showIngredientCustomItem.getIngredients().getIngredientsKey())) {
                                    selectedIngredients.put(showIngredientCustomItem.getIngredients().getIngredientsKey(), showIngredientCustomItem.getIngredients());
                                }
                            }
                        } else {

                            if (totalSelected - 1 >= 0 && v.isSelected()) {
                                selectedIngredientsCountByCategory.put(showIngredientCustomItem.getIngredients().getIngredientsCategoryKey(), totalSelected - 1);
                                v.setSelected(!v.isSelected());

                                showIngredientCustomItem.setIsSelected(false);
                                ingredientPrice = ingredientPrice - showIngredientCustomItem.getIngredients().getPrice();
                                if (selectedIngredients.containsKey(showIngredientCustomItem.getIngredients().getIngredientsKey())) {
                                    selectedIngredients.remove(showIngredientCustomItem.getIngredients().getIngredientsKey());
                                }
                            } else {
                                //tvMax.setTextColor(context.getResources().getColor(R.color.red));
                                tvMax.startAnimation(shake);

                                // Load initial and final colors.
                                final int initialColor = context.getResources().getColor(R.color.gray);
                                final int finalColor = context.getResources().getColor(R.color.toolbar_color);
                                changeViewColor(context, tvMax, initialColor, finalColor);
                                changeViewColor(context, tvMax, finalColor, initialColor);
                                //vibrator.vibrate(200);
                            }

                        }


                        CalculateCost();
                        v.setTag(showIngredientCustomItem);

                    }
                });
                view.setId(count);
                mChildLayout.addView(view);


                previousIngredientsCategory = ingredient.getIngredientsCategoryKey();
                isAdded = false;
                count++;
            }

            if (!isAdded) {
                mDynamicSize.addView(mChildLayout);
            }


            //String
            mParentLayout.addView(mDynamicSize);
            llIngredients.addView(mParentLayout);
        }
    }

    private void changeViewColor(final Context context, final TextView view, final int initialColor, final int finalColor) {
        ValueAnimator anim = ValueAnimator.ofFloat(0, 1);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                // Use animation position to blend colors.
                float position = animation.getAnimatedFraction();
                int blended = blendColors(initialColor, finalColor, position);

                // Apply blended color to the view.
                view.setTextColor(blended);
            }
        });

        anim.setDuration(500).start();
    }

    private int blendColors(int from, int to, float ratio) {
        final float inverseRatio = 1f - ratio;

        final float r = Color.red(to) * ratio + Color.red(from) * inverseRatio;
        final float g = Color.green(to) * ratio + Color.green(from) * inverseRatio;
        final float b = Color.blue(to) * ratio + Color.blue(from) * inverseRatio;

        return Color.rgb((int) r, (int) g, (int) b);
    }

    private Boolean MinSelectionStatus() {
        Boolean isMinSelected = true;
        for (String key : selectedIngredientsMinCountByCategory.keySet()) {
            Integer minCount = selectedIngredientsMinCountByCategory.get(key);
            Integer selectedCount = 0;
            if (selectedIngredientsCountByCategory.containsKey(key)) {
                selectedCount = selectedIngredientsCountByCategory.get(key);
            }

            if (minCount > selectedCount) {
                isMinSelected = false;
                break;
            }
        }

        return isMinSelected;
    }

    private void CalculateCost() {
        tvPrice.setText(Common.getPriceWithCurrencyCode(AppConfig.decimalFormat.format(count * (productPrice + ingredientPrice))));
        tvPrice.setTypeface(Typeface.DEFAULT);
    }
}
