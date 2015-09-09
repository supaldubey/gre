package in.cubestack.material.androidmaterial.fragment;

import android.content.Intent;
import android.support.v4.app.Fragment;

public class AbstractFragment extends Fragment {

    public static final String POSITION = "position";
    public static final String ITEM = "ITEM";

    public String getTabName(){ return tabName; }

    private String title;
    private String tabName;

    public void setTabName(String tabName) {
        this.tabName = tabName;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getTitle(){
        return title;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
