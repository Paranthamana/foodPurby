package com.foodpurby.ondbstorage;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "RESTAURANT_BRANCH".
*/
public class RestaurantBranchDao extends AbstractDao<RestaurantBranch, Long> {

    public static final String TABLENAME = "RESTAURANT_BRANCH";

    /**
     * Properties of entity RestaurantBranch.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property RestaurantKey = new Property(1, String.class, "restaurantKey", false, "RESTAURANT_KEY");
        public final static Property RestaurantBranchKey = new Property(2, String.class, "restaurantBranchKey", false, "RESTAURANT_BRANCH_KEY");
        public final static Property RestaurantBranchGroupKey = new Property(3, String.class, "restaurantBranchGroupKey", false, "RESTAURANT_BRANCH_GROUP_KEY");
        public final static Property RestaurantBranchName = new Property(4, String.class, "restaurantBranchName", false, "RESTAURANT_BRANCH_NAME");
        public final static Property RestaurantImageUrl = new Property(5, String.class, "restaurantImageUrl", false, "RESTAURANT_IMAGE_URL");
        public final static Property MinOrderPrice = new Property(6, Double.class, "minOrderPrice", false, "MIN_ORDER_PRICE");
        public final static Property MinOrderDeliveryPrice = new Property(7, double.class, "minOrderDeliveryPrice", false, "MIN_ORDER_DELIVERY_PRICE");
        public final static Property RestaurantAddress = new Property(8, String.class, "restaurantAddress", false, "RESTAURANT_ADDRESS");
        public final static Property RestaurantCuisines = new Property(9, String.class, "restaurantCuisines", false, "RESTAURANT_CUISINES");
        public final static Property RestaurantArea = new Property(10, String.class, "restaurantArea", false, "RESTAURANT_AREA");
        public final static Property RestaurantCity = new Property(11, String.class, "restaurantCity", false, "RESTAURANT_CITY");
        public final static Property RestaurantAverageRating = new Property(12, Double.class, "restaurantAverageRating", false, "RESTAURANT_AVERAGE_RATING");
        public final static Property RestaurantStatus = new Property(13, String.class, "restaurantStatus", false, "RESTAURANT_STATUS");
        public final static Property RestaurantFavouriteStatus = new Property(14, Boolean.class, "restaurantFavouriteStatus", false, "RESTAURANT_FAVOURITE_STATUS");
        public final static Property RestaurantDistance = new Property(15, Double.class, "restaurantDistance", false, "RESTAURANT_DISTANCE");
        public final static Property RestaurantDeliveryInMin = new Property(16, Integer.class, "restaurantDeliveryInMin", false, "RESTAURANT_DELIVERY_IN_MIN");
        public final static Property RestaurantPaymentTypes = new Property(17, Integer.class, "restaurantPaymentTypes", false, "RESTAURANT_PAYMENT_TYPES");
        public final static Property RestaurantOpeningTime = new Property(18, String.class, "restaurantOpeningTime", false, "RESTAURANT_OPENING_TIME");
        public final static Property RestaurantOfferText = new Property(19, String.class, "restaurantOfferText", false, "RESTAURANT_OFFER_TEXT");
        public final static Property RestaurantOfferCode = new Property(20, String.class, "restaurantOfferCode", false, "RESTAURANT_OFFER_CODE");
        public final static Property RestaurantOfferMinOrderValue = new Property(21, Double.class, "restaurantOfferMinOrderValue", false, "RESTAURANT_OFFER_MIN_ORDER_VALUE");
        public final static Property RestaurantPureVegetarianStatus = new Property(22, Boolean.class, "restaurantPureVegetarianStatus", false, "RESTAURANT_PURE_VEGETARIAN_STATUS");
        public final static Property RestaurantLatitude = new Property(23, Double.class, "restaurantLatitude", false, "RESTAURANT_LATITUDE");
        public final static Property RestaurantLongitude = new Property(24, Double.class, "restaurantLongitude", false, "RESTAURANT_LONGITUDE");
        public final static Property ActiveStatus = new Property(25, Boolean.class, "activeStatus", false, "ACTIVE_STATUS");
    };


    public RestaurantBranchDao(DaoConfig config) {
        super(config);
    }
    
    public RestaurantBranchDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"RESTAURANT_BRANCH\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"RESTAURANT_KEY\" TEXT," + // 1: restaurantKey
                "\"RESTAURANT_BRANCH_KEY\" TEXT," + // 2: restaurantBranchKey
                "\"RESTAURANT_BRANCH_GROUP_KEY\" TEXT," + // 3: restaurantBranchGroupKey
                "\"RESTAURANT_BRANCH_NAME\" TEXT," + // 4: restaurantBranchName
                "\"RESTAURANT_IMAGE_URL\" TEXT," + // 5: restaurantImageUrl
                "\"MIN_ORDER_PRICE\" REAL," + // 6: minOrderPrice
                "\"MIN_ORDER_DELIVERY_PRICE\" REAL NOT NULL ," + // 7: minOrderDeliveryPrice
                "\"RESTAURANT_ADDRESS\" TEXT," + // 8: restaurantAddress
                "\"RESTAURANT_CUISINES\" TEXT," + // 9: restaurantCuisines
                "\"RESTAURANT_AREA\" TEXT," + // 10: restaurantArea
                "\"RESTAURANT_CITY\" TEXT," + // 11: restaurantCity
                "\"RESTAURANT_AVERAGE_RATING\" REAL," + // 12: restaurantAverageRating
                "\"RESTAURANT_STATUS\" TEXT," + // 13: restaurantStatus
                "\"RESTAURANT_FAVOURITE_STATUS\" INTEGER," + // 14: restaurantFavouriteStatus
                "\"RESTAURANT_DISTANCE\" REAL," + // 15: restaurantDistance
                "\"RESTAURANT_DELIVERY_IN_MIN\" INTEGER," + // 16: restaurantDeliveryInMin
                "\"RESTAURANT_PAYMENT_TYPES\" INTEGER," + // 17: restaurantPaymentTypes
                "\"RESTAURANT_OPENING_TIME\" TEXT," + // 18: restaurantOpeningTime
                "\"RESTAURANT_OFFER_TEXT\" TEXT," + // 19: restaurantOfferText
                "\"RESTAURANT_OFFER_CODE\" TEXT," + // 20: restaurantOfferCode
                "\"RESTAURANT_OFFER_MIN_ORDER_VALUE\" REAL," + // 21: restaurantOfferMinOrderValue
                "\"RESTAURANT_PURE_VEGETARIAN_STATUS\" INTEGER," + // 22: restaurantPureVegetarianStatus
                "\"RESTAURANT_LATITUDE\" REAL," + // 23: restaurantLatitude
                "\"RESTAURANT_LONGITUDE\" REAL," + // 24: restaurantLongitude
                "\"ACTIVE_STATUS\" INTEGER);"); // 25: activeStatus
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"RESTAURANT_BRANCH\"";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, RestaurantBranch entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String restaurantKey = entity.getRestaurantKey();
        if (restaurantKey != null) {
            stmt.bindString(2, restaurantKey);
        }
 
        String restaurantBranchKey = entity.getRestaurantBranchKey();
        if (restaurantBranchKey != null) {
            stmt.bindString(3, restaurantBranchKey);
        }
 
        String restaurantBranchGroupKey = entity.getRestaurantBranchGroupKey();
        if (restaurantBranchGroupKey != null) {
            stmt.bindString(4, restaurantBranchGroupKey);
        }
 
        String restaurantBranchName = entity.getRestaurantBranchName();
        if (restaurantBranchName != null) {
            stmt.bindString(5, restaurantBranchName);
        }
 
        String restaurantImageUrl = entity.getRestaurantImageUrl();
        if (restaurantImageUrl != null) {
            stmt.bindString(6, restaurantImageUrl);
        }
 
        Double minOrderPrice = entity.getMinOrderPrice();
        if (minOrderPrice != null) {
            stmt.bindDouble(7, minOrderPrice);
        }
        stmt.bindDouble(8, entity.getMinOrderDeliveryPrice());
 
        String restaurantAddress = entity.getRestaurantAddress();
        if (restaurantAddress != null) {
            stmt.bindString(9, restaurantAddress);
        }
 
        String restaurantCuisines = entity.getRestaurantCuisines();
        if (restaurantCuisines != null) {
            stmt.bindString(10, restaurantCuisines);
        }
 
        String restaurantArea = entity.getRestaurantArea();
        if (restaurantArea != null) {
            stmt.bindString(11, restaurantArea);
        }
 
        String restaurantCity = entity.getRestaurantCity();
        if (restaurantCity != null) {
            stmt.bindString(12, restaurantCity);
        }
 
        Double restaurantAverageRating = entity.getRestaurantAverageRating();
        if (restaurantAverageRating != null) {
            stmt.bindDouble(13, restaurantAverageRating);
        }
 
        String restaurantStatus = entity.getRestaurantStatus();
        if (restaurantStatus != null) {
            stmt.bindString(14, restaurantStatus);
        }
 
        Boolean restaurantFavouriteStatus = entity.getRestaurantFavouriteStatus();
        if (restaurantFavouriteStatus != null) {
            stmt.bindLong(15, restaurantFavouriteStatus ? 1L: 0L);
        }
 
        Double restaurantDistance = entity.getRestaurantDistance();
        if (restaurantDistance != null) {
            stmt.bindDouble(16, restaurantDistance);
        }
 
        Integer restaurantDeliveryInMin = entity.getRestaurantDeliveryInMin();
        if (restaurantDeliveryInMin != null) {
            stmt.bindLong(17, restaurantDeliveryInMin);
        }
 
        Integer restaurantPaymentTypes = entity.getRestaurantPaymentTypes();
        if (restaurantPaymentTypes != null) {
            stmt.bindLong(18, restaurantPaymentTypes);
        }
 
        String restaurantOpeningTime = entity.getRestaurantOpeningTime();
        if (restaurantOpeningTime != null) {
            stmt.bindString(19, restaurantOpeningTime);
        }
 
        String restaurantOfferText = entity.getRestaurantOfferText();
        if (restaurantOfferText != null) {
            stmt.bindString(20, restaurantOfferText);
        }
 
        String restaurantOfferCode = entity.getRestaurantOfferCode();
        if (restaurantOfferCode != null) {
            stmt.bindString(21, restaurantOfferCode);
        }
 
        Double restaurantOfferMinOrderValue = entity.getRestaurantOfferMinOrderValue();
        if (restaurantOfferMinOrderValue != null) {
            stmt.bindDouble(22, restaurantOfferMinOrderValue);
        }
 
        Boolean restaurantPureVegetarianStatus = entity.getRestaurantPureVegetarianStatus();
        if (restaurantPureVegetarianStatus != null) {
            stmt.bindLong(23, restaurantPureVegetarianStatus ? 1L: 0L);
        }
 
        Double restaurantLatitude = entity.getRestaurantLatitude();
        if (restaurantLatitude != null) {
            stmt.bindDouble(24, restaurantLatitude);
        }
 
        Double restaurantLongitude = entity.getRestaurantLongitude();
        if (restaurantLongitude != null) {
            stmt.bindDouble(25, restaurantLongitude);
        }
 
        Boolean activeStatus = entity.getActiveStatus();
        if (activeStatus != null) {
            stmt.bindLong(26, activeStatus ? 1L: 0L);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public RestaurantBranch readEntity(Cursor cursor, int offset) {
        RestaurantBranch entity = new RestaurantBranch( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // restaurantKey
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // restaurantBranchKey
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // restaurantBranchGroupKey
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // restaurantBranchName
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // restaurantImageUrl
            cursor.isNull(offset + 6) ? null : cursor.getDouble(offset + 6), // minOrderPrice
            cursor.getDouble(offset + 7), // minOrderDeliveryPrice
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // restaurantAddress
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // restaurantCuisines
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // restaurantArea
            cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11), // restaurantCity
            cursor.isNull(offset + 12) ? null : cursor.getDouble(offset + 12), // restaurantAverageRating
            cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13), // restaurantStatus
            cursor.isNull(offset + 14) ? null : cursor.getShort(offset + 14) != 0, // restaurantFavouriteStatus
            cursor.isNull(offset + 15) ? null : cursor.getDouble(offset + 15), // restaurantDistance
            cursor.isNull(offset + 16) ? null : cursor.getInt(offset + 16), // restaurantDeliveryInMin
            cursor.isNull(offset + 17) ? null : cursor.getInt(offset + 17), // restaurantPaymentTypes
            cursor.isNull(offset + 18) ? null : cursor.getString(offset + 18), // restaurantOpeningTime
            cursor.isNull(offset + 19) ? null : cursor.getString(offset + 19), // restaurantOfferText
            cursor.isNull(offset + 20) ? null : cursor.getString(offset + 20), // restaurantOfferCode
            cursor.isNull(offset + 21) ? null : cursor.getDouble(offset + 21), // restaurantOfferMinOrderValue
            cursor.isNull(offset + 22) ? null : cursor.getShort(offset + 22) != 0, // restaurantPureVegetarianStatus
            cursor.isNull(offset + 23) ? null : cursor.getDouble(offset + 23), // restaurantLatitude
            cursor.isNull(offset + 24) ? null : cursor.getDouble(offset + 24), // restaurantLongitude
            cursor.isNull(offset + 25) ? null : cursor.getShort(offset + 25) != 0 // activeStatus
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, RestaurantBranch entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setRestaurantKey(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setRestaurantBranchKey(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setRestaurantBranchGroupKey(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setRestaurantBranchName(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setRestaurantImageUrl(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setMinOrderPrice(cursor.isNull(offset + 6) ? null : cursor.getDouble(offset + 6));
        entity.setMinOrderDeliveryPrice(cursor.getDouble(offset + 7));
        entity.setRestaurantAddress(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setRestaurantCuisines(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setRestaurantArea(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        entity.setRestaurantCity(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
        entity.setRestaurantAverageRating(cursor.isNull(offset + 12) ? null : cursor.getDouble(offset + 12));
        entity.setRestaurantStatus(cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13));
        entity.setRestaurantFavouriteStatus(cursor.isNull(offset + 14) ? null : cursor.getShort(offset + 14) != 0);
        entity.setRestaurantDistance(cursor.isNull(offset + 15) ? null : cursor.getDouble(offset + 15));
        entity.setRestaurantDeliveryInMin(cursor.isNull(offset + 16) ? null : cursor.getInt(offset + 16));
        entity.setRestaurantPaymentTypes(cursor.isNull(offset + 17) ? null : cursor.getInt(offset + 17));
        entity.setRestaurantOpeningTime(cursor.isNull(offset + 18) ? null : cursor.getString(offset + 18));
        entity.setRestaurantOfferText(cursor.isNull(offset + 19) ? null : cursor.getString(offset + 19));
        entity.setRestaurantOfferCode(cursor.isNull(offset + 20) ? null : cursor.getString(offset + 20));
        entity.setRestaurantOfferMinOrderValue(cursor.isNull(offset + 21) ? null : cursor.getDouble(offset + 21));
        entity.setRestaurantPureVegetarianStatus(cursor.isNull(offset + 22) ? null : cursor.getShort(offset + 22) != 0);
        entity.setRestaurantLatitude(cursor.isNull(offset + 23) ? null : cursor.getDouble(offset + 23));
        entity.setRestaurantLongitude(cursor.isNull(offset + 24) ? null : cursor.getDouble(offset + 24));
        entity.setActiveStatus(cursor.isNull(offset + 25) ? null : cursor.getShort(offset + 25) != 0);
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(RestaurantBranch entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(RestaurantBranch entity) {
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
