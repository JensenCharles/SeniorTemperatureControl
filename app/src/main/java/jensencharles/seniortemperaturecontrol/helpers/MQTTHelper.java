package jensencharles.seniortemperaturecontrol.helpers;

import android.content.Context;
import android.util.Log;
import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.DisconnectedBufferOptions;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
/**
 * Created by Jensen on 4/11/2018.
 */
public class MQTTHelper {

    public MqttAndroidClient mqttAndroidClient;
    final String serverUri = "tcp://m14.cloudmqtt.com:14199";
    final String clientId = "TCUAndroidClient";
    final String subscriptionTopic = "readings/temp";
    final String username = "TCU";
    final String password = "1234567";

    String topic = "TCUcommand";
    String payload_on = "on";
    String payload_off = "off";
    String payload_low = "low";
    String payload_med = "med";
    String payload_high = "high";


    public MQTTHelper(Context context) {
        mqttAndroidClient = new MqttAndroidClient(context, serverUri, clientId);
        mqttAndroidClient.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean b, String s) {
                Log.w("mqtt", s);
            }

            @Override
            public void connectionLost(Throwable throwable) {

            }

            @Override
            public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
                Log.w("Mqtt", mqttMessage.toString());
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

            }
        });
        connect();
    }

    public MQTTHelper() throws MqttException {
    }

    public void setCallback(MqttCallbackExtended callback) {
        mqttAndroidClient.setCallback(callback);
    }

    private void connect() {
        MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
        mqttConnectOptions.setAutomaticReconnect(true);
        mqttConnectOptions.setCleanSession(false);
        mqttConnectOptions.setUserName(username);
        mqttConnectOptions.setPassword(password.toCharArray());

        try {

            mqttAndroidClient.connect(mqttConnectOptions, null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {

                    DisconnectedBufferOptions disconnectedBufferOptions = new DisconnectedBufferOptions();
                    disconnectedBufferOptions.setBufferEnabled(true);
                    disconnectedBufferOptions.setBufferSize(100);
                    disconnectedBufferOptions.setPersistBuffer(false);
                    disconnectedBufferOptions.setDeleteOldestMessages(false);
                    mqttAndroidClient.setBufferOpts(disconnectedBufferOptions);
                    subscribeToTopic();
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Log.w("Mqtt", "Failed to connect to: " + serverUri + exception.toString());
                }
            });
        } catch (MqttException ex) {
            ex.printStackTrace();
        }
    }

    private void subscribeToTopic() {
        try {
            mqttAndroidClient.subscribe(subscriptionTopic, 0, null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Log.w("Mqtt", "Subscribed!");
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Log.w("Mqtt", "Subscribed fail!");
                }
            });

        } catch (MqttException ex) {
            System.err.println("Exception whilst subscribing");
            ex.printStackTrace();
        }
    }

    public void TurnOn() {

        try {
            mqttAndroidClient.connect(null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {

                    MqttMessage message = new MqttMessage(payload_on.getBytes());
                    message.setQos(0);
                    message.setRetained(false);

                    try {
                        mqttAndroidClient.publish(topic, message);
                    } catch (MqttException e) {
                        e.printStackTrace();
                    }
                    Log.w("Mqtt", "Message Published");
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Log.w("Mqtt", "Connection Failed");

                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }


    public void TurnOff() {

        try {
            mqttAndroidClient.connect(null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    MqttMessage message = new MqttMessage(payload_off.getBytes());
                    message.setQos(0);
                    message.setRetained(false);

                    try {
                        mqttAndroidClient.publish(topic, message);
                    } catch (MqttException e) {
                        e.printStackTrace();
                    }

                    Log.w("Mqtt", "Message Published");


                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Log.w("Mqtt", "Connection Failed");

                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }


    public void TurnLow() {

        try {
            mqttAndroidClient.connect(null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    MqttMessage message = new MqttMessage(payload_low.getBytes());
                    message.setQos(0);
                    message.setRetained(false);

                    try {
                        mqttAndroidClient.publish(topic, message);
                    } catch (MqttException e) {
                        e.printStackTrace();
                    }

                    Log.w("Mqtt", "Message Published");

                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Log.w("Mqtt", "Connection Failed");

                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }


    public void TurnMed() {

        try {
            mqttAndroidClient.connect(null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    MqttMessage message = new MqttMessage(payload_med.getBytes());
                    message.setQos(0);
                    message.setRetained(false);


                    try {
                        mqttAndroidClient.publish(topic, message);
                    } catch (MqttException e) {
                        e.printStackTrace();
                    }

                    Log.w("Mqtt", "Message Published");

                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Log.w("Mqtt", "Connection Failed");

                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void TurnHigh() {

        try {
            mqttAndroidClient.connect(null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    MqttMessage message = new MqttMessage(payload_high.getBytes());
                    message.setQos(0);
                    message.setRetained(false);

                    try {
                        mqttAndroidClient.publish(topic, message);
                    } catch (MqttException e) {
                        e.printStackTrace();
                    }

                    Log.w("Mqtt", "Message Published");

                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Log.w("Mqtt", "Connection Failed");

                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}





