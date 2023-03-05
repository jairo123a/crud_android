package com.example.gremlin;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.gremlin.model.Pedidos;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    EditText referencia,estado,cantidad,fecha,hora;
    ListView listV_pedidos;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    private List<Pedidos> listaPedidos = new ArrayList<Pedidos>();
    ArrayAdapter<Pedidos> arrayAdapterPedidos;

    Pedidos pedidoSeleccionado;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        referencia = findViewById(R.id.txtReferencia);
        estado = findViewById(R.id.txtEstado);
        cantidad = findViewById(R.id.txtCantidad);
        fecha = findViewById(R.id.txtFecha);
        hora = findViewById(R.id.txtHora);

        listV_pedidos = findViewById(R.id.lv_pedidos);

        inicializarFirebase();
        listarPedidos();

        listV_pedidos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                pedidoSeleccionado = (Pedidos) adapterView.getItemAtPosition(i);
                referencia.setText(pedidoSeleccionado.getReferencia());
                estado.setText(pedidoSeleccionado.getEstado());
                cantidad.setText(pedidoSeleccionado.getCantidad());
                fecha.setText(pedidoSeleccionado.getFecha());
                hora.setText(pedidoSeleccionado.getHora());
            }
        });

    }

    private void listarPedidos() {
        databaseReference.child("Pedido").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listaPedidos.clear();
                for(DataSnapshot objSnaptshot : snapshot.getChildren()){
                    Pedidos p = objSnaptshot.getValue(Pedidos.class);
                    listaPedidos.add(p);

                    arrayAdapterPedidos = new ArrayAdapter<Pedidos>(MainActivity.this,android.R.layout.simple_list_item_1,listaPedidos);
                    listV_pedidos.setAdapter(arrayAdapterPedidos);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void inicializarFirebase(){
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseDatabase.setPersistenceEnabled(true);
        databaseReference = firebaseDatabase.getReference();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        String ref = referencia.getText().toString();
        String state = estado.getText().toString();
        String cant = cantidad.getText().toString();
        String date = fecha.getText().toString();
        String hour = hora.getText().toString();
        //return super.onOptionsItemSelected(item);

        if (ref.equals("") || state.equals("") || cant.equals("") || date.equals("") || hour.equals(""))
        {
            Toast.makeText(this,"Por favor diligencie todos los datos", Toast.LENGTH_LONG).show();
            if(ref.equals("") ){
                referencia.setError("Required");
            }
            if(state.equals("") ){
                estado.setError("Required");
            }
            if(cant.equals("") ){
                cantidad.setError("Required");
            }
            if(date.equals("") ){
                fecha.setError("Required");
            }
            if(hour.equals("") ){
                hora.setError("Required");
            }
        }
        else{
            Pedidos pedido = new Pedidos();
            pedido.setUid(UUID.randomUUID().toString());
            pedido.setReferencia(ref);
            pedido.setEstado(state);
            pedido.setCantidad(cant);
            pedido.setFecha(date);
            pedido.setHora(hour);


            switch (item.getItemId()){
                case R.id.icon_add:{

                    databaseReference.child("Pedido").child(pedido.getUid()).setValue(pedido);
                    Toast.makeText(this,"Pedido agregado con exito", Toast.LENGTH_LONG).show();
                    break;
                }
                case R.id.icon_save:{
                    Pedidos p = new Pedidos();
                    p.setUid(pedidoSeleccionado.getUid().toString());
                    p.setReferencia(referencia.getText().toString().trim());
                    p.setEstado(estado.getText().toString().trim());
                    p.setCantidad(cantidad.getText().toString().trim());
                    p.setFecha(fecha.getText().toString().trim());
                    p.setHora(hora.getText().toString().trim());
                    databaseReference.child("Pedido").child(p.getUid()).setValue(p);
                    Toast.makeText(this,"Pedido Actualizado con exito", Toast.LENGTH_LONG).show();
                    break;
                }
                case R.id.icon_delete:{
                    Pedidos ped = new Pedidos();
                    ped.setUid(pedidoSeleccionado.getUid());
                    databaseReference.child("Pedido").child(ped.getUid()).removeValue();
                    Toast.makeText(this,"Pedido Borrado con exito", Toast.LENGTH_LONG).show();
                    break;
                }
                default:break;
            }
            limpiarTexto();

        }


        return true;
    }

    private void limpiarTexto(){
        referencia.setText("");
        estado.setText("");
        cantidad.setText("");
        fecha.setText("");
        hora.setText("");
    }



}