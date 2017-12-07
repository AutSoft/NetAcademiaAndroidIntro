package hu.netacademia.uidemo.view;

import android.content.Context;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import hu.netacademia.uidemo.R;

public class PasswordEditText extends RelativeLayout {

    private EditText passwordEditText;

    public PasswordEditText(Context context) {
        super(context);
        init(context);
    }

    public PasswordEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PasswordEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        LayoutInflater.from(context).inflate(R.layout.view_password_editext,this,true);

        passwordEditText = findViewById(R.id.editText);
        ImageView show=findViewById(R.id.show);

        setTransformationMethod(PasswordTransformationMethod.getInstance());

        show.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if (motionEvent.getAction()== MotionEvent.ACTION_DOWN) {
                    setTransformationMethod(null);
                    return true;

                }else if(motionEvent.getAction()==MotionEvent.ACTION_UP || motionEvent.getAction()==MotionEvent.ACTION_CANCEL){
                    setTransformationMethod(PasswordTransformationMethod.getInstance());
                    return true;
                }

                return false;
            }
        });

    }

    private void setTransformationMethod(TransformationMethod method){
        passwordEditText.setTransformationMethod(method);
        passwordEditText.setSelection(passwordEditText.getSelectionStart(),passwordEditText.getSelectionEnd());
    }


}
