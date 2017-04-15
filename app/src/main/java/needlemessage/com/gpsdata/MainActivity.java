package needlemessage.com.gpsdata;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private FileTransferProtocol ftp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ftp = new FileTransferProtocol("192.168.8.101", "pi","rapsberry");
        ftp.setOnFileTransfer(new FileTransferProtocol.OnFileTransfer() {
            @Override
            public void onFileTransfer(File file) {
                Toast.makeText(MainActivity.this, "File has been transferred successfully", Toast.LENGTH_SHORT).show();
            }
        });
        ftp.getFile("Desktop/gpsData", "watt.txt");
    }
}
