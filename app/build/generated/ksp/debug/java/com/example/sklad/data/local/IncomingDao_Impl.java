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
public final class IncomingDao_Impl implements IncomingDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<IncomingEntity> __insertionAdapterOfIncomingEntity;

  public IncomingDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfIncomingEntity = new EntityInsertionAdapter<IncomingEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `incoming` (`id`,`productId`,`quantity`,`purchasePrice`,`locationId`,`date`,`supplier`,`docNumber`,`comment`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final IncomingEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getProductId());
        statement.bindDouble(3, entity.getQuantity());
        statement.bindDouble(4, entity.getPurchasePrice());
        statement.bindLong(5, entity.getLocationId());
        statement.bindLong(6, entity.getDate());
        statement.bindString(7, entity.getSupplier());
        statement.bindString(8, entity.getDocNumber());
        statement.bindString(9, entity.getComment());
      }
    };
  }

  @Override
  public Object insert(final IncomingEntity item, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfIncomingEntity.insert(item);
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
