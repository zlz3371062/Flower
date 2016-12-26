package com.nbhero.DIYview;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.nbhero.flower.R;

/**
 * Created by zhenglingzhong on 2016/12/14.
 */

public class FlowerLoading extends Dialog {

    public FlowerLoading(Context context) {

        super(context);
    }

    public static class Builder {

        private Context context;
        private String title;
        private String message;
        private String positiveButtonText;
        private String negativeButtonText;
        private View contentView;
        private OnClickListener positiveButtonClickListener;
        private OnClickListener negativeButtonClickListener;
        private FlowerLoading dialog ;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        /**
         * Set the Dialog message from resource
         *
         * @param
         * @return
         */
        public Builder setMessage(int message) {
            this.message = (String) context.getText(message);
            return this;
        }

        /**
         * Set the Dialog title from resource
         *
         * @param title
         * @return
         */
        public Builder setTitle(int title) {
            this.title = (String) context.getText(title);
            return this;
        }

        /**
         * Set the Dialog title from String
         *
         * @param title
         * @return
         */

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setContentView(View v) {
            this.contentView = v;
            return this;
        }


        public void dismiss() {

            dialog.dismiss();

        }

        public FlowerLoading create() {

            dialog = new FlowerLoading(context);

            Window dialogWindow = dialog.getWindow();
            dialogWindow.requestFeature(Window.FEATURE_NO_TITLE);
            View view = LayoutInflater.from(context).inflate(R.layout.loadin,null);
            dialogWindow.setContentView(view);
            dialogWindow.setBackgroundDrawableResource(R.drawable.back_dialog);

            WindowManager.LayoutParams lp = dialogWindow.getAttributes();

            lp.width = (int)((context.getResources().getDisplayMetrics().widthPixels) * 0.7 );

//             AboutGas gas = (AboutGas) view.findViewById(R.id.dialog_loading);
            TextView messagetxt = (TextView) view.findViewById(R.id.dialog_message);
            messagetxt.setText(message);

            dialogWindow.setAttributes(lp);
            dialog.setCanceledOnTouchOutside(false);

            return  dialog;
        }

    }
}
