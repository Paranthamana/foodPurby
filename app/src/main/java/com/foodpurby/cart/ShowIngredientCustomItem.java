package com.foodpurby.cart;


import com.foodpurby.ondbstorage.Ingredients;

/**
 * Created by android1 on 12/17/2015.
 */
public class ShowIngredientCustomItem {

    private Ingredients ingredients;
    private boolean IsSelected;

    private Integer minSelection;
    private Integer maxSelection;
    private Integer requiredSelection;

    public Ingredients getIngredients() {
        return ingredients;
    }

    public void setIngredients(Ingredients ingredients) {
        this.ingredients = ingredients;
    }

    public boolean isSelected() {
        return IsSelected;
    }

    public Integer getRequiredSelection() {
        return requiredSelection;
    }

    public void setRequiredSelection(Integer requiredSelection) {
        this.requiredSelection = requiredSelection;
    }

    public Integer getMinSelection() {
        return minSelection;
    }

    public void setMinSelection(Integer minSelection) {
        this.minSelection = minSelection;
    }

    public Integer getMaxSelection() {
        return this.maxSelection;
    }

    public void setMaxSelection(Integer maxSelection) {
        this.maxSelection = maxSelection;
    }

    public void setIsSelected(boolean isSelected) {
        IsSelected = isSelected;
    }
    public ShowIngredientCustomItem(Ingredients ingredients, boolean isSelected, Integer minSelection, Integer maxSelection, Integer requiredSelection) {
        this.ingredients = ingredients;
        this.IsSelected = isSelected;
        this.minSelection = minSelection;
        this.maxSelection = maxSelection;
        this.requiredSelection = requiredSelection;
    }
}
