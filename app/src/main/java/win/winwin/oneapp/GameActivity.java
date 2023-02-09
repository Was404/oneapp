package win.winwin.oneapp;

import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class GameActivity extends AppCompatActivity {

    TextView questions;
    String[] questionArray;
    String[] variable1;
    String[] variable2;
    String[] variable3;

    Button btn_next;
    Button btn_1;
    Button btn_2;
    Button btn_3;
    int count = 1;
    int btn_var1 = 0;
    int k = 0;

    LinearLayout linearLayout2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //НАЧАЛО ЛОГИКИ ИГРЫ
        questions = findViewById(R.id.questions);
        questionArray = getResources().getStringArray(R.array.questionArray);
        variable1 = getResources().getStringArray(R.array.variable1);
        variable2 = getResources().getStringArray(R.array.variable2);
        variable3 = getResources().getStringArray(R.array.variable3);
        btn_next = findViewById(R.id.btn_next);
        btn_1 = findViewById(R.id.btn_1);
        btn_2 = findViewById(R.id.btn_2);
        btn_3 = findViewById(R.id.btn_3);

        questions.setText(questionArray[0]);
        btn_1.setText(variable1[0]);
        btn_2.setText(variable2[0]);
        btn_3.setText(variable3[0]);
        //ANIMATION BTN
        final Animation animTranslate = AnimationUtils.loadAnimation(this, R.anim.translate);
        final Animation animScale = AnimationUtils.loadAnimation(this, R.anim.scale);
        final Animation animScaleDown = AnimationUtils.loadAnimation(this, R.anim.scale_down);
        //
        linearLayout2 = findViewById(R.id.linearLayout2);
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (count <= 9) {
                    btn_next.setText("Next ➡");
                    view.startAnimation(animScaleDown);
                    linearLayout2.startAnimation(animScale);
                    questions.setText(questionArray[count]);
                    btn_1.setText(variable1[count]);
                    btn_2.setText(variable2[count]);
                    btn_3.setText(variable3[count]);
                    count ++;
                    btn_1.setVisibility(View.VISIBLE);
                    btn_2.setVisibility(View.VISIBLE);
                    btn_3.setVisibility(View.VISIBLE);

                    btn_1.setEnabled(true);
                    btn_2.setEnabled(true);
                    btn_3.setEnabled(true);
                    k ++;
                }
                else {
                    btn_next.setText("Restart");
                    if (btn_var1 >= 8) {
                        questions.setText(R.string.test_success);
                    }
                    else if (btn_var1 < 8 && btn_var1 > 2){
                        questions.setText(R.string.test_merge);
                    }
                    else if (btn_var1 <= 2){
                        questions.setText(R.string.test_failed);
                    }
                    k = -1;
                    count = 0;
                    btn_var1 = 0;
                    btn_1.setVisibility(View.INVISIBLE);
                    btn_2.setVisibility(View.INVISIBLE);
                    btn_3.setVisibility(View.INVISIBLE);
                }
            }
        });
        btn_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (k == 0 || k == 3 || k == 5 || k == 6 || k == 8){
                    btn_var1 += 1;
                }
                btn_2.setVisibility(View.INVISIBLE);
                btn_3.setVisibility(View.INVISIBLE);
                btn_2.startAnimation(animTranslate);
                btn_3.startAnimation(animTranslate);
                btn_1.setEnabled(false);
            }
        });
        btn_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (k == 1 || k == 2 || k == 7){
                    btn_var1 += 1;
                }
                btn_1.setVisibility(View.INVISIBLE);
                btn_3.setVisibility(View.INVISIBLE);
                btn_1.startAnimation(animTranslate);
                btn_3.startAnimation(animTranslate);
                btn_2.setEnabled(false);
            }
        });
        btn_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (k == 4 || k == 9){
                    btn_var1 += 1;
                }
                btn_1.setVisibility(View.INVISIBLE);
                btn_2.setVisibility(View.INVISIBLE);
                btn_1.startAnimation(animTranslate);
                btn_2.startAnimation(animTranslate);
                btn_3.setEnabled(false);
            }
        });
    }
}
