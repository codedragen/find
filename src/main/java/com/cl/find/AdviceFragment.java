package com.cl.find;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zzhoujay.richtext.RichText;

/**
 * Created by Administrator on 2017/3/12 0012.
 */

public class AdviceFragment extends BaseFragment {
    String title="<h3>老年痴呆症患者的护理知识</h3>";
String content="<ol>" +
        "<h5>老年痴呆病人常见的护理问题</h5>" +
        "<li>自理缺陷:与老年痴呆病人动作慢、行动困难，合并躯体疾病，精神障碍、记忆和智能方面障碍等有关</li>" +
        "<li>认识环境受损综合征:与缺乏或丧失记忆力、定向力和判断力有关；脑血流下降，神经介质活性下降，脑经元数量减少致认知功能减退及脑卒中等因素有关。</li>" +
        "<li>语言沟通障碍:与交往能力降低或丧失有关，表现为表达或理解困难;与脑血管病变有关;与阿尔采默痴呆严重程度有关且为最主要因素。</li>" +
        "<li>思维过程受损:与脑萎缩、脑血管疾病、合并精神症状等因素有关。</li>" +
        "<li>睡眠障碍:与身体不适，不适应环境变化和抑郁有关，与兴奋性降低、脑供血不足及精神症状也可能。</li>" +
        "<h5>老年痴呆病人的护理对策</h5>" +
        "<li>针对老年痴呆症患者的自理缺陷，护理措施上对日常生活困难或不能，衣着和住房不清洁或穿脱衣服困难或不能的病人，注意传递情感交流、使其身心舒适，同时注意病人的害羞和自尊心理:对于独立进食有困难的病人，进行耐心的进食协助，并以集体进餐的方式通过人际交流来增进病人食欲；向病人表达出理解病人自己动手自我照顾的困难，在给予生活技能训练的同时，给予必要的帮助莉耐心的指导。</li>" +
        "<li>针对认识环境受损综合征，在护理上要限制病人独自活动的范围以防走失或受伤，在病人衣服上做好标记，经常协助病人确认现实环境；在此护理中要注意耐心、态度温和，切忌对一些病人表现出来的“找不着方向”的看似好笑钓行为加以取笑。</li>" +
        "<li>针对语言沟通障碍，需要评估病人语言沟通的能力和妨碍语言沟通的因素，对于有语言沟通障碍的病人，注意观察其非语言沟通的信息，鼓励病人慢慢讲话，家属讲话时注意的技巧为:慢慢说、简单说、耐心说和重复关键词并手势；鼓励病人尝试与他人沟通，开始由家属陪伴，直至病人觉得他人不在意他有语言表达限制，或接受他为止，以满足病人的心理需要和维持自尊心，鼓励参加集体活动，在与他人的互动中提高病人的自我价值感；教病人的照顾者与病人的沟通技巧，并鼓励他们之间多进行沟通。</li>" +
        "<li>针对思维过程受损，除管理好清洁剂、消毒剂等物品，以防病人误食中毒外，在心理护理方面，鼓励轻、中度的痴呆的病人多看报、勤交流、常运动，以延缓认知功能减退；鼓励病人回忆过去的生活经历，帮助其认识目前生活中的真实人物。</li>" +
        "</ol>";
    private View rootView;
    private TextView tv_title;
    private TextView tv_content;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return rootView=inflater.inflate(R.layout.fragment_advice,container,false);
    }

    @Override
    public void lazyLoad() {
     initView();
    }

    private void initView() {
        tv_content=(TextView)rootView.findViewById(R.id.fragment_advice_content);
        tv_title=(TextView)rootView.findViewById(R.id.fragment_advice_title);
        tv_title.setGravity(Gravity.CENTER_HORIZONTAL);
        tv_content.setTextSize(18);
        RichText.fromHtml(title).into(tv_title);
        RichText.fromHtml(content).into(tv_content);
        //tv_content.setText(Html.fromHtml(content));

    }

    public static AdviceFragment getInstance() {
        return new AdviceFragment();
    }
}
