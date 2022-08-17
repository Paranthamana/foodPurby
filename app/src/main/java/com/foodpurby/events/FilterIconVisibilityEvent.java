package com.foodpurby.events;

/**
 * Created by android1 on 1/23/2016.
 */
public class FilterIconVisibilityEvent {

    public enum PageTitleType {
        None,
        Home,
        MenuItems,
        Signin,
        InviteAndEarn,
        Offers,
        category,
        Menu
    }

    public Integer getVisibility() {
        return visibility;
    }

    public void setVisibility(Integer visibility) {
        this.visibility = visibility;
    }

    public PageTitleType getPageTitleType() {
        return pageTitleType;
    }

    public void setPageTitleType(PageTitleType pageTitleType) {
        this.pageTitleType = pageTitleType;
    }

    private Integer visibility;
    private PageTitleType pageTitleType;

    public FilterIconVisibilityEvent(Integer visibility, PageTitleType pageTitleType) {
        this.visibility = visibility;
        this.pageTitleType = pageTitleType;
    }
}
