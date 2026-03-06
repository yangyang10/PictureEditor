package com.vachel.editing;

import android.graphics.Color;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.vachel.editor.emoji.Emoji;
import com.vachel.editor.emoji.EmojiDrawer;
import com.vachel.editor.emoji.IEmojiCallback;
import com.vachel.editor.bean.StickerText;
import com.vachel.editor.PictureEditActivity;
import com.vachel.editor.util.Utils;

import java.io.File;

public class MyPicEditActivity extends PictureEditActivity implements IEmojiCallback {

    @Override
    public void initData() {
        mSupportEmoji = true;
    }

    @Override
    public View getStickerLayout() {
            return new EmojiDrawer(this).bindCallback(this);
    }

    @Override
    public void onEmojiClick(String emoji) {
        StickerText stickerText = new StickerText(emoji, Color.WHITE,Color.WHITE,0);
        onText(stickerText, false); // emoji其实也是text文本
        Utils.dismissDialog(mStickerImageDialog);
    }

    @Override
    public void onBackClick() {
        mStickerImageDialog.dismiss();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Emoji.recycleAllEmoji();
    }

    @Override
    public void onSaveSuccess(String savePath) {
        Toast.makeText(this, savePath, Toast.LENGTH_LONG).show();
        finish();
    }

    @Override
    public void onStickImgClick() {
        String url = "https://cdn.wocute.com/wocute/common/icon/condition/symptoms-heartburn.png";
        File file = getImageFilePath(url);
        if(file.exists()){
            addImage(file);
            return;
        }
        FileDownloader.getImpl().create(url).setPath(file.getAbsolutePath()).setListener(
                new FileDownloadListener(){

                    @Override
                    protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {

                    }

                    @Override
                    protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {

                    }

                    @Override
                    protected void completed(BaseDownloadTask task) {

                        Log.e("dd","task.getPath();"+task.getPath());
//                        addImage(file);
                    }

                    @Override
                    protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {

                    }

                    @Override
                    protected void error(BaseDownloadTask task, Throwable e) {

                    }

                    @Override
                    protected void warn(BaseDownloadTask task) {

                    }
                }
        ).start();
    }

    private void addImage(File file){
        Uri imageUri = Uri.fromFile(file);
        mPictureEditView.addStickerImage(imageUri);
    }

    public static final String DIRECTORY_IMAGE = "picture";
    public static final String SUFFIX_TYPE_IMAGE = ".png";
    public File getImageFilePath(String url) {
        return getDownloadFile(url, DIRECTORY_IMAGE, SUFFIX_TYPE_IMAGE);
    }

    public  File getDownloadFile(String name, String directory, String suffixType) {
        //获取保存路径
        File saveDir = getExternalFilesDir("download/" + directory);
        //根据ID拼接名称
        return new File(saveDir, EncryptUtils.encryptMD5ToString(name + suffixType) + suffixType);
    }
}
