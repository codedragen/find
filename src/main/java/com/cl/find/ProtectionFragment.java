package com.cl.find;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zzhoujay.richtext.RichText;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Created by sks on 2017/3/14.
 */

public class ProtectionFragment extends BaseFragment {
   String title=" <h3>如何预防老年痴呆症</h3>";
    String content= "<ol>" +
            "<li>第一，饮食均衡，避免摄取过多的盐分及动物性脂肪。一天食盐的摄取量应控制在10克以下，少吃动物性脂肪及糖，蛋白质、食物纤维、维他命、矿物质等都要均衡摄取。</li>" +
            "<li>　第二，适度运动，维持腰部及脚的强壮。手指的运动也很重要，常做一些复杂精巧的手工会促进脑的活力，做菜、写日记、吹奏乐器、画画等都有预防痴呆的效果。</li>" +
            "<li>第三，避免过度喝酒、抽烟，生活有规律。喝酒过度会导致肝机能障碍、引起脑机能异常。一天喝酒超过0.3升以上的人比起一般人容易得脑血管性痴呆。抽烟不只会造成脑血管性痴呆，也是心肌梗塞等危险疾病的重要原因。</li>" +
            "<li>第四，预防动脉硬化、高血压和肥胖等生活习惯病。检查胆固醇和血压水平，筛查糖尿病，未经治疗的糖尿病、高血压和高胆固醇患者其认知障碍症的发病率相当高。</li>" +
            "<li>第五，护理好口腔卫生，远离牙龈疾病，有牙龈疾病容易出现记忆力问题。</li>" +
            "<li>第六，对事物常保持高度的兴趣及好奇心，可以增加人的注意力，防止记忆力减退。老年人应该多做些感兴趣的事及参加公益活动、社会活动等来强化脑部神经。</li>" +
            "<li>第七，要积极用脑，预防脑力衰退。即使在看电视连续剧时，随时说出自己的感想便可以达到活用脑力的目的。读书发表心得、下棋、写日记、写信等都是简单而有助于脑力的方法。</li>" +
            "<li>第八，随时对人付出关心，保持良好的人际关系，找到自己的生存价值。</li>" +
            "<li>第九，保持年轻的心，锻炼塑形。原因在于锻炼能降低血压、控制胆固醇水平和生成新的脑细胞。</li>" +
            "<li>第十，避免过于深沉、消极、唉声叹气，要以开朗的心情生活。高龄者常须面对退休、朋友亡故等失落的经验，很多人因而得了忧郁症，使免疫机能降低，没有食欲和体力，甚至长期卧床。</li>" +
            "<li>第十一，吃食物时多咀嚼，当人咀嚼食物时，可以增加20%脑部的血流量，而大脑血流量的增加对大脑细胞有养护作用。</li>" +
            "<li>第十二，要积极防治便秘。经常便秘的人，其肠道会产生氨、硫化氢、组织胺、硫醇和吲哚等多种有毒物质，这些有毒物质会随着血液循环进入大脑，从而诱发老年痴呆症。</li>" +
            "<li>第十三，尽量不要使用铝制餐具，因为铝是一种两性物质，与酸碱都可以发生化学反应。如果用铝制的餐具盛放酸性、碱性的食物，会使铝元素游离出来污染食物。而人吃了被铝离子污染过的食物，会使铝在大脑中、肝、肾、脾、甲状腺等多个组织器官中蓄积下来，这会损害人的中枢神经系统，使人的反应变得迟钝，并会加快人体器官的衰老，最终引发老年痴呆症。</li>" +
            " </ol>";
    private View rootView;
    private TextView tv_title;
    private TextView tv_content;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return rootView=inflater.inflate(R.layout.protection,container,false);
    }

    @Override
    public void lazyLoad() {
     initView();
    }

    private void initView() {
        tv_title=(TextView)rootView.findViewById(R.id.fragment_protection_title);
        tv_content=(TextView)rootView.findViewById(R.id.fragment_protection_content);
         tv_title.setGravity(Gravity.CENTER_HORIZONTAL);
        tv_content.setTextSize(18);
        RichText.from(title).into(tv_title);
        RichText.from(content).into(tv_content);


    }

    public static ProtectionFragment getInstance() {
        return new ProtectionFragment();
    }
}
