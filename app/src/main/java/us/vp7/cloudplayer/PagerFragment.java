package us.vp7.cloudplayer;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class PagerFragment extends Fragment {
    private Context context;
    public static final String ARG_PAGE="ARG_PAGE";
    View view=null;
    private int mPage;
    public static PagerFragment newInstant(int page){
        Bundle args=new Bundle();
        args.putInt(ARG_PAGE,page);
        PagerFragment fragment=new PagerFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage=getArguments().getInt(ARG_PAGE);
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {

      if (mPage==1){

            view=inflater.inflate(R.layout.fragment1,container,false);
            RecyclerView rec1= (RecyclerView) view.findViewById(R.id.rec1);
            rec1.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false));
            Adapter_frag1 feedAdapater1=new Adapter_frag1(getActivity());
            rec1.setAdapter(feedAdapater1);
            feedAdapater1.updateItems();
      }

        if (mPage==2){
            view=inflater.inflate(R.layout.fragment1,container,false);
            RecyclerView rec1= (RecyclerView) view.findViewById(R.id.rec1);
            rec1.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false));
            Adapter_frag2 feedAdapater2=new Adapter_frag2(getActivity());
            rec1.setAdapter(feedAdapater2);
            feedAdapater2.updateItems();
        }



        return view;

    }
}
