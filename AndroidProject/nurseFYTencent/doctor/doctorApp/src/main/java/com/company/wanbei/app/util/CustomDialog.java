//package com.company.wanbei.app.util;
//
//import android.app.Dialog;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.widget.EditText;
//import android.widget.TextView;
//
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.company.wanbei.app.R;
//
//public class CustomDialog extends Dialog {
//
//    public CustomDialog(Context context) {
//        super(context);
//    }
//
//    public CustomDialog(Context context, int theme) {
//        super(context, theme);
//    }
//    public static class Builder {
//        private Context context;
//        private String title;
//        private String message;
//        private String positiveButtonText;
//        private String negativeButtonText;
//        private EditText edittxt_result;
//        private View contentView;
//        private OnClickListener positiveButtonClickListener;
//        private OnClickListener negativeButtonClickListener;
//
//        public Builder(Context context) {
//            this.context = context;
//        }
//
//        public Builder setMessage(String message) {
//            this.message = message;
//            return this;
//        }
//
//        /**
//         * Set the Dialog message from resource
//         *
//         * @param message
//         * @return
//         */
//        public Builder setMessage(int message) {
//            this.message = (String) context.getText(message);
//            return this;
//        }
//
//        /**
//         * Set the Dialog title from resource
//         *
//         * @param title
//         * @return
//         */
//        public Builder setTitle(int title) {
//            this.title = (String) context.getText(title);
//            return this;
//        }
//
//        /**
//         * Set the Dialog title from String
//         *
//         * @param title
//         * @return
//         */
//
//        public Builder setTitle(String title) {
//            this.title = title;
//            return this;
//        }
//
//        public Builder setContentView(View v) {
//            this.contentView = v;
//            return this;
//        }
//        public String getResult() {
//            return this.edittxt_result.getText().toString();
//        }
//        public Builder setEditText(String msg) {
//            if ("".equals(msg)) {
//                this.edittxt_result.setHint(this.context.getString(R.string.contents));
//            } else {
//                this.edittxt_result.setHint(msg);
//            }
//
//            return this;
//        }
//        /**
//         * Set the positive button resource and it's listener
//         *
//         * @param positiveButtonText
//         * @return
//         */
//        public Builder setPositiveButton(int positiveButtonText,
//                                         OnClickListener listener) {
//            this.positiveButtonText = (String) context
//                    .getText(positiveButtonText);
//            this.positiveButtonClickListener = listener;
//            return this;
//        }
//
//        public Builder setPositiveButton(String positiveButtonText,
//                                         OnClickListener listener) {
//            this.positiveButtonText = positiveButtonText;
//            this.positiveButtonClickListener = listener;
//            return this;
//        }
//
//        public Builder setNegativeButton(int negativeButtonText,
//                                         OnClickListener listener) {
//            this.negativeButtonText = (String) context
//                    .getText(negativeButtonText);
//            this.negativeButtonClickListener = listener;
//            return this;
//        }
//
//        public Builder setNegativeButton(String negativeButtonText,
//                                         OnClickListener listener) {
//            this.negativeButtonText = negativeButtonText;
//            this.negativeButtonClickListener = listener;
//            return this;
//        }
//
//        public CustomDialog create() {
//            LayoutInflater inflater = (LayoutInflater) context
//                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            // instantiate the dialog with the custom Theme
//            final CustomDialog dialog = new CustomDialog(context,R.style.Dialog);
//            View layout = inflater.inflate(R.layout.dialog_counseling_ask, null);
//            dialog.addContentView(layout, new RecyclerView.LayoutParams(
//                    RecyclerView.LayoutParams.FILL_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
//            // set the dialog title
//            ((TextView) layout.findViewById(R.id.title)).setText(title);
//            // set the confirm button
//
//            this.edittxt_result = (EditText)layout.findViewById(R.id.message);
//            edittxt_result.setHint("请输入首次咨询内容");
//
//            if (positiveButtonText != null) {
//                ((TextView) layout.findViewById(R.id.positiveButton))
//                        .setText(positiveButtonText);
//                if (positiveButtonClickListener != null) {
//                    ((TextView) layout.findViewById(R.id.positiveButton))
//                            .setOnClickListener(new View.OnClickListener() {
//                                public void onClick(View v) {
//                                    positiveButtonClickListener.onClick(dialog,
//                                            DialogInterface.BUTTON_POSITIVE);
//                                }
//                            });
//                }
//            } else {
//                // if no confirm button just set the visibility to GONE
//                layout.findViewById(R.id.positiveButton).setVisibility(
//                        View.GONE);
//            }
//            // set the cancel button
//            if (negativeButtonText != null) {
//                ((TextView) layout.findViewById(R.id.negativeButton))
//                        .setText(negativeButtonText);
//                if (negativeButtonClickListener != null) {
//                    ((TextView) layout.findViewById(R.id.negativeButton))
//                            .setOnClickListener(new View.OnClickListener() {
//                                public void onClick(View v) {
//                                    negativeButtonClickListener.onClick(dialog,
//                                            DialogInterface.BUTTON_NEGATIVE);
//                                }
//                            });
//                }
//            } else {
//                // if no confirm button just set the visibility to GONE
//                layout.findViewById(R.id.negativeButton).setVisibility(
//                        View.GONE);
//            }
//            // set the content message
//            if (message != null) {
//                ((TextView) layout.findViewById(R.id.message)).setText(message);
//            }
//            dialog.setContentView(layout);
//            return dialog;
//        }
//
//        public CustomDialog create2() {
//            LayoutInflater inflater = (LayoutInflater) context
//                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            // instantiate the dialog with the custom Theme
//            final CustomDialog dialog = new CustomDialog(context,R.style.Dialog);
//            View layout = inflater.inflate(R.layout.dialog_counseling_ask, null);
//            dialog.addContentView(layout, new RecyclerView.LayoutParams(
//                    RecyclerView.LayoutParams.FILL_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
//            // set the dialog title
//            ((TextView) layout.findViewById(R.id.title)).setText(title);
//            // set the confirm button
//
////            this.edittxt_result = (EditText)layout.findViewById(R.id.message);
////            edittxt_result.setHint("请输入首次咨询内容");
//
//            if (positiveButtonText != null) {
//                ((TextView) layout.findViewById(R.id.positiveButton))
//                        .setText(positiveButtonText);
//                if (positiveButtonClickListener != null) {
//                    ((TextView) layout.findViewById(R.id.positiveButton))
//                            .setOnClickListener(new View.OnClickListener() {
//                                public void onClick(View v) {
//                                    positiveButtonClickListener.onClick(dialog,
//                                            DialogInterface.BUTTON_POSITIVE);
//                                }
//                            });
//                }
//            } else {
//                // if no confirm button just set the visibility to GONE
//                layout.findViewById(R.id.positiveButton).setVisibility(
//                        View.GONE);
//            }
//            // set the cancel button
//            if (negativeButtonText != null) {
//                ((TextView) layout.findViewById(R.id.negativeButton))
//                        .setText(negativeButtonText);
//                if (negativeButtonClickListener != null) {
//                    ((TextView) layout.findViewById(R.id.negativeButton))
//                            .setOnClickListener(new View.OnClickListener() {
//                                public void onClick(View v) {
//                                    negativeButtonClickListener.onClick(dialog,
//                                            DialogInterface.BUTTON_NEGATIVE);
//                                }
//                            });
//                }
//            } else {
//                // if no confirm button just set the visibility to GONE
//                layout.findViewById(R.id.negativeButton).setVisibility(
//                        View.GONE);
//            }
//            // set the content message
//            if (message != null) {
//                ((TextView) layout.findViewById(R.id.message)).setText(message);
//            }
//            dialog.setContentView(layout);
//            return dialog;
//        }
//    }
//}
