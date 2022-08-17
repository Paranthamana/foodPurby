package com.foodpurby.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by android1 on 12/28/2015.
 */
public class Tokens {
    private Integer start_index;
    private Integer network;
    private String more;
    private Integer valid_until;
    private Integer page_num;
    private Integer page_size;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * @return The start_index
     */
    public Integer getStart_index() {
        return start_index;
    }

    /**
     * @param start_index The start_index
     */
    public void setStart_index(Integer start_index) {
        this.start_index = start_index;
    }

    /**
     * @return The network
     */
    public Integer getNetwork() {
        return network;
    }

    /**
     * @param network The network
     */
    public void setNetwork(Integer network) {
        this.network = network;
    }

    /**
     * @return The more
     */
    public String getMore() {
        return more;
    }

    /**
     * @param more The more
     */
    public void setMore(String more) {
        this.more = more;
    }

    /**
     * @return The valid_until
     */
    public Integer getValid_until() {
        return valid_until;
    }

    /**
     * @param valid_until The valid_until
     */
    public void setValid_until(Integer valid_until) {
        this.valid_until = valid_until;
    }

    /**
     * @return The page_num
     */
    public Integer getPage_num() {
        return page_num;
    }

    /**
     * @param page_num The page_num
     */
    public void setPage_num(Integer page_num) {
        this.page_num = page_num;
    }

    /**
     * @return The page_size
     */
    public Integer getPage_size() {
        return page_size;
    }

    /**
     * @param page_size The page_size
     */
    public void setPage_size(Integer page_size) {
        this.page_size = page_size;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }
}