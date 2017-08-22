package com.ascend.wangfeng.latte.ui.launcher;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;

import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;

import java.io.InputStream;

/**
 * Created by fengye on 2017/8/22.
 * email 1040441325@qq.com
 */

public class LauncherHolderCreator implements CBViewHolderCreator<LauncherHolderCreator.Launcherholder> {
    @Override
    public Launcherholder createHolder() {
        return new Launcherholder();
    }

    class Launcherholder implements Holder<Integer> {
        private AppCompatImageView mImageView = null;

        @Override
        public View createView(Context context) {
            mImageView = new AppCompatImageView(context);
            return mImageView;
        }

        @Override
        public void UpdateUI(Context context, int position, Integer data) {
            mImageView.setBackground(getImage(context,data));
        }
        public BitmapDrawable getImage(Context context, Integer id) {
            BitmapFactory.Options opt = new BitmapFactory.Options();
            opt.inPreferredConfig = Bitmap.Config.RGB_565;
            opt.inPurgeable = true;
            opt.inInputShareable = true;
            InputStream is = context.getResources().openRawResource(id);
            Bitmap bm = BitmapFactory.decodeStream(is, null, opt);
            BitmapDrawable bd = new BitmapDrawable(context.getResources(), bm);
            return bd;
        }
    }
}
