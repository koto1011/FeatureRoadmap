package com.wpfandroid.dbaccess;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.wpfandroid.pojo.Milestone;
import com.wpfandroid.pojo.Roadmap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

public class DataHelper implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3753208037576232319L;
	private static final String DATABASE_NAME = "FeatureRoadmap.db";
	private static final int DATABASE_VERSION = 1;

	private static final String TABLE_NAME_ROADMAP = "roadmap";
	private static final String TABLE_NAME_MILESTONE = "milestone";

	private Context context;
	private SQLiteDatabase db;

	private SQLiteStatement insertStmtRoadmap;
	private SQLiteStatement insertStmtMilestone;

//	private SQLiteStatement updateStmtMilestoneById;

	private static final String INSERT_ROADMAP = "insert into "
			+ TABLE_NAME_ROADMAP
			+ "(name, start_date, end_date, project_id) values (?, ?, ?, ?)";
	private static final String INSERT_MILESTONE = "insert into "
			+ TABLE_NAME_MILESTONE
			+ "(name, description, date, roadmap_id) values (?, ?, ?, ?)";

	public DataHelper(Context context) {
		this.context = context;
		OpenHelper openHelper = new OpenHelper(this.context);
		this.db = openHelper.getWritableDatabase();
		this.insertStmtRoadmap = this.db.compileStatement(INSERT_ROADMAP);
		this.insertStmtMilestone = this.db.compileStatement(INSERT_MILESTONE);
	}

	public Roadmap getRoadmapByName(String name) {
		Log.d("EXAMPLE", "getRoadmapByName - Begin");
		Roadmap roadmap = null;
		Cursor cursor = this.db.query(TABLE_NAME_ROADMAP, new String[] {
				"id", "name", "start_date", "end_date", "project_id" }, "name = '"
				+ name + "'", null, null, null, null);

		if (!cursor.moveToFirst())
			Log.d("EXAMPLE", "cursor is empty");

		if (cursor.moveToFirst()) {
			roadmap = new Roadmap(cursor.getInt(0),
					cursor.getString(1), cursor.getString(2),
					cursor.getString(3), cursor.getInt(4));
		}

		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
			Log.d("EXAMPLE", "cursor is closed");
		}

		Log.d("EXAMPLE", "getRoadmapByName - End");
		return roadmap;
	}

	public Roadmap getRoadmapById(int id) {
		Log.d("EXAMPLE", "getRoadmapById - Begin");
		Roadmap roadmap = null;
		Cursor cursor = this.db.query(TABLE_NAME_ROADMAP, new String[] {
				"id", "name", "start_date", "end_date", "project_id" }, "id = "
				+ String.valueOf(id), null, null, null, null);

		if (!cursor.moveToFirst())
			Log.d("EXAMPLE", "cursor is empty");

		if (cursor.moveToFirst()) {
			roadmap = new Roadmap(cursor.getInt(0),
					cursor.getString(1), cursor.getString(2),
					cursor.getString(3), cursor.getInt(4));
		}

		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
			Log.d("EXAMPLE", "cursor is closed");
		}

		Log.d("EXAMPLE", "getRoadmapById - End");
		return roadmap;
	}
	
	public Milestone getMilestoneById(int id) {
		Log.d("EXAMPLE", "getRoadmapById - Begin");
		Milestone milestone = null;
		Cursor cursor = this.db.query(TABLE_NAME_MILESTONE, new String[] {
				"id", "name", "description", "date", "roadmap_id" }, "id = "
				+ String.valueOf(id), null, null, null, null);

		if (!cursor.moveToFirst())
			Log.d("EXAMPLE", "cursor is empty");

		if (cursor.moveToFirst()) {
			milestone = new Milestone(cursor.getInt(0),
					cursor.getString(1), cursor.getString(2),
					cursor.getString(3), this.getRoadmapById(cursor.getInt(4)));
		}

		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
			Log.d("EXAMPLE", "cursor is closed");
		}

		Log.d("EXAMPLE", "getRoadmapById - End");
		return milestone;
	}

	public Roadmap createRoadmap(String name, String start_date, String end_date,
			int project_id) {
		this.insertStmtRoadmap.bindString(1, name);
		this.insertStmtRoadmap.bindString(2, start_date);
		this.insertStmtRoadmap.bindString(3, end_date);
		this.insertStmtRoadmap.bindLong(4, project_id);
		this.insertStmtRoadmap.executeInsert();
		return this.getRoadmapByName(name);
	}

	public Milestone createMilestone(String name, String description, String date,
			int roadmap_id) {
		this.insertStmtMilestone.bindString(1, name);
		this.insertStmtMilestone.bindString(2, description);
		this.insertStmtMilestone.bindString(3, date);
		this.insertStmtMilestone.bindLong(4, roadmap_id);
		this.insertStmtMilestone.executeInsert();
		return this.getMilestoneByName(name);
	}
	
	public Milestone getMilestoneByName(String name) {
		Log.d("EXAMPLE", "getMilestoneByName - Begin");
		Milestone milestone = null;
		Cursor cursor = this.db.query(TABLE_NAME_MILESTONE, new String[] {
				"id", "name", "description", "date", "roadmap_id" }, "name = '"
				+ name + "'", null, null, null, null);

		if (!cursor.moveToFirst())
			Log.d("EXAMPLE", "cursor is empty");

		if (cursor.moveToFirst()) {
			milestone = new Milestone(cursor.getInt(0),
					cursor.getString(1), cursor.getString(2),
					cursor.getString(3), this.getRoadmapById(cursor.getInt(4)));
		}

		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
			Log.d("EXAMPLE", "cursor is closed");
		}

		Log.d("EXAMPLE", "getMilestoneByName - End");
		return milestone;
	}

	public Milestone updateMilestone(Milestone milestone) {
		Log.d("EXAMPLE", "updateMilestoneById - Begin");
		ContentValues args = new ContentValues();
	    args.put("name", milestone.getName());	
	    args.put("description", milestone.getDescription());
	    args.put("date", milestone.getDate());
		db.update(TABLE_NAME_MILESTONE, args, "id = " + milestone.getId(), null);
		Log.d("EXAMPLE", "updateMilestoneById - End");
		return milestone;
	}

	public void deleteAllRoadmaps() {
		this.db.delete(TABLE_NAME_ROADMAP, null, null);
	}

	public void deleteAllMilestones() {
		this.db.delete(TABLE_NAME_MILESTONE, null, null);
	}

	public List<String> getAllRoadmapNames() {
		List<String> list = new ArrayList<String>();
		Cursor cursor = this.db.query(TABLE_NAME_ROADMAP,
				new String[] { "name" }, null, null, null, null, "name desc");
		if (cursor.moveToFirst()) {
			do {
				list.add(cursor.getString(0));
			} while (cursor.moveToNext());
		}
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		return list;
	}

	public List<String> getAllMilestoneNames() {
		List<String> list = new ArrayList<String>();
		Cursor cursor = this.db.query(TABLE_NAME_MILESTONE,
				new String[] { "name" }, null, null, null, null, "name desc");
		if (cursor.moveToFirst()) {
			do {
				list.add(cursor.getString(0));
			} while (cursor.moveToNext());
		}
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		return list;
	}

	public List<Milestone> getAllMilestones() {
		Log.d("EXAMPLE", "getAllMilestones - Begin");
		List<Milestone> milestones = new ArrayList<Milestone>();
		Cursor cursor = this.db.query(TABLE_NAME_MILESTONE, new String[] {
				"id", "name", "description", "date", "roadmap_id" }, null,
				null, null, null, "id desc");

		if (!cursor.moveToFirst())
			Log.d("EXAMPLE", "cursor is empty");

		if (cursor.moveToFirst()) {
			do {
				Log.d("EXAMPLE", "cursordurchlauf - begin");
				Milestone milestone = new Milestone(cursor.getInt(0),
						cursor.getString(1), cursor.getString(2),
						cursor.getString(3), this.getRoadmapById(cursor.getInt(4)));
				milestones.add(milestone);
				Log.d("EXAMPLE", "cursordurchlaufende");
			} while (cursor.moveToNext());
		}
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
			Log.d("EXAMPLE", "cursor is closed");
		}

		Log.d("EXAMPLE", "getAllMilestones - End");
		return milestones;
	}

	public List<Roadmap> getAllRoadmaps() {
		Log.d("EXAMPLE", "getAllRoadmaps - Begin");
		List<Roadmap> roadmaps = new ArrayList<Roadmap>();
		Cursor cursor = this.db.query(TABLE_NAME_ROADMAP, new String[] { "id",
				"name", "start_date", "end_date", "project_id" }, null, null,
				null, null, "id asc");

		if (!cursor.moveToFirst())
			Log.d("EXAMPLE", "cursor is empty");

		if (cursor.moveToFirst()) {
			do {
				Log.d("EXAMPLE", "cursordurchlauf - begin");
				Roadmap roadmap = new Roadmap(cursor.getInt(0),
						cursor.getString(1), cursor.getString(2),
						cursor.getString(3), cursor.getInt(4));
				roadmaps.add(roadmap);
				Log.d("EXAMPLE", "cursordurchlaufende");
			} while (cursor.moveToNext());
		}
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
			Log.d("EXAMPLE", "cursor is closed");
		}

		Log.d("EXAMPLE", "getAllRoadmaps - End");
		return roadmaps;
	}

	public List<Milestone> getAllMilestonesByRoadmapId(int roadmapId) {
		Log.d("EXAMPLE", "getAllMilestonesByRoadmapId - Begin");
		List<Milestone> milestones = new ArrayList<Milestone>();
		Cursor cursor = this.db.query(TABLE_NAME_MILESTONE, new String[] {
				"id", "name", "description", "date", "roadmap_id" },
				"roadmap_id = " + String.valueOf(roadmapId), null, null, null,
				"id desc");

		if (!cursor.moveToFirst())
			Log.d("EXAMPLE", "cursor is empty");

		if (cursor.moveToFirst()) {
			do {
				Log.d("EXAMPLE", "cursordurchlauf - begin");
				Milestone milestone = new Milestone(cursor.getInt(0),
						cursor.getString(1), cursor.getString(2),
						cursor.getString(3), null);
				milestones.add(milestone);
				Log.d("EXAMPLE", "cursordurchlaufende");
			} while (cursor.moveToNext());
		}
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
			Log.d("EXAMPLE", "cursor is closed");
		}

		Log.d("EXAMPLE", "getAllMilestonesByRoadmapId - End");
		return milestones;
	}

	public void deleteAllMilestonesByRoadmapId(int roadmapId) {
		Log.d("DEBUG", "deleteAllMilestonesByRoadmapId - Begin");
		
		db.delete(TABLE_NAME_MILESTONE, "roadmap_id = " + roadmapId, null);

		Log.d("DEBUG", "deleteAllMilestonesByRoadmapId - End");
	}
	
	public void closeDB() {
		this.db.close();
	}

	private static class OpenHelper extends SQLiteOpenHelper {

		OpenHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL("CREATE TABLE "
					+ TABLE_NAME_ROADMAP
					+ "(id INTEGER PRIMARY KEY, name TEXT UNIQUE, start_date TEXT, end_date TEXT, project_id INTEGER)");
			db.execSQL("CREATE TABLE "
					+ TABLE_NAME_MILESTONE
					+ "(id INTEGER PRIMARY KEY, name TEXT UNIQUE, description TEXT, date TEXT, roadmap_id INTEGER)");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.w("EXAMPLE",
					"Upgrading database 'FeatureRoadmap.db', this will drop tables and recreate.");
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_ROADMAP);
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_MILESTONE);
			onCreate(db);
		}
	}
}
