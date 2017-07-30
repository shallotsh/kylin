package org.kylin.util;


import java.io.*;
import java.text.Format;
import java.util.ArrayList;
import java.util.List;

import freemarker.template.utility.StringUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xwpf.usermodel.Borders;
import org.apache.poi.xwpf.usermodel.BreakClear;
import org.apache.poi.xwpf.usermodel.BreakType;
import org.apache.poi.xwpf.usermodel.LineSpacingRule;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.TextAlignment;
import org.apache.poi.xwpf.usermodel.UnderlinePatterns;
import org.apache.poi.xwpf.usermodel.VerticalAlign;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.kylin.bean.W3DCode;
import org.kylin.bean.WelfareCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author huangyawu
 * @date 2017/7/16 下午3:57.
 * ref: http://svn.apache.org/repos/asf/poi/trunk/src/examples/src/org/apache/poi/xwpf/usermodel/examples/SimpleDocument.java
 *      https://poi.apache.org/document/quick-guide-xwpf.html
 */
public class DocUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(DocUtils.class);

    private static final String BASE_PATH = "/var/attachment/";


    public static void saveW3DCodes(WelfareCode welfareCode, OutputStream outputStream) throws IOException{
        if(welfareCode == null || CollectionUtils.isEmpty(welfareCode.getW3DCodes())){
            throw new IllegalArgumentException("参数错误");
        }

        XWPFDocument doc = new XWPFDocument();

        XWPFParagraph header = doc.createParagraph();
        header.setVerticalAlignment(TextAlignment.TOP);
        header.setWordWrap(true);
        header.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun hr1 = header.createRun();
        hr1.setText(toUTF8("《我要发·518》福彩3D预测报表"));
        hr1.setBold(true);
        hr1.setUnderline(UnderlinePatterns.DOT_DOT_DASH);
        hr1.setTextPosition(20);
        hr1.setFontSize(28);
        hr1.addBreak();

        XWPFRun hr2 = header.createRun();

        hr2.setText(toUTF8("共计" + welfareCode.getW3DCodes().size() + "注3D码!!!     时间：" + CommonUtils.getCurrentDateString()));
        hr2.setTextPosition(10);
        hr2.setFontSize(18);


        List<W3DCode> pairCodes = TransferUtil.getPairCodes(welfareCode.getW3DCodes());
        String title = String.format("对子共计 %d 注", pairCodes.size());
        writeCodes(doc.createParagraph(), pairCodes, toUTF8(title));

        List<W3DCode> nonPairCodes = TransferUtil.getNonPairCodes(welfareCode.getW3DCodes());
        title = String.format("对子共计 %d 注", nonPairCodes.size());
        writeCodes(doc.createParagraph(), nonPairCodes, toUTF8(title));

        doc.write(outputStream);

    }

    public static String saveW3DCodes(WelfareCode welfareCode) throws IOException{
        if(welfareCode == null || CollectionUtils.isEmpty(welfareCode.getW3DCodes())){
            return "";
        }

        String fileName = CommonUtils.getCurrentTimeString();
        String subDirectory = fileName.substring(0,6);
        String targetDirName = BASE_PATH + subDirectory;

        if(!createDirIfNotExist(targetDirName)){
            LOGGER.info("save-w3dCodes-create-directory-error targetDirName={}", targetDirName);
            throw new IOException("directory create error");
        }

        XWPFDocument doc = new XWPFDocument();

        XWPFParagraph header = doc.createParagraph();
        header.setVerticalAlignment(TextAlignment.TOP);
        header.setWordWrap(true);
        header.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun hr1 = header.createRun();
        hr1.setText(toUTF8("《我要发·518》福彩3D预测报表"));
        hr1.setBold(true);
        hr1.setUnderline(UnderlinePatterns.DOT_DOT_DASH);
        hr1.setTextPosition(20);
        hr1.setFontSize(28);
        hr1.addBreak();

        XWPFRun hr2 = header.createRun();

        hr2.setText(toUTF8("共计" + welfareCode.getW3DCodes().size() + "注3D码!!!     时间：" + CommonUtils.getCurrentDateString()));
        hr2.setTextPosition(10);
        hr2.setFontSize(18);


        List<W3DCode> pairCodes = TransferUtil.getPairCodes(welfareCode.getW3DCodes());
        String title = String.format("对子共计 %d 注", pairCodes.size());
        writeCodes(doc.createParagraph(), pairCodes, toUTF8(title));

        List<W3DCode> nonPairCodes = TransferUtil.getNonPairCodes(welfareCode.getW3DCodes());
        title = String.format("非对子共计 %d 注", nonPairCodes.size());
        writeCodes(doc.createParagraph(), nonPairCodes, toUTF8(title));

        // 保存
        StringBuilder sb = new StringBuilder();
        sb.append(targetDirName);
        sb.append(File.separator);
        sb.append(fileName);
        sb.append(".docx");
        FileOutputStream out = new FileOutputStream(sb.toString());
        doc.write(out);
        out.close();

        return fileName + ".docx";
    }




    public static void writeCodes(XWPFParagraph paragraph, List<W3DCode> w3DCodes, String titleString){
        if(paragraph == null || CollectionUtils.isEmpty(w3DCodes)){
            return;
        }

        XWPFRun title = paragraph.createRun();
        title.setFontSize(20);
        title.setBold(true);
        title.setText(titleString);
        title.addBreak();

        XWPFRun hr = paragraph.createRun();
        hr.setFontSize(10);
        hr.setText("-----------------------------------");
        hr.addBreak();

        XWPFRun content = paragraph.createRun();
        content.setFontSize(20);


        for(W3DCode w3DCode : w3DCodes) {
            content.setText(w3DCode.toString() + "     ");
        }

        content.addBreak();
        content.setTextPosition(20);

        XWPFRun sep = paragraph.createRun();
        sep.setTextPosition(50);
    }


    public static boolean createDirIfNotExist(String destDirName) {
        File dir = new File(destDirName);
        if (dir.exists()) {
            return true;
        }
        if (!destDirName.endsWith(File.separator)) {
            destDirName = destDirName + File.separator;
        }

        //创建目录
        if (dir.mkdirs()) {
            LOGGER.info("创建目录" + destDirName + "成功！");
            return true;
        } else {
            LOGGER.info("创建目录" + destDirName + "失败！");
            return false;
        }
    }

    public static String toUTF8(String str){
        if(StringUtils.isBlank(str)){
            return str;
        }

        try {
            String target = new String(str.getBytes(), "UTF-8");
            LOGGER.info("target:{}", target);
            return target;
        } catch (UnsupportedEncodingException e) {
            return str;
        }
    }

    public static void main(String[] args) throws Exception {
        WelfareCode welfareCode = new WelfareCode();
        List<W3DCode> w3DCodes = new ArrayList<>();
        W3DCode w3DCode = new W3DCode(2,3,5);
        w3DCodes.add(w3DCode);
        w3DCode = new W3DCode(2,3,5);
        w3DCodes.add(w3DCode);
        w3DCode = new W3DCode(2,2,5);
        w3DCodes.add(w3DCode);
        w3DCode = new W3DCode(2,1,5);
        w3DCodes.add(w3DCode);
        w3DCode = new W3DCode(1,3,5);
        w3DCodes.add(w3DCode);
        w3DCode = new W3DCode(1,2,5);
        w3DCodes.add(w3DCode);
        w3DCode = new W3DCode(1,1,5);
        w3DCodes.add(w3DCode);
        w3DCode = new W3DCode(2,3,4);
        w3DCodes.add(w3DCode);
        w3DCode = new W3DCode(2,2,4);
        w3DCodes.add(w3DCode);
        w3DCode = new W3DCode(2,1,4);
        w3DCodes.add(w3DCode);
        w3DCode = new W3DCode(2,3,6);
        w3DCodes.add(w3DCode);
        w3DCode = new W3DCode(2,2,6);
        w3DCodes.add(w3DCode);
        w3DCode = new W3DCode(2,1,6);
        w3DCodes.add(w3DCode);
        welfareCode.setW3DCodes(w3DCodes);


        String filePath = saveW3DCodes(welfareCode);

        LOGGER.info("file saved!!! path:{}", filePath);
    }

}
