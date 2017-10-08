package com.hardcopy.arduinocontroller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class ArduinoControllerActivity extends Activity {

	private Context context = null;
	private Communication communication = null;
	
	//private TextView mTextLog = null; //alterado;
	private TextView mTextInfo = null;

	private ImageButton button1 = null;
	private ImageButton button2 = null;
	private ImageButton button3 = null;
	private ImageButton button4 = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// System
		context = getApplicationContext();
		
		// Layouts
		setContentView(R.layout.activity_arduino_controller);

		//mTextLog = (TextView) findViewById(R.id.list_devices); //alterado;
		//mTextLog.setMovementMethod(new ScrollingMovementMethod());
		mTextInfo = (TextView) findViewById(R.id.text_info);
		mTextInfo.setMovementMethod(new ScrollingMovementMethod());

		button1 = (ImageButton) findViewById(R.id.imageButton);
		button2 = (ImageButton) findViewById(R.id.imageButton2);
		button3 = (ImageButton) findViewById(R.id.imageButton3);
		button4 = (ImageButton) findViewById(R.id.imageButton4);

		button1.setOnTouchListener(new View.OnTouchListener() {

			@Override public boolean onTouch(View v, MotionEvent event) {

				switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN:
						communication.send("moveforward");
						break;
					case MotionEvent.ACTION_UP:
						communication.send("-");
						break;
					case MotionEvent.ACTION_CANCEL:
						break;
				}

				return false;
			}
		});

		button2.setOnTouchListener(new View.OnTouchListener() {

			@Override public boolean onTouch(View v, MotionEvent event) {

				switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN:
						communication.send("rotateright");
						break;
					case MotionEvent.ACTION_UP:
						communication.send("-");
						break;
					case MotionEvent.ACTION_CANCEL:
						break;
				}

				return false;
			}
		});

		button3.setOnTouchListener(new View.OnTouchListener() {

			@Override public boolean onTouch(View v, MotionEvent event) {

				switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN:
						communication.send("rotateleft");
						break;
					case MotionEvent.ACTION_UP:
						communication.send("-");
						break;
					case MotionEvent.ACTION_CANCEL:
						break;
				}

				return false;
			}
		});

		button4.setOnTouchListener(new View.OnTouchListener() {

			@Override public boolean onTouch(View v, MotionEvent event) {

				switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN:
						communication.send("moveback");
						break;
					case MotionEvent.ACTION_UP:
						communication.send("-");
						break;
					case MotionEvent.ACTION_CANCEL:
						break;
				}

				return false;
			}
		});

		// Initialize
		InputListener inputListener = new InputListener() {
			@Override
			public void onRead(String message) {
				mTextInfo.setText((String) message);
				//mTextLog.append((String) message);
				//mTextLog.append("\n");
			}

			@Override
			public void onError(String error) {
				Toast.makeText(ArduinoControllerActivity.this, error, Toast.LENGTH_SHORT).show();
			}
		};
		
		// Initialize Serial connector and starts Serial monitoring thread.
		communication = new BluetoothCommunication();
		communication.start(this, inputListener);
		if (!communication.isConnected()) {
			Toast.makeText(this, "Não foi possível conectar. :( Por favor feche a aplicação e tente novamente.", Toast.LENGTH_SHORT).show();
		}
	}
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		communication.onActivityResult(requestCode, resultCode, data);
		try {
			Thread.sleep(2000);
		} catch(InterruptedException ex) {
			Thread.currentThread().interrupt();
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return true;
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		
		communication.stop();
	}
}

