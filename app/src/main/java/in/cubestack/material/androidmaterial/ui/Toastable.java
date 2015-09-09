package in.cubestack.material.androidmaterial.ui;

/**
 * Created by cubestack on 9/9/2015.
 */
public interface Toastable {

    void showLoadingProgressDialog();

    void showProgressDialog(CharSequence message);

    void showLoadingProgressDialog(boolean closeable);

    void showProgressDialog(CharSequence message, boolean closeable);

    void dismissProgressDialog();

    void toast(String message);

    void updateProgressDialogMessage(String msg);
}
