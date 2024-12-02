package com.example.roles_0;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    TextView texto;
    Spinner sp1;
    EditText editTextDate2, editTextPassword, editTextPhone;
    RadioGroup radioGroupGenero;
    CheckBox checkBoxEducacion, checkBoxTecnologia, checkBoxComunicacion, checkBoxSociedad;
    Switch activarEnvio;
    Button acceso,consultar;
    private int userId = -1; // Variable para almacenar el ID del usuario si es actualización
    private DatabaseHelper dbHelper;
    private int currentUserId = -1;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Ajuste de insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Date picker para fecha de nacimiento
        editTextDate2 = findViewById(R.id.editTextDate2);
        editTextPassword = findViewById(R.id.editTextTextPassword);
        editTextPhone = findViewById(R.id.editTextPhone);
        editTextDate2.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this, (view, year1, monthOfYear, dayOfMonth) ->
                    editTextDate2.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year1), year, month, day);
            datePickerDialog.show();
        });

        // Toggle de visibilidad de contraseña
        editTextPassword.setOnTouchListener((v, event) -> {
            final int DRAWABLE_RIGHT = 2;
            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (event.getRawX() >= (editTextPassword.getRight() - editTextPassword.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                    if (editTextPassword.getInputType() == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                        editTextPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    } else {
                        editTextPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    }
                    editTextPassword.setSelection(editTextPassword.length());
                    return true;
                }
            }
            return false;
        });

        // Código de país para el número de teléfono
        editTextPhone.setText("+52 ");

        // Inicializar RadioGroup y CheckBoxes
        radioGroupGenero = findViewById(R.id.radioGroupGenero);
        checkBoxEducacion = findViewById(R.id.checkBox);
        checkBoxTecnologia = findViewById(R.id.checkBox2);
        checkBoxComunicacion = findViewById(R.id.checkBox3);
        checkBoxSociedad = findViewById(R.id.checkBox4);
        activarEnvio = findViewById(R.id.switch1);

        // Listener para RadioGroup (Género)
        radioGroupGenero.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.radioButton4) {
                Toast.makeText(MainActivity.this, "Seleccionaste Masculino", Toast.LENGTH_SHORT).show();
            } else if (checkedId == R.id.radioButton3) {
                Toast.makeText(MainActivity.this, "Seleccionaste Femenino", Toast.LENGTH_SHORT).show();
            }
        });

        // Listeners para CheckBoxes (Temas de interés)
        checkBoxEducacion.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                Toast.makeText(MainActivity.this, "Seleccionaste Educación", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "Deseleccionaste Educación", Toast.LENGTH_SHORT).show();
            }
        });

        checkBoxTecnologia.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                Toast.makeText(MainActivity.this, "Seleccionaste Tecnología", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "Deseleccionaste Tecnología", Toast.LENGTH_SHORT).show();
            }
        });

        checkBoxComunicacion.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                Toast.makeText(MainActivity.this, "Seleccionaste Comunicación", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "Deseleccionaste Comunicación", Toast.LENGTH_SHORT).show();
            }
        });

        checkBoxSociedad.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                Toast.makeText(MainActivity.this, "Seleccionaste Sociedad", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "Deseleccionaste Sociedad", Toast.LENGTH_SHORT).show();
            }
        });

        activarEnvio.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                Toast.makeText(MainActivity.this, "Se enviará una copia de sus respuestas", Toast.LENGTH_SHORT).show();
            }
        });


        sp1 = findViewById(R.id.spinnerNivel);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.nivel_estudio, android.R.layout.simple_spinner_item);
        sp1.setAdapter(adapter);

        dbHelper = new DatabaseHelper(this);
        // Verificar si hay un ID en el intent (para actualización)
        Intent intent = getIntent();
        if (intent.hasExtra("ID")) {
            userId = intent.getIntExtra("ID", -1);
            cargarDatosUsuario(userId);
        }

        acceso = findViewById(R.id.button);
        acceso.setOnClickListener(v -> {
            String nombre = ((EditText) findViewById(R.id.editTextText2)).getText().toString();
            String genero = ((RadioButton) findViewById(radioGroupGenero.getCheckedRadioButtonId())).getText().toString();
            String fechaNacimiento = editTextDate2.getText().toString();
            String nivelEstudios = sp1.getSelectedItem().toString();
            String intereses = getSelectedInterests();
            String telefono = editTextPhone.getText().toString();
            String usuario = ((EditText) findViewById(R.id.editTextText3)).getText().toString();
            String contraseña = editTextPassword.getText().toString();

            if (currentUserId == -1) { // Comprobación para insertar o actualizar
                boolean isInserted = dbHelper.insertData(nombre, genero, fechaNacimiento, nivelEstudios, intereses, telefono, usuario, contraseña);
                if (isInserted) {
                    Toast.makeText(this, "Datos guardados en SQLite", Toast.LENGTH_SHORT).show();

                }
            } else {
                // Actualización de un registro existente
                boolean resultado = dbHelper.updateData(userId, nombre, genero, fechaNacimiento, nivelEstudios, intereses, telefono, usuario, contraseña);
                if (resultado) {
                    Toast.makeText(this, "Usuario actualizado correctamente", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this, MostrarDatosBD1.class));
                }
                currentUserId = -1; // Reiniciar a -1 para futuros registros nuevos
            }
        });




        consultar = findViewById(R.id.button2);
        consultar.setOnClickListener(v -> {

            startActivity(new Intent(MainActivity.this, MostrarDatosBD1.class));
        });
    }

    // Método para obtener intereses seleccionados
    private String getSelectedInterests() {
        StringBuilder interests = new StringBuilder();
        CheckBox cb1 = findViewById(R.id.checkBox);
        CheckBox cb2 = findViewById(R.id.checkBox2);
        CheckBox cb3 = findViewById(R.id.checkBox3);
        CheckBox cb4 = findViewById(R.id.checkBox4);

        if (cb1.isChecked()) interests.append(cb1.getText().toString()).append(", ");
        if (cb2.isChecked()) interests.append(cb2.getText().toString()).append(", ");
        if (cb3.isChecked()) interests.append(cb3.getText().toString()).append(", ");
        if (cb4.isChecked()) interests.append(cb4.getText().toString()).append(", ");

        if (interests.length() > 0) {
            interests.setLength(interests.length() - 2); // Remueve la última coma
        }
        return interests.toString();
    }

    private void cargarDatosUsuario(int id) {
        // Método para cargar los datos existentes en el formulario para la edición
        Usuario usuario = dbHelper.getUserById(id);
        if (usuario != null) {
            currentUserId = id;
            // Nombre
            ((EditText) findViewById(R.id.editTextText2)).setText(usuario.getNombre());
            // Género
            RadioGroup radioGroupGenero = findViewById(R.id.radioGroupGenero);
            if (usuario.getGenero().equals("Masculino")) {
                radioGroupGenero.check(R.id.radioButton4);
            } else if (usuario.getGenero().equals("Femenino")) {
                radioGroupGenero.check(R.id.radioButton3);
            }
            // Fecha de Nacimiento
            ((EditText) findViewById(R.id.editTextDate2)).setText(usuario.getFechaNacimiento());
            // Nivel de Estudios
            Spinner spinnerNivel = findViewById(R.id.spinnerNivel);
            ArrayAdapter adapter = (ArrayAdapter) spinnerNivel.getAdapter();
            int position = adapter.getPosition(usuario.getNivelEstudios());
            spinnerNivel.setSelection(position);
            // Teléfono
            ((EditText) findViewById(R.id.editTextPhone)).setText(usuario.getTelefono());
            // Usuario
            ((EditText) findViewById(R.id.editTextText3)).setText(usuario.getUsuario());
            // Contraseña
            ((EditText) findViewById(R.id.editTextTextPassword)).setText(usuario.getContraseña());
            getSelectedInterests();
        }
    }

}