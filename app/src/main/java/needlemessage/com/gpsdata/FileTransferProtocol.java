package needlemessage.com.gpsdata;

import android.os.Environment;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by Ali on 4/15/2017.
 */
public class FileTransferProtocol {

    private FTPClient client;
    private OnFileTransfer onFileTransfer;

    public FileTransferProtocol(String ip ,String user, String pass){
        /*
        / open connection with FTP server immediately
         */

        try {
            client.connect(ip);
            client.login(user, pass);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public File getFile(String folderPath, String fileName){
        /*
            File name must include extension
         */
        File file = null;
        OutputStream out = null;

        try {
            client.setFileType(FTP.BINARY_FILE_TYPE);
            boolean success = client.changeWorkingDirectory(folderPath);

            if(success){
                String sdCard = Environment.getExternalStorageDirectory().getAbsolutePath();
                File dir = new File(sdCard + "/GPS Data");
                if (!dir.exists()) {
                    dir.mkdir();
                }

                file = new File(dir.getAbsolutePath() + "/" + fileName);
                out = new BufferedOutputStream(new FileOutputStream(file));

                //call interface upon transferring file
                onFileTransfer.onFileTransfer(file);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


        return file;
    }

    public OnFileTransfer getOnFileTransfer() {
        return onFileTransfer;
    }

    public void setOnFileTransfer(OnFileTransfer onFileTransfer) {
        this.onFileTransfer = onFileTransfer;
    }

    public interface OnFileTransfer{
        public void onFileTransfer(File file);
    }
}
