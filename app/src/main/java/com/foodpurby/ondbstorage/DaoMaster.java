package com.foodpurby.ondbstorage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import de.greenrobot.dao.AbstractDaoMaster;
import de.greenrobot.dao.identityscope.IdentityScopeType;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * Master of DAO (schema version 1000): knows all DAOs.
*/
public class DaoMaster extends AbstractDaoMaster {
    public static final int SCHEMA_VERSION = 1000;

    /** Creates underlying database table using DAOs. */
    public static void createAllTables(SQLiteDatabase db, boolean ifNotExists) {
        CuisineFilterDao.createTable(db, ifNotExists);
        RestaurantBranchDao.createTable(db, ifNotExists);
        GroupDao.createTable(db, ifNotExists);
        CuisineDao.createTable(db, ifNotExists);
        CategoryDao.createTable(db, ifNotExists);
        ProductsDao.createTable(db, ifNotExists);
        IngredientsCategoryDao.createTable(db, ifNotExists);
        IngredientsDao.createTable(db, ifNotExists);
        CartProductsDao.createTable(db, ifNotExists);
        CartProductsIngredientsDao.createTable(db, ifNotExists);
        UserAddressDao.createTable(db, ifNotExists);
        UserDao.createTable(db, ifNotExists);
        AppSettingsDao.createTable(db, ifNotExists);
    }
    
    /** Drops underlying database table using DAOs. */
    public static void dropAllTables(SQLiteDatabase db, boolean ifExists) {
        CuisineFilterDao.dropTable(db, ifExists);
        RestaurantBranchDao.dropTable(db, ifExists);
        GroupDao.dropTable(db, ifExists);
        CuisineDao.dropTable(db, ifExists);
        CategoryDao.dropTable(db, ifExists);
        ProductsDao.dropTable(db, ifExists);
        IngredientsCategoryDao.dropTable(db, ifExists);
        IngredientsDao.dropTable(db, ifExists);
        CartProductsDao.dropTable(db, ifExists);
        CartProductsIngredientsDao.dropTable(db, ifExists);
        UserAddressDao.dropTable(db, ifExists);
        UserDao.dropTable(db, ifExists);
        AppSettingsDao.dropTable(db, ifExists);
    }
    
    public static abstract class OpenHelper extends SQLiteOpenHelper {

        public OpenHelper(Context context, String name, CursorFactory factory) {
            super(context, name, factory, SCHEMA_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.i("greenDAO", "Creating tables for schema version " + SCHEMA_VERSION);
            createAllTables(db, false);
        }
    }
    
    /** WARNING: Drops all table on Upgrade! Use only during development. */
    public static class DevOpenHelper extends OpenHelper {
        public DevOpenHelper(Context context, String name, CursorFactory factory) {
            super(context, name, factory);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.i("greenDAO", "Upgrading schema from version " + oldVersion + " to " + newVersion + " by dropping all tables");
            dropAllTables(db, true);
            onCreate(db);
        }
    }

    public DaoMaster(SQLiteDatabase db) {
        super(db, SCHEMA_VERSION);
        registerDaoClass(CuisineFilterDao.class);
        registerDaoClass(RestaurantBranchDao.class);
        registerDaoClass(GroupDao.class);
        registerDaoClass(CuisineDao.class);
        registerDaoClass(CategoryDao.class);
        registerDaoClass(ProductsDao.class);
        registerDaoClass(IngredientsCategoryDao.class);
        registerDaoClass(IngredientsDao.class);
        registerDaoClass(CartProductsDao.class);
        registerDaoClass(CartProductsIngredientsDao.class);
        registerDaoClass(UserAddressDao.class);
        registerDaoClass(UserDao.class);
        registerDaoClass(AppSettingsDao.class);
    }
    
    public DaoSession newSession() {
        return new DaoSession(db, IdentityScopeType.Session, daoConfigMap);
    }
    
    public DaoSession newSession(IdentityScopeType type) {
        return new DaoSession(db, type, daoConfigMap);
    }
    
}
