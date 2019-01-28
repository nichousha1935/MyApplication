package fragment;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.ind4.myapplication.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import refresh.ind4Header;
import utils.FixDexUtils;

/**
 * 文件描述:
 *
 * @author Administrator
 * @date 2019/1/9
 */
public class CBAFragment extends Fragment {
    private SmartRefreshLayout refreshLayout;
    private ListView listView;
    private List<String> list=new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cba, null);
        listView=view.findViewById(R.id.listView);
        refreshLayout = view.findViewById(R.id.refreshLayout);
        refreshLayout.setRefreshHeader(new ind4Header(getContext()));
        refreshLayout.setRefreshFooter(new ClassicsFooter(getContext()));
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        for (int i=0;i<30;i++){
            list.add(i+"");
        }
        final ArrayAdapter<String> adapter=new ArrayAdapter<>(getContext(),R.layout.item_text,R.id.text,list);
        listView.setAdapter(adapter);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.finishRefresh(2000, true);
                list.clear();
                for (int i=0;i<30;i++){
                    list.add(i+"");
                }
                adapter.notifyDataSetChanged();
                refreshLayout.setEnableLoadMore(true);
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                for (int i=30;i<60;i++){
                    list.add(i+"");
                }
                adapter.notifyDataSetChanged();
                refreshLayout.finishLoadMore(2000, true, true);
            }
        });
    }
}
