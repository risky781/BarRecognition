package com.learn.camerascan;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {

    private final static int SCANNIN_GREQUEST_CODE = 1;

    TextView mTextView;
    ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = (TextView) findViewById(R.id.result);
        mImageView = (ImageView) findViewById(R.id.iv_scan_bitmap);

        //�����ť��ת����ά��ɨ����棬�����õ���startActivityForResult��ת
        //ɨ������֮������ý���
        Button scanButton = (Button) findViewById(R.id.button_scan);
        scanButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, MipcaActivityCapture.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivityForResult(intent, SCANNIN_GREQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case SCANNIN_GREQUEST_CODE:
//                if(requestCode == RESULT_OK) {
                    Bundle bundle = data.getExtras();
                    String myUrl = bundle.getString("result");
                    Bitmap bitmap = data.getParcelableExtra("bitmap");

                    mTextView.setText(myUrl);
                    mImageView.setImageBitmap(bitmap);

                    isDialogRemind(myUrl);
//                }
                break;
        }
    }

    private void isDialogRemind(final String myUrl) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("提示：")
                .setIcon(R.drawable.dialog_reminder)
                .setMessage("是否打开链接： " + myUrl + " ?");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(myUrl));
                startActivity(intent);
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create()
                .show();
    }

}
