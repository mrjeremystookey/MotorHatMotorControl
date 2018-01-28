package rover.stookey.motorhatmotorcontrol;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.things.contrib.driver.motorhat.MotorHat;
import com.google.android.things.pio.I2cDevice;
import com.google.android.things.pio.PeripheralManagerService;

import java.io.IOException;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Skeleton of an Android Things activity.
 * <p>
 * Android Things peripheral APIs are accessible through the class
 * PeripheralManagerService. For example, the snippet below will open a GPIO pin and
 * set it to HIGH:
 * <p>
 * <pre>{@code
 * PeripheralManagerService service = new PeripheralManagerService();
 * mLedGpio = service.openGpio("BCM6");
 * mLedGpio.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
 * mLedGpio.setValue(true);
 * }</pre>
 * <p>
 * For more complex peripherals, look for an existing user-space driver, or implement one if none
 * is available.
 *
 * @see <a href="https://github.com/androidthings/contrib-drivers#readme">https://github.com/androidthings/contrib-drivers#readme</a>
 */
public class MotorController extends Activity {
    private static final String TAG = "MotorController";
    private MotorHat motorHat;
    private I2cDevice mDevice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_motor_controller);

        //Looking for I2C devices
        PeripheralManagerService manager = new PeripheralManagerService();
        List<String> deviceList = manager.getI2cBusList();
        if(deviceList.isEmpty()){
            Log.i(TAG, "no I2C bus available");
        } else {
            Log.i(TAG, "List of available devices: " + deviceList);
        }

/*
        //Connecting to I2C devices
        try {
            Log.d(TAG, "opening the I2C device (motorhat)");
            mDevice = manager.openI2cDevice(deviceList.get(0), 0x60);
            Log.d(TAG, mDevice.toString());
        } catch (IOException e) {
            Log.d(TAG, "Exception opening the I2C device");
        }*/

        try {
            Log.d(TAG, "initializing the Motorhat");
            motorHat = new MotorHat(deviceList.get(0));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            motorHat.setMotorSpeed(0,255);
            motorHat.setMotorState(0, MotorHat.MOTOR_STATE_CW);
            motorHat.setMotorSpeed(1,255);
            motorHat.setMotorState(1, MotorHat.MOTOR_STATE_CW);
            motorHat.setMotorSpeed(2,255);
            motorHat.setMotorState(2, MotorHat.MOTOR_STATE_CW);
            motorHat.setMotorSpeed(3,255);
            motorHat.setMotorState(3, MotorHat.MOTOR_STATE_CW);
            //motorHat.setMotorState(0, MotorHat.MOTOR_STATE_CCW);
            motorHat.setMotorState(0, MotorHat.MOTOR_STATE_RELEASE);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try{
            motorHat.close();
        } catch (IOException e){
            Log.d(TAG, "Error closing the hat");
        }


    }
}
