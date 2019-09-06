//
//                       _oo0oo_
//                      o8888888o
//                      88" . "88
//                      (| -_- |)
//                      0\  =  /0
//                    ___/`---'\___
//                  .' \|     |// '.
//                 / \|||  :  |||// \
//                / _||||| -:- |||||- \
//               |   | \  -  /// |     |
//               | \_|  ''\---/''  |_/ |
//               \  .-\__  '-'  ___/-. /
//             ___'. .'  /--.--\  `. .'___
//          ."" '<  `.___\_<|>_/___.' >' "".
//         | | :  `- \`.;`\ _ /`;.`/ - ` : | |
//         \  \ `_.   \_ __\ /__ _/   .-` /  /
//     =====`-.____`.___ \_____/___.-`___.-'=====
//                       `=---='
//
//
//     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//
//               佛祖保佑         永无BUG
package com.coop.android.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.coop.android.BuildConfig;
import com.coop.android.CBaseActivity;
import com.coop.android.R;
import com.umeng.analytics.MobclickAgent;

import zuo.biao.library.ui.statusbar.StatusBarUtils;

/**
 * Created by MR-Z on 2018/12/11.
 */
public class UserAgreementActivity extends CBaseActivity {
    protected Toolbar toolBar;
    private TextView aboutDesc;
    private boolean isExpanded = false;
    private String aboutUs;

    /**
     * 启动这个Activity的Intent
     *
     * @param context
     * @return
     */
    public static Intent createIntent(Context context) {
        return new Intent(context, UserAgreementActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_agree);
        StatusBarUtils.setStatusBarColorDefault(this);
        initView();
        initData();
        initEvent();
    }

