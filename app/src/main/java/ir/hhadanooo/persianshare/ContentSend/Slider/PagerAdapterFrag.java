package ir.hhadanooo.persianshare.ContentSend.Slider;



import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;



public class PagerAdapterFrag extends FragmentStatePagerAdapter {

    private int tabCount;


    public PagerAdapterFrag(FragmentManager fm , int tabCount ) {
        super(fm);
        this.tabCount = tabCount;

    }



    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new SlideFileManager();
            case 1:
                return new SlideAppPicker();
            default:
                return null;
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        switch (position) {
            case 0:
                return "FileManager";
            case 1:
                return "AppPicker";
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }


}
