package org.kylin.constant;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author huangyawu
 * @date 2017/10/5 上午10:40.
 */
public interface WelfareConfig {
    String[] HFC = {"002","004","006","013","017","022","030","036","038","045",
            "055","056","057","058","062","063","068","069","072","077","080","082",
            "083","084","085","087","100","106","107","108","109","115","119","122",
            "124","127","130","133","139","141","142","143","144","145","146","156",
            "157","158","160","162","163","164","168","169","170","172","175","176",
            "178","180","182","189","194","199","200","204","206","208","209","210",
            "211","212","214","215","219","220","224","225","227","236","238","241",
            "242","243","244","246","247","248","250","251","256","257","258","260",
            "262","263","265","272","277","278","279","281","283","284","285","286",
            "287","294","298","300","301","303","310","312","315","316","319","329",
            "330","334","335","337","341","342","344","345","347","348","350","351",
            "352","353","354","358","359","361","363","365","367","368","369","370",
            "371","383","384","385","386","388","389","391","392","397","404","409",
            "411","412","415","418","419","420","424","425","428","429","430","431",
            "432","439","442","447","454","456","457","458","464","466","467","472",
            "474","475","478","481","482","485","487","488","491","494","495","497",
            "499","500","502","506","510","518","521","523","525","528","531","537",
            "540","541","544","546","548","552","553","558","559","560","571","573",
            "574","575","578","580","582","583","585","587","588","590","591","594",
            "597","608","610","612","613","618","623","626","627","628","630","631",
            "634","635","636","638","640","643","644","645","648","650","652","657",
            "659","662","668","669","670","672","675","677","679","681","682","683",
            "684","685","687","688","689","691","693","697","699","700","701","702",
            "703","704","708","712","713","714","716","721","723","725","726","727",
            "730","738","742","743","746","750","753","757","758","759","761","763",
            "764","767","768","769","770","773","774","779","780","782","784","785",
            "787","788","789","791","793","796","803","804","805","806","808","809",
            "813","816","818","819","820","821","827","829","832","840","843","849",
            "852","854","855","858","859","862","863","866","868","870","871","875",
            "876","879","883","886","892","895","901","902","903","905","907","910",
            "911","915","918","920","921","922","926","927","931","934","936","937",
            "938","940","941","945","946","954","955","957","958","960","965","968",
            "969","970","975","977","980","982","985","986","987","992","998"};

    Set<String> hfcSets = new HashSet<>(Arrays.asList(HFC));
}
