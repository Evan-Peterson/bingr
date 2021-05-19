package com.bingr.app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bingr.app.models.MessageModel;
import com.bingr.app.net_utils.Const;
import com.google.gson.Gson;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

public class Chat extends AppCompatActivity {

    Button send;
    ImageButton exit;
    EditText textField;
    TextView displayText;

    String sender;
    String receiver;

    Gson gson = new Gson();

    private WebSocketClient socket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        send = (Button) findViewById(R.id.PC_send);
        exit = (ImageButton) findViewById(R.id.PC_exit);
        textField = (EditText) findViewById(R.id.PC_message);
        displayText = (TextView) findViewById(R.id.PC_show);

        Draft[] drafts = {
                new Draft_6455()
        };

        sender = getIntent().getStringExtra("emailId");
        receiver = getIntent().getStringExtra("receiver");

        String w = Const.URL_WS + getIntent().getStringExtra("chatURLExtension");
        Log.d("Socket", "attempting to connect at " + w);

        try {
            Log.d("Socket:", "Trying socket");
            socket = new WebSocketClient(new URI(w), (Draft) drafts[0]) {
                @Override
                public void onMessage(String message) {
                    displayText.append(message + "\n");
                }

                @Override
                public void onOpen(ServerHandshake handshake) {
                    Log.d("OPEN", "run() returned: " + "is connecting");
                }

                @Override
                public void onClose(int code, String reason, boolean remote) {
                    Log.d("CLOSE", "onClose() returned: " + reason);
                    finish();
                }

                @Override
                public void onError(Exception e) {
                    Log.d("Exception:", e.toString());
                }
            };
        } catch (URISyntaxException e) {
            Log.d("Exception:", e.getMessage());
            e.printStackTrace();
        }
        socket.connect();

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if(socket.isOpen()) {
                        MessageModel message =
                                new MessageModel(sender,
                                        receiver,
                                        textField.getText().toString());
                        String JSONMessage = gson.toJson(message);
                        socket.send(JSONMessage);
                        textField.setText("");
                        Log.v("MESSAGE", JSONMessage);
                    }
                } catch (Exception e) {
                    Log.d("ExceptionSendMessage:", e.toString());
                }
            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                socket.close();
            }
        });
    }

}