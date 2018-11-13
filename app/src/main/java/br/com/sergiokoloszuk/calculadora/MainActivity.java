package br.com.sergiokoloszuk.calculadora;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {
    EditText visor;
    String currentString = "0", previusString = null;
    boolean isTempStringShown = false;
    int currentopperand = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        visor = findViewById( R.id.visor );
        int numberButtons[] = {R.id.button0, R.id.button1, R.id.button2, R.id.button3, R.id.button4, R.id.button5, R.id.button6, R.id.button7, R.id.button8, R.id.button9};
        NumberButtonClickListener numberClickListener = new NumberButtonClickListener();

        for (int id : numberButtons) {
            View v = findViewById( id );
            v.setOnClickListener( numberClickListener );
        }

        int opperandButtons[] = {R.id.button_somar, R.id.button_subtrair, R.id.button_dividir, R.id.button_multiplicar, R.id.button_ponto, R.id.btn_c, R.id.button_igual};
        OpperandButtonClickListener oppClickListener = new OpperandButtonClickListener();

        for (int id : opperandButtons) {
            View v = findViewById( id );
            v.setOnClickListener( oppClickListener );
        }
        setCurrentString( "0" );
    }

    void setCurrentString(String s) {
        currentString = s;
        visor.setText( s );
    }

    public class NumberButtonClickListener implements OnClickListener {
        @Override
        public void onClick(View v) {

            if (isTempStringShown) {
                previusString = currentString;
                currentString = "0";
                isTempStringShown = false;
            }

            String text = (String) ((Button) v).getText();
            if (currentString.equals( "0" )) setCurrentString( text );
            else setCurrentString( currentString + text );
        }
    }

    public class OpperandButtonClickListener implements OnClickListener {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            if (id == R.id.btn_c) {
                isTempStringShown = false;
                setCurrentString( "0" );
                previusString = null;
            }
            if (id == R.id.button_ponto)
                if (!currentString.contains( "." )) setCurrentString( currentString + "." );
            if (id == R.id.button_somar || id == R.id.button_subtrair || id == R.id.button_multiplicar || id == R.id.button_dividir) {
                currentopperand = id;
                previusString = currentString;
                isTempStringShown = true;
            }
            if (id == R.id.button_igual) {
                double curr = Double.parseDouble( currentString );
                double result = 0;
                try {
                    if (previusString != null) {
                        double prev = Double.parseDouble( previusString );
                        switch (currentopperand) {
                            case R.id.button_somar:
                                result = prev + curr;
                                break;
                            case R.id.button_subtrair:
                                result = prev - curr;
                                break;
                            case R.id.button_multiplicar:
                                result = prev * curr;
                                break;
                            case R.id.button_dividir:
                                result = prev / curr;
                                break;
                        }
                    }
                    setCurrentString( Double.toString( result ) );
                } catch (ArithmeticException ex) {
                    System.out.println( ex + "Infinity" );
                                    }
            }
        }
    }
}