    @Override
    public void initView() {
        aboutUs = "欢迎您使用CO-OP！\n" +
                "CO-OP在此特别提示，如您欲接受CO-OP服务，须事先认真阅读本协议全部条款（未成年人阅读时应得到其监护人的陪同），特别是本协议中免除、减轻或者限制CO-OP责任的全部条款以及含有限制用户权利内容的全部条款。如果您对本协议的任何条款表示异议, 您可以选择不使用CO-OP服务。当您使用了CO-OP服务,即视为您已阅读、理解并同意受本协议的约束。此外，当您接受CO-OP服务时，您和CO-OP应遵守CO-OP随时公布的与该服务相关的指引和规则。前述所有的指引和规则，均构成本协议的一部分。\n" +
                "\n" +
                "您特此声明，已经完全理解本协议相关内容，并不存在任何重大误解；同时，认可协议内容并不存在有失公平的情形。\n" +
                "\n" +
                "一、定义与解释\n" +
                "1.1用户：使用CO-OP服务的自然人、法人或其他组织,在本协议中更多地称之为“您”。\n" +
                "1.2 CO-OP：北京盛美宇辰科技有限公司（后面简称盛美宇辰）以及其相关关联机构为提供本服务和与之相关服务的运营单位。CO-OP是一个应用区块链技术帮助创始人管理企业股权并实现企业股权的快速确权的管理工具平台。平台目的是帮助创始人更好的管理公司股权，同时激活股权的未来价值属性，帮助创始人可以通过非现金的方式与拥有资源协作者达成合作。\n" +
                "1.3本服务：CO-OP向创始人提供的工具旨在帮助企业实现高效的股权管理，并以区块链技术记录方式实现股权标记、归属、转移以及相关权益划分的真实性、有效性、合理性的平台化服务。\n" +
                "1.4本协议：您与CO-OP之间关于您使用CO-OP创业服务所订立的协议，此外还包括CO-OP随时公布的、与本服务相关的指引和规则。此种指引和规则，均构成本协议的一部分。\n" +
                "1.5协作者：符合CO-OP规定的具有独立行为能力的自然人或法人组织，同意遵守平台相关协议约定、自愿申请并经CO-OP审核通过，接受CO-OP相关服务的用户。\n" +
                "1.6项目：由创业者提交平台审核，并已经完成合法的公司注册，拥有董事会授权的创业项目。\n" +
                "1.7账号：用户在CO-OP、网站、或其他关联方网站建立的网络空间，可用来浏览、获知、填写、发布相关信息。\n" +
                "1.8 BP：商业计划书。\n" +
                "1.9 数字权证：CO-OP利用区块链技术对于每个项目单独生成、记录、存储的数字化资产标示，每一数字化资产对应权利和规则依据CO-OP与项目签订的协议为准。\n" +
                "\n" +
                "二、协议当事人\n" +
                "2.1 CO-OP与用户经友好协商，依据《中华人民共和国合同法》、《中华人民共和国公司法》及其他相关法律、法规的规定，本着平等、自愿、等价有偿的原则，就CO-OP向用户提供服务事宜，达成本服务协议，以资共同遵守。\n" +
                "2.2除非本协议中另有约定，用户、CO-OP在本协议中合称为“双方”，单称为“一方”。\n" +
                "\n" +
                "三、服务内容、费用与期限\n" +
                "3.1 CO-OP是一个专业化的股权管理、价值激活的协作平台。产品和服务深度聚焦创业领域服务和赋能服务。为创业者提供基于区块链技术服务的股权权益分割激励管理平台。\n" +
                "为创业者和协作者打造高效、精准的对接渠道，致力于使创业项目在初创及发展阶段得到最有效的多方帮助，大大提升创业项目的生存几率。\n" +
                "3.2 CO-OP根据项目质量决定是否提供本服务。CO-OP作为平台审核材料基于创业者提交材料范围，必要时需要创业者对于材料进行补充，但平台不承担材料真实性核查义务。\n" +
                "3.3 CO-OP将根据有关法律规定对于您的个人身份进行真实性验证，但不对项目以及协作者能力以及所拥有资源做合法、真实性验证。\n" +
                "3.4 CO-OP向初次使用的企业收取相关服务报酬，并依据企业使用CO-OP功能范围和情况保留后续收取付费报酬的权利。\n" +
                "3.5 CO-OP不对创业者和协作者达成协作过程收取手续费用。\n" +
                "3.6 CO-OP保留协作者在收到价值数字权证后进行二次交易时收取相关手续费用的权利。\n" +
                "3.7 CO-OP项目上线后资料将在平台中永久保留。项目正常终止（包括但不限于上市、并购、回购或清算）或异常终止（包括但不限于项目违反平台规则，项目违反法律或存在其他风险）CO-OP平台将依约定或相关法律负责价值数字权证后续清算工作。\n" +
                "\n" +
                "四、注册使用\n" +
                "4.1 CO-OP不对任何用户的任何登记资料承担责任（包括但不限于鉴别、核实任何登记资料的真实性、正确性、完整性、适用性及/或是否为最新资料的责任），也不对您未保管好自己的帐号和/或密码而导致的任何损失、损害承担责任。\n" +
                "4.2 如CO-OP有理由怀疑您的资料存在不真实的情况，有权暂停或终止您的帐号，并在现在和未来拒绝您使用全部或任何部分的CO-OP服务。\n" +
                "4.3 服务期内，用户对CO-OP账号仅有使用权，禁止赠与、借用、租用、转让或售卖。如果CO-OP发现使用者并非约定用户，即有权收回该帐号而无需向用户承担法律责任。由此带来的包括并不限于资料清空、通讯中断、BP遗失或机会丧失等损失，概由用户自行承担。\n" +
                "4.4 依照相关法律CO-OP将对个人用户进行实名验证，依照有关法律和规定执行。\n" +
                "\n" +
                "五、创业者权利义务\n" +
                "5.1 创业者有权依据本协议的约定向CO-OP提交申请，并以约定提交所需材料。并与CO-OP就合作签署所需相关的法律文件和手续。\n" +
                "5.2 创业者有权在符合相关条件的前提下收取CO-OP发布、推送、提供的相关信息，以便及时对平台提供的更新、活动、课程、优惠等作出响应。\n" +
                "5.3 创业者需声明并承诺其是具有完全民事行为能力的自然人且对提交创业项目拥有控股权或者经董事会授权。并且项目应为依据中国法律设立并有效存续的有限责任公司/股份有限公司/其他形式的组织。订立及履行本协议项下的所有义务和责任，本协议一经生效即对其具有法律约束力。\n" +
                "5.4 创业者有权发布相关信息并对已发布的信息进行更新、维护和管理，但需承诺并保证其发布的或向CO-OP提供的所有信息（包括但不限于基本资料、项目信息、BP等）是真实、准确、完整的，符合中国各项法律、法规及规范性文件以及CO-OP相关规则的规定，不存在虚假、误导性陈述或重大遗漏，不包含任何重大事实的错报或省略从而不会引起有关交易方对其中的陈述产生误解。\n" +
                "5.5 在接受CO-OP服务及与协作者进行合作的过程中，创业者不得以任何方式侵犯其他任何人依法享有的专利权、著作权、商标权、商业秘密等知识产权，第三人的名誉权或其他任何第三方的合法权益。\n" +
                "5.6 创业者承诺并保证依据自身判断对通过CO-OP创建、发布、提交的项目作出独立的、审慎的决定，并保证依法、依约完成其通过接受本服务而达成的各项交易。\n" +
                "5.7 创业者与协作者达成交易基于平等互利原则，由协作者提供创业者所需资源或提供包括但不限于智力、体力、行为等协助，由创业者支付经平台认定的价值数字权证作为酬劳。但创业者与协作者不得进行价值数字权证与现金交易行为。\n" +
                "5.8您承诺并保证遵守本协议以及其与CO-OP或其关联方签署的其他协议的约定，并遵守CO-OP制定的其他相关制度、规则，不实施有损于CO-OP的行为，包括但不限于以下行为：\n" +
                "5.7.1未经CO-OP书面许可，通过本协议约定方式之外的途径进入CO-OP计算机信息网络系统或者使用本协议约定范围外的信息网络资源或服务；\n" +
                "\t5.7.2未经CO-OP书面许可，对CO-OP计算机信息网络功能进行删除、修改或者增加，或对CO-OP计算机信息网络中存储、处理或者传输的数据和应用程序进行删除、修改或者增加；\n" +
                "\t5.7.3制作、传播计算机病毒等破坏性程序；\n" +
                "5.9创业者承诺并保证遵守如果在今后达成融资或发生任何关于股权质押、变更等行为，有义务及时通知CO-OP并提供成交信息。包括融资金额、估值额、投资方、成交理由等。\n" +
                "5.10若创业者违反上述承诺和保证，CO-OP有权单方终止本协议，停止对用户发布信息的技术支持和其他服务，并保存有关记录、删除含有有关信息的地址、目录或关闭服务器，必要时可依据中国法律、法规及规范性文件向国家有关机关报告。此外，创业者应对CO-OP及其他任何第三方由此受到的损失（包括合理的追索费用与必要支出）进行赔偿。\n" +
                "\n" +
                "六、协作者权利义务\n" +
                "6.1 协作者有权依据本协议的约定向CO-OP提交申请，并以约定提交所需材料。\n" +
                "6.2 协作者有权在符合相关条件的前提下收取CO-OP发布、推送、提供的相关信息，以便及时对平台提供的更新、活动、课程、优惠等作出响应。\n" +
                "6.3 协作者需声明并承诺其是具有完全民事行为能力的自然人且对提交个人信息与资源情况予以保证。协作者与CO-OP订立及履行本协议项下的所有义务和责任，本协议一经生效即对其具有法律约束力。\n" +
                "6.4 协作者有权发布相关信息并对已发布的信息进行更新、维护和管理，但需承诺并保证其发布的或向CO-OP提供的所有信息（包括但不限于基本资料、个人介绍、资源情况等）是真实、准确、完整的，符合中国各项法律、法规及规范性文件以及CO-OP相关规则的规定，不存在虚假、误导性陈述或重大遗漏，不包含任何重大事实的错报或省略从而不会引起相关人员对其中的陈述产生误解。\n" +
                "6.5 在接受CO-OP服务及与创业者进行合作的过程中，协作者不得以任何方式侵犯其他任何人依法享有的专利权、著作权、商标权、商业秘密等知识产权，第三人的名誉权或其他任何第三方的合法权益。\n" +
                "6.6 协作者承诺并保证依据自身判断对通过CO-OP创建、发布、提交的项目作出独立的、审慎的决定，并依据个人以及掌握资源的实际情况与创业者达成合作。并在合作确认后保证依法、依约完成其约定的各项服务内容。如遇特殊情况无法按照约定完成合作应及时通知创业者和平台。\n" +
                "6.7 协作者承诺并保证遵守本协议以及其与CO-OP或其关联方签署的其他协议的约定，并遵守CO-OP制定的其他相关制度、规则，不实施有损于CO-OP的行为，包括但不限于以下行为：\n" +
                "6.7.1未经CO-OP书面许可，通过本协议约定方式之外的途径进入CO-OP计算机信息网络系统或者使用本协议约定范围外的信息网络资源或服务；\n" +
                "\t6.7.2未经CO-OP书面许可，对CO-OP计算机信息网络功能进行删除、修改或者增加，或对CO-OP计算机信息网络中存储、处理或者传输的数据和应用程序进行删除、修改或者增加；\n" +
                "\t6.7.3制作、传播计算机病毒等破坏性程序；\n" +
                "6.8 若协作者违反上述承诺和保证，CO-OP有权单方终止本协议，停止对用户发布信息的技术支持和其他服务，并保存有关记录、删除含有有关信息的地址、目录或关闭服务器，必要时可依据中国法律、法规及规范性文件向国家有关机关报告。此外，协作者应对CO-OP及其他任何第三方由此受到的损失（包括合理的追索费用与必要支出）进行赔偿。\n" +
                "\n" +
                "七、CO-OP权利义务：\n" +
                "7.1 CO-OP声明并承诺其是依据中国法律设立并有效存续的有限责任公司，具有一切必要的权利及能力订立及履行本协议项下的所有义务和责任；本协议一经生效即对其具有法律约束力。\n" +
                "7.2 CO-OP为用户提供本协议约定的服务，但不对交易的达成或用户可能遭受的损失做出担保、保证故不承担相应责任，但CO-OP存在过错并经有效判决做出其他认定的除外。\n" +
                "7.3 CO-OP承诺并保证依据自身既有条件和水平最大限度的维护服务稳定性并积极促成交易，但不就技术设备故障（电讯系统或互联网的中断或无法运作、技术故障、计算机错误或病毒、信息损坏或丢失等）或用户的违约、破产、合并、分立等影响服务运行、交易达成的事件承担责任。\n" +
                "7.4 CO-OP保证严格尊重和维护用户隐私、保守用户的秘密。非经用户书面授权，CO-OP不得公开、修改或透露用户的基本资料、项目信息，亦不公开本服务中的非公开内容，但以下情况除外：\n" +
                "7.4.1根据中国法律、法规和规范性文件的强制性规定，或应有权机关要求，有义务提供用户通过CO-OP发布的信息内容及其发布时间、互联网地址或者域名等信息；\n" +
                "7.5在不透露用户隐私资料的前提下，CO-OP有权对整个用户数据库进行分析并发布分析结果，同时有权对用户数据库进行商业利用；\n" +
                "7.6如用户对资料的真实性隐瞒，或由于其他目的对提交资料、达成交易行为涉嫌纠纷、欺诈或具有违规、违法行为CO-OP有权依据现实情况对于账户以及账户内所涉及的资产进行冻结并依法进行处置。\n" +
                "\n" +
                "八、知识产权\n" +
                "8.1 CO-OP系与服务相关的全部知识产权的权利人，对本服务提供过程中包含的全部知识产权，包括但不限于任何文本、图片、图形、音频和/或视频资料享有及保留完整、独立的全部权利。\n" +
                "8.2对于CO-OP享有版权的内容，未经CO-OP同意，用户不得在任何媒体直接或间接发布、播放、出于播放或发布目的而改写或再发行CO-OP享有知识产权的或者CO-OP服务提供任何资料和信息，也不得将前述资料和信息用于任何商业目的；\n" +
                "8.3对于用户通过CO-OP发布的视频、音频或其它任何形式的内容包括文字、图片、连接等, 用户应该保证其对作品享有合法著作权/版权，并且同意授予CO-OP对所有上述作品和内容的在全球范围内的免费、不可撤销的无限期的并且可转让的排他性使用权许可。\n" +
                "\n" +
                "九、违约及免责\n" +
                "9.1除非本协议另有约定的外，对于协议一方的任何违约行为，另一方有权以书面形式通知违约方要求补救。除非违约方在十日内采取及时、充分的补救措施，否则要求补救的守约方有权就其所受的损失要求违约方赔偿。\n" +
                "9.2 CO-OP提供的全部服务信息仅依照该等信息提供时的现状提供并仅供用户参考，CO-OP不对信前述信息的准确性、完整性、适用性等做任何承诺和保证。用户应对CO-OP提供的信息自行判断，并承担因使用前述信息而引起的任何、全部风险，包括因其对信息的正确性、完整性或实用性的任何依赖或信任而导致的风险。CO-OP无需对因用户使用信息的任何行为导致的任何损失承担责任。\n" +
                "9.3本服务旨在帮助用户高效对接创业者与协作者,CO-OP不能对双方对项目的跟进深度和跟进内容进行保证。\n" +
                "9.4 如您选择在CO-OP平台进行融资服务，则我们不能对投资人、经您同意的路演及创业大赛组织方、财务顾问、媒体、其他我们为促成融资对接需要通知的第三方对您的融资项目的相关信息严格保密进行保证，也无法对其可能出现的违约、侵权行为进行保证。对上述行为或者原因造成或者发生的损失或者责任，CO-OP不承担任何责任。\n" +
                "9.5由于地震、台风、战争、罢工、政府行为、瘟疫、爆发性和流行性传染病或其他重大疫情、非因各方原因造成的火灾、基础电信网络中断造成的及其它各方不能预见并且对其发生后果不能预防或避免的不可抗力原因，致使相关服务中断，CO-OP不承担由此产生的损失，但应及时通知服务中断原因，并积极加以解决。\n" +
                "9.6对于因不可抗力或CO-OP不能预料、或不能控制的原因（包括但不限于计算机病毒或黑客攻击、系统不稳定、用户不当使用账户、以及其他任何技术、互联网络、通信线路原因等）造成的包括但不限于用户计算机信息和数据的安全，用户个人信息的安全等给用户或任何第三方造成的任何损失等，CO-OP不承担任何责任。\n" +
                "9.7由于用户以违法、违规或违反本协议约定等任何方式使用CO-OP服务的行为，包括但不限于登录网站内容违法、不真实、不正当，侵犯第三方任何合法权益等，给CO-OP或其他第三人造成的任何损失，用户同意承担由此造成的全部损害赔偿责任。\n" +
                "\n" +
                "十、协议修改与终止\n" +
                "10.1 根据有关法律、法规及规范性文件的变化,或者因业务发展需要,CO-OP有权对本协议的条款作出修改或变更,一旦本协议的内容发生变动,CO-OP将会直接在CO-OP网站、APP及其他CO-OP相关平台上公布修改之后的协议内容,该公布行为视为CO-OP已经通知用户修改内容。 CO-OP也可采用电子邮件或私信的传送方式,提示用户协议条款的修改、服务变更、或其它重要事项。如果不同意所改动的内容，用户可以主动取消CO-OP服务。如果用户继续享用CO-OP服务，则视为接受服务条款的变动。\n" +
                "10.2 本协议有效期限为12个月，自用户盖章/签字之日起生效；虽未盖章签字，但已缴纳相关费用或接受CO-OP提供的相关服务的，视为协议生效。用户可通过删除内容、关闭账户、停止浏览等提前终止使用CO-OP服务，但不得要求CO-OP退还服务费用。服务终止之后，免责条款、知识产权及其他依据其性质应长期有效的内容继续有效。\n" +
                "\n" +
                "十一、其他\n" +
                "11.1本协议签订地为中华人民共和国北京市。因本协议的成立、生效、履行、解释及纠纷解决,适用中华人民共和国大陆地区法律(不包括冲突法)，双方一致同意向北京市海淀区人民法院提起诉讼。\n" +
                "11.2本协议取代了各方就服务事项之前所达成的任何口头协议、谅解或备忘录。本协议项下的条款和条件构成各方完整和有约束力的义务，协议双方就本协议未尽事宜经协商一致签署的补充协议或确认函，与本协议具有同等的法律效力。\n" +
                "11.3 一方未行使或执行本服务条款任何权利或规定，不构成对前述权利或规定之放弃；\n" +
                "11.4如本协议之任何规定因与中华人民共和国法律抵触而无效，本协议其它规定仍应具有完整的效力及效果；\n" +
                "11.5本服务条款之标题仅供方便而设，不具任何法律或契约效果；\n" +
                "11.6本协议及本协议任何条款内容的最终解释权及修改权归北京盛美宇辰科技有限公司（CO-OP）所有。";
        tvBaseTitle.setText(getString(R.string.txt_title_user_agreement));
        toolBar = findViewById(R.id.toolbar_img);
        toolBar.setNavigationIcon(R.mipmap.back_left_btn);
        aboutDesc = findViewById(R.id.aboutDesc);
        aboutDesc.setText(aboutUs);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("用户协议页面");
        MobclickAgent.onResume(this); //统计时长
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("用户协议页面");
        MobclickAgent.onPause(this);
    }
}
