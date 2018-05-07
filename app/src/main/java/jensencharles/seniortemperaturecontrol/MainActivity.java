package jensencharles.seniortemperaturecontrol;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import jensencharles.seniortemperaturecontrol.helpers.MQTTHelper;


public class MainActivity extends AppCompatActivity {


    TextView dataReceived;
    Button btnon;
    Button btnoff;
    Button btnlow;
    Button btnmed;
    Button btnhigh;
    private MQTTHelper mqttHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataReceived = (TextView) findViewById(R.id.dataReceived);
        btnon = (Button) findViewById(R.id.button_on);
        btnoff = (Button) findViewById(R.id.button_off);
        btnlow = (Button) findViewById(R.id.button_low);
        btnmed = (Button) findViewById(R.id.button_med);
        btnhigh = (Button) findViewById(R.id.button_high);

        btnon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mqttHelper.TurnOn();

            }
        });

        btnoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              mqttHelper.TurnOff();

            }
        });

        btnlow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mqttHelper.TurnLow();
            }
        });

        btnmed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mqttHelper.TurnMed();

            }
        });

        btnhigh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mqttHelper.TurnHigh();

            }
        });

        startMqtt();
    }




    private void startMqtt() {
        mqttHelper = new MQTTHelper(getApplicationContext());
        mqttHelper.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean b, String s) {

            }

            @Override
            public void connectionLost(Throwable throwable) {

            }

            @Override
            public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
                Log.w("Debug", mqttMessage.toString());
                dataReceived.setText(mqttMessage.toString() + (char) 0x00B0 + " F");
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

            }
        });
    }
}