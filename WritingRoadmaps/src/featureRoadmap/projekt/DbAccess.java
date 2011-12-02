package featureRoadmap.projekt;

import java.io.IOException;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

public class DbAccess extends SQLiteOpenHelper {

	// The Android's default system path of your application database.
	private static String DB_NAME = "roadmaps";
	private static String DB_PATH = "/data/data/featureRoadmap.projekt/databases/";
	private static int DATABASE_VERSION = 3;
	private SQLiteDatabase myDataBase;
	private final Context myContext;

	private static final String SPRINT_TABLE_NAME = "sprints";
	private static final String SPRINT_NAME = "name";
	private static final String SPRINT_DESCRIPTION = "description";
	private static final String NEWS_BODY = "body";
	private static final String NEWS_ISSUE_DATE = "issue_date";

	private static final String SPRINT_TABLE_CREATE = "create table "
			+ SPRINT_TABLE_NAME + "(" + SPRINT_NAME + " varchar(50) primary key, "
			+ SPRINT_DESCRIPTION +")"; 
			//+ " varchar(50), " + NEWS_SUBTITLE + " varchar(50), "
			//+ NEWS_BODY + " text, " + NEWS_ISSUE_DATE + " text);";

	/**
	 * Constructor Takes and keeps a reference of the passed context in order to
	 * access to the application assets and resources.
	 * 
	 * @param context
	 */
	public DbAccess(Context context) {

		super(context, DB_NAME, null, DATABASE_VERSION);
		this.myContext = context;
		try {
			this.createDataBase();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Creates a empty database on the system and rewrites it with your own
	 * database.
	 * */
	public void createDataBase() throws IOException{
 
		//myContext.deleteDatabase(DB_NAME);
		
    	boolean dbExist = checkDataBase();
 
    	if(dbExist){
    		//do nothing - database already exist
    	}else{
 
    		//By calling this method and empty database will be created into the default system path
               //of your application so we are gonna be able to overwrite that database with our database.
    		myDataBase = this.getWritableDatabase();
    	}

		onCreate(myDataBase);
	}
 
	@Override
	public void onCreate(SQLiteDatabase db) {
		
//		db.execSQL("DROP TABLE 'news';");
		this.openDataBase();
		myDataBase.execSQL(SPRINT_TABLE_CREATE);
		
		myDataBase.execSQL("INSERT INTO sprints VALUES (" + 
		"sprintname" + 
		"sprintdesc" + ");");

	}

	/**
	 * Check if the database already exist to avoid re-copying the file each
	 * time you open the application.
	 * 
	 * @return true if it exists, false if it doesn't
	 */
	private boolean checkDataBase() {

		SQLiteDatabase checkDB = null;

		try {
			String myPath = DB_PATH + DB_NAME;
			checkDB = SQLiteDatabase.openDatabase(myPath, null,
					SQLiteDatabase.OPEN_READWRITE);

		} catch (SQLiteException e) {

			// database does't exist yet.

		}

		if (checkDB != null) {

			checkDB.close();

		}

		return checkDB != null ? true : false;
	}


	public void openDataBase() throws SQLException {

		// Open the database
		String myPath = DB_PATH + DB_NAME;
		myDataBase = SQLiteDatabase.openDatabase(myPath, null,
				SQLiteDatabase.OPEN_READONLY);
	}

	@Override
	public synchronized void close() {

		if (myDataBase != null)
			myDataBase.close();

		super.close();

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}
	
	public String getSprints()
	{
		Cursor c = myDataBase.query(SPRINT_TABLE_NAME, new String[] {
                SPRINT_NAME, SPRINT_DESCRIPTION }, null, null, null, null, null);
		String names = c.getString(0);
		return names;
	}

	// Add your public helper methods to access and get content from the
	// database.
	// You could return cursors by doing "return myDataBase.query(....)" so it'd
	// be easy
	// to you to create adapters for your views.

}