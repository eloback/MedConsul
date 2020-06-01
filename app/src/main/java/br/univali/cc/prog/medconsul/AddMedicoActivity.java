package br.univali.cc.prog.medconsul;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AddMedicoActivity extends AppCompatActivity {
    SQLiteDatabase db;
    EditText etNome;
    EditText etCRM;
    EditText etLogradouro;
    EditText etNumero;
    EditText etCidade;
    Spinner spUF;
    EditText etCelular;
    EditText etFixo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medico);

        etNome = findViewById(R.id.etNomeMedico);
        etCRM = findViewById(R.id.etCRM);
        etLogradouro = findViewById(R.id.etLogradouroMed);
        etNumero = findViewById(R.id.etNumeroMed);
        etCidade = findViewById(R.id.etCidadeMed);
        spUF = findViewById(R.id.spUfMed);
        etCelular = findViewById(R.id.etCelularMed);
        etFixo = findViewById(R.id.etFixoMed);

        String[] spUFmed = new String[] {
                "Escolha uma opção",
                "RO", "AC", "AM", "RR", "PA",
                "AP", "TO", "MA", "PI", "CE",
                "RN", "PB", "PE", "AL", "SE",
                "BA", "MG", "ES", "RJ", "SP",
                "PR", "SC", "RS", "MS", "MT",
                "GO", "DF"
        };
        ArrayAdapter<String> spArrayAdapUF =
                new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, spUFmed);
        spUF.setAdapter(spArrayAdapUF);

        Button btnSalvarMed = findViewById(R.id.btnSalvarMedico);
        btnSalvarMed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvarBD();
            }
        });
    }
    private void salvarBD(){
        String nome = etNome.getText().toString().trim();
        String crm = etCRM.getText().toString().trim();
        String logradouro = etLogradouro.getText().toString().trim();
        String numero = etNumero.getText().toString().trim();
        String cidade = etCidade.getText().toString().trim();
        String uf = spUF.getSelectedItem().toString();
        String celular = etCelular.getText().toString().trim();
        String fixo = etFixo.getText().toString().trim();

        if (nome.equals("")){
            Toast.makeText(getApplicationContext(), "Por favor, informe o Nome!", Toast.LENGTH_LONG).show();
        }else if(crm.equals("")){
            Toast.makeText(getApplicationContext(), "Por favor, informe o CRM!", Toast.LENGTH_LONG).show();
        }else if(logradouro.equals("")){
            Toast.makeText(getApplicationContext(), "Por favor, informe o Logradouro!", Toast.LENGTH_LONG).show();
        }else if(numero.equals("")){
            Toast.makeText(getApplicationContext(), "Por favor, informe o Número!", Toast.LENGTH_LONG).show();
        }else if(cidade.equals("")){
            Toast.makeText(getApplicationContext(), "Por favor, informe a Cidade!", Toast.LENGTH_LONG).show();
        }else if(uf.equals("Escolha uma opção")) {
            Toast.makeText(getApplicationContext(), "Por favor, Selecione a UF!", Toast.LENGTH_LONG).show();
        }else if(celular.equals("")){
            Toast.makeText(getApplicationContext(), "Por favor, informe o telefone Celular!", Toast.LENGTH_LONG).show();
        }else if(fixo.equals("")){
            Toast.makeText(getApplicationContext(), "Por favor, informe o telefone Fixo!", Toast.LENGTH_LONG).show();
        }else{
            db = openOrCreateDatabase("consulta.db", Context.MODE_PRIVATE, null);
            StringBuilder sql = new StringBuilder();

            sql.append("INSERT INTO medico(nome, crm, logradouro, numero, cidade, uf, celular, fixo) VALUES(");
            sql.append(" ' " + nome +" ' ," );
            sql.append(" ' " + crm +" ' ," );
            sql.append(" ' " + logradouro +" ' ," );
            sql.append(numero + "," );
            sql.append(" ' " + cidade +" ' ," );
            sql.append(" ' " + uf +" ' ," );
            sql.append(celular + " ," );
            sql.append(fixo);
            sql.append(");");

            try {
                db.execSQL(sql.toString());
                Toast.makeText(getApplicationContext(), "Médico inserido com sucesso!", Toast.LENGTH_LONG).show();
            }catch (SQLException e){
                Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
            etNome.setText("");
            etCRM.setText("");
            etLogradouro.setText("");
            etNumero.setText("");
            etCidade.setText("");
            spUF.setSelection(0);
            etCelular.setText("");
            etFixo.setText("");
            db.close();
        }
    }
}
