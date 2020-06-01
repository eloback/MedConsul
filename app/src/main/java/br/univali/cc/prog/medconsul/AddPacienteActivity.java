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

public class AddPacienteActivity extends AppCompatActivity {
    SQLiteDatabase db;
    EditText etNome;
    Spinner spGpSangue;
    EditText etLogradouro;
    EditText etNumero;
    EditText etCidade;
    Spinner spUf;
    EditText etCelular;
    EditText etFixo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_paciente);

        etNome = findViewById(R.id.etNomePaciente);
        spGpSangue = findViewById(R.id.spTipoSangue);
        etLogradouro = findViewById(R.id.etLogradouroPac);
        etNumero = findViewById(R.id.etNumeroPac);
        etCidade = findViewById(R.id.etCidadePac);
        spUf = findViewById(R.id.spUfPac);
        etCelular = findViewById(R.id.etCelularPac);
        etFixo = findViewById(R.id.etFixoPac);

        String[] opGrupoSang = new String[]{
                "- Escolha uma opção -",
                "A+", "A-", "B+", "B-",
                "AB+", "AB-", "O+", "O-"

        };
        ArrayAdapter<String> gpGSangArrayAdap =
                new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, opGrupoSang);
        spGpSangue.setAdapter(gpGSangArrayAdap);

        String[] spUFpac = new String[] {
                "- SELECIONE -",
                "RO", "AC", "AM", "RR", "PA",
                "AP", "TO", "MA", "PI", "CE",
                "RN", "PB", "PE", "AL", "SE",
                "BA", "MG", "ES", "RJ", "SP",
                "PR", "SC", "RS", "MS", "MT",
                "GO", "DF"
        };
        ArrayAdapter<String> spArrayAdapUFpac =
                new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, spUFpac);
        spUf.setAdapter(spArrayAdapUFpac);

        Button btnSalvarPaciente = findViewById(R.id.btnSalvarPac);
        btnSalvarPaciente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvarBD();
            }
        });
    }

    private void salvarBD(){
        String nome = etNome.getText().toString().trim();
        String gpSangue = spGpSangue.getSelectedItem().toString();
        String logradouro = etLogradouro.getText().toString().trim();
        String numero = etNumero.getText().toString().trim();
        String cidade = etCidade.getText().toString().trim();
        String uf = spUf.getSelectedItem().toString();
        String celular = etCelular.getText().toString().trim();
        String fixo = etFixo.getText().toString().trim();

        if(nome.equals("")){
            Toast.makeText(getApplicationContext(), "Por favor, informe o Nome!", Toast.LENGTH_LONG).show();
        }else if (gpSangue.equals("- Escolha uma opção -")){
            Toast.makeText(getApplicationContext(), "Por favor, Selecione o Grupo Sanguíneo!", Toast.LENGTH_LONG).show();
        }else if (logradouro.equals("")){
            Toast.makeText(getApplicationContext(), "Por favor, Informe o Logradouro!", Toast.LENGTH_LONG).show();
        }else if (numero.equals("")){
            Toast.makeText(getApplicationContext(), "Por favor, Informe o Número!", Toast.LENGTH_LONG).show();
        }else if (cidade.equals("")){
            Toast.makeText(getApplicationContext(), "Por favor, Informe a Cidade!", Toast.LENGTH_LONG).show();
        }else if (uf.equals("- SELECIONE -")){
            Toast.makeText(getApplicationContext(), "Por favor, Selecione a UF!", Toast.LENGTH_LONG).show();
        }else if (celular.equals("")) {
            Toast.makeText(getApplicationContext(), "Por favor, Informe o Celular!", Toast.LENGTH_LONG).show();
        }else if (fixo.equals("")) {
            Toast.makeText(getApplicationContext(), "Por favor, Informe o Telefone Fixo!", Toast.LENGTH_LONG).show();
        }else{
            db = openOrCreateDatabase("consulta.db", Context.MODE_PRIVATE, null);
            StringBuilder sql = new StringBuilder();

            sql.append("INSERT INTO paciente(nome, grp_sanguineo, logradouro, numero, cidade, uf, celular, fixo) VALUES(");
            sql.append(" ' " + nome +" ' ," );
            sql.append(" ' " + gpSangue +" ' ," );
            sql.append(" ' " + logradouro +" ' ," );
            sql.append(" ' " + numero +" ' ," );
            sql.append(" ' " + cidade +" ' ," );
            sql.append(" ' " + uf +" ' ," );
            sql.append(celular +" , " );
            sql.append(fixo);
            sql.append(");");
            try {
                db.execSQL(sql.toString());
                Toast.makeText(getApplicationContext(), "Paciente inserido com sucesso!", Toast.LENGTH_LONG).show();
            }catch (SQLException e) {
                Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
            etNome.setText("");
            spGpSangue.setSelection(0);
            etLogradouro.setText("");
            etNumero.setText("");
            etCidade.setText("");
            spUf.setSelection(0);
            etCelular.setText("");
            etFixo.setText("");
            db.close();
        }


    }

}
