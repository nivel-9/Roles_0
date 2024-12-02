package com.example.roles_0;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TableLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class MostrarDatosBD1 extends AppCompatActivity {
    private DatabaseHelper dbHelper;
    private TableLayout tableLayout;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_datos_bd);

        dbHelper = new DatabaseHelper(this);
        tableLayout = findViewById(R.id.tableLayout);
        View btnAgregar = findViewById(R.id.btnAgregar);

        // Configura el intent para ir a RegistroSQLite
        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MostrarDatosBD1.this, MainActivity.class);
                startActivity(intent);
            }
        });

        cargarDatos();
    }
    private void cargarDatos() {
        List<Usuario> usuarios = dbHelper.getAllUsers();

        for (Usuario usuario : usuarios) {
            TableRow fila = new TableRow(this);

            TextView nombreText = new TextView(this);
            nombreText.setText(usuario.getNombre());
            nombreText.setPadding(8, 8, 8, 8);
            fila.addView(nombreText);

            TextView generoText = new TextView(this);
            generoText.setText(usuario.getGenero());
            generoText.setPadding(8, 8, 8, 8);
            fila.addView(generoText);

            TextView fechaNacimientoText = new TextView(this);
            fechaNacimientoText.setText(usuario.getFechaNacimiento());
            fechaNacimientoText.setPadding(8, 8, 8, 8);
            fila.addView(fechaNacimientoText);

            TextView nivelEstudiosText = new TextView(this);
            nivelEstudiosText.setText(usuario.getNivelEstudios());
            nivelEstudiosText.setPadding(8, 8, 8, 8);
            fila.addView(nivelEstudiosText);

            TextView interesesText = new TextView(this);
            interesesText.setText(usuario.getIntereses());
            interesesText.setPadding(8, 8, 8, 8);
            fila.addView(interesesText);

            TextView telefonoText = new TextView(this);
            telefonoText.setText(usuario.getTelefono());
            telefonoText.setPadding(8, 8, 8, 8);
            fila.addView(telefonoText);

            TextView usuarioText = new TextView(this);
            usuarioText.setText(usuario.getUsuario());
            usuarioText.setPadding(8, 8, 8, 8);
            fila.addView(usuarioText);

            TextView contraseñaText = new TextView(this);
            contraseñaText.setText(usuario.getContraseña());
            contraseñaText.setPadding(8, 8, 8, 8);
            fila.addView(contraseñaText);

            // Botón para actualizar
            Button btnActualizar = new Button(this);
            btnActualizar.setText("Editar");
            btnActualizar.setOnClickListener(v -> {
                Intent intent = new Intent(MostrarDatosBD1.this, MainActivity.class);
                intent.putExtra("ID", usuario.getId()); // Pasar el ID del usuario
                startActivity(intent);
            });
            fila.addView(btnActualizar);

            // Botón para eliminar
            Button btnEliminar = new Button(this);
            btnEliminar.setText("Eliminar");
            btnEliminar.setOnClickListener(v -> {
                dbHelper.deleteData(usuario.getId());
                tableLayout.removeView(fila); // Remover la fila de la tabla
                Toast.makeText(MostrarDatosBD1.this, "Registro eliminado", Toast.LENGTH_SHORT).show();
            });
            fila.addView(btnEliminar);

            tableLayout.addView(fila);


        }
    }
}