package com.example.sklad.data.local;

import androidx.annotation.NonNull;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class SaleDao_Impl implements SaleDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<SaleEntity> __insertionAdapterOfSaleEntity;

  public SaleDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfSaleEntity = new EntityInsertionAdapter<SaleEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `sales` (`id`,`productId`,`locationId`,`quantity`,`salePrice`,`date`,`customer`,`comment`,`amount`,`profit`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final SaleEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getProductId());
        statement.bindLong(3, entity.getLocationId());
        statement.bindDouble(4, entity.getQuantity());
        statement.bindDouble(5, entity.getSalePrice());
        statement.bindLong(6, entity.getDate());
        statement.bindString(7, entity.getCustomer());
        statement.bindString(8, entity.getComment());
        statement.bindDouble(9, entity.getAmount());
        statement.bindDouble(10, entity.getProfit());
      }
    };
  }

  @Override
  public Object insert(final SaleEntity item, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfSaleEntity.insert(item);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
