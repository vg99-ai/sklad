package com.example.sklad.data.local;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class AppDatabase_Impl extends AppDatabase {
  private volatile ProductDao _productDao;

  private volatile LocationDao _locationDao;

  private volatile StockDao _stockDao;

  private volatile IncomingDao _incomingDao;

  private volatile MovementDao _movementDao;

  private volatile SaleDao _saleDao;

  private volatile WriteOffDao _writeOffDao;

  private volatile OperationLogDao _operationLogDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `products` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT NOT NULL, `article` TEXT NOT NULL, `barcode` TEXT NOT NULL, `category` TEXT NOT NULL, `unit` TEXT NOT NULL, `purchasePrice` REAL NOT NULL, `retailPrice` REAL NOT NULL, `minStock` REAL NOT NULL, `comment` TEXT NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `locations` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT NOT NULL, `type` TEXT NOT NULL, `addressOrComment` TEXT NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `stocks` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `productId` INTEGER NOT NULL, `locationId` INTEGER NOT NULL, `quantity` REAL NOT NULL, FOREIGN KEY(`productId`) REFERENCES `products`(`id`) ON UPDATE NO ACTION ON DELETE NO ACTION , FOREIGN KEY(`locationId`) REFERENCES `locations`(`id`) ON UPDATE NO ACTION ON DELETE NO ACTION )");
        db.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_stocks_productId_locationId` ON `stocks` (`productId`, `locationId`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `incoming` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `productId` INTEGER NOT NULL, `quantity` REAL NOT NULL, `purchasePrice` REAL NOT NULL, `locationId` INTEGER NOT NULL, `date` INTEGER NOT NULL, `supplier` TEXT NOT NULL, `docNumber` TEXT NOT NULL, `comment` TEXT NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `movements` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `productId` INTEGER NOT NULL, `fromLocationId` INTEGER NOT NULL, `toLocationId` INTEGER NOT NULL, `quantity` REAL NOT NULL, `date` INTEGER NOT NULL, `comment` TEXT NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `sales` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `productId` INTEGER NOT NULL, `locationId` INTEGER NOT NULL, `quantity` REAL NOT NULL, `salePrice` REAL NOT NULL, `date` INTEGER NOT NULL, `customer` TEXT NOT NULL, `comment` TEXT NOT NULL, `amount` REAL NOT NULL, `profit` REAL NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `write_offs` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `productId` INTEGER NOT NULL, `locationId` INTEGER NOT NULL, `quantity` REAL NOT NULL, `reason` TEXT NOT NULL, `date` INTEGER NOT NULL, `comment` TEXT NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `operation_logs` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `date` INTEGER NOT NULL, `operationType` TEXT NOT NULL, `productId` INTEGER NOT NULL, `quantity` REAL NOT NULL, `fromLocationId` INTEGER, `toLocationId` INTEGER, `sum` REAL, `comment` TEXT NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '62ddbb16b584e672d8a6e228641071ed')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `products`");
        db.execSQL("DROP TABLE IF EXISTS `locations`");
        db.execSQL("DROP TABLE IF EXISTS `stocks`");
        db.execSQL("DROP TABLE IF EXISTS `incoming`");
        db.execSQL("DROP TABLE IF EXISTS `movements`");
        db.execSQL("DROP TABLE IF EXISTS `sales`");
        db.execSQL("DROP TABLE IF EXISTS `write_offs`");
        db.execSQL("DROP TABLE IF EXISTS `operation_logs`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        db.execSQL("PRAGMA foreign_keys = ON");
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsProducts = new HashMap<String, TableInfo.Column>(10);
        _columnsProducts.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProducts.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProducts.put("article", new TableInfo.Column("article", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProducts.put("barcode", new TableInfo.Column("barcode", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProducts.put("category", new TableInfo.Column("category", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProducts.put("unit", new TableInfo.Column("unit", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProducts.put("purchasePrice", new TableInfo.Column("purchasePrice", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProducts.put("retailPrice", new TableInfo.Column("retailPrice", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProducts.put("minStock", new TableInfo.Column("minStock", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProducts.put("comment", new TableInfo.Column("comment", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysProducts = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesProducts = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoProducts = new TableInfo("products", _columnsProducts, _foreignKeysProducts, _indicesProducts);
        final TableInfo _existingProducts = TableInfo.read(db, "products");
        if (!_infoProducts.equals(_existingProducts)) {
          return new RoomOpenHelper.ValidationResult(false, "products(com.example.sklad.data.local.ProductEntity).\n"
                  + " Expected:\n" + _infoProducts + "\n"
                  + " Found:\n" + _existingProducts);
        }
        final HashMap<String, TableInfo.Column> _columnsLocations = new HashMap<String, TableInfo.Column>(4);
        _columnsLocations.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLocations.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLocations.put("type", new TableInfo.Column("type", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLocations.put("addressOrComment", new TableInfo.Column("addressOrComment", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysLocations = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesLocations = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoLocations = new TableInfo("locations", _columnsLocations, _foreignKeysLocations, _indicesLocations);
        final TableInfo _existingLocations = TableInfo.read(db, "locations");
        if (!_infoLocations.equals(_existingLocations)) {
          return new RoomOpenHelper.ValidationResult(false, "locations(com.example.sklad.data.local.LocationEntity).\n"
                  + " Expected:\n" + _infoLocations + "\n"
                  + " Found:\n" + _existingLocations);
        }
        final HashMap<String, TableInfo.Column> _columnsStocks = new HashMap<String, TableInfo.Column>(4);
        _columnsStocks.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStocks.put("productId", new TableInfo.Column("productId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStocks.put("locationId", new TableInfo.Column("locationId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStocks.put("quantity", new TableInfo.Column("quantity", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysStocks = new HashSet<TableInfo.ForeignKey>(2);
        _foreignKeysStocks.add(new TableInfo.ForeignKey("products", "NO ACTION", "NO ACTION", Arrays.asList("productId"), Arrays.asList("id")));
        _foreignKeysStocks.add(new TableInfo.ForeignKey("locations", "NO ACTION", "NO ACTION", Arrays.asList("locationId"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesStocks = new HashSet<TableInfo.Index>(1);
        _indicesStocks.add(new TableInfo.Index("index_stocks_productId_locationId", true, Arrays.asList("productId", "locationId"), Arrays.asList("ASC", "ASC")));
        final TableInfo _infoStocks = new TableInfo("stocks", _columnsStocks, _foreignKeysStocks, _indicesStocks);
        final TableInfo _existingStocks = TableInfo.read(db, "stocks");
        if (!_infoStocks.equals(_existingStocks)) {
          return new RoomOpenHelper.ValidationResult(false, "stocks(com.example.sklad.data.local.StockEntity).\n"
                  + " Expected:\n" + _infoStocks + "\n"
                  + " Found:\n" + _existingStocks);
        }
        final HashMap<String, TableInfo.Column> _columnsIncoming = new HashMap<String, TableInfo.Column>(9);
        _columnsIncoming.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsIncoming.put("productId", new TableInfo.Column("productId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsIncoming.put("quantity", new TableInfo.Column("quantity", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsIncoming.put("purchasePrice", new TableInfo.Column("purchasePrice", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsIncoming.put("locationId", new TableInfo.Column("locationId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsIncoming.put("date", new TableInfo.Column("date", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsIncoming.put("supplier", new TableInfo.Column("supplier", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsIncoming.put("docNumber", new TableInfo.Column("docNumber", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsIncoming.put("comment", new TableInfo.Column("comment", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysIncoming = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesIncoming = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoIncoming = new TableInfo("incoming", _columnsIncoming, _foreignKeysIncoming, _indicesIncoming);
        final TableInfo _existingIncoming = TableInfo.read(db, "incoming");
        if (!_infoIncoming.equals(_existingIncoming)) {
          return new RoomOpenHelper.ValidationResult(false, "incoming(com.example.sklad.data.local.IncomingEntity).\n"
                  + " Expected:\n" + _infoIncoming + "\n"
                  + " Found:\n" + _existingIncoming);
        }
        final HashMap<String, TableInfo.Column> _columnsMovements = new HashMap<String, TableInfo.Column>(7);
        _columnsMovements.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMovements.put("productId", new TableInfo.Column("productId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMovements.put("fromLocationId", new TableInfo.Column("fromLocationId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMovements.put("toLocationId", new TableInfo.Column("toLocationId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMovements.put("quantity", new TableInfo.Column("quantity", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMovements.put("date", new TableInfo.Column("date", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMovements.put("comment", new TableInfo.Column("comment", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysMovements = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesMovements = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoMovements = new TableInfo("movements", _columnsMovements, _foreignKeysMovements, _indicesMovements);
        final TableInfo _existingMovements = TableInfo.read(db, "movements");
        if (!_infoMovements.equals(_existingMovements)) {
          return new RoomOpenHelper.ValidationResult(false, "movements(com.example.sklad.data.local.MovementEntity).\n"
                  + " Expected:\n" + _infoMovements + "\n"
                  + " Found:\n" + _existingMovements);
        }
        final HashMap<String, TableInfo.Column> _columnsSales = new HashMap<String, TableInfo.Column>(10);
        _columnsSales.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSales.put("productId", new TableInfo.Column("productId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSales.put("locationId", new TableInfo.Column("locationId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSales.put("quantity", new TableInfo.Column("quantity", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSales.put("salePrice", new TableInfo.Column("salePrice", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSales.put("date", new TableInfo.Column("date", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSales.put("customer", new TableInfo.Column("customer", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSales.put("comment", new TableInfo.Column("comment", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSales.put("amount", new TableInfo.Column("amount", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSales.put("profit", new TableInfo.Column("profit", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysSales = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesSales = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoSales = new TableInfo("sales", _columnsSales, _foreignKeysSales, _indicesSales);
        final TableInfo _existingSales = TableInfo.read(db, "sales");
        if (!_infoSales.equals(_existingSales)) {
          return new RoomOpenHelper.ValidationResult(false, "sales(com.example.sklad.data.local.SaleEntity).\n"
                  + " Expected:\n" + _infoSales + "\n"
                  + " Found:\n" + _existingSales);
        }
        final HashMap<String, TableInfo.Column> _columnsWriteOffs = new HashMap<String, TableInfo.Column>(7);
        _columnsWriteOffs.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWriteOffs.put("productId", new TableInfo.Column("productId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWriteOffs.put("locationId", new TableInfo.Column("locationId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWriteOffs.put("quantity", new TableInfo.Column("quantity", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWriteOffs.put("reason", new TableInfo.Column("reason", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWriteOffs.put("date", new TableInfo.Column("date", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWriteOffs.put("comment", new TableInfo.Column("comment", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysWriteOffs = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesWriteOffs = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoWriteOffs = new TableInfo("write_offs", _columnsWriteOffs, _foreignKeysWriteOffs, _indicesWriteOffs);
        final TableInfo _existingWriteOffs = TableInfo.read(db, "write_offs");
        if (!_infoWriteOffs.equals(_existingWriteOffs)) {
          return new RoomOpenHelper.ValidationResult(false, "write_offs(com.example.sklad.data.local.WriteOffEntity).\n"
                  + " Expected:\n" + _infoWriteOffs + "\n"
                  + " Found:\n" + _existingWriteOffs);
        }
        final HashMap<String, TableInfo.Column> _columnsOperationLogs = new HashMap<String, TableInfo.Column>(9);
        _columnsOperationLogs.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsOperationLogs.put("date", new TableInfo.Column("date", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsOperationLogs.put("operationType", new TableInfo.Column("operationType", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsOperationLogs.put("productId", new TableInfo.Column("productId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsOperationLogs.put("quantity", new TableInfo.Column("quantity", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsOperationLogs.put("fromLocationId", new TableInfo.Column("fromLocationId", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsOperationLogs.put("toLocationId", new TableInfo.Column("toLocationId", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsOperationLogs.put("sum", new TableInfo.Column("sum", "REAL", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsOperationLogs.put("comment", new TableInfo.Column("comment", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysOperationLogs = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesOperationLogs = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoOperationLogs = new TableInfo("operation_logs", _columnsOperationLogs, _foreignKeysOperationLogs, _indicesOperationLogs);
        final TableInfo _existingOperationLogs = TableInfo.read(db, "operation_logs");
        if (!_infoOperationLogs.equals(_existingOperationLogs)) {
          return new RoomOpenHelper.ValidationResult(false, "operation_logs(com.example.sklad.data.local.OperationLogEntity).\n"
                  + " Expected:\n" + _infoOperationLogs + "\n"
                  + " Found:\n" + _existingOperationLogs);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "62ddbb16b584e672d8a6e228641071ed", "bd32daacf88a65eebabe7b690027ad17");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "products","locations","stocks","incoming","movements","sales","write_offs","operation_logs");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    final boolean _supportsDeferForeignKeys = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP;
    try {
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = FALSE");
      }
      super.beginTransaction();
      if (_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA defer_foreign_keys = TRUE");
      }
      _db.execSQL("DELETE FROM `stocks`");
      _db.execSQL("DELETE FROM `products`");
      _db.execSQL("DELETE FROM `locations`");
      _db.execSQL("DELETE FROM `incoming`");
      _db.execSQL("DELETE FROM `movements`");
      _db.execSQL("DELETE FROM `sales`");
      _db.execSQL("DELETE FROM `write_offs`");
      _db.execSQL("DELETE FROM `operation_logs`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = TRUE");
      }
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(ProductDao.class, ProductDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(LocationDao.class, LocationDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(StockDao.class, StockDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(IncomingDao.class, IncomingDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(MovementDao.class, MovementDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(SaleDao.class, SaleDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(WriteOffDao.class, WriteOffDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(OperationLogDao.class, OperationLogDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public ProductDao productDao() {
    if (_productDao != null) {
      return _productDao;
    } else {
      synchronized(this) {
        if(_productDao == null) {
          _productDao = new ProductDao_Impl(this);
        }
        return _productDao;
      }
    }
  }

  @Override
  public LocationDao locationDao() {
    if (_locationDao != null) {
      return _locationDao;
    } else {
      synchronized(this) {
        if(_locationDao == null) {
          _locationDao = new LocationDao_Impl(this);
        }
        return _locationDao;
      }
    }
  }

  @Override
  public StockDao stockDao() {
    if (_stockDao != null) {
      return _stockDao;
    } else {
      synchronized(this) {
        if(_stockDao == null) {
          _stockDao = new StockDao_Impl(this);
        }
        return _stockDao;
      }
    }
  }

  @Override
  public IncomingDao incomingDao() {
    if (_incomingDao != null) {
      return _incomingDao;
    } else {
      synchronized(this) {
        if(_incomingDao == null) {
          _incomingDao = new IncomingDao_Impl(this);
        }
        return _incomingDao;
      }
    }
  }

  @Override
  public MovementDao movementDao() {
    if (_movementDao != null) {
      return _movementDao;
    } else {
      synchronized(this) {
        if(_movementDao == null) {
          _movementDao = new MovementDao_Impl(this);
        }
        return _movementDao;
      }
    }
  }

  @Override
  public SaleDao saleDao() {
    if (_saleDao != null) {
      return _saleDao;
    } else {
      synchronized(this) {
        if(_saleDao == null) {
          _saleDao = new SaleDao_Impl(this);
        }
        return _saleDao;
      }
    }
  }

  @Override
  public WriteOffDao writeOffDao() {
    if (_writeOffDao != null) {
      return _writeOffDao;
    } else {
      synchronized(this) {
        if(_writeOffDao == null) {
          _writeOffDao = new WriteOffDao_Impl(this);
        }
        return _writeOffDao;
      }
    }
  }

  @Override
  public OperationLogDao operationLogDao() {
    if (_operationLogDao != null) {
      return _operationLogDao;
    } else {
      synchronized(this) {
        if(_operationLogDao == null) {
          _operationLogDao = new OperationLogDao_Impl(this);
        }
        return _operationLogDao;
      }
    }
  }
}
