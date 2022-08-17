package com.foodpurby.ondbstorage;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "PRODUCTS".
*/
public class ProductsDao extends AbstractDao<Products, Long> {

    public static final String TABLENAME = "PRODUCTS";

    /**
     * Properties of entity Products.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property ProductKey = new Property(1, String.class, "productKey", false, "PRODUCT_KEY");
        public final static Property RestaurantBranchKey = new Property(2, String.class, "restaurantBranchKey", false, "RESTAURANT_BRANCH_KEY");
        public final static Property CategoryKey = new Property(3, String.class, "categoryKey", false, "CATEGORY_KEY");
        public final static Property CuisineKey = new Property(4, String.class, "cuisineKey", false, "CUISINE_KEY");
        public final static Property ProductName = new Property(5, String.class, "productName", false, "PRODUCT_NAME");
        public final static Property ProductDesc = new Property(6, String.class, "productDesc", false, "PRODUCT_DESC");
        public final static Property ProductImgUrl = new Property(7, String.class, "productImgUrl", false, "PRODUCT_IMG_URL");
        public final static Property Price = new Property(8, Double.class, "price", false, "PRICE");
        public final static Property Price_min = new Property(9, Double.class, "price_min", false, "PRICE_MIN");
        public final static Property Price_max = new Property(10, Double.class, "price_max", false, "PRICE_MAX");
        public final static Property Tax_amount = new Property(11, Double.class, "tax_amount", false, "TAX_AMOUNT");
        public final static Property Tax_included = new Property(12, Boolean.class, "tax_included", false, "TAX_INCLUDED");
        public final static Property Tax_value = new Property(13, Double.class, "tax_value", false, "TAX_VALUE");
        public final static Property Active = new Property(14, Boolean.class, "active", false, "ACTIVE");
        public final static Property In_stock = new Property(15, Boolean.class, "in_stock", false, "IN_STOCK");
        public final static Property Orders_accepted = new Property(16, Boolean.class, "orders_accepted", false, "ORDERS_ACCEPTED");
        public final static Property Preparation_time = new Property(17, Integer.class, "preparation_time", false, "PREPARATION_TIME");
        public final static Property Popularity = new Property(18, Integer.class, "popularity", false, "POPULARITY");
        public final static Property SortingNumber = new Property(19, Integer.class, "sortingNumber", false, "SORTING_NUMBER");
        public final static Property ProductType = new Property(20, Integer.class, "productType", false, "PRODUCT_TYPE");
        public final static Property ActiveStatus = new Property(21, Boolean.class, "activeStatus", false, "ACTIVE_STATUS");
    };


    public ProductsDao(DaoConfig config) {
        super(config);
    }
    
    public ProductsDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"PRODUCTS\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"PRODUCT_KEY\" TEXT NOT NULL ," + // 1: productKey
                "\"RESTAURANT_BRANCH_KEY\" TEXT NOT NULL ," + // 2: restaurantBranchKey
                "\"CATEGORY_KEY\" TEXT," + // 3: categoryKey
                "\"CUISINE_KEY\" TEXT NOT NULL ," + // 4: cuisineKey
                "\"PRODUCT_NAME\" TEXT," + // 5: productName
                "\"PRODUCT_DESC\" TEXT," + // 6: productDesc
                "\"PRODUCT_IMG_URL\" TEXT," + // 7: productImgUrl
                "\"PRICE\" REAL," + // 8: price
                "\"PRICE_MIN\" REAL," + // 9: price_min
                "\"PRICE_MAX\" REAL," + // 10: price_max
                "\"TAX_AMOUNT\" REAL," + // 11: tax_amount
                "\"TAX_INCLUDED\" INTEGER," + // 12: tax_included
                "\"TAX_VALUE\" REAL," + // 13: tax_value
                "\"ACTIVE\" INTEGER," + // 14: active
                "\"IN_STOCK\" INTEGER," + // 15: in_stock
                "\"ORDERS_ACCEPTED\" INTEGER," + // 16: orders_accepted
                "\"PREPARATION_TIME\" INTEGER," + // 17: preparation_time
                "\"POPULARITY\" INTEGER," + // 18: popularity
                "\"SORTING_NUMBER\" INTEGER," + // 19: sortingNumber
                "\"PRODUCT_TYPE\" INTEGER," + // 20: productType
                "\"ACTIVE_STATUS\" INTEGER);"); // 21: activeStatus
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"PRODUCTS\"";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, Products entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindString(2, entity.getProductKey());
        stmt.bindString(3, entity.getRestaurantBranchKey());
 
        String categoryKey = entity.getCategoryKey();
        if (categoryKey != null) {
            stmt.bindString(4, categoryKey);
        }
        stmt.bindString(5, entity.getCuisineKey());
 
        String productName = entity.getProductName();
        if (productName != null) {
            stmt.bindString(6, productName);
        }
 
        String productDesc = entity.getProductDesc();
        if (productDesc != null) {
            stmt.bindString(7, productDesc);
        }
 
        String productImgUrl = entity.getProductImgUrl();
        if (productImgUrl != null) {
            stmt.bindString(8, productImgUrl);
        }
 
        Double price = entity.getPrice();
        if (price != null) {
            stmt.bindDouble(9, price);
        }
 
        Double price_min = entity.getPrice_min();
        if (price_min != null) {
            stmt.bindDouble(10, price_min);
        }
 
        Double price_max = entity.getPrice_max();
        if (price_max != null) {
            stmt.bindDouble(11, price_max);
        }
 
        Double tax_amount = entity.getTax_amount();
        if (tax_amount != null) {
            stmt.bindDouble(12, tax_amount);
        }
 
        Boolean tax_included = entity.getTax_included();
        if (tax_included != null) {
            stmt.bindLong(13, tax_included ? 1L: 0L);
        }
 
        Double tax_value = entity.getTax_value();
        if (tax_value != null) {
            stmt.bindDouble(14, tax_value);
        }
 
        Boolean active = entity.getActive();
        if (active != null) {
            stmt.bindLong(15, active ? 1L: 0L);
        }
 
        Boolean in_stock = entity.getIn_stock();
        if (in_stock != null) {
            stmt.bindLong(16, in_stock ? 1L: 0L);
        }
 
        Boolean orders_accepted = entity.getOrders_accepted();
        if (orders_accepted != null) {
            stmt.bindLong(17, orders_accepted ? 1L: 0L);
        }
 
        Integer preparation_time = entity.getPreparation_time();
        if (preparation_time != null) {
            stmt.bindLong(18, preparation_time);
        }
 
        Integer popularity = entity.getPopularity();
        if (popularity != null) {
            stmt.bindLong(19, popularity);
        }
 
        Integer sortingNumber = entity.getSortingNumber();
        if (sortingNumber != null) {
            stmt.bindLong(20, sortingNumber);
        }
 
        Integer productType = entity.getProductType();
        if (productType != null) {
            stmt.bindLong(21, productType);
        }
 
        Boolean activeStatus = entity.getActiveStatus();
        if (activeStatus != null) {
            stmt.bindLong(22, activeStatus ? 1L: 0L);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public Products readEntity(Cursor cursor, int offset) {
        Products entity = new Products( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getString(offset + 1), // productKey
            cursor.getString(offset + 2), // restaurantBranchKey
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // categoryKey
            cursor.getString(offset + 4), // cuisineKey
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // productName
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // productDesc
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // productImgUrl
            cursor.isNull(offset + 8) ? null : cursor.getDouble(offset + 8), // price
            cursor.isNull(offset + 9) ? null : cursor.getDouble(offset + 9), // price_min
            cursor.isNull(offset + 10) ? null : cursor.getDouble(offset + 10), // price_max
            cursor.isNull(offset + 11) ? null : cursor.getDouble(offset + 11), // tax_amount
            cursor.isNull(offset + 12) ? null : cursor.getShort(offset + 12) != 0, // tax_included
            cursor.isNull(offset + 13) ? null : cursor.getDouble(offset + 13), // tax_value
            cursor.isNull(offset + 14) ? null : cursor.getShort(offset + 14) != 0, // active
            cursor.isNull(offset + 15) ? null : cursor.getShort(offset + 15) != 0, // in_stock
            cursor.isNull(offset + 16) ? null : cursor.getShort(offset + 16) != 0, // orders_accepted
            cursor.isNull(offset + 17) ? null : cursor.getInt(offset + 17), // preparation_time
            cursor.isNull(offset + 18) ? null : cursor.getInt(offset + 18), // popularity
            cursor.isNull(offset + 19) ? null : cursor.getInt(offset + 19), // sortingNumber
            cursor.isNull(offset + 20) ? null : cursor.getInt(offset + 20), // productType
            cursor.isNull(offset + 21) ? null : cursor.getShort(offset + 21) != 0 // activeStatus
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, Products entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setProductKey(cursor.getString(offset + 1));
        entity.setRestaurantBranchKey(cursor.getString(offset + 2));
        entity.setCategoryKey(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setCuisineKey(cursor.getString(offset + 4));
        entity.setProductName(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setProductDesc(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setProductImgUrl(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setPrice(cursor.isNull(offset + 8) ? null : cursor.getDouble(offset + 8));
        entity.setPrice_min(cursor.isNull(offset + 9) ? null : cursor.getDouble(offset + 9));
        entity.setPrice_max(cursor.isNull(offset + 10) ? null : cursor.getDouble(offset + 10));
        entity.setTax_amount(cursor.isNull(offset + 11) ? null : cursor.getDouble(offset + 11));
        entity.setTax_included(cursor.isNull(offset + 12) ? null : cursor.getShort(offset + 12) != 0);
        entity.setTax_value(cursor.isNull(offset + 13) ? null : cursor.getDouble(offset + 13));
        entity.setActive(cursor.isNull(offset + 14) ? null : cursor.getShort(offset + 14) != 0);
        entity.setIn_stock(cursor.isNull(offset + 15) ? null : cursor.getShort(offset + 15) != 0);
        entity.setOrders_accepted(cursor.isNull(offset + 16) ? null : cursor.getShort(offset + 16) != 0);
        entity.setPreparation_time(cursor.isNull(offset + 17) ? null : cursor.getInt(offset + 17));
        entity.setPopularity(cursor.isNull(offset + 18) ? null : cursor.getInt(offset + 18));
        entity.setSortingNumber(cursor.isNull(offset + 19) ? null : cursor.getInt(offset + 19));
        entity.setProductType(cursor.isNull(offset + 20) ? null : cursor.getInt(offset + 20));
        entity.setActiveStatus(cursor.isNull(offset + 21) ? null : cursor.getShort(offset + 21) != 0);
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(Products entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(Products entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}
