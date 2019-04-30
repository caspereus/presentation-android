package taystudio.presentasi.presentasi;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.itsrts.pptviewer.PPTViewer;

public class PPTView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pptview);

        String path = Environment.getExternalStorageDirectory().getPath() + "/presentasi/namu.ppt";
        Toast.makeText(this, path, Toast.LENGTH_SHORT).show();
        PPTViewer pptViewer = (PPTViewer) findViewById(R.id.pptviewer);
        pptViewer.setNext_img(R.drawable.next)
                .setPrev_img(R.drawable.prev)
                .setSettings_img(R.drawable.settings)
                .setZoomin_img(R.drawable.zoomin)
                .setZoomout_img(R.drawable.zoomout);
        pptViewer.loadPPT(PPTView.this,path );

    }
}
