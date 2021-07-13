package pitao.sherwin.firebasesampledatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import pitao.sherwin.firebasesampledatabase.model.Exam;
import pitao.sherwin.firebasesampledatabase.model.MClass;
import pitao.sherwin.firebasesampledatabase.model.Student;
import pitao.sherwin.firebasesampledatabase.model.User;

public class MainActivity extends AppCompatActivity {

    private Button btnUser, btnExam, btnClass, btnStudent, btnRetrieve;
    private EditText etKey;

    private FirebaseDatabase mDatabase;
    private DatabaseReference mRefence;
    private FirebaseAuth mAuth;

    private User user;

    private String userKey, classKey, examKey, studentKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();

        btnUser = findViewById(R.id.btnUser);
        btnExam = findViewById(R.id.btnExam);
        btnClass = findViewById(R.id.btnClass);
        btnStudent = findViewById(R.id.btnStudent);
        btnRetrieve = findViewById(R.id.btnRetrieve);
        etKey = findViewById(R.id.etKey);

        String email = "test@gmail.com";
        String name = "Sherwin Test";
        user = new User(email,name);

        btnUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser(user.getEmail(),"password");
            }
        });

        btnExam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createExam("Math Exam");
            }
        });

        btnClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createClass("Math");
            }
        });

        btnStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer[] failed = new Integer[]{13,14,15,16,17,18,20};
                Integer[] correct = new Integer[]{1,2,3,4,5,6,7,8,9,10,11,12};
                createStudent("JV","C-0001",12, Arrays.asList(failed),Arrays.asList(correct));
            }
        });

        btnRetrieve.setOnClickListener((view -> {
            mRefence = mDatabase.getReference();
            Query query = mRefence.child("Users").orderByChild("email").equalTo(etKey.getText().toString());

            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                   // User yourModel = snapshot.getValue(User.class);

                    Log.d("TAGMan",snapshot.toString());

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }));
    }



    private void registerUser(String email, String password){
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("TAGMan", "createUserWithEmailAndPassword:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            Toast.makeText(getBaseContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }

    private void updateUI(FirebaseUser currentUser){
        userKey = currentUser.getUid();
        mRefence = mDatabase.getReference("Users");
        mRefence.child(userKey).setValue(user);
    }

    private void createClass(String name){
        classKey = mRefence.push().getKey();
        mRefence = mDatabase.getReference("Class");
        MClass mClass = new MClass(name, userKey);
        mRefence.child(classKey).setValue(mClass);
    }

    public void createExam(String name){
        examKey = mRefence.push().getKey();
        mRefence = mDatabase.getReference("Exam");
        Exam exam = new Exam(name, classKey);
        mRefence.child(examKey).setValue(exam);
    }

    public void createStudent(String name, String studentId, int score, List<Integer> failedItems, List<Integer> correctItems){
        studentKey = mRefence.push().getKey();
        mRefence = mDatabase.getReference("Student");
        Student student = new Student(name,studentId,score,correctItems,failedItems,examKey);
        mRefence.child(studentKey).setValue(student);
    }
}