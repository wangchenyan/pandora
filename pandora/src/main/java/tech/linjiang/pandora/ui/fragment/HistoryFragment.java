package tech.linjiang.pandora.ui.fragment;


import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import tech.linjiang.pandora.cache.History;
import tech.linjiang.pandora.core.R;
import tech.linjiang.pandora.ui.item.KeyValueItem;
import tech.linjiang.pandora.ui.item.TitleItem;
import tech.linjiang.pandora.ui.recyclerview.BaseItem;
import tech.linjiang.pandora.util.SimpleTask;
import tech.linjiang.pandora.util.Utils;

/**
 * Created by linjiang on 2019/3/4.
 */

public class HistoryFragment extends BaseListFragment {

    @Override
    protected boolean enableSwipeBack() {
        return false;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getToolbar().setTitle("Activity History");
        getToolbar().getMenu().add(-1, 0, 0, R.string.pd_name_delete_key).setIcon(R.drawable.pd_delete)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        getToolbar().setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                History.clear();
                getAdapter().clearItems();
                Utils.toast(R.string.pd_success);
                return true;
            }
        });
        loadData();
    }

    private void loadData() {
        hideError();
        showLoading();
        new SimpleTask<>(new SimpleTask.Callback<Void, List<History>>() {
            @Override
            public List<History> doInBackground(Void[] params) {
                return History.query();
            }

            @Override
            public void onPostExecute(List<History> result) {
                hideLoading();
                List<BaseItem> data = new ArrayList<>(result.size());
                if (Utils.isNotEmpty(result)) {
                    data.add(new TitleItem("Task"));
                    for (History history : result) {
                        String[] value = new String[2];
                        value[0] = history.activity;
                        value[1] = history.event;
                        data.add(new KeyValueItem(value, false, false, Utils.millis2String(history.createTime, Utils.HHMMSS)));
                    }
                    getAdapter().setItems(data);
                } else {
                    showError(null);
                }
            }
        }).execute();
    }
}
