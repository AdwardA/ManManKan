package jiyu.manmankan.component;

import android.content.Context;
import android.view.Menu;
import android.view.MenuItem;

import jiyu.manmankan.R;


/**
 * Created by z on 2017/10/13.
 * O(∩_∩)~
 */

public class TopActionMode implements android.support.v7.view.ActionMode.Callback {
    private Context context;

    public TopActionMode(Context context) {
        this.context = context;
    }

    @Override
    public boolean onCreateActionMode(android.support.v7.view.ActionMode mode, Menu menu) {
        mode.getMenuInflater().inflate(R.menu.menu_action_mode_top,menu);
        mode.setTitle("选择项目");
        return true;
    }

    @Override
    public boolean onPrepareActionMode(android.support.v7.view.ActionMode mode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(android.support.v7.view.ActionMode mode, MenuItem item) {
        int id=item.getItemId();
        if (id==R.id.top_action_right){
            iActionMode.onActionItemClickedSelectAll(mode,item);
        }

        return true;
    }

    @Override
    public void onDestroyActionMode(android.support.v7.view.ActionMode mode) {
        if (iActionMode==null){
            return;
        }
        iActionMode.onDestroyActionMode(mode);
    }



    private IActionMode iActionMode;
    public void setiActionMode(IActionMode iActionMode) {
        this.iActionMode = iActionMode;
    }
    public interface IActionMode{
        void onActionItemClickedSelectAll(android.support.v7.view.ActionMode mode, MenuItem item);
        void onDestroyActionMode(android.support.v7.view.ActionMode mode);

    }

}
