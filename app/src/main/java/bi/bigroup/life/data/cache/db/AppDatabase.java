package bi.bigroup.life.data.cache.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import bi.bigroup.life.data.models.employees.Employee;
import bi.bigroup.life.data.models.feed.Feed;

import static bi.bigroup.life.utils.Constants.DB_NAME;

@Database(entities = {Employee.class, Feed.class}, version = 2)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    public abstract EmployeeDao employeeDao();

    public abstract FeedDao feedDao();

//    public abstract BookDao bookModel();

    public static AppDatabase getInMemoryDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DB_NAME)
                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}