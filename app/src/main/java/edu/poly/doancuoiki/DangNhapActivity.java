package edu.poly.doancuoiki;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class DangNhapActivity extends AppCompatActivity {
    private TextInputEditText etEmail, etPassword;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangnhap);

        // Initialize views
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = Objects.requireNonNull(etEmail.getText()).toString();
                String password = Objects.requireNonNull(etPassword.getText()).toString();

                // Simple validation
                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(DangNhapActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }

                // TODO: Add your authentication logic here
                // For now, we'll just proceed to the next screen
                Intent intent = new Intent(DangNhapActivity.this, TrangChuActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
} 