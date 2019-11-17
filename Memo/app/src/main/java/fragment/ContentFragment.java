package fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.memo.R;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContentFragment extends Fragment {

    public ContentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.new_record,container,false);
        return view;
    }

    public void refresh(String title, String content){
        TextView title_1 = (TextView) getView().findViewById(R.id.view_title);
        TextView content_1 = (TextView) getView().findViewById(R.id.view_content);
        title_1.setText(title);
        content_1.setText(content);
//        EditText title_1 = (EditText) findViewById(R.id.list_time);
//        TextView title_1 = (TextView) getView().findViewById(R.id.view_title);
    }

}
