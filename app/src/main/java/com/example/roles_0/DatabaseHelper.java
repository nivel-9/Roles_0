package com.example.roles_0;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Usuarios.db";
    private static final int DATABASE_VERSION = 1;

    static final String TABLE_NAME = "Usuarios";
    private static final String COL_ID = "id";
    private static final String COL_NOMBRE = "nombre";
    private static final String COL_GENERO = "genero";
    private static final String COL_FECHA_NACIMIENTO = "fecha_nacimiento";
    private static final String COL_NIVEL_ESTUDIOS = "nivel_estudios";
    private static final String COL_INTERESES = "intereses";
    private static final String COL_TELEFONO = "telefono";
    private static final String COL_USUARIO = "usuario";
    private static final String COL_CONTRASEÑA = "contraseña";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_NOMBRE + " TEXT, " +
                COL_GENERO + " TEXT, " +
                COL_FECHA_NACIMIENTO + " TEXT, " +
                COL_NIVEL_ESTUDIOS + " TEXT, " +
                COL_INTERESES + " TEXT, " +
                COL_TELEFONO + " TEXT, " +
                COL_USUARIO + " TEXT, " +
                COL_CONTRASEÑA + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // Método para obtener un usuario por ID en DatabaseHelper
    public Usuario getUserById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COL_ID + " = ?", new String[]{String.valueOf(id)});
        if (cursor.moveToFirst()) {
            Usuario user = new Usuario(
                    cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_NOMBRE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_GENERO)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_FECHA_NACIMIENTO)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_NIVEL_ESTUDIOS)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_INTERESES)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_TELEFONO)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_USUARIO)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_CONTRASEÑA))
            );
            cursor.close();
            return user;
        }
        cursor.close();
        return null;
    }

    public boolean insertData(String nombre, String genero, String fechaNacimiento, String nivelEstudios,
                              String intereses, String telefono, String usuario, String contraseña) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_NOMBRE, nombre);
        contentValues.put(COL_GENERO, genero);
        contentValues.put(COL_FECHA_NACIMIENTO, fechaNacimiento);
        contentValues.put(COL_NIVEL_ESTUDIOS, nivelEstudios);
        contentValues.put(COL_INTERESES, intereses);
        contentValues.put(COL_TELEFONO, telefono);
        contentValues.put(COL_USUARIO, usuario);
        contentValues.put(COL_CONTRASEÑA, contraseña);

        long result = db.insert(TABLE_NAME, null, contentValues);
        return result != -1; // Retorna true si la inserción fue exitosa
    }

    public List<Usuario> getAllUsers() {
        List<Usuario> userList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        if (cursor.moveToFirst()) {
            do {
                Usuario user = new Usuario(
                        cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_NOMBRE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_GENERO)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_FECHA_NACIMIENTO)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_NIVEL_ESTUDIOS)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_INTERESES)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_TELEFONO)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_USUARIO)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_CONTRASEÑA)));
                userList.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return userList;
    }

    // Método para eliminar un registro
    public boolean deleteData(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_NAME, COL_ID + " = ?", new String[]{String.valueOf(id)});
        return result > 0; // Retorna true si la eliminación fue exitosa
    }

    // Método para actualizar un usuario
    public boolean updateData(int id, String nombre, String genero, String fechaNacimiento, String nivelEstudios,
                              String intereses, String telefono, String usuario, String contraseña) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_NOMBRE, nombre);
        contentValues.put(COL_GENERO, genero);
        contentValues.put(COL_FECHA_NACIMIENTO, fechaNacimiento);
        contentValues.put(COL_NIVEL_ESTUDIOS, nivelEstudios);
        contentValues.put(COL_INTERESES, intereses);
        contentValues.put(COL_TELEFONO, telefono);
        contentValues.put(COL_USUARIO, usuario);
        contentValues.put(COL_CONTRASEÑA, contraseña);

        int result = db.update(TABLE_NAME, contentValues, COL_ID + " = ?", new String[]{String.valueOf(id)});
        return result > 0;
    }
}