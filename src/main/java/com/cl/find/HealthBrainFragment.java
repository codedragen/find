package com.cl.find;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.Spanned;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zzhoujay.richtext.RichText;

/**
 * Created by sks on 2017/3/14.
 */

public class HealthBrainFragment extends BaseFragment {


    private View rootView;
    private TextView tv_content;
    private  String title="<h3 align=\"center\">阿尔茨海默病的前期症状</h3>";
    private String content= " <ol>" +
            " <li><font size=\"6\">记忆力衰退在生活中的表现为对近期事物的遗忘，比如不记得刚刚说过的话，不记得刚刚吃了午饭。</font></li>" +
            " <li>不能完成熟悉的工作，对于曾经熟练掌握的工作不能熟练完成。</li>" +
            " <li>语言表达出现障碍经常忘记熟悉的语言，或者话到嘴边不知如何表达，说出来的话没有逻辑，让人难以理解。</li>" +
            " <li>搞不清时间和地点阿尔茨海默病患者出门常迷路，记不住日期，甚至分不清白天和黑夜。</li>" +
            " <li>判断力受损阿尔茨海默病会让人丧失对一些事物的正确判断能力，例如花很多钱去买根本不值钱的东西，或者吃已经不再新鲜的食物，甚至会横冲直撞地过马路。</li>" +
            " <li>理解力下降阿尔茨海默病早期患者与人交流往往会出现一定的障碍，跟不上他人的交谈思路。</li>" +
            " <li>情绪或行为的改变无缘无故地情绪涨落，或者情绪变得淡漠、麻木。</li>" +
            " <li>将物品或钱错放在不恰当的地方水果放在衣柜里，袜子放在餐桌上......总把东西放错地儿，也是阿尔茨海默病的一大征兆。</li>" +
            " <li>性格改变糊涂、多疑、害怕、易怒、焦虑、抑郁......阿尔茨海默病很可能会改变一个人的性格。</li>" +
            " <li>兴趣丧失有的患者能在电视机前呆坐好几个小时，又或者长时间地昏昏欲睡。阿尔茨海默病甚至会偷走一个人的爱好，让人对以前的喜好不再有兴趣。</li>" +
            " </ol>";
    private TextView tv_title;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return rootView=inflater.inflate(R.layout.fragment_healthbrain,container,false);
    }

    @Override
    public void lazyLoad() {
      initView();
    }

    private void initView() {
        tv_content= (TextView) rootView.findViewById(R.id.fragment_healthbrain_content);
        tv_title=(TextView)rootView.findViewById(R.id.fragment_healthbrain_title);
       tv_title.setText(Html.fromHtml(title));
        tv_content.setTextSize(18);
        tv_title.setGravity(Gravity.CENTER);


        RichText.fromHtml(content).into(tv_content);
    }

    public static HealthBrainFragment getInstance() {

        return  new HealthBrainFragment();
    }
}
