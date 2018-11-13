MyApplication中可以设置副单位（只能在 pt、in、mm 这三个冷门单位中选择一个作为副单位，选择什么单位就在 layout 文件中用什么单位进行布局）。

AutoSizeConfig.getInstance().getUnitsManager()
        .setSupportDP(false).setSupportSubunits(Subunits.MM);

Activity
Customize the adaptation parameters of the Activity:
public class CustomAdaptActivity extends AppCompatActivity implements CustomAdapt {

    @Override
    public boolean isBaseOnWidth() {
        return false;
    }

    @Override
    public float getSizeInDp() {
        return 667;
    }
}
Cancel the adaptation of the Activity:
public class CancelAdaptActivity extends AppCompatActivity implements CancelAdapt {

}
Fragment
First enable the ability to support Fragment custom parameters
AutoSizeConfig.getInstance().setCustomFragment(true);
Customize the adaptation parameters of the Fragment:
public class CustomAdaptFragment extends Fragment implements CustomAdapt {

    @Override
    public boolean isBaseOnWidth() {
        return false;
    }

    @Override
    public float getSizeInDp() {
        return 667;
    }
}
Cancel the adaptation of the Fragment:
public class CancelAdaptFragment extends Fragment implements CancelAdapt {

}
XML

<TextView
    android:layout_width="200mm"
    android:layout_height="100mm"
    android:background="@color/colorAccent" />

ProGuard
 -keep class me.jessyan.autosize.** { *; }
 -keep interface me.jessyan.autosize.** { *; }
 
 博客参考
 http://www.wanandroid.com/blog/show/2343