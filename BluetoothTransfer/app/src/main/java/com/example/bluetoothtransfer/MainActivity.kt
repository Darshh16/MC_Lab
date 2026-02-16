package com.example.bluetoothtransfer

import android.Manifest
import android.bluetooth.*
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import java.io.InputStream
import java.io.OutputStream
import java.util.*

class MainActivity : AppCompatActivity() {

    private val uuid: UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")
    private lateinit var bluetoothAdapter: BluetoothAdapter
    private var socket: BluetoothSocket? = null
    private lateinit var outputStream: OutputStream

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()

        val scanBtn = findViewById<Button>(R.id.btnScan)
        val listView = findViewById<ListView>(R.id.deviceList)
        val sendBtn = findViewById<Button>(R.id.btnSend)
        val messageBox = findViewById<EditText>(R.id.messageBox)
        val status = findViewById<TextView>(R.id.status)

        requestPermissions()

        scanBtn.setOnClickListener {

            val devices = bluetoothAdapter.bondedDevices
            val list = ArrayList<String>()
            val deviceObjects = ArrayList<BluetoothDevice>()

            for (device in devices) {
                list.add(device.name + "\n" + device.address)
                deviceObjects.add(device)
            }

            val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, list)
            listView.adapter = adapter

            listView.setOnItemClickListener { _, _, position, _ ->
                val device = deviceObjects[position]

                Thread {
                    try {
                        socket = device.createRfcommSocketToServiceRecord(uuid)
                        socket!!.connect()
                        outputStream = socket!!.outputStream

                        runOnUiThread {
                            status.text = "Connected to ${device.name}"
                        }

                    } catch (e: Exception) {
                        runOnUiThread {
                            status.text = "Connection Failed"
                        }
                    }
                }.start()
            }
        }

        sendBtn.setOnClickListener {
            val message = messageBox.text.toString()
            if(socket != null) {
                outputStream.write(message.toByteArray())
                Toast.makeText(this, "Data Sent", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.BLUETOOTH_CONNECT,
                Manifest.permission.BLUETOOTH_SCAN,
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            1
        )
    }
}
