package com.example.sklad.data.local;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class ProductDao_Impl implements ProductDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<ProductEntity> __insertionAdapterOfProductEntity;

  private final EntityDeletionOrUpdateAdapter<ProductEntity> __updateAdapterOfProductEntity;

  private final SharedSQLiteStatement __preparedStmtOfDelete;

  public ProductDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfProductEntity = new EntityInsertionAdapter<ProductEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `products` (`id`,`name`,`article`,`barcode`,`category`,`unit`,`purchasePrice`,`retailPrice`,`minStock`,`comment`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final ProductEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getName());
        statement.bindString(3, entity.getArticle());
        statement.bindString(4, entity.getBarcode());
        statement.bindString(5, entity.getCategory());
        statement.bindString(6, entity.getUnit());
        statement.bindDouble(7, entity.getPurchasePrice());
        statement.bindDouble(8, entity.getRetailPrice());
        statement.bindDouble(9, entity.getMinStock());
        statement.bindString(10, entity.getComment());
      }
    };
    this.__updateAdapterOfProductEntity = new EntityDeletionOrUpdateAdapter<ProductEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `products` SET `id` = ?,`name` = ?,`article` = ?,`barcode` = ?,`category` = ?,`unit` = ?,`purchasePrice` = ?,`retailPrice` = ?,`minStock` = ?,`comment` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final ProductEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getName());
        statement.bindString(3, entity.getArticle());
        statement.bindString(4, entity.getBarcode());
        statement.bindString(5, entity.getCategory());
        statement.bindString(6, entity.getUnit());
        statement.bindDouble(7, entity.getPurchasePrice());
        statement.bindDouble(8, entity.getRetailPrice());
        statement.bindDouble(9, entity.getMinStock());
        statement.bindString(10, entity.getComment());
        statement.bindLong(11, entity.getId());
      }
    };
    this.__preparedStmtOfDelete = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM products WHERE id=?";
        return _query;
      }
    };
  }

  @Override
  public Object insert(final ProductEntity item, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfProductEntity.insertAndReturnId(item);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object update(final ProductEntity item, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfProductEntity.handle(item);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object delete(final long id, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDelete.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, id);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDelete.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<ProductEntity>> getAll() {
    final String _sql = "SELECT * FROM products ORDER BY name";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"products"}, new Callable<List<ProductEntity>>() {
      @Override
      @NonNull
      public List<ProductEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfArticle = CursorUtil.getColumnIndexOrThrow(_cursor, "article");
          final int _cursorIndexOfBarcode = CursorUtil.getColumnIndexOrThrow(_cursor, "barcode");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfUnit = CursorUtil.getColumnIndexOrThrow(_cursor, "unit");
          final int _cursorIndexOfPurchasePrice = CursorUtil.getColumnIndexOrThrow(_cursor, "purchasePrice");
          final int _cursorIndexOfRetailPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "retailPrice");
          final int _cursorIndexOfMinStock = CursorUtil.getColumnIndexOrThrow(_cursor, "minStock");
          final int _cursorIndexOfComment = CursorUtil.getColumnIndexOrThrow(_cursor, "comment");
          final List<ProductEntity> _result = new ArrayList<ProductEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ProductEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpArticle;
            _tmpArticle = _cursor.getString(_cursorIndexOfArticle);
            final String _tmpBarcode;
            _tmpBarcode = _cursor.getString(_cursorIndexOfBarcode);
            final String _tmpCategory;
            _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            final String _tmpUnit;
            _tmpUnit = _cursor.getString(_cursorIndexOfUnit);
            final double _tmpPurchasePrice;
            _tmpPurchasePrice = _cursor.getDouble(_cursorIndexOfPurchasePrice);
            final double _tmpRetailPrice;
            _tmpRetailPrice = _cursor.getDouble(_cursorIndexOfRetailPrice);
            final double _tmpMinStock;
            _tmpMinStock = _cursor.getDouble(_cursorIndexOfMinStock);
            final String _tmpComment;
            _tmpComment = _cursor.getString(_cursorIndexOfComment);
            _item = new ProductEntity(_tmpId,_tmpName,_tmpArticle,_tmpBarcode,_tmpCategory,_tmpUnit,_tmpPurchasePrice,_tmpRetailPrice,_tmpMinStock,_tmpComment);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<ProductEntity>> search(final String q) {
    final String _sql = "SELECT * FROM products WHERE name LIKE ? OR article LIKE ? OR barcode LIKE ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 3);
    int _argIndex = 1;
    _statement.bindString(_argIndex, q);
    _argIndex = 2;
    _statement.bindString(_argIndex, q);
    _argIndex = 3;
    _statement.bindString(_argIndex, q);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"products"}, new Callable<List<ProductEntity>>() {
      @Override
      @NonNull
      public List<ProductEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfArticle = CursorUtil.getColumnIndexOrThrow(_cursor, "article");
          final int _cursorIndexOfBarcode = CursorUtil.getColumnIndexOrThrow(_cursor, "barcode");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfUnit = CursorUtil.getColumnIndexOrThrow(_cursor, "unit");
          final int _cursorIndexOfPurchasePrice = CursorUtil.getColumnIndexOrThrow(_cursor, "purchasePrice");
          final int _cursorIndexOfRetailPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "retailPrice");
          final int _cursorIndexOfMinStock = CursorUtil.getColumnIndexOrThrow(_cursor, "minStock");
          final int _cursorIndexOfComment = CursorUtil.getColumnIndexOrThrow(_cursor, "comment");
          final List<ProductEntity> _result = new ArrayList<ProductEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ProductEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpArticle;
            _tmpArticle = _cursor.getString(_cursorIndexOfArticle);
            final String _tmpBarcode;
            _tmpBarcode = _cursor.getString(_cursorIndexOfBarcode);
            final String _tmpCategory;
            _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            final String _tmpUnit;
            _tmpUnit = _cursor.getString(_cursorIndexOfUnit);
            final double _tmpPurchasePrice;
            _tmpPurchasePrice = _cursor.getDouble(_cursorIndexOfPurchasePrice);
            final double _tmpRetailPrice;
            _tmpRetailPrice = _cursor.getDouble(_cursorIndexOfRetailPrice);
            final double _tmpMinStock;
            _tmpMinStock = _cursor.getDouble(_cursorIndexOfMinStock);
            final String _tmpComment;
            _tmpComment = _cursor.getString(_cursorIndexOfComment);
            _item = new ProductEntity(_tmpId,_tmpName,_tmpArticle,_tmpBarcode,_tmpCategory,_tmpUnit,_tmpPurchasePrice,_tmpRetailPrice,_tmpMinStock,_tmpComment);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object byId(final long id, final Continuation<? super ProductEntity> $completion) {
    final String _sql = "SELECT * FROM products WHERE id=?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<ProductEntity>() {
      @Override
      @Nullable
      public ProductEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfArticle = CursorUtil.getColumnIndexOrThrow(_cursor, "article");
          final int _cursorIndexOfBarcode = CursorUtil.getColumnIndexOrThrow(_cursor, "barcode");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfUnit = CursorUtil.getColumnIndexOrThrow(_cursor, "unit");
          final int _cursorIndexOfPurchasePrice = CursorUtil.getColumnIndexOrThrow(_cursor, "purchasePrice");
          final int _cursorIndexOfRetailPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "retailPrice");
          final int _cursorIndexOfMinStock = CursorUtil.getColumnIndexOrThrow(_cursor, "minStock");
          final int _cursorIndexOfComment = CursorUtil.getColumnIndexOrThrow(_cursor, "comment");
          final ProductEntity _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpArticle;
            _tmpArticle = _cursor.getString(_cursorIndexOfArticle);
            final String _tmpBarcode;
            _tmpBarcode = _cursor.getString(_cursorIndexOfBarcode);
            final String _tmpCategory;
            _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            final String _tmpUnit;
            _tmpUnit = _cursor.getString(_cursorIndexOfUnit);
            final double _tmpPurchasePrice;
            _tmpPurchasePrice = _cursor.getDouble(_cursorIndexOfPurchasePrice);
            final double _tmpRetailPrice;
            _tmpRetailPrice = _cursor.getDouble(_cursorIndexOfRetailPrice);
            final double _tmpMinStock;
            _tmpMinStock = _cursor.getDouble(_cursorIndexOfMinStock);
            final String _tmpComment;
            _tmpComment = _cursor.getString(_cursorIndexOfComment);
            _result = new ProductEntity(_tmpId,_tmpName,_tmpArticle,_tmpBarcode,_tmpCategory,_tmpUnit,_tmpPurchasePrice,_tmpRetailPrice,_tmpMinStock,_tmpComment);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
